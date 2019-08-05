package org.uniprot.core.cv.taxonomy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Class that parses a taxonomic line found within the taxonomy file
 */
public class FileNodeIterable implements Iterable<TaxonomicNode> {
	private static final String TAB_SEPARATOR = "\t";
	private static final String NULL_PLACEHOLDER = "\\N";

	private final Logger logger = LoggerFactory.getLogger(getClass());

	private String fieldSeparator;
	private String nullPlaceholder;
	private File nodeFile;

	public FileNodeIterable(File nodeFile) {
		this(nodeFile, TAB_SEPARATOR, NULL_PLACEHOLDER);
	}

	public FileNodeIterable(File taxonomyFile, String fieldSeparator, String nullPlaceholder) {
		if (taxonomyFile == null) {
			throw new IllegalArgumentException("Taxonomy file is null");
		}
		if (fieldSeparator == null || fieldSeparator.isEmpty()) {
			throw new IllegalArgumentException("Invalid field separator: " + fieldSeparator);
		}
		if (nullPlaceholder == null) {
			throw new IllegalArgumentException("Invalid null placeholder");
		}

		this.nodeFile = taxonomyFile;
		this.fieldSeparator = fieldSeparator;
		this.nullPlaceholder = nullPlaceholder;
	}

	@Override
	public Iterator<TaxonomicNode> iterator() {
		try {
			FileReader fileReader = new FileReader(nodeFile);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			logger.info("Building taxonomy node iterator from file: {}", nodeFile.getAbsolutePath());

			return new Iterator<TaxonomicNode>() {
				TaxonomicNode next = null;

				@Override
				public boolean hasNext() {
					boolean hasNext = false;

					try {
						if (next != null) {
							hasNext = true;
						} else {
							String line = bufferedReader.readLine();

							if (line != null) {
								String[] nodeElements = line.split(fieldSeparator);
								next = createNode(nodeElements);
								hasNext = true;
							}
						}
					} catch (IOException e) {
						throw new IllegalStateException("An exception occurred whilst reading from the taxonomy file",
								e);
					} finally {
						if (!hasNext) {
							try {
								bufferedReader.close();
							} catch (IOException e) {
								logger.error("An exception ocurred when closing the reader.", e);
							}

							try {
								fileReader.close();
							} catch (IOException e) {
								logger.error("An exception ocurred when closing the reader.", e);
							}
						}
					}

					return hasNext;
				}

				@Override
				public TaxonomicNode next() {
					if (!hasNext()) {
						throw new NoSuchElementException("No elements left in iterator");
					}

					TaxonomicNode next = this.next;
					this.next = null;

					return next;
				}
			};
		} catch (IOException e) {
			throw new IllegalStateException("An exception occurred whilst accessing the taxonomy file", e);
		}
	}

	private TaxonomicNode createNode(String[] nodeElements) {
		Integer id = parseInteger(nodeElements[0]);

		String scientificName = parseString(nodeElements[9]); //sptr_scientific
		if(scientificName == null || scientificName.equalsIgnoreCase("\\N")){
			scientificName = parseString(nodeElements[7]); //ncbi_scientific
		}

		String commonName = parseString(nodeElements[10]); //sptr_common
		if(commonName == null || commonName.equalsIgnoreCase("\\N")){
			commonName = parseString(nodeElements[8]); //ncbi_common;
		}

		Integer parentId = parseInteger(nodeElements[1]);
		TaxonomicNode parentNode = createParentNode(parentId);

		TaxonomicNode node = new TaxonomicNodeImpl.Builder(id, scientificName)
				.withCommonName(commonName)
				.withSynonymName(parseString(nodeElements[11]))
				.withMnemonic(parseString(nodeElements[12]))
				.childOf(parentNode).build();
		return node;
	}

	private TaxonomicNode createParentNode(Integer parentId) {
		TaxonomicNode parentNode = null;

		if (parentId != null) {
			parentNode = new TaxonomicNodeImpl.Builder(parentId,
					TaxonomicNodeImpl.UNDEFINED_SCIENTIFIC_NAME).build();
		}

		return parentNode;
	}

	private Integer parseInteger(String valueText) {
		Integer value = null;

		if (valueText != null) {
			valueText = valueText.trim();

			if (!valueText.isEmpty() && !valueText.equals(nullPlaceholder)) {
				value = Integer.valueOf(valueText);
			}
		}

		return value;
	}

	private String parseString(String valueText) {
		String value = null;

		if (valueText != null) {
			valueText = valueText.trim();

			if (!valueText.isEmpty() && !valueText.equals(nullPlaceholder)) {
				value = valueText;
			}
		}

		return value;
	}
}

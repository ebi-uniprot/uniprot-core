package org.uniprot.core.cv.taxonomy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

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
	public FileNodeIterator iterator() {
		return new FileNodeIterator(this.nodeFile, this.fieldSeparator, this.nullPlaceholder);
	}
}

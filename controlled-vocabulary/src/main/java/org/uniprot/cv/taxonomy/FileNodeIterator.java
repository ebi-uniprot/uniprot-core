package org.uniprot.cv.taxonomy;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.NoSuchElementException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.uniprot.cv.taxonomy.impl.TaxonomicNodeImpl;

public class FileNodeIterator implements Iterator<TaxonomicNode> {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private FileReader fileReader;
    private BufferedReader bufferedReader;
    private TaxonomicNode next;
    private String fieldSeparator;
    private String nullPlaceholder;

    private static final String READER_ERROR_MSG = "An exception occurred when closing the reader.";

    public FileNodeIterator(File file, String fieldSeparator, String nullPlaceholder) {
        try {
            this.fileReader = new FileReader(file);
            this.bufferedReader = new BufferedReader(fileReader);
            this.fieldSeparator = fieldSeparator;
            this.nullPlaceholder = nullPlaceholder;
            this.next = readNode(this.bufferedReader.readLine());
        } catch (IOException ex) {
            if (this.bufferedReader != null) {
                try {
                    this.bufferedReader.close();
                } catch (IOException e) {
                    logger.error(READER_ERROR_MSG, e);
                }
            }

            if (this.fileReader != null) {
                try {
                    this.fileReader.close();
                } catch (IOException e) {
                    logger.error("An exception ocurred when closing the fileReader.", e);
                }
            }
            this.bufferedReader = null;
            this.fileReader = null;
            throw new IllegalStateException(
                    "An exception occurred whilst accessing the taxonomy file", ex);
        }
    }

    @Override
    public boolean hasNext() {
        return this.next != null;
    }

    @Override
    public TaxonomicNode next() {
        TaxonomicNode localNext = this.next;
        try {
            if (!hasNext()) {
                throw new NoSuchElementException("No elements left in iterator");
            } else {
                this.next = readNode(this.bufferedReader.readLine());
            }
        } catch (IOException e) {
            throw new IllegalStateException(
                    "An exception occurred whilst reading from the taxonomy file", e);
        } finally {
            if (!hasNext()) {
                try {
                    this.bufferedReader.close();
                } catch (IOException e) {
                    logger.error(READER_ERROR_MSG, e);
                }

                try {
                    this.fileReader.close();
                } catch (IOException e) {
                    logger.error(READER_ERROR_MSG, e);
                }
            }
        }
        return localNext;
    }

    private TaxonomicNode createNode(String[] nodeElements) {
        Integer id = parseInteger(nodeElements[0]);

        String scientificName = parseString(nodeElements[9]); // sptr_scientific
        if (scientificName == null || scientificName.equalsIgnoreCase("\\N")) {
            scientificName = parseString(nodeElements[7]); // ncbi_scientific
        }

        String commonName = parseString(nodeElements[10]); // sptr_common
        if (commonName == null || commonName.equalsIgnoreCase("\\N")) {
            commonName = parseString(nodeElements[8]); // ncbi_common
        }

        Integer parentId = parseInteger(nodeElements[1]);
        TaxonomicNode parentNode = createParentNode(parentId);

        return new TaxonomicNodeImpl.Builder(id, scientificName)
                .withCommonName(commonName)
                .withSynonymName(parseString(nodeElements[11]))
                .withMnemonic(parseString(nodeElements[12]))
                .childOf(parentNode)
                .build();
    }

    private TaxonomicNode createParentNode(Integer parentId) {
        TaxonomicNode parentNode = null;

        if (parentId != null) {
            parentNode =
                    new TaxonomicNodeImpl.Builder(
                                    parentId, TaxonomicNodeImpl.UNDEFINED_SCIENTIFIC_NAME)
                            .build();
        }

        return parentNode;
    }

    private Integer parseInteger(String valueText) {
        Integer value = null;

        if (valueText != null) {
            valueText = valueText.trim();

            if (!valueText.isEmpty() && !valueText.equals(this.nullPlaceholder)) {
                value = Integer.valueOf(valueText);
            }
        }

        return value;
    }

    private String parseString(String valueText) {
        String value = null;

        if (valueText != null) {
            valueText = valueText.trim();

            if (!valueText.isEmpty() && !valueText.equals(this.nullPlaceholder)) {
                value = valueText;
            }
        }

        return value;
    }

    private TaxonomicNode readNode(String line) {
        TaxonomicNode node = null;
        if (line != null) {
            String[] nodeElements = line.split(this.fieldSeparator);
            node = createNode(nodeElements);
        }

        return node;
    }
}

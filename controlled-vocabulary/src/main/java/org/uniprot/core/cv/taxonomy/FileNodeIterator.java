package org.uniprot.core.cv.taxonomy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class FileNodeIterator implements Iterator<TaxonomicNode> {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private FileReader fileReader;
    private BufferedReader bufferedReader;
    private TaxonomicNode next;
    private String fieldSeparator;
    private String nullPlaceholder;

    public FileNodeIterator(File file, String fieldSeparator, String nullPlaceholder) {
        try {
            this.fileReader = new FileReader(file);
            this.bufferedReader = new BufferedReader(fileReader);
        } catch (IOException ex) {
            if (this.bufferedReader != null) {
                try {
                    this.bufferedReader.close();
                } catch (IOException e) {
                    logger.error("An exception ocurred when closing the reader.", e);
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
            throw new IllegalStateException("An exception occurred whilst accessing the taxonomy file", ex);
        }

        this.fieldSeparator = fieldSeparator;
        this.nullPlaceholder = nullPlaceholder;

    }

    @Override
    public boolean hasNext() {
        boolean hasNext = false;
        try {
            if (this.next != null) {
                hasNext = true;
            } else {
                String line = this.bufferedReader.readLine();
                if (line != null) {
                    String[] nodeElements = line.split(this.fieldSeparator);
                    this.next = createNode(nodeElements);
                    hasNext = true;
                }
            }
        } catch (IOException e) {
            throw new IllegalStateException("An exception occurred whilst reading from the taxonomy file", e);
        } finally {
            if (!hasNext) {
                try {
                    this.bufferedReader.close();
                } catch (IOException e) {
                    logger.error("An exception ocurred when closing the reader.", e);
                }

                try {
                    this.fileReader.close();
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

    private TaxonomicNode createNode(String[] nodeElements) {
        Integer id = parseInteger(nodeElements[0]);

        String scientificName = parseString(nodeElements[9]); //sptr_scientific
        if (scientificName == null || scientificName.equalsIgnoreCase("\\N")) {
            scientificName = parseString(nodeElements[7]); //ncbi_scientific
        }

        String commonName = parseString(nodeElements[10]); //sptr_common
        if (commonName == null || commonName.equalsIgnoreCase("\\N")) {
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
}

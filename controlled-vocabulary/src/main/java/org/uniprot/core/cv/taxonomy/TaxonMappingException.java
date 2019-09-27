package org.uniprot.core.cv.taxonomy;

/**
 * Exception that will occur when there is a problem with the retrieval of a taxonomic node
 */
public class TaxonMappingException extends RuntimeException {
    public TaxonMappingException(String message) {
        super(message);
    }
}

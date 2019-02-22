package uk.ac.ebi.uniprot.domain.citation;

/**
 * @link uk.ac.ebi.kraken.interfaces.common.Value;
 */
public interface Thesis extends Citation {

    String getInstitute();

    String getAddress();

    boolean hasInstitute();

    boolean hasAddress();
}

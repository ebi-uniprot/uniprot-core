package uk.ac.ebi.uniprot.domain.uniprot.citation;

/**
 * @link uk.ac.ebi.kraken.interfaces.common.Value;
 */

public interface Thesis extends Citation{

	public Institute getInstitute();

    public void setInstitute(Institute institute);

    public Adress getAddress();
}

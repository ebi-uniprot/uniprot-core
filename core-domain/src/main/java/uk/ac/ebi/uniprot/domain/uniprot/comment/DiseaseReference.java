package uk.ac.ebi.uniprot.domain.uniprot.comment;

/**
 * Reference of an external source the best describes the disease
 *
 * @author Francesco Fazzini
 * @author Ricardo Antunes
 * @see Disease
 * @version 1.0
 */
public interface DiseaseReference {
    /**
     * @return an expression used by the external source to uniquely identify the disease
     */
	public String getDiseaseReferenceId();


    /**
     * @return the source that references the disease
     */
	public DiseaseReferenceType getDiseaseReferenceType();

}
package uk.ac.ebi.uniprot.domain.uniprot.citation;



/**
 * Created by IntelliJ IDEA.
 * User: barrera
 * Date: 08/06/11
 * Time: 11:18
 */

public interface CitationXrefs {
    public PubMedId getPubmedId();
    public DOI getDOI();
    public AgricolaId getAgricolaId();
    public boolean hasPubmedId();
    public boolean hasDOI();
    public boolean hasAgricolaId();
}

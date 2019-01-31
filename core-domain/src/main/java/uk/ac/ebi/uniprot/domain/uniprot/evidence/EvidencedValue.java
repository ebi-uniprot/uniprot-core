package uk.ac.ebi.uniprot.domain.uniprot.evidence;


import uk.ac.ebi.uniprot.domain.Value;

public interface EvidencedValue extends Value, HasEvidences {
    public String getDisplayed(String separator);
}

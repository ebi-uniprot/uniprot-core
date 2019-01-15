package uk.ac.ebi.uniprot.domain.uniprot.evidence2;


import uk.ac.ebi.uniprot.domain.Value;

public interface EvidencedValue extends Value, HasEvidences {
    public String getDisplayed(String separator);
}

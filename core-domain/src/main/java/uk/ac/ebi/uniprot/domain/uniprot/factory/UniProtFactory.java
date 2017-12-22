package uk.ac.ebi.uniprot.domain.uniprot.factory;

import uk.ac.ebi.uniprot.domain.uniprot.EvidencedValue;
import uk.ac.ebi.uniprot.domain.uniprot.UniProtAccession;
import uk.ac.ebi.uniprot.domain.uniprot.evidences.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.impl.EvidencedValueImpl;
import uk.ac.ebi.uniprot.domain.uniprot.impl.UniProtAccessionImpl;

import java.util.List;

public final class UniProtFactory {
    public static EvidencedValue createEvidencedValue(String value, List<Evidence> evidences) {
        return new EvidencedValueImpl(value, evidences);
    }
    public static UniProtAccession createUniProtAccession(String value){
        return new UniProtAccessionImpl(value);
    }
}

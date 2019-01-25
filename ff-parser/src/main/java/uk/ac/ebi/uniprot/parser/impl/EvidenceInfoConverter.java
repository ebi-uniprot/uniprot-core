package uk.ac.ebi.uniprot.parser.impl;

import uk.ac.ebi.uniprot.domain.uniprot.evidence2.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.evidence2.EvidenceType;
import uk.ac.ebi.uniprot.parser.Converter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static uk.ac.ebi.uniprot.domain.uniprot.evidence2.impl.EvidenceHelper.parseEvidenceLines;

public class EvidenceInfoConverter extends EvidenceCollector implements Converter<EvidenceInfo, Map<Object, List<Evidence>>> {
    private static final EvidenceType REFERENCE = new EvidenceType("Reference");
    private static final String REF_PREFIX = "Ref.";

    @Override
    public Map<Object, List<Evidence>> convert(EvidenceInfo evidenceInfo) {
        Map<Object, List<Evidence>> evidences = new HashMap<>();
        if (evidenceInfo == null)
            return evidences;

        for (Map.Entry<Object, List<String>> entry : evidenceInfo.evidences.entrySet()) {
            // TODO: 22/01/19 sort this out
            evidences.put(entry.getKey(), parseEvidenceLines(entry.getValue()));
        }

        return evidences;
    }
}

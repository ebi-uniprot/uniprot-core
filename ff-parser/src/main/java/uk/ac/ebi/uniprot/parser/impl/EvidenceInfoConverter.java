package uk.ac.ebi.uniprot.parser.impl;

import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;
import uk.ac.ebi.uniprot.parser.Converter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static uk.ac.ebi.uniprot.domain.uniprot.evidence.impl.EvidenceHelper.parseEvidenceLines;

public class EvidenceInfoConverter extends EvidenceCollector implements Converter<EvidenceInfo, Map<Object, List<Evidence>>> {
    @Override
    public Map<Object, List<Evidence>> convert(EvidenceInfo evidenceInfo) {
        Map<Object, List<Evidence>> evidences = new HashMap<>();
        if (evidenceInfo == null)
            return evidences;

        for (Map.Entry<Object, List<String>> entry : evidenceInfo.evidences.entrySet()) {
            evidences.put(entry.getKey(), parseEvidenceLines(entry.getValue()));
        }

        return evidences;
    }
}

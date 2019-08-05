package org.uniprot.core.flatfile.parser.impl;

import static org.uniprot.core.uniprot.evidence.impl.EvidenceHelper.parseEvidenceLines;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.uniprot.core.flatfile.parser.Converter;
import org.uniprot.core.uniprot.evidence.Evidence;

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

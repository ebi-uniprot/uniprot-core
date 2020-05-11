package org.uniprot.core.flatfile.parser.impl;

import static org.uniprot.cv.evidence.EvidenceHelper.parseEvidenceLines;

import org.uniprot.core.flatfile.parser.Converter;
import org.uniprot.core.uniprotkb.evidence.Evidence;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EvidenceInfoConverter extends EvidenceCollector
        implements Converter<EvidenceInfo, Map<Object, List<Evidence>>> {
    @Override
    public Map<Object, List<Evidence>> convert(EvidenceInfo evidenceInfo) {
        Map<Object, List<Evidence>> evidences = new HashMap<>();
        if (evidenceInfo == null) return evidences;

        for (Map.Entry<Object, List<String>> entry : evidenceInfo.getEvidences().entrySet()) {
            evidences.put(entry.getKey(), parseEvidenceLines(entry.getValue()));
        }

        return evidences;
    }
}

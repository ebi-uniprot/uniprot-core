package org.uniprot.core.flatfile.parser.impl;

import org.uniprot.core.uniprotkb.evidence.Evidence;

import java.util.List;
import java.util.Map;

public class EvidenceConverterHelper {
    private static EvidenceInfoConverter evConverter = new EvidenceInfoConverter();

    public static Map<Object, List<Evidence>> convert(EvidenceInfo evidenceInfo) {
        return evConverter.convert(evidenceInfo);
    }
}

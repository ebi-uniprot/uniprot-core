package uk.ac.ebi.uniprot.flatfile.parser.impl;

import java.util.List;
import java.util.Map;

import org.uniprot.core.uniprot.evidence.Evidence;

public class EvidenceConverterHelper {
    private static EvidenceInfoConverter evConverter = new EvidenceInfoConverter();

    public static Map<Object, List<Evidence>> convert(EvidenceInfo evidenceInfo) {
        return evConverter.convert(evidenceInfo);
    }
}

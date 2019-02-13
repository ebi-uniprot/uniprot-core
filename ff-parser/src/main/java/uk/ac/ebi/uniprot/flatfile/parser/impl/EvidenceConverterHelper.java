package uk.ac.ebi.uniprot.flatfile.parser.impl;

import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;

import java.util.List;
import java.util.Map;

public class EvidenceConverterHelper {
    private static EvidenceInfoConverter evConverter = new EvidenceInfoConverter();

    public static Map<Object, List<Evidence>> convert(EvidenceInfo evidenceInfo) {
        return evConverter.convert(evidenceInfo);
    }
}

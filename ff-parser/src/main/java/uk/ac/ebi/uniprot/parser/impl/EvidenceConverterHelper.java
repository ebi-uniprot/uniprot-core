package uk.ac.ebi.uniprot.parser.impl;

import uk.ac.ebi.uniprot.domain.uniprot.evidence2.Evidence;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class EvidenceConverterHelper {
    private static EvidenceInfoConverter evConverter = new EvidenceInfoConverter();

    public static Map<Object, List<Evidence>> convert(EvidenceInfo evidenceInfo) {
        return evConverter.convert(evidenceInfo);
    }

    public static List<Evidence> convert(List<String> evStrs) {
        return evStrs.stream().map(evConverter::parseEvidenceLine).collect(Collectors.toList());
    }
}

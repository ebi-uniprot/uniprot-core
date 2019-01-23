package uk.ac.ebi.uniprot.parser.impl;

import uk.ac.ebi.uniprot.domain.DBCrossReference;
import uk.ac.ebi.uniprot.domain.impl.DBCrossReferenceImpl;
import uk.ac.ebi.uniprot.domain.uniprot.evidence2.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.evidence2.EvidenceCode;
import uk.ac.ebi.uniprot.domain.uniprot.evidence2.EvidenceType;
import uk.ac.ebi.uniprot.domain.uniprot.evidence2.builder.EvidenceBuilder;
import uk.ac.ebi.uniprot.parser.Converter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    private List<Evidence> parseEvidenceLines(List<String> evStrs) {
        return evStrs.stream().map(this::parseEvidenceLine).collect(Collectors.toList());
    }

    Evidence parseEvidenceLine(String val) {
        String[] token = val.split("\\|");
        String code = token[0];
        DBCrossReference<EvidenceType> xref = null;
        EvidenceBuilder evidenceBuilder = new EvidenceBuilder();
        if (token.length == 2) {
            int index = token[1].indexOf(':');
            if (index == -1) {
                if (token[1].startsWith(REF_PREFIX)) {
                    xref = new DBCrossReferenceImpl<>(REFERENCE, token[1]);
                } else {
                    throw new IllegalArgumentException(val + " is not valid evidence string");
                }
            } else {
                String type = token[1].substring(0, index);
                String id = token[1].substring(index + 1);
                xref = new DBCrossReferenceImpl<>(new EvidenceType(type), id);
            }
        }

        EvidenceCode evidenceCode = EvidenceCode.codeOf(code);

        return evidenceBuilder
                .evidenceCode(evidenceCode)
                .databaseName(xref.getDatabaseType().getName())
                .databaseId(xref.getId())
                .build();
    }
}

package org.uniprot.core.uniprot.evidence.impl;

import static org.uniprot.core.uniprot.evidence.impl.EvidenceImpl.REFERENCE;

import java.util.List;
import java.util.stream.Collectors;

import org.uniprot.core.DBCrossReference;
import org.uniprot.core.impl.DBCrossReferenceImpl;
import org.uniprot.core.uniprot.evidence.Evidence;
import org.uniprot.core.uniprot.evidence.EvidenceCode;
import org.uniprot.core.uniprot.evidence.EvidenceType;
import org.uniprot.core.uniprot.evidence.builder.EvidenceBuilder;

/**
 * Created 22/01/19
 *
 * @author Edd
 */
public class EvidenceHelper {
    private static final String REF_PREFIX = "Ref.";

    public static List<Evidence> parseEvidenceLines(List<String> evStrs) {
        return evStrs.stream().map(EvidenceHelper::parseEvidenceLine).collect(Collectors.toList());
    }

    public static Evidence parseEvidenceLine(String val) {
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

        EvidenceBuilder builder = evidenceBuilder.evidenceCode(evidenceCode);
        if (xref != null) {
            builder.databaseName(xref.getDatabaseType().getName()).databaseId(xref.getId());
        }

        return builder.build();
    }
}

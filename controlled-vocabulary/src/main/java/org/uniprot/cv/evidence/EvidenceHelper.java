package org.uniprot.cv.evidence;

import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Nonnull;

import org.uniprot.core.CrossReference;
import org.uniprot.core.impl.CrossReferenceImpl;
import org.uniprot.core.uniprot.evidence.Evidence;
import org.uniprot.core.uniprot.evidence.EvidenceCode;
import org.uniprot.core.uniprot.evidence.EvidenceDatabase;
import org.uniprot.core.uniprot.evidence.builder.EvidenceBuilder;

/**
 * Created 22/01/19
 *
 * @author Edd
 */
public class EvidenceHelper {
    private static final String REF_PREFIX = "Ref.";
    private static final EvidenceDatabase REFERENCE = new EvidenceDatabase("Reference");

    public static @Nonnull List<Evidence> parseEvidenceLines(@Nonnull List<String> evStrs) {
        return evStrs.stream().map(EvidenceHelper::parseEvidenceLine).collect(Collectors.toList());
    }

    public static @Nonnull Evidence parseEvidenceLine(@Nonnull String val) {
        String[] token = val.split("\\|");
        String code = token[0];
        CrossReference<EvidenceDatabase> xref = null;
        EvidenceBuilder evidenceBuilder = new EvidenceBuilder();
        if (token.length == 2) {
            int index = token[1].indexOf(':');
            if (index == -1) {
                if (token[1].startsWith(REF_PREFIX)) {
                    xref = new CrossReferenceImpl<>(REFERENCE, token[1]);
                } else {
                    throw new IllegalArgumentException(val + " is not valid evidence string");
                }
            } else {
                String type = token[1].substring(0, index);
                String id = token[1].substring(index + 1);
                xref = new CrossReferenceImpl<>(new EvidenceDatabase(type), id);
            }
        }

        EvidenceCode evidenceCode = EvidenceCode.typeOf(code);

        EvidenceBuilder builder = evidenceBuilder.evidenceCode(evidenceCode);
        if (xref != null) {
            builder.databaseName(xref.getDatabase().getName()).databaseId(xref.getId());
        }

        return builder.build();
    }
}

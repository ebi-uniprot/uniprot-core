package org.uniprot.core.flatfile.parser.impl.ss;

import static org.uniprot.core.flatfile.writer.impl.FFLineConstant.SEPARATOR_SEMICOLON;
import static org.uniprot.core.flatfile.writer.impl.FFLineConstant.STOP;

import java.util.ArrayList;
import java.util.List;

import org.uniprot.core.flatfile.writer.FFLine;
import org.uniprot.core.flatfile.writer.LineType;
import org.uniprot.core.flatfile.writer.impl.FFLineBuilderAbstr;
import org.uniprot.core.flatfile.writer.impl.FFLines;
import org.uniprot.core.uniprotkb.evidence.Evidence;
import org.uniprot.core.uniprotkb.evidence.EvidenceLine;
import org.uniprot.cv.evidence.EvidenceHelper;

public class SSEvidenceLineBuilder extends FFLineBuilderAbstr<List<EvidenceLine>> {
    private static final String REFERENCE = "Reference";
    private static final String prefix = "**EV ";
    private static final String DEFAULT_ORIG_NAME = "-";

    public SSEvidenceLineBuilder() {
        super(LineType.STAR_STAR);
    }

    @Override
    public String buildString(List<EvidenceLine> f) {
        return buildLine(f, false).toString();
    }

    @Override
    public String buildStringWithEvidence(List<EvidenceLine> f) {
        return buildLine(f, true).toString();
    }

    @Override
    protected FFLine buildLine(List<EvidenceLine> evidences, boolean showEvidence) {
        List<String> lines = new ArrayList<>();
        if ((evidences == null) || (evidences.isEmpty())) return FFLines.create(lines);
        for (EvidenceLine line : evidences) {
            StringBuilder sb = new StringBuilder();
            Evidence evidence = EvidenceHelper.parseEvidenceLine(line.getEvidence());
            sb.append(prefix);

            sb.append(evidence.getEvidenceCode().getCode());
            sb.append(SEPARATOR_SEMICOLON);
            if (evidence.getEvidenceCrossReference() != null) {
                if (!evidence.getEvidenceCrossReference()
                        .getDatabase()
                        .getName()
                        .equals(REFERENCE)) {
                    sb.append(evidence.getEvidenceCrossReference().getDatabase().getName())
                            .append(":");
                }
                sb.append(evidence.getEvidenceCrossReference().getId());
            } else {
                sb.append("-");
            }
            sb.append(SEPARATOR_SEMICOLON);
            if (line.getCurator() == null) {
                sb.append(DEFAULT_ORIG_NAME);
            } else sb.append(line.getCurator());

            sb.append(SEPARATOR_SEMICOLON);
            sb.append(dateFormatter.format(line.getCreateDate()).toUpperCase());
            sb.append(STOP);
            lines.add(sb.toString());
        }
        return FFLines.create(lines);
    }
}

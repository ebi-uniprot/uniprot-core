package org.uniprot.core.flatfile.parser.impl.oc;

import static org.uniprot.core.flatfile.writer.impl.FFLineConstant.SEPARATOR_SEMICOLON;
import static org.uniprot.core.flatfile.writer.impl.FFLineConstant.STOP;

import org.uniprot.core.flatfile.writer.FFLine;
import org.uniprot.core.flatfile.writer.FFLineBuilder;
import org.uniprot.core.flatfile.writer.LineType;
import org.uniprot.core.flatfile.writer.impl.FFLineBuilderAbstr;
import org.uniprot.core.flatfile.writer.impl.FFLineWrapper;
import org.uniprot.core.flatfile.writer.impl.FFLines;

import java.util.List;

public class OCLineBuilder extends FFLineBuilderAbstr<List<String>>
        implements FFLineBuilder<List<String>> {
    private static final String UNCLASSIFIED = "unclassified";

    public OCLineBuilder() {
        super(LineType.OC);
    }

    @Override
    protected FFLine buildLine(List<String> f, boolean showEvidence) {
        StringBuilder sb = build(f, showEvidence, true);
        List<String> lls = FFLineWrapper.buildLines(sb, SEPARATOR_SEMICOLON, linePrefix);
        return FFLines.create(lls);
    }

    @Override
    public String buildString(List<String> f) {
        return build(f, false, false).toString();
    }

    @Override
    public String buildStringWithEvidence(List<String> f) {
        return build(f, true, false).toString();
    }

    private StringBuilder build(List<String> f, boolean showEvidence, boolean includeFFMarkup) {
        StringBuilder sb = new StringBuilder();
        if (includeFFMarkup) {
            sb.append(linePrefix);
        }
        if ((f.size() != 0)) {
            boolean isFirst = true;
            for (String taxon : f) {
                if (!isFirst) {
                    sb.append(SEPARATOR_SEMICOLON);
                }
                sb.append(taxon);
                isFirst = false;
            }
        } else {
            sb.append(UNCLASSIFIED);
        }
        sb.append(STOP);
        return sb;
    }
}

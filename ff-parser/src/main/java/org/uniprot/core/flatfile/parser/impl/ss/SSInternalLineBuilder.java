package org.uniprot.core.flatfile.parser.impl.ss;

import java.util.ArrayList;
import java.util.List;

import org.uniprot.core.flatfile.writer.FFLine;
import org.uniprot.core.flatfile.writer.LineType;
import org.uniprot.core.flatfile.writer.impl.FFLineBuilderAbstr;
import org.uniprot.core.flatfile.writer.impl.FFLines;
import org.uniprot.core.uniprot.InternalLine;
import org.uniprot.core.uniprot.InternalLineType;

public class SSInternalLineBuilder extends FFLineBuilderAbstr<List<InternalLine>> {

    public SSInternalLineBuilder() {
        super(LineType.STAR_STAR);
    }

    @Override
    public String buildString(List<InternalLine> f) {
        return buildLine(f, false).toString();
    }

    @Override
    public String buildStringWithEvidence(List<InternalLine> f) {
        return buildLine(f, true).toString();
    }

    @Override
    protected FFLine buildLine(List<InternalLine> internalLines, boolean showEvidence) {
        List<String> lines = new ArrayList<>();
        for (InternalLine line : internalLines) {

            if (InternalLineType.PROSITE != line.getType()) {
                StringBuilder starStar = new StringBuilder();
                starStar.append("**");
                starStar.append(line.getType());
                starStar.append(" ");
                starStar.append(line.getValue());
                lines.add(starStar.toString());
            }
        }
        return FFLines.create(lines);
    }
}

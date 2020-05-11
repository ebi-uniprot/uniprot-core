package org.uniprot.core.flatfile.parser.impl.kw;

import static org.uniprot.core.flatfile.writer.impl.FFLineConstant.*;

import org.uniprot.core.flatfile.writer.FFLine;
import org.uniprot.core.flatfile.writer.FFLineBuilder;
import org.uniprot.core.flatfile.writer.LineType;
import org.uniprot.core.flatfile.writer.impl.FFLineBuilderAbstr;
import org.uniprot.core.flatfile.writer.impl.FFLineWrapper;
import org.uniprot.core.flatfile.writer.impl.FFLines;
import org.uniprot.core.uniprotkb.Keyword;

import java.util.ArrayList;
import java.util.List;

public class KWLineBuilder extends FFLineBuilderAbstr<List<Keyword>>
        implements FFLineBuilder<List<Keyword>> {

    public KWLineBuilder() {
        super(LineType.KW);
    }

    @Override
    public String buildString(List<Keyword> f) {
        return FFLines.create(buildLines(f, false, false)).toString();
    }

    @Override
    public String buildStringWithEvidence(List<Keyword> f) {
        return FFLines.create(buildLines(f, false, true)).toString();
    }

    @Override
    protected FFLine buildLine(List<Keyword> f, boolean showEvidence) {
        return FFLines.create(buildLines(f, true, showEvidence));
    }

    private List<String> buildLines(
            List<Keyword> f, boolean includeFFMarkup, boolean showEvidence) {
        List<String> lines = new ArrayList<>();
        if (f.isEmpty()) return lines;
        int counter = 0;
        StringBuilder sb = new StringBuilder();
        if (includeFFMarkup) sb.append(linePrefix);
        boolean isFirst = true;
        String separator = SPACE;
        for (Keyword keyword : f) {
            StringBuilder item = new StringBuilder();
            counter++;
            item.append(keyword.getName());
            addEvidences(item, keyword, showEvidence);

            if (f.size() == counter) {
                item.append(STOP);
            } else {
                item.append(SEMICOLON);
            }
            if (isFirst) {
                sb.append(item);
                isFirst = false;
            } else if (sb.length() + item.length() >= (LINE_LENGTH)) {
                sb.append(separator);
                lines.add(sb.toString().trim());
                sb = new StringBuilder();
                if (includeFFMarkup) sb.append(linePrefix);
                sb.append(item);

            } else {
                sb.append(separator);
                sb.append(item);
            }

            if (sb.length() > LINE_LENGTH && includeFFMarkup) {
                List<String> tempLines =
                        FFLineWrapper.buildLines(sb.toString(), SEPS, linePrefix, LINE_LENGTH);
                for (int i = 0; i < tempLines.size(); i++) {
                    if (i < (tempLines.size() - 1)) {
                        lines.add(tempLines.get(i));
                    } else {
                        sb = new StringBuilder();
                        sb.append(tempLines.get(i));
                    }
                }
            }
        }
        if (sb.length() > 0) lines.add(sb.toString());
        return lines;
    }
}

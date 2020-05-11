package org.uniprot.core.flatfile.parser.impl.ss;

import java.util.ArrayList;
import java.util.List;

import org.uniprot.core.flatfile.writer.FFLine;
import org.uniprot.core.flatfile.writer.LineType;
import org.uniprot.core.flatfile.writer.impl.FFLineBuilderAbstr;
import org.uniprot.core.flatfile.writer.impl.FFLines;
import org.uniprot.core.uniprotkb.SourceLine;

public class SSSourceLineBuilder extends FFLineBuilderAbstr<List<SourceLine>> {
    private static final String SOURCE_SECTION =
            "**   #################     SOURCE SECTION     ##################";
    private static final String prefix = "**   ";

    public SSSourceLineBuilder() {
        super(LineType.STAR_STAR);
    }

    @Override
    public String buildString(List<SourceLine> f) {
        return buildLine(f, false).toString();
    }

    @Override
    public String buildStringWithEvidence(List<SourceLine> f) {
        return buildLine(f, true).toString();
    }

    @Override
    protected FFLine buildLine(List<SourceLine> sourceLines, boolean showEvidence) {
        List<String> lines = new ArrayList<>();
        if (!(sourceLines.isEmpty())) {
            lines.add(SOURCE_SECTION);
            for (SourceLine line : sourceLines) {
                lines.add(prefix + line.getValue());
            }
        }
        return FFLines.create(lines);
    }
}

package org.uniprot.core.flatfile.parser.impl.ss;

import java.util.ArrayList;
import java.util.List;

import org.uniprot.core.flatfile.writer.FFLine;
import org.uniprot.core.flatfile.writer.LineType;
import org.uniprot.core.flatfile.writer.impl.FFLineBuilderAbstr;
import org.uniprot.core.flatfile.writer.impl.FFLines;
import org.uniprot.core.uniprot.InternalSection;

public class SSLineBuilder extends FFLineBuilderAbstr<InternalSection> {
    private static final String INTERNAL_SECTION =
            "**   #################    INTERNAL SECTION    ##################";
    private static final String START_LINE = "**";
    private SSSourceLineBuilder sourceLineBuilder = new SSSourceLineBuilder();
    private SSEvidenceLineBuilder evidenceLineBuilder = new SSEvidenceLineBuilder();
    private SSInternalLineBuilder internalLineBuilder = new SSInternalLineBuilder();

    public SSLineBuilder() {
        super(LineType.STAR_STAR);
    }

    @Override
    public String buildString(InternalSection f) {
        if (f == null) {
            return "";
        }
        return buildLine(f, false).toString();
    }

    @Override
    public String buildStringWithEvidence(InternalSection f) {
        if (f == null) return "";
        return buildLine(f, true).toString();
    }

    @Override
    protected FFLine buildLine(InternalSection f, boolean showEvidence) {
        List<String> lines = new ArrayList<>();
        if (f == null) {
            return FFLines.create(lines);
        }
        FFLine sourceLines = sourceLineBuilder.buildWithEvidence(f.getSourceLines());
        FFLine evidenceLines = evidenceLineBuilder.buildWithEvidence(f.getEvidenceLines());
        FFLine internalLines = internalLineBuilder.buildWithEvidence(f.getInternalLines());
        evidenceLines.add(internalLines);

        //		if(sourceLines.isEmpty() && evidenceLines.isEmpty()){
        //			return FFLines.create(lines);
        //		}
        lines.add(START_LINE);
        lines.addAll(sourceLines.lines());
        lines.add(INTERNAL_SECTION);
        if (!evidenceLines.isEmpty()) {

            lines.addAll(evidenceLines.lines());
        }
        return FFLines.create(lines);
    }
}

package org.uniprot.core.flatfile.parser.impl.pe;

import static org.uniprot.core.flatfile.writer.impl.FFLineConstant.SEMICOLON;

import org.uniprot.core.flatfile.writer.FFLine;
import org.uniprot.core.flatfile.writer.LineType;
import org.uniprot.core.flatfile.writer.impl.FFLineBuilderAbstr;
import org.uniprot.core.flatfile.writer.impl.FFLines;
import org.uniprot.core.uniprotkb.ProteinExistence;

import java.util.ArrayList;
import java.util.List;

public class PELineBuilder extends FFLineBuilderAbstr<ProteinExistence> {
    public PELineBuilder() {
        super(LineType.PE);
    }

    @Override
    public String buildString(ProteinExistence f) {
        return buildLine(f, false, false).toString();
    }

    @Override
    public String buildStringWithEvidence(ProteinExistence f) {
        return buildLine(f, false, true).toString();
    }

    @Override
    protected FFLine buildLine(ProteinExistence f, boolean showEvidence) {
        return buildLine(f, true, showEvidence);
    }

    private FFLine buildLine(ProteinExistence f, boolean includeFFMark, boolean showEvidence) {
        List<String> lines = new ArrayList<>();
        StringBuilder pe = new StringBuilder();
        if (includeFFMark) pe.append(linePrefix);
        pe.append(f.getDisplayName());
        pe.append(SEMICOLON);
        lines.add(pe.toString());
        return FFLines.create(lines);
    }
}

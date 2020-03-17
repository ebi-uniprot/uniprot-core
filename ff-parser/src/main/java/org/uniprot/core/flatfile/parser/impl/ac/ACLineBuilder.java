package org.uniprot.core.flatfile.parser.impl.ac;

import static org.uniprot.core.flatfile.writer.impl.FFLineConstant.SEMICOLON;
import static org.uniprot.core.flatfile.writer.impl.FFLineConstant.SEPARATOR_SEMICOLON;

import java.util.List;

import org.uniprot.core.flatfile.writer.FFLine;
import org.uniprot.core.flatfile.writer.FFLineBuilder;
import org.uniprot.core.flatfile.writer.LineType;
import org.uniprot.core.flatfile.writer.impl.FFLineBuilderAbstr;
import org.uniprot.core.flatfile.writer.impl.FFLineWrapper;
import org.uniprot.core.flatfile.writer.impl.FFLines;
import org.uniprot.core.uniprotkb.UniProtKBAccession;

public class ACLineBuilder extends FFLineBuilderAbstr<List<UniProtKBAccession>>
        implements FFLineBuilder<List<UniProtKBAccession>> {
    public ACLineBuilder() {
        super(LineType.AC);
    }

    @Override
    public String buildString(List<UniProtKBAccession> f) {
        return build(f, false).toString();
    }

    @Override
    public String buildStringWithEvidence(List<UniProtKBAccession> f) {
        return build(f, true).toString();
    }

    @Override
    protected FFLine buildLine(List<UniProtKBAccession> f, boolean showEvidence) {
        StringBuilder sb = build(f, true);
        List<String> lls = FFLineWrapper.buildLines(sb, SEPARATOR_SEMICOLON, linePrefix);
        return FFLines.create(lls);
    }

    private StringBuilder build(List<UniProtKBAccession> f, boolean includeFFMarkup) {

        StringBuilder sb = new StringBuilder();
        if (includeFFMarkup) {
            sb.append(linePrefix);
        }
        boolean isFirst = true;
        for (UniProtKBAccession ac : f) {
            if (!isFirst) {
                sb.append(SEPARATOR_SEMICOLON);
            }
            sb.append(ac.getValue());
            isFirst = false;
        }
        sb.append(SEMICOLON);
        return sb;
    }
}

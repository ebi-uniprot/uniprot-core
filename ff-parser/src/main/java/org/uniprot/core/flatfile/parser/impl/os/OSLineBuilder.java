package org.uniprot.core.flatfile.parser.impl.os;

import static org.uniprot.core.flatfile.writer.impl.FFLineConstant.SEPARATOR;

import java.util.List;
import java.util.stream.Collectors;

import org.uniprot.core.flatfile.writer.FFLine;
import org.uniprot.core.flatfile.writer.FFLineBuilder;
import org.uniprot.core.flatfile.writer.LineType;
import org.uniprot.core.flatfile.writer.impl.FFLineBuilderAbstr;
import org.uniprot.core.flatfile.writer.impl.FFLineWrapper;
import org.uniprot.core.flatfile.writer.impl.FFLines;
import org.uniprot.core.uniprotkb.taxonomy.Organism;

public class OSLineBuilder extends FFLineBuilderAbstr<Organism>
        implements FFLineBuilder<Organism> {;

    public OSLineBuilder() {
        super(LineType.OS);
    }

    @Override
    protected FFLine buildLine(Organism f, boolean showEvidence) {
        StringBuilder sb = build(f, showEvidence, true);
        List<String> lls = FFLineWrapper.buildLines(sb, SEPARATOR, linePrefix);
        return FFLines.create(lls);
    }

    @Override
    public String buildString(Organism f) {
        return build(f, false, false).toString();
    }

    @Override
    public String buildStringWithEvidence(Organism f) {
        return build(f, true, false).toString();
    }

    private StringBuilder build(Organism organism, boolean showEvidence, boolean includeFFMarkup) {
        StringBuilder sb = new StringBuilder();
        if (includeFFMarkup) sb.append(linePrefix);
        if (organism.getScientificName() != null) {
            sb.append(organism.getScientificName());
        }
        if (organism.getCommonName() != null && !organism.getCommonName().equals("")) {
            sb.append(" (");
            sb.append(organism.getCommonName());
            sb.append(")");
        }
        if (!organism.getSynonyms().isEmpty()) {
            String val = organism.getSynonyms().stream().collect(Collectors.joining(", "));
            sb.append(" (");
            sb.append(val);
            sb.append(")");
        }

        appendIfNot(sb, '.');

        return sb;
    }
}

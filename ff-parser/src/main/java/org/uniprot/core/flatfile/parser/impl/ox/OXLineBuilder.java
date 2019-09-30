package org.uniprot.core.flatfile.parser.impl.ox;

import java.util.ArrayList;
import java.util.List;

import org.uniprot.core.flatfile.writer.FFLine;
import org.uniprot.core.flatfile.writer.FFLineBuilder;
import org.uniprot.core.flatfile.writer.LineType;
import org.uniprot.core.flatfile.writer.impl.FFLineBuilderAbstr;
import org.uniprot.core.flatfile.writer.impl.FFLines;
import org.uniprot.core.uniprot.taxonomy.Organism;

public class OXLineBuilder extends FFLineBuilderAbstr<Organism> implements FFLineBuilder<Organism> {
    private static final String NAME = "NCBI_TaxID=";
    private static final String STOP = ";";

    public OXLineBuilder() {
        super(LineType.OX);
    }

    @Override
    protected FFLine buildLine(Organism f, boolean showEvidence) {
        StringBuilder sb = build(f, showEvidence, true);
        List<String> lls = new ArrayList<>();
        lls.add(sb.toString());
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

    private StringBuilder build(Organism f, boolean showEvidence, boolean includeFFMarkup) {
        StringBuilder sb = new StringBuilder();
        if (includeFFMarkup) sb.append(linePrefix);
        sb.append(NAME);

        sb.append(f.getTaxonId());
        addEvidences(sb, f, showEvidence);

        sb.append(STOP);
        return sb;
    }
}

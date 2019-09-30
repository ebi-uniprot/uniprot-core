package org.uniprot.core.flatfile.parser.impl.ft;

import java.util.ArrayList;
import java.util.List;

import org.uniprot.core.flatfile.writer.FFLine;
import org.uniprot.core.flatfile.writer.FFLineBuilder;
import org.uniprot.core.flatfile.writer.LineType;
import org.uniprot.core.flatfile.writer.impl.FFLineBuilderAbstr;
import org.uniprot.core.flatfile.writer.impl.FFLines;
import org.uniprot.core.uniprot.feature.Feature;

public class FTLineBuilder extends FFLineBuilderAbstr<List<Feature>>
        implements FFLineBuilder<List<Feature>> {
    public FTLineBuilder() {
        super(LineType.FT);
    }

    @Override
    public String buildString(List<Feature> features) {
        List<String> lines = buildLines(features, false, false);
        return FFLines.create(lines).toString();
    }

    @Override
    public String buildStringWithEvidence(List<Feature> features) {
        List<String> lines = buildLines(features, false, true);
        return FFLines.create(lines).toString();
    }

    @Override
    protected FFLine buildLine(List<Feature> f, boolean showEvidence) {
        List<String> lines = buildLines(f, true, showEvidence);
        return FFLines.create(lines);
    }

    private List<String> buildLines(
            List<Feature> features, boolean includeFFMarkings, boolean showEvidence) {
        List<String> lines = new ArrayList<>();
        for (Feature feature : features) {
            FFLineBuilder<Feature> fbuilder = FeatureLineBuilderFactory.create(feature);
            if (includeFFMarkings) {
                if (showEvidence) lines.addAll(fbuilder.buildWithEvidence(feature).lines());
                else lines.addAll(fbuilder.build(feature).lines());
            } else {
                if (showEvidence) lines.add(fbuilder.buildStringWithEvidence(feature));
                else lines.add(fbuilder.buildString(feature));
            }
        }
        return lines;
    }
}

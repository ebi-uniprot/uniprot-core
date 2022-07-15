package org.uniprot.core.flatfile.parser.impl.ft;

import static org.uniprot.core.flatfile.writer.impl.FFLineConstant.DASH;
import static org.uniprot.core.flatfile.writer.impl.FFLineConstant.LINE_LENGTH;
import static org.uniprot.core.flatfile.writer.impl.FFLineConstant.SEPARATOR;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.uniprot.core.flatfile.writer.FFLine;
import org.uniprot.core.flatfile.writer.FFLineBuilder;
import org.uniprot.core.flatfile.writer.LineType;
import org.uniprot.core.flatfile.writer.impl.FFLineBuilderAbstr;
import org.uniprot.core.flatfile.writer.impl.FFLineWrapper;
import org.uniprot.core.flatfile.writer.impl.FFLines;
import org.uniprot.core.uniprotkb.feature.UniProtKBFeature;

public abstract class AbstractFeatureLineBuilder extends FFLineBuilderAbstr<UniProtKBFeature>
        implements FFLineBuilder<UniProtKBFeature> {
    public AbstractFeatureLineBuilder() {
        super(LineType.FT);
    }

    @Override
    public String buildString(UniProtKBFeature f) {
        List<String> lines = buildLines(f, false, false);
        return FFLines.create(lines).toString();
    }

    @Override
    public String buildStringWithEvidence(UniProtKBFeature f) {
        List<String> lines = buildLines(f, false, true);
        return FFLines.create(lines).toString();
    }

    @Override
    protected FFLine buildLine(UniProtKBFeature f, boolean showEvidence) {
        List<String> lines = buildLines(f, true, showEvidence);
        return FFLines.create(lines);
    }

    protected List<String> buildLines(
            UniProtKBFeature f, boolean includeFFMarkings, boolean addEvidence) {
        List<String> lines = new ArrayList<>();
        lines.addAll(buildFtHeaderLines(f, includeFFMarkings));
        lines.addAll(buildLigands(f, includeFFMarkings));
        lines.addAll(buildFtNoteLines(f, includeFFMarkings));
        if (addEvidence) {
            lines.addAll(buildFtEvidenceLines(f, includeFFMarkings));
        }
        lines.addAll(buildFtIdLines(f, includeFFMarkings));
        return lines;
    }

    protected List<String> buildFtHeaderLines(UniProtKBFeature f, boolean includeFFMarkings) {
        StringBuilder sb = FTLineBuilderHelper.buildFeatureHeader(f, includeFFMarkings);
        List<String> lists = new ArrayList<>();
        lists.add(sb.toString());
        return lists;
    }

    protected List<String> buildFtNoteLines(UniProtKBFeature f, boolean includeFFMarkings) {
        return FTLineBuilderHelper.buildNote(f, getDescription(f), includeFFMarkings);
    }

    protected List<String> buildFtEvidenceLines(UniProtKBFeature f, boolean includeFFMarkings) {
        StringBuilder sb = FTLineBuilderHelper.buildEvidences(f.getEvidences(), includeFFMarkings);
        if (sb.length() == 0) {
            return Collections.emptyList();
        }
        String[] seps = {SEPARATOR, DASH};
        if (includeFFMarkings) {
            return FFLineWrapper.buildLines(
                    sb.toString(), seps, FTLineBuilderHelper.FT_LINE_PREFIX_2, LINE_LENGTH);
        } else {
            List<String> lists = new ArrayList<>();
            lists.add(sb.toString());
            return lists;
        }
    }

    protected List<String> buildFtIdLines(UniProtKBFeature f, boolean includeFFMarkings) {
        if (f.hasFeatureId()) {
            StringBuilder featureId =
                    FTLineBuilderHelper.buildFeatureId(f.getFeatureId(), includeFFMarkings);
            if (featureId.length() > 0) {
                List<String> lists = new ArrayList<>();
                lists.add(featureId.toString());
                return lists;
            } else return Collections.emptyList();
        } else {
            return Collections.emptyList();
        }
    }

    protected List<String> buildLigands(UniProtKBFeature f, boolean includeFFMarkings) {
        return List.of();
    }

    protected StringBuilder getDescription(UniProtKBFeature f) {
        return FTLineBuilderHelper.getDescriptionString(f);
    }
}

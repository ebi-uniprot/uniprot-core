package org.uniprot.core.flatfile.writer.impl;

import static org.uniprot.core.flatfile.writer.impl.FFLineConstant.DEFAUT_LINESPACE;

import java.util.ArrayList;
import java.util.List;

import org.uniprot.core.flatfile.parser.impl.ra.RALineBuilder;
import org.uniprot.core.flatfile.parser.impl.rc.RCLineBuilder;
import org.uniprot.core.flatfile.parser.impl.rg.RGLineBuilder;
import org.uniprot.core.flatfile.parser.impl.rl.RLLineBuilder;
import org.uniprot.core.flatfile.parser.impl.rp.RPLineBuilder;
import org.uniprot.core.flatfile.parser.impl.rt.RTLineBuilder;
import org.uniprot.core.flatfile.parser.impl.rx.RXLineBuilder;
import org.uniprot.core.flatfile.writer.FFLine;
import org.uniprot.core.flatfile.writer.FFLineBuilder;
import org.uniprot.core.flatfile.writer.LineType;
import org.uniprot.core.uniprot.UniProtReference;
import org.uniprot.core.uniprot.evidence.HasEvidences;

public class RLineBuilder extends FFLineBuilderAbstr<UniProtReference>
        implements FFLineBuilder<UniProtReference> {
    private final static RPLineBuilder rpLineBuilder = new RPLineBuilder();
    private final static RCLineBuilder rcLineBuilder = new RCLineBuilder();
    private final static RXLineBuilder rxLineBuilder = new RXLineBuilder();
    private final static RGLineBuilder rgLineBuilder = new RGLineBuilder();
    private final static RALineBuilder raLineBuilder = new RALineBuilder();
    private final static RTLineBuilder rtLineBuilder = new RTLineBuilder();
    private final static RLLineBuilder rlLineBuilder = new RLLineBuilder();
    private int rn;

    public RLineBuilder() {
        super(LineType.RN);
    }

    public void setRN(int rn) {
        this.rn = rn;
    }

    @Override
    public String buildString(UniProtReference f) {
        return buildLine(f, false, false).toString();
    }

    @Override
    public String buildStringWithEvidence(UniProtReference f) {
        return buildLine(f, false, true).toString();
    }

    @Override
    protected FFLine buildLine(UniProtReference f, boolean showEvidence) {
        return buildLine(f, true, showEvidence);
    }

    private FFLine buildLine(UniProtReference f, boolean includeFFMarkup, boolean showEvidence) {
        List<String> lines = new ArrayList<>();
        // RN LINE

        lines.addAll(buildRNLine(rn, f, includeFFMarkup, showEvidence));

        // RP Line rLine.append(RPLineNew.export(citation));
        lines.addAll(rpLineBuilder.buildLine(f.getReferencePositions(), includeFFMarkup, showEvidence));

        // RC Line rLine.append(RCLineNew.exportWithEvidenceTag(citation));
        lines.addAll(rcLineBuilder.buildLine(f.getReferenceComments(), includeFFMarkup, showEvidence));
        // RX line rLine.append(RXLineNew.export(citation));
        lines.addAll(rxLineBuilder.buildLine(f.getCitation(), includeFFMarkup, showEvidence));
        // RG line rLine.append(RGLineNew.export(citation));
        lines.addAll(rgLineBuilder.buildLine(f.getCitation().getAuthoringGroup(), includeFFMarkup, showEvidence));
        // RA line rLine.append(RALineNew.export(citation));
        lines.addAll(raLineBuilder.buildLine(f.getCitation().getAuthors(), includeFFMarkup, showEvidence));
        // RT line rLine.append(RTLineNew.export(citation));
        lines.addAll(rtLineBuilder.buildLine(f.getCitation().getTitle(), includeFFMarkup, showEvidence));
        // RL line rLine.append(RLLineNew.export(citation));
        lines.addAll(rlLineBuilder.buildLine(f.getCitation(), includeFFMarkup, showEvidence));

        return FFLines.create(lines);
    }

    private List<String> buildRNLine(int rn, HasEvidences he, boolean includeFFMarkup, boolean showEvidence) {

        StringBuilder sb = new StringBuilder();
        String rnLinePrefix = LineType.RN + DEFAUT_LINESPACE;
        if (includeFFMarkup) {
            sb.append(rnLinePrefix);
        }
        String rnStr = "[" + rn + "]";
        sb.append(rnStr);
        addEvidences(sb, he, showEvidence);
        List<String> lines = new ArrayList<>();
        lines.add(sb.toString());
        return lines;

    }
}

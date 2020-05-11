package org.uniprot.core.flatfile.parser.impl.og;

import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.uniprot.core.flatfile.antlr.OgLineParser;
import org.uniprot.core.flatfile.antlr.OgLineParserBaseListener;
import org.uniprot.core.flatfile.parser.ParseTreeObjectExtractor;
import org.uniprot.core.flatfile.parser.impl.EvidenceInfo;

import java.util.List;

/**
 * Created with IntelliJ IDEA. User: wudong Date: 08/08/13 Time: 12:26 To change this template use
 * File | Settings | File Templates.
 */
public class OgLineModelListener extends OgLineParserBaseListener
        implements ParseTreeObjectExtractor<OgLineObject> {

    private OgLineObject object;

    @Override
    public void enterOg_og(@NotNull OgLineParser.Og_ogContext ctx) {
        this.object = new OgLineObject();
    }

    @Override
    public void exitPlasmid_name(@NotNull OgLineParser.Plasmid_nameContext ctx) {
        Object targetObj;
        if (ctx.PLASMID_VALUE() != null) {
            String text = ctx.PLASMID_VALUE().getText();
            object.plasmidNames.add(text);
            targetObj = text;
        } else {
            object.ogs.add(OgLineObject.OgEnum.PLASMID);
            targetObj = OgLineObject.OgEnum.PLASMID;
        }

        OgLineParser.EvidenceContext evidence = ctx.evidence();
        if (evidence != null) {
            List<TerminalNode> terminalNodes = evidence.EV_TAG();
            EvidenceInfo.processEvidence(object.getEvidenceInfo(), targetObj, terminalNodes);
        }
    }

    @Override
    public void exitHydrogenosome_line(@NotNull OgLineParser.Hydrogenosome_lineContext ctx) {
        object.ogs.add(OgLineObject.OgEnum.HYDROGENOSOME);
        OgLineParser.EvidenceContext evidence = ctx.evidence();
        if (evidence != null) {
            List<TerminalNode> terminalNodes = evidence.EV_TAG();
            EvidenceInfo.processEvidence(
                    object.getEvidenceInfo(), OgLineObject.OgEnum.HYDROGENOSOME, terminalNodes);
        }
    }

    @Override
    public void exitNucleomorph_line(@NotNull OgLineParser.Nucleomorph_lineContext ctx) {
        object.ogs.add(OgLineObject.OgEnum.NUCLEOMORPH);
        OgLineParser.EvidenceContext evidence = ctx.evidence();
        if (evidence != null) {
            List<TerminalNode> terminalNodes = evidence.EV_TAG();
            EvidenceInfo.processEvidence(
                    object.getEvidenceInfo(), OgLineObject.OgEnum.NUCLEOMORPH, terminalNodes);
        }
    }

    @Override
    public void exitMitochondrion_line(@NotNull OgLineParser.Mitochondrion_lineContext ctx) {
        object.ogs.add(OgLineObject.OgEnum.MITOCHONDRION);
        OgLineParser.EvidenceContext evidence = ctx.evidence();
        if (evidence != null) {
            List<TerminalNode> terminalNodes = evidence.EV_TAG();
            EvidenceInfo.processEvidence(
                    object.getEvidenceInfo(), OgLineObject.OgEnum.MITOCHONDRION, terminalNodes);
        }
    }

    @Override
    public void exitPlastid_line(@NotNull OgLineParser.Plastid_lineContext ctx) {
        OgLineParser.Plastid_nameContext plastid_nameContext = ctx.plastid_name();
        OgLineObject.OgEnum og = null;
        if (plastid_nameContext == null) {
            og = OgLineObject.OgEnum.PLASTID;
        } else {
            if (plastid_nameContext.APICOPLAST() != null) {
                og = OgLineObject.OgEnum.PLASTID_APICOPLAST;
            } else if (plastid_nameContext.CYANELLE() != null) {
                og = OgLineObject.OgEnum.PLASTID_CYANELLE;
            } else if (plastid_nameContext.ORGANELLAR_CHROMATOPHORE() != null) {
                og = OgLineObject.OgEnum.PLASTID_ORGANELLAR_CHROMATOPHORE;
            } else if (plastid_nameContext.NON_PHOTOSYNTHETIC_PLASTID() != null) {
                og = OgLineObject.OgEnum.PLASTID_NON_PHOTOSYNTHETIC;
            } else if (plastid_nameContext.CHLOROPLAST() != null) {
                og = OgLineObject.OgEnum.PLASTID_CHLOROPLAST;
            }
        }
        object.ogs.add(og);

        OgLineParser.EvidenceContext evidence = ctx.evidence();
        if (evidence != null) {
            List<TerminalNode> terminalNodes = evidence.EV_TAG();
            EvidenceInfo.processEvidence(object.getEvidenceInfo(), og, terminalNodes);
        }
    }

    public OgLineObject getObject() {
        return object;
    }
}

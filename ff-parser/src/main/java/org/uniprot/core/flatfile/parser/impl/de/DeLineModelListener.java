package org.uniprot.core.flatfile.parser.impl.de;

import java.util.ArrayList;
import java.util.List;

import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.uniprot.core.flatfile.antlr.DeLineParser;
import org.uniprot.core.flatfile.antlr.DeLineParserBaseListener;
import org.uniprot.core.flatfile.parser.ParseTreeObjectExtractor;
import org.uniprot.core.flatfile.parser.impl.EvidenceInfo;

/**
 * Created with IntelliJ IDEA. User: wudong Date: 08/08/13 Time: 12:26 To change this template use
 * File | Settings | File Templates.
 */
public class DeLineModelListener extends DeLineParserBaseListener
        implements ParseTreeObjectExtractor<DeLineObject> {

    private DeLineObject object;

    // temp object for the Include/Contain blocks.
    private DeLineObject.NameBlock block;

    @Override
    public DeLineObject getObject() {
        return object;
    }

    @Override
    public void enterDe_de(@NotNull DeLineParser.De_deContext ctx) {
        object = new DeLineObject();
    }

    @Override
    public void exitRec_name(@NotNull DeLineParser.Rec_nameContext ctx) {
        DeLineObject.Name recName = createDeLineObjectName(ctx.full_name(), ctx.short_name());
        this.object.setRecName(recName);
        processECs(ctx.ec(), recName);
    }

    @Override
    public void exitAlt_biotech(@NotNull DeLineParser.Alt_biotechContext ctx) {
        String name = ctx.name_value().getText();
        this.object.setAltBiotech(this.object.getEvidenceInfo().retrieveEvidenceString(name));
    }

    @Override
    public void exitAlt_inn(@NotNull DeLineParser.Alt_innContext ctx) {
        String name = ctx.name_value().getText();
        object.getAltInns().add(this.object.getEvidenceInfo().retrieveEvidenceString(name));
    }

    @Override
    public void exitAlt_allergen(@NotNull DeLineParser.Alt_allergenContext ctx) {
        String name = ctx.name_value().getText();
        this.object.setAltAllergen(this.object.getEvidenceInfo().retrieveEvidenceString(name));
    }

    @Override
    public void exitAlt_cdantigen(@NotNull DeLineParser.Alt_cdantigenContext ctx) {
        String text = ctx.name_value().getText();
        this.object
                .getAltCdAntigens()
                .add(this.object.getEvidenceInfo().retrieveEvidenceString(text));
    }

    @Override
    public void exitSub_alt_biotech(@NotNull DeLineParser.Sub_alt_biotechContext ctx) {
        String name = ctx.name_value().getText();
        block.setAltBiotech(object.getEvidenceInfo().retrieveEvidenceString(name));
    }

    @Override
    public void exitSub_alt_inn(@NotNull DeLineParser.Sub_alt_innContext ctx) {
        String text = ctx.name_value().getText();
        block.getAltInns().add(object.getEvidenceInfo().retrieveEvidenceString(text));
    }

    @Override
    public void exitSub_alt_allergen(@NotNull DeLineParser.Sub_alt_allergenContext ctx) {
        String name = ctx.name_value().getText();
        block.setAltAllergen(object.getEvidenceInfo().retrieveEvidenceString(name));
    }

    @Override
    public void exitSub_alt_cdantigen(@NotNull DeLineParser.Sub_alt_cdantigenContext ctx) {
        String text = ctx.name_value().getText();
        block.getAltCdAntigens().add(object.getEvidenceInfo().retrieveEvidenceString(text));
    }

    @Override
    public void exitSub_rec_name(@NotNull DeLineParser.Sub_rec_nameContext ctx) {
        DeLineObject.Name recName = createDeLineObjectName(ctx.full_name(), ctx.short_name());
        this.block.setRecName(recName);
        processECs(ctx.ec(), recName);
    }

    @Override
    public void exitSub_alt_name(@NotNull DeLineParser.Sub_alt_nameContext ctx) {
        DeLineObject.Name altName = new DeLineObject.Name();
        DeLineParser.Sub_alt_name_1Context altName1Context = ctx.sub_alt_name_1();
        DeLineParser.Sub_alt_name_2Context altName2Context = ctx.sub_alt_name_2();
        DeLineParser.Sub_alt_name_3Context altName3Context = ctx.sub_alt_name_3();

        if (altName1Context != null) {
            altName =
                    createDeLineObjectName(
                            altName1Context.full_name(), altName1Context.short_name());
            processECs(altName1Context.ec(), altName);
        } else if (altName2Context != null) {
            altName = createDeLineObjectName(null, altName2Context.short_name());
            processECs(altName2Context.ec(), altName);
        } else if (altName3Context != null) {
            processECs(altName3Context.ec(), altName);
        }

        this.block.getAltNames().add(altName);
    }

    @Override
    public void exitAlt_name(@NotNull DeLineParser.Alt_nameContext ctx) {
        DeLineParser.Alt_name_1Context altName1Context = ctx.alt_name_1();
        DeLineParser.Alt_name_2Context altName2Context = ctx.alt_name_2();
        DeLineParser.Alt_name_3Context altName3Context = ctx.alt_name_3();
        DeLineObject.Name altName = new DeLineObject.Name();

        if (altName1Context != null) {
            altName =
                    createDeLineObjectName(
                            altName1Context.full_name(), altName1Context.short_name());
            processECs(altName1Context.ec(), altName);
        } else if (altName2Context != null) {
            altName = createDeLineObjectName(null, altName2Context.short_name());
            processECs(altName2Context.ec(), altName);
        } else if (altName3Context != null) {
            processECs(altName3Context.ec(), altName);
        }
        this.object.getAltNames().add(altName);
    }

    @Override
    public void exitSub_name(@NotNull DeLineParser.Sub_nameContext ctx) {
        processExitSubname(
                ctx.full_name().name_value().getText(), ctx.ec(), this.object.getSubNames());
    }

    private void processExitSubname(
            String nameString,
            List<DeLineParser.EcContext> ecs,
            List<DeLineObject.Name> container) {

        DeLineObject.Name subName = new DeLineObject.Name();
        String fullName = this.object.getEvidenceInfo().retrieveEvidenceString(nameString);
        subName.setFullName(fullName);
        processECs(ecs, subName);
        container.add(subName);
    }

    @Override
    public void exitSub_sub_name(@NotNull DeLineParser.Sub_sub_nameContext ctx) {
        processExitSubname(ctx.full_name().name_value().getText(), ctx.ec(), block.getSubNames());
    }

    @Override
    public void enterIncluded_de(@NotNull DeLineParser.Included_deContext ctx) {
        block = new DeLineObject.NameBlock();
    }

    @Override
    public void exitIncluded_de(@NotNull DeLineParser.Included_deContext ctx) {
        this.object.getIncludedNames().add(this.block);
        this.block = null;
    }

    @Override
    public void enterContained_de(@NotNull DeLineParser.Contained_deContext ctx) {
        block = new DeLineObject.NameBlock();
    }

    @Override
    public void exitContained_de(@NotNull DeLineParser.Contained_deContext ctx) {
        this.object.getContainedNames().add(this.block);
        this.block = null;
    }

    @Override
    public void exitFlag_value(@NotNull DeLineParser.Flag_valueContext ctx) {

        DeLineObject.FlagType flag = null;

        if (ctx.FRAGMENT() != null) {
            flag = DeLineObject.FlagType.Fragment;
        } else if (ctx.PRECURSOR() != null) {
            flag = DeLineObject.FlagType.Precursor;
        } else if (ctx.FRAGMENTS() != null) {
            flag = DeLineObject.FlagType.Fragments;
        }

        this.object.getFlags().add(flag);

        DeLineParser.EvidenceContext evidence = ctx.evidence();
        if (evidence != null) {
            List<TerminalNode> terminalNodes = evidence.EV_TAG();
            EvidenceInfo.processEvidence(this.object.getEvidenceInfo(), flag, terminalNodes);
        }
    }

    private DeLineObject.Name createDeLineObjectName(
            DeLineParser.Full_nameContext fContext,
            List<DeLineParser.Short_nameContext> sContexts) {

        DeLineObject.Name recName = new DeLineObject.Name();

        EvidenceInfo evidenceInfo = this.object.getEvidenceInfo();

        if (fContext != null) {
            String name = fContext.name_value().getText();
            String fullName = evidenceInfo.retrieveEvidenceString(name);
            recName.setFullName(fullName);
        }

        List<String> shortNames = new ArrayList<>();
        List<DeLineParser.Short_nameContext> short_nameContexts = sContexts;
        for (DeLineParser.Short_nameContext short_nameContext : short_nameContexts) {
            String name = short_nameContext.name_value().getText();
            String shortName = evidenceInfo.retrieveEvidenceString(name);
            shortNames.add(shortName);
        }
        recName.setShortNames(shortNames);
        return recName;
    }

    private void processECs(List<DeLineParser.EcContext> ecs, DeLineObject.Name nameBelong) {
        for (DeLineParser.EcContext ecContext : ecs) {
            processEC(ecContext, nameBelong);
        }
    }

    private void processEC(DeLineParser.EcContext context, DeLineObject.Name nameBelong) {
        String ecValue = context.EC_NAME_VALUE().getText();
        nameBelong.getEcs().add(ecValue);

        DeLineParser.EvidenceContext evidence = context.evidence();
        if (evidence != null) {
            List<TerminalNode> terminalNodes = evidence.EV_TAG();

            DeLineObject.ECEvidence ecEvidence = new DeLineObject.ECEvidence();
            ecEvidence.setEcValue(ecValue);
            ecEvidence.setNameECBelong(nameBelong);

            EvidenceInfo.processEvidence(this.object.getEvidenceInfo(), ecEvidence, terminalNodes);
        }
    }
}

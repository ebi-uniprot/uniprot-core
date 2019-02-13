package uk.ac.ebi.uniprot.flatfile.parser.impl.de;

import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.TerminalNode;
import uk.ac.ebi.uniprot.flatfile.antlr.DeLineParser;
import uk.ac.ebi.uniprot.flatfile.antlr.DeLineParserBaseListener;
import uk.ac.ebi.uniprot.flatfile.parser.ParseTreeObjectExtractor;
import uk.ac.ebi.uniprot.flatfile.parser.impl.EvidenceInfo;

import java.util.List;

/**
 * Created with IntelliJ IDEA. User: wudong Date: 08/08/13 Time: 12:26 To change this template use File | Settings |
 * File Templates.
 */
public class DeLineModelListener extends DeLineParserBaseListener implements ParseTreeObjectExtractor<DeLineObject> {

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
        object.recName = new DeLineObject.Name();
        object.recName.fullName = ctx.full_name().name_value().getText();
        object.recName.fullName = object.getEvidenceInfo().retrieveEvidenceString(object.recName.fullName);

        List<DeLineParser.Short_nameContext> short_nameContexts = ctx.short_name();
        for (DeLineParser.Short_nameContext short_nameContext : short_nameContexts) {
            String name = short_nameContext.name_value().getText();
            object.recName.shortNames.add(
                    object.getEvidenceInfo().retrieveEvidenceString(name));
        }

        List<DeLineParser.EcContext> ec = ctx.ec();
        for (DeLineParser.EcContext ecContext : ec) {
            processECs(ecContext, object.recName);
        }
    }

    private void processECs(DeLineParser.EcContext context, DeLineObject.Name nameBelong) {
        String ecValue = context.EC_NAME_VALUE().getText();
        nameBelong.ecs.add(ecValue);

        DeLineParser.EvidenceContext evidence = context.evidence();
        if (evidence != null) {
            List<TerminalNode> terminalNodes = evidence.EV_TAG();

            DeLineObject.ECEvidence ecEvidence = new DeLineObject.ECEvidence();
            ecEvidence.ecValue = ecValue;
            ecEvidence.nameECBelong = nameBelong;

            EvidenceInfo.processEvidence(object.getEvidenceInfo(), ecEvidence, terminalNodes);
        }
    }

    @Override
    public void exitAlt_biotech(@NotNull DeLineParser.Alt_biotechContext ctx) {
        object.alt_Biotech = ctx.name_value().getText();
        object.alt_Biotech = object.getEvidenceInfo().retrieveEvidenceString(object.alt_Biotech);
    }

    @Override
    public void exitAlt_inn(@NotNull DeLineParser.Alt_innContext ctx) {
        String text = ctx.name_value().getText();
        object.alt_INN.add(object.getEvidenceInfo().retrieveEvidenceString(text));
    }

    @Override
    public void exitAlt_allergen(@NotNull DeLineParser.Alt_allergenContext ctx) {
        object.alt_Allergen = ctx.name_value().getText();
        object.alt_Allergen = object.getEvidenceInfo().retrieveEvidenceString(object.alt_Allergen);
    }

    @Override
    public void exitAlt_cdantigen(@NotNull DeLineParser.Alt_cdantigenContext ctx) {
        String text = ctx.name_value().getText();
        object.alt_CD_antigen.add(object.getEvidenceInfo().retrieveEvidenceString(text));
    }

    @Override
    public void exitSub_alt_biotech(@NotNull DeLineParser.Sub_alt_biotechContext ctx) {
        block.alt_Biotech = ctx.name_value().getText();
        block.alt_Biotech = object.getEvidenceInfo().retrieveEvidenceString(block.alt_Biotech);
    }

    @Override
    public void exitSub_alt_inn(@NotNull DeLineParser.Sub_alt_innContext ctx) {
        String text = ctx.name_value().getText();
        block.alt_INN.add(object.getEvidenceInfo().retrieveEvidenceString(text));
    }

    @Override
    public void exitSub_alt_allergen(@NotNull DeLineParser.Sub_alt_allergenContext ctx) {
        block.alt_Allergen = ctx.name_value().getText();
        block.alt_Allergen = object.getEvidenceInfo().retrieveEvidenceString(block.alt_Allergen);
    }

    @Override
    public void exitSub_alt_cdantigen(@NotNull DeLineParser.Sub_alt_cdantigenContext ctx) {
        String text = ctx.name_value().getText();
        block.alt_CD_antigen.add(object.getEvidenceInfo().retrieveEvidenceString(text));
    }

    @Override
    public void exitSub_rec_name(@NotNull DeLineParser.Sub_rec_nameContext ctx) {
        block.recName = new DeLineObject.Name();
        block.recName.fullName = ctx.full_name().name_value().getText();
        block.recName.fullName = object.getEvidenceInfo().retrieveEvidenceString(block.recName.fullName);

        List<DeLineParser.Short_nameContext> short_nameContexts = ctx.short_name();
        for (DeLineParser.Short_nameContext short_nameContext : short_nameContexts) {
            String text = short_nameContext.name_value().getText();
            block.recName.shortNames.add(object.getEvidenceInfo().retrieveEvidenceString(text));
        }
        List<DeLineParser.EcContext> ec = ctx.ec();
        for (DeLineParser.EcContext ecContext : ec) {
            processECs(ecContext, block.recName);
        }
//        for (DeLineParser.EcContext ecContext : ec) {
//            String text = ecContext.EC_NAME_VALUE().getText();
//            block.recName.ecs.add(object.getEvidenceInfo().retrieveEvidenceString(text));
//        }
    }

    @Override
    public void exitSub_alt_name(@NotNull DeLineParser.Sub_alt_nameContext ctx) {
        DeLineObject.Name name = new DeLineObject.Name();
        DeLineParser.Sub_alt_name_1Context alt_name_1Context = ctx.sub_alt_name_1();
        DeLineParser.Sub_alt_name_2Context alt_name_2Context = ctx.sub_alt_name_2();
        DeLineParser.Sub_alt_name_3Context alt_name_3Context = ctx.sub_alt_name_3();

        if (alt_name_1Context != null) {
            DeLineParser.Full_nameContext full_nameContext = alt_name_1Context.full_name();
            if (full_nameContext != null) {
                name.fullName = full_nameContext.name_value().getText();
                name.fullName = object.getEvidenceInfo().retrieveEvidenceString(name.fullName);
            }

            List<DeLineParser.Short_nameContext> short_nameContexts = alt_name_1Context.short_name();
            for (DeLineParser.Short_nameContext short_nameContext : short_nameContexts) {
                String text = short_nameContext.name_value().getText();
                name.shortNames.add(object.getEvidenceInfo().retrieveEvidenceString(text));
            }
            List<DeLineParser.EcContext> ec = alt_name_1Context.ec();
            for (DeLineParser.EcContext ecContext : ec) {
                processECs(ecContext, name);

            }
        } else if (alt_name_2Context != null) {
            List<DeLineParser.Short_nameContext> short_nameContexts = alt_name_2Context.short_name();
            for (DeLineParser.Short_nameContext short_nameContext : short_nameContexts) {
                String text = short_nameContext.name_value().getText();
                name.shortNames.add(object.getEvidenceInfo().retrieveEvidenceString(text));
            }

            List<DeLineParser.EcContext> ec = alt_name_2Context.ec();
            for (DeLineParser.EcContext ecContext : ec) {
                processECs(ecContext, name);
            }
        } else if (alt_name_3Context != null) {
            List<DeLineParser.EcContext> ec = alt_name_3Context.ec();
            for (DeLineParser.EcContext ecContext : ec) {
                processECs(ecContext, name);
            }
        }
        block.altName.add(name);
    }

    @Override
    public void exitAlt_name(@NotNull DeLineParser.Alt_nameContext ctx) {
        DeLineObject.Name name = new DeLineObject.Name();
        DeLineParser.Alt_name_1Context alt_name_1Context = ctx.alt_name_1();
        DeLineParser.Alt_name_2Context alt_name_2Context = ctx.alt_name_2();
        DeLineParser.Alt_name_3Context alt_name_3Context = ctx.alt_name_3();

        if (alt_name_1Context != null) {
            if (alt_name_1Context.full_name() != null) {
                name.fullName = alt_name_1Context.full_name().name_value().getText();
                name.fullName = object.getEvidenceInfo().retrieveEvidenceString(name.fullName);
            }

            List<DeLineParser.Short_nameContext> short_nameContexts = alt_name_1Context.short_name();
            for (DeLineParser.Short_nameContext short_nameContext : short_nameContexts) {
                String text = short_nameContext.name_value().getText();
                name.shortNames.add(object.getEvidenceInfo().retrieveEvidenceString(text));
            }

            List<DeLineParser.EcContext> ec = alt_name_1Context.ec();
            for (DeLineParser.EcContext ecContext : ec) {
                processECs(ecContext, name);
            }
        } else if (alt_name_2Context != null) {
            List<DeLineParser.Short_nameContext> short_nameContexts = alt_name_2Context.short_name();
            for (DeLineParser.Short_nameContext short_nameContext : short_nameContexts) {
                String text = short_nameContext.name_value().getText();
                name.shortNames.add(object.getEvidenceInfo().retrieveEvidenceString(text));
            }

            List<DeLineParser.EcContext> ec = alt_name_2Context.ec();
            for (DeLineParser.EcContext ecContext : ec) {
                processECs(ecContext, name);
            }
        } else if (alt_name_3Context != null) {
            List<DeLineParser.EcContext> ec = alt_name_3Context.ec();
            for (DeLineParser.EcContext ecContext : ec) {
                processECs(ecContext, name);
            }
        }
        object.altName.add(name);
    }

    @Override
    public void exitSub_name(@NotNull DeLineParser.Sub_nameContext ctx) {
        processExitSubname(ctx.full_name().name_value().getText(), ctx.ec(), object.subName);
    }

    private void processExitSubname(String nameString, List<DeLineParser.EcContext> ec,
            List<DeLineObject.Name> container) {
        DeLineObject.Name name = new DeLineObject.Name();

        name.fullName = object.getEvidenceInfo().retrieveEvidenceString(nameString);

        for (DeLineParser.EcContext ecContext : ec) {
            processECs(ecContext, name);
        }

        container.add(name);
    }

    @Override
    public void exitSub_sub_name(@NotNull DeLineParser.Sub_sub_nameContext ctx) {
        processExitSubname(ctx.full_name().name_value().getText(), ctx.ec(), block.subName);
    }

    @Override
    public void enterIncluded_de(@NotNull DeLineParser.Included_deContext ctx) {
        block = new DeLineObject.NameBlock();
    }

    @Override
    public void exitIncluded_de(@NotNull DeLineParser.Included_deContext ctx) {
        object.includedNames.add(block);
        block = null;
    }

    @Override
    public void enterContained_de(@NotNull DeLineParser.Contained_deContext ctx) {
        block = new DeLineObject.NameBlock();
    }

    @Override
    public void exitContained_de(@NotNull DeLineParser.Contained_deContext ctx) {
        object.containedNames.add(block);
        block = null;
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

        object.flags.add(flag);

        DeLineParser.EvidenceContext evidence = ctx.evidence();
        if (evidence != null) {
            List<TerminalNode> terminalNodes = evidence.EV_TAG();
            EvidenceInfo.processEvidence(object.getEvidenceInfo(), flag, terminalNodes);
        }
    }

}

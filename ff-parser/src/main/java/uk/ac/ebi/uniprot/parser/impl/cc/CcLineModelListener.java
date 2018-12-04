package uk.ac.ebi.uniprot.parser.impl.cc;

import uk.ac.ebi.uniprot.parser.ParseTreeObjectExtractor;
import uk.ac.ebi.uniprot.antlr.CcLineParser;
import uk.ac.ebi.uniprot.antlr.CcLineParser.Cc_alternative_value_with_evidenceContext;
import uk.ac.ebi.uniprot.antlr.CcLineParser.Cc_biophyiochemical_absorption_basContext;
import uk.ac.ebi.uniprot.antlr.CcLineParser.Cc_biophyiochemical_absorption_noteContext;
import uk.ac.ebi.uniprot.antlr.CcLineParser.Cc_cat_act_pd_lineContext;
import uk.ac.ebi.uniprot.antlr.CcLineParser.Cc_cat_act_reaction_lineContext;
import uk.ac.ebi.uniprot.antlr.CcLineParser.Cc_cofactor_lineContext;
import uk.ac.ebi.uniprot.antlr.CcLineParser.Cc_cofactor_linesContext;
import uk.ac.ebi.uniprot.antlr.CcLineParser.Cc_cofactor_moleculeContext;
import uk.ac.ebi.uniprot.antlr.CcLineParser.Cc_cofactor_noteContext;
import uk.ac.ebi.uniprot.antlr.CcLineParser.Cc_common_text_with_evContext;
import uk.ac.ebi.uniprot.antlr.CcLineParser.Cc_common_textsContext;
import uk.ac.ebi.uniprot.antlr.CcLineParser.Cc_properties_note_text_level2_with_evContext;
import uk.ac.ebi.uniprot.antlr.CcLineParser.Cc_properties_note_text_with_evContext;
import uk.ac.ebi.uniprot.antlr.CcLineParser.Cc_properties_notesContext;
import uk.ac.ebi.uniprot.antlr.CcLineParser.Cc_properties_notes_level_2Context;
import uk.ac.ebi.uniprot.antlr.CcLineParser.Cc_rna_editing_single_positionContext;
import uk.ac.ebi.uniprot.antlr.CcLineParserBaseListener;
import uk.ac.ebi.uniprot.parser.impl.EvidenceInfo;
import uk.ac.ebi.uniprot.parser.impl.cc.CcLineObject.EvidencedString;

import java.util.ArrayList;
import java.util.List;
import org.antlr.v4.runtime.tree.TerminalNode;

/**
 * Created with IntelliJ IDEA. User: wudong Date: 08/08/13 Time: 12:26 To change this template use File | Settings |
 * File Templates.
 */
public class CcLineModelListener extends CcLineParserBaseListener implements ParseTreeObjectExtractor<CcLineObject> {

    private CcLineObject object;

    private EvidencedString getEvidencedString(CcLineParser.Cc_properties_text_level2_with_evContext context) {
        String text = context.cc_properties_text_level2().getText().trim();
        List<String> evidence =
                context.evidence() == null ? null : EvidenceInfo.processEvidence(context.evidence().EV_TAG());
        return EvidencedString.get(text, evidence);
    }

    private EvidencedString getEvidencedString(CcLineParser.Cc_properties_note_text_level2_with_evContext context) {
        String text = context.cc_properties_note_text_level2().getText().trim();
        List<String> evidence =
                context.evidence() == null ? null : EvidenceInfo.processEvidence(context.evidence().EV_TAG());
        return EvidencedString.get(text, evidence);
    }

    private EvidencedString getEvidencedString(CcLineParser.Cc_properties_note_text_with_evContext context) {
        String text = context.cc_properties_note_text().getText().trim();
        List<String> evidence =
                context.evidence() == null ? null : EvidenceInfo.processEvidence(context.evidence().EV_TAG());
        return EvidencedString.get(text, evidence);
    }

    private EvidencedString getEvidencedString(CcLineParser.Cc_common_text_with_evContext context) {
        String text = context.cc_common_text().getText().trim();
        List<String> evidence =
                context.evidence() == null ? null : EvidenceInfo.processEvidence(context.evidence().EV_TAG());
        return EvidencedString.get(text, evidence);
    }

    private EvidencedString getEvidenceString(Cc_alternative_value_with_evidenceContext context) {
        String text = context.cc_alternative_value().getText().trim();
        List<String> evidence =
                context.evidence() == null ? null : EvidenceInfo.processEvidence(context.evidence().EV_TAG());
        return EvidencedString.get(text, evidence);
    }

    @Override
    public void exitCc_common(CcLineParser.Cc_commonContext ctx) {
        CcLineObject.CC ccCommon = new CcLineObject.CC();
        TerminalNode terminalNode = ctx.CC_TOPIC_COMMON();
        ccCommon.topic = CcLineObject.CCTopicEnum.fromString(terminalNode.getText());

        CcLineObject.FreeText ftext = new CcLineObject.FreeText();

        Cc_common_textsContext ccCommonTextsContext = ctx.cc_common_texts();
        List<Cc_common_text_with_evContext> ccCommonTextWithEvContexts = ccCommonTextsContext.cc_common_text_with_ev();

        for (Cc_common_text_with_evContext ftCtx : ccCommonTextWithEvContexts) {
            ftext.texts.add(getEvidencedString(ftCtx));
        }

        ccCommon.object = ftext;
        object.ccs.add(ccCommon);
    }

    @Override
    public void enterCc_cc(CcLineParser.Cc_ccContext ctx) {
        object = new CcLineObject();
    }

    @Override
    public void enterCc_lines(CcLineParser.Cc_linesContext ctx) {
        object = new CcLineObject();
    }

    @Override
    public void exitCc_sequence_caution(CcLineParser.Cc_sequence_cautionContext ctx) {
        CcLineObject.CC cc = new CcLineObject.CC();
        cc.topic = CcLineObject.CCTopicEnum.SEQUENCE_CAUTION;

        CcLineObject.SequenceCaution sc = new CcLineObject.SequenceCaution();
        cc.object = sc;
        object.ccs.add(cc);

        List<CcLineParser.Cc_sequence_caution_lineContext> ccSequenceCautionLineContexts =
                ctx.cc_sequence_caution_line();

        for (CcLineParser.Cc_sequence_caution_lineContext lineContext : ccSequenceCautionLineContexts) {
            CcLineObject.SequenceCautionObject object1 = new CcLineObject.SequenceCautionObject();
            sc.sequenceCautionObjects.add(object1);

            CcLineParser.Cc_sequence_caution_sequenceContext ccSequenceCautionSequenceContext =
                    lineContext.cc_sequence_caution_sequence();
            object1.sequence = ccSequenceCautionSequenceContext.CC_SC_SEQUENCE_TEXT().getText();
            if (ccSequenceCautionSequenceContext.evidence() != null) {
                EvidenceInfo.processEvidence(object.getEvidenceInfo(), object1.sequence,
                        ccSequenceCautionSequenceContext.evidence().EV_TAG());
            }

            CcLineParser.Cc_sequence_caution_typeContext ccSequenceCautionTypeContext =
                    lineContext.cc_sequence_caution_type();
            object1.type = CcLineObject.SequenceCautionType
                    .fromSting(ccSequenceCautionTypeContext.CC_SC_TYPE_VALUE().getText());

            CcLineParser.Cc_sequence_caution_positionContext ccSequenceCautionPositionContext =
                    lineContext.cc_sequence_caution_position();
            if (ccSequenceCautionPositionContext != null) {
                CcLineParser.Cc_sequence_caution_position_valueContext ccc =
                        ccSequenceCautionPositionContext.cc_sequence_caution_position_value();

                List<TerminalNode> integer = ccc.INTEGER();
                for (TerminalNode terminalNode : integer) {
                    object1.positions.add(Integer.parseInt(terminalNode.getText()));
                }

                TerminalNode terminalNode = ccc.CC_SC_P_VALUE();
                if (terminalNode != null) {
                    object1.positionValue = terminalNode.getText();
                }
            }

            CcLineParser.Cc_sequence_caution_noteContext ccSequenceCautionNoteContext =
                    lineContext.cc_sequence_caution_note();
            if (ccSequenceCautionNoteContext != null) {
                object1.note = ccSequenceCautionNoteContext.CC_SC_NOTE_TEXT().getText();
            }

            CcLineParser.Cc_sequence_caution_evidenceContext ev = lineContext.cc_sequence_caution_evidence();
            if (ev != null) {
                EvidenceInfo.processEvidence(object.getEvidenceInfo(), object1, ev.EV_TAG());
            }
        }
    }

    @Override
    public void exitCc_mass_spectrometry(CcLineParser.Cc_mass_spectrometryContext ctx) {
        CcLineObject.CC cc = new CcLineObject.CC();
        cc.topic = CcLineObject.CCTopicEnum.MASS_SPECTROMETRY;

        CcLineObject.MassSpectrometry ms = new CcLineObject.MassSpectrometry();
        cc.object = ms;
        object.ccs.add(cc);

        CcLineParser.Cc_mass_spectrometry_massContext ccMassSpectrometryMassContext =
                ctx.cc_mass_spectrometry_mass();
        String text = ccMassSpectrometryMassContext.CC_MS_V_NUMBER().getText();
        ms.mass = Double.parseDouble(text);

        CcLineParser.Cc_mass_spectrometry_mass_errorContext ccMassSpectrometryMassErrorContext =
                ctx.cc_mass_spectrometry_mass_error();
        if (ccMassSpectrometryMassErrorContext != null) {
            String text1 = ccMassSpectrometryMassErrorContext.CC_MS_V_NUMBER().getText();
            ms.massError = Double.parseDouble(text1);
        }

        CcLineParser.Cc_mass_spectrometry_mass_methodContext ccMassSpectrometryMassMethodContext =
                ctx.cc_mass_spectrometry_mass_method();
        String text1 = ccMassSpectrometryMassMethodContext.cc_mass_spectrometry_value().getText();
        ms.method = text1;

        CcLineParser.Cc_mass_spectrometry_mass_rangeContext rangeContext = ctx.cc_mass_spectrometry_mass_range();

        List<CcLineParser.Cc_mass_spectrometry_mass_range_valueContext> rangeValueContexts =
                rangeContext.cc_mass_spectrometry_mass_range_value();
        for (CcLineParser.Cc_mass_spectrometry_mass_range_valueContext rangeValueContext : rangeValueContexts) {
            CcLineParser.Cc_mass_spectrometry_mass_range_value_valueContext vv1 =
                    rangeValueContext.cc_mass_spectrometry_mass_range_value_value(0);

            CcLineParser.Cc_mass_spectrometry_mass_range_value_valueContext vv2 =
                    rangeValueContext.cc_mass_spectrometry_mass_range_value_value(1);

            CcLineObject.MassSpectrometryRange range = new CcLineObject.MassSpectrometryRange();

            if (vv1.INTEGER() != null) {
                range.start = Integer.parseInt(vv1.INTEGER().getText());
            } else {
                range.startUnknown = true;
            }

            if (vv2.INTEGER() != null) {
                range.end = Integer.parseInt(vv2.INTEGER().getText());
            } else {
                range.endUnknown = true;
            }

            CcLineParser.Cc_mass_spectrometry_mass_range_isoformContext isoform =
                    rangeValueContext.cc_mass_spectrometry_mass_range_isoform();
            if (isoform != null) {
                String text2 = isoform.CC_MS_R_V_ISO().getText();
                range.rangeIsoform = text2.replace("\nCC       ", "");
            }
            ms.ranges.add(range);
        }

        CcLineParser.Cc_mass_spectrometry_mass_noteContext noteContext = ctx.cc_mass_spectrometry_mass_note();
        if (noteContext != null) {
            ms.note = noteContext.cc_properties_text().getText();
        }

        CcLineParser.Cc_mass_spectrometry_mass_sourceContext sourceContext = ctx.cc_mass_spectrometry_mass_source();
        if (sourceContext != null) {
            EvidenceInfo.processEvidence(object.getEvidenceInfo(), ms, sourceContext.EV_TAG());

            // also put into the sources to maintain code compatibility
            List<TerminalNode> terminalNodes = sourceContext.EV_TAG();
            for (TerminalNode node : terminalNodes) {
                ms.sources.add(node.getText());
            }
        }
    }

    @Override
    public void exitCc_web_resource(CcLineParser.Cc_web_resourceContext ctx) {
        CcLineObject.CC cc = new CcLineObject.CC();
        cc.topic = CcLineObject.CCTopicEnum.WEB_RESOURCE;

        CcLineObject.WebResource wr = new CcLineObject.WebResource();
        cc.object = wr;
        object.ccs.add(cc);

        CcLineParser.Cc_web_resource_nameContext ccWebResourceNameContext = ctx.cc_web_resource_name();
        wr.name = ccWebResourceNameContext.cc_properties_text().getText();

        CcLineParser.Cc_web_resource_urlContext ccWebResourceUrlContext = ctx.cc_web_resource_url();
        if (ccWebResourceUrlContext != null) {
            String text = ccWebResourceUrlContext.cc_properties_text().getText();
            // url is in the format of "http://..."
            wr.url = text.substring(1, text.length() - 1);
        }

        CcLineParser.Cc_web_resource_noteContext ccWebResourceNoteContext = ctx.cc_web_resource_note();
        if (ccWebResourceNoteContext != null) {
            wr.note = ccWebResourceNoteContext.cc_properties_text().getText();
        }
    }

    @Override
    public void exitCc_interaction(CcLineParser.Cc_interactionContext ctx) {
        CcLineObject.CC cc = new CcLineObject.CC();
        cc.topic = CcLineObject.CCTopicEnum.INTERACTION;

        CcLineObject.Interaction ir = new CcLineObject.Interaction();
        cc.object = ir;
        object.ccs.add(cc);

        // CC {{SP_Ac:identifier[ (xeno)]}|Self}; NbExp=n; IntAct=IntAct_Protein_Ac, IntAct_Protein_Ac;
        List<CcLineParser.Cc_interaction_lineContext> ccInteractionLineContexts = ctx.cc_interaction_line();
        for (CcLineParser.Cc_interaction_lineContext io : ccInteractionLineContexts) {
            CcLineObject.InteractionObject interactionObject = new CcLineObject.InteractionObject();
            CcLineParser.Cc_interaction_intactContext ccInteractionIntactContext = io.cc_interaction_intact();
            interactionObject.firstId = ccInteractionIntactContext.CC_IR_AC(0).getText();
            interactionObject.secondId = ccInteractionIntactContext.CC_IR_AC(1).getText();

            TerminalNode integer = io.cc_interaction_nbexp().INTEGER();
            interactionObject.nbexp = Integer.parseInt(integer.getText());

            CcLineParser.Cc_interaction_spContext ccInteractionSpContext = io.cc_interaction_sp();
            if (ccInteractionSpContext.CC_IR_SELF() != null) {
                interactionObject.isSelf = true;
            } else {
                interactionObject.spAc = ccInteractionSpContext.CC_IR_AC().getText();
                if (ccInteractionSpContext.DASH() != null) {
                    interactionObject.gene = "-";
                } else {
                    interactionObject.gene = ccInteractionSpContext.cc_interaction_gene().getText();
                }

                if (ccInteractionSpContext.CC_IR_XENO() != null) {
                    interactionObject.xeno = true;
                }
            }

            ir.interactions.add(interactionObject);
        }
    }

    @Override
    public void exitCc_biophyiochemical(CcLineParser.Cc_biophyiochemicalContext ctx) {
        CcLineObject.CC cc = new CcLineObject.CC();
        cc.topic = CcLineObject.CCTopicEnum.BIOPHYSICOCHEMICAL_PROPERTIES;
        CcLineObject.BiophysicochemicalProperties bp = new CcLineObject.BiophysicochemicalProperties();
        cc.object = bp;
        object.ccs.add(cc);

        List<CcLineParser.Cc_biophyiochemical_propertiesContext> ctx2 = ctx.cc_biophyiochemical_properties();
        for (CcLineParser.Cc_biophyiochemical_propertiesContext ccBiophyiochemicalPropertiesContext : ctx2) {
            processBiophysicalProperties(ccBiophyiochemicalPropertiesContext, bp);
        }
    }

    private void processBiophysicalProperties(CcLineParser.Cc_biophyiochemical_propertiesContext ctx,
            CcLineObject.BiophysicochemicalProperties bp) {
        if (ctx.cc_biophyiochemical_absorption() != null) {
            Cc_biophyiochemical_absorption_basContext absBasContext =
                    ctx.cc_biophyiochemical_absorption().cc_biophyiochemical_absorption_bas();
            CcLineParser.Cc_properties_text_level2_with_evContext textWithEv =
                    absBasContext.cc_properties_text_level2_with_ev();
            String text = textWithEv.cc_properties_text_level2().getText();
            String absValue = text;

            if (text.startsWith("~")) {
                bp.bsorptionAbsApproximate = true;
                absValue = text.substring(1);
            }

            CcLineParser.EvidenceContext evidence1 = textWithEv.evidence();
            bp.bsorptionAbs = EvidencedString.get(absValue,
                    evidence1 == null ? null : EvidenceInfo.processEvidence(evidence1.EV_TAG()));

            Cc_biophyiochemical_absorption_noteContext absNotContext =
                    ctx.cc_biophyiochemical_absorption().cc_biophyiochemical_absorption_note();
            if (absNotContext != null) {
                Cc_properties_notes_level_2Context ccPropertiesNotesLevel2Context =
                        absNotContext.cc_properties_notes_level_2();
                List<CcLineParser.Cc_properties_note_text_level2_with_evContext> textWithEvs =
                        ccPropertiesNotesLevel2Context.cc_properties_note_text_level2_with_ev();
                for (CcLineParser.Cc_properties_note_text_level2_with_evContext c : textWithEvs) {
                    bp.bsorptionNote.add(getEvidencedString(c));
                }
            }
        }

        CcLineParser.Cc_biophyiochemical_kineticContext kineticContext = ctx.cc_biophyiochemical_kinetic();
        if (kineticContext != null) {
            List<CcLineParser.Cc_biophyiochemical_kinetic_kmContext> ccBiophyiochemicalKineticKmContexts =
                    kineticContext.cc_biophyiochemical_kinetic_km();
            for (CcLineParser.Cc_biophyiochemical_kinetic_kmContext km : ccBiophyiochemicalKineticKmContexts) {
                CcLineParser.Cc_properties_text_level2_with_evContext c = km.cc_properties_text_level2_with_ev();
                bp.kms.add(getEvidencedString(c));
            }

            List<CcLineParser.Cc_biophyiochemical_kinetic_bpmaxContext> maxcontext =
                    kineticContext.cc_biophyiochemical_kinetic_bpmax();
            for (CcLineParser.Cc_biophyiochemical_kinetic_bpmaxContext c : maxcontext) {
                CcLineParser.Cc_properties_text_level2_with_evContext cc = c.cc_properties_text_level2_with_ev();
                bp.vmaxs.add(getEvidencedString(cc));
            }

            CcLineParser.Cc_biophyiochemical_kinetic_noteContext noteContext =
                    kineticContext.cc_biophyiochemical_kinetic_note();
            if (noteContext != null) {
                Cc_properties_notes_level_2Context ccPropertiesNotesLevel2Context =
                        noteContext.cc_properties_notes_level_2();
                List<CcLineParser.Cc_properties_note_text_level2_with_evContext> textLevel2WithEvContexts =
                        ccPropertiesNotesLevel2Context.cc_properties_note_text_level2_with_ev();
                for (CcLineParser.Cc_properties_note_text_level2_with_evContext cc : textLevel2WithEvContexts) {
                    bp.kpNote.add(getEvidencedString(cc));
                }
            }
        }

        if (ctx.cc_biophyiochemical_redox() != null) {
            Cc_properties_notes_level_2Context c = ctx.cc_biophyiochemical_redox().cc_properties_notes_level_2();
            List<CcLineParser.Cc_properties_note_text_level2_with_evContext> textWithEvs =
                    c.cc_properties_note_text_level2_with_ev();
            for (CcLineParser.Cc_properties_note_text_level2_with_evContext cc : textWithEvs) {
                bp.rdoxPotential.add(getEvidencedString(cc));
            }
        }

        if (ctx.cc_biophyiochemical_ph() != null) {
            CcLineParser.Cc_properties_notes_level_2Context c =
                    ctx.cc_biophyiochemical_ph().cc_properties_notes_level_2();
            List<CcLineParser.Cc_properties_note_text_level2_with_evContext> textWithEvs =
                    c.cc_properties_note_text_level2_with_ev();
            for (CcLineParser.Cc_properties_note_text_level2_with_evContext cc : textWithEvs) {
                bp.phDependence.add(getEvidencedString(cc));
            }
        }

        if (ctx.cc_biophyiochemical_temperature() != null) {
            Cc_properties_notes_level_2Context ccPropertiesNotesLevel2Context =
                    ctx.cc_biophyiochemical_temperature().cc_properties_notes_level_2();
            List<Cc_properties_note_text_level2_with_evContext> textLevel2WithEvContexts =
                    ccPropertiesNotesLevel2Context.cc_properties_note_text_level2_with_ev();
            for (Cc_properties_note_text_level2_with_evContext textLevel2WithEvContext : textLevel2WithEvContexts) {
                bp.temperatureDependence.add(getEvidencedString(textLevel2WithEvContext));
            }
        }
    }

    @Override
    public void exitCc_rna_editing(CcLineParser.Cc_rna_editingContext ctx) {

        CcLineObject.CC cc = new CcLineObject.CC();

        cc.topic = CcLineObject.CCTopicEnum.RNA_EDITING;
        CcLineObject.RnaEditing re = new CcLineObject.RnaEditing();
        cc.object = re;
        CcLineParser.Cc_rna_editing_positionContext positionContext =
                ctx.cc_rna_edigint_modified_position().cc_rna_editing_position();
        if (positionContext != null) {
            List<Cc_rna_editing_single_positionContext> singlePosCtxs =
                    positionContext.cc_rna_editing_single_position();
            for (Cc_rna_editing_single_positionContext sPosCtx : singlePosCtxs) {
                String text = sPosCtx.INTEGER().getText();
                re.locations.add(Integer.parseInt(text));
                if (sPosCtx.evidence() != null) {
                    EvidenceInfo.processEvidence(object.getEvidenceInfo(), text, sPosCtx.evidence().EV_TAG());
                }
            }
        }

        else if ((ctx.cc_rna_edigint_modified_position().cc_re_position_undetermined() != null)
                && (ctx.cc_rna_edigint_modified_position().cc_re_position_undetermined()
                        .CC_RE_MODIFIED_POSITION_UNDETERMINED() != null)) {
            re.locationEnum = CcLineObject.RnaEditingLocationEnum.UNDETERMINED;
            if (ctx.cc_rna_edigint_modified_position().cc_re_position_undetermined().evidence() != null) {
                EvidenceInfo.processEvidence(object.getEvidenceInfo(), re.locationEnum,
                        ctx.cc_rna_edigint_modified_position().cc_re_position_undetermined().evidence().EV_TAG());
            }
        } else if ((ctx.cc_rna_edigint_modified_position().cc_re_position_not_applicable() != null)
                && (ctx.cc_rna_edigint_modified_position().cc_re_position_not_applicable()
                        .CC_RE_MODIFIED_POSITION_NOT_APPLICABLE() != null)) {
            re.locationEnum = CcLineObject.RnaEditingLocationEnum.NOT_APPLICABLE;
            if (ctx.cc_rna_edigint_modified_position().cc_re_position_not_applicable().evidence() != null) {
                EvidenceInfo.processEvidence(object.getEvidenceInfo(), re.locationEnum,
                        ctx.cc_rna_edigint_modified_position().cc_re_position_not_applicable().evidence().EV_TAG());
            }
        }

        if (ctx.cc_rna_edigint_note() != null) {
            Cc_properties_notesContext ccPropertiesNotesContext = ctx.cc_rna_edigint_note().cc_properties_notes();
            List<Cc_properties_note_text_with_evContext> ccPropertiesTextWithEvContexts =
                    ccPropertiesNotesContext.cc_properties_note_text_with_ev();
            for (Cc_properties_note_text_with_evContext c : ccPropertiesTextWithEvContexts) {
                re.note.add(getEvidencedString(c));
            }
        }
        object.ccs.add(cc);
    }

    @Override
    public void exitCc_subcellular_location(CcLineParser.Cc_subcellular_locationContext ctx) {

        CcLineObject.CC cc = new CcLineObject.CC();

        cc.topic = CcLineObject.CCTopicEnum.SUBCELLULAR_LOCATION;
        CcLineObject.SubcullarLocation sl = new CcLineObject.SubcullarLocation();
        cc.object = sl;

        CcLineParser.Cc_subcellular_location_moleculeContext ccSubcellularLocationMoleculeContext =
                ctx.cc_subcellular_location_molecule();
        if (ccSubcellularLocationMoleculeContext != null) {
            String word = ccSubcellularLocationMoleculeContext.cc_subcellular_words().getText();
            sl.molecule = word;
        }

        CcLineParser.Cc_subcellular_noteContext ccSubcellularNoteContext = ctx.cc_subcellular_note();
        if (ccSubcellularNoteContext != null) {
            Cc_common_textsContext ccCommonTextsContext = ccSubcellularNoteContext.cc_common_texts();
            List<Cc_common_text_with_evContext> ccCommonTextWithEvContexts =
                    ccCommonTextsContext.cc_common_text_with_ev();

            for (Cc_common_text_with_evContext ftCtx : ccCommonTextWithEvContexts) {
                sl.note.add(getEvidencedString(ftCtx));
            }
        }

        CcLineParser.Cc_subcellular_location_sectionContext ccSubcellularLocationSectionContext =
                ctx.cc_subcellular_location_section();
        if (ccSubcellularLocationSectionContext != null) {
            List<CcLineParser.Cc_subcellular_location_location_with_evidenceContext> ccSubcellularLocationLocationWithEvidenceContexts =
                    ccSubcellularLocationSectionContext.cc_subcellular_location_location_with_evidence();
            for (CcLineParser.Cc_subcellular_location_location_with_evidenceContext locationContext : ccSubcellularLocationLocationWithEvidenceContexts) {
                CcLineObject.LocationObject locationObject = new CcLineObject.LocationObject();

                CcLineParser.Cc_subcellular_location_locationContext ccSubcellularLocationLocationContext =
                        locationContext.cc_subcellular_location_location();

                List<CcLineParser.Cc_subcellular_location_value_with_evidenceContext> valueContexts =
                        ccSubcellularLocationLocationContext.cc_subcellular_location_value_with_evidence();
                int size = valueContexts.size();
                if (size >= 1) {
                    CcLineParser.Cc_subcellular_location_value_with_evidenceContext locEvi = valueContexts.get(0);
                    CcLineParser.Cc_subcellular_location_valueContext ccSubcellularLocationValueContext =
                            locEvi.cc_subcellular_location_value();

                    locationObject.subcellularLocation = new CcLineObject.LocationValue();

                    locationObject.subcellularLocation.value =
                            ccSubcellularLocationValueContext.cc_subcellular_words().getText();

                    if (ccSubcellularLocationValueContext.cc_subcellular_location_flag() != null) {
                        String text = ccSubcellularLocationValueContext.cc_subcellular_location_flag().CC_SL_FLAG()
                                .getText();
                        String replace = text.replace("\nCC       ", " ");
                        locationObject.subcellularLocation.flag =
                                CcLineObject.LocationFlagEnum.fromSting(replace.substring(1, replace.length() - 1));
                    }

                    if (locEvi.evidence() != null) {
                        EvidenceInfo.processEvidence(object.getEvidenceInfo(), locationObject.subcellularLocation,
                                locEvi.evidence().EV_TAG());
                    }
                }

                if (size >= 2) {

                    CcLineParser.Cc_subcellular_location_value_with_evidenceContext locEvi = valueContexts.get(1);
                    CcLineParser.Cc_subcellular_location_valueContext ccSubcellularLocationValueContext =
                            locEvi.cc_subcellular_location_value();

                    locationObject.topology = new CcLineObject.LocationValue();
                    locationObject.topology.value =
                            ccSubcellularLocationValueContext.cc_subcellular_words().getText();

                    if (ccSubcellularLocationValueContext.cc_subcellular_location_flag() != null) {
                        String text = ccSubcellularLocationValueContext.cc_subcellular_location_flag().CC_SL_FLAG()
                                .getText();
                        String replace = text.replace("\nCC       ", " ");
                        locationObject.topology.flag =
                                CcLineObject.LocationFlagEnum.fromSting(replace.substring(1, replace.length() - 1));
                    }

                    if (locEvi.evidence() != null) {
                        EvidenceInfo.processEvidence(object.getEvidenceInfo(), locationObject.topology,
                                locEvi.evidence().EV_TAG());
                    }
                }

                if (size >= 3) {
                    CcLineParser.Cc_subcellular_location_value_with_evidenceContext locEvi = valueContexts.get(2);
                    CcLineParser.Cc_subcellular_location_valueContext ccSubcellularLocationValueContext =
                            locEvi.cc_subcellular_location_value();

                    locationObject.orientation = new CcLineObject.LocationValue();
                    locationObject.orientation.value =
                            ccSubcellularLocationValueContext.cc_subcellular_words().getText();

                    if (ccSubcellularLocationValueContext.cc_subcellular_location_flag() != null) {
                        String text = ccSubcellularLocationValueContext.cc_subcellular_location_flag().CC_SL_FLAG()
                                .getText();
                        String replace = text.replace("\nCC       ", " ");
                        locationObject.orientation.flag =
                                CcLineObject.LocationFlagEnum.fromSting(replace.substring(1, replace.length() - 1));
                    }

                    if (locEvi.evidence() != null) {
                        EvidenceInfo.processEvidence(object.getEvidenceInfo(), locationObject.orientation,
                                locEvi.evidence().EV_TAG());
                    }
                }

                if (locationContext.evidence() != null) {
                    EvidenceInfo.processEvidence(object.getEvidenceInfo(), locationObject,
                            locationContext.evidence().EV_TAG());
                }

                sl.locations.add(locationObject);
            }
        }

        object.ccs.add(cc);
    }

    @Override
    public void exitCc_alternative_products(CcLineParser.Cc_alternative_productsContext ctx) {
        CcLineObject.CC cc = new CcLineObject.CC();
        cc.topic = CcLineObject.CCTopicEnum.ALTERNATIVE_PRODUCTS;
        CcLineObject.AlternativeProducts ap = new CcLineObject.AlternativeProducts();
        cc.object = ap;

        CcLineParser.Cc_alternative_products_eventContext ccAlternativeProductsEventContext =
                ctx.cc_alternative_products_event();
        List<CcLineParser.Cc_alternative_valueContext> ccAlternativeValueContexts1 =
                ccAlternativeProductsEventContext.cc_alternative_products_event_event().cc_alternative_value();

        for (CcLineParser.Cc_alternative_valueContext ccAlternativeValueContext : ccAlternativeValueContexts1) {
            String text = ccAlternativeValueContext.getText();
            ap.events.add(text);
        }

        ap.namedIsoforms = ccAlternativeProductsEventContext.cc_alternative_products_event_namedisoforms()
                .cc_alternative_value().getText();
        if (ccAlternativeProductsEventContext.cc_alternative_products_event_comment() != null) {

            Cc_properties_notes_level_2Context ccPropertiesNotesLevel2Context =
                    ccAlternativeProductsEventContext.cc_alternative_products_event_comment()
                            .cc_properties_notes_level_2();
            List<Cc_properties_note_text_level2_with_evContext> textLevel2WithEvContexts =
                    ccPropertiesNotesLevel2Context.cc_properties_note_text_level2_with_ev();
            for (Cc_properties_note_text_level2_with_evContext co : textLevel2WithEvContexts) {
                ap.comment.add(getEvidencedString(co));
            }

        }

        List<CcLineParser.Cc_alternative_products_nameContext> ccAlternativeProductsNameContexts =
                ctx.cc_alternative_products_name();
        for (CcLineParser.Cc_alternative_products_nameContext nameContext : ccAlternativeProductsNameContexts) {
            CcLineObject.AlternativeProductName name = new CcLineObject.AlternativeProductName();
            name.name = getEvidenceString(nameContext.cc_alternative_value_with_evidence());

            CcLineParser.Cc_alternative_products_isoidContext ccAlternativeProductsIsoidContext =
                    nameContext.cc_alternative_products_isoid();
            List<CcLineParser.Cc_alternative_valueContext> isos =
                    ccAlternativeProductsIsoidContext.cc_alternative_value();
            for (CcLineParser.Cc_alternative_valueContext ccAlternativeValueContext : isos) {
                name.isoId.add(ccAlternativeValueContext.getText());
            }

            CcLineParser.Cc_alternative_products_sequence_valueContext seqvalueContext =
                    nameContext.cc_alternative_products_sequence().cc_alternative_products_sequence_value();
            if (seqvalueContext.CC_AP_DISPLAYED() != null) {
                name.sequenceEnum = CcLineObject.AlternativeNameSequenceEnum.DISPLAYED;
            } else if (seqvalueContext.CC_AP_EXTERNAL() != null) {
                name.sequenceEnum = CcLineObject.AlternativeNameSequenceEnum.EXTERNAL;
            } else if (seqvalueContext.CC_AP_NOT_DESCRIBED() != null) {
                name.sequenceEnum = CcLineObject.AlternativeNameSequenceEnum.NOT_DESCRIBED;
            } else if (seqvalueContext.CC_AP_VALUE_UNSURE() != null) {
                name.sequenceEnum = CcLineObject.AlternativeNameSequenceEnum.UNSURE;
            } else if (seqvalueContext.CC_AP_DESCRIBED() != null) {
                name.sequenceEnum = CcLineObject.AlternativeNameSequenceEnum.DESCRIBED;
            } else if (seqvalueContext.cc_alternative_products_sequence_value_identifiers() != null) {
                List<TerminalNode> terminalNodes =
                        seqvalueContext.cc_alternative_products_sequence_value_identifiers().CC_AP_FEATURE_IDENTIFIER();
                for (TerminalNode terminalNode : terminalNodes) {
                    String text = terminalNode.getText();
                    name.sequenceFTId.add(text);
                }
            }

            CcLineParser.Cc_alternative_products_synonymsContext synContext =
                    nameContext.cc_alternative_products_synonyms();
            if (synContext != null) {
                List<CcLineParser.Cc_alternative_value_with_evidenceContext> ccAlternativeValueContexts =
                        synContext.cc_alternative_value_with_evidence();
                for (CcLineParser.Cc_alternative_value_with_evidenceContext ccAlternativeValueContext : ccAlternativeValueContexts) {
                    name.synNames.add(getEvidenceString(ccAlternativeValueContext));
                }
            }

            CcLineParser.Cc_alternative_products_noteContext ccAlternativeProductsNoteContext =
                    nameContext.cc_alternative_products_note();
            if (ccAlternativeProductsNoteContext != null) {
                Cc_properties_notes_level_2Context ccPropertiesNotesLevel2Context =
                        ccAlternativeProductsNoteContext.cc_properties_notes_level_2();
                List<Cc_properties_note_text_level2_with_evContext> textLevel2WithEvContexts =
                        ccPropertiesNotesLevel2Context.cc_properties_note_text_level2_with_ev();
                for (Cc_properties_note_text_level2_with_evContext ccc : textLevel2WithEvContexts) {
                    name.note.add(getEvidencedString(ccc));
                }
            }
            ap.names.add(name);
        }

        object.ccs.add(cc);
    }

    @Override
    public void exitCc_disease(CcLineParser.Cc_diseaseContext ctx) {

        CcLineObject.CC cc = new CcLineObject.CC();
        cc.topic = CcLineObject.CCTopicEnum.DISEASE;
        CcLineObject.Disease dd = new CcLineObject.Disease();
        cc.object = dd;

        if (ctx.cc_disease_name_abbr() != null) {
            String text = ctx.cc_disease_name_abbr().getText();
            if ((text.endsWith(")")) && (text.indexOf("(") > 0)) {
                int lastS = text.lastIndexOf(" ");
                if (lastS < 0) {
                    lastS = 0;
                }
                int lastB = text.indexOf("(", lastS);
                if (lastB < 0) {
                    lastB = text.indexOf("(");
                }
                dd.name = text.substring(0, lastB).trim();
                dd.abbr = text.substring(lastB + 1, text.length() - 1);
            } else
                dd.name = text;

            CcLineParser.Cc_disease_mimContext ccDiseaseAbbrMinContext = ctx.cc_disease_mim();
            String text1 = ccDiseaseAbbrMinContext.getText();
            dd.mim = text1.substring(5, text1.length() - 2);
        }

        if (ctx.cc_disease_description() != null) {
            dd.description = ctx.cc_disease_description().cc_disease_text_d().getText();

            if (ctx.cc_disease_description().evidence() != null) {
                EvidenceInfo.processEvidence(object.evidenceInfo, dd.description,
                        ctx.cc_disease_description().evidence().EV_TAG());
            }
        }

        if (ctx.cc_disease_note() != null) {

            Cc_common_textsContext ccCommonTextsContext = ctx.cc_disease_note().cc_common_texts();

            List<Cc_common_text_with_evContext> ccCommonTextWithEvContexts =
                    ccCommonTextsContext.cc_common_text_with_ev();

            for (Cc_common_text_with_evContext ftCtx : ccCommonTextWithEvContexts) {
                dd.note.add(getEvidencedString(ftCtx));
            }
        }

        object.ccs.add(cc);
    }

    @Override
    public void exitCc_cofactor(CcLineParser.Cc_cofactorContext ctx) {
        CcLineObject.CC cc = new CcLineObject.CC();
        cc.topic = CcLineObject.CCTopicEnum.COFACTOR;
        CcLineObject.StructuredCofactor dd = new CcLineObject.StructuredCofactor();
        cc.object = dd;
        Cc_cofactor_moleculeContext molecule = ctx.cc_cofactor_molecule();
        if (molecule != null) {

            String word = molecule.getText();
            dd.molecule = word;
        }
        Cc_cofactor_noteContext note = ctx.cc_cofactor_note();
        if (note != null) {
            Cc_properties_notesContext ccPropertiesNotesContext = note.cc_properties_notes();
            List<Cc_properties_note_text_with_evContext> ccPropertiesTextWithEvContexts =
                    ccPropertiesNotesContext.cc_properties_note_text_with_ev();
            for (Cc_properties_note_text_with_evContext ccPropertiesTextWithEvContext : ccPropertiesTextWithEvContexts) {
                dd.note.add(getEvidencedString(ccPropertiesTextWithEvContext));
            }
        }
        Cc_cofactor_linesContext lineCtx = ctx.cc_cofactor_lines();
        if (lineCtx != null) {
            List<Cc_cofactor_lineContext> lines = lineCtx.cc_cofactor_line();
            if (!lines.isEmpty()) {
                dd.cofactors = new ArrayList<>();
                for (Cc_cofactor_lineContext line : lines) {
                    CcLineObject.CofactorItem item = new CcLineObject.CofactorItem();
                    item.name = line.cc_cofactor_name().cc_properties_text_level2().getText();
                    item.xref = line.cc_cofactor_xref().cc_properties_text_level2().getText();
                    CcLineParser.Cc_cofactor_evidenceContext evidenceContext = line.cc_cofactor_evidence();
                    if (evidenceContext != null) {
                        EvidenceInfo.processEvidence(object.getEvidenceInfo(), item, evidenceContext.EV_TAG());
                    }
                    dd.cofactors.add(item);
                }
            }
        }
        object.ccs.add(cc);
    }

    @Override
    public void exitCc_catalytic_activity(CcLineParser.Cc_catalytic_activityContext ctx) {
    	  CcLineObject.CC cc = new CcLineObject.CC();
          cc.topic = CcLineObject.CCTopicEnum.CATALYTIC_ACTIVITY;
          CcLineObject.CatalyticActivity dd = new CcLineObject.CatalyticActivity();
          cc.object = dd;
         
          Cc_cat_act_reaction_lineContext reactionLine = ctx.cc_cat_act_reaction_line();
          if (reactionLine != null) {
        	  CcLineObject.CAReaction reaction =new CcLineObject.CAReaction();
        	  dd.reaction =reaction;
        	  reaction.name = reactionLine.cc_cat_act_reaction().cc_properties_text_level2().getText();
        	  if(reactionLine.cc_cat_act_xref() !=null)
        		  reaction.xref =reactionLine.cc_cat_act_xref().cc_properties_text_level2().getText();
        	  if(reactionLine.cc_cat_act_ec() !=null) {
        		  reaction.ec = reactionLine.cc_cat_act_ec().cc_properties_text_level2().getText();		  
        	  }
        	  CcLineParser.Cc_cat_act_evidenceContext evidenceContext =reactionLine.cc_cat_act_evidence();
        	  if (evidenceContext != null) {
                  EvidenceInfo.processEvidence(object.getEvidenceInfo(), reaction, evidenceContext.EV_TAG());
              }
          }
          CcLineParser.Cc_cat_act_pd_linesContext lineCtx = ctx.cc_cat_act_pd_lines();
          if (lineCtx != null) {
              List<Cc_cat_act_pd_lineContext> lines = lineCtx.cc_cat_act_pd_line();
              if (!lines.isEmpty()) {
                  dd.physiologicalDirections = new ArrayList<>();
                  for (Cc_cat_act_pd_lineContext line : lines) {
                      CcLineObject.CAPhysioDirection item = new CcLineObject.CAPhysioDirection();
                      item.name = line.cc_cat_act_pd().cc_properties_text_level2().getText();
                      item.xref = line.cc_cat_act_pd_xref().cc_properties_text_level2().getText();
                      CcLineParser.Cc_cat_act_pd_evidenceContext evidenceContext = line.cc_cat_act_pd_evidence();
                      if (evidenceContext != null) {
                          EvidenceInfo.processEvidence(object.getEvidenceInfo(), item, evidenceContext.EV_TAG());
                      }
                      dd.physiologicalDirections.add(item);
                  }
              }
          }
          object.ccs.add(cc);
    }
    @Override
    public CcLineObject getObject() {
        return object;
    }

}

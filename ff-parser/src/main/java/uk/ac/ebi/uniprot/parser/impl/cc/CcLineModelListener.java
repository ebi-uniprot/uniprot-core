package uk.ac.ebi.uniprot.parser.impl.cc;

import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.TerminalNode;

import uk.ac.ebi.uniprot.antlr.CcLineParser;
import uk.ac.ebi.uniprot.antlr.CcLineParser.*;
import uk.ac.ebi.uniprot.antlr.CcLineParserBaseListener;
import uk.ac.ebi.uniprot.antlr.TextHelper;
import uk.ac.ebi.uniprot.parser.ParseTreeObjectExtractor;

import uk.ac.ebi.uniprot.parser.impl.EvidenceInfo;
import uk.ac.ebi.uniprot.parser.impl.cc.CcLineObject.EvidencedString;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: wudong
 * Date: 08/08/13
 * Time: 12:26
 * To change this template use File | Settings | File Templates.
 */
public class CcLineModelListener extends CcLineParserBaseListener implements ParseTreeObjectExtractor<CcLineObject> {

    private CcLineObject object;


    private EvidencedString getEvidencedString(CcLineParser.Cc_properties_text_level2_with_evContext context) {
        String text = context.cc_properties_text_level2().getText().trim();
        List<String> evidence = context.evidence() == null ? null : EvidenceInfo.processEvidence(context.evidence().EV_TAG());
        return EvidencedString.get(text, evidence);
    }

    private EvidencedString getEvidencedString(CcLineParser.Cc_properties_note_text_level2_with_evContext context) {
        String text = context.cc_properties_note_text_level2().getText().trim();
        List<String> evidence = context.evidence() == null ? null : EvidenceInfo.processEvidence(context.evidence().EV_TAG());
        return EvidencedString.get(text, evidence);
    }

    private EvidencedString getEvidencedString(CcLineParser.Cc_properties_note_text_with_evContext context) {
        String text = context.cc_properties_note_text().getText().trim();
        List<String> evidence = context.evidence() == null ? null : EvidenceInfo.processEvidence(context.evidence().EV_TAG());
        return EvidencedString.get(text, evidence);
    }


    private EvidencedString getEvidencedString(CcLineParser.Cc_common_text_with_evContext context) {
        String text = context.cc_common_text().getText().trim();
        List<String> evidence = context.evidence() == null ? null : EvidenceInfo.processEvidence(context.evidence().EV_TAG());
        return EvidencedString.get(text, evidence);
    }

    private EvidencedString getEvidenceString(Cc_alternative_value_with_evidenceContext context){
    	  String text = context.cc_alternative_value().getText().trim();
          List<String> evidence = context.evidence() == null ? null : EvidenceInfo.processEvidence(context.evidence().EV_TAG());
          return EvidencedString.get(text, evidence);
    }

    @Override
    public void exitCc_common(@NotNull CcLineParser.Cc_commonContext ctx) {
        CcLineObject.CC ccCommon = new CcLineObject.CC();
        TerminalNode terminalNode = ctx.CC_TOPIC_COMMON();
        ccCommon.topic = CcLineObject.CCTopicEnum.fromSting(terminalNode.getText());

        CcLineObject.FreeText ftext = new CcLineObject.FreeText();

        Cc_common_textsContext cc_common_textsContext = ctx.cc_common_texts();
        List<Cc_common_text_with_evContext> cc_common_text_with_evContexts = cc_common_textsContext.cc_common_text_with_ev();

        for (Cc_common_text_with_evContext ftCtx : cc_common_text_with_evContexts) {
            ftext.texts.add(getEvidencedString(ftCtx));
        }

        ccCommon.object = ftext;
        object.ccs.add(ccCommon);
    }

    @Override
    public void enterCc_cc(@NotNull CcLineParser.Cc_ccContext ctx) {
        object = new CcLineObject();
    }

    @Override
    public void enterCc_lines(@NotNull CcLineParser.Cc_linesContext ctx) {
        object = new CcLineObject();
    }

    @Override
    public void exitCc_sequence_caution(@NotNull CcLineParser.Cc_sequence_cautionContext ctx) {
        CcLineObject.CC cc = new CcLineObject.CC();
        cc.topic = CcLineObject.CCTopicEnum.SEQUENCE_CAUTION;

        CcLineObject.SequenceCaution sc = new CcLineObject.SequenceCaution();
        cc.object = sc;
        object.ccs.add(cc);

        List<CcLineParser.Cc_sequence_caution_lineContext>
                cc_sequence_caution_lineContexts = ctx.cc_sequence_caution_line();

        for (CcLineParser.Cc_sequence_caution_lineContext lineContext : cc_sequence_caution_lineContexts) {
            CcLineObject.SequenceCautionObject object1 = new CcLineObject.SequenceCautionObject();
            sc.sequenceCautionObjects.add(object1);

            CcLineParser.Cc_sequence_caution_sequenceContext cc_sequence_caution_sequenceContext = lineContext.cc_sequence_caution_sequence();
            object1.sequence = cc_sequence_caution_sequenceContext.CC_SC_SEQUENCE_TEXT().getText();
            if (cc_sequence_caution_sequenceContext.evidence() != null) {
                EvidenceInfo.processEvidence(object.getEvidenceInfo(), object1.sequence, cc_sequence_caution_sequenceContext.evidence().EV_TAG());
            }

            CcLineParser.Cc_sequence_caution_typeContext cc_sequence_caution_typeContext = lineContext.cc_sequence_caution_type();
            object1.type = CcLineObject.SequenceCautionType.fromSting(cc_sequence_caution_typeContext.CC_SC_TYPE_VALUE().getText());

            CcLineParser.Cc_sequence_caution_positionContext cc_sequence_caution_positionContext = lineContext.cc_sequence_caution_position();
            if (cc_sequence_caution_positionContext != null) {
                CcLineParser.Cc_sequence_caution_position_valueContext ccc = cc_sequence_caution_positionContext.cc_sequence_caution_position_value();

                List<TerminalNode> integer = ccc.INTEGER();
                for (TerminalNode terminalNode : integer) {
                    object1.positions.add(Integer.parseInt(terminalNode.getText()));
                }

                TerminalNode terminalNode = ccc.CC_SC_P_VALUE();
                if (terminalNode != null) {
                    object1.positionValue = terminalNode.getText();
                }
                ;
            }

            CcLineParser.Cc_sequence_caution_noteContext cc_sequence_caution_noteContext = lineContext.cc_sequence_caution_note();
            if (cc_sequence_caution_noteContext != null) {
                object1.note = cc_sequence_caution_noteContext.CC_SC_NOTE_TEXT().getText();
            }

            CcLineParser.Cc_sequence_caution_evidenceContext ev = lineContext.cc_sequence_caution_evidence();
            if (ev != null) {
                EvidenceInfo.processEvidence(object.getEvidenceInfo(), object1, ev.EV_TAG());
            }
        }
    }

    @Override
    public void exitCc_mass_spectrometry(@NotNull CcLineParser.Cc_mass_spectrometryContext ctx) {
        CcLineObject.CC cc = new CcLineObject.CC();
        cc.topic = CcLineObject.CCTopicEnum.MASS_SPECTROMETRY;

        CcLineObject.MassSpectrometry ms = new CcLineObject.MassSpectrometry();
        cc.object = ms;
        object.ccs.add(cc);

        CcLineParser.Cc_mass_spectrometry_massContext cc_mass_spectrometry_massContext = ctx.cc_mass_spectrometry_mass();
        String text = cc_mass_spectrometry_massContext.CC_MS_V_NUMBER().getText();
        ms.mass = Float.parseFloat(text);

        CcLineParser.Cc_mass_spectrometry_mass_errorContext cc_mass_spectrometry_mass_errorContext = ctx.cc_mass_spectrometry_mass_error();
        if (cc_mass_spectrometry_mass_errorContext != null) {
            String text1 = cc_mass_spectrometry_mass_errorContext.CC_MS_V_NUMBER().getText();
            ms.mass_error = Float.parseFloat(text1);
        }

        CcLineParser.Cc_mass_spectrometry_mass_methodContext cc_mass_spectrometry_mass_methodContext = ctx.cc_mass_spectrometry_mass_method();
        String text1 = cc_mass_spectrometry_mass_methodContext.cc_mass_spectrometry_value().getText();
        ms.method = text1;

        CcLineParser.Cc_mass_spectrometry_mass_rangeContext rangeContext = ctx.cc_mass_spectrometry_mass_range();

        List<CcLineParser.Cc_mass_spectrometry_mass_range_valueContext> range_valueContexts = rangeContext.cc_mass_spectrometry_mass_range_value();
        for (CcLineParser.Cc_mass_spectrometry_mass_range_valueContext range_valueContext : range_valueContexts) {
            CcLineParser.Cc_mass_spectrometry_mass_range_value_valueContext vv1 =
                    range_valueContext.cc_mass_spectrometry_mass_range_value_value(0);

            CcLineParser.Cc_mass_spectrometry_mass_range_value_valueContext vv2 =
                    range_valueContext.cc_mass_spectrometry_mass_range_value_value(1);

            CcLineObject.MassSpectrometryRange range = new CcLineObject.MassSpectrometryRange();

            if (vv1.INTEGER() != null) {
                range.start = Integer.parseInt(vv1.INTEGER().getText());
            } else {
                range.start_unknown = true;
            }

            if (vv2.INTEGER() != null) {
                range.end = Integer.parseInt(vv2.INTEGER().getText());
            } else {
                range.end_unknown = true;
            }

            CcLineParser.Cc_mass_spectrometry_mass_range_isoformContext isoform = range_valueContext.cc_mass_spectrometry_mass_range_isoform();
            if (isoform != null) {
                String text2 = isoform.CC_MS_R_V_ISO().getText();
                range.range_isoform = text2.replace("\nCC       ", "");
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

            //also put into the sources to maintain code compatibility
            List<TerminalNode> terminalNodes = sourceContext.EV_TAG();
            for (TerminalNode node : terminalNodes) {
                ms.sources.add(node.getText());
            }
        }
    }

    @Override
    public void exitCc_web_resource(@NotNull CcLineParser.Cc_web_resourceContext ctx) {
        CcLineObject.CC cc = new CcLineObject.CC();
        cc.topic = CcLineObject.CCTopicEnum.WEB_RESOURCE;

        CcLineObject.WebResource wr = new CcLineObject.WebResource();
        cc.object = wr;
        object.ccs.add(cc);

        CcLineParser.Cc_web_resource_nameContext cc_web_resource_nameContext = ctx.cc_web_resource_name();
        wr.name = cc_web_resource_nameContext.cc_properties_text().getText();

        CcLineParser.Cc_web_resource_urlContext cc_web_resource_urlContext = ctx.cc_web_resource_url();
        if (cc_web_resource_urlContext != null) {
            String text = cc_web_resource_urlContext.cc_properties_text().getText();
            //url is in the format of "http://..."
            wr.url = text.substring(1, text.length() - 1);
        }

        CcLineParser.Cc_web_resource_noteContext cc_web_resource_noteContext = ctx.cc_web_resource_note();
        if (cc_web_resource_noteContext != null) {
            wr.note = cc_web_resource_noteContext.cc_properties_text().getText();
        }
    }

    @Override
    public void exitCc_interaction(@NotNull CcLineParser.Cc_interactionContext ctx) {
        CcLineObject.CC cc = new CcLineObject.CC();
        cc.topic = CcLineObject.CCTopicEnum.INTERACTION;

        CcLineObject.Interaction ir = new CcLineObject.Interaction();
        cc.object = ir;
        object.ccs.add(cc);

        //CC       {{SP_Ac:identifier[ (xeno)]}|Self}; NbExp=n; IntAct=IntAct_Protein_Ac, IntAct_Protein_Ac;
        List<CcLineParser.Cc_interaction_lineContext> cc_interaction_lineContexts = ctx.cc_interaction_line();
        for (CcLineParser.Cc_interaction_lineContext io : cc_interaction_lineContexts) {
            CcLineObject.InteractionObject interactionObject = new CcLineObject.InteractionObject();
            CcLineParser.Cc_interaction_intactContext cc_interaction_intactContext = io.cc_interaction_intact();
            interactionObject.firstId = cc_interaction_intactContext.CC_IR_AC(0).getText();
            interactionObject.secondId = cc_interaction_intactContext.CC_IR_AC(1).getText();

            TerminalNode integer = io.cc_interaction_nbexp().INTEGER();
            interactionObject.nbexp = Integer.parseInt(integer.getText());

            CcLineParser.Cc_interaction_spContext cc_interaction_spContext = io.cc_interaction_sp();
            if (cc_interaction_spContext.CC_IR_SELF() != null) {
                interactionObject.isSelf = true;
            } else {
                interactionObject.spAc = cc_interaction_spContext.CC_IR_AC().getText();
                if (cc_interaction_spContext.DASH() != null) {
                    interactionObject.gene = "-";
                } else {
                    interactionObject.gene = cc_interaction_spContext.cc_interaction_gene().getText();
                }

                if (cc_interaction_spContext.CC_IR_XENO() != null) {
                    interactionObject.xeno = true;
                }
            }

            ir.interactions.add(interactionObject);
        }
    }

    @Override
    public void exitCc_biophyiochemical(@NotNull CcLineParser.Cc_biophyiochemicalContext ctx) {
        CcLineObject.CC cc = new CcLineObject.CC();
        cc.topic = CcLineObject.CCTopicEnum.BIOPHYSICOCHEMICAL_PROPERTIES;
        CcLineObject.BiophysicochemicalProperties bp = new CcLineObject.BiophysicochemicalProperties();
        cc.object = bp;
        object.ccs.add(cc);

        List<CcLineParser.Cc_biophyiochemical_propertiesContext> ctx2 = ctx.cc_biophyiochemical_properties();
        for (CcLineParser.Cc_biophyiochemical_propertiesContext cc_biophyiochemical_propertiesContext : ctx2) {
            processBiophysicalProperties(cc_biophyiochemical_propertiesContext, bp);
        }
    }


    private void processBiophysicalProperties(@NotNull CcLineParser.Cc_biophyiochemical_propertiesContext ctx, CcLineObject.BiophysicochemicalProperties bp) {
        if (ctx.cc_biophyiochemical_absorption() != null) {
            //	TerminalNode terminalNode = ctx.cc_biophyiochemical_absorption().cc_biophyiochemical_absorption_bas()
            Cc_biophyiochemical_absorption_basContext absBasContext = ctx.cc_biophyiochemical_absorption().cc_biophyiochemical_absorption_bas();
            CcLineParser.Cc_properties_text_level2_with_evContext textWithEv = absBasContext.cc_properties_text_level2_with_ev();
            String text = textWithEv.cc_properties_text_level2().getText();
            String absValue = text;

            if (text.startsWith("~")) {
                bp.bsorption_abs_approximate = true;
                absValue = text.substring(1);
            }

            CcLineParser.EvidenceContext evidence1 = textWithEv.evidence();
            bp.bsorption_abs = EvidencedString.get(absValue, evidence1 == null ? null : EvidenceInfo.processEvidence(evidence1.EV_TAG()));

            Cc_biophyiochemical_absorption_noteContext absNotContext = ctx.cc_biophyiochemical_absorption().cc_biophyiochemical_absorption_note();
            if (absNotContext != null) {
                Cc_properties_notes_level_2Context cc_properties_notes_level_2Context = absNotContext.cc_properties_notes_level_2();
                List<CcLineParser.Cc_properties_note_text_level2_with_evContext> textWithEvs = cc_properties_notes_level_2Context.cc_properties_note_text_level2_with_ev();
                for (CcLineParser.Cc_properties_note_text_level2_with_evContext c : textWithEvs) {
                    bp.bsorption_note.add(getEvidencedString(c));
                }
            }
        }

        CcLineParser.Cc_biophyiochemical_kineticContext kineticContext = ctx.cc_biophyiochemical_kinetic();
        if (kineticContext != null) {
            List<CcLineParser.Cc_biophyiochemical_kinetic_kmContext> cc_biophyiochemical_kinetic_kmContexts = kineticContext.cc_biophyiochemical_kinetic_km();
            for (CcLineParser.Cc_biophyiochemical_kinetic_kmContext km : cc_biophyiochemical_kinetic_kmContexts) {
                CcLineParser.Cc_properties_text_level2_with_evContext c = km.cc_properties_text_level2_with_ev();
                bp.kms.add(getEvidencedString(c));
            }

            List<CcLineParser.Cc_biophyiochemical_kinetic_bpmaxContext> maxcontext = kineticContext.cc_biophyiochemical_kinetic_bpmax();
            for (CcLineParser.Cc_biophyiochemical_kinetic_bpmaxContext c : maxcontext) {
                CcLineParser.Cc_properties_text_level2_with_evContext cc = c.cc_properties_text_level2_with_ev();
                bp.vmaxs.add(getEvidencedString(cc));
            }

            CcLineParser.Cc_biophyiochemical_kinetic_noteContext noteContext = kineticContext.cc_biophyiochemical_kinetic_note();
            if (noteContext != null) {
                Cc_properties_notes_level_2Context cc_properties_notes_level_2Context = noteContext.cc_properties_notes_level_2();
                List<CcLineParser.Cc_properties_note_text_level2_with_evContext> text_level2_with_evContexts = cc_properties_notes_level_2Context.cc_properties_note_text_level2_with_ev();
                for (CcLineParser.Cc_properties_note_text_level2_with_evContext cc : text_level2_with_evContexts) {
                    bp.kp_note.add(getEvidencedString(cc));
                }
            }
        }

        if (ctx.cc_biophyiochemical_redox() != null) {
            Cc_properties_notes_level_2Context c = ctx.cc_biophyiochemical_redox().cc_properties_notes_level_2();
            List<CcLineParser.Cc_properties_note_text_level2_with_evContext> textWithEvs = c.cc_properties_note_text_level2_with_ev();
            for (CcLineParser.Cc_properties_note_text_level2_with_evContext cc : textWithEvs) {
                bp.rdox_potential.add(getEvidencedString(cc));
            }
            //bp.rdox_potential.add(getEvidencedString(c));
        }

        if (ctx.cc_biophyiochemical_ph() != null) {
            CcLineParser.Cc_properties_notes_level_2Context c = ctx.cc_biophyiochemical_ph().cc_properties_notes_level_2();
            List<CcLineParser.Cc_properties_note_text_level2_with_evContext> textWithEvs = c.cc_properties_note_text_level2_with_ev();
            for (CcLineParser.Cc_properties_note_text_level2_with_evContext cc : textWithEvs) {
            	 bp.ph_dependence.add(getEvidencedString(cc));
            }
    //        bp.ph_dependence.add(getEvidencedString(c));
        }

        if (ctx.cc_biophyiochemical_temperature() != null) {
            Cc_properties_notes_level_2Context cc_properties_notes_level_2Context = ctx.cc_biophyiochemical_temperature().cc_properties_notes_level_2();
            List<Cc_properties_note_text_level2_with_evContext> text_level2_with_evContexts = cc_properties_notes_level_2Context.cc_properties_note_text_level2_with_ev();
            for (Cc_properties_note_text_level2_with_evContext text_level2_with_evContext : text_level2_with_evContexts) {
                bp.temperature_dependence.add(getEvidencedString(text_level2_with_evContext));
            }
        }
    }

    @Override
    public void exitCc_rna_editing(@NotNull CcLineParser.Cc_rna_editingContext ctx) {

        CcLineObject.CC cc = new CcLineObject.CC();

        cc.topic = CcLineObject.CCTopicEnum.RNA_EDITING;
        CcLineObject.RnaEditing re = new CcLineObject.RnaEditing();
        cc.object = re;
        CcLineParser.Cc_rna_editing_positionContext positionContext = ctx.cc_rna_edigint_modified_position().cc_rna_editing_position();
        if (positionContext != null) {
            List<Cc_rna_editing_single_positionContext> singlePosCtxs = positionContext.cc_rna_editing_single_position();
            for (Cc_rna_editing_single_positionContext sPosCtx : singlePosCtxs) {
                String text = sPosCtx.INTEGER().getText();
                re.locations.add(Integer.parseInt(text));
                if (sPosCtx.evidence() != null) {
                    EvidenceInfo.processEvidence(object.getEvidenceInfo(), text, sPosCtx.evidence().EV_TAG());
                }
            }
        }
//		if (positionContext != null) {
//			List<TerminalNode> integer = positionContext.INTEGER();
//			for (TerminalNode terminalNode : integer) {
//				String text = terminalNode.getText();
//				re.locations.add(Integer.parseInt(text));
//				EvidenceInfo.processEvidence(object.getEvidenceInfo(), text, positionContext.evidence().EV_TAG());
//			}
//		}
        else if ((ctx.cc_rna_edigint_modified_position().cc_re_position_undetermined() != null)
                && (ctx.cc_rna_edigint_modified_position().cc_re_position_undetermined().CC_RE_MODIFIED_POSITION_UNDETERMINED() != null)) {
            re.locationEnum = CcLineObject.RnaEditingLocationEnum.Undetermined;
            if (ctx.cc_rna_edigint_modified_position().cc_re_position_undetermined().evidence() != null) {
                EvidenceInfo.processEvidence(object.getEvidenceInfo(), re.locationEnum, ctx.cc_rna_edigint_modified_position().cc_re_position_undetermined().evidence().EV_TAG());
            }
        } else if ((ctx.cc_rna_edigint_modified_position().cc_re_position_not_applicable() != null)
                && (ctx.cc_rna_edigint_modified_position().cc_re_position_not_applicable().CC_RE_MODIFIED_POSITION_NOT_APPLICABLE() != null)) {
            re.locationEnum = CcLineObject.RnaEditingLocationEnum.Not_applicable;
            if (ctx.cc_rna_edigint_modified_position().cc_re_position_not_applicable().evidence() != null) {
                EvidenceInfo.processEvidence(object.getEvidenceInfo(), re.locationEnum, ctx.cc_rna_edigint_modified_position().cc_re_position_not_applicable().evidence().EV_TAG());
            }
        }

        if (ctx.cc_rna_edigint_note() != null) {
            Cc_properties_notesContext cc_properties_notesContext = ctx.cc_rna_edigint_note().cc_properties_notes();
            List<Cc_properties_note_text_with_evContext> cc_properties_text_with_evContexts = cc_properties_notesContext.cc_properties_note_text_with_ev();
            for (Cc_properties_note_text_with_evContext c : cc_properties_text_with_evContexts) {
                re.note.add(getEvidencedString(c));
            }
        }
        object.ccs.add(cc);
    }

    @Override
    public void exitCc_subcellular_location(@NotNull CcLineParser.Cc_subcellular_locationContext ctx) {

        CcLineObject.CC cc = new CcLineObject.CC();

        cc.topic = CcLineObject.CCTopicEnum.SUBCELLULAR_LOCATION;
        CcLineObject.SubcullarLocation sl = new CcLineObject.SubcullarLocation();
        cc.object = sl;

        CcLineParser.Cc_subcellular_location_moleculeContext cc_subcellular_location_moleculeContext = ctx.cc_subcellular_location_molecule();
        if (cc_subcellular_location_moleculeContext != null) {
            String word = cc_subcellular_location_moleculeContext.cc_subcellular_words().getText();
            sl.molecule = word;
        }

        CcLineParser.Cc_subcellular_noteContext cc_subcellular_noteContext = ctx.cc_subcellular_note();
        if (cc_subcellular_noteContext != null) {
            Cc_common_textsContext cc_common_textsContext = cc_subcellular_noteContext.cc_common_texts();
            List<Cc_common_text_with_evContext> cc_common_text_with_evContexts = cc_common_textsContext.cc_common_text_with_ev();

            for (Cc_common_text_with_evContext ftCtx : cc_common_text_with_evContexts) {
                sl.note.add(getEvidencedString(ftCtx));
            }
        }

        CcLineParser.Cc_subcellular_location_sectionContext cc_subcellular_location_sectionContext = ctx.cc_subcellular_location_section();
        if (cc_subcellular_location_sectionContext != null) {
            List<CcLineParser.Cc_subcellular_location_location_with_evidenceContext> cc_subcellular_location_location_with_evidenceContexts = cc_subcellular_location_sectionContext.cc_subcellular_location_location_with_evidence();
            for (CcLineParser.Cc_subcellular_location_location_with_evidenceContext locationContext : cc_subcellular_location_location_with_evidenceContexts) {
                CcLineObject.LocationObject locationObject = new CcLineObject.LocationObject();

                CcLineParser.Cc_subcellular_location_locationContext cc_subcellular_location_locationContext = locationContext.cc_subcellular_location_location();

                List<CcLineParser.Cc_subcellular_location_value_with_evidenceContext> valueContexts = cc_subcellular_location_locationContext.cc_subcellular_location_value_with_evidence();
                int size = valueContexts.size();
                if (size >= 1) {
                    CcLineParser.Cc_subcellular_location_value_with_evidenceContext loc_evi = valueContexts.get(0);
                    CcLineParser.Cc_subcellular_location_valueContext cc_subcellular_location_valueContext = loc_evi.cc_subcellular_location_value();

                    locationObject.subcellular_location = new CcLineObject.LocationValue();

                    locationObject.subcellular_location.value = cc_subcellular_location_valueContext.cc_subcellular_words().getText();

                    if (cc_subcellular_location_valueContext.cc_subcellular_location_flag() != null) {
                        String text = cc_subcellular_location_valueContext.cc_subcellular_location_flag().CC_SL_FLAG().getText();
                        String replace = text.replace("\nCC       ", " ");
                        locationObject.subcellular_location.flag = CcLineObject.LocationFlagEnum.fromSting(replace.substring(1, replace.length() - 1));
                    }

                    if (loc_evi.evidence() != null) {
                        EvidenceInfo.processEvidence(object.getEvidenceInfo(), locationObject.subcellular_location, loc_evi.evidence().EV_TAG());
                    }
                }

                if (size >= 2) {

                    CcLineParser.Cc_subcellular_location_value_with_evidenceContext loc_evi = valueContexts.get(1);
                    CcLineParser.Cc_subcellular_location_valueContext cc_subcellular_location_valueContext = loc_evi.cc_subcellular_location_value();

                    locationObject.topology = new CcLineObject.LocationValue();
                    locationObject.topology.value = cc_subcellular_location_valueContext.cc_subcellular_words().getText();

                    if (cc_subcellular_location_valueContext.cc_subcellular_location_flag() != null) {
                        String text = cc_subcellular_location_valueContext.cc_subcellular_location_flag().CC_SL_FLAG().getText();
                        String replace = text.replace("\nCC       ", " ");
                        locationObject.topology.flag = CcLineObject.LocationFlagEnum.fromSting(replace.substring(1, replace.length() - 1));
                    }

                    if (loc_evi.evidence() != null) {
                        EvidenceInfo.processEvidence(object.getEvidenceInfo(), locationObject.topology, loc_evi.evidence().EV_TAG());
                    }
                }

                if (size >= 3) {
                    CcLineParser.Cc_subcellular_location_value_with_evidenceContext loc_evi = valueContexts.get(2);
                    CcLineParser.Cc_subcellular_location_valueContext cc_subcellular_location_valueContext = loc_evi.cc_subcellular_location_value();

                    locationObject.orientation = new CcLineObject.LocationValue();
                    locationObject.orientation.value = cc_subcellular_location_valueContext.cc_subcellular_words().getText();

                    if (cc_subcellular_location_valueContext.cc_subcellular_location_flag() != null) {
                        String text = cc_subcellular_location_valueContext.cc_subcellular_location_flag().CC_SL_FLAG().getText();
                        String replace = text.replace("\nCC       ", " ");
                        locationObject.orientation.flag = CcLineObject.LocationFlagEnum.fromSting(replace.substring(1, replace.length() - 1));
                    }

                    if (loc_evi.evidence() != null) {
                        EvidenceInfo.processEvidence(object.getEvidenceInfo(), locationObject.orientation, loc_evi.evidence().EV_TAG());
                    }
                }

                if (locationContext.evidence() != null) {
                    EvidenceInfo.processEvidence(object.getEvidenceInfo(), locationObject, locationContext.evidence().EV_TAG());
                }

                sl.locations.add(locationObject);
            }
        }

        object.ccs.add(cc);
    }

    @Override
    public void exitCc_alternative_products(@NotNull CcLineParser.Cc_alternative_productsContext ctx) {
        CcLineObject.CC cc = new CcLineObject.CC();
        cc.topic = CcLineObject.CCTopicEnum.ALTERNATIVE_PRODUCTS;
        CcLineObject.AlternativeProducts ap = new CcLineObject.AlternativeProducts();
        cc.object = ap;

        CcLineParser.Cc_alternative_products_eventContext cc_alternative_products_eventContext = ctx.cc_alternative_products_event();
        List<CcLineParser.Cc_alternative_valueContext> cc_alternative_valueContexts1 =
                cc_alternative_products_eventContext.cc_alternative_products_event_event().cc_alternative_value();

        for (CcLineParser.Cc_alternative_valueContext cc_alternative_valueContext : cc_alternative_valueContexts1) {
            String text = cc_alternative_valueContext.getText();
            ap.events.add(text);
        }

        ap.namedIsoforms = cc_alternative_products_eventContext.cc_alternative_products_event_namedisoforms().cc_alternative_value().getText();
        if (cc_alternative_products_eventContext.cc_alternative_products_event_comment() != null) {

            Cc_properties_notes_level_2Context cc_properties_notes_level_2Context =
                    cc_alternative_products_eventContext.cc_alternative_products_event_comment().cc_properties_notes_level_2();
            List<Cc_properties_note_text_level2_with_evContext> text_level2_with_evContexts = cc_properties_notes_level_2Context.cc_properties_note_text_level2_with_ev();
            for (Cc_properties_note_text_level2_with_evContext co : text_level2_with_evContexts) {
                ap.comment.add(getEvidencedString(co));
            }

        }

        List<CcLineParser.Cc_alternative_products_nameContext> cc_alternative_products_nameContexts = ctx.cc_alternative_products_name();
        for (CcLineParser.Cc_alternative_products_nameContext nameContext : cc_alternative_products_nameContexts) {
            CcLineObject.AlternativeProductName name = new CcLineObject.AlternativeProductName();
            name.name =getEvidenceString(nameContext.cc_alternative_value_with_evidence());

//            		nameContext.cc_alternative_value_with_evidence().cc_alternative_value().getText();
//            if (nameContext.cc_alternative_value_with_evidence().evidence() != null) {
//                EvidenceInfo.processEvidence(object.getEvidenceInfo(), name.name, nameContext.cc_alternative_value_with_evidence().evidence().EV_TAG());
//            }

            CcLineParser.Cc_alternative_products_isoidContext cc_alternative_products_isoidContext = nameContext.cc_alternative_products_isoid();
            List<CcLineParser.Cc_alternative_valueContext> isos = cc_alternative_products_isoidContext.cc_alternative_value();
            for (CcLineParser.Cc_alternative_valueContext cc_alternative_valueContext : isos) {
                name.isoId.add(cc_alternative_valueContext.getText());
            }

            CcLineParser.Cc_alternative_products_sequence_valueContext seqvalueContext =
                    nameContext.cc_alternative_products_sequence().cc_alternative_products_sequence_value();
            if (seqvalueContext.CC_AP_DISPLAYED() != null) {
                name.sequence_enum = CcLineObject.AlternativeNameSequenceEnum.Displayed;
            } else if (seqvalueContext.CC_AP_EXTERNAL() != null) {
                name.sequence_enum = CcLineObject.AlternativeNameSequenceEnum.External;
            } else if (seqvalueContext.CC_AP_NOT_DESCRIBED() != null) {
                name.sequence_enum = CcLineObject.AlternativeNameSequenceEnum.Not_described;
            } else if (seqvalueContext.CC_AP_VALUE_UNSURE() != null) {
                name.sequence_enum = CcLineObject.AlternativeNameSequenceEnum.Unsure;
            } else if (seqvalueContext.CC_AP_DESCRIBED() != null) {
                name.sequence_enum = CcLineObject.AlternativeNameSequenceEnum.Described;
            } else if (seqvalueContext.cc_alternative_products_sequence_value_identifiers() != null) {
                List<TerminalNode> terminalNodes = seqvalueContext.cc_alternative_products_sequence_value_identifiers().CC_AP_FEATURE_IDENTIFIER();
                for (TerminalNode terminalNode : terminalNodes) {
                    String text = terminalNode.getText();
                    name.sequence_FTId.add(text);
                }
            }

            CcLineParser.Cc_alternative_products_synonymsContext synContext = nameContext.cc_alternative_products_synonyms();
            if (synContext != null) {
                List<CcLineParser.Cc_alternative_value_with_evidenceContext> cc_alternative_valueContexts = synContext.cc_alternative_value_with_evidence();
                for (CcLineParser.Cc_alternative_value_with_evidenceContext cc_alternative_valueContext : cc_alternative_valueContexts) {
                //    String sync = cc_alternative_valueContext.cc_alternative_value().getText();
                    name.synNames.add(getEvidenceString(cc_alternative_valueContext));
//                    if (cc_alternative_valueContext.evidence() != null) {
//                        EvidenceInfo.processEvidence(object.getEvidenceInfo(), sync, cc_alternative_valueContext.evidence().EV_TAG());
//                    }
                }
            }

            CcLineParser.Cc_alternative_products_noteContext cc_alternative_products_noteContext
                    = nameContext.cc_alternative_products_note();
            if (cc_alternative_products_noteContext != null) {
                Cc_properties_notes_level_2Context cc_properties_notes_level_2Context = cc_alternative_products_noteContext.cc_properties_notes_level_2();
                List<Cc_properties_note_text_level2_with_evContext> text_level2_with_evContexts = cc_properties_notes_level_2Context.cc_properties_note_text_level2_with_ev();
                for (Cc_properties_note_text_level2_with_evContext ccc : text_level2_with_evContexts) {
                    name.note.add(getEvidencedString(ccc));
                }
            }
            ap.names.add(name);
        }

        object.ccs.add(cc);
    }


    @Override
    public void exitCc_disease(@NotNull CcLineParser.Cc_diseaseContext ctx) {

        CcLineObject.CC cc = new CcLineObject.CC();
        cc.topic = CcLineObject.CCTopicEnum.DISEASE;
        CcLineObject.Disease dd = new CcLineObject.Disease();
        cc.object = dd;

        if (ctx.cc_disease_name() != null) {
            String text = ctx.cc_disease_name().getText();
            dd.name = text;

            CcLineParser.Cc_disease_abbr_minContext cc_disease_abbr_minContext = ctx.cc_disease_abbr_min();
            String text1 = cc_disease_abbr_minContext.getText();

            String[] objects = TextHelper.parseCCDiseaseAbbrMim(text1);
            dd.abbr = objects[0];
            dd.mim = objects[1];
        }

        if (ctx.cc_disease_description() != null) {
            dd.description = ctx.cc_disease_description().cc_disease_text().getText();

            if (ctx.cc_disease_description().evidence() != null) {
                EvidenceInfo.processEvidence(object.evidenceInfo, dd.description, ctx.cc_disease_description().evidence().EV_TAG());
            }
        }

        if (ctx.cc_disease_note() != null) {

            Cc_common_textsContext cc_common_textsContext = ctx.cc_disease_note().cc_common_texts();

            List<Cc_common_text_with_evContext> cc_common_text_with_evContexts = cc_common_textsContext.cc_common_text_with_ev();

            for (Cc_common_text_with_evContext ftCtx : cc_common_text_with_evContexts) {
                dd.note.add(getEvidencedString(ftCtx));
            }
        }

        object.ccs.add(cc);
    }

    @Override
    public void exitCc_cofactor(@NotNull CcLineParser.Cc_cofactorContext ctx) {
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
            Cc_properties_notesContext cc_properties_notesContext = note.cc_properties_notes();
            List<Cc_properties_note_text_with_evContext> cc_properties_text_with_evContexts = cc_properties_notesContext.cc_properties_note_text_with_ev();
            for (Cc_properties_note_text_with_evContext cc_properties_text_with_evContext : cc_properties_text_with_evContexts) {
                dd.note.add(getEvidencedString(cc_properties_text_with_evContext));
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

    public CcLineObject getObject() {
        return object;
    }


}

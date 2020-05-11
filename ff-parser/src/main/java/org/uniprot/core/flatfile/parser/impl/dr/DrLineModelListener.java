package org.uniprot.core.flatfile.parser.impl.dr;

import java.util.ArrayList;
import java.util.List;

import org.antlr.v4.runtime.misc.NotNull;
import org.uniprot.core.flatfile.antlr.DrLineParser;
import org.uniprot.core.flatfile.antlr.DrLineParserBaseListener;
import org.uniprot.core.flatfile.parser.ParseException;
import org.uniprot.core.flatfile.parser.ParseTreeObjectExtractor;
import org.uniprot.core.flatfile.parser.impl.EvidenceInfo;

/**
 * Created with IntelliJ IDEA. User: wudong Date: 08/08/13 Time: 12:26 To change this template use
 * File | Settings | File Templates.
 */
public class DrLineModelListener extends DrLineParserBaseListener
        implements ParseTreeObjectExtractor<DrLineObject> {

    private DrLineObject object;

    @Override
    public DrLineObject getObject() {
        return object;
    }

    /*
     * @Override public void exitDr_prosite_ss_line(@NotNull DrLineParser.Dr_prosite_ss_lineContext ctx) {
     * DrLineObject.DrObject drObject = new DrLineObject.DrObject(); drObject.DbName = "PROSITE"; if
     * (ctx.dr_four_attribute_line() != null) { DrLineParser.Dr_four_attribute_lineContext dr_four_attribute_lineContext
     * = ctx.dr_four_attribute_line(); drObject.attributes.add(dr_four_attribute_lineContext.dr_attribute(0).getText());
     * drObject.attributes.add(dr_four_attribute_lineContext.dr_attribute(1).getText());
     * drObject.attributes.add(dr_four_attribute_lineContext.dr_attribute(2).getText());
     * drObject.attributes.add(dr_four_attribute_lineContext.dr_attribute(3).getText()); } else if
     * (ctx.dr_three_attribute_line() != null) { DrLineParser.Dr_three_attribute_lineContext
     * dr_three_attribute_lineContext = ctx.dr_three_attribute_line();
     * drObject.attributes.add(dr_three_attribute_lineContext.dr_attribute(0).getText());
     * drObject.attributes.add(dr_three_attribute_lineContext.dr_attribute(1).getText());
     * drObject.attributes.add(dr_three_attribute_lineContext.dr_attribute(2).getText()); } else if
     * (ctx.dr_two_attribute_line() != null) { DrLineParser.Dr_two_attribute_lineContext dr_two_attribute_lineContext =
     * ctx.dr_two_attribute_line(); drObject.attributes.add(dr_two_attribute_lineContext.dr_attribute(0).getText());
     * drObject.attributes.add(dr_two_attribute_lineContext.dr_attribute(1).getText()); } DrLineParser.Dr_isoformContext
     * dr_isoformContext =ctx.dr_isoform(); if (dr_isoformContext !=null){ drObject.isoform =
     * dr_isoformContext.ISOFORM().getText(); } drObject.inSsLine=true; object.drObjects.add(drObject); }
     */

    @Override
    public void enterDr_dr(@NotNull DrLineParser.Dr_drContext ctx) {
        object = new DrLineObject();
    }

    @Override
    public void exitDr_prosite_ss(@NotNull DrLineParser.Dr_prosite_ssContext ctx) {
        DrLineObject.DrObject drObject = new DrLineObject.DrObject();
        drObject.DbName = "PROSITE";

        String text = ctx.ATTRIBUTES().getText();
        // parseDRHardWay(drObject, text);
        drObject.ssLineValue = text;
        object.drObjects.add(drObject);
    }

    @Override
    public void exitDr_line(@NotNull DrLineParser.Dr_lineContext ctx) {

        DrLineObject.DrObject drObject = new DrLineObject.DrObject();
        String text1 = ctx.DB_NAME().getText();
        drObject.DbName = text1;

        String text = ctx.ATTRIBUTES().getText();
        // parsing this text manually.

        parseDRHardWay(drObject, text);

        object.drObjects.add(drObject);
    }

    private void parseDRHardWay(DrLineObject.DrObject drObject, String text) {
        int index = text.indexOf("; ");
        while (index > 0) {
            String attr = text.substring(0, index);
            drObject.attributes.add(attr);
            text = text.substring(index + 2);
            index = text.indexOf("; ");
        }

        // the remainder.
        // DR PRIDE; P19802; -.
        // DR PRIDE; P19802; -.{EI1}
        // DR PRIDE; P19802; -. [P19802-2]
        // DR PROSITE; PS00157; RUBISCO_LARGE; 1. [P21235-2]
        // DR PROSITE; PS00157; RUBISCO_LARGE; 1. [P21235-2]{EI1}

        if (text.endsWith("}")) {
            int evIndex = text.indexOf('{');
            if (evIndex > 0) {
                String evidence = text.substring(evIndex + 1, text.length() - 1);
                this.processEvidence(this.object, drObject, evidence);
                text = text.substring(0, evIndex).trim();
            } else {
                throw new ParseException("Evidence tag without opening {", text, 0, 0, null);
            }
        }

        if (text.endsWith("]")) {
            int evIndex = text.indexOf('[');
            if (evIndex > 0) {
                String isoform = text.substring(evIndex + 1, text.length() - 1);
                drObject.isoform = isoform;
                text = text.substring(0, evIndex).trim();
            } else {
                throw new ParseException("Isoform tag without opening [", text, 0, 0, null);
            }
        }

        if (text.endsWith(".")) {
            String attr = text.substring(0, text.length() - 1);
            drObject.attributes.add(attr);
        } else {
            throw new ParseException("No proper ending for DR attributes", text, 0, 0, null);
        }
    }

    private void processEvidence(DrLineObject line, DrLineObject.DrObject dr, String evidence) {
        List<String> evs = new ArrayList<String>();

        int index = evidence.indexOf(",");
        while (index > 0) {
            String attr = evidence.substring(0, index);
            evs.add(attr.trim());
            evidence = evidence.substring(index + 1);
            index = evidence.indexOf(",");
        }

        evs.add(evidence);

        EvidenceInfo.processEvidenceString(line.getEvidenceInfo(), dr, evs);
    }
}

package org.uniprot.core.flatfile.parser.impl.ft;

import java.util.List;

import org.antlr.v4.runtime.tree.TerminalNode;
import org.uniprot.core.flatfile.antlr.FtLineParser;
import org.uniprot.core.flatfile.antlr.FtLineParserBaseListener;
import org.uniprot.core.flatfile.parser.ParseTreeObjectExtractor;
import org.uniprot.core.flatfile.parser.impl.EvidenceInfo;

import com.google.common.base.Strings;

/**
 * Created with IntelliJ IDEA. User: wudong Date: 08/08/13 Time: 12:26 To change this template use
 * File | Settings | File Templates.
 */
public class FtLineModelListener extends FtLineParserBaseListener
        implements ParseTreeObjectExtractor<FtLineObject> {

    /** */
    private static final long serialVersionUID = -7255681918186532074L;

    private FtLineObject object;

    private FtLineObject.FT ft;

    @Override
    public FtLineObject getObject() {
        return object;
    }

    @Override
    public void enterFt_ft(FtLineParser.Ft_ftContext ctx) {
        this.object = new FtLineObject();
    }

    @Override
    public void enterFt_line(FtLineParser.Ft_lineContext ctx) {
        ft = new FtLineObject.FT();
    }

    @Override
    public void exitFt_line(FtLineParser.Ft_lineContext ctx) {
        if (ft.getLocationEnd() == null) {
            ft.setLocationEnd(ft.getLocationStart());
        }
        object.getFts().add(ft);
        ft = null;
    }

    @Override
    public void exitFt_key(FtLineParser.Ft_keyContext ctx) {
        String key = ctx.getText();

        if (!Strings.isNullOrEmpty(key)) {
            ft.setType(FtLineObject.FTType.valueOf(key));
        }
    }

    @Override
    public void exitFt_note(FtLineParser.Ft_noteContext ctx) {
        if (ctx.note_text() != null) {
            String result = ctx.note_text().getText();
            result = result.replaceAll("''", "\"");
            ft.setFtText(updateAltSeqText(result));
        }
    }

    @Override
    public void exitFt_evidence(FtLineParser.Ft_evidenceContext ctx) {
        List<TerminalNode> terminalNodes = ctx.EV_TAG();
        List<String> evidences = EvidenceInfo.processEvidence(terminalNodes);
        object.getEvidenceInfo().getEvidences().put(ft, evidences);
    }

    @Override
    public void exitFt_id(FtLineParser.Ft_idContext ctx) {
        if (ctx.FTID_VALUE() != null) ft.setFtId(ctx.FTID_VALUE().getText());
    }

    @Override
    public void exitLoc_start(FtLineParser.Loc_startContext ctx) {
        String text = ctx.getText();
        int index = text.indexOf(":");
        if (index != -1) {
            ft.setSequence(text.substring(0, index));
            ft.setLocationStart(text.substring(index + 1));
        } else ft.setLocationStart(text);
    }

    @Override
    public void exitLoc_end(FtLineParser.Loc_endContext ctx) {
        ft.setLocationEnd(ctx.FT_LOCATION_2().getText().trim());
    }

    private String updateAltSeqText(String text) {
        if (!FtLineObject.hasAltSeq(ft.getType())) return text;
        return fixVarSeqSpace(text);
    }

    private String fixVarSeqSpace(String value) {
        String temp = value;
        int index = -1;
        do {
            index = isSpaceBetweenCapital(temp);
            if (index != -1) {
                String temp2 = temp.substring(0, index) + temp.substring(index + 1);
                temp = temp2;
            }
        } while (index != -1);

        index = firstNonCapital(temp);
        if (index == -1) return temp;
        int spaceLocation = -1;
        do {
            spaceLocation = getSpaceLocation(temp, index);
            if (spaceLocation == -1) break;
            String val = temp.substring(0, spaceLocation) + temp.substring(spaceLocation + 1);
            temp = val;
            index -= 1;
        } while (spaceLocation != -1);

        return temp;
    }

    private int getSpaceLocation(String value, int index) {
        for (int i = 0; i < index; i++) {
            if (isCapital(value.charAt(i))) {
                if ((i + 2) < index) {
                    if ((value.charAt(i + 1) == ' ') && isCapital(value.charAt(i + 2))) {
                        return i + 1;
                    }
                }
            }
        }
        return -1;
    }

    private boolean isCapital(char c) {
        return c >= 'A' && c <= 'Z';
    }

    private int isSpaceBetweenCapital(String val) {
        for (int i = 0; i < val.length(); i++) {
            if (val.charAt(i) == ' ') {
                if (isNearCapital(val, i)) return i;
            }
        }
        return -1;
    }

    private int firstNonCapital(String val) {
        for (int i = 0; i < val.length(); i++) {
            char c = val.charAt(i);
            if (isCapital(c)) continue;

            if ((c == ' ') || (c == '-') || (c == '>')) {
                continue;
            }
            return i;
        }
        return -1;
    }

    private boolean isNearCapital(String val, int i) {
        boolean left = true;
        boolean right = true;
        if (i > 1) {
            left = isCapital(val.charAt(i - 1));
        }
        if (i < val.length() - 1) {
            right = isCapital(val.charAt(i + 1));
        }
        return left && right;
    }
}

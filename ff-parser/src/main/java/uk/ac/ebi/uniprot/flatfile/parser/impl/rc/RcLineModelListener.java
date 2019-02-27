package uk.ac.ebi.uniprot.flatfile.parser.impl.rc;

import org.antlr.v4.runtime.misc.NotNull;
import uk.ac.ebi.uniprot.flatfile.antlr.RcLineParser;
import uk.ac.ebi.uniprot.flatfile.antlr.RcLineParserBaseListener;
import uk.ac.ebi.uniprot.flatfile.parser.ParseTreeObjectExtractor;
import uk.ac.ebi.uniprot.flatfile.parser.impl.EvidenceInfo;

import java.util.List;


/**
 * Created with IntelliJ IDEA.
 * User: wudong
 * Date: 08/08/13
 * Time: 12:26
 * To change this template use File | Settings | File Templates.
 */
public class RcLineModelListener extends RcLineParserBaseListener implements ParseTreeObjectExtractor<RcLineObject> {

    private RcLineObject object ;

    @Override
    public void enterRc_rc(@NotNull RcLineParser.Rc_rcContext ctx) {
        this.object = new RcLineObject();
    }

    @Override
    public void exitRc(@NotNull RcLineParser.RcContext ctx) {
        RcLineParser.Rc_tokenContext rc_tokenContext = ctx.rc_token();
        RcLineObject.RcTokenEnum type = null;
        if (rc_tokenContext.PLASMID() != null) {
            type = RcLineObject.RcTokenEnum.PLASMID;
        } else if (rc_tokenContext.STRAIN() != null) {
            type = RcLineObject.RcTokenEnum.STRAIN;
        } else if (rc_tokenContext.TISSUE() != null) {
            type = RcLineObject.RcTokenEnum.TISSUE;
        } else if (rc_tokenContext.TRANSPOSON() != null) {
            type = RcLineObject.RcTokenEnum.TRANSPOSON;
        }

        RcLineObject.RC rc = new RcLineObject.RC();
        rc.tokenType=type;

        List<RcLineParser.Rc_valueContext> rc_valueContexts = ctx.rc_text().rc_value();
        for (RcLineParser.Rc_valueContext rc_valueContext : rc_valueContexts) {
            String text = rc_valueContext.rc_value_v().getText();

            rc.values.add(text);

	        RcLineParser.EvidenceContext evidence = rc_valueContext.evidence();
	        if (evidence !=null){
		        EvidenceInfo.processEvidence(rc.getEvidenceInfo(), text, evidence.EV_TAG());
	        }
        }

        int indexOfAnd = -1;
        for (int i=0; i< rc.values.size(); i++){
            String s = rc.values.get(i);

            if (s.startsWith("and ")){
                rc.values.set(i, s.substring(4));
                indexOfAnd = i;

                List<String> strings = rc.getEvidenceInfo().evidences.get(s);
                if (strings!=null){
                    rc.getEvidenceInfo().evidences.put(s.substring(4), strings);
                }

                break;
            }
        }

        if (indexOfAnd > 0 && indexOfAnd < rc.values.size()-1){
            List<String> evidence = null;
            String s = rc.values.get(indexOfAnd);
            for (int j=indexOfAnd+1; j<rc.values.size(); j++){
                s+= (", " +rc.values.get(j));
                evidence = rc.getEvidenceInfo().evidences.remove(rc.values.get(j));
            }
            for (int j=indexOfAnd+1; j<rc.values.size(); j++){
               rc.values.remove(j);
            }

            rc.values.set(indexOfAnd, s);

            if (evidence!=null){
                rc.getEvidenceInfo().evidences.put(s, evidence);
            }
        }

        object.rcs.add(rc);
    }

    public RcLineObject getObject() {
        return object;
    }
}

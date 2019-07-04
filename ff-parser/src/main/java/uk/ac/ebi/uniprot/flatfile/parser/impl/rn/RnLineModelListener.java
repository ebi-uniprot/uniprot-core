package uk.ac.ebi.uniprot.flatfile.parser.impl.rn;

import org.antlr.v4.runtime.misc.NotNull;
import uk.ac.ebi.uniprot.flatfile.antlr.RnLineParserBaseListener;
import uk.ac.ebi.uniprot.flatfile.antlr.RnLineParser;
import uk.ac.ebi.uniprot.flatfile.parser.ParseTreeObjectExtractor;
import uk.ac.ebi.uniprot.flatfile.parser.impl.EvidenceInfo;

/**
 * Created with IntelliJ IDEA.
 * User: wudong
 * Date: 08/08/13
 * Time: 12:26
 * To change this template use File | Settings | File Templates.
 */
public class RnLineModelListener extends RnLineParserBaseListener implements ParseTreeObjectExtractor<RnLineObject> {

    private RnLineObject object;

    @Override
    public void enterRn_rn(@NotNull RnLineParser.Rn_rnContext ctx) {
        this.object=new RnLineObject();
    }

    @Override
    public void exitRn_number(@NotNull RnLineParser.Rn_numberContext ctx) {
        String text = ctx.getText();
        object.number = Integer.parseInt(text);
    }

    public RnLineObject getObject() {
        return object;
    }

	@Override
	public void exitEvidence(@NotNull RnLineParser.EvidenceContext ctx) {
		EvidenceInfo.processEvidence(object.getEvidenceInfo(), object.number, ctx.EV_TAG());
	}
}

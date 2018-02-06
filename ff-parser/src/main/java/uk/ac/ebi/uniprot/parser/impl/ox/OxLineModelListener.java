package uk.ac.ebi.uniprot.parser.impl.ox;

import org.antlr.v4.runtime.misc.NotNull;
import uk.ac.ebi.uniprot.parser.ParseTreeObjectExtractor;
import uk.ac.ebi.uniprot.parser.impl.EvidenceInfo;
import uk.ac.ebi.uniprot.antlr.OxLineBaseListener;
import uk.ac.ebi.uniprot.antlr.OxLineParser;


/**
 * Created with IntelliJ IDEA.
 * User: wudong
 * Date: 08/08/13
 * Time: 12:26
 * To change this template use File | Settings | File Templates.
 */
public class OxLineModelListener extends OxLineBaseListener implements ParseTreeObjectExtractor<OxLineObject> {

	private OxLineObject object;

    @Override
    public void enterOx_ox(@NotNull OxLineParser.Ox_oxContext ctx) {
        this.object = new OxLineObject();;
    }

    @Override
	public void exitTax(@NotNull OxLineParser.TaxContext ctx) {
		String text = ctx.getText();
		object.taxonomy_id = Integer.parseInt(text);
	}

	@Override
	public void exitEvidence(@NotNull OxLineParser.EvidenceContext ctx) {
		EvidenceInfo.processEvidence(object.getEvidenceInfo(), object.taxonomy_id,ctx.EV_TAG());
	}

	public OxLineObject getObject() {
		return object;
	}
}

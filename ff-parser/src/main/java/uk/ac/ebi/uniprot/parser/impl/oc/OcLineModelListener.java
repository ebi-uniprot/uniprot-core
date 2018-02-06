package uk.ac.ebi.uniprot.parser.impl.oc;

import org.antlr.v4.runtime.misc.NotNull;
import uk.ac.ebi.uniprot.parser.ParseTreeObjectExtractor;
import uk.ac.ebi.uniprot.antlr.OcLineParser;
import uk.ac.ebi.uniprot.antlr.OcLineParserBaseListener;

/**
 * Created with IntelliJ IDEA.
 * User: wudong
 * Date: 08/08/13
 * Time: 12:26
 * To change this template use File | Settings | File Templates.
 */
public class OcLineModelListener extends OcLineParserBaseListener implements ParseTreeObjectExtractor<OcLineObject> {

	private OcLineObject object;

    @Override
    public void enterOc_oc(@NotNull OcLineParser.Oc_ocContext ctx) {
        this.object=new OcLineObject();
    }

    @Override
	public void exitOc(@NotNull OcLineParser.OcContext ctx) {
		object.nodes.add(ctx.getText());
	}

	public OcLineObject getObject() {
		return object;
	}
}

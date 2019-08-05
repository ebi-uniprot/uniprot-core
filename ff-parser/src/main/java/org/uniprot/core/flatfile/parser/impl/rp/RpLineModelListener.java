package org.uniprot.core.flatfile.parser.impl.rp;

import org.antlr.v4.runtime.misc.NotNull;
import org.uniprot.core.flatfile.parser.ParseTreeObjectExtractor;

import uk.ac.ebi.uniprot.flatfile.antlr.RpLineParser;
import uk.ac.ebi.uniprot.flatfile.antlr.RpLineParserBaseListener;

/**
 * Created with IntelliJ IDEA.
 * User: wudong
 * Date: 08/08/13
 * Time: 12:26
 * To change this template use File | Settings | File Templates.
 */
public class RpLineModelListener extends RpLineParserBaseListener implements ParseTreeObjectExtractor<RpLineObject> {

	private RpLineObject object;

    @Override
    public void enterRp_rp(@NotNull RpLineParser.Rp_rpContext ctx) {
        object = new RpLineObject();
    }

    public RpLineObject getObject() {
		return object;
	}

	@Override
	public void exitRp_scope(@NotNull RpLineParser.Rp_scopeContext ctx) {
		String scope = ctx.getText();
		object.scopes.add(scope);
	}
}

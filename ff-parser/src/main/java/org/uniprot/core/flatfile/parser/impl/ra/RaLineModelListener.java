package org.uniprot.core.flatfile.parser.impl.ra;

import org.antlr.v4.runtime.misc.NotNull;
import org.uniprot.core.flatfile.parser.ParseTreeObjectExtractor;

import org.uniprot.core.flatfile.antlr.RaLineParser;
import org.uniprot.core.flatfile.antlr.RaLineParserBaseListener;


/**
 * Created with IntelliJ IDEA.
 * User: wudong
 * Date: 08/08/13
 * Time: 12:26
 * To change this template use File | Settings | File Templates.
 */
public class RaLineModelListener extends RaLineParserBaseListener implements ParseTreeObjectExtractor<RaLineObject> {

	private RaLineObject object;

    @Override
    public void enterRa_ra(@NotNull RaLineParser.Ra_raContext ctx) {
        this.object = new RaLineObject();
    }

    @Override
	public void exitName(@NotNull RaLineParser.NameContext ctx) {
		String text = ctx.getText();
		object.authors.add(text);
	}

	public RaLineObject getObject() {
		return object;
	}
}

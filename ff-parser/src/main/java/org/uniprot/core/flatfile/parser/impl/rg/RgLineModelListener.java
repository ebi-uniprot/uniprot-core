package org.uniprot.core.flatfile.parser.impl.rg;

import org.antlr.v4.runtime.misc.NotNull;
import org.uniprot.core.flatfile.parser.ParseTreeObjectExtractor;

import uk.ac.ebi.uniprot.flatfile.antlr.RgLineParser;
import uk.ac.ebi.uniprot.flatfile.antlr.RgLineParserBaseListener;


/**
 * Created with IntelliJ IDEA.
 * User: wudong
 * Date: 08/08/13
 * Time: 12:26
 * To change this template use File | Settings | File Templates.
 */
public class RgLineModelListener extends RgLineParserBaseListener implements ParseTreeObjectExtractor<RgLineObject> {

	private RgLineObject object ;

    @Override
    public void enterRg_rg(@NotNull RgLineParser.Rg_rgContext ctx) {
        this.object = new RgLineObject();
    }

    @Override
	public void exitRg_value(@NotNull RgLineParser.Rg_valueContext ctx) {
		String text = ctx.getText();
		object.reference_groups.add(text);
	}

	public RgLineObject getObject() {
		return object;
	}
}

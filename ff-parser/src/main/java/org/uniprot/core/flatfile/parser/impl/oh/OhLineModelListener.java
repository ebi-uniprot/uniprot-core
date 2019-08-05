package org.uniprot.core.flatfile.parser.impl.oh;

import org.antlr.v4.runtime.misc.NotNull;
import org.uniprot.core.flatfile.parser.ParseTreeObjectExtractor;

import uk.ac.ebi.uniprot.flatfile.antlr.OhLineParser;
import uk.ac.ebi.uniprot.flatfile.antlr.OhLineParserBaseListener;

/**
 * Created with IntelliJ IDEA.
 * User: wudong
 * Date: 08/08/13
 * Time: 12:26
 * To change this template use File | Settings | File Templates.
 */
public class OhLineModelListener extends OhLineParserBaseListener implements ParseTreeObjectExtractor<OhLineObject> {

	private OhLineObject object;

    @Override
    public void enterOh_oh(@NotNull OhLineParser.Oh_ohContext ctx) {
        this.object = new OhLineObject();
    }

    @Override
    public void exitOh_line(@NotNull OhLineParser.Oh_lineContext ctx) {
        OhLineObject.OhValue ohValue = new OhLineObject.OhValue();
        ohValue.hostname = ctx.hostname().getText();
        String text = ctx.tax().getText();
        ohValue.tax_id = Integer.parseInt(text);
        object.hosts.add(ohValue);
    }


	public OhLineObject getObject() {
		return object;
	}
}

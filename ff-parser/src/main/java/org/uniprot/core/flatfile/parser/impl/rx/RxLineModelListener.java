package org.uniprot.core.flatfile.parser.impl.rx;

import org.antlr.v4.runtime.misc.NotNull;
import org.uniprot.core.flatfile.parser.ParseTreeObjectExtractor;

import uk.ac.ebi.uniprot.flatfile.antlr.RxLineParser;
import uk.ac.ebi.uniprot.flatfile.antlr.RxLineParserBaseListener;

/**
 * Created with IntelliJ IDEA.
 * User: wudong
 * Date: 08/08/13
 * Time: 12:26
 * To change this template use File | Settings | File Templates.
 */
public class RxLineModelListener extends RxLineParserBaseListener implements ParseTreeObjectExtractor<RxLineObject> {

	private RxLineObject object;

    @Override
    public void enterRx_rx(@NotNull RxLineParser.Rx_rxContext ctx) {
        object = new RxLineObject();
    }

    @Override
	public void exitDoi(@NotNull RxLineParser.DoiContext ctx) {
		RxLineObject.RX rx = new RxLineObject.RX();
		rx.type = RxLineObject.DB.DOI;
		rx.value = ctx.VALUE().getText();
		object.rxs.add(rx);
	}

	@Override
	public void exitAgri(@NotNull RxLineParser.AgriContext ctx) {
		RxLineObject.RX rx = new RxLineObject.RX();
		rx.type = RxLineObject.DB.AGRICOLA;
		rx.value = ctx.VALUE().getText();
		object.rxs.add(rx);
	}

	@Override
	public void exitPubmed(@NotNull RxLineParser.PubmedContext ctx) {
		RxLineObject.RX rx = new RxLineObject.RX();
		rx.type = RxLineObject.DB.PubMed;
		rx.value = ctx.VALUE().getText();
		object.rxs.add(rx);
	}

	public RxLineObject getObject() {
		return object;
	}
}

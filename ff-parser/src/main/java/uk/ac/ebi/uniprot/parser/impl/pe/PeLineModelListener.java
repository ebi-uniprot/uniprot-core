package uk.ac.ebi.uniprot.parser.impl.pe;

import org.antlr.v4.runtime.misc.NotNull;
import uk.ac.ebi.uniprot.parser.ParseTreeObjectExtractor;
import uk.ac.ebi.uniprot.antlr.PeLineBaseListener;
import uk.ac.ebi.uniprot.antlr.PeLineParser;

/**
 * Created with IntelliJ IDEA.
 * User: wudong
 * Date: 08/08/13
 * Time: 12:26
 * To change this template use File | Settings | File Templates.
 */
public class PeLineModelListener extends PeLineBaseListener implements ParseTreeObjectExtractor<PeLineObject> {

	private PeLineObject object ;

    @Override
    public void enterPe_pe(@NotNull PeLineParser.Pe_peContext ctx) {
        this.object = new PeLineObject();
    }

    @Override
	public void exitPe_level(@NotNull PeLineParser.Pe_levelContext ctx) {
		if (ctx.LEVEL1() != null) {
			object.level = 1;
		} else if (ctx.LEVEL2() != null) {
			object.level = 2;
		} else if (ctx.LEVEL3() != null) {
			object.level = 3;
		} else if (ctx.LEVEL4() != null) {
			object.level = 4;
		} else if (ctx.LEVEL5() != null) {
			object.level = 5;
		}
	}

	public PeLineObject getObject() {
		return object;
	}
}

package uk.ac.ebi.uniprot.parser.impl.rt;

import org.antlr.v4.runtime.misc.NotNull;
import uk.ac.ebi.uniprot.parser.ParseTreeObjectExtractor;
import uk.ac.ebi.uniprot.antlr.RtLineParser;
import uk.ac.ebi.uniprot.antlr.RtLineParserBaseListener;

/**
 * Created with IntelliJ IDEA.
 * User: wudong
 * Date: 08/08/13
 * Time: 12:26
 * To change this template use File | Settings | File Templates.
 */
public class RtLineModelListener extends RtLineParserBaseListener implements ParseTreeObjectExtractor<RtLineObject> {

    private RtLineObject object;

    @Override
    public void enterRt_rt(@NotNull RtLineParser.Rt_rtContext ctx) {
        this.object=new RtLineObject();
    }

    @Override
	public void exitRt_line(@NotNull RtLineParser.Rt_lineContext ctx) {
		object.title = ctx.getText();
	}

	public RtLineObject getObject() {
        return object;
    }
}

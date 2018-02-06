package uk.ac.ebi.uniprot.parser.impl.os;

import org.antlr.v4.runtime.misc.NotNull;
import uk.ac.ebi.uniprot.parser.ParseTreeObjectExtractor;
import uk.ac.ebi.uniprot.antlr.OsLineParser;
import uk.ac.ebi.uniprot.antlr.OsLineParserBaseListener;

/**
 * Created with IntelliJ IDEA.
 * User: wudong
 * Date: 08/08/13
 * Time: 12:26
 * To change this template use File | Settings | File Templates.
 */
public class OsLineModelListener extends OsLineParserBaseListener implements ParseTreeObjectExtractor<OsLineObject> {

	private OsLineObject object;

    @Override
    public void enterOs_os(@NotNull OsLineParser.Os_osContext ctx) {
        this.object = new OsLineObject();
    }

    @Override
	public void exitOs_os(@NotNull OsLineParser.Os_osContext ctx) {
		String text = ctx.os_line().getText();
		object.organism_species = text;
	}

	public OsLineObject getObject() {
		return object;
	}
}

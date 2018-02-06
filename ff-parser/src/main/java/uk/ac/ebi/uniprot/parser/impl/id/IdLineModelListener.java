package uk.ac.ebi.uniprot.parser.impl.id;

import org.antlr.v4.runtime.misc.NotNull;

import uk.ac.ebi.uniprot.antlr.IdLineParser;
import uk.ac.ebi.uniprot.parser.ParseTreeObjectExtractor;
import uk.ac.ebi.uniprot.antlr.IdLineParserBaseListener;

/**
 * Created with IntelliJ IDEA.
 * User: wudong
 * Date: 08/08/13
 * Time: 12:26
 * To change this template use File | Settings | File Templates.
 */
public class IdLineModelListener extends IdLineParserBaseListener implements ParseTreeObjectExtractor<IdLineObject> {

    private IdLineObject object;

    @Override
    public void enterId_id(@NotNull IdLineParser.Id_idContext ctx) {
        this.object = new IdLineObject();
    }

    @Override
    public void exitReview_status(@NotNull IdLineParser.Review_statusContext ctx) {
        if (ctx.REVIEW_STATUS_REVIEWED() != null) {
            object.reviewed = true;
        }
        if (ctx.REVIEW_STATUS_UNREVIEWED() != null) {
            object.reviewed = false;
        }
    }

    @Override
    public void exitEntry_name(@NotNull IdLineParser.Entry_nameContext ctx) {
        object.entryName = ctx.getText();
    }

    @Override
    public void exitLength(@NotNull IdLineParser.LengthContext ctx) {
        object.sequenceLength = Integer.parseInt(ctx.getText());
    }

    public IdLineObject getObject() {
        return object;
    }
}

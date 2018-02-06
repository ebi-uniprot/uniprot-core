package uk.ac.ebi.uniprot.parser.impl.dt;

import uk.ac.ebi.uniprot.antlr.DtLineParser;
import uk.ac.ebi.uniprot.parser.ParseTreeObjectExtractor;
import uk.ac.ebi.uniprot.antlr.DtLineParserBaseListener;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Locale;
import org.antlr.v4.runtime.misc.NotNull;

/**
 * Created with IntelliJ IDEA. User: wudong Date: 08/08/13 Time: 12:26 To change this template use File | Settings |
 * File Templates.
 */
public class DtLineModelListener extends DtLineParserBaseListener implements ParseTreeObjectExtractor<DtLineObject> {

    private DtLineObject object;
    private DateTimeFormatter formatter = new DateTimeFormatterBuilder()
            .parseCaseInsensitive()
            .append(DateTimeFormatter.ofPattern("dd-MMM-yyyy"))
            .toFormatter(Locale.ENGLISH);

    @Override
    public void exitDt_entryver_line(@NotNull DtLineParser.Dt_entryver_lineContext ctx) {
        String text = ctx.DATE().getText();
        object.entry_date = LocalDate.parse(text, formatter);

        String text1 = ctx.VERSION().getText();
        object.entry_version = Integer.parseInt(text1);
    }

    @Override
    public void enterDt_dt(@NotNull DtLineParser.Dt_dtContext ctx) {
        object = new DtLineObject();
    }

    @Override
    public void exitDt_integration_line(@NotNull DtLineParser.Dt_integration_lineContext ctx) {
        String text = ctx.DATE().getText();
        object.integration_date = LocalDate.parse(text, formatter);
        if (ctx.dt_database().SWISSPROT() != null) {
            object.isSiwssprot = true;
        } else if (ctx.dt_database().TREMBL() != null) {
            object.isSiwssprot = false;
        }
    }

    @Override
    public void exitDt_seqver_line(@NotNull DtLineParser.Dt_seqver_lineContext ctx) {
        String text = ctx.DATE().getText();
        object.seq_date = LocalDate.parse(text, formatter);

        String text1 = ctx.VERSION().getText();
        object.seq_version = Integer.parseInt(text1);
    }

    public DtLineObject getObject() {
        return object;
    }
}

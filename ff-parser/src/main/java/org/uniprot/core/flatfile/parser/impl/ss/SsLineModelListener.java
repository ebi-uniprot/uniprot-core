package org.uniprot.core.flatfile.parser.impl.ss;

import org.antlr.v4.runtime.misc.NotNull;
import org.uniprot.core.flatfile.antlr.SsLineParser;
import org.uniprot.core.flatfile.antlr.SsLineParserBaseListener;
import org.uniprot.core.flatfile.parser.ParseTreeObjectExtractor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Locale;

/**
 * Created with IntelliJ IDEA. User: wudong Date: 08/08/13 Time: 12:26 To change this template use
 * File | Settings | File Templates.
 */
public class SsLineModelListener extends SsLineParserBaseListener
        implements ParseTreeObjectExtractor<SsLineObject> {

    private SsLineObject object;

    @Override
    public void enterSs_ss(@NotNull SsLineParser.Ss_ssContext ctx) {
        this.object = new SsLineObject();
    }

    @Override
    public void exitInternal_annotation_line(
            @NotNull SsLineParser.Internal_annotation_lineContext ctx) {
        SsLineObject.SsLine ssLine = new SsLineObject.SsLine();
        ssLine.setTopic(ctx.TOPIC().getText());
        ssLine.setText(ctx.IA_TEXT().getText());
        object.getSsIALines().add(ssLine);
    }

    @Override
    public void exitSource_section_line(@NotNull SsLineParser.Source_section_lineContext ctx) {
        String text = ctx.SOURCE_TEXT().getText();
        object.getSsSourceLines().add(text);
    }

    @Override
    public void exitEvidence_line(@NotNull SsLineParser.Evidence_lineContext ctx) {
        SsLineObject.EvLine evLine = new SsLineObject.EvLine();
        evLine.setId(ctx.ev_id().getText());
        DateTimeFormatter formatter =
                new DateTimeFormatterBuilder()
                        .parseCaseInsensitive()
                        .append(DateTimeFormatter.ofPattern("dd-MMM-yyyy"))
                        .toFormatter(Locale.ENGLISH);

        evLine.setDate(LocalDate.parse(ctx.EV_DATE().getText(), formatter));
        evLine.setAttr2((ctx.ev_attr_2() != null) ? ctx.ev_attr_2().getText() : "");

        evLine.setDb(ctx.ev_db().getText());
        int i = evLine.getDb().indexOf(':');
        if (i >= 0) { // new eco code style.
            String db = evLine.getDb().substring(0, i);
            String attr = evLine.getDb().substring(i + 1);
            evLine.setDb(db);
            evLine.setAttr1(attr);
        } else {
            evLine.setAttr1((ctx.ev_attr_1() != null) ? ctx.ev_attr_1().getText() : "");
        }

        object.getSsEVLines().add(evLine);
    }

    public SsLineObject getObject() {
        return object;
    }
}

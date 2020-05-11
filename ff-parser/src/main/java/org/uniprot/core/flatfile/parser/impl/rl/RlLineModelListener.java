package org.uniprot.core.flatfile.parser.impl.rl;

// import org.antlr.v4.runtime.misc.NotNull;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.uniprot.core.flatfile.antlr.RlLineParser;
import org.uniprot.core.flatfile.antlr.RlLineParserBaseListener;
import org.uniprot.core.flatfile.parser.ParseTreeObjectExtractor;

/**
 * Created with IntelliJ IDEA. User: wudong Date: 08/08/13 Time: 12:26 To change this template use
 * File | Settings | File Templates.
 */
public class RlLineModelListener extends RlLineParserBaseListener
        implements ParseTreeObjectExtractor<RlLineObject> {

    private RlLineObject object;

    @Override
    public void enterRl_rl(@NotNull RlLineParser.Rl_rlContext ctx) {
        object = new RlLineObject();
    }

    @Override
    public void exitRl_journal(@NotNull RlLineParser.Rl_journalContext ctx) {
        RlLineObject.JournalArticle journalArticle = new RlLineObject.JournalArticle();
        journalArticle.setJournal(ctx.journal_abbr().getText());
        journalArticle.setVolume(ctx.journal_volume().J_ABBR_WORD().getText());
        journalArticle.setFirstPage(ctx.journal_volume().journal_first_page().getText());
        journalArticle.setLastPage(ctx.journal_volume().journal_last_page().getText());
        journalArticle.setYear(Integer.parseInt(ctx.journal_year().J_WORD().getText()));
        object.setReference(journalArticle);
    }

    @Override
    public void exitRl_epub(@NotNull RlLineParser.Rl_epubContext ctx) {
        RlLineObject.EPub epub = new RlLineObject.EPub();
        epub.setTitle(ctx.EP_WORD().getText());
        object.setReference(epub);
    }

    @Override
    public void exitRl_patent(@NotNull RlLineParser.Rl_patentContext ctx) {
        RlLineObject.Patent patent = new RlLineObject.Patent();
        patent.setPatentNumber(ctx.patent_number().getText());

        String text = ctx.PATENT_DATE().getText();
        String[] split = text.split("-");

        patent.setDay(Integer.parseInt(split[0]));
        patent.setMonth(split[1]);
        patent.setYear(Integer.parseInt(split[2]));

        this.object.setReference(patent);
    }

    @Override
    public void exitRl_book(@NotNull RlLineParser.Rl_bookContext ctx) {
        RlLineObject.Book book = new RlLineObject.Book();

        if (ctx.book_editors() != null) {
            List<RlLineParser.Book_editorContext> editors = ctx.book_editors().book_editor();
            for (RlLineParser.Book_editorContext editor : editors) {
                String text = editor.getText();
                book.getEditors().add(text);
            }
        }

        book.setTitle(ctx.book_name().getText());

        if (ctx.BOOK_YEAR() != null) {
            String text = ctx.BOOK_YEAR().getText();
            // it should be in format (1985).\n
            book.setYear(Integer.parseInt(text.substring(1, text.length() - 3)));
        } else {
            System.out.println(book.getTitle());
        }
        RlLineParser.Book_pageContext book_pageContext = ctx.book_page();
        RlLineParser.Book_abstract_pageContext book_abstract_pageContext = ctx.book_abstract_page();

        if (book_pageContext != null) {
            if (book_pageContext.BOOK_V_WORD() != null) {
                book.setPageString(book_pageContext.BOOK_V_WORD().getText());
            } else {
                if (book_pageContext.book_page_volume() != null) {
                    book.setVolume(book_pageContext.book_page_volume().BOOK_V_WORD().getText());
                }
                book.setPageStart(book_pageContext.book_page_first().getText());
                book.setPageEnd(book_pageContext.book_page_last().getText());
            }
        } else if (book_abstract_pageContext != null) {
            book.setPageString(book_abstract_pageContext.getText());
        }

        if (ctx.book_city() != null) book.setPlace(ctx.book_city().getText());
        if (ctx.book_publisher() != null) book.setPress(ctx.book_publisher().getText());

        this.object.setReference(book);
    }

    @Override
    public void exitRl_unpublished(@NotNull RlLineParser.Rl_unpublishedContext ctx) {
        RlLineObject.Unpublished unp = new RlLineObject.Unpublished();
        String text = ctx.UP_YEAR_MONTH().getText();
        String text1 = text.substring(1, text.length() - 1);
        String[] split = text1.split("-");
        unp.setMonth(split[0]);
        unp.setYear(Integer.parseInt(split[1]));
        this.object.setReference(unp);
    }

    @Override
    public void exitRl_submission(@NotNull RlLineParser.Rl_submissionContext ctx) {
        RlLineObject.Submission sub = new RlLineObject.Submission();
        String text = ctx.SUBMISSION_YEAR().getText();
        String text1 = text.substring(1, text.length() - 1);
        String[] split = text1.split("-");
        sub.setMonth(split[0]);
        sub.setYear(Integer.parseInt(split[1]));
        if (ctx.submission_db().EMBL() != null) {
            sub.setDb(RlLineObject.SubmissionDB.EMBL);
        } else if (ctx.submission_db().PDB() != null) {
            sub.setDb(RlLineObject.SubmissionDB.PDB);
        } else if (ctx.submission_db().UNIPROT() != null) {
            sub.setDb(RlLineObject.SubmissionDB.UNIPROTKB);
        } else if (ctx.submission_db().PIR() != null) {
            sub.setDb(RlLineObject.SubmissionDB.PIR);
        }
        this.object.setReference(sub);
    }

    @Override
    public void exitRl_thesis(@NotNull RlLineParser.Rl_thesisContext ctx) {
        RlLineObject.Thesis thesis = new RlLineObject.Thesis();

        thesis.setInstitute(ctx.thesis_institution().getText());
        String text = ctx.THESIS_YEAR().getText();
        String text1 = text.substring(1, text.length() - 1);
        thesis.setYear(Integer.parseInt(text1));
        if (ctx.thesis_country() != null) {
            thesis.setCountry(ctx.thesis_country().getText());
        }

        this.object.setReference(thesis);
    }

    public RlLineObject getObject() {
        return object;
    }
}

package uk.ac.ebi.uniprot.parser.impl.rl;

//import org.antlr.v4.runtime.misc.NotNull;
import uk.ac.ebi.uniprot.parser.ParseTreeObjectExtractor;
import uk.ac.ebi.uniprot.antlr.RlLineParser;
import uk.ac.ebi.uniprot.antlr.RlLineParserBaseListener;

import java.util.List;

import javax.validation.constraints.NotNull;

/**
 * Created with IntelliJ IDEA.
 * User: wudong
 * Date: 08/08/13
 * Time: 12:26
 * To change this template use File | Settings | File Templates.
 */
public class RlLineModelListener extends RlLineParserBaseListener implements ParseTreeObjectExtractor<RlLineObject> {

	private RlLineObject object;

    @Override
    public void enterRl_rl(@NotNull RlLineParser.Rl_rlContext ctx) {
        object = new RlLineObject();
    }

    @Override
	public void exitRl_journal(@NotNull RlLineParser.Rl_journalContext ctx) {
		RlLineObject.JournalArticle journalArticle = new RlLineObject.JournalArticle();
		journalArticle.journal = ctx.journal_abbr().getText();
		journalArticle.volume = ctx.journal_volume().J_ABBR_WORD().getText();
		journalArticle.first_page = ctx.journal_volume().journal_first_page().getText();
		journalArticle.last_page = ctx.journal_volume().journal_last_page().getText();
		journalArticle.year = Integer.parseInt(ctx.journal_year().J_WORD().getText());
		object.reference = journalArticle;
	}

	@Override
	public void exitRl_epub(@NotNull RlLineParser.Rl_epubContext ctx) {
		RlLineObject.EPub epub = new RlLineObject.EPub();
		epub.title = ctx.EP_WORD().getText();
		object.reference = epub;
	}

	@Override
	public void exitRl_patent(@NotNull RlLineParser.Rl_patentContext ctx) {
		RlLineObject.Patent patent = new RlLineObject.Patent();
		patent.patentNumber = ctx.patent_number().getText();

		String text = ctx.PATENT_DATE().getText();
		String[] split = text.split("-");

		patent.day = Integer.parseInt(split[0]);
		patent.month = split[1];
		patent.year = Integer.parseInt(split[2]);

		object.reference = patent;
	}

	@Override
	public void exitRl_book(@NotNull RlLineParser.Rl_bookContext ctx) {
		RlLineObject.Book book = new RlLineObject.Book();

		if (ctx.book_editors() != null) {
			List<RlLineParser.Book_editorContext> editors = ctx.book_editors().book_editor();
			for (RlLineParser.Book_editorContext editor : editors) {
				String text = editor.getText();
				book.editors.add(text);
			}
		}

		book.title = ctx.book_name().getText();

		String text = ctx.BOOK_YEAR().getText();
		//it should be in format (1985).\n
		book.year = Integer.parseInt(text.substring(1, text.length() - 3));

		RlLineParser.Book_pageContext book_pageContext = ctx.book_page();
		RlLineParser.Book_abstract_pageContext book_abstract_pageContext = ctx.book_abstract_page();

		if (book_pageContext != null) {
			if (book_pageContext.BOOK_V_WORD() != null) {
				book.pageString = book_pageContext.BOOK_V_WORD().getText();
			} else {
				if (book_pageContext.book_page_volume() != null) {
					book.volume = book_pageContext.book_page_volume().BOOK_V_WORD().getText();
				}
				book.page_start = book_pageContext.book_page_first().getText();
				book.page_end = book_pageContext.book_page_last().getText();
			}
		}else if (book_abstract_pageContext!=null){
			book.pageString = book_abstract_pageContext.getText();
		}

		if (ctx.book_city() != null)
			book.place = ctx.book_city().getText();
		if (ctx.book_publisher() != null)
			book.press = ctx.book_publisher().getText();

		object.reference = book;
	}


	@Override
	public void exitRl_unpublished(@NotNull RlLineParser.Rl_unpublishedContext ctx) {
		RlLineObject.Unpublished unp = new RlLineObject.Unpublished();
		String text = ctx.UP_YEAR_MONTH().getText();
		String text1 = text.substring(1, text.length() - 1);
		String[] split = text1.split("-");
		unp.month = split[0];
		unp.year = Integer.parseInt(split[1]);
		object.reference = unp;
	}

	@Override
	public void exitRl_submission(@NotNull RlLineParser.Rl_submissionContext ctx) {
		RlLineObject.Submission sub = new RlLineObject.Submission();
		String text = ctx.SUBMISSION_YEAR().getText();
		String text1 = text.substring(1, text.length() - 1);
		String[] split = text1.split("-");
		sub.month = split[0];
		sub.year = Integer.parseInt(split[1]);
		if (ctx.submission_db().EMBL() != null) {
			sub.db = RlLineObject.SubmissionDB.EMBL;
		} else if (ctx.submission_db().PDB() != null) {
			sub.db = RlLineObject.SubmissionDB.PDB;
		} else if (ctx.submission_db().UNIPROT() != null) {
			sub.db = RlLineObject.SubmissionDB.UNIPROTKB;
		} else if (ctx.submission_db().PIR() != null) {
			sub.db = RlLineObject.SubmissionDB.PIR;
		}
		object.reference = sub;
	}

	@Override
	public void exitRl_thesis(@NotNull RlLineParser.Rl_thesisContext ctx) {
		RlLineObject.Thesis thesis = new RlLineObject.Thesis();

		thesis.institute = ctx.thesis_institution().getText();
		String text = ctx.THESIS_YEAR().getText();
		String text1 = text.substring(1, text.length() - 1);
		thesis.year = Integer.parseInt(text1);
		if (ctx.thesis_country() != null) {
			thesis.country = ctx.thesis_country().getText();
		}

		object.reference = thesis;
	}

	public RlLineObject getObject() {
		return object;
	}
}

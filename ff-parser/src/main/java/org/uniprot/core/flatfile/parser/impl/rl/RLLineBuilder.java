package org.uniprot.core.flatfile.parser.impl.rl;

import static org.uniprot.core.flatfile.writer.impl.FFLineConstant.*;

import java.util.ArrayList;
import java.util.List;

import org.uniprot.core.citation.*;
import org.uniprot.core.flatfile.writer.LineType;
import org.uniprot.core.flatfile.writer.impl.FFLineWrapper;
import org.uniprot.core.flatfile.writer.impl.RLine;

import com.google.common.base.Strings;

public class RLLineBuilder implements RLine<Citation> {
    private final LineType lineType = LineType.RL;
    private final String linePrefix = lineType + DEFAUT_LINESPACE;

    @Override
    public List<String> buildLine(
            Citation citation, boolean includeFFMarkup, boolean showEvidence) {
        switch (citation.getCitationType()) {
            case BOOK:
                return book(citation, includeFFMarkup);

            case ELECTRONIC_ARTICLE:
                return electronic_article(citation, includeFFMarkup);

            case JOURNAL_ARTICLE:
                return journal_article(citation, includeFFMarkup);

            case PATENT:
                return patent(citation, includeFFMarkup);

            case SUBMISSION:
                return submission(citation, includeFFMarkup);

            case THESIS:
                return thesis(citation, includeFFMarkup);
            case UNPUBLISHED:
                return unpublished_observations(citation, includeFFMarkup);
        }
        return new ArrayList<>();
    }

    private List<String> journal_article(Citation citation, boolean includeFFMarkup) {
        JournalArticle journalArticle = (JournalArticle) citation;
        StringBuilder result = new StringBuilder();

        if (includeFFMarkup) result.append(linePrefix);
        result.append(journalArticle.getJournal().getName());
        result.append(SPACE);
        result.append(journalArticle.getVolume());
        result.append(":");
        result.append(journalArticle.getFirstPage());
        result.append("-");
        result.append(journalArticle.getLastPage());
        result.append("(");
        String date = journalArticle.getPublicationDate().getValue();
        result.append(date);
        result.append(")");

        result.append(STOP);
        if (includeFFMarkup) {
            return FFLineWrapper.buildLines(result.toString(), SPACE, linePrefix, LINE_LENGTH);
        } else {
            List<String> lines = new ArrayList<>();
            lines.add(result.toString());
            return lines;
        }
    }

    private List<String> electronic_article(Citation citation, boolean includeFFMarkup) {
        StringBuilder result = new StringBuilder();
        ElectronicArticle electronicArticle = (ElectronicArticle) citation;
        if (includeFFMarkup) result.append(linePrefix);
        if (electronicArticle.getJournal().getName().startsWith(" ")) {
            result.append("(er)");
            result.append(electronicArticle.getJournal().getName());
        } else {
            result.append("(er) ");
            result.append(electronicArticle.getJournal().getName());
        }
        if ((electronicArticle.getLocator() != null)
                && (!electronicArticle.getLocator().getValue().equals(""))) {
            result.append(" ");
            result.append(electronicArticle.getLocator().getValue());
        }
        if ((citation.getPublicationDate() != null)
                && (!citation.getPublicationDate().getValue().isEmpty())) {
            result.append("(").append(citation.getPublicationDate().getValue()).append(")");
        }
        result.append(STOP);
        List<String> lines = new ArrayList<>();
        lines.add(result.toString());
        return lines;
    }

    private List<String> patent(Citation citation, boolean includeFFMarkup) {
        StringBuilder result = new StringBuilder();
        if (includeFFMarkup) result.append(linePrefix);
        result.append("Patent number ");

        Patent patent = (Patent) citation;
        result.append(patent.getPatentNumber());
        result.append(", ");
        String date = patent.getPublicationDate().getValue();
        result.append(date);
        result.append(STOP);
        List<String> lines = new ArrayList<>();
        lines.add(result.toString());
        return lines;
    }

    private List<String> thesis(Citation citation, boolean includeFFMarkup) {
        StringBuilder result = new StringBuilder();
        Thesis thesis = (Thesis) citation;
        if (includeFFMarkup) result.append(linePrefix);
        result.append("Thesis (");
        String date = thesis.getPublicationDate().getValue();
        result.append(date);
        result.append("), ");
        result.append(thesis.getInstitute());
        if (!Strings.isNullOrEmpty(thesis.getAddress())) {
            result.append(", ");
            result.append(thesis.getAddress());
        }
        result.append(STOP);
        if (includeFFMarkup) {
            return FFLineWrapper.buildLines(result.toString(), SPACE, linePrefix, LINE_LENGTH);
        } else {
            List<String> lines = new ArrayList<>();
            lines.add(result.toString());
            return lines;
        }
    }

    private List<String> submission(Citation citation, boolean includeFFMarkup) {
        StringBuilder result = new StringBuilder();
        Submission submission = (Submission) citation;
        if (includeFFMarkup) result.append(linePrefix);
        result.append("Submitted");
        result.append(" (");

        String date = submission.getPublicationDate().getValue();
        result.append(date);

        if ((submission.getSubmissionDatabase().equals(SubmissionDatabase.SWISS_PROT))
                || (submission.getSubmissionDatabase().equals(SubmissionDatabase.UNIPROTKB))) {
            result.append(") to ");
        } else {
            result.append(") to the ");
        }
        result.append(submission.getSubmissionDatabase().getName());
        result.append(STOP);
        List<String> lines = new ArrayList<>();
        lines.add(result.toString());
        return lines;
    }

    private List<String> unpublished_observations(Citation citation, boolean includeFFMarkup) {
        StringBuilder result = new StringBuilder();
        if (includeFFMarkup) result.append(linePrefix);
        Unpublished observations = (Unpublished) citation;
        result.append("Unpublished observations (");
        String date = observations.getPublicationDate().getValue();
        result.append(date);
        result.append(")");
        result.append(STOP);
        List<String> lines = new ArrayList<>();
        lines.add(result.toString());
        return lines;
    }

    private List<String> book(Citation citation, boolean includeFFMarkup) {
        Book book = (Book) citation;
        // StringBuilder result = new StringBuilder();
        List<String> lines = new ArrayList<>();

        StringBuilder temp = new StringBuilder();
        if (includeFFMarkup) temp.append(linePrefix);
        temp.append("(In)");
        String end = "(eds.);";
        List<Author> editors = book.getEditors();
        for (int i = 0; i < editors.size(); i++) {
            String val = editors.get(i).getValue();
            if (temp.length() + val.length() >= LINE_LENGTH - 2) {
                lines.add(temp.toString());
                temp = new StringBuilder();
                if (includeFFMarkup) temp.append(linePrefix);
            } else {
                temp.append(SPACE);
            }
            temp.append(val);
            if (i != (editors.size() - 1)) {
                temp.append(COMA);
            }
        }
        if (editors.size() > 0) {
            if (includeFFMarkup && (temp.length() + end.length() >= LINE_LENGTH - 1)) {
                lines.add(temp.toString());
                temp = new StringBuilder();
                if (includeFFMarkup) temp.append(linePrefix);
            } else {
                temp.append(SPACE);
            }
            temp.append(end);
            if (includeFFMarkup) {
                lines.add(temp.toString());
                temp = new StringBuilder();
                temp.append(linePrefix);
            } else {
                temp.append(SPACE);
            }

        } else {
            temp.append(SPACE);
        }

        temp.append(book.getBookName());
        boolean bookAstract = bookHasAbstract(book.getBookName());
        boolean hasPages =
                (!Strings.isNullOrEmpty(book.getVolume())
                        || !Strings.isNullOrEmpty(book.getFirstPage())
                        || !Strings.isNullOrEmpty(book.getLastPage()));

        if (hasPages && !bookAstract) {

            if (!Strings.isNullOrEmpty(book.getFirstPage())
                    && book.getFirstPage().toUpperCase().startsWith("ABSTRACT#")) {
                temp.append(", ");
                temp.append(book.getFirstPage());
            } else {
                temp.append(", pp.");
                if (!Strings.isNullOrEmpty(book.getVolume())) {
                    temp.append(book.getVolume());
                    temp.append(":");
                }
                if (!Strings.isNullOrEmpty(book.getFirstPage())) {
                    temp.append(book.getFirstPage());
                }
                if (!Strings.isNullOrEmpty(book.getLastPage())) {
                    temp.append("-");
                    temp.append(book.getLastPage());
                }
            }
            if (!Strings.isNullOrEmpty(book.getPublisher())) {
                temp.append(", ");
                temp.append(book.getPublisher());
            }

            if (!Strings.isNullOrEmpty(book.getAddress())) {
                temp.append(", ");
                temp.append(book.getAddress());
            }
        }

        temp.append(" (").append(book.getPublicationDate().getValue()).append(")");
        temp.append(STOP);

        if (includeFFMarkup) {
            lines.addAll(FFLineWrapper.buildLines(temp.toString(), SPACE, linePrefix, LINE_LENGTH));
        } else {
            // List<String> lines = new ArrayList<>();
            lines.add(temp.toString());
        }
        return lines;
    }

    private boolean bookHasAbstract(String title) {
        if (Strings.isNullOrEmpty(title)) return false;
        String titleNoCase = title.toLowerCase();
        if (titleNoCase.contains(", abstract")) return true;
        return false;
    }
}

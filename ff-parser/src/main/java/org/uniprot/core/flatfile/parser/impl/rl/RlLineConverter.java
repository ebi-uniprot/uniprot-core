package org.uniprot.core.flatfile.parser.impl.rl;

import org.uniprot.core.citation.Citation;
import org.uniprot.core.citation.SubmissionDatabase;
import org.uniprot.core.citation.builder.*;
import org.uniprot.core.flatfile.parser.Converter;

public class RlLineConverter
        implements Converter<
                RlLineObject,
                AbstractCitationBuilder<
                        ? extends AbstractCitationBuilder<?, ?>, ? extends Citation>> {
    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    public AbstractCitationBuilder<? extends AbstractCitationBuilder<?, ?>, ? extends Citation>
            convert(RlLineObject f) {
        if (f.reference instanceof RlLineObject.JournalArticle) {
            return (AbstractCitationBuilder) convert((RlLineObject.JournalArticle) f.reference);
        } else if (f.reference instanceof RlLineObject.Book) {
            return (AbstractCitationBuilder) convert((RlLineObject.Book) f.reference);
        } else if (f.reference instanceof RlLineObject.EPub) {
            return (AbstractCitationBuilder) convert((RlLineObject.EPub) f.reference);
        } else if (f.reference instanceof RlLineObject.Patent) {
            return (AbstractCitationBuilder) convert((RlLineObject.Patent) f.reference);
        } else if (f.reference instanceof RlLineObject.Submission) {
            return (AbstractCitationBuilder) convert((RlLineObject.Submission) f.reference);
        } else if (f.reference instanceof RlLineObject.Thesis) {
            return (AbstractCitationBuilder) convert((RlLineObject.Thesis) f.reference);
        } else if (f.reference instanceof RlLineObject.Unpublished) {
            return (AbstractCitationBuilder) convert((RlLineObject.Unpublished) f.reference);
        } else throw new RuntimeException("Unable to parse RL line");
    }

    private JournalArticleBuilder convert(RlLineObject.JournalArticle ja) {
        JournalArticleBuilder builder = new JournalArticleBuilder();
        builder.journalName(ja.journal)
                .firstPage("" + ja.first_page)
                .lastPage("" + ja.last_page)
                .volume("" + ja.volume)
                .publicationDate("" + ja.year);
        return builder;
    }

    private BookBuilder convert(RlLineObject.Book b) {
        BookBuilder builder = new BookBuilder();
        if (b.page_start != null) builder.firstPage("" + b.page_start);
        if (b.page_end != null) builder.lastPage("" + b.page_end);
        builder.publicationDate("" + b.year);

        if (b.volume != null) builder.volume("" + b.volume);

        if ((b.page_start == null) && (b.page_end == null) && b.pageString != null) {
            builder.firstPage(b.pageString);
            builder.bookName(b.title);
        } else if (b.pageString != null) {
            builder.bookName(b.title + ", " + b.pageString);

        } else builder.bookName(b.title);

        builder.editors(b.editors);
        if (b.press != null && (!b.press.isEmpty())) {
            builder.publisher(b.press);
        }
        if ((b.place != null) && (!b.place.isEmpty())) {
            builder.address(b.place);
        }
        return builder;
    }

    private ElectronicArticleBuilder convert(RlLineObject.EPub ep) {
        String line = ep.title;
        ElectronicArticleBuilder builder = new ElectronicArticleBuilder();
        String pubDate = null;
        if (line.endsWith(")")) {
            int index = line.lastIndexOf('(');
            if (index != -1) {
                pubDate = line.substring(index + 1, line.length() - 1);
                line = line.substring(0, index).trim();
                builder.publicationDate(pubDate);
            }
        }
        if (line.startsWith("Plant Gene Register ")) {
            builder.journalName("Plant Gene Register");
            builder.locator(line.substring(line.lastIndexOf(" ") + 1, line.length()));

        } else if (line.startsWith("Worm Breeder's Gazette")) {
            builder.journalName("Worm Breeder's Gazette");
            builder.locator(line.substring(line.lastIndexOf(" ") + 1, line.length()));
        } else {
            builder.locator(ep.title);
        }
        return builder;
    }

    private PatentBuilder convert(RlLineObject.Patent patent) {
        PatentBuilder builder = new PatentBuilder();
        builder.patentNumber(patent.patentNumber);
        String day = "" + patent.day;
        if (patent.day < 10) {
            day = "0" + patent.day;
        }
        String date = day + "-" + patent.month + "-" + patent.year;
        builder.publicationDate(date);
        return builder;
    }

    private SubmissionBuilder convert(RlLineObject.Submission submission) {
        SubmissionBuilder builder = new SubmissionBuilder();
        SubmissionDatabase sdb = SubmissionDatabase.UNKNOWN;
        switch (submission.db) {
            case EMBL:
                sdb = SubmissionDatabase.EMBL_GENBANK_DDBJ;
                break;
            case UNIPROTKB:
                sdb = SubmissionDatabase.UNIPROTKB;
                break;
            case PDB:
                sdb = SubmissionDatabase.PDB;
                break;
            case PIR:
                sdb = SubmissionDatabase.PIR;
                break;
            default:
                throw new RuntimeException("submission db is not supported");
        }
        builder.submittedToDatabase(sdb);
        String date = submission.month + "-" + submission.year;
        builder.publicationDate(date);
        return builder;
    }

    private ThesisBuilder convert(RlLineObject.Thesis thesis) {
        ThesisBuilder builder = new ThesisBuilder();
        builder.publicationDate("" + thesis.year);
        builder.institute(thesis.institute);
        if (thesis.country != null) builder.address(thesis.country);
        return builder;
    }

    private UnpublishedBuilder convert(RlLineObject.Unpublished unpub) {
        UnpublishedBuilder builder = new UnpublishedBuilder();
        builder.publicationDate(unpub.month + "-" + unpub.year);
        return builder;
    }
}

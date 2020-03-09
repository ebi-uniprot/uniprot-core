package org.uniprot.core.flatfile.parser.impl.rl;

import org.uniprot.core.citation.Citation;
import org.uniprot.core.citation.SubmissionDatabase;
import org.uniprot.core.citation.impl.*;
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
        if (f.getReference() instanceof RlLineObject.JournalArticle) {
            return (AbstractCitationBuilder)
                    convert((RlLineObject.JournalArticle) f.getReference());
        } else if (f.getReference() instanceof RlLineObject.Book) {
            return (AbstractCitationBuilder) convert((RlLineObject.Book) f.getReference());
        } else if (f.getReference() instanceof RlLineObject.EPub) {
            return (AbstractCitationBuilder) convert((RlLineObject.EPub) f.getReference());
        } else if (f.getReference() instanceof RlLineObject.Patent) {
            return (AbstractCitationBuilder) convert((RlLineObject.Patent) f.getReference());
        } else if (f.getReference() instanceof RlLineObject.Submission) {
            return (AbstractCitationBuilder) convert((RlLineObject.Submission) f.getReference());
        } else if (f.getReference() instanceof RlLineObject.Thesis) {
            return (AbstractCitationBuilder) convert((RlLineObject.Thesis) f.getReference());
        } else if (f.getReference() instanceof RlLineObject.Unpublished) {
            return (AbstractCitationBuilder) convert((RlLineObject.Unpublished) f.getReference());
        } else throw new RuntimeException("Unable to parse RL line");
    }

    private JournalArticleBuilder convert(RlLineObject.JournalArticle ja) {
        JournalArticleBuilder builder = new JournalArticleBuilder();
        builder.journalName(ja.getJournal())
                .firstPage("" + ja.getFirstPage())
                .lastPage("" + ja.getLastPage())
                .volume("" + ja.getVolume())
                .publicationDate("" + ja.getYear());
        return builder;
    }

    private BookBuilder convert(RlLineObject.Book b) {
        BookBuilder builder = new BookBuilder();
        if (b.getPageStart() != null) builder.firstPage("" + b.getPageStart());
        if (b.getPageEnd() != null) builder.lastPage("" + b.getPageEnd());
        builder.publicationDate("" + b.getYear());

        if (b.getVolume() != null) builder.volume("" + b.getVolume());

        if ((b.getPageStart() == null) && (b.getPageEnd() == null) && b.getPageString() != null) {
            builder.firstPage(b.getPageString());
            builder.bookName(b.getTitle());
        } else if (b.getPageString() != null) {
            builder.bookName(b.getTitle() + ", " + b.getPageString());

        } else builder.bookName(b.getTitle());

        builder.editorsSet(b.getEditors());
        if (b.getPress() != null && (!b.getPress().isEmpty())) {
            builder.publisher(b.getPress());
        }
        if ((b.getPlace() != null) && (!b.getPlace().isEmpty())) {
            builder.address(b.getPlace());
        }
        return builder;
    }

    private ElectronicArticleBuilder convert(RlLineObject.EPub ep) {
        String line = ep.getTitle();
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
            builder.locator(ep.getTitle());
        }
        return builder;
    }

    private PatentBuilder convert(RlLineObject.Patent patent) {
        PatentBuilder builder = new PatentBuilder();
        builder.patentNumber(patent.getPatentNumber());
        String day = "" + patent.getDay();
        if (patent.getDay() < 10) {
            day = "0" + patent.getDay();
        }
        String date = day + "-" + patent.getMonth() + "-" + patent.getYear();
        builder.publicationDate(date);
        return builder;
    }

    private SubmissionBuilder convert(RlLineObject.Submission submission) {
        SubmissionBuilder builder = new SubmissionBuilder();
        SubmissionDatabase sdb = SubmissionDatabase.UNKNOWN;
        switch (submission.getDb()) {
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
        String date = submission.getMonth() + "-" + submission.getYear();
        builder.publicationDate(date);
        return builder;
    }

    private ThesisBuilder convert(RlLineObject.Thesis thesis) {
        ThesisBuilder builder = new ThesisBuilder();
        builder.publicationDate("" + thesis.getYear());
        builder.institute(thesis.getInstitute());
        if (thesis.getCountry() != null) builder.address(thesis.getCountry());
        return builder;
    }

    private UnpublishedBuilder convert(RlLineObject.Unpublished unpub) {
        UnpublishedBuilder builder = new UnpublishedBuilder();
        builder.publicationDate(unpub.getMonth() + "-" + unpub.getYear());
        return builder;
    }
}

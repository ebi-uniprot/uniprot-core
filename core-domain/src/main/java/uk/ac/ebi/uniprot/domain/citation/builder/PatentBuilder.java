package uk.ac.ebi.uniprot.domain.citation.builder;

import uk.ac.ebi.uniprot.domain.citation.Author;
import uk.ac.ebi.uniprot.domain.citation.CitationType;
import uk.ac.ebi.uniprot.domain.citation.CitationXrefs;
import uk.ac.ebi.uniprot.domain.citation.Patent;
import uk.ac.ebi.uniprot.domain.citation.PublicationDate;

import java.util.List;

public final class PatentBuilder extends AbstractCitationBuilder {
    public static PatentBuilder newInstance() {
        return new PatentBuilder();
    }

    private String patentNumber;

    public Patent build() {
        return new PatentImpl(authoringGroups, authors,
                xrefs, title, publicationDate, patentNumber);
    }

    public PatentBuilder patentNumber(String patentNumber) {
        this.patentNumber = patentNumber;
        return this;
    }

    class PatentImpl extends AbstractCitationImpl implements Patent {
        private final String patentNumber;

        PatentImpl(List<String> authoringGroups, List<Author> authors, CitationXrefs xrefs, String title,
            PublicationDate publicationDate, String patentNumber) {
            super(CitationType.PATENT, authoringGroups, authors, xrefs, title, publicationDate);
            this.patentNumber = patentNumber;
        }

        @Override
        public String getPatentNumber() {
            return patentNumber;
        }

    }

}

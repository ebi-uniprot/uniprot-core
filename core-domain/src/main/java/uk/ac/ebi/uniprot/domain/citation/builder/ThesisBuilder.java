package uk.ac.ebi.uniprot.domain.citation.builder;

import uk.ac.ebi.uniprot.domain.citation.Author;
import uk.ac.ebi.uniprot.domain.citation.CitationType;
import uk.ac.ebi.uniprot.domain.citation.CitationXrefs;
import uk.ac.ebi.uniprot.domain.citation.PublicationDate;
import uk.ac.ebi.uniprot.domain.citation.Thesis;

import java.util.List;

public final class ThesisBuilder extends AbstractCitationBuilder {

    public static ThesisBuilder newInstance() {
        return new ThesisBuilder();
    }
    
    private String institute;
    private String address;
    
    public Thesis build() {
        return new ThesisImpl(authoringGroups, authors,
                xrefs, title, publicationDate, institute, address);
    }

    public ThesisBuilder institute(String institute) {
        this.institute = institute;
        return this;
    }
    
    public ThesisBuilder address(String address) {
        this.address = address;
        return this;
    }
    
    class ThesisImpl extends AbstractCitationImpl implements Thesis {
        private final String institute;
        private final String address;
       
        public ThesisImpl(List<String> authoringGroups, List<Author> authors, CitationXrefs xrefs, String title,
            PublicationDate publicationDate, String institute, String address) {
            super(CitationType.THESIS, authoringGroups, authors, xrefs, title, publicationDate);
            this.institute = institute;
            this.address = address;
        }

        @Override
        public String getInstitute() {
            return institute;
        }

        @Override
        public String getAddress() {
            return address;
        }

    }

}

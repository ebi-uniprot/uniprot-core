package uk.ac.ebi.uniprot.domain.citation.builder;

import uk.ac.ebi.uniprot.domain.citation.Author;
import uk.ac.ebi.uniprot.domain.citation.CitationType;
import uk.ac.ebi.uniprot.domain.citation.CitationXrefs;
import uk.ac.ebi.uniprot.domain.citation.PublicationDate;
import uk.ac.ebi.uniprot.domain.citation.UnpublishedObservations;

import java.util.List;

public final class UnpublishedObservationsBuilder extends AbstractCitationBuilder<UnpublishedObservations> {
    public static UnpublishedObservationsBuilder newInstance() {
        return new UnpublishedObservationsBuilder();
    }

    public UnpublishedObservations build() {
        return new UnpublishedObservationsImpl(authoringGroups, authors,
                xrefs, title, publicationDate);
    }


    
    class UnpublishedObservationsImpl extends AbstractCitationImpl implements UnpublishedObservations {

        public UnpublishedObservationsImpl(List<String> authoringGroups, 
                List<Author> authors, CitationXrefs xrefs,
            String title, PublicationDate publicationDate) {
            super(CitationType.UNPUBLISHED_OBSERVATIONS, authoringGroups, authors, xrefs, title, publicationDate);

        }
    }

}

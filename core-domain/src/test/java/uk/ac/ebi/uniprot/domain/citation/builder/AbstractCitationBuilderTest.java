package uk.ac.ebi.uniprot.domain.citation.builder;

import org.junit.jupiter.api.Test;
import uk.ac.ebi.uniprot.domain.DBCrossReference;
import uk.ac.ebi.uniprot.domain.Value;
import uk.ac.ebi.uniprot.domain.citation.CitationType;
import uk.ac.ebi.uniprot.domain.citation.CitationXrefType;
import uk.ac.ebi.uniprot.domain.citation.CitationXrefs;
import uk.ac.ebi.uniprot.domain.citation.impl.AbstractCitationImpl;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

class AbstractCitationBuilderTest {
    private static final CitationType CITATION_TYPE = CitationType.UNPUBLISHED;

    @Test
    void check() {
        DBCrossReference<CitationXrefType> xRef1 = new DBCrossReferenceBuilder<CitationXrefType>()
                .databaseType(CitationXrefType.PUBMED)
                .id("id1")
                .build();
        DBCrossReference<CitationXrefType> xRef2 = new DBCrossReferenceBuilder<CitationXrefType>()
                .databaseType(CitationXrefType.AGRICOLA)
                .id("id2")
                .build();
        CitationXrefs xrefs = new CitationsXrefsBuilder()
                .addXRef(xRef1)
                .addXRef(xRef2)
                .build();

        String title = "a title";
        String publicationDate = "2015-MAY";
        List<String> authoringGroup = asList("G1", "G2");
        List<String> authors = asList("Tom", "John");
        TestableCitation citation = new TestableCitationBuilder()
                .title(title)
                .publicationDate(publicationDate)
                .authoringGroups(authoringGroup)
                .authors(authors)
                .citationXrefs(xrefs)
                .build();

        assertThat(citation.getTitle(), is(title));
        assertThat(citation.getPublicationDate().getValue(), is(publicationDate));
        assertThat(citation.getAuthoringGroup(), is(authoringGroup));
        assertThat(citation.getAuthors().stream().map(Value::getValue).collect(Collectors.toList()), is(authors));
        assertThat(citation.getCitationType(), is(CITATION_TYPE));
        assertThat(citation.hasCitationXrefs(), is(true));
        assertThat(citation.getCitationXrefs().getXrefs(), contains(xRef1, xRef2));
    }

    private static class TestableCitationBuilder extends AbstractCitationBuilder<TestableCitationBuilder, TestableCitation> {
        @Override
        public TestableCitation build() {
            return new TestableCitation(this);
        }

        @Override
        public TestableCitationBuilder from(TestableCitation instance) {
            init(instance);
            return this;
        }

        @Override
        protected TestableCitationBuilder getThis() {
            return this;
        }
    }

    private static class TestableCitation extends AbstractCitationImpl {
        TestableCitation(TestableCitationBuilder builder) {
            super(CITATION_TYPE, builder.authoringGroups, builder.authors, builder.xrefs, builder.title, builder.publicationDate);
        }
    }
}

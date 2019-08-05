package org.uniprot.core.citation.builder;

import org.junit.jupiter.api.Test;
import org.uniprot.core.DBCrossReference;
import org.uniprot.core.Value;
import org.uniprot.core.builder.DBCrossReferenceBuilder;
import org.uniprot.core.citation.Citation;
import org.uniprot.core.citation.CitationType;
import org.uniprot.core.citation.CitationXrefType;
import org.uniprot.core.citation.builder.AbstractCitationBuilder;
import org.uniprot.core.citation.impl.AbstractCitationImpl;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

class AbstractCitationBuilderTest {
    private static final CitationType CITATION_TYPE = CitationType.UNPUBLISHED;
    private static final String TITLE = "Some title";
    private static final String PUBLICATION_DATE = "2015-MAY";
    private static final List<String> GROUPS = asList("T1", "T2");
    private static final List<String> AUTHORS = asList("Tom", "John");
    private static final DBCrossReference<CitationXrefType> XREF1 = new DBCrossReferenceBuilder<CitationXrefType>()
            .databaseType(CitationXrefType.PUBMED)
            .id("id1")
            .build();
    private static final DBCrossReference<CitationXrefType> XREF2 = new DBCrossReferenceBuilder<CitationXrefType>()
            .databaseType(CitationXrefType.AGRICOLA)
            .id("id2")
            .build();

    @Test
    void checkAbstractCitationCreationIsAsExpected() {
        String title = "a title";
        String publicationDate = "2015-MAY";
        List<String> authoringGroup = asList("G1", "G2");
        List<String> authors = asList("Tom", "John");
        TestableCitation citation = new TestableCitationBuilder()
                .title(title)
                .publicationDate(publicationDate)
                .authoringGroups(authoringGroup)
                .authors(authors)
                .citationXrefs(asList(XREF1, XREF2))
                .build();

        assertThat(citation.getTitle(), is(title));
        assertThat(citation.getPublicationDate().getValue(), is(publicationDate));
        assertThat(citation.getAuthoringGroup(), is(authoringGroup));
        assertThat(citation.getAuthors().stream().map(Value::getValue).collect(Collectors.toList()), is(authors));
        assertThat(citation.getCitationType(), is(CITATION_TYPE));
        assertThat(citation.hasCitationXrefs(), is(true));
        assertThat(citation.getCitationXrefs(), contains(XREF1, XREF2));
    }

    void buildCitationParameters(AbstractCitationBuilder<?, ?> builder) {
        builder
                .title(TITLE)
                .publicationDate(PUBLICATION_DATE)
                .authoringGroups(GROUPS)
                .authors(AUTHORS)
                .citationXrefs(asList(new DBCrossReferenceBuilder<CitationXrefType>()
                                              .databaseType(CitationXrefType.PUBMED)
                                              .id("id1")
                                              .build(),
                                      new DBCrossReferenceBuilder<CitationXrefType>()
                                              .databaseType(CitationXrefType.AGRICOLA)
                                              .id("id2")
                                              .build()))
                .build();
    }

    void verifyCitation(Citation citation, CitationType citationType) {
        assertThat(citation.getTitle(), is(TITLE));
        assertThat(citation.getPublicationDate().getValue(), is(PUBLICATION_DATE));
        assertThat(citation.getAuthoringGroup(), is(GROUPS));
        assertThat(citation.getAuthors().stream().map(Value::getValue).collect(Collectors.toList()), is(AUTHORS));
        assertThat(citation.getCitationType(), is(citationType));
        assertThat(citation.hasCitationXrefs(), is(true));
        assertThat(citation.getCitationXrefs(), contains(XREF1, XREF2));
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

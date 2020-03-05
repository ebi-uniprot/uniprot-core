package org.uniprot.core.citation.builder;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.core.Is.is;

import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Nonnull;

import org.junit.jupiter.api.Test;
import org.uniprot.core.CrossReference;
import org.uniprot.core.Value;
import org.uniprot.core.builder.CrossReferenceBuilder;
import org.uniprot.core.citation.Citation;
import org.uniprot.core.citation.CitationDatabase;
import org.uniprot.core.citation.CitationType;
import org.uniprot.core.citation.impl.AbstractCitationImpl;

class AbstractCitationBuilderTest {
    static final CitationType CITATION_TYPE = CitationType.UNPUBLISHED;
    static final String TITLE = "Some title";
    static final String PUBLICATION_DATE = "2015-MAY";
    static final List<String> GROUPS = asList("T1", "T2");
    static final List<String> AUTHORS = asList("Tom", "John");
    static final CrossReference<CitationDatabase> XREF1 =
            new CrossReferenceBuilder<CitationDatabase>()
                    .database(CitationDatabase.PUBMED)
                    .id("id1")
                    .build();
    static final CrossReference<CitationDatabase> XREF2 =
            new CrossReferenceBuilder<CitationDatabase>()
                    .database(CitationDatabase.AGRICOLA)
                    .id("id2")
                    .build();

    @Test
    void checkAbstractCitationCreationIsAsExpected() {
        String title = "a title";
        String publicationDate = "2015-MAY";
        List<String> authoringGroup = asList("G1", "G2");
        List<String> authors = asList("Tom", "John");
        TestableCitation citation =
                new TestableCitationBuilder()
                        .title(title)
                        .publicationDate(publicationDate)
                        .authoringGroupsSet(authoringGroup)
                        .authorsSet(authors)
                        .citationCrossReferencesSet(asList(XREF1, XREF2))
                        .build();

        assertThat(citation.getTitle(), is(title));
        assertThat(citation.getPublicationDate().getValue(), is(publicationDate));
        assertThat(citation.getAuthoringGroups(), is(authoringGroup));
        assertThat(
                citation.getAuthors().stream().map(Value::getValue).collect(Collectors.toList()),
                is(authors));
        assertThat(citation.getCitationType(), is(CITATION_TYPE));
        assertThat(citation.hasCitationCrossReferences(), is(true));
        assertThat(citation.getCitationCrossReferences(), contains(XREF1, XREF2));
    }

    void buildCitationParameters(AbstractCitationBuilder<?, ?> builder) {
        builder.title(TITLE)
                .publicationDate(PUBLICATION_DATE)
                .authoringGroupsSet(GROUPS)
                .authorsSet(AUTHORS)
                .citationCrossReferencesSet(
                        asList(
                                new CrossReferenceBuilder<CitationDatabase>()
                                        .database(CitationDatabase.PUBMED)
                                        .id("id1")
                                        .build(),
                                new CrossReferenceBuilder<CitationDatabase>()
                                        .database(CitationDatabase.AGRICOLA)
                                        .id("id2")
                                        .build()))
                .build();
    }

    void verifyCitation(Citation citation, CitationType citationType) {
        assertThat(citation.getTitle(), is(TITLE));
        assertThat(citation.getPublicationDate().getValue(), is(PUBLICATION_DATE));
        assertThat(citation.getAuthoringGroups(), is(GROUPS));
        assertThat(
                citation.getAuthors().stream().map(Value::getValue).collect(Collectors.toList()),
                is(AUTHORS));
        assertThat(citation.getCitationType(), is(citationType));
        assertThat(citation.hasCitationCrossReferences(), is(true));
        assertThat(citation.getCitationCrossReferences(), contains(XREF1, XREF2));
    }

    private static class TestableCitationBuilder
            extends AbstractCitationBuilder<TestableCitationBuilder, TestableCitation> {
        @Override
        public @Nonnull TestableCitation build() {
            return new TestableCitation(this);
        }

        public TestableCitationBuilder from(TestableCitation instance) {
            AbstractCitationBuilder.init(this, instance);
            return this;
        }

        @Override
        protected @Nonnull TestableCitationBuilder getThis() {
            return this;
        }
    }

    private static class TestableCitation extends AbstractCitationImpl {
        TestableCitation(TestableCitationBuilder builder) {
            super(
                    CITATION_TYPE,
                    builder.authoringGroups,
                    builder.authors,
                    builder.citationCrossReferences,
                    builder.title,
                    builder.publicationDate);
        }
    }
}

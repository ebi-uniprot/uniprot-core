package org.uniprot.core.citation.impl;

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
import org.uniprot.core.citation.Citation;
import org.uniprot.core.citation.CitationDatabase;
import org.uniprot.core.citation.CitationType;
import org.uniprot.core.impl.CrossReferenceBuilder;

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

    static final CrossReference<CitationDatabase> XREF3 =
            new CrossReferenceBuilder<CitationDatabase>()
                    .database(CitationDatabase.DOI)
                    .id("doi-id3")
                    .build();

    @Test
    void checkCitationIdIsPubMed() {
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
                        .citationCrossReferencesAdd(XREF1)
                        .build();
        assertThat(citation.getId(), is("id1"));
    }


    @Test
    void checkCitationIdIsAgricola() {
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
                        .citationCrossReferencesAdd(XREF2)
                        .build();
        assertThat(citation.getId(), is("id2"));
    }

    @Test
    void checkCitationIdIsDoiHashGenerated() {
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
                        .citationCrossReferencesAdd(XREF3)
                        .build();
        assertThat(citation.getId(), is("CI-2V34M1IJ67J60"));
        assertThat(citation.generateHash("doi-id3"), is("CI-2V34M1IJ67J60"));
    }

    @Test
    void checkCitationIdIsHashGenerated() {
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
                        .build();
        assertThat(citation.getId(), is("CI-7TL5R4DGOC4O8"));
    }

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

    @Test
    void checkHasCitationsWorksCorrectly() {
        String title = "a title";
        String publicationDate = "2015-MAY";
        List<String> authors = asList("Tom", "John");
        TestableCitation citation =
                new TestableCitationBuilder()
                        .title(title)
                        .publicationDate(publicationDate)
                        .authorsSet(authors)
                        .build();

        assertThat(citation.hasCitationCrossReferences(), is(false));
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
        assertThat(citation.getId(), is("id1"));
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
        private static final long serialVersionUID = -8191941562478700666L;

        TestableCitation(TestableCitationBuilder builder) {
            super(
                    CITATION_TYPE,
                    builder.authoringGroups,
                    builder.authors,
                    builder.citationCrossReferences,
                    builder.title,
                    builder.publicationDate);
            super.id = generateId();
        }
    }
}

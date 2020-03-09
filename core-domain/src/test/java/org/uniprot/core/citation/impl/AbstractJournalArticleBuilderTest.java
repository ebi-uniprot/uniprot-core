package org.uniprot.core.citation.impl;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.core.Is.is;

import java.util.stream.Collectors;

import javax.annotation.Nonnull;

import org.junit.jupiter.api.Test;
import org.uniprot.core.Value;
import org.uniprot.core.citation.CitationType;
import org.uniprot.core.citation.JournalArticle;

/**
 * @author lgonzales
 * @since 2020-01-28
 */
class AbstractJournalArticleBuilderTest extends AbstractCitationBuilderTest {
    private static final String JOURNAL_NAME = "journal name";
    private static final String FIRST_PAGE = "page 1";
    private static final String LAST_PAGE = "page 20";
    private static final String VOLUME = "volume 50";

    @Test
    void checkAbstractJournalArticleCreationIsAsExpected() {
        AbstractJournalArticleBuilderTest.TestableJournalArticle journalArticle =
                new TestableJournalArticleBuilder()
                        .title(TITLE)
                        .publicationDate(PUBLICATION_DATE)
                        .authoringGroupsSet(GROUPS)
                        .authorsSet(AUTHORS)
                        .citationCrossReferencesSet(asList(XREF1, XREF2))
                        .journalName(JOURNAL_NAME)
                        .firstPage(FIRST_PAGE)
                        .lastPage(LAST_PAGE)
                        .volume(VOLUME)
                        .build();

        assertThat(journalArticle.getTitle(), is(TITLE));
        assertThat(journalArticle.getPublicationDate().getValue(), is(PUBLICATION_DATE));
        assertThat(journalArticle.getAuthoringGroups(), is(GROUPS));
        assertThat(
                journalArticle.getAuthors().stream()
                        .map(Value::getValue)
                        .collect(Collectors.toList()),
                is(AUTHORS));
        assertThat(journalArticle.getCitationType(), is(CITATION_TYPE));
        assertThat(journalArticle.hasCitationCrossReferences(), is(true));
        assertThat(journalArticle.getCitationCrossReferences(), contains(XREF1, XREF2));

        assertThat(journalArticle.getJournal().getName(), is(JOURNAL_NAME));
        assertThat(journalArticle.getFirstPage(), is(FIRST_PAGE));
        assertThat(journalArticle.getLastPage(), is(LAST_PAGE));
        assertThat(journalArticle.getVolume(), is(VOLUME));
    }

    void buildJournalArticleParameters(AbstractJournalArticleBuilder<?, ?> builder) {
        super.buildCitationParameters(builder);
        builder.journalName(JOURNAL_NAME)
                .firstPage(FIRST_PAGE)
                .lastPage(LAST_PAGE)
                .volume(VOLUME)
                .build();
    }

    void verifyJournalArticle(JournalArticle journalArticle, CitationType citationType) {
        super.verifyCitation(journalArticle, citationType);
        assertThat(journalArticle.getJournal().getName(), is(JOURNAL_NAME));
        assertThat(journalArticle.getFirstPage(), is(FIRST_PAGE));
        assertThat(journalArticle.getLastPage(), is(LAST_PAGE));
        assertThat(journalArticle.getVolume(), is(VOLUME));
    }

    private static class TestableJournalArticleBuilder
            extends AbstractJournalArticleBuilder<
                    AbstractJournalArticleBuilderTest.TestableJournalArticleBuilder,
                    AbstractJournalArticleBuilderTest.TestableJournalArticle> {
        @Override
        public @Nonnull AbstractJournalArticleBuilderTest.TestableJournalArticle build() {
            return new AbstractJournalArticleBuilderTest.TestableJournalArticle(this);
        }

        public AbstractJournalArticleBuilderTest.TestableJournalArticleBuilder from(
                AbstractJournalArticleBuilderTest.TestableJournalArticle instance) {
            AbstractJournalArticleBuilder.init(this, instance);
            return this;
        }

        @Override
        protected @Nonnull AbstractJournalArticleBuilderTest.TestableJournalArticleBuilder
                getThis() {
            return this;
        }
    }

    private static class TestableJournalArticle extends AbstractJournalArticleImpl {
        TestableJournalArticle(TestableJournalArticleBuilder builder) {
            super(
                    CITATION_TYPE,
                    builder.authoringGroups,
                    builder.authors,
                    builder.citationCrossReferences,
                    builder.title,
                    builder.publicationDate,
                    builder.journalName,
                    builder.firstPage,
                    builder.lastPage,
                    builder.volume);
        }
    }
}

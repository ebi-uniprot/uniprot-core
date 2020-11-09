package org.uniprot.cv.keyword;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.uniprot.core.cv.keyword.KeywordEntry;
import org.uniprot.cv.keyword.impl.KeywordRepoImpl;

import java.util.Collection;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.hamcrest.core.IsNull.notNullValue;

/**
 * Created 09/11/2020
 *
 * @author Edd
 */
class KeywordRepoTest {
    private static KeywordRepo repo;

    @BeforeAll
    static void setUp() {
        repo = new KeywordRepoImpl("keyword/keywlist.txt");
    }

    @Test
    void isNotNull() {
        assertThat(repo, is(notNullValue()));
    }

    @Test
    void canGetByAccession() {
        KeywordEntry entry = repo.getByAccession("KW-0869");
        assertThat(entry, is(notNullValue()));
        assertThat(entry.getKeyword().getId(), is("KW-0869"));
    }

    @Test
    void getByMissingAccessionReturnsNull() {
        KeywordEntry entry = repo.getByAccession("KW-WRONG");
        assertThat(entry, is(nullValue()));
    }

    @Test
    void canGetAll() {
        Collection<KeywordEntry> entries = repo.getAll();
        assertThat(entries, is(not(empty())));
    }

    @Test
    void canGetAllCategories() {
        Collection<KeywordEntry> entries = repo.getAllCategories();
        assertThat(entries, is(not(empty())));
        assertThat(
                entries,
                is(not(repo.getAll()))); // categories and "all" should at least be different
    }

    @Test
    void canGetByName() {
        KeywordEntry entries = repo.getByName("Chloride channel");
        assertThat(entries, is(notNullValue()));
        assertThat(entries.getKeyword().getId(), is("KW-0869"));
    }
}

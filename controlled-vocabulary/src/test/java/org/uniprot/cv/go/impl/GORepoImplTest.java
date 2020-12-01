package org.uniprot.cv.go.impl;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.uniprot.core.cv.go.GeneOntologyEntry;
import org.uniprot.core.cv.go.GoTerm;
import org.uniprot.core.cv.go.impl.GeneOntologyEntryBuilder;
import org.uniprot.cv.go.RelationshipType;

/**
 * Created 25/11/2020
 *
 * @author Edd
 */
class GORepoImplTest {

    private static GORepoImpl repo;

    @BeforeAll
    static void setUp() {
        repo = new GORepoImpl("src/test/resources/go");
    }

    @Test
    void canGetById() {
        assertThat(
                repo.getById("GO:0048308"),
                is(
                        new GeneOntologyEntryBuilder()
                                .id("GO:0048308")
                                .name("low-affinity zinc ion transmembrane transporter activity")
                                .build()));
    }

    @Test
    void isALoadedCorrectly() {
        assertThat(
                repo.getIsA("GO:0000001"),
                containsInAnyOrder(
                        new GeneOntologyEntryBuilder()
                                .id("GO:0048308")
                                .name("low-affinity zinc ion transmembrane transporter activity")
                                .build(),
                        new GeneOntologyEntryBuilder()
                                .id("GO:0048311")
                                .name("alpha-1,6-mannosyltransferase activity")
                                .build()));
    }

    @Test
    void partOfLoadedCorrectly() {
        assertThat(
                repo.getPartOf("GO:0000101"),
                contains(
                        new GeneOntologyEntryBuilder()
                                .id("GO:0000200")
                                .name("nuclear euchromatin")
                                .build()));
    }

    @Test
    void correctAncestorsLoaded() {
        Set<GeneOntologyEntry> ancestors =
                repo.getAncestors(
                        "GO:0000001", asList(RelationshipType.IS_A, RelationshipType.PART_OF));
        Set<String> ancestorIds = ancestors.stream().map(GoTerm::getId).collect(Collectors.toSet());
        assertThat(
                ancestorIds,
                containsInAnyOrder(
                        "GO:0048308", "GO:0048311", "GO:0048168", "GO:0000100", "GO:0000200"));
    }

    @Test
    void ancestorsCacheFunctioningCorrectly() {
        int ancestorCacheSize = repo.ancestorCache.keySet().size();

        Set<GeneOntologyEntry> firstRetrieval =
                repo.getAncestors("GO:0000001", Collections.singletonList(RelationshipType.IS_A));

        assertThat(repo.ancestorCache.keySet(), hasSize(ancestorCacheSize + 1));
        int sizeAfterOneRetrieval = ancestorCacheSize + 1;

        Set<GeneOntologyEntry> secondRetrieval =
                repo.getAncestors("GO:0000001", Collections.singletonList(RelationshipType.IS_A));

        // retrieval results are actually the same objects (meaning cache is working)
        assertThat(firstRetrieval == secondRetrieval, is(true));

        // ... and cache size remains the same (meaning cache is working)
        assertThat(repo.ancestorCache.keySet(), hasSize(sizeAfterOneRetrieval));

        Set<GeneOntologyEntry> thirdRetrieval =
                repo.getAncestors("GO:0000001", Collections.singletonList(RelationshipType.IS_A));

        // retrieval results are the same objects again (meaning cache is working)
        assertThat(secondRetrieval == thirdRetrieval, is(true));

        // ... and cache size remains the same (meaning cache is working)
        assertThat(repo.ancestorCache.keySet(), hasSize(sizeAfterOneRetrieval));
    }

    @Test
    void defaultAncestorsUsesIsAAndPartOf() {
        Set<GeneOntologyEntry> ancestors = repo.getAncestors("GO:0000001");

        assertThat(
                ancestors.stream().map(GoTerm::getId).collect(Collectors.toSet()),
                containsInAnyOrder(
                        "GO:0000200", "GO:0000100", "GO:0048311", "GO:0048168", "GO:0048308"));
    }
}

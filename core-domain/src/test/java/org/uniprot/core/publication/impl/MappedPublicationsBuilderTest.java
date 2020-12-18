package org.uniprot.core.publication.impl;

import org.junit.jupiter.api.Test;
import org.uniprot.core.publication.ComputationallyMappedReference;
import org.uniprot.core.publication.MappedPublications;
import org.uniprot.core.publication.UniProtKBMappedReference;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.collection.IsEmptyCollection.empty;

/**
 * @author sahmad
 * @created 18/12/2020
 */
public class MappedPublicationsBuilderTest {

    @Test
    void checkEmptyReference() {
        MappedPublications obj = new MappedPublicationsBuilder().build();
        assertThat(obj, is(notNullValue()));
        assertThat(obj.getUnreviewedMappedReference(), is(nullValue()));
        assertThat(obj.getReviewedMappedReference(), is(nullValue()));
        assertThat(obj.getCommunityMappedReferences(), is(empty()));
        assertThat(obj.getComputationalMappedReferences(), is(empty()));
    }

    @Test
    void canSetUnreviewedMappedReference(){
        MappedPublicationsBuilder builder = new MappedPublicationsBuilder();
        UniProtKBMappedReference reference = new UniProtKBMappedReferenceImpl();
        builder.unreviewedMappedReference(reference);
        MappedPublications mappedPubs = builder.build();
        assertThat(mappedPubs, is(notNullValue()));
        assertThat(mappedPubs.getUnreviewedMappedReference(), is(reference));
        assertThat(mappedPubs.getReviewedMappedReference(), is(nullValue()));
        assertThat(mappedPubs.getComputationalMappedReferences(), is(empty()));
        assertThat(mappedPubs.getCommunityMappedReferences(), is(empty()));
    }

    @Test
    void canSetReviewedMappedReference(){
        MappedPublicationsBuilder builder = new MappedPublicationsBuilder();
        UniProtKBMappedReference reference = new UniProtKBMappedReferenceImpl();
        builder.reviewedMappedReference(reference);
        MappedPublications mappedPubs = builder.build();
        assertThat(mappedPubs, is(notNullValue()));
        assertThat(mappedPubs.getReviewedMappedReference(), is(reference));
        assertThat(mappedPubs.getUnreviewedMappedReference(), is(nullValue()));
        assertThat(mappedPubs.getComputationalMappedReferences(), is(empty()));
        assertThat(mappedPubs.getCommunityMappedReferences(), is(empty()));
    }

    @Test
    void canAddComputationalMappedReference() {
        ComputationallyMappedReference reference = new ComputationallyMappedReferenceImpl();
        MappedPublicationsBuilder builder = new MappedPublicationsBuilder();
        builder.computationalMappedReferencesAdd(reference);
        MappedPublications mappedPubs = builder.build();

        assertThat(mappedPubs.getComputationalMappedReferences(), hasSize(1));
        assertThat(mappedPubs.getReviewedMappedReference(), is(nullValue()));
        assertThat(mappedPubs.getUnreviewedMappedReference(), is(nullValue()));
        assertThat(mappedPubs.getCommunityMappedReferences(), is(empty()));
    }
}

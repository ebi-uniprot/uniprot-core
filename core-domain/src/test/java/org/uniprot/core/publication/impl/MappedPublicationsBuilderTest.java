package org.uniprot.core.publication.impl;

import org.junit.jupiter.api.Test;
import org.uniprot.core.publication.CommunityMappedReference;
import org.uniprot.core.publication.ComputationallyMappedReference;
import org.uniprot.core.publication.MappedPublications;
import org.uniprot.core.publication.UniProtKBMappedReference;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * @author sahmad
 * @created 18/12/2020
 */
public class MappedPublicationsBuilderTest {

    @Test
    void checkEmptyReference() {
        MappedPublications obj = new MappedPublicationsBuilder().build();
        assertThat(obj, is(notNullValue()));
        assertThat(obj.getUniProtKBMappedReference(), is(nullValue()));
        assertThat(obj.getCommunityMappedReferences(), is(empty()));
        assertThat(obj.getComputationallyMappedReferences(), is(empty()));
    }

    @Test
    void canSetUniProtKBMappedReference(){
        MappedPublicationsBuilder builder = new MappedPublicationsBuilder();
        UniProtKBMappedReference reference = new UniProtKBMappedReferenceImpl();
        builder.uniProtKBMappedReference(reference);
        MappedPublications mappedPubs = builder.build();
        assertThat(mappedPubs, is(notNullValue()));
        assertThat(mappedPubs.getUniProtKBMappedReference(), is(reference));
        assertThat(mappedPubs.getComputationallyMappedReferences(), is(empty()));
        assertThat(mappedPubs.getCommunityMappedReferences(), is(empty()));
    }

    @Test
    void canAddComputationalMappedReference() {
        ComputationallyMappedReference reference = new ComputationallyMappedReferenceImpl();
        MappedPublicationsBuilder builder = new MappedPublicationsBuilder();
        builder.computationalMappedReferencesAdd(reference);
        MappedPublications mappedPubs = builder.build();

        assertThat(mappedPubs.getComputationallyMappedReferences(), hasSize(1));
        assertThat(mappedPubs.getUniProtKBMappedReference(), is(nullValue()));
        assertThat(mappedPubs.getCommunityMappedReferences(), is(empty()));
    }

    @Test
    void canSetComputationalMappedReference() {
        ComputationallyMappedReference reference1 = new ComputationallyMappedReferenceImpl();
        ComputationallyMappedReference reference2 = new ComputationallyMappedReferenceImpl();
        MappedPublicationsBuilder builder = new MappedPublicationsBuilder();
        builder.computationalMappedReferencesSet(Arrays.asList(reference1, reference2));
        MappedPublications mappedPubs = builder.build();

        assertThat(mappedPubs.getComputationallyMappedReferences(), hasSize(2));
        assertThat(mappedPubs.getUniProtKBMappedReference(), is(nullValue()));
        assertThat(mappedPubs.getCommunityMappedReferences(), is(empty()));
    }

    @Test
    void canAddCommunityMappedReference() {
        CommunityMappedReference reference = new CommunityMappedReferenceImpl();
        MappedPublicationsBuilder builder = new MappedPublicationsBuilder();
        builder.communityMappedReferencesAdd(reference);
        MappedPublications mappedPubs = builder.build();

        assertThat(mappedPubs.getCommunityMappedReferences(), hasSize(1));
        assertThat(mappedPubs.getUniProtKBMappedReference(), is(nullValue()));
        assertThat(mappedPubs.getComputationallyMappedReferences(), is(empty()));
    }

    @Test
    void canSetCommunityMappedReference() {
        CommunityMappedReference reference1 = new CommunityMappedReferenceImpl();
        CommunityMappedReference reference2 = new CommunityMappedReferenceImpl();
        MappedPublicationsBuilder builder = new MappedPublicationsBuilder();
        builder.communityMappedReferencesSet(Arrays.asList(reference1, reference2));
        MappedPublications mappedPubs = builder.build();

        assertThat(mappedPubs.getCommunityMappedReferences(), hasSize(2));
        assertThat(mappedPubs.getUniProtKBMappedReference(), is(nullValue()));
        assertThat(mappedPubs.getComputationallyMappedReferences(), is(empty()));
    }

    @Test
    void canCreateBuilderFromInstance() {
        MappedPublications obj = new MappedPublicationsBuilder().build();
        MappedPublicationsBuilder builder = MappedPublicationsBuilder.from(obj);
        assertNotNull(builder);
    }
}

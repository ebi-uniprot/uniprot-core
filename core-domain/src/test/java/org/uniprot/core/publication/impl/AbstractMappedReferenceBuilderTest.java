package org.uniprot.core.publication.impl;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.collection.IsEmptyCollection.empty;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.Nonnull;

import org.junit.jupiter.api.Test;
import org.uniprot.core.publication.MappedSource;
import org.uniprot.core.uniprotkb.UniProtKBAccession;
import org.uniprot.core.uniprotkb.impl.UniProtKBAccessionBuilder;

/**
 * Created 08/12/2020
 *
 * @author Edd
 */
class AbstractMappedReferenceBuilderTest {
    @Test
    void checkEmptyReference() {
        FakeMappedReference reference = new FakeMappedReferenceBuilder().build();
        assertThat(reference.getSourceCategories(), is(empty()));
    }

    @Test
    void canSetAccessionWithString() {
        String acc = "P12345";
        FakeMappedReference reference =
                new FakeMappedReferenceBuilder().uniProtKBAccession(acc).build();
        assertThat(reference.getUniProtKBAccession().getValue(), is(acc));
    }

    @Test
    void canSetAccession() {
        String acc = "P12345";
        FakeMappedReference reference =
                new FakeMappedReferenceBuilder()
                        .uniProtKBAccession(new UniProtKBAccessionBuilder(acc).build())
                        .build();
        assertThat(reference.getUniProtKBAccession().getValue(), is(acc));
    }

    @Test
    void canSetCitationId() {
        String citationId = "1243";
        FakeMappedReference reference = new FakeMappedReferenceBuilder().citationId(citationId).build();
        assertThat(reference.getCitationId(), is(citationId));
    }

    @Test
    void canAddCategory() {
        String category = "value";
        FakeMappedReference reference =
                new FakeMappedReferenceBuilder().sourceCategoriesAdd(category).build();
        assertThat(reference.getSourceCategories(), containsInAnyOrder(category));
    }

    @Test
    void canSetCategories() {
        Set<String> categories = new HashSet<>();
        categories.add("one");
        categories.add("two");
        FakeMappedReference reference =
                new FakeMappedReferenceBuilder().sourceCategoriesSet(categories).build();
        assertThat(reference.getSourceCategories(), is(categories));
    }

    @Test
    void canSetSource() {
        MappedSource source = new MappedSourceBuilder().name("source1").id("value1").build();
        FakeMappedReference reference = new FakeMappedReferenceBuilder().source(source).build();
        assertThat(reference.getSource(), is(source));
    }

    @Test
    void canCreateViaFrom() {
        String id = "id";
        FakeMappedReference ref = new FakeMappedReferenceBuilder().uniProtKBAccession(id).build();
        FakeMappedReferenceBuilder builder = FakeMappedReferenceBuilder.from(ref);
        assertThat(builder.build().getUniProtKBAccession().getValue(), is(id));
    }

    private static class FakeMappedReference extends AbstractMappedReference {
        public FakeMappedReference(
                MappedSource source,
                String pubMedId,
                UniProtKBAccession uniProtKBAccession,
                Set<String> sourceCategories) {
            super(source, pubMedId, uniProtKBAccession, sourceCategories);
        }
    }

    private static class FakeMappedReferenceBuilder
            extends AbstractMappedReferenceBuilder<
                    FakeMappedReferenceBuilder, FakeMappedReference> {
        @Nonnull
        @Override
        public FakeMappedReference build() {
            return new FakeMappedReference(source, citationId, uniProtKBAccession, sourceCategories);
        }

        @Nonnull
        @Override
        protected FakeMappedReferenceBuilder getThis() {
            return this;
        }

        public static FakeMappedReferenceBuilder from(FakeMappedReference instance) {
            return AbstractMappedReferenceBuilder.from(new FakeMappedReferenceBuilder(), instance);
        }
    }
}

package org.uniprot.core.publication.impl;

import org.junit.jupiter.api.Test;
import org.uniprot.core.publication.MappedSource;
import org.uniprot.core.uniprotkb.UniProtKBAccession;
import org.uniprot.core.uniprotkb.impl.UniProtKBAccessionBuilder;

import javax.annotation.Nonnull;
import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;

/**
 * Created 08/12/2020
 *
 * @author Edd
 */
class AbstractMappedReferenceBuilderTest {
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
    void canSetPubMed() {
        String pubmed = "1243";
        FakeMappedReference reference = new FakeMappedReferenceBuilder().pubMedId(pubmed).build();
        assertThat(reference.getPubMedId(), is(pubmed));
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
    void canAddSource() {
        MappedSource source =
                new MappedSourceBuilder()
                        .source("source1")
                        .sourceIdsAdd("value1")
                        .sourceIdsAdd("value2")
                        .build();
        FakeMappedReference reference = new FakeMappedReferenceBuilder().sourcesAdd(source).build();
        assertThat(reference.getSources(), containsInAnyOrder(source));
    }

    @Test
    void canSetSources() {
        Set<MappedSource> categories = new HashSet<>();
        categories.add(
                new MappedSourceBuilder()
                        .source("source1")
                        .sourceIdsAdd("value1")
                        .sourceIdsAdd("value2")
                        .build());
        categories.add(
                new MappedSourceBuilder()
                        .source("source2")
                        .sourceIdsAdd("value3")
                        .sourceIdsAdd("value4")
                        .build());
        FakeMappedReference reference =
                new FakeMappedReferenceBuilder().sourcesSet(categories).build();
        assertThat(reference.getSources(), is(categories));
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
                Set<MappedSource> sources,
                String pubMedId,
                UniProtKBAccession uniProtKBAccession,
                Set<String> sourceCategories) {
            super(sources, pubMedId, uniProtKBAccession, sourceCategories);
        }
    }

    private static class FakeMappedReferenceBuilder
            extends AbstractMappedReferenceBuilder<
                    FakeMappedReferenceBuilder, FakeMappedReference> {
        @Nonnull
        @Override
        public FakeMappedReference build() {
            return new FakeMappedReference(sources, pubMedId, uniProtKBAccession, sourceCategories);
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

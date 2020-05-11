package org.uniprot.core.feature.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.uniprot.core.ObjectsForTests.createEvidence;
import static org.uniprot.core.ObjectsForTests.createEvidences;

import java.util.Collections;

import javax.annotation.Nonnull;

import org.junit.jupiter.api.Test;
import org.uniprot.core.CrossReference;
import org.uniprot.core.feature.*;
import org.uniprot.core.impl.CrossReferenceBuilder;

/**
 * @author lgonzales
 * @since 06/05/2020
 */
class AbstractFeatureBuilderTest {

    @Test
    void canSetType() {
        TestableFeature obj =
                new TestableFeatureBuilder().type(TestableFeatureType.TEST_TYPE).build();
        assertEquals(TestableFeatureType.TEST_TYPE, obj.getType());
    }

    @Test
    void canSetLocation() {
        FeatureLocation range = new FeatureLocation(1, 2);
        TestableFeature obj = new TestableFeatureBuilder().location(range).build();
        assertEquals(range, obj.getLocation());
        assertTrue(obj.hasLocation());
    }

    @Test
    void canSetDescriptionFromString() {
        TestableFeature obj = new TestableFeatureBuilder().description("des").build();
        assertEquals("des", obj.getDescription().getValue());
        assertTrue(obj.hasDescription());
    }

    @Test
    void canSetAlternativeSequence() {
        AlternativeSequence org = new AlternativeSequenceBuilder().original("org").build();
        TestableFeature obj = new TestableFeatureBuilder().alternativeSequence(org).build();
        assertEquals(org, obj.getAlternativeSequence());
    }

    @Test
    void canSetDbXref() {
        CrossReference<TestableFeatureDatabase> xrefs =
                new CrossReferenceBuilder<TestableFeatureDatabase>()
                        .database(TestableFeatureDatabase.TEST_DATABASE)
                        .id("db id")
                        .build();
        TestableFeature obj = new TestableFeatureBuilder().featureCrossReference(xrefs).build();
        assertEquals(xrefs, obj.getFeatureCrossReference());
        assertTrue(obj.hasFeatureCrossReference());
    }

    @Test
    void canAddSingleEvidence() {
        TestableFeature obj = new TestableFeatureBuilder().evidencesAdd(createEvidence()).build();
        assertNotNull(obj.getEvidences());
        assertFalse(obj.getEvidences().isEmpty());
        assertTrue(obj.hasEvidences());
    }

    @Test
    void nullEvidence_willBeIgnore() {
        TestableFeature obj = new TestableFeatureBuilder().evidencesAdd(null).build();
        assertNotNull(obj.getEvidences());
        assertTrue(obj.getEvidences().isEmpty());
        assertFalse(obj.hasEvidences());
    }

    @Test
    void evidences_willConvertUnModifiable_toModifiable() {
        TestableFeature obj =
                new TestableFeatureBuilder()
                        .evidencesSet(Collections.emptyList())
                        .evidencesAdd(createEvidence())
                        .build();
        assertNotNull(obj.getEvidences());
        assertFalse(obj.getEvidences().isEmpty());
        assertTrue(obj.hasEvidences());
    }

    @Test
    void canAddListEvidences() {
        TestableFeature obj = new TestableFeatureBuilder().evidencesSet(createEvidences()).build();
        assertNotNull(obj.getEvidences());
        assertFalse(obj.getEvidences().isEmpty());
        assertTrue(obj.hasEvidences());
    }

    @Test
    void canCreateBuilderFromInstance() {
        TestableFeature obj = new TestableFeatureBuilder().build();
        TestableFeatureBuilder builder = new TestableFeatureBuilder().from(obj);
        assertNotNull(builder);
        assertEquals(obj, builder.build());
    }

    @Test
    void defaultBuild_objsAreEqual() {
        TestableFeature obj = new TestableFeatureBuilder().build();
        TestableFeature obj2 = new TestableFeatureBuilder().build();
        assertTrue(obj.equals(obj2) && obj2.equals(obj));
        assertEquals(obj.hashCode(), obj2.hashCode());
    }

    private static class TestableFeatureBuilder
            extends AbstractFeatureBuilder<
                    TestableFeatureBuilder,
                    TestableFeature,
                    TestableFeatureDatabase,
                    TestableFeatureType> {
        @Override
        public @Nonnull AbstractFeatureBuilderTest.TestableFeature build() {
            return new TestableFeature(this);
        }

        public TestableFeatureBuilder from(TestableFeature instance) {
            AbstractFeatureBuilder.from(this, instance);
            return this;
        }

        @Override
        protected @Nonnull AbstractFeatureBuilderTest.TestableFeatureBuilder getThis() {
            return this;
        }
    }

    private static class TestableFeature
            extends AbstractFeature<TestableFeatureDatabase, TestableFeatureType> {
        private static final long serialVersionUID = 6301569605832876243L;

        TestableFeature(TestableFeatureBuilder builder) {
            super(
                    builder.type,
                    builder.location,
                    builder.description,
                    builder.alternativeSequence,
                    builder.featureCrossReference,
                    builder.evidences);
        }
    }

    private static enum TestableFeatureDatabase implements FeatureDatabase {
        TEST_DATABASE;

        @Override
        public String getName() {
            return name();
        }
    }

    private static enum TestableFeatureType implements FeatureType {
        TEST_TYPE;

        @Override
        public String getName() {
            return name();
        }
    }
}

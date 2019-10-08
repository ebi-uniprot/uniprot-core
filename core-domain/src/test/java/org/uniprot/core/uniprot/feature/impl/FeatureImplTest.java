package org.uniprot.core.uniprot.feature.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.uniprot.core.uniprot.EvidenceHelper.createEvidences;

import org.junit.jupiter.api.Test;
import org.uniprot.core.DBCrossReference;
import org.uniprot.core.PositionModifier;
import org.uniprot.core.Range;
import org.uniprot.core.builder.DBCrossReferenceBuilder;
import org.uniprot.core.uniprot.feature.Feature;
import org.uniprot.core.uniprot.feature.FeatureId;
import org.uniprot.core.uniprot.feature.FeatureType;
import org.uniprot.core.uniprot.feature.FeatureXDbType;
import org.uniprot.core.uniprot.feature.builder.AlternativeSequenceBuilder;
import org.uniprot.core.uniprot.feature.builder.FeatureBuilder;
import org.uniprot.core.uniprot.feature.builder.FeatureIdBuilder;

class FeatureImplTest {

    @Test
    void testSimple() {
        Range location = new Range(32, 50, PositionModifier.EXACT, PositionModifier.UNSURE);
        Feature feature =
          new FeatureBuilder().type(FeatureType.ACT_SITE)
          .location(location)
          .description("Some description")
          .evidences(createEvidences())
          .build();
        assertEquals(location, feature.getLocation());
        assertEquals("Some description", feature.getDescription().getValue());
        assertEquals(2, feature.getEvidences().size());
        assertEquals(FeatureType.ACT_SITE, feature.getType());
        assertFalse(feature.hasAlternativeSequence());
        assertFalse(feature.hasFeatureId());
    }

    @Test
    void testWithFeatureId() {
        Range location = new Range(32, 96);
        FeatureId featureId = new FeatureIdImpl("PRO_324");
        Feature feature =
          new FeatureBuilder().type(FeatureType.CHAIN)
            .location(location)
            .description("Some chain description")
            .featureId(featureId)
            .evidences(createEvidences())
            .build();

        assertEquals(location, feature.getLocation());
        assertEquals("Some chain description", feature.getDescription().getValue());
        assertEquals(2, feature.getEvidences().size());
        assertEquals(FeatureType.CHAIN, feature.getType());
        assertFalse(feature.hasAlternativeSequence());
        assertTrue(feature.hasFeatureId());
        assertEquals(featureId, feature.getFeatureId());
        assertNull(feature.getAlternativeSequence());
        assertNull(feature.getDbXref());
    }

    @Test
    void needDefaultConstructorForJsonDeserialization() {
        Feature obj = new FeatureImpl();
        assertNotNull(obj);
    }

    @Test
    void builderFrom_constructorImp_shouldCreate_equalObject() {
        DBCrossReference<FeatureXDbType> xrefs = new DBCrossReferenceBuilder<FeatureXDbType>().databaseType(FeatureXDbType.DBSNP)
        .id("db id")
        .build();
        Feature impl = new FeatureImpl(FeatureType.ZN_FING, new Range(1, 2), new FeatureDescriptionImpl("abc")
          , new FeatureIdBuilder("1").build(), new AlternativeSequenceBuilder().build(), xrefs, createEvidences());
        Feature obj = new FeatureBuilder().from(impl).build();
        assertTrue(impl.equals(obj) && obj.equals(impl));
        assertEquals(impl.hashCode(), obj.hashCode());
    }
}

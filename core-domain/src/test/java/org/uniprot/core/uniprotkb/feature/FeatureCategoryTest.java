package org.uniprot.core.uniprotkb.feature;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class FeatureCategoryTest {

    @Test
    void MOLECULE_PROCESSING() {
        assertEquals("Molecule processing", FeatureCategory.MOLECULE_PROCESSING.getName());
    }

    @Test
    void REGIONS() {
        assertEquals("Regions", FeatureCategory.REGIONS.getName());
    }

    @Test
    void SITES() {
        assertEquals("Sites", FeatureCategory.SITES.getName());
    }

    @Test
    void AMINO_ACID_MODIFICATIONS() {
        assertEquals(
                "Amino acid modifications", FeatureCategory.AMINO_ACID_MODIFICATIONS.getName());
    }

    @Test
    void NATURAL_VARIATIONS() {
        assertEquals("Natural variations", FeatureCategory.NATURAL_VARIATIONS.getName());
    }

    @Test
    void EXPERIMENTAL_INFO() {
        assertEquals("Experimental info", FeatureCategory.EXPERIMENTAL_INFO.getName());
    }

    @Test
    void SECONDARY_STRUCTURE() {
        assertEquals("Secondary structure", FeatureCategory.SECONDARY_STRUCTURE.getName());
    }

    @Test
    void canUseTypeOf() {
        assertEquals(
                FeatureCategory.SECONDARY_STRUCTURE, FeatureCategory.typeOf("Secondary structure"));
    }
}

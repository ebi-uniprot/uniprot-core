package org.uniprot.core.uniprotkb.taxonomy.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprotkb.evidence.impl.EvidenceBuilder;
import org.uniprot.core.uniprotkb.taxonomy.Organism;

class OrganismBuilderTest {

    @Test
    void defaultBuilder_differentObject_shouldEqual() {
        Organism organism = new OrganismBuilder().build();
        Organism organism2 = new OrganismBuilder().build();
        assertTrue(organism.equals(organism2) && organism2.equals(organism));
        assertEquals(organism.hashCode(), organism2.hashCode());
    }

    @Test
    void canSetTaxonId() {
        Organism organism = new OrganismBuilder().taxonId(123).build();
        assertEquals(123, organism.getTaxonId());
    }

    @Test
    void settingLineageNull_willNeverBeNull() {
        Organism organism = new OrganismBuilder().lineagesSet(null).build();
        assertNotNull(organism.getLineages());
        assertTrue(organism.getLineages().isEmpty());
    }

    @Test
    void settingLineage_unModifiable_willConvertToModifiable() {
        Organism organism =
                new OrganismBuilder()
                        .lineagesSet(Collections.emptyList())
                        .lineagesAdd("DEF")
                        .build();
        assertEquals("DEF", organism.getLineages().get(0));
    }

    @Test
    void canAddLineage_withOutAddingLineageList_defaultLineageListNeverBeNull() {
        Organism organism = new OrganismBuilder().lineagesAdd("abc").build();
        assertNotNull(organism.getLineages());
        assertFalse(organism.getLineages().isEmpty());
        assertEquals("abc", organism.getLineages().get(0));
    }

    @Test
    void settingEvidencesNull_willNeverBeNull() {
        Organism organism = new OrganismBuilder().evidencesSet(null).build();
        assertNotNull(organism.getEvidences());
        assertTrue(organism.getEvidences().isEmpty());
    }

    @Test
    void settingEvidences_unModifiable_willConvertToModifiable() {
        Organism organism =
                new OrganismBuilder()
                        .evidencesSet(Collections.emptyList())
                        .evidencesAdd(new EvidenceBuilder().build())
                        .build();
        assertNotNull(organism.getEvidences().get(0));
    }

    @Test
    void canAddEvidence_withOutAddingEvidencesList_defaultEvidencesListNeverBeNull() {
        Organism organism =
                new OrganismBuilder().evidencesAdd(new EvidenceBuilder().build()).build();
        assertNotNull(organism.getEvidences());
        assertFalse(organism.getEvidences().isEmpty());
        assertNotNull(organism.getEvidences().get(0));
    }

    @Test
    void canCreateBuilderFromInstance() {
        Organism organism = new OrganismBuilder().build();
        OrganismBuilder builder = OrganismBuilder.from(organism);
        assertNotNull(builder);
    }

    @Test
    void whenEvidenceIsAbsent_willReturnFalse() {
        Organism organism = new OrganismBuilder().build();
        assertFalse(organism.hasEvidences());
    }

    @Test
    void whenEvidencePresent_willReturnTrue() {
        Organism organism =
                new OrganismBuilder().evidencesAdd(new EvidenceBuilder().build()).build();
        assertTrue(organism.hasEvidences());
    }
}

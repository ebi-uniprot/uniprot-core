package org.uniprot.core.proteome.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.uniprot.core.proteome.CanonicalProtein;
import org.uniprot.core.proteome.Protein;

import java.util.ArrayList;
import java.util.List;

class CanonicalProteinBuilderTest {

    @Test
    void testProtein() {
        Protein protein = new ProteinBuilder().accession("P12345").build();
        CanonicalProtein cProtein = new CanonicalProteinBuilder().canonicalProtein(protein).build();
        assertEquals(protein, cProtein.getCanonicalProtein());
    }

    @Test
    void testRelatedProteins() {
        List<Protein> proteins = new ArrayList<>();
        proteins.add(new ProteinBuilder().accession("P12345").build());
        proteins.add(new ProteinBuilder().accession("P12346").build());
        CanonicalProtein cProtein =
                new CanonicalProteinBuilder().relatedProteinsSet(proteins).build();
        assertEquals(proteins, cProtein.getRelatedProteins());
    }

    @Test
    void addRelatedProtein() {
        Protein protein = new ProteinBuilder().accession("P12345").build();
        CanonicalProtein cProtein =
                new CanonicalProteinBuilder().relatedProteinsAdd(protein).build();
        List<Protein> proteins = new ArrayList<>();
        proteins.add(protein);
        assertEquals(proteins, cProtein.getRelatedProteins());
    }

    @Test
    void testFrom() {
        Protein protein = new ProteinBuilder().accession("P12345").build();
        CanonicalProtein cProtein = new CanonicalProteinBuilder().canonicalProtein(protein).build();
        CanonicalProtein newProtein = CanonicalProteinBuilder.from(cProtein).build();
        assertEquals(cProtein, newProtein);
    }
}

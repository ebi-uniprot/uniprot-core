package org.uniprot.core.proteome.builder;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.uniprot.core.proteome.CanonicalProtein;
import org.uniprot.core.proteome.Protein;

class CanonicalProteinBuilderTest {

    @Test
    void testProtein() {
        Protein protein = ProteinBuilder.newInstance().accession("P12345").build();
        CanonicalProtein cProtein =
                CanonicalProteinBuilder.newInstance().canonicalProtein(protein).build();
        assertEquals(protein, cProtein.getCanonicalProtein());
    }

    @Test
    void testRelatedProteins() {
        List<Protein> proteins = new ArrayList<>();
        proteins.add(ProteinBuilder.newInstance().accession("P12345").build());
        proteins.add(ProteinBuilder.newInstance().accession("P12346").build());
        CanonicalProtein cProtein =
                CanonicalProteinBuilder.newInstance().relatedProteins(proteins).build();
        assertEquals(proteins, cProtein.getRelatedProteins());
    }

    @Test
    void addRelatedProtein() {
        Protein protein = ProteinBuilder.newInstance().accession("P12345").build();
        CanonicalProtein cProtein =
                CanonicalProteinBuilder.newInstance().addRelatedProtein(protein).build();
        List<Protein> proteins = new ArrayList<>();
        proteins.add(protein);
        assertEquals(proteins, cProtein.getRelatedProteins());
    }

    @Test
    void testFrom() {
        Protein protein = ProteinBuilder.newInstance().accession("P12345").build();
        CanonicalProtein cProtein =
                CanonicalProteinBuilder.newInstance().canonicalProtein(protein).build();
        CanonicalProtein newProtein = CanonicalProteinBuilder.newInstance().from(cProtein).build();
        assertEquals(cProtein, newProtein);
    }
}

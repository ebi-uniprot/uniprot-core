package org.uniprot.core.uniprot.taxonomy.builder;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprot.taxonomy.OrganismHost;

class OrganismHostBuilderTest {

    @Test
    void canSetTaxonId() {
        OrganismHost organismHost = new OrganismHostBuilder().taxonId(123).build();
        assertEquals(123, organismHost.getTaxonId());
    }

    @Test
    void defaultBuilder_differentObject_shouldEqual() {
        OrganismHost organismHost = new OrganismHostBuilder().build();
        OrganismHost organismHost2 = new OrganismHostBuilder().build();
        assertTrue(organismHost.equals(organismHost2) && organismHost2.equals(organismHost));
        assertEquals(organismHost.hashCode(), organismHost2.hashCode());
    }

    @Test
    void canCreateBuilderFromInstance() {
        OrganismHost organismHost = new OrganismHostBuilder().build();
        OrganismHostBuilder builder = new OrganismHostBuilder().from(organismHost);
        assertNotNull(builder);
    }
}

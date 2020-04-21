package org.uniprot.core.parser.tsv.uniprot;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprotkb.taxonomy.OrganismHost;
import org.uniprot.core.uniprotkb.taxonomy.impl.OrganismHostBuilder;

/**
 * @author lgonzales
 * @since 21/04/2020
 */
class EntryVirusHostMapTest {

    @Test
    void testFields() {
        List<String> fields = EntryVirusHostMap.FIELDS;
        List<String> expected = Collections.singletonList("virus_hosts");
        assertEquals(expected, fields);
    }

    @Test
    void testEmptyVirusHosts() {
        List<OrganismHost> virusHosts = Collections.emptyList();
        EntryVirusHostMap mapper = new EntryVirusHostMap(virusHosts);
        Map<String, String> result = mapper.attributeValues();
        assertEquals(0, result.size());
    }

    @Test
    void testVirusHosts() {
        OrganismHost host =
                new OrganismHostBuilder().taxonId(9606).scientificName("name value").build();
        List<OrganismHost> virusHosts = Collections.singletonList(host);
        EntryVirusHostMap mapper = new EntryVirusHostMap(virusHosts);
        Map<String, String> result = mapper.attributeValues();
        assertEquals(1, result.size());
        verify("virus_hosts", "name value [TaxID: 9606]", result);
    }

    @Test
    void testInvalidContains() {
        List<String> fields = Collections.singletonList("INVALID");
        assertFalse(EntryVirusHostMap.contains(fields));
    }

    @Test
    void testValidContains() {
        assertTrue(EntryVirusHostMap.contains(EntryVirusHostMap.FIELDS));
    }

    private void verify(String field, String expected, Map<String, String> result) {
        assertEquals(expected, result.get(field));
    }
}

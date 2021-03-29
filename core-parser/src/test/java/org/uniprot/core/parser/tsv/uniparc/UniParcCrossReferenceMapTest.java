package org.uniprot.core.parser.tsv.uniparc;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.*;

import org.junit.jupiter.api.Test;
import org.uniprot.core.Property;
import org.uniprot.core.uniparc.UniParcCrossReference;
import org.uniprot.core.uniparc.UniParcDatabase;
import org.uniprot.core.uniparc.impl.UniParcCrossReferenceBuilder;

/**
 * @author jluo
 * @date: 24 Jun 2019
 */
class UniParcCrossReferenceMapTest {

    @Test
    void testSimpleAttributeValues() {
        LocalDate created = LocalDate.of(2017, 5, 17);
        LocalDate lastUpdated = LocalDate.of(2018, 2, 7);
        UniParcCrossReference xref =
                new UniParcCrossReferenceBuilder()
                        .database(UniParcDatabase.EG_BACTERIA)
                        .id("IDVALUE")
                        .created(created)
                        .lastUpdated(lastUpdated)
                        .build();
        UniParcCrossReferenceMap xrefMap =
                new UniParcCrossReferenceMap(Collections.singletonList(xref));
        Map<String, String> result = xrefMap.attributeValues();
        assertEquals(13, result.size());
        assertEquals("2017-05-17", result.get("first_seen"));
        assertEquals("2018-02-07", result.get("last_seen"));
        assertEquals("IDVALUE", result.get("EnsemblBacteria"));
        assertEquals("", result.get("gene"));
        assertEquals("", result.get("protein"));
        assertEquals("", result.get("accession"));
        assertEquals("", result.get("proteome"));
        assertEquals("EnsemblBacteria", result.get("database"));
        assertEquals("false", result.get("active"));
        assertEquals("", result.get("ncbiGi"));
        assertEquals("", result.get("version"));
        assertEquals("0", result.get("version_uniparc"));
        assertEquals("-266", result.get("timeline"));
    }

    @Test
    void testAttributeValues() {
        List<UniParcCrossReference> xrefs = create();
        UniParcCrossReferenceMap xrefMap = new UniParcCrossReferenceMap(xrefs);
        Map<String, String> result = xrefMap.attributeValues();
        assertEquals(14, result.size());
        assertEquals("geneValue", result.get("gene"));
        assertEquals("some pname;some pname2", result.get("protein"));
        assertEquals("P12345; P12347.2 (obsolete)", result.get("accession"));
        assertEquals("UP00000564:chromosome 1", result.get("proteome"));
        assertEquals("2015-01-11", result.get("first_seen"));
        assertEquals("2018-02-07", result.get("last_seen"));
        assertEquals("P12345", result.get("UniProtKB/Swiss-Prot"));
        assertEquals("P12347", result.get("UniProtKB/TrEMBL"));
    }

    @Test
    void testFields() {
        List<String> fields = new ArrayList<>();
        fields.addAll(
                Arrays.asList(
                        "gene", "protein", "proteome", "accession", "first_seen", "last_seen",
                        "database", "active", "ncbiGi", "timeline", "version", "version_uniparc"));
        Arrays.stream(UniParcDatabase.values()).map(UniParcDatabase::getName).forEach(fields::add);
        assertEquals(UniParcCrossReferenceMap.FIELDS, fields);
    }

    @Test
    void testContains() {
        List<String> fields = Arrays.asList("gene", "upi", "CDD");
        assertTrue(UniParcCrossReferenceMap.contains(fields));
    }

    @Test
    void testAttributeValuesWithoutDates() {
        List<UniParcCrossReference> xrefs = createWithoutDates();
        UniParcCrossReferenceMap xrefMap = new UniParcCrossReferenceMap(xrefs);
        Map<String, String> result = xrefMap.attributeValues();
        assertEquals(14, result.size());
        assertTrue(result.get("first_seen").isEmpty());
        assertTrue(result.get("last_seen").isEmpty());
        assertTrue(result.get("timeline").isEmpty());
    }

    List<UniParcCrossReference> create() {
        LocalDate created = LocalDate.of(2017, 5, 17);
        LocalDate lastUpdated = LocalDate.of(2018, 2, 7);
        List<UniParcCrossReference> xrefs = createWithoutDates();
        UniParcCrossReference xref1 =
                UniParcCrossReferenceBuilder.from(xrefs.get(0))
                        .created(created)
                        .lastUpdated(lastUpdated)
                        .build();

        LocalDate created2 = LocalDate.of(2015, 1, 11);
        LocalDate lastUpdated2 = LocalDate.of(2017, 2, 27);
        UniParcCrossReference xref2 =
                UniParcCrossReferenceBuilder.from(xrefs.get(1))
                        .created(created2)
                        .lastUpdated(lastUpdated2)
                        .build();
        return List.of(xref1, xref2);
    }

    List<UniParcCrossReference> createWithoutDates() {
        List<Property> properties = new ArrayList<>();
        properties.add(new Property("prop1", "value"));
        UniParcCrossReference xref =
                new UniParcCrossReferenceBuilder()
                        .versionI(3)
                        .database(UniParcDatabase.SWISSPROT)
                        .id("P12345")
                        .version(7)
                        .active(true)
                        .propertiesSet(properties)
                        .proteinName("some pname")
                        .geneName("geneValue")
                        .build();

        List<Property> properties2 = new ArrayList<>();
        properties.add(new Property("prop2", "value2"));
        UniParcCrossReference xref2 =
                new UniParcCrossReferenceBuilder()
                        .versionI(3)
                        .database(UniParcDatabase.TREMBL)
                        .id("P12347")
                        .version(2)
                        .active(false)
                        .propertiesSet(properties2)
                        .proteinName("some pname2")
                        .proteomeId("UP00000564")
                        .component("chromosome 1")
                        .build();

        return List.of(xref, xref2);
    }
}

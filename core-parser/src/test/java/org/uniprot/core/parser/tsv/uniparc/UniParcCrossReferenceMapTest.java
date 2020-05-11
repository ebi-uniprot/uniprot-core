package org.uniprot.core.parser.tsv.uniparc;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.uniprot.core.Property;
import org.uniprot.core.uniparc.UniParcCrossReference;
import org.uniprot.core.uniparc.UniParcDatabase;
import org.uniprot.core.uniparc.impl.UniParcCrossReferenceBuilder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author jluo
 * @date: 24 Jun 2019
 */
class UniParcCrossReferenceMapTest {

    @Test
    void testAttributeValues() {
        List<UniParcCrossReference> xrefs = create();
        UniParcCrossReferenceMap xrefMap = new UniParcCrossReferenceMap(xrefs);
        Map<String, String> result = xrefMap.attributeValues();
        assertEquals(8, result.size());
        assertEquals(";", result.get("gene"));
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
                        "gene", "protein", "proteome", "accession", "first_seen", "last_seen"));
        Arrays.stream(UniParcDatabase.values()).map(UniParcDatabase::getName).forEach(fields::add);
        assertEquals(UniParcCrossReferenceMap.FIELDS, fields);
    }

    @Test
    void testContains() {
        List<String> fields = Arrays.asList("gene", "upi", "CDD");
        assertTrue(UniParcCrossReferenceMap.contains(fields));
    }

    List<UniParcCrossReference> create() {
        LocalDate created = LocalDate.of(2017, 5, 17);
        LocalDate lastUpdated = LocalDate.of(2018, 2, 7);
        List<Property> properties = new ArrayList<>();
        properties.add(new Property(UniParcCrossReference.PROPERTY_PROTEIN_NAME, "some pname"));
        //	properties.add(new Property(UniParcCrossReference.PROPERTY_GENE_NAME, "some gname"));
        UniParcCrossReference xref =
                new UniParcCrossReferenceBuilder()
                        .versionI(3)
                        .database(UniParcDatabase.SWISSPROT)
                        .id("P12345")
                        .version(7)
                        .active(true)
                        .created(created)
                        .lastUpdated(lastUpdated)
                        .propertiesSet(properties)
                        .build();

        LocalDate created2 = LocalDate.of(2015, 1, 11);
        LocalDate lastUpdated2 = LocalDate.of(2017, 2, 27);
        List<Property> properties2 = new ArrayList<>();
        properties2.add(new Property(UniParcCrossReference.PROPERTY_PROTEIN_NAME, "some pname2"));
        properties2.add(new Property(UniParcCrossReference.PROPERTY_PROTEOME_ID, "UP00000564"));
        properties2.add(new Property(UniParcCrossReference.PROPERTY_COMPONENT, "chromosome 1"));
        UniParcCrossReference xref2 =
                new UniParcCrossReferenceBuilder()
                        .versionI(3)
                        .database(UniParcDatabase.TREMBL)
                        .id("P12347")
                        .version(2)
                        .active(false)
                        .created(created2)
                        .lastUpdated(lastUpdated2)
                        .propertiesSet(properties2)
                        .build();

        return Arrays.asList(xref, xref2);
    }
}

package org.uniprot.core.parser.tsv.uniparc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.uniprot.core.Property;
import org.uniprot.core.uniparc.UniParcCrossReference;
import org.uniprot.core.uniparc.UniParcDatabase;
import org.uniprot.core.uniparc.impl.UniParcCrossReferenceBuilder;
import org.uniprot.core.uniprotkb.taxonomy.Organism;
import org.uniprot.core.uniprotkb.taxonomy.impl.OrganismBuilder;
import org.uniprot.core.util.Pair;
import org.uniprot.core.util.PairImpl;

/**
 * @author sahmad
 * @created 29/03/2021
 */
class UniParcEntryCrossRefValueMapperTest {
    @Test
    void testMapObjectWithRequiredFields() {
        UniParcCrossReference xref =
                new UniParcCrossReferenceBuilder()
                        .database(UniParcDatabase.EG_BACTERIA)
                        .id("id")
                        .active(true)
                        .versionI(1)
                        .build();
        UniParcEntryCrossRefValueMapper mapper = new UniParcEntryCrossRefValueMapper();
        Map<String, String> results = mapper.mapEntity(xref, null);
        Assertions.assertNotNull(results);
        Assertions.assertEquals(14, results.size());
        Assertions.assertEquals("", results.get("first_seen"));
        Assertions.assertEquals("", results.get("last_seen"));
        Assertions.assertEquals("", results.get("organism"));
        Assertions.assertEquals("", results.get("gene"));
        Assertions.assertEquals("", results.get("ncbiGi"));
        Assertions.assertEquals("Yes", results.get("active"));
        Assertions.assertEquals("id", results.get("accession"));
        Assertions.assertEquals("", results.get("version"));
        Assertions.assertEquals("", results.get("proteome"));
        Assertions.assertEquals("EnsemblBacteria", results.get("database"));
        Assertions.assertEquals("", results.get("organism_id"));
        Assertions.assertEquals("", results.get("protein"));
        Assertions.assertEquals("", results.get("timeline"));
        Assertions.assertEquals("1", results.get("version_uniparc"));
    }

    @Test
    void testMapObjectWithAllFieldsPopulated() {
        UniParcCrossReference xref = createCrossRef();
        UniParcEntryCrossRefValueMapper mapper = new UniParcEntryCrossRefValueMapper();
        Map<String, String> results = mapper.mapEntity(xref, null);
        Assertions.assertNotNull(results);
        Assertions.assertEquals(14, results.size());
        Assertions.assertEquals("2017-05-17", results.get("first_seen"));
        Assertions.assertEquals("2017-02-27", results.get("last_seen"));
        Assertions.assertEquals("Homo sapiens", results.get("organism"));
        Assertions.assertEquals("some gname", results.get("gene"));
        Assertions.assertEquals("random ncbi", results.get("ncbiGi"));
        Assertions.assertEquals("Yes", results.get("active"));
        Assertions.assertEquals("P12345", results.get("accession"));
        Assertions.assertEquals("7", results.get("version"));
        Assertions.assertEquals("proteoemid:component", results.get("proteome"));
        Assertions.assertEquals("UniProtKB/Swiss-Prot", results.get("database"));
        Assertions.assertEquals("9606", results.get("organism_id"));
        Assertions.assertEquals("some pname", results.get("protein"));
        Assertions.assertEquals("79", results.get("timeline"));
        Assertions.assertEquals("3", results.get("version_uniparc"));
    }

    private UniParcCrossReference createCrossRef() {
        Organism taxonomy =
                new OrganismBuilder().taxonId(9606).scientificName("Homo sapiens").build();
        List<Property> properties = new ArrayList<>();
        properties.add(new Property("prop1", "pvalue"));
        Pair<String, String> proteomeIdComponent = new PairImpl<>("proteoemid", "component");
        UniParcCrossReference xref =
                new UniParcCrossReferenceBuilder()
                        .versionI(3)
                        .database(UniParcDatabase.SWISSPROT)
                        .id("P12345")
                        .version(7)
                        .active(true)
                        .created(LocalDate.of(2017, 5, 17))
                        .lastUpdated(LocalDate.of(2017, 2, 27))
                        .propertiesSet(properties)
                        .organism(taxonomy)
                        .proteinName("some pname")
                        .geneName("some gname")
                        .ncbiGi("random ncbi")
                        .proteomeIdComponentPairsAdd(proteomeIdComponent)
                        .build();
        return xref;
    }
}

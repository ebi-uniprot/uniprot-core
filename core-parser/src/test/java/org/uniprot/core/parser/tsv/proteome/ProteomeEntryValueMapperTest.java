package org.uniprot.core.parser.tsv.proteome;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.*;

import org.junit.jupiter.api.Test;
import org.uniprot.core.CrossReference;
import org.uniprot.core.impl.CrossReferenceBuilder;
import org.uniprot.core.proteome.Component;
import org.uniprot.core.proteome.ProteomeDatabase;
import org.uniprot.core.proteome.ProteomeEntry;
import org.uniprot.core.proteome.ProteomeId;
import org.uniprot.core.proteome.ProteomeType;
import org.uniprot.core.proteome.RedundantProteome;
import org.uniprot.core.proteome.Superkingdom;
import org.uniprot.core.proteome.impl.ComponentBuilder;
import org.uniprot.core.proteome.impl.ProteomeEntryBuilder;
import org.uniprot.core.proteome.impl.ProteomeIdBuilder;
import org.uniprot.core.proteome.impl.RedundantProteomeBuilder;
import org.uniprot.core.taxonomy.TaxonomyLineage;
import org.uniprot.core.taxonomy.impl.TaxonomyLineageBuilder;
import org.uniprot.core.uniprotkb.taxonomy.Taxonomy;
import org.uniprot.core.uniprotkb.taxonomy.impl.TaxonomyBuilder;

/**
 * @author jluo
 * @date: 24 Jun 2019
 */
class ProteomeEntryValueMapperTest {

    @Test
    void testGetDataSimple() {
        ProteomeEntry entry = create();
        List<String> fields = Arrays.asList("upid", "genome_assembly", "protein_count");
        Map<String, String> entryMap = new ProteomeEntryValueMapper().mapEntity(entry, fields);

        assertEquals(fields.size(), entryMap.size());
        verify("UP000005640", "upid", entryMap);
        verify("204", "protein_count", entryMap);
        verify("", "genome_assembly", entryMap);
    }

    @Test
    void testGetDataLineage() {
        ProteomeEntry entry = create();
        List<String> fields = Arrays.asList("upid", "lineage");
        Map<String, String> entryMap = new ProteomeEntryValueMapper().mapEntity(entry, fields);

        assertEquals(4, entryMap.size());
        verify("UP000005640", "upid", entryMap);
        verify("Hominidae, Homo", "lineage", entryMap);
    }

    @Test
    void testGetDataComponent() {
        ProteomeEntry entry = create();
        List<String> fields = Arrays.asList("upid", "components");
        Map<String, String> entryMap = new ProteomeEntryValueMapper().mapEntity(entry, fields);

        assertEquals(4, entryMap.size());
        verify("UP000005640", "upid", entryMap);
        verify("someName1; someName2", "components", entryMap);
    }

    @Test
    void testGetDataOrganism() {
        ProteomeEntry entry = create();
        List<String> fields = Arrays.asList("upid", "organism", "organism_id", "taxon_mnemonic");
        Map<String, String> entryMap = new ProteomeEntryValueMapper().mapEntity(entry, fields);

        assertEquals(6, entryMap.size());
        verify("UP000005640", "upid", entryMap);
        verify("Homo sapiens (Human)", "organism", entryMap);
        verify("9606", "organism_id", entryMap);
        verify(null, "taxon_mnemonic", entryMap);
    }

    private void verify(String expected, String field, Map<String, String> result) {
        assertEquals(expected, result.get(field));
    }

    private ProteomeEntry create() {
        String id = "UP000005640";
        String description = "about some proteome";
        LocalDate modified = LocalDate.of(2015, 11, 5);
        ProteomeId proteomeId = new ProteomeIdBuilder(id).build();

        List<CrossReference<ProteomeDatabase>> xrefs = new ArrayList<>();
        CrossReference<ProteomeDatabase> xref1 =
                new CrossReferenceBuilder<ProteomeDatabase>()
                        .database(ProteomeDatabase.GENOME_ACCESSION)
                        .id("ACA121")
                        .build();
        CrossReference<ProteomeDatabase> xref2 =
                new CrossReferenceBuilder<ProteomeDatabase>()
                        .database(ProteomeDatabase.GENOME_ANNOTATION)
                        .id("ADFDA121")
                        .build();
        xrefs.add(xref1);
        xrefs.add(xref2);
        List<Component> components = new ArrayList<>();
        Component component1 =
                new ComponentBuilder()
                        .name("someName1")
                        .description("some description")
                        .proteinCount(102)
                        .build();

        Component component2 =
                new ComponentBuilder()
                        .name("someName2")
                        .description("some description 2")
                        .proteinCount(102)
                        .build();

        components.add(component1);
        components.add(component2);
        List<RedundantProteome> redundantProteomes = new ArrayList<>();
        String rid = "UP000004340";
        RedundantProteome rproteome1 =
                new RedundantProteomeBuilder()
                        .proteomeId(new ProteomeIdBuilder(rid).build())
                        .similarity(0.98f)
                        .build();
        String rid2 = "UP000004343";
        RedundantProteome rproteome2 =
                new RedundantProteomeBuilder()
                        .proteomeId(new ProteomeIdBuilder(rid2).build())
                        .similarity(0.88f)
                        .build();
        redundantProteomes.add(rproteome1);
        redundantProteomes.add(rproteome2);
        Taxonomy taxonomy =
                new TaxonomyBuilder()
                        .taxonId(9606)
                        .scientificName("Homo sapiens")
                        .commonName("Human")
                        .build();
        TaxonomyLineage taxon1 =
                new TaxonomyLineageBuilder().taxonId(9604).scientificName("Hominidae").build();
        TaxonomyLineage taxon2 =
                new TaxonomyLineageBuilder().taxonId(9605).scientificName("Homo").build();

        ProteomeEntry proteome =
                new ProteomeEntryBuilder()
                        .proteomeId(proteomeId)
                        .description(description)
                        .taxonomy(taxonomy)
                        .modified(modified)
                        .proteomeType(ProteomeType.REFERENCE)
                        .strain("some Strain")
                        .proteomeCrossReferencesSet(xrefs)
                        .taxonLineagesAdd(taxon1)
                        .taxonLineagesAdd(taxon2)
                        .citationsSet(Collections.emptyList())
                        .superkingdom(Superkingdom.EUKARYOTA)
                        .componentsSet(components)
                        .redundantProteomesSet(redundantProteomes)
                        .build();
        return proteome;
    }
}

package org.uniprot.core.parser.tsv.proteome;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.uniprot.core.DBCrossReference;
import org.uniprot.core.builder.DBCrossReferenceBuilder;
import org.uniprot.core.proteome.Component;
import org.uniprot.core.proteome.ProteomeEntry;
import org.uniprot.core.proteome.ProteomeId;
import org.uniprot.core.proteome.ProteomeType;
import org.uniprot.core.proteome.ProteomeXReferenceType;
import org.uniprot.core.proteome.RedundantProteome;
import org.uniprot.core.proteome.Superkingdom;
import org.uniprot.core.proteome.builder.ComponentBuilder;
import org.uniprot.core.proteome.builder.ProteomeEntryBuilder;
import org.uniprot.core.proteome.builder.ProteomeIdBuilder;
import org.uniprot.core.proteome.builder.RedundantProteomeBuilder;
import org.uniprot.core.taxonomy.TaxonomyLineage;
import org.uniprot.core.taxonomy.builder.TaxonomyLineageBuilder;
import org.uniprot.core.uniprot.taxonomy.Taxonomy;
import org.uniprot.core.uniprot.taxonomy.builder.TaxonomyBuilder;

/**
 * @author jluo
 * @date: 24 Jun 2019
 */
class ProteomeEntryMapTest {

    @Test
    void testGetDataSimple() {
        ProteomeEntry entry = create();
        List<String> fields = Arrays.asList("upid", "genome_assembly_id", "protein_count");
        ProteomeEntryMap entryMap = new ProteomeEntryMap(entry, fields);
        List<String> result = entryMap.getData();

        assertEquals(fields.size(), result.size());
        verify("UP000005640", 0, result);
        verify("", 1, result);
        verify("204", 2, result);
    }

    @Test
    void testGetDataLineage() {
        ProteomeEntry entry = create();
        List<String> fields = Arrays.asList("upid", "lineage");
        ProteomeEntryMap entryMap = new ProteomeEntryMap(entry, fields);
        List<String> result = entryMap.getData();

        assertEquals(fields.size(), result.size());
        verify("UP000005640", 0, result);
        verify("Hominidae, Homo", 1, result);
    }

    @Test
    void testGetDataComponent() {
        ProteomeEntry entry = create();
        List<String> fields = Arrays.asList("upid", "proteome_components");
        ProteomeEntryMap entryMap = new ProteomeEntryMap(entry, fields);
        List<String> result = entryMap.getData();

        assertEquals(fields.size(), result.size());
        verify("UP000005640", 0, result);
        verify("someName1; someName2", 1, result);
    }

    @Test
    void testGetDataOrganism() {
        ProteomeEntry entry = create();
        List<String> fields = Arrays.asList("upid", "organism", "organism_id", "taxon_mnemonic");
        ProteomeEntryMap entryMap = new ProteomeEntryMap(entry, fields);
        List<String> result = entryMap.getData();

        assertEquals(fields.size(), result.size());
        verify("UP000005640", 0, result);
        verify("Homo sapiens (Human)", 1, result);
        verify("9606", 2, result);
        verify(null, 3, result);
    }

    private void verify(String expected, int pos, List<String> result) {
        assertEquals(expected, result.get(pos));
    }

    private ProteomeEntry create() {
        String id = "UP000005640";
        String description = "about some proteome";
        LocalDate modified = LocalDate.of(2015, 11, 5);
        ProteomeId proteomeId = new ProteomeIdBuilder(id).build();

        List<DBCrossReference<ProteomeXReferenceType>> xrefs = new ArrayList<>();
        DBCrossReference<ProteomeXReferenceType> xref1 =
                new DBCrossReferenceBuilder<ProteomeXReferenceType>()
                        .databaseType(ProteomeXReferenceType.GENOME_ACCESSION)
                        .id("ACA121")
                        .build();
        DBCrossReference<ProteomeXReferenceType> xref2 =
                new DBCrossReferenceBuilder<ProteomeXReferenceType>()
                        .databaseType(ProteomeXReferenceType.GENOME_ANNOTATION)
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
                        .dbXReferencesSet(xrefs)
                        .taxonLineageAdd(taxon1)
                        .taxonLineageAdd(taxon2)
                        .referencesSet(Collections.emptyList())
                        .superkingdom(Superkingdom.EUKARYOTA)
                        .componentsSet(components)
                        .redundantProteomesSet(redundantProteomes)
                        .build();
        return proteome;
    }
}

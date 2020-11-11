package org.uniprot.core.proteome.impl;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.uniprot.core.ObjectsForTests.updateCitationBuilderWithCommonAttributes;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.uniprot.core.CrossReference;
import org.uniprot.core.citation.*;
import org.uniprot.core.citation.impl.JournalArticleBuilder;
import org.uniprot.core.citation.impl.SubmissionBuilder;
import org.uniprot.core.impl.CrossReferenceBuilder;
import org.uniprot.core.proteome.*;
import org.uniprot.core.taxonomy.TaxonomyLineage;
import org.uniprot.core.taxonomy.impl.TaxonomyLineageBuilder;
import org.uniprot.core.uniprotkb.taxonomy.Taxonomy;
import org.uniprot.core.uniprotkb.taxonomy.impl.TaxonomyBuilder;

class ProteomeEntryBuilderTest {

    @Test
    void testFrom() {
        String id = "UP000005640";
        ProteomeId proteomeId = new ProteomeIdBuilder(id).build();
        ProteomeEntry proteome = new ProteomeEntryBuilder().proteomeId(proteomeId).build();

        ProteomeEntry proteome2 = ProteomeEntryBuilder.from(proteome).build();
        assertEquals(proteome, proteome2);
    }

    @Test
    void testProteomeId() {
        String id = "UP000005640";
        ProteomeId proteomeId = new ProteomeIdBuilder(id).build();
        ProteomeEntry proteome = new ProteomeEntryBuilder().proteomeId(proteomeId).build();
        assertEquals(proteomeId, proteome.getId());
    }

    @Test
    void testDescription() {
        String description = "about some proteome";
        ProteomeEntry proteome = new ProteomeEntryBuilder().description(description).build();
        assertEquals(description, proteome.getDescription());
    }

    @Test
    void testTaxonomy() {
        Taxonomy taxonomy =
                new TaxonomyBuilder().taxonId(9606).scientificName("Homo sapiens").build();
        ProteomeEntry proteome = new ProteomeEntryBuilder().taxonomy(taxonomy).build();
        assertEquals(taxonomy, proteome.getTaxonomy());
    }

    @Test
    void testModified() {
        LocalDate modified = LocalDate.of(2015, 11, 5);
        ProteomeEntry proteome = new ProteomeEntryBuilder().modified(modified).build();
        assertEquals(modified, proteome.getModified());
    }

    @Test
    void testProteomeType() {
        ProteomeType type = ProteomeType.NORMAL;
        ProteomeEntry proteome = new ProteomeEntryBuilder().proteomeType(type).build();
        assertEquals(type, proteome.getProteomeType());

        type = ProteomeType.REFERENCE;
        proteome = new ProteomeEntryBuilder().proteomeType(type).build();
        assertEquals(type, proteome.getProteomeType());
    }

    @Test
    void testRedundantTo() {
        String id = "UP000005640";
        ProteomeId proteomeId = new ProteomeIdBuilder(id).build();
        ProteomeEntry proteome = new ProteomeEntryBuilder().redundantTo(proteomeId).build();
        assertEquals(proteomeId, proteome.getRedundantTo());
    }

    @Test
    void testStrain() {
        String strain = "some strains";
        ProteomeEntry proteome = new ProteomeEntryBuilder().strain(strain).build();
        assertEquals(strain, proteome.getStrain());
    }

    @Test
    void testIsolate() {
        String isolate = "some isolate";
        ProteomeEntry proteome = new ProteomeEntryBuilder().isolate(isolate).build();
        assertEquals(isolate, proteome.getIsolate());
    }

    @Test
    void testDbXReferences() {
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
        ProteomeEntry proteome =
                new ProteomeEntryBuilder().proteomeCrossReferencesSet(xrefs).build();
        assertEquals(2, proteome.getProteomeCrossReferences().size());
        assertThat(proteome.getProteomeCrossReferences(), hasItem(xref2));
    }

    @Test
    void testAddDbXReferences() {

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
        ProteomeEntry proteome =
                new ProteomeEntryBuilder()
                        .proteomeCrossReferencesAdd(xref1)
                        .proteomeCrossReferencesAdd(xref2)
                        .build();
        assertEquals(2, proteome.getProteomeCrossReferences().size());
        assertThat(proteome.getProteomeCrossReferences(), hasItem(xref1));
    }

    @Test
    void testComponents() {
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
        ProteomeEntry proteome = new ProteomeEntryBuilder().componentsSet(components).build();
        assertEquals(2, proteome.getComponents().size());
        assertThat(proteome.getComponents(), hasItem(component1));
    }

    @Test
    void testAddComponent() {
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

        ProteomeEntry proteome =
                new ProteomeEntryBuilder()
                        .componentsAdd(component1)
                        .componentsAdd(component2)
                        .build();
        assertEquals(2, proteome.getComponents().size());
        assertThat(proteome.getComponents(), hasItem(component2));
    }

    @Test
    void testReferences() {
        JournalArticleBuilder builder = new JournalArticleBuilder();
        updateCitationBuilderWithCommonAttributes(builder);
        JournalArticle citation1 = builder.build();

        SubmissionBuilder builder2 = new SubmissionBuilder();
        updateCitationBuilderWithCommonAttributes(builder2);

        builder2.submittedToDatabase(SubmissionDatabase.PDB);
        Submission citation2 = builder2.build();

        List<Citation> citations = new ArrayList<>();
        citations.add(citation1);
        citations.add(citation2);
        ProteomeEntry proteome = new ProteomeEntryBuilder().citationsSet(citations).build();
        assertEquals(2, proteome.getCitations().size());
        assertThat(proteome.getCitations(), hasItem(citation1));
    }

    @Test
    void testAddReferenceCitation() {
        JournalArticleBuilder builder = new JournalArticleBuilder();
        updateCitationBuilderWithCommonAttributes(builder);
        JournalArticle citation1 = builder.build();

        SubmissionBuilder builder2 = new SubmissionBuilder();
        updateCitationBuilderWithCommonAttributes(builder2);

        builder2.submittedToDatabase(SubmissionDatabase.PDB);
        Submission citation2 = builder2.build();

        ProteomeEntry proteome =
                new ProteomeEntryBuilder().citationsAdd(citation1).citationsAdd(citation2).build();
        assertEquals(2, proteome.getCitations().size());
        assertThat(proteome.getCitations(), hasItem(citation2));
    }

    @Test
    void testRedundantProteomes() {
        List<RedundantProteome> redundantProteomes = new ArrayList<>();
        String id = "UP000004340";
        RedundantProteome rproteome1 =
                new RedundantProteomeBuilder()
                        .proteomeId(new ProteomeIdBuilder(id).build())
                        .similarity(0.98f)
                        .build();
        String id2 = "UP000004343";
        RedundantProteome rproteome2 =
                new RedundantProteomeBuilder()
                        .proteomeId(new ProteomeIdBuilder(id2).build())
                        .similarity(0.88f)
                        .build();
        redundantProteomes.add(rproteome1);
        redundantProteomes.add(rproteome2);

        ProteomeEntry proteome =
                new ProteomeEntryBuilder().redundantProteomesSet(redundantProteomes).build();
        assertEquals(2, proteome.getRedudantProteomes().size());
        assertThat(proteome.getRedudantProteomes(), hasItem(rproteome1));
    }

    @Test
    void testAddRedundantProteome() {
        String id = "UP000004340";
        RedundantProteome rproteome1 =
                new RedundantProteomeBuilder()
                        .proteomeId(new ProteomeIdBuilder(id).build())
                        .similarity(0.98f)
                        .build();
        String id2 = "UP000004343";
        RedundantProteome rproteome2 =
                new RedundantProteomeBuilder()
                        .proteomeId(new ProteomeIdBuilder(id2).build())
                        .similarity(0.88f)
                        .build();

        ProteomeEntry proteome =
                new ProteomeEntryBuilder()
                        .redundantProteomesAdd(rproteome1)
                        .redundantProteomesAdd(rproteome2)
                        .build();
        assertEquals(2, proteome.getRedudantProteomes().size());
        assertThat(proteome.getRedudantProteomes(), hasItem(rproteome2));
    }

    @Test
    void testPanproteome() {
        String id = "UP000005644";
        ProteomeId proteomeId = new ProteomeIdBuilder(id).build();
        ProteomeEntry proteome = new ProteomeEntryBuilder().panproteome(proteomeId).build();
        assertEquals(proteomeId, proteome.getPanproteome());
    }

    @Test
    void testAnnotationScore() {
        ProteomeEntry proteome = new ProteomeEntryBuilder().annotationScore(20).build();
        assertEquals(20, proteome.getAnnotationScore());
    }

    @Test
    void testSuperkingdom() {
        Superkingdom superkingdom = Superkingdom.EUKARYOTA;
        ProteomeEntry proteome = new ProteomeEntryBuilder().superkingdom(superkingdom).build();
        assertEquals(superkingdom, proteome.getSuperkingdom());
    }

    @Test
    void testGeneCount() {
        ProteomeEntry proteome = new ProteomeEntryBuilder().geneCount(203).build();
        assertEquals(203, proteome.getGeneCount());
    }

    @Test
    void testTaxonLineage() {
        TaxonomyLineage taxon =
                new TaxonomyLineageBuilder().taxonId(9605).scientificName("Homo").build();
        ProteomeEntry proteome =
                new ProteomeEntryBuilder().taxonLineagesSet(Arrays.asList(taxon)).build();
        assertEquals(Arrays.asList(taxon), proteome.getTaxonLineages());
    }

    @Test
    void addCanonicalProtein() {
        List<Protein> proteins = new ArrayList<>();
        proteins.add(new ProteinBuilder().accession("P12345").build());
        proteins.add(new ProteinBuilder().accession("P12346").build());
        CanonicalProtein cProtein =
                new CanonicalProteinBuilder().relatedProteinsSet(proteins).build();

        Protein protein = new ProteinBuilder().accession("P22345").build();
        CanonicalProtein cProtein2 =
                new CanonicalProteinBuilder().relatedProteinsAdd(protein).build();
        ProteomeEntry proteome =
                new ProteomeEntryBuilder()
                        .canonicalProteinsAdd(cProtein)
                        .canonicalProteinsAdd(cProtein2)
                        .build();
        assertEquals(2, proteome.getCanonicalProteins().size());
        assertThat(proteome.getCanonicalProteins(), hasItem(cProtein));
    }

    @Test
    void canonicalProteins() {
        List<Protein> proteins = new ArrayList<>();
        proteins.add(new ProteinBuilder().accession("P12345").build());
        proteins.add(new ProteinBuilder().accession("P12346").build());
        CanonicalProtein cProtein =
                new CanonicalProteinBuilder().relatedProteinsSet(proteins).build();

        Protein protein = new ProteinBuilder().accession("P22345").build();
        CanonicalProtein cProtein2 =
                new CanonicalProteinBuilder().relatedProteinsAdd(protein).build();
        List<CanonicalProtein> cProteins = Arrays.asList(cProtein, cProtein2);
        ProteomeEntry proteome = new ProteomeEntryBuilder().canonicalProteinsSet(cProteins).build();
        assertEquals(2, proteome.getCanonicalProteins().size());
        assertThat(proteome.getCanonicalProteins(), hasItem(cProtein2));
    }

    @Test
    void canAddIdAsString() {
        ProteomeEntry entry = new ProteomeEntryBuilder().proteomeId("id").build();
        assertNotNull(entry.getId());
    }

    @Test
    void canAddDbSouce() {
        String db = "sd";
        ProteomeEntry entry = new ProteomeEntryBuilder().sourceDb(db).build();
        assertEquals(db, entry.getSourceDb());
    }

    @Test
    void canAddTaxonomyLineage() {
        TaxonomyLineage taxonomyLineage = new TaxonomyLineageBuilder().build();
        ProteomeEntry entry = new ProteomeEntryBuilder().taxonLineagesAdd(taxonomyLineage).build();
        assertFalse(entry.getTaxonLineages().isEmpty());
    }

    @Test
    void canAddProteomeCompletenessReport() {
        ProteomeCompletenessReport report = new ProteomeCompletenessReportBuilder().build();
        ProteomeEntry entry = new ProteomeEntryBuilder().proteomeCompletenessReport(report).build();
        assertEquals(report, entry.getProteomeCompletenessReport());
    }

    @Test
    void canAddGenomeAssembly() {
        GenomeAssembly genomeAssembly = new GenomeAssemblyBuilder().build();
        ProteomeEntry entry = new ProteomeEntryBuilder().genomeAssembly(genomeAssembly).build();
        assertEquals(genomeAssembly, entry.getGenomeAssembly());
    }

    @Test
    void addExclusionReason() {
        ProteomeEntry proteome =
                new ProteomeEntryBuilder()
                        .exclusionReasonsAdd(ExclusionReason.METAGENOME)
                        .exclusionReasonsAdd(ExclusionReason.MIXED_CULTURE)
                        .build();
        assertEquals(2, proteome.getExclusionReasons().size());
        assertThat(proteome.getExclusionReasons(), hasItem(ExclusionReason.METAGENOME));
        assertThat(proteome.getExclusionReasons(), hasItem(ExclusionReason.MIXED_CULTURE));
    }

    @Test
    void ExclusionReasons() {
        List<ExclusionReason> reasons = new ArrayList<>();
        reasons.add(ExclusionReason.METAGENOME);
        reasons.add(ExclusionReason.MIXED_CULTURE);

        ProteomeEntry proteome = new ProteomeEntryBuilder().exclusionReasonsSet(reasons).build();
        assertEquals(2, proteome.getExclusionReasons().size());
        assertThat(proteome.getExclusionReasons(), hasItem(ExclusionReason.METAGENOME));
        assertThat(proteome.getExclusionReasons(), hasItem(ExclusionReason.MIXED_CULTURE));
    }
}

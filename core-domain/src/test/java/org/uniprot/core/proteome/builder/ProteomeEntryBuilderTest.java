package org.uniprot.core.proteome.builder;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.uniprot.core.ObjectsForTests.updateCitationBuilderWithCommonAttributes;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.uniprot.core.DBCrossReference;
import org.uniprot.core.builder.DBCrossReferenceBuilder;
import org.uniprot.core.citation.*;
import org.uniprot.core.citation.builder.JournalArticleBuilder;
import org.uniprot.core.citation.builder.SubmissionBuilder;
import org.uniprot.core.proteome.*;
import org.uniprot.core.taxonomy.TaxonomyLineage;
import org.uniprot.core.taxonomy.builder.TaxonomyLineageBuilder;
import org.uniprot.core.uniprot.taxonomy.Taxonomy;
import org.uniprot.core.uniprot.taxonomy.builder.TaxonomyBuilder;

class ProteomeEntryBuilderTest {

    @Test
    void testFrom() {
        String id = "UP000005640";
        ProteomeId proteomeId = new ProteomeIdBuilder(id).build();
        ProteomeEntry proteome = ProteomeEntryBuilder.newInstance().proteomeId(proteomeId).build();

        ProteomeEntry proteome2 = ProteomeEntryBuilder.from(proteome).build();
        assertEquals(proteome, proteome2);
    }

    @Test
    void testProteomeId() {
        String id = "UP000005640";
        ProteomeId proteomeId = new ProteomeIdBuilder(id).build();
        ProteomeEntry proteome = ProteomeEntryBuilder.newInstance().proteomeId(proteomeId).build();
        assertEquals(proteomeId, proteome.getId());
    }

    @Test
    void testDescription() {
        String description = "about some proteome";
        ProteomeEntry proteome =
                ProteomeEntryBuilder.newInstance().description(description).build();
        assertEquals(description, proteome.getDescription());
    }

    @Test
    void testTaxonomy() {
        Taxonomy taxonomy =
                TaxonomyBuilder.newInstance().taxonId(9606).scientificName("Homo sapiens").build();
        ProteomeEntry proteome = ProteomeEntryBuilder.newInstance().taxonomy(taxonomy).build();
        assertEquals(taxonomy, proteome.getTaxonomy());
    }

    @Test
    void testModified() {
        LocalDate modified = LocalDate.of(2015, 11, 5);
        ProteomeEntry proteome = ProteomeEntryBuilder.newInstance().modified(modified).build();
        assertEquals(modified, proteome.getModified());
    }

    @Test
    void testProteomeType() {
        ProteomeType type = ProteomeType.NORMAL;
        ProteomeEntry proteome = ProteomeEntryBuilder.newInstance().proteomeType(type).build();
        assertEquals(type, proteome.getProteomeType());

        type = ProteomeType.REFERENCE;
        proteome = ProteomeEntryBuilder.newInstance().proteomeType(type).build();
        assertEquals(type, proteome.getProteomeType());
    }

    @Test
    void testRedundantTo() {
        String id = "UP000005640";
        ProteomeId proteomeId = new ProteomeIdBuilder(id).build();
        ProteomeEntry proteome = ProteomeEntryBuilder.newInstance().redundantTo(proteomeId).build();
        assertEquals(proteomeId, proteome.getRedundantTo());
    }

    @Test
    void testStrain() {
        String strain = "some strains";
        ProteomeEntry proteome = ProteomeEntryBuilder.newInstance().strain(strain).build();
        assertEquals(strain, proteome.getStrain());
    }

    @Test
    void testIsolate() {
        String isolate = "some isolate";
        ProteomeEntry proteome = ProteomeEntryBuilder.newInstance().isolate(isolate).build();
        assertEquals(isolate, proteome.getIsolate());
    }

    @Test
    void testDbXReferences() {
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
        ProteomeEntry proteome = ProteomeEntryBuilder.newInstance().dbXReferences(xrefs).build();
        assertEquals(2, proteome.getDbXReferences().size());
        assertThat(proteome.getDbXReferences(), hasItem(xref2));
    }

    @Test
    void testAddDbXReferences() {

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
        ProteomeEntry proteome =
                ProteomeEntryBuilder.newInstance()
                        .addDbXReferences(xref1)
                        .addDbXReferences(xref2)
                        .build();
        assertEquals(2, proteome.getDbXReferences().size());
        assertThat(proteome.getDbXReferences(), hasItem(xref1));
    }

    @Test
    void testComponents() {
        List<Component> components = new ArrayList<>();
        Component component1 =
                ComponentBuilder.newInstance()
                        .name("someName1")
                        .description("some description")
                        .proteinCount(102)
                        .build();

        Component component2 =
                ComponentBuilder.newInstance()
                        .name("someName2")
                        .description("some description 2")
                        .proteinCount(102)
                        .build();

        components.add(component1);
        components.add(component2);
        ProteomeEntry proteome = ProteomeEntryBuilder.newInstance().components(components).build();
        assertEquals(2, proteome.getComponents().size());
        assertThat(proteome.getComponents(), hasItem(component1));
    }

    @Test
    void testAddComponent() {
        Component component1 =
                ComponentBuilder.newInstance()
                        .name("someName1")
                        .description("some description")
                        .proteinCount(102)
                        .build();

        Component component2 =
                ComponentBuilder.newInstance()
                        .name("someName2")
                        .description("some description 2")
                        .proteinCount(102)
                        .build();

        ProteomeEntry proteome =
                ProteomeEntryBuilder.newInstance()
                        .addComponent(component1)
                        .addComponent(component2)
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
        ProteomeEntry proteome = ProteomeEntryBuilder.newInstance().references(citations).build();
        assertEquals(2, proteome.getReferences().size());
        assertThat(proteome.getReferences(), hasItem(citation1));
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
                ProteomeEntryBuilder.newInstance()
                        .addReference(citation1)
                        .addReference(citation2)
                        .build();
        assertEquals(2, proteome.getReferences().size());
        assertThat(proteome.getReferences(), hasItem(citation2));
    }

    @Test
    void testRedundantProteomes() {
        List<RedundantProteome> redundantProteomes = new ArrayList<>();
        String id = "UP000004340";
        RedundantProteome rproteome1 =
                RedundantProteomeBuilder.newInstance()
                        .proteomeId(new ProteomeIdBuilder(id).build())
                        .similarity(0.98f)
                        .build();
        String id2 = "UP000004343";
        RedundantProteome rproteome2 =
                RedundantProteomeBuilder.newInstance()
                        .proteomeId(new ProteomeIdBuilder(id2).build())
                        .similarity(0.88f)
                        .build();
        redundantProteomes.add(rproteome1);
        redundantProteomes.add(rproteome2);

        ProteomeEntry proteome =
                ProteomeEntryBuilder.newInstance().redundantProteomes(redundantProteomes).build();
        assertEquals(2, proteome.getRedudantProteomes().size());
        assertThat(proteome.getRedudantProteomes(), hasItem(rproteome1));
    }

    @Test
    void testAddRedundantProteome() {
        String id = "UP000004340";
        RedundantProteome rproteome1 =
                RedundantProteomeBuilder.newInstance()
                        .proteomeId(new ProteomeIdBuilder(id).build())
                        .similarity(0.98f)
                        .build();
        String id2 = "UP000004343";
        RedundantProteome rproteome2 =
                RedundantProteomeBuilder.newInstance()
                        .proteomeId(new ProteomeIdBuilder(id2).build())
                        .similarity(0.88f)
                        .build();

        ProteomeEntry proteome =
                ProteomeEntryBuilder.newInstance()
                        .addRedundantProteome(rproteome1)
                        .addRedundantProteome(rproteome2)
                        .build();
        assertEquals(2, proteome.getRedudantProteomes().size());
        assertThat(proteome.getRedudantProteomes(), hasItem(rproteome2));
    }

    @Test
    void testPanproteome() {
        String id = "UP000005644";
        ProteomeId proteomeId = new ProteomeIdBuilder(id).build();
        ProteomeEntry proteome = ProteomeEntryBuilder.newInstance().panproteome(proteomeId).build();
        assertEquals(proteomeId, proteome.getPanproteome());
    }

    @Test
    void testAnnotationScore() {
        ProteomeEntry proteome = ProteomeEntryBuilder.newInstance().annotationScore(20).build();
        assertEquals(20, proteome.getAnnotationScore());
    }

    @Test
    void testSuperkingdom() {
        Superkingdom superkingdom = Superkingdom.EUKARYOTA;
        ProteomeEntry proteome =
                ProteomeEntryBuilder.newInstance().superkingdom(superkingdom).build();
        assertEquals(superkingdom, proteome.getSuperkingdom());
    }

    @Test
    void testGeneCount() {
        ProteomeEntry proteome = ProteomeEntryBuilder.newInstance().geneCount(203).build();
        assertEquals(203, proteome.getGeneCount());
    }

    @Test
    void testTaxonLineage() {
        TaxonomyLineage taxon =
                new TaxonomyLineageBuilder().taxonId(9605).scientificName("Homo").build();
        ProteomeEntry proteome =
                ProteomeEntryBuilder.newInstance().taxonLineage(Arrays.asList(taxon)).build();
        assertEquals(Arrays.asList(taxon), proteome.getTaxonLineage());
    }

    @Test
    void addCanonicalProtein() {
        List<Protein> proteins = new ArrayList<>();
        proteins.add(ProteinBuilder.newInstance().accession("P12345").build());
        proteins.add(ProteinBuilder.newInstance().accession("P12346").build());
        CanonicalProtein cProtein =
                CanonicalProteinBuilder.newInstance().relatedProteins(proteins).build();

        Protein protein = ProteinBuilder.newInstance().accession("P22345").build();
        CanonicalProtein cProtein2 =
                CanonicalProteinBuilder.newInstance().addRelatedProtein(protein).build();
        ProteomeEntry proteome =
                ProteomeEntryBuilder.newInstance()
                        .addCanonicalProtein(cProtein)
                        .addCanonicalProtein(cProtein2)
                        .build();
        assertEquals(2, proteome.getCanonicalProteins().size());
        assertThat(proteome.getCanonicalProteins(), hasItem(cProtein));
    }

    @Test
    void canonicalProteins() {
        List<Protein> proteins = new ArrayList<>();
        proteins.add(ProteinBuilder.newInstance().accession("P12345").build());
        proteins.add(ProteinBuilder.newInstance().accession("P12346").build());
        CanonicalProtein cProtein =
                CanonicalProteinBuilder.newInstance().relatedProteins(proteins).build();

        Protein protein = ProteinBuilder.newInstance().accession("P22345").build();
        CanonicalProtein cProtein2 =
                CanonicalProteinBuilder.newInstance().addRelatedProtein(protein).build();
        List<CanonicalProtein> cProteins = Arrays.asList(cProtein, cProtein2);
        ProteomeEntry proteome =
                ProteomeEntryBuilder.newInstance().canonicalProteins(cProteins).build();
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
        ProteomeEntry entry = new ProteomeEntryBuilder().addTaxonLineage(taxonomyLineage).build();
        assertFalse(entry.getTaxonLineage().isEmpty());
    }
}

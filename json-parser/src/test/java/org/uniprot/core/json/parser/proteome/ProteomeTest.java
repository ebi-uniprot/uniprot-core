package org.uniprot.core.json.parser.proteome;

import static org.junit.jupiter.api.Assertions.fail;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.uniprot.core.CrossReference;
import org.uniprot.core.builder.CrossReferenceBuilder;
import org.uniprot.core.citation.*;
import org.uniprot.core.citation.builder.JournalArticleBuilder;
import org.uniprot.core.citation.builder.SubmissionBuilder;
import org.uniprot.core.json.parser.ValidateJson;
import org.uniprot.core.proteome.*;
import org.uniprot.core.proteome.builder.ComponentBuilder;
import org.uniprot.core.proteome.builder.ProteomeEntryBuilder;
import org.uniprot.core.proteome.builder.ProteomeIdBuilder;
import org.uniprot.core.proteome.builder.RedundantProteomeBuilder;
import org.uniprot.core.taxonomy.TaxonomyLineage;
import org.uniprot.core.taxonomy.builder.TaxonomyLineageBuilder;
import org.uniprot.core.uniprot.taxonomy.Taxonomy;
import org.uniprot.core.uniprot.taxonomy.builder.TaxonomyBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;

class ProteomeTest {
    @Test
    void testComponent() {
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
        Component component =
                new ComponentBuilder()
                        .name("someName")
                        .description("some description")
                        .proteinCount(102)
                        .proteomeCrossReferencesSet(xrefs)
                        .type(ComponentType.PRIMARY)
                        .build();
        ValidateJson.verifyJsonRoundTripParser(
                ProteomeJsonConfig.getInstance().getFullObjectMapper(), component);
    }

    @Test
    void testProteomeId() {
        String id = "UP000005640";
        ProteomeId proteomeId = new ProteomeIdBuilder(id).build();
        ValidateJson.verifyJsonRoundTripParser(
                ProteomeJsonConfig.getInstance().getFullObjectMapper(), proteomeId);
    }

    @Test
    void testRedundantProteome() {
        String id = "UP000004340";
        RedundantProteome rproteome =
                new RedundantProteomeBuilder()
                        .proteomeId(new ProteomeIdBuilder(id).build())
                        .similarity(0.98f)
                        .build();
        ValidateJson.verifyJsonRoundTripParser(
                ProteomeJsonConfig.getInstance().getFullObjectMapper(), rproteome);
    }

    @Test
    void testProteome() {
        String id = "UP000005640";
        String description = "about some proteome";
        LocalDate modified = LocalDate.of(2015, 11, 5);
        ProteomeId proteomeId = new ProteomeIdBuilder(id).build();
        String redundantId = "UP000005642";
        ProteomeId redId = new ProteomeIdBuilder(redundantId).build();
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
        Taxonomy taxonomy =
                new TaxonomyBuilder()
                        .taxonId(9606)
                        .scientificName("Homo sapiens")
                        .commonName("Human")
                        .build();
        ProteomeEntry proteome =
                new ProteomeEntryBuilder()
                        .proteomeId(proteomeId)
                        .description(description)
                        .taxonomy(taxonomy)
                        .modified(modified)
                        .proteomeType(ProteomeType.REDUNDANT)
                        .redundantTo(redId)
                        .strain("some Strain")
                        .proteomeCrossReferencesSet(xrefs)
                        .citationsSet(getCitations())
                        .superkingdom(Superkingdom.EUKARYOTA)
                        .panproteome(new ProteomeIdBuilder("UP000005649").build())
                        .build();

        ValidateJson.verifyJsonRoundTripParser(
                ProteomeJsonConfig.getInstance().getFullObjectMapper(), proteome);
    }

    @Test
    void testTaxonomyLineage() {
        TaxonomyLineage taxon1 =
                new TaxonomyLineageBuilder().taxonId(9604).scientificName("Hominidae").build();
        ValidateJson.verifyJsonRoundTripParser(
                ProteomeJsonConfig.getInstance().getFullObjectMapper(), taxon1);
    }

    @Test
    void testReferenceProteome() {
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
                        //		.addTaxonLineage(taxon2)
                        .citationsSet(getCitations())
                        .superkingdom(Superkingdom.EUKARYOTA)
                        //	.components(components)
                        .redundantProteomesSet(redundantProteomes)
                        .build();

        ValidateJson.verifyJsonRoundTripParser(
                ProteomeJsonConfig.getInstance().getFullObjectMapper(), proteome);

        try {
            ObjectMapper mapper = ProteomeJsonConfig.getInstance().getSimpleObjectMapper();
            String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(proteome);
            System.out.println(json);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    private List<Citation> getCitations() {

        JournalArticle citation1 = getJournalArticle();
        Submission citation2 = getSubmission();

        List<Citation> citations = new ArrayList<>();
        citations.add(citation1);
        citations.add(citation2);
        return citations;
    }

    private JournalArticle getJournalArticle() {
        CrossReference<CitationDatabase> xref =
                new CrossReferenceBuilder<CitationDatabase>()
                        .database(CitationDatabase.PUBMED)
                        .id("somepID1")
                        .build();
        return new JournalArticleBuilder()
                .journalName("journal name")
                .firstPage("first page")
                .lastPage("last page")
                .volume("volume value")
                .publicationDate("date value")
                .authoringGroupsAdd("auth group")
                .authorsAdd("author Leo")
                .title("Leo book tittle")
                .citationCrossReferencesSet(Collections.singletonList(xref))
                .build();
    }

    private Submission getSubmission() {
        CrossReference<CitationDatabase> xref =
                new CrossReferenceBuilder<CitationDatabase>()
                        .database(CitationDatabase.PUBMED)
                        .id("somepID1")
                        .build();
        return new SubmissionBuilder()
                .submittedToDatabase(SubmissionDatabase.PIR)
                .publicationDate("date value")
                .authoringGroupsAdd("auth group")
                .authorsAdd("author Leo")
                .title("Leo book tittle")
                .citationCrossReferencesSet(Collections.singletonList(xref))
                .build();
    }
}

package org.uniprot.core.json.parser.proteome;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.uniprot.core.CrossReference;
import org.uniprot.core.citation.*;
import org.uniprot.core.citation.impl.JournalArticleBuilder;
import org.uniprot.core.citation.impl.SubmissionBuilder;
import org.uniprot.core.impl.CrossReferenceBuilder;
import org.uniprot.core.json.parser.ValidateJson;
import org.uniprot.core.proteome.*;
import org.uniprot.core.proteome.impl.*;
import org.uniprot.core.taxonomy.TaxonomyLineage;
import org.uniprot.core.taxonomy.TaxonomyRank;
import org.uniprot.core.taxonomy.impl.TaxonomyLineageBuilder;
import org.uniprot.core.uniprotkb.taxonomy.Taxonomy;
import org.uniprot.core.uniprotkb.taxonomy.impl.TaxonomyBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ProteomeTest {
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
        ProteomeEntry proteome = getCompleteProteomeEntry();

        ValidateJson.verifyJsonRoundTripParser(
                ProteomeJsonConfig.getInstance().getFullObjectMapper(), proteome);
        ValidateJson.verifyEmptyFields(proteome);
    }

    @Test
    void testIfCanWriteExtraBuscoScore() throws JsonProcessingException {
        ObjectMapper mapper = ProteomeJsonConfig.getInstance().getSimpleObjectMapper();
        BuscoReport buscoReport = createBuscoReport();

        String jsonValue = mapper.writeValueAsString(buscoReport);

        assertNotNull(jsonValue);
        assertTrue(jsonValue.contains("\"score\":95"));
    }

    public static ProteomeEntry getCompleteProteomeEntry() {
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
                        .database(ProteomeDatabase.GENOME_ASSEMBLY)
                        .id("ADFDA121")
                        .build();
        CrossReference<ProteomeDatabase> xref3 =
                new CrossReferenceBuilder<ProteomeDatabase>()
                        .database(ProteomeDatabase.GENOME_ACCESSION)
                        .id("ACA122")
                        .build();
        xrefs.add(xref1);
        xrefs.add(xref2);
        Taxonomy taxonomy =
                new TaxonomyBuilder()
                        .taxonId(9606)
                        .scientificName("Homo sapiens")
                        .commonName("Human")
                        .mnemonic("HUMAN")
                        .synonymsAdd("synonym")
                        .build();

        TaxonomyLineage taxonomyLineage =
                new TaxonomyLineageBuilder()
                        .taxonId(9606)
                        .scientificName("Homo sapiens")
                        .commonName("Human")
                        .synonymsAdd("synonym")
                        .hidden(true)
                        .rank(TaxonomyRank.FAMILY)
                        .build();

        GenomeAnnotation genomeAnnotation =
                new GenomeAnnotationBuilder().source("source value").url("URL value").build();

        Component component =
                new ComponentBuilder()
                        .description("description")
                        .name("name")
                        .proteinCount(18)
                        .proteomeCrossReferencesAdd(xref1)
                        .proteomeCrossReferencesAdd(xref2)
                        .proteomeCrossReferencesAdd(xref3)
                        .genomeAnnotation(genomeAnnotation)
                        .build();

        ProteomeStatistics proteomeStatistics = new ProteomeStatisticsBuilder().reviewedProteinCount(1)
                .unreviewedProteinCount(10).isoformProteinCount(23).build();

        return new ProteomeEntryBuilder()
                .proteomeId(proteomeId)
                .description(description)
                .taxonomy(taxonomy)
                .modified(modified)
                .proteomeType(ProteomeType.REDUNDANT)
                .redundantTo(redId)
                .strain("some Strain")
                .citationsSet(getCitations())
                .superkingdom(Superkingdom.EUKARYOTA)
                .panproteome(new ProteomeIdBuilder("UP000005649").build())
                .redundantProteomesAdd(
                        new RedundantProteomeBuilder()
                                .proteomeId("UP000005648")
                                .similarity(10F)
                                .build())
                .redundantTo(new ProteomeIdBuilder("UP000005650").build())
                .componentsAdd(component)
                .isolate("isolate value")
                .annotationScore(20)
                .geneCount(28)
                .taxonLineagesAdd(taxonomyLineage)
                .proteomeCompletenessReport(createProteomeCompletenessReport())
                .genomeAssembly(createGenomeAssembly())
                .genomeAnnotation(genomeAnnotation)
                .exclusionReasonsAdd(ExclusionReason.MIXED_CULTURE)
                .proteinCount(250)
                .proteomeStatistics(proteomeStatistics)
                .build();
    }

    private static GenomeAssembly createGenomeAssembly() {
        return new GenomeAssemblyBuilder()
                .assemblyId("id value")
                .genomeAssemblyUrl("url value")
                .source(GenomeAssemblySource.ENSEMBLMETAZOA)
                .level(GenomeAssemblyLevel.FULL)
                .build();
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
                        .taxonLineagesAdd(taxon1)
                        //		.addTaxonLineage(taxon2)
                        .citationsSet(getCitations())
                        .superkingdom(Superkingdom.EUKARYOTA)
                        //	.components(components)
                        .redundantProteomesSet(redundantProteomes)
                        .proteomeCompletenessReport(createProteomeCompletenessReport())
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

    private static List<Citation> getCitations() {

        JournalArticle citation1 = getJournalArticle();
        Submission citation2 = getSubmission();

        List<Citation> citations = new ArrayList<>();
        citations.add(citation1);
        citations.add(citation2);
        return citations;
    }

    private static JournalArticle getJournalArticle() {
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

    private static Submission getSubmission() {
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

    private static ProteomeCompletenessReport createProteomeCompletenessReport() {
        return new ProteomeCompletenessReportBuilder()
                .buscoReport(createBuscoReport())
                .cpdReport(createCPDReport())
                .build();
    }

    private static CPDReport createCPDReport() {
        return new CPDReportBuilder()
                .proteomeCount(15)
                .stdCdss(13d)
                .averageCdss(8)
                .confidence(10)
                .status(CPDStatus.STANDARD)
                .build();
    }

    private static BuscoReport createBuscoReport() {
        return new BuscoReportBuilder()
                .total(103)
                .complete(80)
                .completeDuplicated(12)
                .completeSingle(8)
                .fragmented(18)
                .missing(20)
                .lineageDb("lineageDb value")
                .build();
    }
}

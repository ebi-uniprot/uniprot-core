package org.uniprot.core.xml.proteome;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.uniprot.core.proteome.ProteomeType.*;

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
import org.uniprot.core.proteome.*;
import org.uniprot.core.proteome.impl.*;
import org.uniprot.core.uniprotkb.taxonomy.Taxonomy;
import org.uniprot.core.uniprotkb.taxonomy.impl.TaxonomyBuilder;
import org.uniprot.core.xml.jaxb.proteome.Proteome;


class ProteomeConverterTest {

    @Test
    void test() {
        ProteomeConverter converter = new ProteomeConverter();
        ProteomeEntry proteome = create();
        Proteome xml = converter.toXml(proteome);
        ProteomeEntry converted = converter.fromXml(xml);
        System.out.println(ProteomeXMLTestHelper.toXmlString(xml, Proteome.class, "proteome"));
        assertEquals(proteome, converted);
    }

    @Test
    void testExcludedProteome() {
        ProteomeConverter converter = new ProteomeConverter();
        ProteomeEntry proteome =
                new ProteomeEntryBuilder()
                        .proteomeId("UP1234567890")
                        .proteomeType(EXCLUDED)
                        .exclusionReasonsAdd(ExclusionReason.MIXED_CULTURE)
                        .exclusionReasonsAdd(ExclusionReason.METAGENOME)
                        .build();
        Proteome xml = converter.toXml(proteome);
        ProteomeEntry converted = converter.fromXml(xml);
        assertEquals(proteome, converted);
    }

    @Test
    void testReferenceProteome() {
        ProteomeConverter converter = new ProteomeConverter();
        ProteomeEntry proteome =
                new ProteomeEntryBuilder()
                        .proteomeId("UP1234567890")
                        .proteomeType(REFERENCE)
                        .build();
        Proteome xml = converter.toXml(proteome);
        ProteomeEntry converted = converter.fromXml(xml);
        assertEquals(proteome, converted);
    }

    @Test
    void testFromSurveillanceProteome() {
        ProteomeConverter converter = new ProteomeConverter();
        ProteomeEntry proteome =
                new ProteomeEntryBuilder()
                        .proteomeId("UP1234567890")
                        .proteomeType(SURVEILLANCE)
                        .build();
        Proteome xml = converter.toXml(proteome);
        ProteomeEntry converted = converter.fromXml(xml);
        assertEquals(NON_REFERENCE, converted.getProteomeType());
    }

    private ProteomeEntry create() {
        String id = "UP000005640";
        ProteomeId proteomeId = new ProteomeIdBuilder(id).build();
        String description = "about some proteome";
        Taxonomy taxonomy = new TaxonomyBuilder().taxonId(9606).build();
        LocalDate modified = LocalDate.of(2015, 11, 5);
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

        CrossReference<ProteomeDatabase> xref3 =
                new CrossReferenceBuilder<ProteomeDatabase>()
                        .database(ProteomeDatabase.ASSEMBLY_ID)
                        .id("ADA121")
                        .build();

        CrossReference<ProteomeDatabase> xref4 =
                new CrossReferenceBuilder<ProteomeDatabase>()
                        .database(ProteomeDatabase.GENOME_ASSEMBLY)
                        .id("AFA121")
                        .build();

        CrossReference<ProteomeDatabase> xref5 =
                new CrossReferenceBuilder<ProteomeDatabase>()
                        .database(ProteomeDatabase.BIOSAMPLE)
                        .id("BDA121")
                        .build();
        xrefs.add(xref1);
        xrefs.add(xref2);
        xrefs.add(xref3);
        xrefs.add(xref4);
        xrefs.add(xref5);
        List<Component> components = new ArrayList<>();

        GenomeAnnotation genomeAnnotation =
                new GenomeAnnotationBuilder().source("source value").url("url value").build();

        Component component1 =
                new ComponentBuilder()
                        .name("someName1")
                        .description("some description")
                        .genomeAnnotation(genomeAnnotation)
                        .build();

        Component component2 =
                new ComponentBuilder().name("someName2").description("some description 2").build();

        components.add(component1);
        components.add(component2);
        List<Citation> citations = new ArrayList<>();
        citations.add(createJournal());
        citations.add(createSubmission());

        ProteomeCompletenessReport report =
                new ProteomeCompletenessReportBuilder()
                        .buscoReport(ScoreBuscoConverterTest.createBuscoReport())
                        .cpdReport(ScoreCPDConverterTest.createCPDReport())
                        .build();

        Taxonomy panproteomeTaxonomy = new TaxonomyBuilder().taxonId(9605).build();
        // related proteomes
        String id1 = "UP1234567870";
        ProteomeId proteomeId1 = new ProteomeIdBuilder(id1).build();
        float similarity1 = 0.5f;
        Taxonomy taxon1 = new TaxonomyBuilder().taxonId(9600).build();
        RelatedProteome relatedProteome1 = new RelatedProteomeBuilder().proteomeId(proteomeId1)
                .similarity(similarity1).taxonomy(taxon1).build();
        Taxonomy taxon2 = new TaxonomyBuilder().taxonId(9601).build();
        float similarity2 = 0.6f;
        String id2 = "UP1234567880";
        ProteomeId proteomeId2 = new ProteomeIdBuilder(id2).build();
        RelatedProteome relatedProteome2 = new RelatedProteomeBuilder().proteomeId(proteomeId2)
                .similarity(similarity2).taxonomy(taxon2).build();
        List<RelatedProteome> relatedProteomes = new ArrayList<>();
        relatedProteomes.add(relatedProteome1);
        relatedProteomes.add(relatedProteome2);

        ProteomeEntryBuilder builder =
                new ProteomeEntryBuilder()
                        .proteomeId(proteomeId)
                        .description(description)
                        .taxonomy(taxonomy)
                        .isolate("isolate value")
                        .strain("strain value")
                        .modified(modified)
                        .proteomeType(EXCLUDED)
                        .componentsSet(components)
                        .citationsSet(citations)
                        .annotationScore(15)
                        .genomeAssembly(GenomeAssemblyConverterTest.createGenomeAssembly())
                        .proteomeCompletenessReport(report)
                        .genomeAnnotation(genomeAnnotation)
                        .exclusionReasonsAdd(ExclusionReason.METAGENOME)
                        .exclusionReasonsAdd(ExclusionReason.CONTAMINATED)
                        .panproteomeTaxon(panproteomeTaxonomy)
                        .relatedProteomesSet(relatedProteomes);
        return builder.build();
    }

    private JournalArticle createJournal() {
        JournalArticleBuilder builder = new JournalArticleBuilder();
        String title = "Some article title";
        String date = "2008";
        String journalName = "Nature";
        builder.journalName(journalName)
                .firstPage("213")
                .lastPage("223")
                .volume("2")
                .title(title)
                .publicationDate(date)
                .authorsAdd("Sulson J.E.")
                .authorsAdd("JWaterston R.")
                .authoringGroupsSet(
                        Collections.singletonList("The C. elegans sequencing consortium"))
                .citationCrossReferencesAdd(
                        new CrossReferenceBuilder<CitationDatabase>()
                                .database(CitationDatabase.PUBMED)
                                .id("9851916")
                                .build())
                .citationCrossReferencesAdd(
                        new CrossReferenceBuilder<CitationDatabase>()
                                .database(CitationDatabase.DOI)
                                .id("https://doi.org/10.1126/science.282.5396.2012")
                                .build());
        return builder.build();
    }

    private Submission createSubmission() {
        SubmissionBuilder builder = new SubmissionBuilder();
        String date = "JAN-2018";
        builder.submittedToDatabase(SubmissionDatabase.EMBL_GENBANK_DDBJ)
                .authorsAdd("Sulson J.E.")
                .authorsAdd("JWaterston R.")
                .title("Another title")
                .publicationDate(date);
        return builder.build();
    }
}

package org.uniprot.core.xml.proteome;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
import org.uniprot.core.proteome.impl.ComponentBuilder;
import org.uniprot.core.proteome.impl.ProteomeCompletenessReportBuilder;
import org.uniprot.core.proteome.impl.ProteomeEntryBuilder;
import org.uniprot.core.proteome.impl.ProteomeIdBuilder;
import org.uniprot.core.uniprotkb.taxonomy.Taxonomy;
import org.uniprot.core.uniprotkb.taxonomy.impl.TaxonomyBuilder;

class ProteomeConverterTest {

    @Test
    void test() {
        ProteomeConverter converter = new ProteomeConverter();
        ProteomeEntry proteome = create();
        org.uniprot.core.xml.jaxb.proteome.Proteome xml = converter.toXml(proteome);
        ProteomeEntry converted = converter.fromXml(xml);
        assertEquals(proteome, converted);
    }

    private ProteomeEntry create() {
        String id = "UP000005640";
        ProteomeId proteomeId = new ProteomeIdBuilder(id).build();
        String description = "about some proteome";
        Taxonomy taxonomy =
                new TaxonomyBuilder().taxonId(9606).scientificName("Homo sapiens").build();
        LocalDate modified = LocalDate.of(2015, 11, 5);
        String reId = "UP000005641";
        ProteomeId redId = new ProteomeIdBuilder(reId).build();
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
                        .type(org.uniprot.core.proteome.ComponentType.UNPLACED)
                        .build();

        Component component2 =
                new ComponentBuilder()
                        .name("someName2")
                        .description("some description 2")
                        .type(org.uniprot.core.proteome.ComponentType.SEGMENTED_GENOME)
                        .build();

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

        ProteomeEntryBuilder builder =
                new ProteomeEntryBuilder()
                        .proteomeId(proteomeId)
                        .description(description)
                        .taxonomy(taxonomy)
                        .modified(modified)
                        .proteomeType(ProteomeType.REFERENCE)
                        .redundantTo(redId)
                        .panproteome(redId)
                        .proteomeCrossReferencesSet(xrefs)
                        .componentsSet(components)
                        .superkingdom(Superkingdom.EUKARYOTA)
                        .citationsSet(citations)
                        .annotationScore(15)
                        .genomeAssembly(GenomeAssemblyConverterTest.createGenomeAssembly())
                        .proteomeCompletenessReport(report);
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
                .authoringGroupsSet(Arrays.asList("The C. elegans sequencing consortium"))
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
        JournalArticle citation = builder.build();
        return citation;
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

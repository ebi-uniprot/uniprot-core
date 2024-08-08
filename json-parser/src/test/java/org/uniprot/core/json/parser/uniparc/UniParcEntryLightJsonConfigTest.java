package org.uniprot.core.json.parser.uniparc;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.uniprot.core.Sequence;
import org.uniprot.core.impl.SequenceBuilder;
import org.uniprot.core.json.parser.ValidateJson;
import org.uniprot.core.json.parser.uniprot.CreateUtils;
import org.uniprot.core.uniparc.*;
import org.uniprot.core.uniparc.impl.CommonOrganismBuilder;
import org.uniprot.core.uniparc.impl.ProteomeBuilder;
import org.uniprot.core.uniparc.impl.UniParcEntryLightBuilder;
import org.uniprot.core.uniprotkb.evidence.Evidence;
import org.uniprot.core.uniprotkb.taxonomy.Organism;
import org.uniprot.core.uniprotkb.taxonomy.impl.OrganismBuilder;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.fail;
import static org.uniprot.core.json.parser.uniparc.UniParcEntryTest.getSeqFeature;


class UniParcEntryLightJsonConfigTest {

    @Test
    void testFullUniParcEntryLightJsonRoundTrip() {
        UniParcEntryLight entry = getCompleteUniParcEntryLight();

        ValidateJson.verifyJsonRoundTripParser(
                UniParcEntryLightJsonConfig.getInstance().getFullObjectMapper(), entry);
        ValidateJson.verifyEmptyFields(entry);
        try {
            ObjectMapper mapper = UniParcEntryLightJsonConfig.getInstance().getSimpleObjectMapper();
            String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(entry);
            System.out.println(json);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    private UniParcEntryLight getCompleteUniParcEntryLight() {
        // Create sample data for all the fields
        String uniParcId = "UPI0000000001";

        int crossReferenceCount = 19;

        List<CommonOrganism> commonTaxons = List.of(
                new CommonOrganismBuilder().topLevel("cellular organisms").commonTaxon("Homo sapiens").build(),
                new CommonOrganismBuilder().topLevel("Viruses").commonTaxon("Mus musculus").build()
        );

        LinkedHashSet<String> uniProtKBAccessions = new LinkedHashSet<>(List.of("P12345", "Q67890"));

        String sequenceStr = "MVSWGRFICLVVVTMATLSLARPSFSLVED";
        Sequence sequence = new SequenceBuilder(sequenceStr).build();

        List<SequenceFeature> sequenceFeatures = new ArrayList<>();
        Arrays.stream(SignatureDbType.values()).forEach(signatureType -> sequenceFeatures.add(getSeqFeature(signatureType)));

        LocalDate oldestCrossRefCreated = LocalDate.of(2020, 1, 1);
        LocalDate mostRecentCrossRefUpdated = LocalDate.of(2023, 6, 19);

        List<Evidence> evidences = CreateUtils.createEvidenceList("ECO:0000269|PubMed:11389730");
        Organism organism = new OrganismBuilder()
                .taxonId(123L)
                .scientificName("ScientificName")
                .lineagesAdd("Lineage 1")
                .commonName("common Name")
                .synonymsAdd("syn name")
                .evidencesSet(evidences)
                .build();

        LinkedHashSet<Organism> organisms = new LinkedHashSet<>(List.of(organism));
        LinkedHashSet<String> proteinNames = new LinkedHashSet<>(List.of("Protein Alpha", "Protein Beta"));
        LinkedHashSet<String> geneNames = new LinkedHashSet<>(List.of("Gene1", "Gene2"));
        LinkedHashSet<Proteome> proteomes = new LinkedHashSet<>(List.of(new ProteomeBuilder().id("UP000005640").component("Chromosome 1").build(), new ProteomeBuilder().id("UP000000589").component("Chromosome 2").build()));

        // Use the builder to create a complete UniParcEntryLight
        return new UniParcEntryLightBuilder()
                .uniParcId(uniParcId)
                .crossReferenceCount(crossReferenceCount)
                .commonTaxonsSet(commonTaxons)
                .uniProtKBAccessionsSet(uniProtKBAccessions)
                .sequence(sequence)
                .sequenceFeaturesSet(sequenceFeatures)
                .oldestCrossRefCreated(oldestCrossRefCreated)
                .mostRecentCrossRefUpdated(mostRecentCrossRefUpdated)
                .organismsSet(organisms)
                .proteinNamesSet(proteinNames)
                .geneNamesSet(geneNames)
                .proteomesSet(proteomes)
                .build();
    }
}

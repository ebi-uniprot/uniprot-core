package org.uniprot.core.json.parser.uniparc;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.uniprot.core.Sequence;
import org.uniprot.core.impl.SequenceBuilder;
import org.uniprot.core.json.parser.ValidateJson;
import org.uniprot.core.uniparc.SequenceFeature;
import org.uniprot.core.uniparc.SignatureDbType;
import org.uniprot.core.uniparc.UniParcEntryLight;
import org.uniprot.core.uniparc.impl.UniParcEntryLightBuilder;
import org.uniprot.core.util.Pair;
import org.uniprot.core.util.PairImpl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

        List<String> uniParcCrossReferences = List.of(
                "UPI0000000001-REFSEQ-12345-3",
                "UPI0000000002-EMBL-67890-1"
        );

        List<Pair<Integer, String>> commonTaxons = List.of(
                new PairImpl<>(9606, "Homo sapiens"),
                new PairImpl<>(10090, "Mus musculus")
        );

        List<String> uniProtKBAccessions = List.of(
                "P12345",
                "Q67890"
        );

        String uniProtExclusionReason = "Excluded due to redundancy";

        String sequenceStr = "MVSWGRFICLVVVTMATLSLARPSFSLVED";
        Sequence sequence = new SequenceBuilder(sequenceStr).build();

        List<SequenceFeature> sequenceFeatures = new ArrayList<>();
        Arrays.stream(SignatureDbType.values()).forEach(signatureType -> sequenceFeatures.add(getSeqFeature(signatureType)));

        LocalDate oldestCrossRefCreated = LocalDate.of(2020, 1, 1);
        LocalDate mostRecentCrossRefUpdated = LocalDate.of(2023, 6, 19);

        List<Pair<Integer, String>> organisms = List.of(
                new PairImpl<>(9606, "Homo sapiens"),
                new PairImpl<>(10090, "Mus musculus")
        );

        List<String> proteinNames = List.of(
                "Protein Alpha",
                "Protein Beta"
        );

        List<String> geneNames = List.of(
                "Gene1",
                "Gene2"
        );

        List<String> proteomeIds = List.of(
                "UP000005640",
                "UP000000589"
        );

        // Use the builder to create a complete UniParcEntryLight
        return new UniParcEntryLightBuilder()
                .uniParcId(uniParcId)
                .uniParcCrossReferencesSet(uniParcCrossReferences)
                .commonTaxonsSet(commonTaxons)
                .uniProtKBAccessionsSet(uniProtKBAccessions)
                .uniProtExclusionReason(uniProtExclusionReason)
                .sequence(sequence)
                .sequenceFeaturesSet(sequenceFeatures)
                .oldestCrossRefCreated(oldestCrossRefCreated)
                .mostRecentCrossRefUpdated(mostRecentCrossRefUpdated)
                .organismsSet(organisms)
                .proteinNamesSet(proteinNames)
                .geneNamesSet(geneNames)
                .proteomeIdsSet(proteomeIds)
                .build();
    }
}

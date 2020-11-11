package org.uniprot.core.json.parser.genecentric;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.uniprot.core.genecentric.GeneCentricEntry;
import org.uniprot.core.genecentric.Protein;
import org.uniprot.core.genecentric.impl.GeneCentricEntryBuilder;
import org.uniprot.core.genecentric.impl.ProteinBuilder;
import org.uniprot.core.json.parser.ValidateJson;
import org.uniprot.core.json.parser.uniprot.CreateUtils;
import org.uniprot.core.uniprotkb.ProteinExistence;
import org.uniprot.core.uniprotkb.UniProtKBEntryType;
import org.uniprot.core.uniprotkb.description.FlagType;
import org.uniprot.core.uniprotkb.evidence.Evidence;
import org.uniprot.core.uniprotkb.taxonomy.Organism;
import org.uniprot.core.uniprotkb.taxonomy.impl.OrganismBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author lgonzales
 * @since 23/10/2020
 */
class GeneCentricJsonConfigTest {

    @Test
    void testSimpleObjectMapperWithCompleteGeneCentricEntry() throws JsonProcessingException {
        ObjectMapper mapper = GeneCentricJsonConfig.getInstance().getSimpleObjectMapper();
        GeneCentricEntry geneCentricEntry = getCompleteGeneCentricEntry();

        String jsonValue = mapper.writeValueAsString(geneCentricEntry);
        assertNotNull(jsonValue);
        assertTrue(jsonValue.contains("\"proteomeId\":\"proteome Id value\""));
    }

    @Test
    void testFullObjectMapperWithSimpleGeneCentricEntry() {
        GeneCentricEntryBuilder builder = new GeneCentricEntryBuilder();

        GeneCentricEntry geneCentricEntry = builder.build();
        ValidateJson.verifyJsonRoundTripParser(
                GeneCentricJsonConfig.getInstance().getFullObjectMapper(), geneCentricEntry);
    }

    @Test
    void testFullObjectMapperWithCompleteGeneCentricEntry() {
        GeneCentricEntry geneCentricEntry = getCompleteGeneCentricEntry();
        ValidateJson.verifyJsonRoundTripParser(
                GeneCentricJsonConfig.getInstance().getFullObjectMapper(), geneCentricEntry);
        ValidateJson.verifyEmptyFields(geneCentricEntry);
    }

    public static GeneCentricEntry getCompleteGeneCentricEntry() {
        List<Evidence> evidences = CreateUtils.createEvidenceList("ECO:0000269|PubMed:11389730");
        Organism organism =
                new OrganismBuilder()
                        .taxonId(123L)
                        .scientificName("ScientificName")
                        .lineagesAdd("Lineage 1")
                        .commonName("common Name")
                        .synonymsAdd("syn name")
                        .evidencesSet(evidences)
                        .build();
        Protein protein =
                new ProteinBuilder()
                        .id("P21802")
                        .entryType(UniProtKBEntryType.TREMBL)
                        .flagType(FlagType.PRECURSOR)
                        .proteinExistence(ProteinExistence.PROTEIN_LEVEL)
                        .geneName("Gene Name")
                        .organism(organism)
                        .proteinName("protein Name")
                        .sequence("AAAAA")
                        .sequenceVersion(2)
                        .uniProtkbId("P21802_ID")
                        .build();

        return new GeneCentricEntryBuilder()
                .proteomeId("proteome Id value")
                .canonicalProtein(protein)
                .relatedProteinsAdd(protein)
                .build();
    }
}

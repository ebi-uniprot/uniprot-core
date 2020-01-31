package org.uniprot.core.json.parser.proteome;

import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;
import org.uniprot.core.json.parser.ValidateJson;
import org.uniprot.core.proteome.CanonicalProtein;
import org.uniprot.core.proteome.GeneNameType;
import org.uniprot.core.proteome.Protein;
import org.uniprot.core.proteome.builder.CanonicalProteinBuilder;
import org.uniprot.core.proteome.builder.ProteinBuilder;
import org.uniprot.core.uniprot.UniProtEntryType;

import com.fasterxml.jackson.databind.ObjectMapper;

class ProteinTest {
    @Test
    void testProtein() {
        String accession = "P12345";
        Protein protein =
                new ProteinBuilder()
                        .accession(accession)
                        .entryType(UniProtEntryType.SWISSPROT)
                        .geneName("some gene")
                        .geneNameType(GeneNameType.ENSEMBL)
                        .sequenceLength(307)
                        .build();
        ValidateJson.verifyJsonRoundTripParser(
                ProteomeJsonConfig.getInstance().getFullObjectMapper(), protein);

        try {
            ObjectMapper mapper = ProteomeJsonConfig.getInstance().getSimpleObjectMapper();
            String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(protein);
            System.out.println(json);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    void testCanonicalProtein() {
        String accession = "P12345";
        Protein protein =
                new ProteinBuilder()
                        .accession(accession)
                        .entryType(UniProtEntryType.SWISSPROT)
                        .geneName("some gene")
                        .geneNameType(GeneNameType.ENSEMBL)
                        .sequenceLength(307)
                        .build();

        Protein rProtein1 =
                new ProteinBuilder()
                        .accession("P23456")
                        .entryType(UniProtEntryType.SWISSPROT)
                        .geneName("some gene2")
                        .geneNameType(GeneNameType.ENSEMBL)
                        .sequenceLength(303)
                        .build();
        Protein rProtein2 =
                new ProteinBuilder()
                        .accession("P26456")
                        .entryType(UniProtEntryType.TREMBL)
                        .geneName("some gene5")
                        .geneNameType(GeneNameType.MOD)
                        .sequenceLength(504)
                        .build();

        CanonicalProtein cProtein =
                new CanonicalProteinBuilder()
                        .canonicalProtein(protein)
                        .relatedProteinsAdd(rProtein1)
                        .relatedProteinsAdd(rProtein2)
                        .build();
        ValidateJson.verifyJsonRoundTripParser(
                ProteomeJsonConfig.getInstance().getFullObjectMapper(), cProtein);
        try {
            ObjectMapper mapper = ProteomeJsonConfig.getInstance().getSimpleObjectMapper();
            String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(cProtein);
            System.out.println(json);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }
}

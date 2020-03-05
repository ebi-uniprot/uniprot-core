package org.uniprot.core.json.parser.uniparc;

import static org.junit.jupiter.api.Assertions.fail;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.uniprot.core.Property;
import org.uniprot.core.json.parser.ValidateJson;
import org.uniprot.core.uniparc.UniParcCrossReference;
import org.uniprot.core.uniparc.UniParcDatabase;
import org.uniprot.core.uniparc.builder.UniParcCrossReferenceBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author jluo
 * @date: 24 May 2019
 */
class UniParcDbCrossReferenceTest {
    @Test
    void test() {
        UniParcCrossReferenceBuilder builder = new UniParcCrossReferenceBuilder();
        builder.database(UniParcDatabase.TREMBL)
                .id("A0A0C4DHG2")
                .versionI(1)
                .version(1)
                .active(true)
                .created(LocalDate.of(2015, 4, 1))
                .lastUpdated(LocalDate.of(2019, 5, 8));
        List<Property> properties = new ArrayList<>();
        properties.add(new Property(UniParcCrossReference.PROPERTY_NCBI_TAXONOMY_ID, "7227"));
        properties.add(new Property(UniParcCrossReference.PROPERTY_NCBI_TAXONOMY_ID, "9606"));
        properties.add(
                new Property(UniParcCrossReference.PROPERTY_PROTEIN_NAME, "Gelsolin, isoform J"));
        properties.add(new Property(UniParcCrossReference.PROPERTY_GENE_NAME, "Gel"));
        builder.propertiesSet(properties);

        UniParcCrossReference xref = builder.build();

        ValidateJson.verifyJsonRoundTripParser(
                UniParcJsonConfig.getInstance().getFullObjectMapper(), xref);

        try {
            ObjectMapper mapper = UniParcJsonConfig.getInstance().getSimpleObjectMapper();
            String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(xref);
            System.out.println(json);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    void testNoProperty() {
        UniParcCrossReferenceBuilder builder = new UniParcCrossReferenceBuilder();
        builder.database(UniParcDatabase.ENSEMBL_VERTEBRATE)
                .id("CG1106-PB")
                .versionI(1)
                .active(false)
                .created(LocalDate.of(2003, 4, 1))
                .lastUpdated(LocalDate.of(2007, 11, 22));
        UniParcCrossReference xref = builder.build();
        ValidateJson.verifyJsonRoundTripParser(
                UniParcJsonConfig.getInstance().getFullObjectMapper(), xref);

        try {
            ObjectMapper mapper = UniParcJsonConfig.getInstance().getSimpleObjectMapper();
            String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(xref);
            System.out.println(json);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }
}

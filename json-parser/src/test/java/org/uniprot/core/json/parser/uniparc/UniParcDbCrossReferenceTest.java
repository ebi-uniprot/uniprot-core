package org.uniprot.core.json.parser.uniparc;

import static org.junit.jupiter.api.Assertions.fail;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.uniprot.core.Property;
import org.uniprot.core.json.parser.ValidateJson;
import org.uniprot.core.json.parser.uniprot.CreateUtils;
import org.uniprot.core.uniparc.UniParcCrossReference;
import org.uniprot.core.uniparc.UniParcDatabase;
import org.uniprot.core.uniparc.impl.UniParcCrossReferenceBuilder;
import org.uniprot.core.uniprotkb.evidence.Evidence;
import org.uniprot.core.uniprotkb.taxonomy.Organism;
import org.uniprot.core.uniprotkb.taxonomy.impl.OrganismBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author jluo
 * @date: 24 May 2019
 */
@Slf4j
class UniParcDbCrossReferenceTest {
    @Test
    void test() {
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

        UniParcCrossReferenceBuilder builder = new UniParcCrossReferenceBuilder();
        builder.database(UniParcDatabase.TREMBL)
                .id("A0A0C4DHG2")
                .versionI(1)
                .version(1)
                .active(true)
                .created(LocalDate.of(2015, 4, 1))
                .lastUpdated(LocalDate.of(2019, 5, 8))
                .organism(organism)
                .geneName("Gel")
                .proteomeId("UPI")
                .component("ComponentValue")
                .chain("chainValue")
                .ncbiGi("ncbiGiValue")
                .proteinName("proteinNameValue");
        List<Property> properties = new ArrayList<>();
        properties.add(new Property("Prop1", "propValue1"));
        builder.propertiesSet(properties);

        UniParcCrossReference xref = builder.build();

        ValidateJson.verifyJsonRoundTripParser(
                UniParcJsonConfig.getInstance().getFullObjectMapper(), xref);

        try {
            ObjectMapper mapper = UniParcJsonConfig.getInstance().getSimpleObjectMapper();
            String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(xref);
            log.debug(json);
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
            log.debug(json);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }
}

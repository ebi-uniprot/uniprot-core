package org.uniprot.core.json.parser.uniparc;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.uniprot.core.Property;
import org.uniprot.core.Sequence;
import org.uniprot.core.impl.SequenceBuilder;
import org.uniprot.core.json.parser.ValidateJson;
import org.uniprot.core.json.parser.uniprot.CreateUtils;
import org.uniprot.core.uniparc.*;
import org.uniprot.core.uniparc.impl.*;
import org.uniprot.core.uniprotkb.evidence.Evidence;
import org.uniprot.core.uniprotkb.taxonomy.Organism;
import org.uniprot.core.uniprotkb.taxonomy.impl.OrganismBuilder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.fail;

/**
 * @author jluo
 * @date: 24 May 2019
 */
public class UniParcEntryTest {
    @Test
    void testUniParcEntryJsonParser() {
        UniParcEntry entry = getCompleteUniParcEntry();

        ValidateJson.verifyJsonRoundTripParser(
                UniParcJsonConfig.getInstance().getFullObjectMapper(), entry);
        ValidateJson.verifyEmptyFields(entry);
        try {
            ObjectMapper mapper = UniParcJsonConfig.getInstance().getSimpleObjectMapper();
            String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(entry);
            System.out.println(json);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    public static UniParcEntry getCompleteUniParcEntry() {
        UniParcEntryBuilder builder = new UniParcEntryBuilder();
        String sequence =
                "MALYSISKPVGSKINKHSYQDENTLVGKQALSKGTEKTKLSTNFEINLPRRTVLSDVSNV"
                        + "GKNNADEKDTKKAKRSFDESNLSTNEEADKPVESKFVKKLKVYSKNADPSVETLQKDRVS"
                        + "NVDDHLSSNPLMAEEYAPEIFEYIRKLDLKCLPNPKYMDQQKELTWKMREILNEWLVEIH"
                        + "SNFCLMPETLYLAVNIIDRFLSRRSCSLSKFQLTGITALLIASKYEEVMCPSIQNFVYMT"
                        + "DGAFTVEDVCVAERYMLNVLNFDLSYPSPLNFLRKISQAEGYDAQTRTLGKYLTEIYLFD"
                        + "HDLLRYPMSKIAAAAMYLSRRLLRRGPWTPKLVESSGGYEEHELKEIAYIMLHYHNKPLE"
                        + "HKAFFQKYSSKRFLKASIFVHQLVRQRYSVNRTDDDDLQSEPSSSLTNDGH";
        Sequence uniSeq = new SequenceBuilder(sequence).build();
        List<SequenceFeature> sfs = new ArrayList<>();
        Arrays.stream(SignatureDbType.values())
                .forEach(
                        signatureType -> {
                            sfs.add(getSeqFeature(signatureType));
                        });

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
        Proteome proteomeIdComponent = new ProteomeBuilder().id("UPI").component( "ComponentValue").build();
        builder.uniParcId("UPI0000083A08")
                .sequence(uniSeq)
                .sequenceFeaturesSet(sfs)
                .uniParcCrossReferencesAdd(
                        new UniParcCrossReferenceBuilder()
                                .database(UniParcDatabase.ENSEMBL_VERTEBRATE)
                                .id("CG1106-PB")
                                .versionI(1)
                                .version(10)
                                .active(true)
                                .created(LocalDate.of(2003, 4, 1))
                                .lastUpdated(LocalDate.of(2007, 11, 22))
                                .organism(organism)
                                .geneName("Gel")
                                .proteomesAdd(proteomeIdComponent)
                                .chain("chainValue")
                                .ncbiGi("ncbiGiValue")
                                .proteinName("proteinNameValue")
                                .build());

        UniParcCrossReferenceBuilder xrefBuilder = new UniParcCrossReferenceBuilder();
        xrefBuilder
                .database(UniParcDatabase.TREMBL)
                .id("A0A0C4DHG2")
                .versionI(1)
                .version(1)
                .active(true)
                .created(LocalDate.of(2015, 4, 1))
                .lastUpdated(LocalDate.of(2019, 5, 8))
                .organism(organism)
                .geneName("Gel")
                .proteomesAdd(proteomeIdComponent)
                .chain("chainValue")
                .ncbiGi("ncbiGiValue")
                .proteinName("proteinNameValue");
        List<Property> properties = new ArrayList<>();
        properties.add(new Property("Prop1", "Prop1Value"));
        xrefBuilder.propertiesSet(properties);
        builder.uniParcCrossReferencesAdd(xrefBuilder.build());

        builder.uniprotExclusionReason("good reason");
        return builder.build();
    }

    static SequenceFeature getSeqFeature(SignatureDbType signatureType) {
        return new SequenceFeatureBuilder()
                .signatureDbType(signatureType)
                .signatureDbId("id-" + signatureType)
                .locationsAdd(new SequenceFeatureLocationBuilder().range(81, 163).alignment("48M").build())
                .interproGroup(
                        new InterProGroupBuilder()
                                .id("IPR007123")
                                .name("Gelsolin-like domain")
                                .build())
                .build();
    }
}

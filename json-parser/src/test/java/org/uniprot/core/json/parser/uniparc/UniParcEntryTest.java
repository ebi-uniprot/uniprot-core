package org.uniprot.core.json.parser.uniparc;

import static org.junit.jupiter.api.Assertions.fail;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.uniprot.core.Location;
import org.uniprot.core.Property;
import org.uniprot.core.Sequence;
import org.uniprot.core.impl.SequenceBuilder;
import org.uniprot.core.json.parser.ValidateJson;
import org.uniprot.core.uniparc.*;
import org.uniprot.core.uniparc.UniParcCrossReference;
import org.uniprot.core.uniparc.impl.InterProGroupBuilder;
import org.uniprot.core.uniparc.impl.SequenceFeatureBuilder;
import org.uniprot.core.uniparc.impl.UniParcCrossReferenceBuilder;
import org.uniprot.core.uniparc.impl.UniParcEntryBuilder;
import org.uniprot.core.uniprotkb.taxonomy.impl.TaxonomyBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author jluo
 * @date: 24 May 2019
 */
public class UniParcEntryTest {
    @Test
    void test() {
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
                                .build());

        UniParcCrossReferenceBuilder xrefBuilder = new UniParcCrossReferenceBuilder();
        xrefBuilder
                .database(UniParcDatabase.TREMBL)
                .id("A0A0C4DHG2")
                .versionI(1)
                .version(1)
                .active(true)
                .created(LocalDate.of(2015, 4, 1))
                .lastUpdated(LocalDate.of(2019, 5, 8));
        List<Property> properties = new ArrayList<>();
        properties.add(new Property(UniParcCrossReference.PROPERTY_NCBI_TAXONOMY_ID, "9606"));
        properties.add(
                new Property(UniParcCrossReference.PROPERTY_PROTEIN_NAME, "Gelsolin, isoform J"));
        properties.add(new Property(UniParcCrossReference.PROPERTY_GENE_NAME, "Gel"));
        properties.add(new Property(UniParcCrossReference.PROPERTY_PROTEOME_ID, "UPI"));
        xrefBuilder.propertiesSet(properties);
        builder.uniParcCrossReferencesAdd(xrefBuilder.build());

        builder.uniprotExclusionReason("good reason");
        builder.taxonomiesAdd(
                new TaxonomyBuilder()
                        .taxonId(9606)
                        .scientificName("Homo Sapiens")
                        .commonName("human")
                        .mnemonic("value")
                        .synonymsAdd("syn value")
                        .build());
        return builder.build();
    }

    private static SequenceFeature getSeqFeature(SignatureDbType signatureType) {
        return new SequenceFeatureBuilder()
                .signatureDbType(signatureType)
                .signatureDbId("id-" + signatureType)
                .locationsAdd(new Location(81, 163))
                .interproGroup(
                        new InterProGroupBuilder()
                                .id("IPR007123")
                                .name("Gelsolin-like domain")
                                .build())
                .build();
    }
}

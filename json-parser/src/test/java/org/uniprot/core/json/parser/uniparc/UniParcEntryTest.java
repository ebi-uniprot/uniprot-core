package org.uniprot.core.json.parser.uniparc;

import static org.junit.jupiter.api.Assertions.fail;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.uniprot.core.Location;
import org.uniprot.core.Property;
import org.uniprot.core.Sequence;
import org.uniprot.core.builder.SequenceBuilder;
import org.uniprot.core.json.parser.ValidateJson;
import org.uniprot.core.uniparc.SequenceFeature;
import org.uniprot.core.uniparc.SignatureDbType;
import org.uniprot.core.uniparc.UniParcDBCrossReference;
import org.uniprot.core.uniparc.UniParcDatabaseType;
import org.uniprot.core.uniparc.UniParcEntry;
import org.uniprot.core.uniparc.builder.InterProGroupBuilder;
import org.uniprot.core.uniparc.builder.SequenceFeatureBuilder;
import org.uniprot.core.uniparc.builder.UniParcDBCrossReferenceBuilder;
import org.uniprot.core.uniparc.builder.UniParcEntryBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author jluo
 * @date: 24 May 2019
 */
class UniParcEntryTest {
    @Test
    void test() {
        UniParcEntry entry = createEntry();

        ValidateJson.verifyJsonRoundTripParser(
                UniParcJsonConfig.getInstance().getFullObjectMapper(), entry);

        try {
            ObjectMapper mapper = UniParcJsonConfig.getInstance().getSimpleObjectMapper();
            String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(entry);
            System.out.println(json);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    private UniParcEntry createEntry() {
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
        SequenceFeatureBuilder sfBuilder = new SequenceFeatureBuilder();
        sfBuilder
                .signatureDbType(SignatureDbType.PANTHER)
                .signatureDbId("PTHR11977")
                .locationsAdd(new Location(49, 790))
                .interproGroup(
                        new InterProGroupBuilder().id("IPR007122").name("Villin/Gelsolin").build());

        SequenceFeatureBuilder sfBuilder2 = new SequenceFeatureBuilder();
        sfBuilder2
                .signatureDbType(SignatureDbType.PFAM)
                .signatureDbId("PF00626")
                .locationsAdd(new Location(81, 163))
                .locationsAdd(new Location(202, 267))
                .locationsAdd(new Location(330, 398))
                .locationsAdd(new Location(586, 653))
                .locationsAdd(new Location(692, 766))
                .interproGroup(
                        new InterProGroupBuilder()
                                .id("IPR007123")
                                .name("Gelsolin-like domain")
                                .build());
        sfs.add(sfBuilder.build());
        sfs.add(sfBuilder2.build());
        builder.uniParcId("UPI0000083A08")
                .sequence(uniSeq)
                .sequenceFeaturesSet(sfs)
                .databaseCrossReferencesAdd(
                        new UniParcDBCrossReferenceBuilder()
                                .databaseType(UniParcDatabaseType.ENSEMBL_VERTEBRATE)
                                .id("CG1106-PB")
                                .versionI(1)
                                .active(false)
                                .created(LocalDate.of(2003, 4, 1))
                                .lastUpdated(LocalDate.of(2007, 11, 22))
                                .build());

        UniParcDBCrossReferenceBuilder xrefBuilder = new UniParcDBCrossReferenceBuilder();
        xrefBuilder
                .databaseType(UniParcDatabaseType.TREMBL)
                .id("A0A0C4DHG2")
                .versionI(1)
                .version(1)
                .active(true)
                .created(LocalDate.of(2015, 4, 1))
                .lastUpdated(LocalDate.of(2019, 5, 8));
        List<Property> properties = new ArrayList<>();
        properties.add(new Property(UniParcDBCrossReference.PROPERTY_NCBI_TAXONOMY_ID, "9606"));
        properties.add(
                new Property(UniParcDBCrossReference.PROPERTY_PROTEIN_NAME, "Gelsolin, isoform J"));
        properties.add(new Property(UniParcDBCrossReference.PROPERTY_GENE_NAME, "Gel"));

        xrefBuilder.propertiesSet(properties);
        builder.databaseCrossReferencesAdd(xrefBuilder.build());

        // id="NC_004354_874_0" version_i="5" active="Y" created="2007-04-27" last="2007-04-27">
        UniParcDBCrossReferenceBuilder xrefBuilder2 = new UniParcDBCrossReferenceBuilder();
        xrefBuilder2
                .databaseType(UniParcDatabaseType.TROME)
                .id("NC_004354_874_0")
                .versionI(5)
                .active(true)
                .created(LocalDate.of(2007, 4, 27))
                .lastUpdated(LocalDate.of(2007, 4, 27));
        List<Property> properties2 = new ArrayList<>();
        properties2.add(new Property(UniParcDBCrossReference.PROPERTY_NCBI_TAXONOMY_ID, "7227"));
        xrefBuilder2.propertiesSet(properties2);
        builder.databaseCrossReferencesAdd(xrefBuilder2.build());
        return builder.build();
    }
}

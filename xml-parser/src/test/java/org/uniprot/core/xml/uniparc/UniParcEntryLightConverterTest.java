package org.uniprot.core.xml.uniparc;

import org.junit.jupiter.api.Test;
import org.uniprot.core.Location;
import org.uniprot.core.Sequence;
import org.uniprot.core.impl.SequenceBuilder;
import org.uniprot.core.uniparc.SequenceFeature;
import org.uniprot.core.uniparc.SignatureDbType;
import org.uniprot.core.uniparc.UniParcEntryLight;
import org.uniprot.core.uniparc.impl.InterProGroupBuilder;
import org.uniprot.core.uniparc.impl.SequenceFeatureBuilder;
import org.uniprot.core.uniparc.impl.UniParcEntryLightBuilder;
import org.uniprot.core.xml.jaxb.uniparc.Entry;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UniParcEntryLightConverterTest {

    @Test
    void objectToXMLAndXMLToObjectTest() {
        UniParcEntryLight uniParcEntryLight = createUniParcEntryLight();
        UniParcEntryLightConverter converter = new UniParcEntryLightConverter();
        Entry xmlObj = converter.toXml(uniParcEntryLight);
        UniParcEntryLight converted = converter.fromXml(xmlObj);
        assertEquals(uniParcEntryLight, converted);
    }

    private UniParcEntryLight createUniParcEntryLight() {
        UniParcEntryLightBuilder builder = new UniParcEntryLightBuilder();
        String sequenceStr =
                "MALYSISKPVGSKINKHSYQDENTLVGKQALSKGTEKTKLSTNFEINLPRRTVLSDVSNV"
                        + "GKNNADEKDTKKAKRSFDESNLSTNEEADKPVESKFVKKLKVYSKNADPSVETLQKDRVS"
                        + "NVDDHLSSNPLMAEEYAPEIFEYIRKLDLKCLPNPKYMDQQKELTWKMREILNEWLVEIH"
                        + "SNFCLMPETLYLAVNIIDRFLSRRSCSLSKFQLTGITALLIASKYEEVMCPSIQNFVYMT"
                        + "DGAFTVEDVCVAERYMLNVLNFDLSYPSPLNFLRKISQAEGYDAQTRTLGKYLTEIYLFD"
                        + "HDLLRYPMSKIAAAAMYLSRRLLRRGPWTPKLVESSGGYEEHELKEIAYIMLHYHNKPLE"
                        + "HKAFFQKYSSKRFLKASIFVHQLVRQRYSVNRTDDDDLQSEPSSSLTNDGH";
        Sequence sequence = new SequenceBuilder(sequenceStr).build();
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
                .sequence(sequence)
                .sequenceFeaturesSet(sfs);
        return builder.build();
    }
}

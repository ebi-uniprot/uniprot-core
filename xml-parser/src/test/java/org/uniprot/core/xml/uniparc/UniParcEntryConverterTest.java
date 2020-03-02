package org.uniprot.core.xml.uniparc;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.uniprot.core.Location;
import org.uniprot.core.Property;
import org.uniprot.core.Sequence;
import org.uniprot.core.builder.SequenceBuilder;
import org.uniprot.core.uniparc.*;
import org.uniprot.core.uniparc.UniParcDatabase;
import org.uniprot.core.uniparc.builder.InterProGroupBuilder;
import org.uniprot.core.uniparc.builder.SequenceFeatureBuilder;
import org.uniprot.core.uniparc.builder.UniParcCrossReferenceBuilder;
import org.uniprot.core.uniparc.builder.UniParcEntryBuilder;
import org.uniprot.core.xml.jaxb.uniparc.Entry;

/**
 * @author jluo
 * @date: 24 May 2019
 */
class UniParcEntryConverterTest {

    @Test
    void test() {
        UniParcEntry uniparcEntry = createEntry();
        UniParcEntryConverter converter = new UniParcEntryConverter();
        Entry xmlObj = converter.toXml(uniparcEntry);
        System.out.println(UniParcXmlTestHelper.toXmlString(xmlObj, Entry.class, "entry"));
        UniParcEntry converted = converter.fromXml(xmlObj);
        assertEquals(2, converted.getTaxonomies().size());
        assertEquals(uniparcEntry, converted);
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
                        new UniParcCrossReferenceBuilder()
                                .databaseType(UniParcDatabase.ENSEMBL_VERTEBRATE)
                                .id("CG1106-PB")
                                .versionI(1)
                                .active(false)
                                .created(LocalDate.of(2003, 4, 1))
                                .lastUpdated(LocalDate.of(2007, 11, 22))
                                .build());

        UniParcCrossReferenceBuilder xrefBuilder = new UniParcCrossReferenceBuilder();
        xrefBuilder
                .databaseType(UniParcDatabase.TREMBL)
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

        xrefBuilder.propertiesSet(properties);
        builder.databaseCrossReferencesAdd(xrefBuilder.build());

        // id="NC_004354_874_0" version_i="5" active="Y" created="2007-04-27" last="2007-04-27">
        UniParcCrossReferenceBuilder xrefBuilder2 = new UniParcCrossReferenceBuilder();
        xrefBuilder2
                .databaseType(UniParcDatabase.TROME)
                .id("NC_004354_874_0")
                .versionI(5)
                .active(true)
                .created(LocalDate.of(2007, 4, 27))
                .lastUpdated(LocalDate.of(2007, 4, 27));
        List<Property> properties2 = new ArrayList<>();
        properties2.add(new Property(UniParcCrossReference.PROPERTY_NCBI_TAXONOMY_ID, "7227"));
        xrefBuilder2.propertiesSet(properties2);
        builder.databaseCrossReferencesAdd(xrefBuilder2.build());
        return builder.build();
    }
}

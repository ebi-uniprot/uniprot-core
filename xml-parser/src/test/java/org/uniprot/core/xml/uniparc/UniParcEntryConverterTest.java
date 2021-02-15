package org.uniprot.core.xml.uniparc;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.uniprot.core.Location;
import org.uniprot.core.Sequence;
import org.uniprot.core.impl.SequenceBuilder;
import org.uniprot.core.uniparc.*;
import org.uniprot.core.uniparc.UniParcDatabase;
import org.uniprot.core.uniparc.impl.InterProGroupBuilder;
import org.uniprot.core.uniparc.impl.SequenceFeatureBuilder;
import org.uniprot.core.uniparc.impl.UniParcCrossReferenceBuilder;
import org.uniprot.core.uniparc.impl.UniParcEntryBuilder;
import org.uniprot.core.uniprotkb.taxonomy.Organism;
import org.uniprot.core.uniprotkb.taxonomy.impl.OrganismBuilder;
import org.uniprot.core.xml.jaxb.uniparc.Entry;

/**
 * @author jluo
 * @date: 24 May 2019
 */
@Slf4j
class UniParcEntryConverterTest {

    @Test
    void test() {
        UniParcEntry uniparcEntry = createEntry();
        UniParcEntryConverter converter = new UniParcEntryConverter();
        Entry xmlObj = converter.toXml(uniparcEntry);
        log.debug(UniParcXmlTestHelper.toXmlString(xmlObj, Entry.class, "entry"));
        UniParcEntry converted = converter.fromXml(xmlObj);
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
                .uniParcCrossReferencesAdd(
                        new UniParcCrossReferenceBuilder()
                                .database(UniParcDatabase.ENSEMBL_VERTEBRATE)
                                .id("CG1106-PB")
                                .versionI(1)
                                .active(false)
                                .created(LocalDate.of(2003, 4, 1))
                                .lastUpdated(LocalDate.of(2007, 11, 22))
                                .build());
        Organism taxonomy = new OrganismBuilder().taxonId(9606).build();

        UniParcCrossReferenceBuilder xrefBuilder = new UniParcCrossReferenceBuilder();
        xrefBuilder
                .database(UniParcDatabase.TREMBL)
                .id("A0A0C4DHG2")
                .versionI(1)
                .version(1)
                .active(true)
                .created(LocalDate.of(2015, 4, 1))
                .lastUpdated(LocalDate.of(2019, 5, 8))
                .organism(taxonomy)
                .proteinName("Gelsolin, isoform J")
                .geneName("Gel");

        builder.uniParcCrossReferencesAdd(xrefBuilder.build());

        Organism taxonomy2 = new OrganismBuilder().taxonId(7227).build();

        // id="NC_004354_874_0" version_i="5" active="Y" created="2007-04-27" last="2007-04-27">
        UniParcCrossReferenceBuilder xrefBuilder2 = new UniParcCrossReferenceBuilder();
        xrefBuilder2
                .database(UniParcDatabase.TROME)
                .id("NC_004354_874_0")
                .versionI(5)
                .active(true)
                .created(LocalDate.of(2007, 4, 27))
                .lastUpdated(LocalDate.of(2007, 4, 27))
                .organism(taxonomy2)
                .proteinName("some pname")
                .proteomeId("UP00000564")
                .component("chromosome 1");
        builder.uniParcCrossReferencesAdd(xrefBuilder2.build());
        return builder.build();
    }
}

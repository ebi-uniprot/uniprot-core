package org.uniprot.core.parser.fasta.uniparc;

import static org.junit.jupiter.api.Assertions.*;
import static org.uniprot.core.uniparc.impl.UniParcEntryLightBuilder.HAS_ACTIVE_CROSS_REF;

import org.junit.jupiter.api.Test;
import org.uniprot.core.Property;
import org.uniprot.core.Sequence;
import org.uniprot.core.impl.SequenceBuilder;
import org.uniprot.core.uniparc.*;
import org.uniprot.core.uniparc.impl.*;
import org.uniprot.core.uniprotkb.taxonomy.Organism;
import org.uniprot.core.uniprotkb.taxonomy.impl.OrganismBuilder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
/**
 * @author jluo
 * @date: 24 Jun 2019
 */
class UniParcFastaParserTest {

    public static final String EXPECTED_FASTA_RESULT = """
            >UPI0000083A08 status=active
            MSMAMARALATLGRLRYRVSGQLPLLDETAIEVMAGGQFLDGRKAREELGFFSTTALDDT
            LLRAIDWFRDNGYFNA""";
    public static final String EXPECTED_FASTA_RESULT_INACTIVE = """
            >UPI0000083A08 status=inactive
            MSMAMARALATLGRLRYRVSGQLPLLDETAIEVMAGGQFLDGRKAREELGFFSTTALDDT
            LLRAIDWFRDNGYFNA""";
    @Test
    void testUniParcEntryToFasta() {
        UniParcEntry entry = create();
        String fasta = UniParcFastaParser.toFasta(entry);
        assertEquals(EXPECTED_FASTA_RESULT, fasta);
    }

    @Test
    void testUniParcEntryLightToFasta() {
        UniParcEntryLight entry = createEntryLight();
        String fasta = UniParcFastaParser.toFasta(entry);
        assertEquals(EXPECTED_FASTA_RESULT, fasta);
    }

    @Test
    void testUniParcEntryLightToFastaInactive() {
        UniParcEntryLight entry = createEntryLight();
        entry = UniParcEntryLightBuilder.from(entry).extraAttributesAdd(HAS_ACTIVE_CROSS_REF, false).build();
        String fasta = UniParcFastaParser.toFasta(entry);
        assertEquals(EXPECTED_FASTA_RESULT_INACTIVE, fasta);
    }

    @Test
    void testUniParcLightFastaSeqRange(){
        String expectedFasta = ">UPI0000083A08|5-10 status=active\n" +
                "MARALA";
        UniParcEntryLight entry = createEntryLight();
        String seqRange = "5-10";
        String fasta = UniParcFastaParser.toFasta(entry, seqRange);
        assertNotNull(fasta);
        assertEquals(expectedFasta, fasta);
    }

    @Test
    void testUniParcLightFastaSeqRangeOutOfRangeShouldReturnTillEnd(){
        String expectedFasta = ">UPI0000083A08|5-1000000 status=active\n" +
                "MARALATLGRLRYRVSGQLPLLDETAIEVMAGGQFLDGRKAREELGFFSTTALDDTLLRA\n" +
                "IDWFRDNGYFNA";
        UniParcEntryLight entry = createEntryLight();
        String seqRange = "5-1000000";
        String fasta = UniParcFastaParser.toFasta(entry, seqRange);
        assertNotNull(fasta);
        assertEquals(expectedFasta, fasta);
    }

    private UniParcEntry create() {
        Sequence sequence = getSequence();
        List<UniParcCrossReference> xrefs = getXrefs();
        return new UniParcEntryBuilder()
                        .uniParcId(new UniParcIdBuilder("UPI0000083A08").build())
                        .uniParcCrossReferencesSet(xrefs)
                        .sequence(sequence)
                        .build();
    }

    private UniParcEntryLight createEntryLight() {
        return new UniParcEntryLightBuilder()
                .uniParcId("UPI0000083A08")
                .sequence(getSequence())
                .build();
    }

    private static Sequence getSequence() {
        String seq =
                "MSMAMARALATLGRLRYRVSGQLPLLDETAIEVMAGGQFLDGRKAREELGFFSTTALDDT" + "LLRAIDWFRDNGYFNA";
        Sequence sequence = new SequenceBuilder(seq).build();
        return sequence;
    }

    private List<UniParcCrossReference> getXrefs() {
        Organism taxonomy =
                new OrganismBuilder().taxonId(9606).scientificName("Homo sapiens").build();
        List<Property> properties = new ArrayList<>();
        properties.add(new Property("prop1", "pvalue"));
        UniParcCrossReference xref =
                new UniParcCrossReferenceBuilder()
                        .versionI(3)
                        .database(UniParcDatabase.SWISSPROT)
                        .id("P12345")
                        .version(7)
                        .active(true)
                        .created(LocalDate.of(2017, 5, 17))
                        .lastUpdated(LocalDate.of(2017, 2, 27))
                        .propertiesSet(properties)
                        .organism(taxonomy)
                        .proteinName("some pname")
                        .geneName("some gname")
                        .build();

        List<Property> properties2 = new ArrayList<>();
        properties.add(new Property("prop2", "pvalue2"));
        Organism taxonomy2 = new OrganismBuilder().taxonId(10090).scientificName("MOUSE").build();

        UniParcCrossReference xref2 =
                new UniParcCrossReferenceBuilder()
                        .versionI(1)
                        .database(UniParcDatabase.TREMBL)
                        .id("P52346")
                        .version(7)
                        .active(true)
                        .created(LocalDate.of(2017, 2, 12))
                        .lastUpdated(LocalDate.of(2017, 4, 23))
                        .propertiesSet(properties2)
                        .organism(taxonomy2)
                        .proteinName("some pname")
                        .proteomeId("UP00000564")
                        .component("chromosome 1")
                        .build();

        return Arrays.asList(xref, xref2);
    }

}

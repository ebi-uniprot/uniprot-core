package org.uniprot.core.parser.fasta.uniparc;

import static org.junit.jupiter.api.Assertions.*;
import static org.uniprot.core.parser.fasta.uniparc.UniParcFastaParserTestUtils.*;
import static org.uniprot.core.uniparc.impl.UniParcEntryLightBuilder.HAS_ACTIVE_CROSS_REF;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniparc.*;
import org.uniprot.core.uniparc.impl.UniParcEntryLightBuilder;

/**
 * @author jluo
 * @date: 24 Jun 2019
 */
class UniParcEntryLightFastaParserTest {

    public static final String EXPECTED_FASTA_RESULT = """
            >UPI0000083A08 status=active
            MSMAMARALATLGRLRYRVSGQLPLLDETAIEVMAGGQFLDGRKAREELGFFSTTALDDT
            LLRAIDWFRDNGYFNA""";
    public static final String EXPECTED_FASTA_RESULT_INACTIVE = """
            >UPI0000083A08 status=inactive
            MSMAMARALATLGRLRYRVSGQLPLLDETAIEVMAGGQFLDGRKAREELGFFSTTALDDT
            LLRAIDWFRDNGYFNA""";


    @Test
    void testUniParcEntryLightToFasta() {
        UniParcEntryLight entry = createEntryLight();
        String fasta = UniParcEntryLightFastaParser.toFasta(entry);
        assertEquals(EXPECTED_FASTA_RESULT, fasta);
    }

    @Test
    void testUniParcEntryLightToFastaInactive() {
        UniParcEntryLight entry = createEntryLight();
        entry = UniParcEntryLightBuilder.from(entry).extraAttributesAdd(HAS_ACTIVE_CROSS_REF, false).build();
        String fasta = UniParcEntryLightFastaParser.toFasta(entry);
        assertEquals(EXPECTED_FASTA_RESULT_INACTIVE, fasta);
    }

    private UniParcEntryLight createEntryLight() {
        return new UniParcEntryLightBuilder()
                .uniParcId("UPI0000083A08")
                .sequence(getSequence())
                .build();
    }

}

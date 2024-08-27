package org.uniprot.core.parser.fasta.uniparc;

import static org.junit.jupiter.api.Assertions.*;
import static org.uniprot.core.parser.fasta.uniparc.UniParcFastaParserTestUtils.*;

import org.junit.jupiter.api.Test;
import org.uniprot.core.parser.fasta.UniParcFastaParser;
import org.uniprot.core.uniparc.*;
import org.uniprot.core.uniparc.impl.UniParcEntryBuilder;
import org.uniprot.core.uniparc.impl.UniParcIdBuilder;

/**
 * @author jluo
 * @date: 24 Jun 2019
 */
class UniParcFastaParserTest {

    @Test
    void testToFastaActive() {
        UniParcEntry entry = create();
        String fasta = UniParcFastaParser.toFasta(entry);
        String expected =
                ">UPI0000083A08 status=active\n"
                        + "MSMAMARALATLGRLRYRVSGQLPLLDETAIEVMAGGQFLDGRKAREELGFFSTTALDDT\n"
                        + "LLRAIDWFRDNGYFNA";
        assertEquals(expected, fasta);
    }

    @Test
    void testToFastaInactive() {
        UniParcEntry entry =
                new UniParcEntryBuilder()
                        .uniParcId(new UniParcIdBuilder("UPI0000083A08").build())
                        .uniParcCrossReferencesSet(getUniProtXrefs(false))
                        .sequence(getSequence())
                        .build();
        String fasta = UniParcFastaParser.toFasta(entry);
        String expected =
                ">UPI0000083A08 status=inactive\n"
                        + "MSMAMARALATLGRLRYRVSGQLPLLDETAIEVMAGGQFLDGRKAREELGFFSTTALDDT\n"
                        + "LLRAIDWFRDNGYFNA";
        assertEquals(expected, fasta);
    }

}

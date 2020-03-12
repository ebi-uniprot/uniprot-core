package org.uniprot.core.flatfile.writer.line;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.uniprot.core.flatfile.parser.impl.pe.PELineBuilder;
import org.uniprot.core.flatfile.writer.FFLine;
import org.uniprot.core.uniprotkb.ProteinExistence;

class PELineBuildTest {
    @Test
    void test() {
        String peLine = "PE   1: Evidence at protein level;";
        PELineBuilder builder = new PELineBuilder();
        FFLine ffLine = builder.build(ProteinExistence.PROTEIN_LEVEL);
        String resultString = ffLine.toString();
        assertEquals(peLine, resultString);
    }
}

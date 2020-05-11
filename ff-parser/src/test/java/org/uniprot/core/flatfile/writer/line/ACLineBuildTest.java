package org.uniprot.core.flatfile.writer.line;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.uniprot.core.flatfile.parser.impl.ac.ACLineBuilder;
import org.uniprot.core.flatfile.writer.FFLine;
import org.uniprot.core.uniprotkb.UniProtKBAccession;
import org.uniprot.core.uniprotkb.impl.UniProtKBAccessionBuilder;

import java.util.ArrayList;
import java.util.List;

class ACLineBuildTest {
    private ACLineBuilder builder = new ACLineBuilder();

    @Test
    void test1() {
        String acLine = "AC   P99999; P00001; Q6NUR2; Q6NX69; Q96BV4;";
        List<UniProtKBAccession> acces = new ArrayList<>();
        acces.add(new UniProtKBAccessionBuilder("P99999").build());
        acces.add(new UniProtKBAccessionBuilder("P00001").build());
        acces.add(new UniProtKBAccessionBuilder("Q6NUR2").build());
        acces.add(new UniProtKBAccessionBuilder("Q6NX69").build());
        acces.add(new UniProtKBAccessionBuilder("Q96BV4").build());
        FFLine ffLine = builder.build(acces);
        String resultString = ffLine.toString();
        assertEquals(acLine, resultString);
    }

    @Test
    void testNewAc() {
        String acLine = "AC   A0A000ACJ5; P00001; Q6NUR2; Q6NX69; Q96BV4;";
        List<UniProtKBAccession> acces = new ArrayList<>();
        acces.add(new UniProtKBAccessionBuilder("A0A000ACJ5").build());
        acces.add(new UniProtKBAccessionBuilder("P00001").build());
        acces.add(new UniProtKBAccessionBuilder("Q6NUR2").build());
        acces.add(new UniProtKBAccessionBuilder("Q6NX69").build());
        acces.add(new UniProtKBAccessionBuilder("Q96BV4").build());
        FFLine ffLine = builder.build(acces);
        String resultString = ffLine.toString();
        assertEquals(acLine, resultString);
    }

    @Test
    void test2() {
        String acLine =
                "AC   Q96PK6; B0LM41; B3KMN4; D6RGD8; O75932; Q2PYN1; Q53GV1; Q68DQ9; Q96PK5;\n"
                        + "AC   A0A000ACJ5; Q96BV4;";
        List<UniProtKBAccession> acces = new ArrayList<>();
        acces.add(new UniProtKBAccessionBuilder("Q96PK6").build());
        acces.add(new UniProtKBAccessionBuilder("B0LM41").build());
        acces.add(new UniProtKBAccessionBuilder("B3KMN4").build());
        acces.add(new UniProtKBAccessionBuilder("D6RGD8").build());
        acces.add(new UniProtKBAccessionBuilder("O75932").build());

        acces.add(new UniProtKBAccessionBuilder("Q2PYN1").build());
        acces.add(new UniProtKBAccessionBuilder("Q53GV1").build());
        acces.add(new UniProtKBAccessionBuilder("Q68DQ9").build());
        acces.add(new UniProtKBAccessionBuilder("Q96PK5").build());
        acces.add(new UniProtKBAccessionBuilder("A0A000ACJ5").build());
        acces.add(new UniProtKBAccessionBuilder("Q96BV4").build());

        FFLine ffLine = builder.build(acces);
        String resultString = ffLine.toString();
        assertEquals(acLine, resultString);
    }
}

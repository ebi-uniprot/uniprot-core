package org.uniprot.core.flatfile.writer.line;

import org.junit.jupiter.api.Test;
import org.uniprot.core.flatfile.parser.impl.ac.ACLineBuilder;
import org.uniprot.core.flatfile.writer.FFLine;
import org.uniprot.core.uniprot.UniProtAccession;
import org.uniprot.core.uniprot.builder.UniProtAccessionBuilder;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ACLineBuildTest {
    ACLineBuilder builder = new ACLineBuilder();

    @Test
    public void test1() {
        String acLine = "AC   P99999; P00001; Q6NUR2; Q6NX69; Q96BV4;";
        List<UniProtAccession> acces = new ArrayList<>();
        acces.add(new UniProtAccessionBuilder("P99999").build());
        acces.add(new UniProtAccessionBuilder("P00001").build());
        acces.add(new UniProtAccessionBuilder("Q6NUR2").build());
        acces.add(new UniProtAccessionBuilder("Q6NX69").build());
        acces.add(new UniProtAccessionBuilder("Q96BV4").build());
        FFLine ffLine = builder.build(acces);
        String resultString = ffLine.toString();
        assertEquals(acLine, resultString);
    }

    @Test
    public void testNewAc() {
        String acLine = "AC   A0A000ACJ5; P00001; Q6NUR2; Q6NX69; Q96BV4;";
        List<UniProtAccession> acces = new ArrayList<>();
        acces.add(new UniProtAccessionBuilder("A0A000ACJ5").build());
        acces.add(new UniProtAccessionBuilder("P00001").build());
        acces.add(new UniProtAccessionBuilder("Q6NUR2").build());
        acces.add(new UniProtAccessionBuilder("Q6NX69").build());
        acces.add(new UniProtAccessionBuilder("Q96BV4").build());
        FFLine ffLine = builder.build(acces);
        String resultString = ffLine.toString();
        assertEquals(acLine, resultString);

    }

    @Test
    public void test2() {
        String acLine = "AC   Q96PK6; B0LM41; B3KMN4; D6RGD8; O75932; Q2PYN1; Q53GV1; Q68DQ9;\n" +
                "AC   Q96PK5; A0A000ACJ5; Q96BV4;";
        List<UniProtAccession> acces = new ArrayList<>();
        acces.add(new UniProtAccessionBuilder("Q96PK6").build());
        acces.add(new UniProtAccessionBuilder("B0LM41").build());
        acces.add(new UniProtAccessionBuilder("B3KMN4").build());
        acces.add(new UniProtAccessionBuilder("D6RGD8").build());
        acces.add(new UniProtAccessionBuilder("O75932").build());

        acces.add(new UniProtAccessionBuilder("Q2PYN1").build());
        acces.add(new UniProtAccessionBuilder("Q53GV1").build());
        acces.add(new UniProtAccessionBuilder("Q68DQ9").build());
        acces.add(new UniProtAccessionBuilder("Q96PK5").build());
        acces.add(new UniProtAccessionBuilder("A0A000ACJ5").build());
        acces.add(new UniProtAccessionBuilder("Q96BV4").build());


        FFLine ffLine = builder.build(acces);
        String resultString = ffLine.toString();
        assertEquals(acLine, resultString);

    }
}

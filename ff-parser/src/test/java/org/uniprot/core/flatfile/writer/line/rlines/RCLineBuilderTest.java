package org.uniprot.core.flatfile.writer.line.rlines;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.uniprot.core.flatfile.parser.impl.rc.RCLineBuilder;
import org.uniprot.core.uniprot.ReferenceComment;
import org.uniprot.core.uniprot.ReferenceCommentType;
import org.uniprot.core.uniprot.builder.ReferenceCommentBuilder;
import org.uniprot.core.uniprot.evidence.Evidence;
import org.uniprot.core.uniprot.evidence.impl.EvidenceHelper;

class RCLineBuilderTest {

    private final RCLineBuilder builder = new RCLineBuilder();

    @Test
    void testSingle() {
        List<ReferenceComment> sss = buildRc();
        List<String> lines = builder.buildLine(sss, true, true);
        assertEquals(5, lines.size());
        String line3 = "RC   {ECO:0000269|PubMed:10433554}, pSd13_G1271";
        assertEquals(line3, lines.get(2));
    }

    @Test
    void test2() {
        List<ReferenceComment> sss = buildRc();
        List<String> lines = builder.buildLine(sss, true, false);
        assertEquals(2, lines.size());
        String line1 = "RC   PLASMID=pSd11_G1246, pSd12_G1263, pSd13_G1271, pSd4_G1190, and";
        assertEquals(line1, lines.get(0));
    }

    @Test
    void test3() {
        List<ReferenceComment> sss = buildRc();
        List<String> lines = builder.buildLine(sss, false, false);
        assertEquals(1, lines.size());
        String line1 = "PLASMID=pSd11_G1246, pSd12_G1263, pSd13_G1271, pSd4_G1190, and pSd5_G1213;";
        assertEquals(line1, lines.get(0));
    }

    private List<ReferenceComment> buildRc() {
        String ev1 = "ECO:0000313|EMBL:BAG16761.1";
        String ev2 = "ECO:0000269|PubMed:10433554";
        String ev3 = "ECO:0000303|Ref.6";
        String ev4 = "ECO:0000313|PDB:3OW2";
        String ev5 = "ECO:0000256|HAMAP-Rule:MF_00205";
        List<ReferenceComment> sss = new ArrayList<>();
        String ss1 = "pSd11_G1246";
        List<String> evs1 = new ArrayList<>();
        evs1.add(ev1);
        evs1.add(ev2);
        sss.add(buildReferenceComment(ss1, ReferenceCommentType.PLASMID, evs1));

        String ss2 = "pSd12_G1263";
        List<String> evs2 = new ArrayList<>();
        evs2.add(ev2);
        sss.add(buildReferenceComment(ss2, ReferenceCommentType.PLASMID, evs2));

        String ss3 = "pSd13_G1271";
        List<String> evs3 = new ArrayList<>();
        evs3.add(ev4);
        evs3.add(ev5);
        sss.add(buildReferenceComment(ss3, ReferenceCommentType.PLASMID, evs3));

        String ss4 = "pSd4_G1190";
        List<String> evs4 = new ArrayList<>();
        evs4.add(ev3);
        sss.add(buildReferenceComment(ss4, ReferenceCommentType.PLASMID, evs4));

        String ss5 = "pSd5_G1213";
        List<String> evs5 = new ArrayList<>();
        evs5.add(ev5);
        sss.add(buildReferenceComment(ss5, ReferenceCommentType.PLASMID, evs5));
        return sss;
    }

    private ReferenceComment buildReferenceComment(
            String val, ReferenceCommentType type, List<String> evs) {
        return new ReferenceCommentBuilder()
                .type(type)
                .value(val)
                .evidences(createEvidence(evs))
                .build();
    }

    private List<Evidence> createEvidence(List<String> evIds) {
        return evIds.stream().map(EvidenceHelper::parseEvidenceLine).collect(Collectors.toList());
    }
}

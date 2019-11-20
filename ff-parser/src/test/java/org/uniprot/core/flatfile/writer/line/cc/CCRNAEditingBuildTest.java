package org.uniprot.core.flatfile.writer.line.cc;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprot.comment.Note;
import org.uniprot.core.uniprot.comment.RnaEdPosition;
import org.uniprot.core.uniprot.comment.RnaEditingComment;
import org.uniprot.core.uniprot.comment.RnaEditingLocationType;
import org.uniprot.core.uniprot.comment.builder.RnaEditingCommentBuilder;
import org.uniprot.core.uniprot.comment.builder.RnaEditingPositionBuilder;

class CCRNAEditingBuildTest extends CCBuildTestAbstr {
    @Test
    void testRNAEDITING() {
        String ccLine = ("CC   -!- RNA EDITING: Modified_positions=393, 431, 452, 495;");
        String ccLineString = ("RNA EDITING: Modified_positions=393, 431, 452, 495;");

        RnaEditingCommentBuilder builder = new RnaEditingCommentBuilder();
        builder.locationType(RnaEditingLocationType.Known);

        List<RnaEdPosition> positions = new ArrayList<>();
        String p = "393";
        List<String> pEvs = new ArrayList<>();
        RnaEdPosition position = buildPosition(p, pEvs);
        positions.add(position);

        p = "431";
        pEvs = new ArrayList<>();
        RnaEdPosition position2 = buildPosition(p, pEvs);
        positions.add(position2);

        p = "452";
        pEvs = new ArrayList<>();
        RnaEdPosition position3 = buildPosition(p, pEvs);
        positions.add(position3);

        p = "495";
        pEvs = new ArrayList<>();
        RnaEdPosition position4 = buildPosition(p, pEvs);
        positions.add(position4);
        builder.positions(positions);
        RnaEditingComment comment = builder.build();
        doTest(ccLine, comment);
        doTestString(ccLineString, comment);
    }

    @Test
    void testRNAEDITING3() {
        String ccLine =
                ("CC   -!- RNA EDITING: Modified_positions=156 {ECO:0000313|EMBL:BAG16761.1}, 158\n"
                        + "CC       {ECO:0000269|PubMed:10433554, ECO:0000303|Ref.6}, 160\n"
                        + "CC       {ECO:0000303|Ref.6}; Note=Partially edited. RNA editing generates\n"
                        + "CC       receptor isoforms that differ in their ability to interact with the\n"
                        + "CC       phospholipase C signaling cascade in a transfected cell line,\n"
                        + "CC       suggesting that this RNA processing event may contribute to the\n"
                        + "CC       modulation of serotonergic neurotransmission in the central nervous\n"
                        + "CC       system. {ECO:0000256|HAMAP-Rule:MF_00205, ECO:0000313|PDB:3OW2};");
        String ccLineString =
                ("RNA EDITING: Modified_positions=156, "
                        + "158, 160; Note=Partially edited. RNA editing generates "
                        + "receptor isoforms that differ in their ability to interact with "
                        + "the phospholipase C signaling cascade in a transfected cell line, "
                        + "suggesting that this RNA processing event may contribute to the "
                        + "modulation of serotonergic neurotransmission in the central "
                        + "nervous system.;");

        String ccLineStringEvidence =
                ("RNA EDITING: Modified_positions=156 {ECO:0000313|EMBL:BAG16761.1}, "
                        + "158 {ECO:0000269|PubMed:10433554, ECO:0000303|Ref.6}, 160 "
                        + "{ECO:0000303|Ref.6}; Note=Partially edited. RNA editing generates "
                        + "receptor isoforms that differ in their ability to interact with "
                        + "the phospholipase C signaling cascade in a transfected cell line, "
                        + "suggesting that this RNA processing event may contribute to the "
                        + "modulation of serotonergic neurotransmission in the central "
                        + "nervous system. {ECO:0000256|HAMAP-Rule:MF_00205, "
                        + "ECO:0000313|PDB:3OW2};");

        String ev1 = "ECO:0000313|EMBL:BAG16761.1";
        String ev2 = "ECO:0000269|PubMed:10433554";
        String ev3 = "ECO:0000303|Ref.6";
        String ev4 = "ECO:0000313|PDB:3OW2";
        String ev5 = "ECO:0000256|HAMAP-Rule:MF_00205";

        String note =
                "Partially edited. RNA editing generates receptor isoforms "
                        + "that differ in their ability to interact with the phospholipase C "
                        + "signaling cascade in a transfected cell line, suggesting that this "
                        + "RNA processing event may contribute to the modulation of "
                        + "serotonergic neurotransmission in the central nervous system";

        List<String> noteEvs = new ArrayList<>();
        noteEvs.add(ev4);
        noteEvs.add(ev5);
        RnaEditingCommentBuilder builder = new RnaEditingCommentBuilder();
        builder.locationType(RnaEditingLocationType.Known);
        Note cNote = buildNote(note, noteEvs);
        builder.note(cNote);
        List<RnaEdPosition> positions = new ArrayList<>();
        String p = "156";
        List<String> pEvs = new ArrayList<>();
        pEvs.add(ev1);
        RnaEdPosition position = buildPosition(p, pEvs);
        positions.add(position);

        p = "158";
        pEvs = new ArrayList<>();
        pEvs.add(ev2);
        pEvs.add(ev3);
        RnaEdPosition position2 = buildPosition(p, pEvs);
        positions.add(position2);

        p = "160";
        pEvs = new ArrayList<>();
        pEvs.add(ev3);
        RnaEdPosition position3 = buildPosition(p, pEvs);
        positions.add(position3);

        builder.positions(positions);
        RnaEditingComment comment = builder.build();
        doTest(ccLine, comment);
        doTestString(ccLineString, comment);
        doTestStringEv(ccLineStringEvidence, comment);
    }

    @Test
    void testRNAEDITING4() {
        String ccLine =
                ("CC   -!- RNA EDITING: Modified_positions=156 {ECO:0000313|EMBL:BAG16761.1}, 158\n"
                        + "CC       {ECO:0000269|PubMed:10433554, ECO:0000303|Ref.6}, 160\n"
                        + "CC       {ECO:0000303|Ref.6}; Note=Partial edited. {ECO:0000313|PDB:3OW2}. RNA\n"
                        + "CC       editing generates receptor isoforms that differ in the phospholipase C\n"
                        + "CC       signaling cascade in a transfected cell line, suggesting that this RNA\n"
                        + "CC       processing event may contribute to the modulation of serotonergic\n"
                        + "CC       neurotransmission in the central nervous system. {ECO:0000256|HAMAP-\n"
                        + "CC       Rule:MF_00205, ECO:0000313|PDB:3OW2};");
        String ccLineString =
                ("RNA EDITING: Modified_positions=156, "
                        + "158, 160; Note=Partial edited.. RNA editing generates "
                        + "receptor isoforms that differ in "
                        + "the phospholipase C signaling cascade in a transfected cell line, "
                        + "suggesting that this RNA processing event may contribute to the "
                        + "modulation of serotonergic neurotransmission in the central "
                        + "nervous system.;");

        String ccLineStringEvidence =
                ("RNA EDITING: Modified_positions=156 {ECO:0000313|EMBL:BAG16761.1}, "
                        + "158 {ECO:0000269|PubMed:10433554, ECO:0000303|Ref.6}, 160 "
                        + "{ECO:0000303|Ref.6}; Note=Partial edited. {ECO:0000313|PDB:3OW2}. RNA editing generates "
                        + "receptor isoforms that differ in "
                        + "the phospholipase C signaling cascade in a transfected cell line, "
                        + "suggesting that this RNA processing event may contribute to the "
                        + "modulation of serotonergic neurotransmission in the central "
                        + "nervous system. {ECO:0000256|HAMAP-Rule:MF_00205, "
                        + "ECO:0000313|PDB:3OW2};");

        String ev1 = "ECO:0000313|EMBL:BAG16761.1";
        String ev2 = "ECO:0000269|PubMed:10433554";
        String ev3 = "ECO:0000303|Ref.6";
        String ev4 = "ECO:0000313|PDB:3OW2";
        String ev5 = "ECO:0000256|HAMAP-Rule:MF_00205";

        String note = "Partial edited";
        List<String> noteEvs = new ArrayList<>();
        noteEvs.add(ev4);

        String note2 =
                "RNA editing generates receptor isoforms "
                        + "that differ in the phospholipase C "
                        + "signaling cascade in a transfected cell line, suggesting that this "
                        + "RNA processing event may contribute to the modulation of "
                        + "serotonergic neurotransmission in the central nervous system";
        List<String> noteEvs2 = new ArrayList<>();
        noteEvs2.add(ev4);
        noteEvs2.add(ev5);
        RnaEditingCommentBuilder builder = new RnaEditingCommentBuilder();
        builder.locationType(RnaEditingLocationType.Known);
        List<Map.Entry<String, List<String>>> notes = new ArrayList<>();
        notes.add(new AbstractMap.SimpleEntry<>(note, noteEvs));

        notes.add(new AbstractMap.SimpleEntry<>(note2, noteEvs2));

        Note commentNote = buildNote(notes);

        builder.note(commentNote);

        List<RnaEdPosition> positions = new ArrayList<>();
        String p = "156";
        List<String> pEvs = new ArrayList<>();
        pEvs.add(ev1);
        RnaEdPosition position = buildPosition(p, pEvs);
        positions.add(position);

        p = "158";
        pEvs = new ArrayList<>();
        pEvs.add(ev2);
        pEvs.add(ev3);
        RnaEdPosition position2 = buildPosition(p, pEvs);
        positions.add(position2);

        p = "160";
        pEvs = new ArrayList<>();
        pEvs.add(ev3);
        RnaEdPosition position3 = buildPosition(p, pEvs);
        positions.add(position3);

        builder.positions(positions);
        RnaEditingComment comment = builder.build();
        doTest(ccLine, comment);
        doTestString(ccLineString, comment);
        doTestStringEv(ccLineStringEvidence, comment);
    }

    private RnaEdPosition buildPosition(String p, List<String> evs) {
        return new RnaEditingPositionBuilder(p, createEvidence(evs)).build();
    }
}

package uk.ac.ebi.uniprot.parser.ffwriter.line.cc;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import uk.ac.ebi.uniprot.domain.uniprot.comment.Note;
import uk.ac.ebi.uniprot.domain.uniprot.comment.Position;
import uk.ac.ebi.uniprot.domain.uniprot.comment.RnaEditingComment;
import uk.ac.ebi.uniprot.domain.uniprot.comment.RnaEditingLocationType;
import uk.ac.ebi.uniprot.domain.uniprot.comment.builder.RnaEditingCommentBuilder;

public class CCRNAEditingBuildTest extends CCBuildTestAbstr {
    @Test
    public void testRNAEDITING() {
        String ccLine = ("CC   -!- RNA EDITING: Modified_positions=393, 431, 452, 495;");
        String ccLineString = ("RNA EDITING: Modified_positions=393, 431, 452, 495;");


        RnaEditingCommentBuilder builder = RnaEditingCommentBuilder.newInstance();
        builder.rnaEditingLocationType(RnaEditingLocationType.Known);
      
        List<Position> positions = new ArrayList<>();
        String p = "393";
        List<String> pEvs = new ArrayList<>();
        Position position = buildPosition(p, pEvs);
        positions.add(position);

        p = "431";
        pEvs = new ArrayList<>();
        Position position2 = buildPosition(p, pEvs);
        positions.add(position2);

        p = "452";
        pEvs = new ArrayList<>();
        Position position3 = buildPosition(p, pEvs);
        positions.add(position3);

        p = "495";
        pEvs = new ArrayList<>();
        Position position4 = buildPosition(p, pEvs);
        positions.add(position4);
        builder.locations(positions);
        RnaEditingComment comment = builder.build();
        doTest(ccLine, comment);
        doTestString(ccLineString, comment);
    }

    @Test
    public void testRNAEDITING3() {
        String ccLine = ("CC   -!- RNA EDITING: Modified_positions=156 {ECO:0000313|EMBL:BAG16761.1},\n" +
                "CC       158 {ECO:0000269|PubMed:10433554, ECO:0000303|Ref.6}, 160\n" +
                "CC       {ECO:0000303|Ref.6}; Note=Partially edited. RNA editing generates\n" +
                "CC       receptor isoforms that differ in their ability to interact with\n" +
                "CC       the phospholipase C signaling cascade in a transfected cell line,\n" +
                "CC       suggesting that this RNA processing event may contribute to the\n" +
                "CC       modulation of serotonergic neurotransmission in the central\n" +
                "CC       nervous system. {ECO:0000256|HAMAP-Rule:MF_00205,\n" +
                "CC       ECO:0000313|PDB:3OW2};");
        String ccLineString = ("RNA EDITING: Modified_positions=156, " +
                "158, 160; Note=Partially edited. RNA editing generates " +
                "receptor isoforms that differ in their ability to interact with " +
                "the phospholipase C signaling cascade in a transfected cell line, " +
                "suggesting that this RNA processing event may contribute to the " +
                "modulation of serotonergic neurotransmission in the central " +
                "nervous system.;");

        String ccLineStringEvidence = ("RNA EDITING: Modified_positions=156 {ECO:0000313|EMBL:BAG16761.1}, " +
                "158 {ECO:0000269|PubMed:10433554, ECO:0000303|Ref.6}, 160 " +
                "{ECO:0000303|Ref.6}; Note=Partially edited. RNA editing generates " +
                "receptor isoforms that differ in their ability to interact with " +
                "the phospholipase C signaling cascade in a transfected cell line, " +
                "suggesting that this RNA processing event may contribute to the " +
                "modulation of serotonergic neurotransmission in the central " +
                "nervous system. {ECO:0000256|HAMAP-Rule:MF_00205, " +
                "ECO:0000313|PDB:3OW2};");

        String ev1 = "ECO:0000313|EMBL:BAG16761.1";
        String ev2 = "ECO:0000269|PubMed:10433554";
        String ev3 = "ECO:0000303|Ref.6";
        String ev4 = "ECO:0000313|PDB:3OW2";
        String ev5 = "ECO:0000256|HAMAP-Rule:MF_00205";

        String note = "Partially edited. RNA editing generates receptor isoforms "
                + "that differ in their ability to interact with the phospholipase C "
                + "signaling cascade in a transfected cell line, suggesting that this "
                + "RNA processing event may contribute to the modulation of "
                + "serotonergic neurotransmission in the central nervous system";

        List<String> noteEvs = new ArrayList<>();
        noteEvs.add(ev4);
        noteEvs.add(ev5);
        RnaEditingCommentBuilder builder = RnaEditingCommentBuilder.newInstance();
        builder.rnaEditingLocationType(RnaEditingLocationType.Known);
        Note cNote = buildNote(note, noteEvs);
        builder.note(cNote);
        List<Position> positions = new ArrayList<>();
        String p = "156";
        List<String> pEvs = new ArrayList<>();
        pEvs.add(ev1);
        Position position = buildPosition(p, pEvs);
        positions.add(position);

        p = "158";
        pEvs = new ArrayList<>();
        pEvs.add(ev2);
        pEvs.add(ev3);
        Position position2 = buildPosition(p, pEvs);
        positions.add(position2);

        p = "160";
        pEvs = new ArrayList<>();
        pEvs.add(ev3);
        Position position3 = buildPosition(p, pEvs);
        positions.add(position3);

        builder.locations(positions);
        RnaEditingComment comment = builder.build();
        doTest(ccLine, comment);
        doTestString(ccLineString, comment);
        doTestStringEv(ccLineStringEvidence, comment);
    }

    @Test
    public void testRNAEDITING4() {
        String ccLine = ("CC   -!- RNA EDITING: Modified_positions=156 {ECO:0000313|EMBL:BAG16761.1},\n" +
                "CC       158 {ECO:0000269|PubMed:10433554, ECO:0000303|Ref.6}, 160\n" +
                "CC       {ECO:0000303|Ref.6}; Note=Partial edited. {ECO:0000313|PDB:3OW2}.\n" +
                "CC       RNA editing generates receptor isoforms that differ in the\n" +
                "CC       phospholipase C signaling cascade in a transfected cell line,\n" +
                "CC       suggesting that this RNA processing event may contribute to the\n" +
                "CC       modulation of serotonergic neurotransmission in the central\n" +
                "CC       nervous system. {ECO:0000256|HAMAP-Rule:MF_00205,\n" +
                "CC       ECO:0000313|PDB:3OW2};");
        String ccLineString = ("RNA EDITING: Modified_positions=156, " +
                "158, 160; Note=Partial edited.. RNA editing generates " +
                "receptor isoforms that differ in " +
                "the phospholipase C signaling cascade in a transfected cell line, " +
                "suggesting that this RNA processing event may contribute to the " +
                "modulation of serotonergic neurotransmission in the central " +
                "nervous system.;");

        String ccLineStringEvidence = ("RNA EDITING: Modified_positions=156 {ECO:0000313|EMBL:BAG16761.1}, " +
                "158 {ECO:0000269|PubMed:10433554, ECO:0000303|Ref.6}, 160 " +
                "{ECO:0000303|Ref.6}; Note=Partial edited. {ECO:0000313|PDB:3OW2}. RNA editing generates " +
                "receptor isoforms that differ in " +
                "the phospholipase C signaling cascade in a transfected cell line, " +
                "suggesting that this RNA processing event may contribute to the " +
                "modulation of serotonergic neurotransmission in the central " +
                "nervous system. {ECO:0000256|HAMAP-Rule:MF_00205, " +
                "ECO:0000313|PDB:3OW2};");

        String ev1 = "ECO:0000313|EMBL:BAG16761.1";
        String ev2 = "ECO:0000269|PubMed:10433554";
        String ev3 = "ECO:0000303|Ref.6";
        String ev4 = "ECO:0000313|PDB:3OW2";
        String ev5 = "ECO:0000256|HAMAP-Rule:MF_00205";

        String note = "Partial edited";
        List<String> noteEvs = new ArrayList<>();
        noteEvs.add(ev4);

        String note2 = "RNA editing generates receptor isoforms "
                + "that differ in the phospholipase C "
                + "signaling cascade in a transfected cell line, suggesting that this "
                + "RNA processing event may contribute to the modulation of "
                + "serotonergic neurotransmission in the central nervous system";
        List<String> noteEvs2 = new ArrayList<>();
        noteEvs2.add(ev4);
        noteEvs2.add(ev5);
        RnaEditingCommentBuilder builder = RnaEditingCommentBuilder.newInstance();
        builder.rnaEditingLocationType(RnaEditingLocationType.Known);
        List<Map.Entry<String, List<String>>> notes = new ArrayList<>();
		notes.add(new AbstractMap.SimpleEntry<>(note, noteEvs));
		
		notes.add(new AbstractMap.SimpleEntry<>(note2, noteEvs2));
		
		Note commentNote =buildNote(notes);
        
		builder.note(commentNote);

        List<Position> positions = new ArrayList<>();
        String p = "156";
        List<String> pEvs = new ArrayList<>();
        pEvs.add(ev1);
        Position position = buildPosition(p, pEvs);
        positions.add(position);

        p = "158";
        pEvs = new ArrayList<>();
        pEvs.add(ev2);
        pEvs.add(ev3);
        Position position2 = buildPosition(p, pEvs);
        positions.add(position2);

        p = "160";
        pEvs = new ArrayList<>();
        pEvs.add(ev3);
        Position position3 = buildPosition(p, pEvs);
        positions.add(position3);

        builder.locations(positions);
        RnaEditingComment comment = builder.build();
        doTest(ccLine, comment);
        doTestString(ccLineString, comment);
        doTestStringEv(ccLineStringEvidence, comment);
    }

    Position buildPosition(String p, List<String> evs) {
    		return RnaEditingCommentBuilder.createPosition(p, createEvidence(evs));

    }
}

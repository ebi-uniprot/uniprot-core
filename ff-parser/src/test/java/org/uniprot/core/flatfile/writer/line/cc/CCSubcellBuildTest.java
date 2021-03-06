package org.uniprot.core.flatfile.writer.line.cc;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprotkb.comment.Note;
import org.uniprot.core.uniprotkb.comment.SubcellularLocation;
import org.uniprot.core.uniprotkb.comment.SubcellularLocationComment;
import org.uniprot.core.uniprotkb.comment.SubcellularLocationValue;
import org.uniprot.core.uniprotkb.comment.impl.SubcellularLocationBuilder;
import org.uniprot.core.uniprotkb.comment.impl.SubcellularLocationCommentBuilder;
import org.uniprot.core.uniprotkb.comment.impl.SubcellularLocationValueBuilder;

import com.google.common.base.Strings;

class CCSubcellBuildTest extends CCBuildTestAbstr {
    @Test
    void test1() {
        String ccLine =
                "CC   -!- SUBCELLULAR LOCATION: Mitochondrion intermembrane space. Note=Loosely\n"
                        + "CC       associated with the inner membrane.";

        String ccLineString =
                "SUBCELLULAR LOCATION: Mitochondrion intermembrane space. "
                        + "Note=Loosely associated with the inner membrane.";
        String molecule = "";
        String note = "Loosely associated with the inner membrane";
        List<String> evs = new ArrayList<>();
        SubcellularLocationCommentBuilder builder = new SubcellularLocationCommentBuilder();
        builder.note(buildNote(note, evs));

        List<SubcellularLocation> subcellularLocations = new ArrayList<>();

        String location = "Mitochondrion intermembrane space";

        List<String> locationEvs = new ArrayList<>();

        String orient = "";

        List<String> orientEvs = new ArrayList<>();
        String top = "";
        List<String> topEvs = new ArrayList<>();

        SubcellularLocation subLocation =
                buildSubLocation(
                        location, locationEvs,
                        orient, orientEvs,
                        top, topEvs);
        subcellularLocations.add(subLocation);
        builder.subcellularLocationsSet(subcellularLocations);
        SubcellularLocationComment comment = builder.build();
        doTest(ccLine, comment);
        doTestString(ccLineString, comment);
    }

    @Test
    void test1Evidences() {
        String ccLine =
                "CC   -!- SUBCELLULAR LOCATION: Mitochondrion intermembrane space\n"
                        + "CC       {ECO:0000269|PubMed:10433554, ECO:0000313|EMBL:BAG16761.1}.\n"
                        + "CC       Note=Loosely associated with the inner membrane."
                        + " {ECO:0000303|Ref.6,\n"
                        + "CC       ECO:0000313|PDB:3OW2}.";

        String ccLineStringEvidence =
                "SUBCELLULAR LOCATION: Mitochondrion intermembrane space "
                        + "{ECO:0000269|PubMed:10433554, ECO:0000313|EMBL:BAG16761.1}. "
                        + "Note=Loosely associated with the inner membrane. "
                        + "{ECO:0000303|Ref.6, ECO:0000313|PDB:3OW2}.";
        String ccLineString =
                "SUBCELLULAR LOCATION: Mitochondrion intermembrane space. "
                        + "Note=Loosely associated with the inner membrane.";

        String ev1 = "ECO:0000313|EMBL:BAG16761.1";
        String ev2 = "ECO:0000269|PubMed:10433554";
        String ev3 = "ECO:0000303|Ref.6";
        String ev4 = "ECO:0000313|PDB:3OW2";
        // String ev5 ="ECO:0000256|HAMAP-Rule:MF_00205";
        String molecule = "";
        String note = "Loosely associated with the inner membrane";

        List<String> evs = new ArrayList<>();
        evs.add(ev3);
        evs.add(ev4);

        SubcellularLocationCommentBuilder builder = new SubcellularLocationCommentBuilder();
        builder.note(buildNote(note, evs));

        List<SubcellularLocation> subcellularLocations = new ArrayList<>();

        String location = "Mitochondrion intermembrane space";
        List<String> locationEvs = new ArrayList<>();
        locationEvs.add(ev1);
        locationEvs.add(ev2);
        String orient = "";

        List<String> orientEvs = new ArrayList<>();
        String top = "";
        List<String> topEvs = new ArrayList<>();

        SubcellularLocation subLocation =
                buildSubLocation(
                        location, locationEvs,
                        orient, orientEvs,
                        top, topEvs);
        subcellularLocations.add(subLocation);
        builder.subcellularLocationsSet(subcellularLocations);
        SubcellularLocationComment comment = builder.build();
        doTest(ccLine, comment);
        doTestString(ccLineString, comment);
        doTestStringEv(ccLineStringEvidence, comment);
    }

    @Test
    void test2() {
        String ccLine =
                "CC   -!- SUBCELLULAR LOCATION: Mitochondrion inner membrane; Multi-pass membrane\n"
                        + "CC       protein (By similarity).";
        String ccLineString =
                "SUBCELLULAR LOCATION: Mitochondrion inner membrane; Multi-pass "
                        + "membrane protein (By similarity).";

        SubcellularLocationCommentBuilder builder = new SubcellularLocationCommentBuilder();
        //   builder.note(buildNote(note, evs));
        List<SubcellularLocation> subcellularLocations = new ArrayList<>();

        String location = "Mitochondrion inner membrane";
        List<String> locationEvs = new ArrayList<>();

        String orient = "Multi-pass membrane protein (By similarity)";

        List<String> orientEvs = new ArrayList<>();
        String top = "";
        List<String> topEvs = new ArrayList<>();

        SubcellularLocation subLocation =
                buildSubLocation(
                        location, locationEvs,
                        orient, orientEvs,
                        top, topEvs);
        subcellularLocations.add(subLocation);
        builder.subcellularLocationsSet(subcellularLocations);
        SubcellularLocationComment comment = builder.build();
        doTest(ccLine, comment);
        doTestString(ccLineString, comment);
    }

    @Test
    void test2Evidence() {
        String ccLine =
                "CC   -!- SUBCELLULAR LOCATION: Mitochondrion inner membrane\n"
                        + "CC       {ECO:0000269|PubMed:10433554, ECO:0000313|EMBL:BAG16761.1};"
                        + " Multi-pass\n"
                        + "CC       membrane protein (By similarity) {ECO:0000303|Ref.6}.";
        String ccLineStringEvidence =
                "SUBCELLULAR LOCATION: Mitochondrion inner membrane "
                        + "{ECO:0000269|PubMed:10433554, ECO:0000313|EMBL:BAG16761.1}; Multi-"
                        + "pass membrane protein (By similarity) {ECO:0000303|Ref.6}.";
        String ccLineString =
                "SUBCELLULAR LOCATION: Mitochondrion inner membrane; "
                        + "Multi-"
                        + "pass membrane protein (By similarity).";

        String ev1 = "ECO:0000313|EMBL:BAG16761.1";
        String ev2 = "ECO:0000269|PubMed:10433554";
        String ev3 = "ECO:0000303|Ref.6";
        // String ev4 ="ECO:0000313|PDB:3OW2";
        // String ev5 ="ECO:0000256|HAMAP-Rule:MF_00205";

        SubcellularLocationCommentBuilder builder = new SubcellularLocationCommentBuilder();

        List<SubcellularLocation> subcellularLocations = new ArrayList<>();

        String location = "Mitochondrion inner membrane";

        List<String> locationEvs = new ArrayList<>();
        locationEvs.add(ev1);
        locationEvs.add(ev2);

        String orient = "Multi-pass membrane protein (By similarity)";

        List<String> orientEvs = new ArrayList<>();
        orientEvs.add(ev3);
        String top = "";

        List<String> topEvs = new ArrayList<>();

        SubcellularLocation subLocation =
                buildSubLocation(
                        location, locationEvs,
                        orient, orientEvs,
                        top, topEvs);
        subcellularLocations.add(subLocation);
        builder.subcellularLocationsSet(subcellularLocations);
        SubcellularLocationComment comment = builder.build();
        doTest(ccLine, comment);
        doTestString(ccLineString, comment);
        doTestStringEv(ccLineStringEvidence, comment);
    }

    @Test
    void test2Evidence2() {
        String ccLine =
                "CC   -!- SUBCELLULAR LOCATION: Mitochondrion inner membrane; Multi-pass membrane\n"
                        + "CC       protein (By similarity) {ECO:0000269|PubMed:10433554,\n"
                        + "CC       ECO:0000303|Ref.6, ECO:0000313|EMBL:BAG16761.1}.";
        String ev1 = "ECO:0000313|EMBL:BAG16761.1";
        String ev2 = "ECO:0000269|PubMed:10433554";
        String ev3 = "ECO:0000303|Ref.6";
        SubcellularLocationCommentBuilder builder = new SubcellularLocationCommentBuilder();
        //   builder.note(buildNote(note, evs));

        List<SubcellularLocation> subcellularLocations = new ArrayList<>();

        String location = "Mitochondrion inner membrane";

        List<String> locationEvs = new ArrayList<>();

        String orient = "Multi-pass membrane protein (By similarity)";
        List<String> orientEvs = new ArrayList<>();
        orientEvs.add(ev1);
        orientEvs.add(ev2);
        orientEvs.add(ev3);
        String top = "";
        List<String> topEvs = new ArrayList<>();

        SubcellularLocation subLocation =
                buildSubLocation(
                        location, locationEvs,
                        orient, orientEvs,
                        top, topEvs);
        subcellularLocations.add(subLocation);
        builder.subcellularLocationsSet(subcellularLocations);
        SubcellularLocationComment comment = builder.build();
        doTest(ccLine, comment);
    }

    @Test
    void test3() {
        String ccLine =
                ("CC   -!- SUBCELLULAR LOCATION: [Spike protein S2]: Virion membrane;"
                        + " Single-pass\n"
                        + "CC       type I membrane sdssds protein (By similarity). Host"
                        + " endoplasmic\n"
                        + "CC       reticulum-Golgi intermediate compartment membrane; Type I me"
                        + " (By\n"
                        + "CC       similarity); Another top. Note=Accumulates in the endoplasmic\n"
                        + "CC       reticulum-Golgi intermediate compartment, where it participates"
                        + " in\n"
                        + "CC       virus particle assembly. Some S oligomers may be transported to"
                        + " the\n"
                        + "CC       plasma membrane, where they may mediate cell fusion (By"
                        + " similarity).");

        String molecule = "Spike protein S2";
        String note =
                "Accumulates in the endoplasmic reticulum-Golgi intermediate compartment, where it"
                        + " participates in virus particle assembly. Some S oligomers may be"
                        + " transported to the plasma membrane, where they may mediate cell fusion (By"
                        + " similarity)";

        List<String> evs = new ArrayList<>();

        SubcellularLocationCommentBuilder builder = new SubcellularLocationCommentBuilder();
        builder.note(buildNote(note, evs));
        builder.molecule(molecule);
        List<SubcellularLocation> subcellularLocations = new ArrayList<>();

        String location = "Virion membrane";

        List<String> locationEvs = new ArrayList<>();

        String orient = "Single-pass type I membrane sdssds protein (By similarity)";
        List<String> orientEvs = new ArrayList<>();
        String top = "";
        List<String> topEvs = new ArrayList<>();

        SubcellularLocation subLocation =
                buildSubLocation(
                        location, locationEvs,
                        orient, orientEvs,
                        top, topEvs);
        subcellularLocations.add(subLocation);

        String location2 = "Host endoplasmic reticulum-Golgi intermediate compartment membrane";
        List<String> locationEvs2 = new ArrayList<>();

        String orient2 = "Another top";
        List<String> orientEvs2 = new ArrayList<>();

        String top2 = "Type I me (By similarity)";
        List<String> topEvs2 = new ArrayList<>();

        SubcellularLocation subLocation2 =
                buildSubLocation(
                        location2, locationEvs2,
                        orient2, orientEvs2,
                        top2, topEvs2);
        subcellularLocations.add(subLocation2);

        builder.subcellularLocationsSet(subcellularLocations);
        SubcellularLocationComment comment = builder.build();
        doTest(ccLine, comment);
    }

    @Test
    void test3Evidence() {
        String ccLine =
                "CC   -!- SUBCELLULAR LOCATION: [Spike protein S2]: Virion membrane\n"
                        + "CC       {ECO:0000313|EMBL:BAG16761.1}; Single-pass type I membrane"
                        + " sdssds\n"
                        + "CC       protein (By similarity) {ECO:0000269|PubMed:10433554}. Host"
                        + " endoplasmic\n"
                        + "CC       reticulum-Golgi intermediate compartment membrane"
                        + " {ECO:0000303|Ref.6};\n"
                        + "CC       Type I me (By similarity) {ECO:0000313|PDB:3OW2}; Another top\n"
                        + "CC       {ECO:0000313|EMBL:BAG16761.1}. Note=Accumulates in the"
                        + " endoplasmic\n"
                        + "CC       reticulum-Golgi intermediate compartment, where it participates"
                        + " in\n"
                        + "CC       virus particle assembly. Some S oligomers may be transported to"
                        + " the\n"
                        + "CC       plasma membrane, where they may mediate cell fusion (By"
                        + " similarity).\n"
                        + "CC       {ECO:0000256|HAMAP-Rule:MF_00205}.";

        String ccLineStringEvidence =
                "SUBCELLULAR LOCATION: [Spike protein S2]: Virion membrane "
                        + "{ECO:0000313|EMBL:BAG16761.1}; Single-pass type I membrane sdssds "
                        + "protein (By similarity) {ECO:0000269|PubMed:10433554}. Host "
                        + "endoplasmic reticulum-Golgi intermediate compartment membrane "
                        + "{ECO:0000303|Ref.6}; Type I me (By similarity) "
                        + "{ECO:0000313|PDB:3OW2}; Another top {ECO:0000313|EMBL:BAG16761.1}. "
                        + "Note=Accumulates in the endoplasmic reticulum-Golgi intermediate "
                        + "compartment, where it participates in virus particle assembly. "
                        + "Some S oligomers may be transported to the plasma membrane, where "
                        + "they may mediate cell fusion (By similarity). {ECO:0000256|HAMAP-"
                        + "Rule:MF_00205}.";

        String ccLineString =
                "SUBCELLULAR LOCATION: [Spike protein S2]: Virion membrane; "
                        + "Single-pass type I membrane sdssds "
                        + "protein (By similarity). Host "
                        + "endoplasmic reticulum-Golgi intermediate compartment membrane; "
                        + "Type I me (By similarity); "
                        + "Another top. "
                        + "Note=Accumulates in the endoplasmic reticulum-Golgi intermediate "
                        + "compartment, where it participates in virus particle assembly. "
                        + "Some S oligomers may be transported to the plasma membrane, where "
                        + "they may mediate cell fusion (By similarity).";

        String ev1 = "ECO:0000313|EMBL:BAG16761.1";
        String ev2 = "ECO:0000269|PubMed:10433554";
        String ev3 = "ECO:0000303|Ref.6";
        String ev4 = "ECO:0000313|PDB:3OW2";
        String ev5 = "ECO:0000256|HAMAP-Rule:MF_00205";

        String molecule = "Spike protein S2";
        String note =
                "Accumulates in the endoplasmic reticulum-Golgi intermediate compartment, where it"
                        + " participates in virus particle assembly. Some S oligomers may be"
                        + " transported to the plasma membrane, where they may mediate cell fusion (By"
                        + " similarity)";

        List<String> evs = new ArrayList<>();
        evs.add(ev5);

        SubcellularLocationCommentBuilder builder = new SubcellularLocationCommentBuilder();
        builder.note(buildNote(note, evs));
        builder.molecule(molecule);

        List<SubcellularLocation> subcellularLocations = new ArrayList<>();

        String location = "Virion membrane";

        List<String> locationEvs = new ArrayList<>();
        locationEvs.add(ev1);

        String orient = "Single-pass type I membrane sdssds protein (By similarity)";
        List<String> orientEvs = new ArrayList<>();
        orientEvs.add(ev2);
        String top = "";
        List<String> topEvs = new ArrayList<>();

        SubcellularLocation subLocation =
                buildSubLocation(
                        location, locationEvs,
                        orient, orientEvs,
                        top, topEvs);
        subcellularLocations.add(subLocation);

        String location2 = "Host endoplasmic reticulum-Golgi intermediate compartment membrane";
        List<String> locationEvs2 = new ArrayList<>();
        locationEvs2.add(ev3);

        String orient2 = "Another top";
        List<String> orientEvs2 = new ArrayList<>();
        orientEvs2.add(ev1);

        String top2 = "Type I me (By similarity)";
        List<String> topEvs2 = new ArrayList<>();
        topEvs2.add(ev4);

        SubcellularLocation subLocation2 =
                buildSubLocation(
                        location2, locationEvs2,
                        orient2, orientEvs2,
                        top2, topEvs2);
        subcellularLocations.add(subLocation2);

        builder.subcellularLocationsSet(subcellularLocations);
        SubcellularLocationComment comment = builder.build();
        doTest(ccLine, comment);
        doTestString(ccLineString, comment);
        doTestStringEv(ccLineStringEvidence, comment);
    }

    @Test
    void test1Evidences3() {
        String ccLine =
                "CC   -!- SUBCELLULAR LOCATION: Mitochondrion intermembrane space\n"
                        + "CC       {ECO:0000269|PubMed:10433554, ECO:0000313|EMBL:BAG16761.1}.\n"
                        + "CC       Note=Loosely associated with the inner membrane."
                        + " {ECO:0000303|Ref.6,\n"
                        + "CC       ECO:0000313|PDB:3OW2}. Another note. {ECO:0000303|Ref.6}.";

        String ccLineStringEvidence =
                "SUBCELLULAR LOCATION: Mitochondrion intermembrane space"
                        + " {ECO:0000269|PubMed:10433554, ECO:0000313|EMBL:BAG16761.1}. Note=Loosely"
                        + " associated with the inner membrane. {ECO:0000303|Ref.6,"
                        + " ECO:0000313|PDB:3OW2}. Another note. {ECO:0000303|Ref.6}.";
        String ccLineString =
                "SUBCELLULAR LOCATION: Mitochondrion intermembrane space. "
                        + "Note=Loosely associated with the inner membrane. Another note.";

        String ev1 = "ECO:0000313|EMBL:BAG16761.1";
        String ev2 = "ECO:0000269|PubMed:10433554";
        String ev3 = "ECO:0000303|Ref.6";
        String ev4 = "ECO:0000313|PDB:3OW2";
        // String ev5 ="ECO:0000256|HAMAP-Rule:MF_00205";
        String molecule = "";
        String note = "Loosely associated with the inner membrane";

        List<String> evs = new ArrayList<>();
        evs.add(ev3);
        evs.add(ev4);

        String note2 = "Another note";

        List<String> evs2 = new ArrayList<>();
        evs2.add(ev3);

        List<Map.Entry<String, List<String>>> notes = new ArrayList<>();
        notes.add(new AbstractMap.SimpleEntry<>(note, evs));

        notes.add(new AbstractMap.SimpleEntry<>(note2, evs2));

        Note commentNote = buildNote(notes);

        SubcellularLocationCommentBuilder builder = new SubcellularLocationCommentBuilder();
        builder.note(commentNote);
        builder.molecule(molecule);

        List<SubcellularLocation> subcellularLocations = new ArrayList<>();

        String location = "Mitochondrion intermembrane space";
        List<String> locationEvs = new ArrayList<>();
        locationEvs.add(ev1);
        locationEvs.add(ev2);
        String orient = "";
        List<String> orientEvs = new ArrayList<>();
        String top = "";
        List<String> topEvs = new ArrayList<>();

        SubcellularLocation subLocation =
                buildSubLocation(
                        location, locationEvs,
                        orient, orientEvs,
                        top, topEvs);
        subcellularLocations.add(subLocation);
        builder.subcellularLocationsSet(subcellularLocations);
        SubcellularLocationComment comment = builder.build();
        doTest(ccLine, comment);
        doTestString(ccLineString, comment);
        doTestStringEv(ccLineStringEvidence, comment);
    }

    SubcellularLocation buildSubLocation(
            String location,
            List<String> locationEvs,
            String orient,
            List<String> orientEvs,
            String top,
            List<String> topEvs) {
        return new SubcellularLocationBuilder()
                .location(buildLocationValue(location, locationEvs))
                .topology(buildLocationValue(top, topEvs))
                .orientation(buildLocationValue(orient, orientEvs))
                .build();
    }

    SubcellularLocationValue buildLocationValue(String value, List<String> evs) {
        if (Strings.isNullOrEmpty(value)) return null;
        return new SubcellularLocationValueBuilder()
                .id("")
                .value(value)
                .evidencesSet(createEvidence(evs))
                .build();
    }
}

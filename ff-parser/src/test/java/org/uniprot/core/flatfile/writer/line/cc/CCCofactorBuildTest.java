package org.uniprot.core.flatfile.writer.line.cc;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.uniprot.core.CrossReference;
import org.uniprot.core.impl.CrossReferenceBuilder;
import org.uniprot.core.uniprotkb.comment.Cofactor;
import org.uniprot.core.uniprotkb.comment.CofactorComment;
import org.uniprot.core.uniprotkb.comment.CofactorDatabase;
import org.uniprot.core.uniprotkb.comment.Note;
import org.uniprot.core.uniprotkb.comment.impl.CofactorBuilder;
import org.uniprot.core.uniprotkb.comment.impl.CofactorCommentBuilder;

class CCCofactorBuildTest extends CCBuildTestAbstr {
    @Test
    void test1() {
        String ccLine =
                "CC   -!- COFACTOR:\n"
                        + "CC       Name=Mg(2+); Xref=ChEBI:CHEBI:18420;"
                        + " Evidence={ECO:0000255|HAMAP-\n"
                        + "CC         Rule:MF_00086};\n"
                        + "CC       Name=Co(2+); Xref=ChEBI:CHEBI:48828;"
                        + " Evidence={ECO:0000255|HAMAP-\n"
                        + "CC         Rule:MF_00089};\n"
                        + "CC       Note=Binds 2 divalent ions per subunit (magnesium or cobalt). A"
                        + " second\n"
                        + "CC       loosely associated metal ion is visible in the crystal"
                        + " structure.\n"
                        + "CC       {ECO:0000255|HAMAP-Rule:MF_00082};";

        String ccLineString =
                "COFACTOR:\n"
                        + "Name=Mg(2+); Xref=ChEBI:CHEBI:18420;\n"
                        + "Name=Co(2+); Xref=ChEBI:CHEBI:48828;\n"
                        + "Note=Binds 2 divalent ions per subunit (magnesium or cobalt). A second"
                        + " loosely associated metal ion is visible in the crystal structure.;";
        String ccLineStringEvidence =
                "COFACTOR:\n"
                        + "Name=Mg(2+); Xref=ChEBI:CHEBI:18420;"
                        + " Evidence={ECO:0000255|HAMAP-Rule:MF_00086};\n"
                        + "Name=Co(2+); Xref=ChEBI:CHEBI:48828;"
                        + " Evidence={ECO:0000255|HAMAP-Rule:MF_00089};\n"
                        + "Note=Binds 2 divalent ions per subunit (magnesium or cobalt). A second"
                        + " loosely associated metal ion is visible in the crystal structure."
                        + " {ECO:0000255|HAMAP-Rule:MF_00082};";

        CofactorCommentBuilder builder = new CofactorCommentBuilder();

        List<Cofactor> cofactors = new ArrayList<>();
        List<String> evs = new ArrayList<>();
        evs.add("ECO:0000255|HAMAP-Rule:MF_00086");
        cofactors.add(buildCofactor("Mg(2+)", CofactorDatabase.CHEBI, "CHEBI:18420", evs));
        List<String> evs2 = new ArrayList<>();
        evs2.add("ECO:0000255|HAMAP-Rule:MF_00089");
        cofactors.add(buildCofactor("Co(2+)", CofactorDatabase.CHEBI, "CHEBI:48828", evs2));
        builder.cofactorsSet(cofactors);
        String noteValue =
                "Binds 2 divalent ions per subunit (magnesium or cobalt). A second loosely"
                        + " associated metal ion is visible in the crystal structure";
        List<String> evs3 = new ArrayList<>();
        evs3.add("ECO:0000255|HAMAP-Rule:MF_00082");
        Note commentNote = buildNote(noteValue, evs3);
        builder.note(commentNote);

        CofactorComment comment = builder.build();
        doTest(ccLine, comment);
        doTestString(ccLineString, comment);
        doTestStringEv(ccLineStringEvidence, comment);
    }

    @Test
    void test2() {
        String ccLine =
                "CC   -!- COFACTOR: [Serine protease NS3]:\n"
                        + "CC       Note=Binds 1 zinc ion per NS3 protease domain.;";
        String ccLineString =
                "COFACTOR: [Serine protease NS3]:\n"
                        + "Note=Binds 1 zinc ion per NS3 protease domain.;";
        CofactorCommentBuilder builder = new CofactorCommentBuilder();
        builder.molecule("Serine protease NS3");
        List<Cofactor> cofactors = new ArrayList<>();
        List<String> evs = new ArrayList<>();
        evs.add("ECO:0000269|PubMed:9060645");
        //	cofactors.add(buildCofactor("Zn(2+)", CofactorDatabase.CHEBI, "CHEBI:29105", evs));
        builder.cofactorsSet(cofactors);
        String noteValue = "Binds 1 zinc ion per NS3 protease domain";
        List<String> evs3 = new ArrayList<>();
        // evs3.add("ECO:0000255|HAMAP-Rule:MF_00082");
        Note commentNote = buildNote(noteValue, evs3);
        builder.note(commentNote);
        CofactorComment comment = builder.build();
        doTest(ccLine, comment);
        doTestString(ccLineString, comment);
        doTestStringEv(ccLineString, comment);
    }

    @Test
    void test3() {

        String ccLine =
                "CC   -!- COFACTOR: [Serine protease NS3]:\n"
                        + "CC       Name=Zn(2+); Xref=ChEBI:CHEBI:29105;\n"
                        + "CC         Evidence={ECO:0000269|PubMed:16683188,"
                        + " ECO:0000269|PubMed:16683189};\n"
                        + "CC       Name=A very looooooooooooong cofactor name with 1 evidence tag;\n"
                        + "CC         Xref=ChEBI:CHEBI:12345;"
                        + " Evidence={ECO:0000269|PubMed:16683188};\n"
                        + "CC       Name=A very very looooooooooooong cofactor name with X evidence"
                        + " tags;\n"
                        + "CC         Xref=ChEBI:CHEBI:54321; Evidence={ECO:0000269|PubMed:16683188,\n"
                        + "CC         ECO:0000269|PubMed:16683189};\n"
                        + "CC       Note=Binds 2 divalent ions per subunit. {ECO:0000255|HAMAP-\n"
                        + "CC       Rule:MF_00086};";

        String ccLineStringEvidence =
                "COFACTOR: [Serine protease NS3]:\n"
                        + "Name=Zn(2+); Xref=ChEBI:CHEBI:29105; Evidence={ECO:0000269|PubMed:16683188,"
                        + " ECO:0000269|PubMed:16683189};\n"
                        + "Name=A very looooooooooooong cofactor name with 1 evidence tag;"
                        + " Xref=ChEBI:CHEBI:12345; Evidence={ECO:0000269|PubMed:16683188};\n"
                        + "Name=A very very looooooooooooong cofactor name with X evidence tags;"
                        + " Xref=ChEBI:CHEBI:54321; Evidence={ECO:0000269|PubMed:16683188,"
                        + " ECO:0000269|PubMed:16683189};\n"
                        + "Note=Binds 2 divalent ions per subunit. {ECO:0000255|HAMAP-Rule:MF_00086};";
        String ccLineString =
                "COFACTOR: [Serine protease NS3]:\n"
                        + "Name=Zn(2+); Xref=ChEBI:CHEBI:29105;\n"
                        + "Name=A very looooooooooooong cofactor name with 1 evidence tag;"
                        + " Xref=ChEBI:CHEBI:12345;\n"
                        + "Name=A very very looooooooooooong cofactor name with X evidence tags;"
                        + " Xref=ChEBI:CHEBI:54321;\n"
                        + "Note=Binds 2 divalent ions per subunit.;";

        CofactorCommentBuilder builder = new CofactorCommentBuilder();
        builder.molecule("Serine protease NS3");
        List<Cofactor> cofactors = new ArrayList<>();
        List<String> evs = new ArrayList<>();
        evs.add("ECO:0000269|PubMed:16683188");
        evs.add("ECO:0000269|PubMed:16683189");
        cofactors.add(buildCofactor("Zn(2+)", CofactorDatabase.CHEBI, "CHEBI:29105", evs));

        evs = new ArrayList<>();
        evs.add("ECO:0000269|PubMed:16683188");
        cofactors.add(
                buildCofactor(
                        "A very looooooooooooong cofactor name with 1 evidence tag",
                        CofactorDatabase.CHEBI,
                        "CHEBI:12345",
                        evs));

        evs = new ArrayList<>();
        evs.add("ECO:0000269|PubMed:16683188");
        evs.add("ECO:0000269|PubMed:16683189");
        cofactors.add(
                buildCofactor(
                        "A very very looooooooooooong cofactor name with X evidence tags",
                        CofactorDatabase.CHEBI,
                        "CHEBI:54321",
                        evs));

        builder.cofactorsSet(cofactors);
        String noteValue = "Binds 2 divalent ions per subunit";
        List<String> evs3 = new ArrayList<>();
        evs3.add("ECO:0000255|HAMAP-Rule:MF_00086");
        Note commentNote = buildNote(noteValue, evs3);
        builder.note(commentNote);

        CofactorComment comment = builder.build();
        doTest(ccLine, comment);
        doTestString(ccLineString, comment);
        doTestStringEv(ccLineStringEvidence, comment);
    }

    @Test
    void test4() {
        String ccLine =
                "CC   -!- COFACTOR:\n"
                        + "CC       Name=Mg(2+); Xref=ChEBI:CHEBI:18420;"
                        + " Evidence={ECO:0000255|HAMAP-\n"
                        + "CC         Rule:MF_00086};\n"
                        + "CC       Name=Co(2+); Xref=ChEBI:CHEBI:48828;"
                        + " Evidence={ECO:0000255|HAMAP-\n"
                        + "CC         Rule:MF_00089};\n"
                        + "CC       Note=Binds 2 divalent ions per subunit (magnesium or cobalt).\n"
                        + "CC       {ECO:0000255|HAMAP-Rule:MF_00082}. Another note."
                        + " {ECO:0000255|HAMAP-\n"
                        + "CC       Rule:MF_00083};";

        String ccLineString =
                "COFACTOR:\n"
                        + "Name=Mg(2+); Xref=ChEBI:CHEBI:18420;\n"
                        + "Name=Co(2+); Xref=ChEBI:CHEBI:48828;\n"
                        + "Note=Binds 2 divalent ions per subunit (magnesium or cobalt).. Another"
                        + " note.;";
        String ccLineStringEvidence =
                "COFACTOR:\n"
                        + "Name=Mg(2+); Xref=ChEBI:CHEBI:18420;"
                        + " Evidence={ECO:0000255|HAMAP-Rule:MF_00086};\n"
                        + "Name=Co(2+); Xref=ChEBI:CHEBI:48828;"
                        + " Evidence={ECO:0000255|HAMAP-Rule:MF_00089};\n"
                        + "Note=Binds 2 divalent ions per subunit (magnesium or cobalt)."
                        + " {ECO:0000255|HAMAP-Rule:MF_00082}. Another note."
                        + " {ECO:0000255|HAMAP-Rule:MF_00083};";

        CofactorCommentBuilder builder = new CofactorCommentBuilder();

        List<Cofactor> cofactors = new ArrayList<>();
        List<String> evs = new ArrayList<>();
        evs.add("ECO:0000255|HAMAP-Rule:MF_00086");
        cofactors.add(buildCofactor("Mg(2+)", CofactorDatabase.CHEBI, "CHEBI:18420", evs));
        List<String> evs2 = new ArrayList<>();
        evs2.add("ECO:0000255|HAMAP-Rule:MF_00089");
        cofactors.add(buildCofactor("Co(2+)", CofactorDatabase.CHEBI, "CHEBI:48828", evs2));
        builder.cofactorsSet(cofactors);
        String noteValue = "Binds 2 divalent ions per subunit (magnesium or cobalt)";
        List<String> evs3 = new ArrayList<>();
        evs3.add("ECO:0000255|HAMAP-Rule:MF_00082");

        List<String> evs4 = new ArrayList<>();
        evs4.add("ECO:0000255|HAMAP-Rule:MF_00083");
        String note2 = "Another note";
        List<Map.Entry<String, List<String>>> notes = new ArrayList<>();
        notes.add(new AbstractMap.SimpleEntry<>(noteValue, evs3));

        notes.add(new AbstractMap.SimpleEntry<>(note2, evs4));

        Note commentNote = buildNote(notes);
        builder.note(commentNote);

        CofactorComment comment = builder.build();
        doTest(ccLine, comment);
        doTestString(ccLineString, comment);
        doTestStringEv(ccLineStringEvidence, comment);
    }

    private Cofactor buildCofactor(
            String name, CofactorDatabase type, String refId, List<String> evids) {

        CrossReference<CofactorDatabase> coRef =
                new CrossReferenceBuilder<CofactorDatabase>().database(type).id(refId).build();
        return new CofactorBuilder()
                .name(name)
                .cofactorCrossReference(coRef)
                .evidencesSet(createEvidence(evids))
                .build();
    }
}

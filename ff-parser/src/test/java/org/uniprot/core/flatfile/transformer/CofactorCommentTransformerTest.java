package org.uniprot.core.flatfile.transformer;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.uniprot.core.CrossReference;
import org.uniprot.core.uniprotkb.comment.*;
import org.uniprot.core.uniprotkb.evidence.EvidencedValue;

class CofactorCommentTransformerTest {
    private final CofactorCommentTransformer transformer = new CofactorCommentTransformer();

    @Test
    void testNote() {
        String val = "Note=Binds 2 divalent ions per subunit (magnesium or cobalt).";

        CofactorComment comment = transformer.transform(CommentType.COFACTOR, val);

        assertNull(comment.getMolecule());
        assertEquals(0, comment.getCofactors().size());
        Note note = comment.getNote();
        assertNotNull(note);
        assertEquals(
                "Binds 2 divalent ions per subunit (magnesium or cobalt)",
                note.getTexts().get(0).getValue());
    }

    @DisplayName("Tests a cofactor that has a molecule and a note")
    @Test
    void testCofactorMoleculeAndNote() {
        String moleculeStr = "Isoform 1";
        String noteStr = "Binds 2 divalent ions per subunit (magnesium or cobalt)";

        String val = "[" + moleculeStr + "]:\n Note=" + noteStr + ".";

        CofactorComment comment = transformer.transform(CommentType.COFACTOR, val);

        assertThat(CommentType.COFACTOR, is(equalTo(comment.getCommentType())));

        assertThat(moleculeStr, is(equalTo(comment.getMolecule())));
        assertThat(comment.getCofactors(), is(empty()));
        Note note = comment.getNote();
        assertThat(noteStr, is(equalTo(note.getTexts().get(0).getValue())));

        CofactorComment comment2 = CommentTransformerHelper.transform(val, CommentType.COFACTOR);
        assertEquals(comment, comment2);
    }

    @DisplayName("Tests a cofactor that has a molecule and a single cofactor reference")
    @Test
    void testCofactorMoleculeAndSimpleReference() {
        String moleculeStr = "Isoform 1";

        String refStr1 = "CHEBI:123";
        String nameStr1 = "(-)-Variabilin";
        String refsStr1 = "Name=" + nameStr1 + "; Xref=ChEBI:" + refStr1 + ";";

        String val = "[" + moleculeStr + "]:\n" + refsStr1;
        CofactorComment comment = transformer.transform(CommentType.COFACTOR, val);

        assertThat(CommentType.COFACTOR, is(equalTo(comment.getCommentType())));

        assertThat(moleculeStr, is(equalTo(comment.getMolecule())));

        List<Cofactor> cofactors = comment.getCofactors();
        assertThat(cofactors, hasSize((1)));

        Cofactor cofactor1 = cofactors.get(0);

        CrossReference<CofactorDatabase> refObj1 = cofactor1.getCofactorCrossReference();
        assertThat(refObj1.getId(), is(equalTo(refStr1)));

        String nameObj1 = cofactor1.getName();
        assertThat(nameStr1, is(equalTo(nameObj1)));
        CofactorComment comment2 = CommentTransformerHelper.transform(val, CommentType.COFACTOR);
        assertEquals(comment, comment2);
    }

    @DisplayName("Tests a cofactor that has a molecule and two cofactor references")
    @Test
    void testCofactorMoleculeAndMultipleReferences() {
        String moleculeStr = "Isoform 1";

        String refStr1 = "CHEBI:123";
        String nameStr1 = "(-)-Variabilin";
        String refsStr1 = "Name=" + nameStr1 + "; Xref=ChEBI:" + refStr1 + ";";

        String refStr2 = "CHEBI:2131";
        String nameStr2 = "5-methyl-3-isoxazolyl sulfate";
        String refsStr2 = "Name=" + nameStr2 + "; Xref=ChEBI:" + refStr2 + ";";

        String val = "[" + moleculeStr + "]:\n" + refsStr1 + " " + refsStr2;
        CofactorComment comment = transformer.transform(CommentType.COFACTOR, val);

        assertThat(CommentType.COFACTOR, is(equalTo(comment.getCommentType())));

        assertThat(moleculeStr, is(equalTo(comment.getMolecule())));

        List<Cofactor> cofactors = comment.getCofactors();
        assertThat(cofactors, hasSize((2)));

        Cofactor cofactor1 = cofactors.get(0);

        CrossReference<CofactorDatabase> refObj1 = cofactor1.getCofactorCrossReference();
        assertThat(refObj1.getId(), is(equalTo(refStr1)));

        String nameObj1 = cofactor1.getName();
        assertThat(nameStr1, is(equalTo(nameObj1)));

        Cofactor cofactor2 = cofactors.get(1);

        CrossReference<CofactorDatabase> refObj2 = cofactor2.getCofactorCrossReference();
        assertThat(refObj2.getId(), is(equalTo(refStr2)));

        String nameObj2 = cofactor2.getName();
        assertThat(nameStr2, is(equalTo(nameObj2)));
    }

    @DisplayName("Tests a cofactor that has a molecule and a single cofactor reference and a note")
    @Test
    void testCofactorMoleculeWithSimpleReferenceAndNote() {
        String moleculeStr = "Isoform 1";

        String refStr1 = "CHEBI:123";
        String nameStr1 = "(-)-Variabilin";
        String refsStr1 = "Name=" + nameStr1 + "; Xref=ChEBI:" + refStr1 + ";";

        String noteStr = "Binds 2 divalent ions per subunit (magnesium or cobalt)";

        String val = "[" + moleculeStr + "]:\n" + refsStr1 + "\n Note=" + noteStr + ".";
        CofactorComment comment = transformer.transform(CommentType.COFACTOR, val);

        assertThat(CommentType.COFACTOR, is(equalTo(comment.getCommentType())));

        assertThat(moleculeStr, is(equalTo(comment.getMolecule())));

        List<Cofactor> cofactors = comment.getCofactors();
        assertThat(cofactors, hasSize((1)));

        Cofactor cofactor1 = cofactors.get(0);

        CrossReference<CofactorDatabase> refObj1 = cofactor1.getCofactorCrossReference();
        assertThat(refObj1.getId(), is(equalTo(refStr1)));

        String nameObj1 = cofactor1.getName();
        assertThat(nameStr1, is(equalTo(nameObj1)));

        Note note = comment.getNote();
        assertThat(noteStr, is(equalTo(note.getTexts().get(0).getValue())));
        CofactorComment comment2 = CommentTransformerHelper.transform(val, CommentType.COFACTOR);
        assertEquals(comment, comment2);
    }

    @Test
    void testInvalidcofactorFormat() {
        String val = "Isoform 1";

        try {
            CommentTransformerHelper.transform(val, CommentType.COFACTOR);
            fail("WTF?");
        } catch (IllegalArgumentException e) {

        }
    }

    @Test
    void testEvidence1() {
        String ccLineStringEvidence =
                "COFACTOR:\n"
                        + "Name=Mg(2+); Xref=ChEBI:CHEBI:18420; Evidence={ECO:0000255|HAMAP-Rule:MF_00086};\n"
                        + "Name=Co(2+); Xref=ChEBI:CHEBI:48828; Evidence={ECO:0000255|HAMAP-Rule:MF_00089, ECO:0000269|PubMed:16683189};\n"
                        + "Note=Binds 2 divalent ions per subunit (magnesium or cobalt). "
                        + "A second loosely associated metal ion is visible in the crystal structure. {ECO:0000255|HAMAP-Rule:MF_00082};";
        CofactorComment comment = transformer.transform(CommentType.COFACTOR, ccLineStringEvidence);
        assertNotNull(comment);
        assertNull(comment.getMolecule());
        assertEquals(2, comment.getCofactors().size());
        Cofactor cofactor1 = comment.getCofactors().get(0);
        Cofactor cofactor2 = comment.getCofactors().get(1);
        assertEquals("Mg(2+)", cofactor1.getName());
        assertEquals("CHEBI:18420", cofactor1.getCofactorCrossReference().getId());
        assertEquals(CofactorDatabase.CHEBI, cofactor1.getCofactorCrossReference().getDatabase());
        assertEquals(1, cofactor1.getEvidences().size());
        assertEquals("ECO:0000255|HAMAP-Rule:MF_00086", cofactor1.getEvidences().get(0).getValue());
        assertEquals("Co(2+)", cofactor2.getName());
        assertEquals("CHEBI:48828", cofactor2.getCofactorCrossReference().getId());
        assertEquals(CofactorDatabase.CHEBI, cofactor2.getCofactorCrossReference().getDatabase());
        assertEquals(2, cofactor2.getEvidences().size());
        assertEquals("ECO:0000255|HAMAP-Rule:MF_00089", cofactor2.getEvidences().get(0).getValue());
        Note note = comment.getNote();
        assertNotNull(note);
        assertEquals(1, note.getTexts().size());
        EvidencedValue note1 = note.getTexts().get(0);
        assertEquals(
                "Binds 2 divalent ions per subunit (magnesium or cobalt). "
                        + "A second loosely associated metal ion is visible in the crystal structure",
                note1.getValue());
        assertEquals(1, note1.getEvidences().size());
        assertEquals("ECO:0000255|HAMAP-Rule:MF_00082", note1.getEvidences().get(0).getValue());
    }

    @Test
    void testEvidence2() {
        String ccLineStringEvidence =
                "COFACTOR: [Serine protease NS3]:\n"
                        + "Name=Zn(2+); Xref=ChEBI:CHEBI:29105; Evidence={ECO:0000269|PubMed:16683188,"
                        + " ECO:0000269|PubMed:16683189};\n"
                        + "Name=A very looooooooooooong cofactor name with 1 evidence tag; "
                        + "Xref=ChEBI:CHEBI:12345; Evidence={ECO:0000269|PubMed:16683188};\n"
                        + "Name=A very very looooooooooooong cofactor name with X evidence tags; "
                        + "Xref=ChEBI:CHEBI:54321; Evidence={ECO:0000269|PubMed:16683188, ECO:0000269|PubMed:16683189};\n"
                        + "Note=Binds 2 divalent ions per subunit. {ECO:0000269|PubMed:16683188, "
                        + "ECO:0000255|HAMAP-Rule:MF_00086}. Another note. {ECO:0000269|PubMed:16683189};";
        CofactorComment comment = transformer.transform(CommentType.COFACTOR, ccLineStringEvidence);
        assertNotNull(comment);
        assertEquals("Serine protease NS3", comment.getMolecule());
        assertEquals(3, comment.getCofactors().size());
        Cofactor cofactor1 = comment.getCofactors().get(0);
        Cofactor cofactor3 = comment.getCofactors().get(2);
        assertEquals("Zn(2+)", cofactor1.getName());
        assertEquals("CHEBI:29105", cofactor1.getCofactorCrossReference().getId());
        assertEquals(CofactorDatabase.CHEBI, cofactor1.getCofactorCrossReference().getDatabase());
        assertEquals(2, cofactor1.getEvidences().size());
        assertEquals("ECO:0000269|PubMed:16683188", cofactor1.getEvidences().get(0).getValue());
        assertEquals(
                "A very very looooooooooooong cofactor name with X evidence tags",
                cofactor3.getName());
        assertEquals("CHEBI:54321", cofactor3.getCofactorCrossReference().getId());
        assertEquals(CofactorDatabase.CHEBI, cofactor3.getCofactorCrossReference().getDatabase());
        assertEquals(2, cofactor3.getEvidences().size());
        assertEquals("ECO:0000269|PubMed:16683189", cofactor3.getEvidences().get(1).getValue());
        Note note = comment.getNote();
        assertNotNull(note);
        assertEquals(2, note.getTexts().size());
        EvidencedValue note1 = note.getTexts().get(0);
        EvidencedValue note2 = note.getTexts().get(1);
        assertEquals("Binds 2 divalent ions per subunit", note1.getValue());
        assertEquals(2, note1.getEvidences().size());
        assertEquals("ECO:0000255|HAMAP-Rule:MF_00086", note1.getEvidences().get(1).getValue());

        assertEquals("Another note", note2.getValue());
        assertEquals(1, note2.getEvidences().size());
        assertEquals("ECO:0000269|PubMed:16683189", note2.getEvidences().get(0).getValue());
    }

    @Test
    void testFailed() {
        String ccLineStringEvidence =
                "COFACTOR: [Serine protease NS3]:\n"
                        + "Name=Zn(2+); Xref=ChEBI:CHEBI:29105; Evidence={ECO:0000269|PubMed:9060645};\n"
                        + "Note=Binds 1 zinc ion. {ECO:0000269|PubMed:9060645};";

        CofactorComment comment = transformer.transform(CommentType.COFACTOR, ccLineStringEvidence);
        assertNotNull(comment);
        assertEquals("Serine protease NS3", comment.getMolecule());
        assertEquals(1, comment.getCofactors().size());
        Cofactor cofactor1 = comment.getCofactors().get(0);

        assertEquals("Zn(2+)", cofactor1.getName());
        assertEquals("CHEBI:29105", cofactor1.getCofactorCrossReference().getId());
        assertEquals(CofactorDatabase.CHEBI, cofactor1.getCofactorCrossReference().getDatabase());
        assertEquals(1, cofactor1.getEvidences().size());
        assertEquals("ECO:0000269|PubMed:9060645", cofactor1.getEvidences().get(0).getValue());

        Note note = comment.getNote();
        assertNotNull(note);
        assertEquals(1, note.getTexts().size());
        EvidencedValue note1 = note.getTexts().get(0);

        assertEquals("Binds 1 zinc ion", note1.getValue());
        assertEquals(1, note1.getEvidences().size());
        assertEquals("ECO:0000269|PubMed:9060645", note1.getEvidences().get(0).getValue());
    }

    @Test
    void testFailed2() {
        String ccLineStringEvidence =
                "COFACTOR: [Non-structural protein 5A]:\n"
                        + "Name=Zn(2+); Xref=ChEBI:CHEBI:29105; Evidence={ECO:0000250};\n"
                        + " Note=Binds 1 zinc ion in the NS5A N-terminal domain. {ECO:0000250};";

        CofactorComment comment = transformer.transform(CommentType.COFACTOR, ccLineStringEvidence);
        assertNotNull(comment);

        assertEquals("Non-structural protein 5A", comment.getMolecule());
        assertEquals(1, comment.getCofactors().size());
        Cofactor cofactor1 = comment.getCofactors().get(0);

        assertEquals("Zn(2+)", cofactor1.getName());
        assertEquals("CHEBI:29105", cofactor1.getCofactorCrossReference().getId());
        assertEquals(CofactorDatabase.CHEBI, cofactor1.getCofactorCrossReference().getDatabase());
        assertEquals(1, cofactor1.getEvidences().size());
        assertEquals("ECO:0000250", cofactor1.getEvidences().get(0).getValue());

        Note note = comment.getNote();
        assertNotNull(note);
        assertEquals(1, note.getTexts().size());
        EvidencedValue note1 = note.getTexts().get(0);

        assertEquals("Binds 1 zinc ion in the NS5A N-terminal domain", note1.getValue());
        assertEquals(1, note1.getEvidences().size());
        assertEquals("ECO:0000250", note1.getEvidences().get(0).getValue());
    }

    @Test
    void testMoleculeWithSlashSymbol() {
        String moleculeStr = "Bifunctional cytochrome P450/NADPH--P450 reductase";

        String refStr1 = "CHEBI:57692";
        String nameStr1 = "FAD";
        String refsStr1 = "Name=" + nameStr1 + "; Xref=ChEBI:" + refStr1 + ";";

        String val = "[" + moleculeStr + "]:\n" + refsStr1;
        System.out.println(val);
        CofactorComment comment = transformer.transform(CommentType.COFACTOR, val);
        assertNotNull(comment);
    }
}

package org.uniprot.core.flatfile.writer.line.cc;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprotkb.comment.MassSpectrometryComment;
import org.uniprot.core.uniprotkb.comment.MassSpectrometryMethod;
import org.uniprot.core.uniprotkb.comment.impl.MassSpectrometryCommentBuilder;

import com.google.common.base.Strings;

class CCMassSpectromBuildTest extends CCBuildTestAbstr {
    @Test
    void testMASSSPEC() {
        String ccLine =
                "CC   -!- MASS SPECTROMETRY: Mass=2189.4; Method=Electrospray;\n"
                        + "CC       Note=Monophosphorylated; Evidence={ECO:0000303|PubMed:16629414};";

        String ccLineString =
                "MASS SPECTROMETRY: Mass=2189.4; Method=Electrospray; Note=Monophosphorylated;"
                        + " Evidence={ECO:0000303|PubMed:16629414};";
        String ev1 = "ECO:0000303|PubMed:16629414";

        List<String> evidences = new ArrayList<>();
        evidences.add(ev1);
        String note = "Monophosphorylated";
        Float molWeight = 2189.4f;
        Float molWeightError = 0.0f;
        MassSpectrometryCommentBuilder builder =
                buildComment(
                        molWeight,
                        molWeightError,
                        MassSpectrometryMethod.ELECTROSPRAY,
                        note,
                        evidences,
                        true);
        MassSpectrometryComment comment = builder.build();
        doTest(ccLine, comment);
        doTestString(ccLineString, comment);
    }

    @Test
    void testMASSSPEC2() {
        String ccLine =
                "CC   -!- MASS SPECTROMETRY: [P04653-1]: Mass=22629; Mass_error=1.6;\n"
                        + "CC       Method=Electrospray; Note=Allele D, with 6 phosphate groups;\n"
                        + "CC       Evidence={ECO:0000303|PubMed:16629414};";
        String ccLineString =
                "MASS SPECTROMETRY: [P04653-1]: Mass=22629; Mass_error=1.6; Method=Electrospray;"
                        + " Note=Allele D, with 6 phosphate groups;"
                        + " Evidence={ECO:0000303|PubMed:16629414};";
        String ev1 = "ECO:0000303|PubMed:16629414";
        List<String> evidences = new ArrayList<>();
        evidences.add(ev1);
        String note = "Allele D, with 6 phosphate groups";
        Float molWeight = 22629f;
        Float molWeightError = 1.6f;
        MassSpectrometryCommentBuilder builder =
                buildComment(
                        molWeight,
                        molWeightError,
                        MassSpectrometryMethod.ELECTROSPRAY,
                        note,
                        evidences,
                        true);
        builder.molecule("P04653-1");

        MassSpectrometryComment comment = builder.build();
        doTest(ccLine, comment);
        doTestString(ccLineString, comment);
    }

    @Test
    void testMass3() {
        String ccLine =
                "CC   -!- MASS SPECTROMETRY: [P04653-1]: Mass=22629; Mass_error=1.6;\n"
                        + "CC       Method=Electrospray; Note=Allele D, with 6 phosphate groups;\n"
                        + "CC       Evidence={ECO:0000303|PubMed:16629414};";
        String ccLineString =
                "MASS SPECTROMETRY: [P04653-1]: Mass=22629; Mass_error=1.6; "
                        + "Method=Electrospray; Note=Allele D, with "
                        + "6 phosphate groups; Evidence={ECO:0000303|PubMed:16629414};";
        String ccLineStringEvidence =
                "MASS SPECTROMETRY: [P04653-1]: Mass=22629; Mass_error=1.6; "
                        + "Method=Electrospray; Note=Allele D, with "
                        + "6 phosphate groups; Evidence={ECO:0000303|PubMed:16629414};";
        String ev1 = "ECO:0000303|PubMed:16629414";
        List<String> evidences = new ArrayList<>();
        evidences.add(ev1);
        String note = "Allele D, with 6 phosphate groups";
        Float molWeight = 22629f;
        Float molWeightError = 1.6f;
        MassSpectrometryCommentBuilder builder =
                buildComment(
                        molWeight,
                        molWeightError,
                        MassSpectrometryMethod.ELECTROSPRAY,
                        note,
                        evidences,
                        false);
        builder.molecule("P04653-1");
        MassSpectrometryComment comment = builder.build();
        doTest(ccLine, comment);
        doTestString(ccLineString, comment);

        doTestStringEv(ccLineStringEvidence, comment);
    }

    @Test
    void testMass4() {
        String ccLine =
                "CC   -!- MASS SPECTROMETRY: Mass=3260; Method=MALDI;\n"
                        + "CC       Evidence={ECO:0000303|PubMed:16629414};";
        String ccLineString =
                "MASS SPECTROMETRY: Mass=3260; Method=MALDI;"
                        + " Evidence={ECO:0000303|PubMed:16629414};";
        String ccLineStringEvidence =
                "MASS SPECTROMETRY: Mass=3260; Method=MALDI; "
                        + "Evidence={ECO:0000303|PubMed:16629414};";
        String ev1 = "ECO:0000303|PubMed:16629414";
        List<String> evidences = new ArrayList<>();
        evidences.add(ev1);
        String note = "";
        Float molWeight = 3260f;
        Float molWeightError = null;
        MassSpectrometryCommentBuilder builder =
                buildComment(
                        molWeight,
                        molWeightError,
                        MassSpectrometryMethod.MALDI,
                        note,
                        evidences,
                        false);

        MassSpectrometryComment comment = builder.build();
        doTest(ccLine, comment);
        doTestString(ccLineString, comment);
        doTestStringEv(ccLineStringEvidence, comment);
    }

    @Test
    void test6() {
        String ccLine =
                "CC   -!- MASS SPECTROMETRY: [P04653-1]: Mass=871.3; Method=Electrospray;\n"
                        + "CC       Note=Monophosphorylated; Evidence={ECO:0000303|PubMed:16629414};";
        String ccLineString =
                "MASS SPECTROMETRY: [P04653-1]: Mass=871.3; Method=Electrospray;"
                        + " Note=Monophosphorylated; Evidence={ECO:0000303|PubMed:16629414};";
        String ev1 = "ECO:0000303|PubMed:16629414";
        List<String> evidences = new ArrayList<>();
        evidences.add(ev1);
        String note = "Monophosphorylated";
        Float molWeight = 871.3f;
        Float molWeightError = null;
        MassSpectrometryCommentBuilder builder =
                buildComment(
                        molWeight,
                        molWeightError,
                        MassSpectrometryMethod.ELECTROSPRAY,
                        note,
                        evidences,
                        true);
        builder.molecule("P04653-1");
        MassSpectrometryComment comment = builder.build();
        doTest(ccLine, comment);
        doTestString(ccLineString, comment);
    }

    MassSpectrometryCommentBuilder buildComment(
            Float molWeight,
            Float molWeightError,
            MassSpectrometryMethod method,
            String note,
            List<String> evs,
            boolean setSources) {
        MassSpectrometryCommentBuilder builder = new MassSpectrometryCommentBuilder();
        builder.evidencesSet(createEvidence(evs));
        builder.molWeight(molWeight);
        builder.molWeightError(molWeightError);
        builder.method(method);
        if (!Strings.isNullOrEmpty(note)) builder.note(note);
        return builder;
    }
}

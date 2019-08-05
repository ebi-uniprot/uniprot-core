package org.uniprot.core.flatfile.writer.line.cc;

import com.google.common.base.Strings;
import org.junit.Test;
import org.uniprot.core.Range;
import org.uniprot.core.uniprot.comment.MassSpectrometryComment;
import org.uniprot.core.uniprot.comment.MassSpectrometryMethod;
import org.uniprot.core.uniprot.comment.MassSpectrometryRange;
import org.uniprot.core.uniprot.comment.builder.MassSpectrometryCommentBuilder;
import org.uniprot.core.uniprot.comment.builder.MassSpectrometryRangeBuilder;

import java.util.ArrayList;
import java.util.List;

public class CCMassSpectromBuildTest extends CCBuildTestAbstr {
    @Test
    public void testMASSSPEC() {
        String ccLine = "CC   -!- MASS SPECTROMETRY: Mass=2189.4; Method=Electrospray;\n" +
                "CC       Range=167-186; Note=Monophosphorylated;\n" +
                "CC       Evidence={ECO:0000303|PubMed:16629414};";

        String ccLineString =
                "MASS SPECTROMETRY: Mass=2189.4; Method=Electrospray; Range=167-186; Note=Monophosphorylated; Evidence={ECO:0000303|PubMed:16629414};";
        String ev1 = "ECO:0000303|PubMed:16629414";


        List<String> evidences = new ArrayList<>();
        evidences.add(ev1);
        String note = "Monophosphorylated";
        Float molWeight = 2189.4f;
        Float molWeightError = 0.0f;
        MassSpectrometryCommentBuilder builder = buildComment(molWeight, molWeightError,
                                                              MassSpectrometryMethod.ELECTROSPRAY,
                                                              note, evidences, true);
        List<MassSpectrometryRange> ranges = new ArrayList<>();
        ranges.add(buildRange(167, 186, ""));
        builder.ranges(ranges);
        MassSpectrometryComment comment = builder.build();
        doTest(ccLine, comment);
        doTestString(ccLineString, comment);
    }

    @Test
    public void testMASSSPEC2() {
        String ccLine = "CC   -!- MASS SPECTROMETRY: Mass=22629; Mass_error=1.6;\n" +
                "CC       Method=Electrospray; Range=16-214 (P04653-1); Note=Allele D, with\n" +
                "CC       6 phosphate groups; Evidence={ECO:0000303|PubMed:16629414};";
        String ccLineString =
                "MASS SPECTROMETRY: Mass=22629; Mass_error=1.6; Method=Electrospray; Range=16-214 (P04653-1); Note=Allele D, with "
                        +
                        "6 phosphate groups; Evidence={ECO:0000303|PubMed:16629414};";
        String ev1 = "ECO:0000303|PubMed:16629414";
        List<String> evidences = new ArrayList<>();
        evidences.add(ev1);
        String note = "Allele D, with 6 phosphate groups";
        Float molWeight = 22629f;
        Float molWeightError = 1.6f;
        MassSpectrometryCommentBuilder builder = buildComment(molWeight, molWeightError,
                                                              MassSpectrometryMethod.ELECTROSPRAY,
                                                              note, evidences, true);
        List<MassSpectrometryRange> ranges = new ArrayList<>();
        ranges.add(buildRange(16, 214, "P04653-1"));
        builder.ranges(ranges);
        MassSpectrometryComment comment = builder.build();
        doTest(ccLine, comment);
        doTestString(ccLineString, comment);
    }

    @Test
    public void testMass3() {
        String ccLine = "CC   -!- MASS SPECTROMETRY: Mass=22629; Mass_error=1.6;\n" +
                "CC       Method=Electrospray; Range=16-214 (P04653-1); Note=Allele D, with\n" +
                "CC       6 phosphate groups; Evidence={ECO:0000303|PubMed:16629414};";
        String ccLineString = "MASS SPECTROMETRY: Mass=22629; Mass_error=1.6; " +
                "Method=Electrospray; Range=16-214 (P04653-1); Note=Allele D, with " +
                "6 phosphate groups; Evidence={ECO:0000303|PubMed:16629414};";
        String ccLineStringEvidence = "MASS SPECTROMETRY: Mass=22629; Mass_error=1.6; " +
                "Method=Electrospray; Range=16-214 (P04653-1); Note=Allele D, with " +
                "6 phosphate groups; Evidence={ECO:0000303|PubMed:16629414};";
        String ev1 = "ECO:0000303|PubMed:16629414";
        List<String> evidences = new ArrayList<>();
        evidences.add(ev1);
        String note = "Allele D, with 6 phosphate groups";
        Float molWeight = 22629f;
        Float molWeightError = 1.6f;
        MassSpectrometryCommentBuilder builder = buildComment(molWeight, molWeightError,
                                                              MassSpectrometryMethod.ELECTROSPRAY,
                                                              note, evidences, false);
        List<MassSpectrometryRange> ranges = new ArrayList<>();
        ranges.add(buildRange(16, 214, "P04653-1"));
        builder.ranges(ranges);
        MassSpectrometryComment comment = builder.build();
        doTest(ccLine, comment);
        doTestString(ccLineString, comment);

        doTestStringEv(ccLineStringEvidence, comment);
    }

    @Test
    public void testMass4() {
        String ccLine = "CC   -!- MASS SPECTROMETRY: Mass=3260; Method=MALDI; Range=?-?;\n" +
                "CC       Evidence={ECO:0000303|PubMed:16629414};";
        String ccLineString = "MASS SPECTROMETRY: Mass=3260; Method=MALDI; Range=?-?; Evidence={ECO:0000303|PubMed:16629414};";
        String ccLineStringEvidence = "MASS SPECTROMETRY: Mass=3260; Method=MALDI; Range=?-?; " +
                "Evidence={ECO:0000303|PubMed:16629414};";
        String ev1 = "ECO:0000303|PubMed:16629414";
        List<String> evidences = new ArrayList<>();
        evidences.add(ev1);
        String note = "";
        Float molWeight = 3260f;
        Float molWeightError = null;
        MassSpectrometryCommentBuilder builder = buildComment(molWeight, molWeightError,
                                                              MassSpectrometryMethod.MALDI,
                                                              note, evidences, false);
        List<MassSpectrometryRange> ranges = new ArrayList<>();
        ranges.add(buildRange(-1, -1, ""));
        builder.ranges(ranges);
        MassSpectrometryComment comment = builder.build();
        doTest(ccLine, comment);
        doTestString(ccLineString, comment);
        doTestStringEv(ccLineStringEvidence, comment);
    }

    @Test
    public void test6() {
        String ccLine = "CC   -!- MASS SPECTROMETRY: Mass=871.3; Method=Electrospray; Range=35-42,\n" +
                "CC       101-108 (P04653-1), 145-152, 189-196; Note=Monophosphorylated;\n" +
                "CC       Evidence={ECO:0000303|PubMed:16629414};";
        String ccLineString = "MASS SPECTROMETRY: Mass=871.3; Method=Electrospray; Range=35-42, " +
                "101-108 (P04653-1), 145-152, 189-196; Note=Monophosphorylated; Evidence={ECO:0000303|PubMed:16629414};";
        String ev1 = "ECO:0000303|PubMed:16629414";
        List<String> evidences = new ArrayList<>();
        evidences.add(ev1);
        String note = "Monophosphorylated";
        Float molWeight = 871.3f;
        Float molWeightError = null;
        MassSpectrometryCommentBuilder builder = buildComment(molWeight, molWeightError,
                                                              MassSpectrometryMethod.ELECTROSPRAY,
                                                              note, evidences, true);
        List<MassSpectrometryRange> ranges = new ArrayList<>();
        ranges.add(buildRange(35, 42, ""));
        ranges.add(buildRange(101, 108, "P04653-1"));
        ranges.add(buildRange(145, 152, ""));
        ranges.add(buildRange(189, 196, ""));
        builder.ranges(ranges);
        MassSpectrometryComment comment = builder.build();
        doTest(ccLine, comment);
        doTestString(ccLineString, comment);
    }

    MassSpectrometryCommentBuilder buildComment(Float molWeight, Float molWeightError,
                                                MassSpectrometryMethod method,
                                                String note, List<String> evs, boolean setSources) {
        MassSpectrometryCommentBuilder builder = new MassSpectrometryCommentBuilder();
        builder.evidences(createEvidence(evs));
        builder.molWeight(molWeight);
        builder.molWeightError(molWeightError);
        builder.method(method);
        if (!Strings.isNullOrEmpty(note))
            builder.note(note);
        return builder;
    }

    MassSpectrometryRange buildRange(int start, int end, String isoform) {
        return new MassSpectrometryRangeBuilder().range(new Range(start, end)).isoformId(isoform).build();
    }
}

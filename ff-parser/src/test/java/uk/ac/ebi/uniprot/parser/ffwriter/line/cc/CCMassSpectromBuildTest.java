package uk.ac.ebi.uniprot.parser.ffwriter.line.cc;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

import com.google.common.base.Strings;

import uk.ac.ebi.uniprot.domain.uniprot.comments.MassSpectrometryComment;
import uk.ac.ebi.uniprot.domain.uniprot.comments.MassSpectrometryMethod;
import uk.ac.ebi.uniprot.domain.uniprot.comments.MassSpectrometryRange;
import uk.ac.ebi.uniprot.domain.uniprot.comments.builder.MassSpectrometryCommentBuilder;

public class CCMassSpectromBuildTest extends CCBuildTestAbstr {
    @Test
    public void testMASSSPEC() {
        String ccLine = "CC   -!- MASS SPECTROMETRY: Mass=2189.4; Method=Electrospray; Range=167-\n" +
                "CC       186; Note=Monophosphorylated;\n" +
                "CC       Evidence={ECO:0000303|PubMed:16629414};";

        String ccLineString =
                "MASS SPECTROMETRY: Mass=2189.4; Method=Electrospray; Range=167-186; Note=Monophosphorylated; Evidence={ECO:0000303|PubMed:16629414};";
        String ev1 = "ECO:0000303|PubMed:16629414";
        
    
        List<String> evidences = new ArrayList<>();
        evidences.add(ev1);
        String note = "Monophosphorylated";
        Double molWeight = 2189.4;
        Double molWeightError = 0.0;
        MassSpectrometryCommentBuilder builder = buildComment(molWeight, molWeightError,
                MassSpectrometryMethod.ELECTROSPRAY,
                note, evidences, true);
        List<MassSpectrometryRange> ranges = new ArrayList<>();
        ranges.add(buildRange(167, 186, ""));
        builder.massSpectrometryRanges(ranges);
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
        double molWeight = 22629;
        Double molWeightError = 1.6;
        MassSpectrometryCommentBuilder builder = buildComment(molWeight, molWeightError,
                MassSpectrometryMethod.ELECTROSPRAY,
                note, evidences, true);
        List<MassSpectrometryRange> ranges = new ArrayList<>();
        ranges.add(buildRange(16, 214, "P04653-1"));
        builder.massSpectrometryRanges(ranges);
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
        double molWeight = 22629;
        double molWeightError = 1.6;
        MassSpectrometryCommentBuilder builder = buildComment(molWeight, molWeightError,
                MassSpectrometryMethod.ELECTROSPRAY,
                note, evidences, false);
        List<MassSpectrometryRange> ranges = new ArrayList<>();
        ranges.add(buildRange(16, 214, "P04653-1"));
        builder.massSpectrometryRanges(ranges);
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
        double molWeight = 3260;
        Double molWeightError = null;
        MassSpectrometryCommentBuilder builder = buildComment(molWeight, molWeightError,
                MassSpectrometryMethod.MALDI,
                note, evidences, false);
        List<MassSpectrometryRange> ranges = new ArrayList<>();
        ranges.add(buildRange(-1, -1, ""));
        builder.massSpectrometryRanges(ranges);
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
        Double molWeight = 871.3;
        Double molWeightError = null;
        MassSpectrometryCommentBuilder builder = buildComment(molWeight, molWeightError,
                MassSpectrometryMethod.ELECTROSPRAY,
                note, evidences, true);
        List<MassSpectrometryRange> ranges = new ArrayList<>();
        ranges.add(buildRange(35, 42, ""));
        ranges.add(buildRange(101, 108, "P04653-1"));
        ranges.add(buildRange(145, 152, ""));
        ranges.add(buildRange(189, 196, ""));
        builder.massSpectrometryRanges(ranges);
        MassSpectrometryComment comment = builder.build();
        doTest(ccLine, comment);
        doTestString(ccLineString, comment);
    }

    MassSpectrometryCommentBuilder buildComment(double molWeight, Double molWeightError,
            MassSpectrometryMethod method,
            String note, List<String> evs, boolean setSources) {
        MassSpectrometryCommentBuilder builder =MassSpectrometryCommentBuilder.newInstance();
        builder.evidences(createEvidence(evs));
        builder.molWeight(molWeight);
        builder.molWeightError(molWeightError);
        builder.massSpectrometryMethod(method);
        if(!Strings.isNullOrEmpty(note))
        	builder.note(note);
        return builder;
    }

    MassSpectrometryRange buildRange(int start, int end, String isoform) {
    	return MassSpectrometryCommentBuilder.createMassSpectrometryRange(start, end, isoform);
   
    }
}

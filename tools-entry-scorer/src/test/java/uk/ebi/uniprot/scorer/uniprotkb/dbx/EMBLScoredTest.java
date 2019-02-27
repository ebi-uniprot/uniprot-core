package uk.ebi.uniprot.scorer.uniprotkb.dbx;

import org.junit.Test;
import uk.ac.ebi.uniprot.domain.uniprot.xdb.UniProtDBCrossReference;
import uk.ebi.uniprot.scorer.uniprotkb.HasScore;
import uk.ebi.uniprot.scorer.uniprotkb.xdb.EmblScored;

import java.util.List;

public class EMBLScoredTest extends AbstractDBXTest {
    @Test
    public void shouldEmblScore3() {
        String line = "DR   EMBL; AF377743; AAK60255.1; ALT_INIT; Genomic_DNA.";
        testDBXrefScore(line, 3.0);
    }

    @Test
    public void shouldEmbl3Score30() {
        String line = "DR   EMBL; AF280559; AAG22715.1; ALT_SEQ; Genomic_DNA.";
        testDBXrefScore(line, 3.0);
    }

    @Test
    public void shouldEmbl2Score30() {
        String line = "DR   EMBL; V01146; CAA24416.1; ALT_TERM; Genomic_DNA.";
        testDBXrefScore(line, 3.0);
    }

    @Test
    public void shouldEmblScore30() {
        String line = "DR   EMBL; Z73122; CAA97462.1; ALT_FRAME; Genomic_DNA.";
        testDBXrefScore(line, 3.0);
    }

    @Test
    public void shouldEmbl5Score01() {
        String line = "DR   EMBL; M63397; AAA51662.1; -; Genomic_DNA.\n" +
                "DR   EMBL; M63395; AAA51662.1; JOINED; Genomic_DNA.\n" +
                "DR   EMBL; M63396; AAA51662.1; JOINED; Genomic_DNA.";
        testDBXrefScore(line, 0.1);
    }

    @Test
    public void shouldEmblScore1() {
        String line = "DR   EMBL; AJ243418; -; NOT_ANNOTATED_CDS; mRNA.";
        testDBXrefScore(line, 1.0);
    }

    @Test
    public void shouldEmbl4Score01() {
        String line = "DR   EMBL; AK057020; BAB71346.1; -; mRNA.{EI1}";
        testDBXrefScore(line, 0.1);
    }

    @Test
    public void shouldEmbl3Score01() {
        String line = "DR   EMBL; M63397; AAA51662.1; -; Genomic_DNA.\n" +
                "DR   EMBL; M63395; AAA51662.1; JOINED; Genomic_DNA.\n" +
                "DR   EMBL; M63396; AAA51662.1; JOINED; Genomic_DNA.\n" +
                "DR   EMBL; AK057020; BAB71346.1; -; mRNA.{EI1}";
        testDBXrefScore(line, 0.1);
    }

    @Test
    public void shouldEmbl2Score01() {
        String line = "DR   EMBL; AK057020; BAB71346.1; -; mRNA.{EI1}";
        testDBXrefScore(line, 0.1);
    }

    @Test
    public void shouldEmbl2Score31() {
        String line = "DR   EMBL; M63397; AAA51662.1; -; Genomic_DNA.\n" +
                "DR   EMBL; M63395; AAA51662.1; JOINED; Genomic_DNA.\n" +
                "DR   EMBL; M63396; AAA51662.1; JOINED; Genomic_DNA.\n" +
                "DR   EMBL; AK057020; BAB71346.1; -; mRNA.{EI1}\n" +
                "DR   EMBL; Z73122; CAA97462.1; ALT_FRAME; Genomic_DNA.\n" +
                "DR   EMBL; Z73122; CAA97462.1; ALT_FRAME; Genomic_DNA.\n" +
                "DR   EMBL; AK057020; BAB71346.1; -; mRNA.{EI1}\n" +
                "DR   EMBL; AK057020; BAB71346.1; JOINED; mRNA.{EI1}";
        testDBXrefScore(line, 3.1);
    }

    @Test
    public void shouldEmblScore31() {
        String line = "DR   EMBL; M63397; AAA51662.1; -; Genomic_DNA.\n" +
                "DR   EMBL; M63395; AAA51662.1; JOINED; Genomic_DNA.\n" +
                "DR   EMBL; M63396; AAA51662.1; JOINED; Genomic_DNA.\n" +
                "DR   EMBL; AK057020; BAB71346.1; -; mRNA.{EI1}\n" +
                "DR   EMBL; AF377743; AAK60255.1; ALT_INIT; Genomic_DNA.\n" +
                "DR   EMBL; Z73122; CAA97462.1; ALT_FRAME; Genomic_DNA.\n" +
                "DR   EMBL; Z73122; CAA97462.1; ALT_FRAME; Genomic_DNA.\n" +
                "DR   EMBL; AK057020; BAB71346.1; -; mRNA.{EI1}\n" +
                "DR   EMBL; AK057020; BAB71346.1; JOINED; mRNA.{EI1}";
        testDBXrefScore(line, 3.1);
    }

    @Test
    public void shouldEmblScored41() {
        String line = "DR   EMBL; M63397; AAA51662.1; -; Genomic_DNA.\n" +
                "DR   EMBL; M63395; AAA51662.1; JOINED; Genomic_DNA.\n" +
                "DR   EMBL; M63396; AAA51662.1; JOINED; Genomic_DNA.\n" +
                "DR   EMBL; AK057020; BAB71346.1; -; mRNA.{EI1}\n" +
                "DR   EMBL; Z73122; CAA97462.1; ALT_FRAME; Genomic_DNA.\n" +
                "DR   EMBL; Z73122; CAA97462.1; ALT_FRAME; Genomic_DNA.\n" +
                "DR   EMBL; AK057020; BAB71346.1; -; mRNA.{EI1}\n" +
                "DR   EMBL; AK057020; BAB71346.1; JOINED; mRNA.{EI1}\n" +
                "DR   EMBL; AJ243418; -; NOT_ANNOTATED_CDS; mRNA.";
        testDBXrefScore(line, 4.1);

    }

    @Test
    public void shouldEmblScore01() {
        String line = "DR   EMBL; EU095219; ABU47413.1; -; Genomic_DNA.\n" +
                "DR   EMBL; EU095220; ABU47426.1; -; Genomic_DNA.\n" +
                "DR   EMBL; EU095221; ABU47439.1; -; Genomic_DNA.\n" +
                "DR   EMBL; EU095222; ABU47452.1; -; Genomic_DNA.\n" +
                "DR   EMBL; EU095223; ABU47465.1; -; Genomic_DNA.\n" +
                "DR   EMBL; EU095224; ABU47478.1; -; Genomic_DNA.\n" +
                "DR   EMBL; EU095225; ABU47491.1; -; Genomic_DNA.\n" +
                "DR   EMBL; EU095226; ABU47504.1; -; Genomic_DNA.\n" +
                "DR   EMBL; EU095227; ABU47517.1; -; Genomic_DNA.\n" +
                "DR   EMBL; EU095228; ABU47530.1; -; Genomic_DNA.\n" +
                "DR   EMBL; EU095229; ABU47543.1; -; Genomic_DNA.\n" +
                "DR   EMBL; EU095230; ABU47556.1; -; Genomic_DNA.\n" +
                "DR   EMBL; EU095231; ABU47569.1; -; Genomic_DNA.\n" +
                "DR   EMBL; EU095232; ABU47582.1; -; Genomic_DNA.\n" +
                "DR   EMBL; EU095233; ABU47595.1; -; Genomic_DNA.\n" +
                "DR   EMBL; EU095234; ABU47608.1; -; Genomic_DNA.\n" +
                "DR   EMBL; EU095235; ABU47621.1; -; Genomic_DNA.\n" +
                "DR   EMBL; EU095236; ABU47634.1; -; Genomic_DNA.\n" +
                "DR   EMBL; EU095237; ABU47647.1; -; Genomic_DNA.\n" +
                "DR   EMBL; EU095238; ABU47660.1; -; Genomic_DNA.\n" +
                "DR   EMBL; EU095239; ABU47673.1; -; Genomic_DNA.\n" +
                "DR   EMBL; EU095240; ABU47686.1; -; Genomic_DNA.";
        testDBXrefScore(line, 0.1);
    }

    @Override
    HasScore getScored(String lines) {
        List<UniProtDBCrossReference> crossReferences = getDBXRefs(lines, "EMBL");
        return new EmblScored(crossReferences);
    }
}
package uk.ebi.uniprot.scorer.uniprotkb.comments;

import org.junit.Test;
import uk.ac.ebi.uniprot.domain.uniprot.comment.CommentType;

public class RnaEditingScoredTest extends CommentScoreTestBase {
    @Test
    public void shouldScore30() throws Exception {
        String line = "CC   -!- RNA EDITING: Modified_positions=245; Note=Partially edited. Target\n" +
                "CC       of Adar.;";
        verify(CommentType.RNA_EDITING, line, 3.0);
    }

    @Test
    public void shouldWithEvScore30() throws Exception {
        String line = "CC   -!- RNA EDITING: Modified_positions=245; Note=Partially edited. Target\n" +
                "CC       of Adar. {ECO:0000256|HAMAP-Rule:MF_01146};";
        verify(CommentType.RNA_EDITING, line, 3.0);
    }

    @Test
    public void shouldSpScore90() throws Exception {
        String line = "CC   -!- RNA EDITING: Modified_positions=Undetermined; Note=Partially\n" +
                "CC       edited. RNA editing at this position consists of an insertion of\n" +
                "CC       one guanine nucleotide (By similarity). The sequence displayed\n" +
                "CC       here is the P protein, derived from the unedited RNA. The edited\n" +
                "CC       RNA gives rise to the V protein.;";
        verify(CommentType.RNA_EDITING, line, 9.0, true);
    }

    @Test
    public void shouldSp2Score90() throws Exception {
        String line = "CC   -!- RNA EDITING: Modified_positions=7, 17, 28, 31, 61, 64, 75; Note=By\n" +
                "CC       similarity. The stop codon at position 75 is created by RNA\n" +
                "CC       editing.;";
        verify(CommentType.RNA_EDITING, line, 9.0, true);
    }

}

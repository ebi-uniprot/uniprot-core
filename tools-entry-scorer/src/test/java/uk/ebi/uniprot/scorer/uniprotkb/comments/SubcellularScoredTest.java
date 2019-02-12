package uk.ebi.uniprot.scorer.uniprotkb.comments;


import org.junit.Test;
import uk.ac.ebi.uniprot.domain.uniprot.comment.CommentType;

public class SubcellularScoredTest extends CommentScoreTestBase {
    @Test
    public void shouldSpScore30()  throws Exception{
        String line = "CC   -!- SUBCELLULAR LOCATION: Mitochondrial matrix.";
        verify(CommentType.SUBCELLULAR_LOCATION, line, 3.0, true);
    }

//
    @Test
    public void shouldSpScore60() throws Exception{
        String line ="CC   -!- SUBCELLULAR LOCATION: Integral membrane protein. Inner membrane.";
        verify(CommentType.SUBCELLULAR_LOCATION, line, 6.0, true);
    }
    @Test
    public void shouldScore10() throws Exception{
        String line = "CC   -!- SUBCELLULAR LOCATION: Cytoplasm.";
        verify(CommentType.SUBCELLULAR_LOCATION, line, 1.0, false);
    }

    @Test
    public void shouldScore20() throws Exception{
        String line = "CC   -!- SUBCELLULAR LOCATION: Spike protein S2: Virion membrane {ECO:0000256|HAMAP-Rule:MF_01146}; Single-\n" +
                "CC       pass type I membrane protein {ECO:0000256|HAMAP-Rule:MF_01146}. Host endoplasmic\n" +
                "CC       reticulum-Golgi intermediate compartment membrane {ECO:0000256|HAMAP-Rule:MF_01146}; Single-pass\n" +
                "CC       type I membrane protein) {ECO:0000256|HAMAP-Rule:MF_01146}. Note=Accumulates in the\n" +
                "CC       endoplasmic reticulum-Golgi intermediate compartment, where it\n" +
                "CC       participates in virus particle assembly. Some S oligomers may be\n" +
                "CC       transported to the plasma membrane, where they may mediate cell -\n" +
                "CC       cell fusion. {ECO:0000256|HAMAP-Rule:MF_01146}.";
        verify(CommentType.SUBCELLULAR_LOCATION, line, 2.0, false);
      
    }

    /**
     * Note only.
     */
    @Test
    public void shouldScore0() throws Exception{
        String line = "CC   -!- SUBCELLULAR LOCATION: Note=Some S oligomers may be transported to\n" +
                "CC       the plasma membrane, where they may mediate cell-cell fusion. S1\n" +
                "CC       is not anchored to the viral envelope, but associates with the\n" +
                "CC       extravirion surface through its binding to S2 (By similarity).";
        verify(CommentType.SUBCELLULAR_LOCATION, line, 0.0, false);

    }

  
}
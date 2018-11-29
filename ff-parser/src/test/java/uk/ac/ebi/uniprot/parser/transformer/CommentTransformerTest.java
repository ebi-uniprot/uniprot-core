package uk.ac.ebi.uniprot.parser.transformer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Ignore;
import org.junit.Test;

import uk.ac.ebi.uniprot.domain.uniprot.EvidencedValue;
import uk.ac.ebi.uniprot.domain.uniprot.comment.Comment;
import uk.ac.ebi.uniprot.domain.uniprot.comment.CommentType;
import uk.ac.ebi.uniprot.domain.uniprot.comment.FreeTextComment;
import uk.ac.ebi.uniprot.parser.ffwriter.FFLineBuilder;
import uk.ac.ebi.uniprot.parser.impl.cc.CCLineBuilderFactory;

public class CommentTransformerTest {
    @Test
    public void testTextOnly1() {
        String tString = "ACTIVITY REGULATION: Inactivated by the serine"
                + " protease inhibitor diisopropylfluorophosphate.";
        testComment(tString, CommentType.ACTIVITY_REGULATION);
    }

    @Test
    public void testTextOnly2() {
        String tString = "SIMILARITY: Belongs to the peptidase S1 family. Granzyme subfamily.";
        testComment(tString, CommentType.SIMILARITY);
    }

    @Test
    public void testTextOnly3() {
        String tString = "PPIases accelerate the folding of proteins.";
        FreeTextComment comment = CommentTransformerHelper.transform(tString,
                CommentType.FUNCTION);
        assertEquals(1, comment.getTexts().size());
        EvidencedValue text = comment.getTexts().get(0);
        assertEquals("PPIases accelerate the folding of proteins",
                text.getValue());
        assertEquals(0, text.getEvidences().size());
    }

    @Test
    public void testTextOnly3Ev() {
        String tString = "PPIases accelerate the folding of proteins (By similarity). {ECO:0000269|PubMed:10051606}.";
        FreeTextComment comment = CommentTransformerHelper.transform(tString,
                CommentType.FUNCTION);
        assertEquals(1, comment.getTexts().size());
        EvidencedValue text = comment.getTexts().get(0);
        assertEquals("PPIases accelerate the folding of proteins (By similarity)",
                text.getValue());
        assertEquals(1, text.getEvidences().size());
        assertEquals("ECO:0000269|PubMed:10051606", text.getEvidences()
                .get(0).getValue());

    }

    @Test
    public void testInteraction() {
        String tString = "INTERACTION:\nP14222:PRF1; NbExp=3; IntAct=EBI-2505785,"
                + " EBI-724466;\nP10124:SRGN; NbExp=2; IntAct=EBI-2505785, EBI-744915;";
        testComment(tString, CommentType.INTERACTION);
    }

    @Test
    public void testSubcellularLocation() {
        String tString = "SUBCELLULAR LOCATION: Cytoplasmic granule. Note=Cytoplasmic "
                + "granules of cytolytic T-lymphocytes and natural killer cells.";
        testComment(tString, CommentType.SUBCELLULAR_LOCATION);
    }

    @Test
    public void testSubcellularLocationWithEvidence() {
        String tString = "SUBCELLULAR LOCATION: Mitochondrion outer membrane {ECO:0000269|PubMed:2250705};"
                + " Single-pass membrane protein {ECO:0000269|PubMed:2250705}. Nucleus membrane"
                + " {ECO:0000269|PubMed:2250705}; Single-pass membrane protein {ECO:0000269|PubMed:2250705}."
                + " Endoplasmic reticulum membrane {ECO:0000269|PubMed:2250705}; Single-pass membrane protein"
                + " {ECO:0000269|PubMed:2250705}.";
        testComment(tString, CommentType.SUBCELLULAR_LOCATION, true);
    }

    @Test
    public void testCofactor() {
        String tString = "COFACTOR:\nName=K(+); Xref=ChEBI:CHEBI:29103; "
                + "Evidence={ECO:0000250};\nNote=Binds 1 potassium ion per subunit.;";
        testComment(tString, CommentType.COFACTOR);
    }

    @Test
    public void testAltProd() {
        String tString = "ALTERNATIVE PRODUCTS:\nEvent=Alternative initiation;"
                + " Named isoforms=2;\nName=Long;\nIsoId=Q10981-1;"
                + " Sequence=Displayed;\nName=Short;\nIsoId=Q10981-2;"
                + " Sequence=VSP_018736;";
        testComment(tString, CommentType.ALTERNATIVE_PRODUCTS);

    }

    @Ignore
    public void testSequenceCaution() {
        String tString = "SEQUENCE CAUTION: Sequence=BAA11638.1;"
                + " Type=Erroneous initiation;";
        testComment(tString, CommentType.SEQUENCE_CAUTION);
    }

    @Test
    public void testBioPhyChem() {
        String tString = "BIOPHYSICOCHEMICAL PROPERTIES:\n"
                + "Kinetic parameters:\nKM=30 uM for acetyl-CoA (at pH 7.5);\n"
                + "KM=57 uM for glyoxylate (at pH 7.5);\n"
                + "pH dependence:\nOptimum pH is 7.5.;";
        testComment(tString, CommentType.BIOPHYSICOCHEMICAL_PROPERTIES);
    }

    @Test
    public void testDisease() {
        String tString = "DISEASE: Breast-ovarian cancer, familial, 1 (BROVCA1) [MIM:604370]:"
                + " A condition associated with familial predisposition to cancer"
                + " of the breast and ovaries. Characteristic features in affected"
                + " families are an early age of onset of breast cancer (often before age 50),"
                + " increased chance of bilateral cancers (cancer that develop in both breasts,"
                + " or both ovaries, independently), frequent occurrence of breast cancer among men,"
                + " increased incidence of tumors of other specific organs, such as the prostate."
                + " Note=Disease susceptibility is associated with variations affecting the gene"
                + " represented in this entry. Mutations in BRCA1 are thought to be responsible"
                + " for more than 80% of inherited breast-ovarian cancer.";
        testComment(tString, CommentType.DISEASE);

    }

    @Test
    public void testMassSpec() {
        String tString = "MASS SPECTROMETRY: Mass=8320; Mass_error=3;"
                + " Method=Electrospray; Range=1-72; Evidence={ECO:0000269|PubMed:8735961};";
        testComment(tString, CommentType.MASS_SPECTROMETRY);

    }

    @Test
    public void testCatalyticActivity() {
    	String ccLine =  "CATALYTIC ACTIVITY:\n"
				+ "Reaction=GDP-beta-L-fucose + NADP(+) = GDP-4-dehydro-alpha-D-"
				+ "rhamnose + H(+) + NADPH; Xref=Rhea:RHEA:18885, ChEBI:CHEBI:57273, "
				+ "ChEBI:CHEBI:58349, ChEBI:CHEBI:57964, ChEBI:CHEBI:57783;"
				+ " EC=1.1.1.271;"
		 + " Evidence={ECO:0000255|HAMAP-Rule:MF_00956, "
					+ "ECO:0000269|PubMed:10480878, ECO:0000269|PubMed:11021971, "
					+ "ECO:0000269|PubMed:9473059};\n"
					+ "PhysiologicalDirection=right-to-left; Xref=Rhea:RHEA:18898; "
					+ "Evidence={ECO:0000255|HAMAP-Rule:MF_00957};\n"
					+ "PhysiologicalDirection=left-to-right; Xref=Rhea:RHEA:18832; "
					+ "Evidence={ECO:0000255|HAMAP-Rule:MF_00952};"
					;
    	  testComment(ccLine, CommentType.CATALYTIC_ACTIVITY);			
    }
    private void testComment(String tString, CommentType type) {
        testComment(tString, type, false);
    }

    private void testComment(String tString, CommentType type, boolean evidence) {
        Comment comment = string2Comment(tString);
        assertNotNull(comment);
        assertEquals(type, comment.getCommentType());
        testCommentString(comment, tString, evidence);
    }

    private void testCommentString(Comment comment, String tString, boolean evidence) {
        FFLineBuilder<Comment> fbuilder = CCLineBuilderFactory
                .create(comment);

        String converted = null;
        if (evidence)
            converted = fbuilder.buildStringWithEvidence(comment);
        else
            converted = fbuilder.buildString(comment);

        assertEquals(tString, converted);
    }

    private Comment string2Comment(String val) {
        int index = val.indexOf(':');
        String type = val.substring(0, index);
        val = val.substring(index + 1).trim();
        CommentType commentType = CommentType.typeOf(type);

        return CommentTransformerHelper.transform(val, commentType);
    }
}

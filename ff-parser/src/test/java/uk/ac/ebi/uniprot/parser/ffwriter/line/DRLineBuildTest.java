package uk.ac.ebi.uniprot.parser.ffwriter.line;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import uk.ac.ebi.uniprot.domain.uniprot.factory.UniProtDBCrossReferenceFactory;
import uk.ac.ebi.uniprot.domain.uniprot.xdb.UniProtDBCrossReference;
import uk.ac.ebi.uniprot.domain.uniprot.xdb.UniProtDBCrossReferences;
import uk.ac.ebi.uniprot.parser.ffwriter.FFLine;
import uk.ac.ebi.uniprot.parser.impl.dr.DRLineBuilder;

public class DRLineBuildTest {
	DRLineBuilder builder = new DRLineBuilder();
	UniProtDBCrossReferenceFactory factory = UniProtDBCrossReferenceFactory.INSTANCE;

	@Test
	public void testWithObsoleteDR() {

		String drLine = "DR   EMBL; U12141; AAA99664.1; -; Genomic_DNA.\n" + "DR   Pfam; PF00534; Glycos_transf_1; 1.\n"
				+
				// "DR CYGD; YNL048w; -.\n" +
				"DR   NMPDR; fig|4932.3.peg.5426; -.";
		List<UniProtDBCrossReference> xrefs = new ArrayList<>();
		xrefs.add(factory.createUniProtDBCrossReference("EMBL", "U12141", "AAA99664.1", "-", "Genomic_DNA"));
		xrefs.add(factory.createUniProtDBCrossReference("PFAM", "PF00534", "Glycos_transf_1", "1"));
		xrefs.add(factory.createUniProtDBCrossReference("NMPDR", "fig|4932.3.peg.5426", "-"));
		UniProtDBCrossReferences uniprotXrefs = factory.createUniProtDBCrossReferences(xrefs);
		FFLine ffLine = builder.build(uniprotXrefs);
		String resultString = ffLine.toString();
		System.out.println(resultString);
		assertEquals(drLine, resultString);

	}

	@Test
	public void test1() {

		String drLine = "DR   EMBL; U12141; AAA99664.1; -; Genomic_DNA.\n"
				+ "DR   EMBL; U62941; AAB49937.1; -; Genomic_DNA.\n"
				+ "DR   EMBL; X94547; CAA64234.1; -; Genomic_DNA.\n"
				+ "DR   EMBL; Z71324; CAA95916.1; -; Genomic_DNA.\n"
				+ "DR   EMBL; BK006947; DAA10497.1; -; Genomic_DNA.\n"
				+ "DR   PIR; S61096; S61096.\n"
				+ "DR   RefSeq; NP_014350.1; NM_001182887.1.\n"
				+ "DR   ProteinModelPortal; P53954; -.\n"
				+ "DR   DIP; DIP-7438N; -.\n"
				+ "DR   IntAct; P53954; 15.\n"
				+ "DR   MINT; MINT-1356407; -.\n"
				+ "DR   STRING; P53954; -.\n" 
				+ "DR   CAZy; GT4; Glycosyltransferase Family 4.\n"
				+ "DR   EnsemblFungi; YNL048W; YNL048W; YNL048W.\n"
				+ "DR   GeneID; 855679; -.\n"
				+ "DR   KEGG; sce:YNL048W; -.\n" 
				+ "DR   SGD; S000004993; ALG11.\n"
				+ "DR   eggNOG; fuNOG07615; -.\n"
				+ "DR   GeneTree; EFGT00050000003720; -.\n"
				+ "DR   HOGENOM; HBG525304; -.\n"
				+ "DR   OMA; VVEYMAS; -.\n"
				+ "DR   OrthoDB; EOG4NS6M8; -.\n"
				+ "DR   PhylomeDB; P53954; -.\n"
				+ "DR   BioCyc; MetaCyc:MONOMER-7187; -.\n" +
				// "DR NextBio; 979975; -.\n" +
				"DR   ExpressionAtlas; P53954; -.\n" +
				// "DR Genevestigator; P53954; -.\n" +
				// "DR GermOnline; YNL048W; Saccharomyces cerevisiae.\n" +
				"DR   GO; GO:0005789; C:endoplasmic reticulum membrane; IDA:SGD.\n"
				+ "DR   GO; GO:0016021; C:integral to membrane; IEA:UniProtKB-KW.\n"
				+ "DR   GO; GO:0000026; F:alpha-1,2-mannosyltransferase activity; IDA:SGD.\n"
				+ "DR   GO; GO:0005515; F:protein binding; IPI:IntAct.\n"
				+ "DR   GO; GO:0006490; P:oligosaccharide-lipid intermediate metabolic process; IDA:SGD.\n"
				+ "DR   InterPro; IPR001296; Glyco_trans_1.\n" 
				+ "DR   Pfam; PF00534; Glycos_transf_1; 1.\n" +
				// "DR CYGD; YNL048w; -.\n" +
				"DR   NMPDR; fig|4932.3.peg.5426; -.";
		List<UniProtDBCrossReference> xrefs = new ArrayList<>();
		xrefs.add(factory.createUniProtDBCrossReference("EMBL", "U12141", "AAA99664.1", "-", "Genomic_DNA"));
		xrefs.add(factory.createUniProtDBCrossReference("EMBL", "U62941", "AAB49937.1", "-", "Genomic_DNA"));
		xrefs.add(factory.createUniProtDBCrossReference("EMBL", "X94547", "CAA64234.1", "-", "Genomic_DNA"));	
		xrefs.add(factory.createUniProtDBCrossReference("EMBL", "Z71324", "CAA95916.1", "-", "Genomic_DNA"));
		xrefs.add(factory.createUniProtDBCrossReference("EMBL", "BK006947", "DAA10497.1", "-", "Genomic_DNA"));	
		xrefs.add(factory.createUniProtDBCrossReference("PIR", "S61096", "S61096"));		
		xrefs.add(factory.createUniProtDBCrossReference("REFSEQ", "NP_014350.1", "NM_001182887.1"));
		xrefs.add(factory.createUniProtDBCrossReference("PROTEINMODELPORTAL", "P53954", "-"));		
		xrefs.add(factory.createUniProtDBCrossReference("DIP", "DIP-7438N", "-"));
		xrefs.add(factory.createUniProtDBCrossReference("INTACT", "P53954", "15"));
		xrefs.add(factory.createUniProtDBCrossReference("MINT", "MINT-1356407", "-"));
		xrefs.add(factory.createUniProtDBCrossReference("STRINGXREF", "P53954", "-"));	
		xrefs.add(factory.createUniProtDBCrossReference("CAZY", "GT4", "Glycosyltransferase Family 4"));
		xrefs.add(factory.createUniProtDBCrossReference("ENSEMBLFUNGI", "YNL048W", "YNL048W", "YNL048W"));
		xrefs.add(factory.createUniProtDBCrossReference("GENEID", "855679", "-"));
		xrefs.add(factory.createUniProtDBCrossReference("KEGG", "sce:YNL048W", "-"));
		xrefs.add(factory.createUniProtDBCrossReference("SGD", "S000004993", "ALG11"));
		xrefs.add(factory.createUniProtDBCrossReference("EGGNOG", "fuNOG07615", "-"));	
		xrefs.add(factory.createUniProtDBCrossReference("GENETREE", "EFGT00050000003720", "-"));
		xrefs.add(factory.createUniProtDBCrossReference("HOGENOM", "HBG525304", "-"));
		xrefs.add(factory.createUniProtDBCrossReference("OMA", "VVEYMAS", "-"));
		xrefs.add(factory.createUniProtDBCrossReference("ORTHODB", "EOG4NS6M8", "-"));
		xrefs.add(factory.createUniProtDBCrossReference("PHYLOMEDB", "P53954", "-"));
		xrefs.add(factory.createUniProtDBCrossReference("BIOCYC", "MetaCyc:MONOMER-7187", "-"));
		
		xrefs.add(factory.createUniProtDBCrossReference("EXPRESSIONATLAS", "P53954", "-"));
		xrefs.add(factory.createUniProtDBCrossReference("GO", "GO:0005789", "C:endoplasmic reticulum membrane", "IDA:SGD"));

		xrefs.add(factory.createUniProtDBCrossReference("GO", "GO:0016021", "C:integral to membrane", "IEA:UniProtKB-KW"));
		xrefs.add(factory.createUniProtDBCrossReference("GO", "GO:0000026", "F:alpha-1,2-mannosyltransferase activity", "IDA:SGD"));
		xrefs.add(factory.createUniProtDBCrossReference("GO", "GO:0005515", "F:protein binding", "IPI:IntAct"));
		xrefs.add(factory.createUniProtDBCrossReference("GO", "GO:0006490", "P:oligosaccharide-lipid intermediate metabolic process", "IDA:SGD"));
		xrefs.add(factory.createUniProtDBCrossReference("INTERPRO", "IPR001296", "Glyco_trans_1"));
		
		xrefs.add(factory.createUniProtDBCrossReference("PFAM", "PF00534", "Glycos_transf_1", "1"));
		xrefs.add(factory.createUniProtDBCrossReference("NMPDR", "fig|4932.3.peg.5426", "-"));
		
		UniProtDBCrossReferences uniprotXrefs = factory.createUniProtDBCrossReferences(xrefs);
		FFLine ffLine = builder.build(uniprotXrefs);
		String resultString = ffLine.toString();
		System.out.println(resultString);
		assertEquals(drLine, resultString);

	}
	
	 @Test
		 public void testWithIsoform() {

		
		 String drLine = "DR   Gene3D; G3DSA:3.20.20.110; RuBisCO_large; 1.\n" +
		 "DR   Gene3D; G3DSA:3.30.70.150; RuBisCO_large; 1.\n" +
		 "DR   HAMAP; MF_03000; private_MF_03000; 1. [P21235-3]\n" +
		 "DR   InterPro; IPR000685; RuBisCO_lsu_C.\n" +
		 "DR   InterPro; IPR017443; RuBisCO_lsu_fd_N.\n" +

		 "DR   Pfam; PF00016; RuBisCO_large; 1.\n" +
		 "DR   Pfam; PF02788; RuBisCO_large_N; 1.\n" +
		 "DR   PROSITE; PS00157; RUBISCO_LARGE; 1. [P21235-2]";
		 
			List<UniProtDBCrossReference> xrefs = new ArrayList<>();
			xrefs.add(factory.createUniProtDBCrossReference("GENE3D", "G3DSA:3.20.20.110", "RuBisCO_large", "1"));
			xrefs.add(factory.createUniProtDBCrossReference("GENE3D", "G3DSA:3.30.70.150", "RuBisCO_large", "1"));
			xrefs.add(factory.createUniProtDBCrossReference("HAMAP", "MF_03000", "private_MF_03000", "1", null, "P21235-3"));
			
			xrefs.add(factory.createUniProtDBCrossReference("INTERPRO", "IPR000685", "RuBisCO_lsu_C"));
			xrefs.add(factory.createUniProtDBCrossReference("INTERPRO", "IPR017443", "RuBisCO_lsu_fd_N"));
			xrefs.add(factory.createUniProtDBCrossReference("PFAM", "PF00016", "RuBisCO_large", "1"));
			xrefs.add(factory.createUniProtDBCrossReference("PFAM", "PF02788", "RuBisCO_large_N", "1"));
			xrefs.add(factory.createUniProtDBCrossReference("PROSITE", "PS00157", "RUBISCO_LARGE", "1", null,"P21235-2"));
			UniProtDBCrossReferences uniprotXrefs = factory.createUniProtDBCrossReferences(xrefs);
			FFLine ffLine = builder.build(uniprotXrefs);
			String resultString = ffLine.toString();
			System.out.println(resultString);
			assertEquals(drLine, resultString);
	 }

}

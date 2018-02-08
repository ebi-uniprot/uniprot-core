package uk.ac.ebi.uniprot.antlr;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import uk.ac.ebi.uniprot.parser.UniprotLineParser;
import uk.ac.ebi.uniprot.parser.impl.DefaultUniprotLineParserFactory;
import uk.ac.ebi.uniprot.parser.impl.dr.DrLineObject;
import uk.ac.ebi.uniprot.parser.impl.dr.DrLineObject.DrObject;

public class DrLineParserTest {
	@Test
	public void testEMBL() {
		 String dfLines = "DR   EMBL; AY548484; AAT09660.1; -; Genomic_DNA.\n"
                 ;
		 UniprotLineParser<DrLineObject> parser = new DefaultUniprotLineParserFactory().createDrLineParser();
		 DrLineObject obj = parser.parse(dfLines);
		 assertEquals(1, obj.drObjects.size());
		 verify(obj.drObjects.get(0), "EMBL", "AY548484", "AAT09660.1", "-", "Genomic_DNA", null);
	}
	private void verify(DrObject obj, String type, String primaryId, String description, String third, String fourth, String isoform) {
		assertEquals(type, obj.DbName);
		assertEquals(isoform, obj.isoform);
		assertEquals(primaryId, obj.attributes.get(0));
		assertEquals(description, obj.attributes.get(1));
		if(third !=null) {
			assertEquals(third, obj.attributes.get(2));
		}
		if(fourth !=null) {
			assertEquals(fourth, obj.attributes.get(3));
		}
	}
	@Test
	public void testMultiDRs() {
		 String dfLines = "DR   EMBL; AY548484; AAT09660.1; -; Genomic_DNA.\n"
				 +"DR   RefSeq; YP_031579.1; NC_005946.1.\n"
				 +"DR   ProteinModelPortal; Q6GZX4; -.\n"
				 +"DR   GeneID; 2947773; -.\n"
				 +"DR   ProtClustDB; CLSP2511514; -.\n"
				 +"DR   GO; GO:0006355; P:regulation of transcription, DNA-dependent; IEA:UniProtKB-KW.\n"
				 +"DR   GO; GO:0046782; P:regulation of viral transcription; IEA:InterPro.\n"
				 +"DR   InterPro; IPR007031; Poxvirus_VLTF3.\n"
				 +"DR   Pfam; PF04947; Pox_VLTF3; 1.\n"
                 ;
		 UniprotLineParser<DrLineObject> parser = new DefaultUniprotLineParserFactory().createDrLineParser();
		 DrLineObject obj = parser.parse(dfLines);
		 assertEquals(9, obj.drObjects.size());
		 verify(obj.drObjects.get(0), "EMBL", "AY548484", "AAT09660.1", "-", "Genomic_DNA", null);
		 verify(obj.drObjects.get(1), "RefSeq", "YP_031579.1", "NC_005946.1", null, null, null);
		 verify(obj.drObjects.get(2), "ProteinModelPortal", "Q6GZX4", "-",  null, null, null);
		 verify(obj.drObjects.get(3), "GeneID", "2947773", "-",  null, null, null);
		 verify(obj.drObjects.get(4), "ProtClustDB", "CLSP2511514", "-",  null, null, null);
		 verify(obj.drObjects.get(5), "GO", "GO:0006355", "P:regulation of transcription, DNA-dependent",  "IEA:UniProtKB-KW", null, null);
		 verify(obj.drObjects.get(6), "GO", "GO:0046782", "P:regulation of viral transcription",  "IEA:InterPro", null, null);
		 verify(obj.drObjects.get(7), "InterPro", "IPR007031", "Poxvirus_VLTF3",  null, null, null);
		 verify(obj.drObjects.get(8), "Pfam", "PF04947", "Pox_VLTF3",  "1", null, null);
	}
	@Test
	public void testWithEvidence() {
		 String dfLines = "DR   EMBL; HF571520; CCQ33941.1; -; Genomic_DNA. {ECO:19841122|Ref.1}\n"
                 ;
		 UniprotLineParser<DrLineObject> parser = new DefaultUniprotLineParserFactory().createDrLineParser();
		 DrLineObject obj = parser.parse(dfLines);
		 assertEquals(1, obj.drObjects.size());
		 verify(obj.drObjects.get(0), "EMBL", "HF571520", "CCQ33941.1", "-", "Genomic_DNA", null);
		 assertEquals("ECO:19841122|Ref.1", obj.evidenceInfo.evidences.get(obj.drObjects.get(0)).get(0));
	}
	@Test
	public void testWithDots() {
		 String dfLines = "DR   EMBL; AY548484; AAT09696.1; -; Genomic_DNA.\n"
				 +"DR   Gene3D; 3.40.50.1000; -; 2.\n"
                 ;
		 UniprotLineParser<DrLineObject> parser = new DefaultUniprotLineParserFactory().createDrLineParser();
		 DrLineObject obj = parser.parse(dfLines);
		 assertEquals(2, obj.drObjects.size());
		 verify(obj.drObjects.get(0), "EMBL", "AY548484", "AAT09696.1", "-", "Genomic_DNA", null);
		 verify(obj.drObjects.get(1), "Gene3D", "3.40.50.1000", "-", "2", null, null);
	}
	@Test
	public void testWithSemicolon() {
		 String dfLines = "DR   Orphanet; 102724; Acute myeloid leukemia with t(8;21)(q22;q22) translocation.\n"
                 ;
		 UniprotLineParser<DrLineObject> parser = new DefaultUniprotLineParserFactory().createDrLineParser();
		 DrLineObject obj = parser.parse(dfLines);
		 assertEquals(1, obj.drObjects.size());
		 verify(obj.drObjects.get(0), "Orphanet", "102724", "Acute myeloid leukemia with t(8;21)(q22;q22) translocation", null, null, null);
	}
	@Test
	public void testWithDots2() {
		 String dfLines = "DR   UCSC; T23F11.3a.1; c. elegans.\n"
				 +"DR   Gene3D; 3.40.50.1000; -; 2.\n"
                 ;
		 UniprotLineParser<DrLineObject> parser = new DefaultUniprotLineParserFactory().createDrLineParser();
		 DrLineObject obj = parser.parse(dfLines);
		 assertEquals(2, obj.drObjects.size());
		 verify(obj.drObjects.get(0), "UCSC", "T23F11.3a.1", "c. elegans", null, null, null);
		 verify(obj.drObjects.get(1), "Gene3D", "3.40.50.1000", "-", "2", null, null);
	}
	@Test
	public void testWithSlash() {
		 String dfLines = "DR   FlyBase; FBgn0013067; Dvir\\Cdc37.\n"
                 ;
		 UniprotLineParser<DrLineObject> parser = new DefaultUniprotLineParserFactory().createDrLineParser();
		 DrLineObject obj = parser.parse(dfLines);
		 assertEquals(1, obj.drObjects.size());
		 verify(obj.drObjects.get(0), "FlyBase", "FBgn0013067", "Dvir\\Cdc37", null, null, null);
	}
	@Test
	public void testWithDash() {
		 String dfLines = "DR   Orphanet; 99880; Hyperparathyroidism - jaw tumor syndrome.\n"
                 ;
		 UniprotLineParser<DrLineObject> parser = new DefaultUniprotLineParserFactory().createDrLineParser();
		 DrLineObject obj = parser.parse(dfLines);
		 assertEquals(1, obj.drObjects.size());
		 verify(obj.drObjects.get(0), "Orphanet", "99880", "Hyperparathyroidism - jaw tumor syndrome", null, null, null);
	}
	@Test
	public void testWithCurlyBracket() {
		 String dfLines = "DR   GO; GO:0033942; F:4-alpha-D-{(1->4)-alpha-D-glucano}trehalose trehalohydrolase activity; IEA:UniProtKB-EC.\n"
                 ;
		 UniprotLineParser<DrLineObject> parser = new DefaultUniprotLineParserFactory().createDrLineParser();
		 DrLineObject obj = parser.parse(dfLines);
		 assertEquals(1, obj.drObjects.size());
		 verify(obj.drObjects.get(0), "GO", "GO:0033942", "F:4-alpha-D-{(1->4)-alpha-D-glucano}trehalose trehalohydrolase activity", "IEA:UniProtKB-EC", null, null);
	}
	
	@Test
	public void testWithStarStar() {
		 String dfLines = "DR   PROSITE; PS51165; THUMP; 1.\n"
				 +"DR   PROSITE; PS01261; UPF0020; 1.\n"
				 +"**   PROSITE; PS00092; N6_MTASE; FALSE_POS_1.\n"
				 +"**   PROSITE; PS00092; N6_MTASE; FALSE_POS_2.\n"
                 ;
		 UniprotLineParser<DrLineObject> parser = new DefaultUniprotLineParserFactory().createDrLineParser();
		 DrLineObject obj = parser.parse(dfLines);
		 assertEquals(4, obj.drObjects.size());
		 verify(obj.drObjects.get(0), "PROSITE", "PS51165", "THUMP", "1", null, null);
		 verify(obj.drObjects.get(1), "PROSITE", "PS01261", "UPF0020", "1", null, null);
		 assertEquals("PS00092; N6_MTASE; FALSE_POS_1.", obj.drObjects.get(2).ssLineValue);
		 assertEquals("PS00092; N6_MTASE; FALSE_POS_2.", obj.drObjects.get(3).ssLineValue);
	}
	@Test
	public void testWithIsoforms() {
		 String dfLines = "DR   PRIDE; P19802; -.\n"
				 +"DR   PRIDE; P19803; -. [P19802-2]\n"
				 +"DR   PROSITE; PS00157; RUBISCO_LARGE; 1. [P21235-2]\n"
				 +"DR   PROSITE; PS00158; RUBISCO_LARGE; 1. [P21235-3]{ECO:19841122|Ref.1}\n"
                 ;
		 UniprotLineParser<DrLineObject> parser = new DefaultUniprotLineParserFactory().createDrLineParser();
		 DrLineObject obj = parser.parse(dfLines);
		 assertEquals(4, obj.drObjects.size());
		 verify(obj.drObjects.get(0), "PRIDE", "P19802", "-", null, null, null);
		 verify(obj.drObjects.get(1), "PRIDE", "P19803", "-", null, null, "P19802-2");
		 verify(obj.drObjects.get(2), "PROSITE", "PS00157", "RUBISCO_LARGE", "1", null, "P21235-2");
		 verify(obj.drObjects.get(3), "PROSITE", "PS00158", "RUBISCO_LARGE", "1", null, "P21235-3");
		 assertEquals("ECO:19841122|Ref.1", obj.evidenceInfo.evidences.get(obj.drObjects.get(3)).get(0));
	}
}

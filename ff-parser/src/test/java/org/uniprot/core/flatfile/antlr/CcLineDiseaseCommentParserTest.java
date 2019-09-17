package org.uniprot.core.flatfile.antlr;

import org.junit.jupiter.api.Test;
import org.uniprot.core.flatfile.parser.UniprotLineParser;
import org.uniprot.core.flatfile.parser.impl.DefaultUniprotLineParserFactory;
import org.uniprot.core.flatfile.parser.impl.cc.CcLineFormater;
import org.uniprot.core.flatfile.parser.impl.cc.CcLineObject;

import static org.junit.jupiter.api.Assertions.*;

class CcLineDiseaseCommentParserTest {
	@Test
	void test() {
			String lines = "CC   -!- DISEASE: Acyl-CoA dehydrogenase family, member 9, deficiency\n"
					+"CC       (ACAD9 deficiency) [MIM:611126]: A metabolic disorder with\n"
					+"CC       variable manifestations that include dilated cardiomyopathy, liver\n"
					+"CC       failure, muscle weakness, neurologic dysfunction, hypoglycemia and\n"
					+"CC       Reye-like episodes (brain edema and vomiting that may rapidly\n"
					+"CC       progress to seizures, coma and death). Note=The disease is caused\n"
					+"CC       by mutations affecting the gene represented in this entry.\n"
					;
			UniprotLineParser<CcLineObject> parser = new DefaultUniprotLineParserFactory().createCcLineParser();
			CcLineObject obj =parser.parse(lines);
			assertEquals(1, obj.ccs.size());
			CcLineObject.CC cc = obj.ccs.get(0);
			assertTrue(cc.object instanceof CcLineObject.Disease);
			CcLineObject.Disease disease = (CcLineObject.Disease)cc.object;
			assertEquals("ACAD9 deficiency", disease.abbr);
			assertEquals("611126", disease.mim);
			assertEquals("Acyl-CoA dehydrogenase family, member 9, deficiency", disease.name);
			assertEquals("A metabolic disorder with"+
	      " variable manifestations that include dilated cardiomyopathy, liver"+
	      " failure, muscle weakness, neurologic dysfunction, hypoglycemia and"+
	      " Reye-like episodes (brain edema and vomiting that may rapidly"+
	      " progress to seizures, coma and death)", disease.description);
	}

	@Test
	void testWithEvidence() {
			String lines = "CC   -!- DISEASE: Colorectal cancer (CRC) [MIM:114500]: A complex disease\n"
					+"CC       characterized by malignant lesions arising from the inner wall of\n"
					+"CC       the large intestine (the colon) and the rectum. Genetic\n"
					+"CC       alterations are often associated with progression from\n"
					+"CC       premalignant lesion (adenoma) to invasive adenocarcinoma. Risk\n"
					+"CC       factors for cancer of the colon and rectum include colon polyps,\n"
					+"CC       long-standing ulcerative colitis, and genetic family history.\n"
					+"CC       {ECO:0000313|EMBL:BAG16761.1, ECO:0000269|PubMed:10433554,\n"
					+"CC       ECO:0000303|Ref.6}. Note=The gene represented in this entry is\n"
					+"CC       involved in disease pathogenesis. {ECO:0000303|Ref.6,\n"
					+"CC       ECO:0000313|PDB:3OW2, ECO:0000256|HAMAP-Rule:MF_00205}.\n"
					;
			UniprotLineParser<CcLineObject> parser = new DefaultUniprotLineParserFactory().createCcLineParser();
			CcLineObject obj =parser.parse(lines);
			assertEquals(1, obj.ccs.size());
			CcLineObject.CC cc = obj.ccs.get(0);
			assertTrue(cc.object instanceof CcLineObject.Disease);
			CcLineObject.Disease disease = (CcLineObject.Disease)cc.object;
			assertEquals("CRC", disease.abbr);
			assertEquals("114500", disease.mim);
			assertEquals("Colorectal cancer", disease.name);
			assertNotNull(disease.description);
			
			assertEquals(1, disease.note.size());
			assertEquals("The gene represented in this entry is involved in disease pathogenesis", disease.note.get(0).value);
			assertEquals("ECO:0000303|Ref.6", disease.note.get(0).evidences.get(0));
			assertEquals("ECO:0000313|PDB:3OW2", disease.note.get(0).evidences.get(1));
			assertEquals("ECO:0000256|HAMAP-Rule:MF_00205", disease.note.get(0).evidences.get(2));
			
			assertEquals("ECO:0000313|EMBL:BAG16761.1", obj.evidenceInfo.evidences.get(disease.description).get(0));
			assertEquals("ECO:0000269|PubMed:10433554", obj.evidenceInfo.evidences.get(disease.description).get(1));
			assertEquals("ECO:0000303|Ref.6", obj.evidenceInfo.evidences.get(disease.description).get(2));
	}
	
	@Test
	void testNoHeaderWithEvidence() {

		String ccLineStringEvidence=
				"DISEASE: Colorectal cancer (CRC) [MIM:114500]: A complex disease\n"
						+"characterized by malignant lesions arising from the inner wall of\n"
						+"the large intestine (the colon) and the rectum. Genetic\n"
						+"alterations are often associated with progression from\n"
						+"premalignant lesion (adenoma) to invasive adenocarcinoma. Risk\n"
						+"factors for cancer of the colon and rectum include colon polyps,\n"
						+"long-standing ulcerative colitis, and genetic family history.\n"
						+"{ECO:0000313|EMBL:BAG16761.1, ECO:0000269|PubMed:10433554,\n"
						+"ECO:0000303|Ref.6}. Note=The gene represented in this entry is\n"
						+"involved in disease pathogenesis. {ECO:0000303|Ref.6,\n"
						+"ECO:0000313|PDB:3OW2, ECO:0000256|HAMAP-Rule:MF_00205}.\n"
						;
			CcLineFormater formater  =new CcLineFormater();
			UniprotLineParser<CcLineObject> parser = new DefaultUniprotLineParserFactory().createCcLineParser();
			String lines = formater.format(ccLineStringEvidence);
			CcLineObject obj = parser.parse(lines);
			assertNotNull(obj);
	}
	
	@Test
	void testNoHeader2() {

		String ccLineStringEvidence=
				("DISEASE: Colorectal cancer (CRC) [MIM:114500]: A complex disease " +
						"characterized by malignant lesions arising from the inner wall of " +
						"the large intestine (the colon) and the rectum. Genetic " +
						"alterations are often associated with progression from " +
						"premalignant lesion (adenoma) to invasive adenocarcinoma. Risk " +
						"factors for cancer of the colon and rectum include colon polyps, " +
						"long-standing ulcerative colitis, and genetic family history. " +
						"Note=The gene represented in this " +
						"entry is involved in disease pathogenesis. Another note.");
			CcLineFormater formater  =new CcLineFormater();
			UniprotLineParser<CcLineObject> parser = new DefaultUniprotLineParserFactory().createCcLineParser();
			String lines = formater.format(ccLineStringEvidence);
			CcLineObject obj = parser.parse(lines);
			assertNotNull(obj);
	}
	@Test
	void testNoHeader3() {
		String ccLineStringEvidence =("DISEASE: Colorectal cancer (CRC) [MIM:114500]: A complex disease " +
				"characterized by malignant lesions arising from the inner wall of " +
				"the large intestine (the colon) and the rectum. Genetic " +
				"alterations are often associated with progression from " +
				"premalignant lesion (adenoma) to invasive adenocarcinoma. Risk " +
				"factors for cancer of the colon and rectum include colon polyps, " +
				"long-standing ulcerative colitis, and genetic family history. " +
				"{ECO:0000269|PubMed:10433554, ECO:0000303|Ref.6, " +
				"ECO:0000313|EMBL:BAG16761.1}. Note=The gene represented in this " +
				"entry is involved in disease pathogenesis. {ECO:0000256|HAMAP-" +
				"Rule:MF_00205, ECO:0000303|Ref.6, ECO:0000313|PDB:3OW2}. Another" +
				" note. {ECO:0000256|HAMAP-Rule:MF_00205, ECO:0000303|Ref.6}.");
			CcLineFormater formater  =new CcLineFormater();
			UniprotLineParser<CcLineObject> parser = new DefaultUniprotLineParserFactory().createCcLineParser();
			String lines = formater.format(ccLineStringEvidence);
			CcLineObject obj = parser.parse(lines);
			assertNotNull(obj);
	}
	
	@Test
	void testNoHeader4() {
		String ccLineStringEvidence = "DISEASE: Juvenile polyposis/hereditary hemorrhagic telangiectasia syndrome (JP/HHT) [MIM:175050]:"
	              + " JP/HHT syndrome phenotype consists of the coexistence of juvenile polyposis (JIP) and hereditary"
	              + " hemorrhagic telangiectasia (HHT) [MIM:187300] in a single individual. JIP and HHT are autosomal"
	              + " dominant disorders with distinct and non-overlapping clinical features. The former, an inherited"
	              + " gastrointestinal malignancy predisposition, is caused by mutations in SMAD4 or BMPR1A, and the latter"
	              + " is a vascular malformation disorder caused by mutations in ENG or ACVRL1. All four genes encode proteins"
	              + " involved in the transforming-growth-factor-signaling pathway. Although there are reports of patients and"
	              + " families with phenotypes of both disorders combined, the genetic etiology of this association is unknown. {ECO:0000269|PubMed:15031030}."
	              + " Note=The disease is caused by mutations affecting the gene represented in this entry.";
		
			CcLineFormater formater  =new CcLineFormater();
			UniprotLineParser<CcLineObject> parser = new DefaultUniprotLineParserFactory().createCcLineParser();
			String lines = formater.format(ccLineStringEvidence);
			CcLineObject obj = parser.parse(lines);
			assertNotNull(obj);
	}

}

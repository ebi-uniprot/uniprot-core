package uk.ac.ebi.uniprot.antlr;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import uk.ac.ebi.uniprot.parser.UniprotLineParser;
import uk.ac.ebi.uniprot.parser.impl.DefaultUniprotLineParserFactory;
import uk.ac.ebi.uniprot.parser.impl.cc.CcLineObject;

public class CcLineDiseaseCommentParserTest {
	@Test
	public void test() {
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
	public void testWithEvidence() {
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
	
	
}

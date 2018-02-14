package uk.ac.ebi.uniprot.antlr;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import uk.ac.ebi.uniprot.parser.impl.cc.CcLineFormater;

public class CcLineFormaterTest {
	@Test
	public void testInteraction() {
		String expected = "CC   -!- INTERACTION:\n"
				+ "CC       Q9W4W2:fs(1)Yb; NbExp=4; IntAct=EBI-2890374, EBI-3424083;\n"
				+ "CC       Q9VKM1:piwi; NbExp=4; IntAct=EBI-2890374, EBI-3406276;\n";
		String lines = "INTERACTION:\n"
				+ "Q9W4W2:fs(1)Yb; NbExp=4; IntAct=EBI-2890374, EBI-3424083;\n"
				+ "Q9VKM1:piwi; NbExp=4; IntAct=EBI-2890374, EBI-3406276;\n";
		CcLineFormater formater = new CcLineFormater();
		
		String formated = formater.format(lines);
		assertEquals(expected, formated);
	}
	@Test
	public void testAPComment() {
		String expected =  "CC   -!- ALTERNATIVE PRODUCTS:\n"
						  +"CC       Event=Alternative splicing; Named isoforms=6;\n"
						  +"CC         Comment=Additional isoforms seem to exist.\n"
		                    +"CC         {ECO:0000269|PubMed:10433554, ECO:0000303|Ref.6};\n"
		                    +"CC       Name=1 {ECO:0000313|EMBL:BAG16761.1}; Synonyms=A\n"
		                    +"CC       {ECO:0000256|HAMAP-Rule:MF_002051, ECO:0000313|PDB:3OW2};\n"
		                    +"CC         IsoId=Q9V8R9-1; Sequence=Displayed;\n"
		                    +"CC         Note=Does not exhibit APOBEC1 complementation activity. Ref.4\n"
		                    +"CC         sequence is in conflict in positions: 33:I->T. No experimental\n"
		                    +"CC         confirmation available. {ECO:0000313|PDB:3OW2};\n"
		                    +"CC       Name=2;\n"
		                    +"CC         IsoId=Q9V8R9-2; Sequence=VSP_000476, VSP_000477, VSP_000479,\n"
		                    +"CC                                  VSP_000480, VSP_000481;\n"
		                    +"CC       Name=Bim-alpha3 {ECO:0000256|HAMAP-Rule:MF_00205,\n"
		                    +"CC       ECO:0000313|PDB:3OW2}; Synonyms=BCL2-like 11 transcript variant 10\n"
		                    +"CC       {ECO:0000313|EMBL:BAG16761.1}, Bim-AD\n"
		                    +"CC       {ECO:0000256|HAMAP-Rule:MF_00205}, BimAD {ECO:0000313|PDB:3OW2};\n"
		                    +"CC         IsoId=Q9V8R9-3; Sequence=VSP_000475, VSP_000478, VSP_000479;\n"
		                    +"CC       Name=4; Synonyms=B;\n"
		                    +"CC         IsoId=Q9V8R9-4; Sequence=VSP_000476, VSP_000477, VSP_000479;\n"
		                    +"CC       Name=5;\n"
		                    +"CC         IsoId=Q9V8R9-5; Sequence=VSP_000474, VSP_000478;\n"
		                    +"CC         Note=No experimental confirmation available.\n"
		                    +"CC         {ECO:0000269|PubMed:10433554, ECO:0000313|EMBL:BAG16761.1};\n"
		                    +"CC       Name=6; Synonyms=D;\n"
		                    +"CC         IsoId=Q9V8R9-6; Sequence=Described;\n"
		                    +"CC         Note=No experimental confirmation.;\n"
						;
		String lines =   "ALTERNATIVE PRODUCTS:\n"
				  +"Event=Alternative splicing; Named isoforms=6;\n"
				  +"  Comment=Additional isoforms seem to exist.\n"
                  +"{ECO:0000269|PubMed:10433554, ECO:0000303|Ref.6};\n"
                  +"Name=1 {ECO:0000313|EMBL:BAG16761.1}; Synonyms=A\n"
                  +"ECO:0000256|HAMAP-Rule:MF_002051, ECO:0000313|PDB:3OW2};\n"
                  +"IsoId=Q9V8R9-1; Sequence=Displayed;\n"
                  +"Note=Does not exhibit APOBEC1 complementation activity. Ref.4\n"
                  +"sequence is in conflict in positions: 33:I->T. No experimental\n"
                  +"confirmation available. {ECO:0000313|PDB:3OW2};\n"
                  +"Name=2;\n"
                  +" IsoId=Q9V8R9-2; Sequence=VSP_000476, VSP_000477, VSP_000479,\n"
                  +"CC                                  VSP_000480, VSP_000481;\n"
                  +"CC       Name=Bim-alpha3 {ECO:0000256|HAMAP-Rule:MF_00205,\n"
                  +"CC       ECO:0000313|PDB:3OW2}; Synonyms=BCL2-like 11 transcript variant 10\n"
                  +"CC       {ECO:0000313|EMBL:BAG16761.1}, Bim-AD\n"
                  +"CC       {ECO:0000256|HAMAP-Rule:MF_00205}, BimAD {ECO:0000313|PDB:3OW2};\n"
                  +"CC         IsoId=Q9V8R9-3; Sequence=VSP_000475, VSP_000478, VSP_000479;\n"
                  +"CC       Name=4; Synonyms=B;\n"
                  +"CC         IsoId=Q9V8R9-4; Sequence=VSP_000476, VSP_000477, VSP_000479;\n"
                  +"CC       Name=5;\n"
                  +"CC         IsoId=Q9V8R9-5; Sequence=VSP_000474, VSP_000478;\n"
                  +"CC         Note=No experimental confirmation available.\n"
                  +"CC         {ECO:0000269|PubMed:10433554, ECO:0000313|EMBL:BAG16761.1};\n"
                  +"CC       Name=6; Synonyms=D;\n"
                  +"CC         IsoId=Q9V8R9-6; Sequence=Described;\n"
                  +"CC         Note=No experimental confirmation.;\n"
				;
		CcLineFormater formater = new CcLineFormater();
		
		String formated = formater.format(lines);
		assertEquals(expected, formated);
	}
}

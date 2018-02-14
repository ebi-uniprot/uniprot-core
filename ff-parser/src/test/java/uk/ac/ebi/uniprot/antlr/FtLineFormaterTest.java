package uk.ac.ebi.uniprot.antlr;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import uk.ac.ebi.uniprot.parser.impl.ft.FtLineFormater;

public class FtLineFormaterTest {
	@Test
	public void test() {
		 String expected = "FT   CHAIN        20    873       104 kDa microneme/rhoptry antigen.\n"
				 +"FT                                /FTId=PRO_0000232680.\n"
                 ;
		 String lines = "CHAIN        20    873       104 kDa microneme/rhoptry antigen.\n"
				 +"/FTId=PRO_0000232680.\n"
                 ;
		 verify(expected, lines);
	}
	void verify(String expected, String lines) {
		FtLineFormater formater = new FtLineFormater();

		String formated = formater.format(lines);
		assertEquals(expected, formated);
	}

	@Test
	public void test2() {
	 String expected = "FT   VAR_SEQ      33     83       TPDINPAWYTGRGIRPVGRFGRRRATPRDVTGLGQLSCLPL\n"
			 +"FT                                -> SECLTYGKQPLTSFHPFTSQMPP (in\n"
			 +"FT                                isoform 2).\n"
			 +"FT                                /FTId=VSP_004370.\n"
            ;
	 String lines = "VAR_SEQ      33     83       TPDINPAWYTGRGIRPVGRFGRRRATPRDVTGLGQLSCLPL\n"
			 +"-> SECLTYGKQPLTSFHPFTSQMPP (in\n"
			 +"isoform 2).\n"
			 +"/FTId=VSP_004370.\n"
             ;
		 verify(expected, lines);
	}
	
}

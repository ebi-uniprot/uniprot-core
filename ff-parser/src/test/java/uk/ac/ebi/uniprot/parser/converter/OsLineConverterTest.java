package uk.ac.ebi.uniprot.parser.converter;

import junit.framework.TestCase;

import org.junit.Test;

import uk.ac.ebi.uniprot.domain.uniprot.OrganismName;
import uk.ac.ebi.uniprot.parser.impl.os.OsLineConverter;
import uk.ac.ebi.uniprot.parser.impl.os.OsLineObject;

public class OsLineConverterTest {
	@Test
	public void test(){
		//"OS   Solanum melongena (Eggplant) (Aubergine).
		OsLineObject osO = new OsLineObject();
		osO.organism_species ="Solanum melongena (Eggplant) (Aubergine)";
		OsLineConverter converter = new OsLineConverter();
		OrganismName org =converter.convert(osO);
		TestCase.assertEquals("Solanum melongena", org.getScientificName());
		TestCase.assertEquals("Eggplant", org.getCommonName());
		TestCase.assertEquals("Aubergine", org.getSynonyms().get(0));
		
	}
	
	
	@Test
	public void test2(){
		//OS   Homo sapiens (Human).
		OsLineObject osO = new OsLineObject();
		osO.organism_species ="Homo sapiens (Human)";
		OsLineConverter converter = new OsLineConverter();
		OrganismName org =converter.convert(osO);
		TestCase.assertEquals("Homo sapiens", org.getScientificName());
		TestCase.assertEquals("Human", org.getCommonName());
		TestCase.assertEquals(0, org.getSynonyms().size());
		
		
	}
	
}

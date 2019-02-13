package uk.ac.ebi.uniprot.flatfile.parser.converter;

import org.junit.Test;
import uk.ac.ebi.uniprot.flatfile.parser.impl.oc.OcLineConverter;
import uk.ac.ebi.uniprot.flatfile.parser.impl.oc.OcLineObject;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class OcLineConverterTest {
	@Test
	public void test(){
	//"OC   Eukaryota; Metazoa; Chordata; Craniata; Vertebrata; Euteleostomi.\n"
		OcLineObject obj = new OcLineObject();
		obj.nodes.add("Eukaryota");
		obj.nodes.add("Metazoa");
		obj.nodes.add("Chordata");
		obj.nodes.add("Craniata");
		obj.nodes.add("Vertebrata");
		obj.nodes.add("Euteleostomi");
		OcLineConverter converter = new OcLineConverter();
		 List<String>  taxons = converter.convert(obj);
		 assertEquals(6, taxons.size());
		 validate("Eukaryota", taxons, 0);
		 validate("Metazoa", taxons, 1);
		 validate("Chordata", taxons, 2);
		 validate("Craniata", taxons, 3);
		 validate("Vertebrata", taxons, 4);
		 validate("Euteleostomi", taxons, 5);
		 
		 
	}
	private void validate(String val,List<String>  taxons, int pos ){
		assertEquals(val, taxons.get(pos));
	}
}

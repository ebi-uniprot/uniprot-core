package uk.ac.ebi.uniprot.cv.impl;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.net.URL;

import org.junit.jupiter.api.Test;

import uk.ac.ebi.uniprot.cv.subcell.SubcellularLocation;
import uk.ac.ebi.uniprot.cv.subcell.impl.SubcellularLocationServiceImpl;

public class SubcellularLocationServiceImplTest {
	@Test
	void test() {
		String file ="src/test/resources/subcell.txt";
		SubcellularLocationServiceImpl service = new SubcellularLocationServiceImpl(file);
		String id ="Acrosome";
		SubcellularLocation location  = service.getById(id);
		
		assertNotNull(location);
		
		assertThat( location.getAccession(), is("SL-0007"));
		
	}
}

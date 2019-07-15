package uk.ac.ebi.uniprot.cv.impl;

import org.junit.jupiter.api.Test;
import uk.ac.ebi.uniprot.cv.subcell.SubcellularLocationEntry;
import uk.ac.ebi.uniprot.cv.subcell.impl.SubcellularLocationServiceImpl;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

public class SubcellularLocationServiceImplTest {
	@Test
	void test() {
		String file ="src/test/resources/subcell.txt";
		SubcellularLocationServiceImpl service = new SubcellularLocationServiceImpl(file);
		String id ="Acrosome";
		SubcellularLocationEntry location = service.getById(id);
		
		assertNotNull(location);
		
		assertThat( location.getAccession(), is("SL-0007"));
		
	}
}

package org.uniprot.core.xml;

import static org.junit.jupiter.api.Assertions.*;

import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import org.junit.jupiter.api.Test;
import org.uniprot.core.xml.XmlChainIterator;
import org.uniprot.core.xml.jaxb.proteome.Proteome;

/**
 *
 * @author jluo
 * @date: 11 Jun 2019
 *
*/

class XmlChainIteratorTest {
	 static final String PROTEOME_ROOT_ELEMENT = "proteome";
	@Test
	void testProteomeXmlReader() {
		String file = "/proteome/proteome_example.xml";
		InputStream is = XmlChainIteratorTest.class.getResourceAsStream(file);
		
		assertNotNull(is);
		
		List<InputStream> iss = Arrays.asList(is);
		
		 XmlChainIterator<Proteome, Proteome>  chainingIterators =
	        		new XmlChainIterator<>(iss.iterator(),
	        				Proteome.class, PROTEOME_ROOT_ELEMENT, Function.identity() );
		 int count =0;
		 while(chainingIterators.hasNext()) {
			 Proteome proteome =chainingIterators.next();
			 count++;
			
		 }
		 assertTrue(count>0);
	}

}


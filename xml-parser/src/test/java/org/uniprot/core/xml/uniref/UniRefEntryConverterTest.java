package org.uniprot.core.xml.uniref;

import static org.junit.jupiter.api.Assertions.*;

import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniref.UniRefEntry;
import org.uniprot.core.xml.XmlChainIterator;
import org.uniprot.core.xml.jaxb.uniref.Entry;

/**
 *
 * @author jluo
 * @date: 13 Aug 2019
 *
*/

class UniRefEntryConverterTest {
	static final String UNIREF_ROOT_ELEMENT = "entry";
	@Test
	void testFromXml() throws Exception{
		String file = "/uniref/50_Q9EPS7_Q95604.xml";
		InputStream is = UniRefEntryConverterTest.class.getResourceAsStream(file);
		
		assertNotNull(is);
		
		List<InputStream> iss = Arrays.asList(is);
		
		 XmlChainIterator<Entry, Entry>  chainingIterators =
	        		new XmlChainIterator<>(iss.iterator(),
	        				Entry.class, UNIREF_ROOT_ELEMENT, Function.identity() );
		 int count =0;
		 while(chainingIterators.hasNext()) {
			 Entry entry =chainingIterators.next();
			 rountripTest(entry);
			 count++;
			
		 }
		 is.close();
		assertEquals(2, count);
	}
	private void rountripTest(Entry xmlEntry) {
		UniRefEntryConverter converter = new UniRefEntryConverter();
		UniRefEntry entry = converter.fromXml(xmlEntry);
		Entry converted = converter.toXml(entry);
		UniRefEntry converted2 = converter.fromXml(converted);
		assertEquals(entry, converted2);
		
	}
}


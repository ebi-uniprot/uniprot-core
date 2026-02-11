package org.uniprot.core.flatfile.parser.impl.aaentry;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprotkb.UniProtKBEntry;

/**
 *
 * @author jluo
 * @date: 30 Jan 2026
 *
*/

class AAUniProtEntryIteratorTest {

	 private  AAUniProtEntryIterator entryIterator;

	    @BeforeEach
	    void setUp() {
	        entryIterator = new  AAUniProtEntryIterator();
	    }
	    
	@Test
	void test() {
		 String filename = "src/test/resources/aaentry/aadat.txt";
         entryIterator.setInput(filename, "", "", "", "");
         List<String> expected = List.of(
        		 "A0ABM6UB92",
        		 "A0ABM6UD37",
        		 "A0ABM6UN56",
        		 "A0A009HMB5",
        		 "A0A009HMZ2",
        		 "A0A009HN62"	 
        		 );
         List<String> result = new ArrayList<>();
         while (entryIterator.hasNext()) {
        	 UniProtKBEntry entry = entryIterator.next();
        	 result.add(entry.getPrimaryAccession().getValue());
         }
         assertEquals(expected, result);
	}

}


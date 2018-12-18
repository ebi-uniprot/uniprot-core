package uk.ac.ebi.uniprot.parser.voldemort;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import uk.ac.ebi.uniprot.domain.uniprot.UniProtEntry;
import uk.ac.ebi.uniprot.parser.impl.DefaultUniProtEntryIterator;

public class NewModelIT {
	  private static List<String> savedAccessions;
	  private static Map<String, UniProtEntry > entryMap = new HashMap<>();
	    private static NewUniProtInMemoryStore voldemortInMemoryEntryStore;
	    private static final String storeName = "json-uniprot";

	    @BeforeAll
	    public static void loadData() throws Exception{
	        URL resourcePath = NewModelIT.class.getClassLoader().getResource("entryIT/sp.dat");
	        assert resourcePath != null;
	     
	        DefaultUniProtEntryIterator iterator = new DefaultUniProtEntryIterator();
	        iterator.setInput(resourcePath.getPath(), "", "");
	        int count =0;
	        int start=0;
	        voldemortInMemoryEntryStore =new NewUniProtInMemoryStore(storeName);
	        savedAccessions = new ArrayList<>();
	        while (iterator.hasNext()) {
	            UniProtEntry next = iterator.next();
	            start++;
	            if(start<=1)
	            	continue;
	            voldemortInMemoryEntryStore.saveEntry(next);
	            savedAccessions.add(next.getPrimaryAccession().getValue());
	            entryMap.put(next.getPrimaryAccession().getValue(), next);
	            count++;
	            if(count ==10000)
	            	break;
	        }
	    }

	    @Disabled
	    @Test
	    public void testStoreName() throws Exception {
	        assertThat(savedAccessions,notNullValue());
	   //     assertThat(savedAccessions.size(),is());
	        assertThat(voldemortInMemoryEntryStore.getStoreName(),notNullValue());
	        assertThat(voldemortInMemoryEntryStore.getStoreName(),is(storeName));
	    }

//	    @Test
//	    void getEntryP55301() {
//	    	String accession ="P55301";
//	    	 Optional<UniProtEntry> entry = voldemortInMemoryEntryStore.getEntry(accession);
//	    	 assertTrue(entry.isPresent());
//	    	 assertEquals(entry.get(), entryMap.get(accession));
//	    }
	    @Disabled
	    @Test
	    public void testSavedEntries() throws Exception {
	        assertThat(savedAccessions,notNullValue());
	        List<String> failedAccessions = new ArrayList<>();
	        for(String accession: savedAccessions) {
	        	   Optional<UniProtEntry> entry = voldemortInMemoryEntryStore.getEntry(accession);
	        	   assertTrue(entry.isPresent());
	        	   UniProtEntry uEntry = entry.get();
	        	   if(!uEntry.equals(entryMap.get(accession))) {
	        		   failedAccessions.add(accession);
	        		   UniProtEntry uEntry2 = entryMap.get(accession);
	        		   uEntry.equals(uEntry2);
	        		
	        	   }
	        		   
	        }
	        System.out.println(failedAccessions);
	        assertTrue(failedAccessions.isEmpty());
	    }
	    
}

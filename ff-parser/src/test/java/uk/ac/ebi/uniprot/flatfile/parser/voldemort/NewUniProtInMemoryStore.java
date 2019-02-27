package uk.ac.ebi.uniprot.flatfile.parser.voldemort;

import uk.ac.ebi.uniprot.domain.uniprot.UniProtEntry;
import voldemort.store.StorageEngine;
import voldemort.store.Store;
import voldemort.store.memory.InMemoryStorageEngine;
import voldemort.versioning.Versioned;

import java.util.*;

public class NewUniProtInMemoryStore {
	   private final String storeName;
	
	    private StorageEngine<String,String,String> storageEngine;

	    public NewUniProtInMemoryStore(String storeName) {
	    	this.storeName = storeName;
	        this.storageEngine = new InMemoryStorageEngine<>(storeName);
	    }

	    public Store<String, String,String> getStore() {
	        return this.storageEngine;
	    }

	    public void saveEntry(UniProtEntry entry) {
	        String id = getStoreId(entry);
	       System.out.println("Saving entry: " + id );
	        

	        Store<String, String,String> store = this.getStore();
	        Versioned<String> versionedEntry = new Versioned<>(getString(entry));
	        store.put(id,versionedEntry,storeName);
	    }

	    public String getStoreId(UniProtEntry entry) {
	        return entry.getPrimaryAccession().getValue();
	    }
	    private String getString(UniProtEntry entry) {
	    	return null;//JsonUtils.getJsonString(entry, false);
	    }
	    public void updateEntry(UniProtEntry entry) {
	        String id = getStoreId(entry);
	        System.out.println("Updating entry: " + id );
	        Store<String, String,String> store = this.getStore();
	        List<Versioned<String>> listVersionedEntry = store.get(id,this.storeName);
	        if(listVersionedEntry != null && !listVersionedEntry.isEmpty()){
	            Versioned<String> currentEntry = listVersionedEntry.get(0);
	            currentEntry.setObject(getString(entry));
	            store.delete(id,currentEntry.getVersion());
	            store.put(id,currentEntry,this.storeName);
	        }
	    }

	    public Optional<UniProtEntry> getEntry(String id) {
	    	  System.out.println("Getting entry: " + id );
	        Store<String, String,String> store = this.getStore();

	        Optional<UniProtEntry> result = Optional.empty();
	        List<Versioned<String>> listVersionedEntry = store.get(id,this.storeName);
	        if(listVersionedEntry != null && !listVersionedEntry.isEmpty()){
	            result = Optional.of(convertEntry(listVersionedEntry.get(0).getValue()));
	        }
	        return result;
	    }

	    private UniProtEntry convertEntry(String string) {
	    	return null;//JsonUtils.convertJsonToObject(string, UniProtEntry.class);
	    }
	    public List<UniProtEntry> getEntries(Iterable<String> acc) {
	    	 System.out.println("Getting entry list :" + acc);
	        List<UniProtEntry> result = new ArrayList<>();
	        acc.forEach(accession ->{
	            Optional<UniProtEntry> entry = getEntry(accession);
	            entry.ifPresent(result::add);
	        });
	        return result;
	    }

	    public Map<String, UniProtEntry> getEntryMap(Iterable<String> acc) {
	        Store<String, String,String> store = this.getStore();
	        Map<String, List<Versioned<String>>> all = store.getAll(acc, null);
	        HashMap<String, UniProtEntry> stringEntryObjectHashMap = new HashMap<>();

	        all.forEach((key, value) ->
	                stringEntryObjectHashMap.put(key, convertEntry(value.get(0).getValue())));

	        return stringEntryObjectHashMap;
	    }

		public String getStoreName() {
			return storeName;
		}
}
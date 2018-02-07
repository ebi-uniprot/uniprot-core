package uk.ac.ebi.uniprot.ffwriter.line;

import java.util.List;

public interface FFLine {
	List<String> lines();
	void add(FFLine line);
	void add(String line);
	boolean isEmpty();
	
}

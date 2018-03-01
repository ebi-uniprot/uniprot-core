package uk.ac.ebi.uniprot.parser.ffwriter;

import java.util.List;

public interface FFLine {
	List<String> lines();
	void add(FFLine line);
	void add(String line);
	boolean isEmpty();
	
}

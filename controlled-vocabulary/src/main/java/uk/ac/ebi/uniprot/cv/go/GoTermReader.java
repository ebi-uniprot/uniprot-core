package uk.ac.ebi.uniprot.cv.go;

import java.util.Map;


public interface GoTermReader {
	public Map<String, GoTerm> read();
}

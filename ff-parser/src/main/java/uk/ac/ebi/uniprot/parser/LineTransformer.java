package uk.ac.ebi.uniprot.parser;

import java.util.List;

public interface LineTransformer<T> {
	List<T> transform(String lines);
	List<T> transformNoHeader(String lines);
}

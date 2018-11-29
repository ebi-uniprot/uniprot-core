package uk.ac.ebi.uniprot.parser;

/**
 * 
 * @author jieluo
 *
 * @param <T>
 * translate string to domain object, used for translating comment string to comment object
 * and feature string to feature object
 */
public interface Transformer<T> {
	void transform(String annotation, T obj);
	T transform(String annotation);
}

package uk.ac.ebi.uniprot.flatfile.parser;

import java.io.Serializable;

/**
 * 
 * @author jieluo
 *
 * @param <F>
 * @param <T>
 * convert Type F to T
 * It is used to convert FF Line Antlr objects to UJDK objects 
 */

public interface Converter<F, T> extends Serializable{
	T convert(F f);
}

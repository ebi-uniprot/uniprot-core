package uk.ac.ebi.uniprot.parser;

import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * <p/>
 * User: wudong, Date: 19/08/13, Time: 15:30
 */
public interface ParseTreeObjectExtractor<T> extends ParseTreeListener{

	public T getObject();
}

package org.uniprot.core.flatfile.parser;

import org.antlr.v4.runtime.tree.ParseTreeListener;

import java.io.Serializable;

/** User: wudong, Date: 19/08/13, Time: 15:30 */
public interface ParseTreeObjectExtractor<T> extends ParseTreeListener, Serializable {

    public T getObject();
}

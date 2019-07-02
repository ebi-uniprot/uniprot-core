package uk.ac.ebi.uniprot.flatfile.parser;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: wudong
 * Date: 08/08/13
 * Time: 15:29
 * To change this template use File | Settings | File Templates.
 */
public interface UniprotLineParser<T> extends Serializable {
    T parse(String s);
}

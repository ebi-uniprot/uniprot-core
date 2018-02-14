package uk.ac.ebi.uniprot.parser;

/**
 * Created with IntelliJ IDEA.
 * User: wudong
 * Date: 08/08/13
 * Time: 15:29
 * To change this template use File | Settings | File Templates.
 */
public interface UniprotLineParser<T> {
    T parse(String s);
}

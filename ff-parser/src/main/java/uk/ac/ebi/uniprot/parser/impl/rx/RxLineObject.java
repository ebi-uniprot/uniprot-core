package uk.ac.ebi.uniprot.parser.impl.rx;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: wudong
 * Date: 08/08/13
 * Time: 11:50
 * To change this template use File | Settings | File Templates.
 */
public class RxLineObject {

    public List<RX> rxs =new ArrayList<RX>();

    public static enum DB {
        PubMed, DOI, AGRICOLA
    }

    public static class RX{
        public DB type;
        public String value;
    }

}

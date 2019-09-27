package org.uniprot.core.flatfile.parser.impl.oh;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA. User: wudong Date: 08/08/13 Time: 11:50 To change this template use
 * File | Settings | File Templates.
 */
public class OhLineObject {

    public List<OhValue> hosts = new ArrayList<OhValue>();

    public static class OhValue {
        public int tax_id;
        public String hostname;
    }
}

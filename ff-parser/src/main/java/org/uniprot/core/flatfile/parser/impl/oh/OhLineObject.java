package org.uniprot.core.flatfile.parser.impl.oh;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA. User: wudong Date: 08/08/13 Time: 11:50 To change this template use
 * File | Settings | File Templates.
 */
public class OhLineObject {

    private List<OhValue> hosts = new ArrayList<>();

    public List<OhValue> getHosts() {
        return hosts;
    }

    public void setHosts(List<OhValue> hosts) {
        this.hosts = hosts;
    }

    public static class OhValue {
        private int tax_id;
        private String hostname;

        public int getTax_id() {
            return tax_id;
        }

        public void setTax_id(int tax_id) {
            this.tax_id = tax_id;
        }

        public String getHostname() {
            return hostname;
        }

        public void setHostname(String hostname) {
            this.hostname = hostname;
        }
    }
}

package org.uniprot.core.flatfile.parser.impl.dr;

import org.uniprot.core.flatfile.parser.impl.EvidenceInfo;
import org.uniprot.core.flatfile.parser.impl.HasEvidenceInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA. User: wudong Date: 13/08/13 Time: 09:20 To change this template use
 * File | Settings | File Templates.
 */
public class DrLineObject implements HasEvidenceInfo {

    public List<DrObject> drObjects = new ArrayList<DrObject>();

    public EvidenceInfo evidenceInfo = new EvidenceInfo();

    @Override
    public EvidenceInfo getEvidenceInfo() {
        return evidenceInfo;
    }

    public static class DrObject {
        public String DbName;
        public List<String> attributes = new ArrayList<String>();
        // public boolean inSsLine;
        public String isoform;
        public String ssLineValue;
    }
}

package org.uniprot.core.flatfile.parser.impl.rc;

import org.uniprot.core.flatfile.parser.impl.EvidenceInfo;
import org.uniprot.core.flatfile.parser.impl.HasEvidenceInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA. User: wudong Date: 08/08/13 Time: 11:50 To change this template use
 * File | Settings | File Templates.
 */
public class RcLineObject {

    public List<RC> rcs = new ArrayList<RC>();

    public static enum RcTokenEnum {
        STRAIN,
        PLASMID,
        TRANSPOSON,
        TISSUE
    }

    public static class RC implements HasEvidenceInfo {
        public RcTokenEnum tokenType;
        public List<String> values = new ArrayList<String>();

        public EvidenceInfo evidenceInfo = new EvidenceInfo();

        @Override
        public EvidenceInfo getEvidenceInfo() {
            return evidenceInfo;
        }
    }
}

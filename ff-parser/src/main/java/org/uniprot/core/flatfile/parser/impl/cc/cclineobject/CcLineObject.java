package org.uniprot.core.flatfile.parser.impl.cc.cclineobject;

import java.util.ArrayList;
import java.util.List;

import org.uniprot.core.flatfile.parser.impl.EvidenceInfo;
import org.uniprot.core.flatfile.parser.impl.HasEvidenceInfo;

/** User: wudong, Date: 03/09/13, Time: 16:35 */
public class CcLineObject implements HasEvidenceInfo {

    private List<CC> ccs = new ArrayList<>();
    private EvidenceInfo evidenceInfo = new EvidenceInfo();

    public List<CC> getCcs() {
        return ccs;
    }

    public void setCcs(List<CC> ccs) {
        this.ccs = ccs;
    }

    @Override
    public EvidenceInfo getEvidenceInfo() {
        return evidenceInfo;
    }

    public void setEvidenceInfo(EvidenceInfo evidenceInfo) {
        this.evidenceInfo = evidenceInfo;
    }
}

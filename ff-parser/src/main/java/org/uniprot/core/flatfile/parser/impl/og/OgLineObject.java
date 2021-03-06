package org.uniprot.core.flatfile.parser.impl.og;

import java.util.ArrayList;
import java.util.List;

import org.uniprot.core.flatfile.parser.impl.EvidenceInfo;
import org.uniprot.core.flatfile.parser.impl.HasEvidenceInfo;

/**
 * Created with IntelliJ IDEA. User: wudong Date: 08/08/13 Time: 11:50 To change this template use
 * File | Settings | File Templates.
 */
public class OgLineObject implements HasEvidenceInfo {

    public EvidenceInfo evidence = new EvidenceInfo();
    public List<String> plasmidNames = new ArrayList<String>();
    public List<OgEnum> ogs = new ArrayList<OgEnum>();

    @Override
    public EvidenceInfo getEvidenceInfo() {
        return evidence;
    }

    public static enum OgEnum {
        HYDROGENOSOME,
        MITOCHONDRION,
        NUCLEOMORPH,
        PLASTID,
        PLASTID_APICOPLAST,
        PLASTID_CHLOROPLAST,
        PLASTID_ORGANELLAR_CHROMATOPHORE,
        PLASTID_CYANELLE,
        PLASTID_NON_PHOTOSYNTHETIC,
        PLASMID
    }
}

package org.uniprot.core.flatfile.parser.impl.gn;

import org.uniprot.core.flatfile.parser.impl.EvidenceInfo;
import org.uniprot.core.flatfile.parser.impl.HasEvidenceInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA. User: wudong Date: 08/08/13 Time: 11:50 To change this template use
 * File | Settings | File Templates.
 */
public class GnLineObject {

    public List<GnObject> gnObjects = new ArrayList<GnObject>();

    public static class GnObject {
        public List<GnName> names = new ArrayList<GnName>();
    }

    public static class GnName implements HasEvidenceInfo {

        public GnNameType type;
        public List<String> names = new ArrayList<String>();

        private EvidenceInfo evidence = new EvidenceInfo();

        @Override
        public EvidenceInfo getEvidenceInfo() {
            return evidence;
        }
    }

    public static enum GnNameType {
        GENAME,
        SYNNAME,
        ORFNAME,
        OLNAME;
    }
}

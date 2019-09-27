package org.uniprot.core.flatfile.parser.impl.entry;

import java.util.ArrayList;
import java.util.List;

import org.uniprot.core.flatfile.parser.impl.ac.AcLineObject;
import org.uniprot.core.flatfile.parser.impl.cc.CcLineObject;
import org.uniprot.core.flatfile.parser.impl.de.DeLineObject;
import org.uniprot.core.flatfile.parser.impl.dr.DrLineObject;
import org.uniprot.core.flatfile.parser.impl.dt.DtLineObject;
import org.uniprot.core.flatfile.parser.impl.ft.FtLineObject;
import org.uniprot.core.flatfile.parser.impl.gn.GnLineObject;
import org.uniprot.core.flatfile.parser.impl.id.IdLineObject;
import org.uniprot.core.flatfile.parser.impl.kw.KwLineObject;
import org.uniprot.core.flatfile.parser.impl.oc.OcLineObject;
import org.uniprot.core.flatfile.parser.impl.og.OgLineObject;
import org.uniprot.core.flatfile.parser.impl.oh.OhLineObject;
import org.uniprot.core.flatfile.parser.impl.os.OsLineObject;
import org.uniprot.core.flatfile.parser.impl.ox.OxLineObject;
import org.uniprot.core.flatfile.parser.impl.pe.PeLineObject;
import org.uniprot.core.flatfile.parser.impl.ra.RaLineObject;
import org.uniprot.core.flatfile.parser.impl.rc.RcLineObject;
import org.uniprot.core.flatfile.parser.impl.rg.RgLineObject;
import org.uniprot.core.flatfile.parser.impl.rl.RlLineObject;
import org.uniprot.core.flatfile.parser.impl.rn.RnLineObject;
import org.uniprot.core.flatfile.parser.impl.rp.RpLineObject;
import org.uniprot.core.flatfile.parser.impl.rt.RtLineObject;
import org.uniprot.core.flatfile.parser.impl.rx.RxLineObject;
import org.uniprot.core.flatfile.parser.impl.sq.SqLineObject;
import org.uniprot.core.flatfile.parser.impl.ss.SsLineObject;

/** User: wudong, Date: 16/09/13, Time: 10:46 */
public class EntryObject {
    public AcLineObject ac;
    public CcLineObject cc;
    public DeLineObject de;
    public DrLineObject dr;
    public DtLineObject dt;
    public FtLineObject ft;
    public GnLineObject gn;
    public IdLineObject id;
    public KwLineObject kw;
    public OcLineObject oc;
    public OgLineObject og;
    public OhLineObject oh;
    public OsLineObject os;
    public OxLineObject ox;
    public PeLineObject pe;
    public SqLineObject sq;
    public SsLineObject ss;
    public List<ReferenceObject> ref = new ArrayList<ReferenceObject>();

    public static class ReferenceObject {
        public RnLineObject rn;
        public RpLineObject rp;
        public RaLineObject ra;
        public RtLineObject rt;
        public RxLineObject rx;
        public RcLineObject rc;
        public RgLineObject rg;
        public RlLineObject rl;
    }
}

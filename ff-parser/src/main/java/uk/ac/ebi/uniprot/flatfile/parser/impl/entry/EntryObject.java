package uk.ac.ebi.uniprot.flatfile.parser.impl.entry;

import uk.ac.ebi.uniprot.flatfile.parser.impl.ac.AcLineObject;
import uk.ac.ebi.uniprot.flatfile.parser.impl.cc.CcLineObject;
import uk.ac.ebi.uniprot.flatfile.parser.impl.de.DeLineObject;
import uk.ac.ebi.uniprot.flatfile.parser.impl.dr.DrLineObject;
import uk.ac.ebi.uniprot.flatfile.parser.impl.dt.DtLineObject;
import uk.ac.ebi.uniprot.flatfile.parser.impl.ft.FtLineObject;
import uk.ac.ebi.uniprot.flatfile.parser.impl.gn.GnLineObject;
import uk.ac.ebi.uniprot.flatfile.parser.impl.id.IdLineObject;
import uk.ac.ebi.uniprot.flatfile.parser.impl.kw.KwLineObject;
import uk.ac.ebi.uniprot.flatfile.parser.impl.oc.OcLineObject;
import uk.ac.ebi.uniprot.flatfile.parser.impl.og.OgLineObject;
import uk.ac.ebi.uniprot.flatfile.parser.impl.oh.OhLineObject;
import uk.ac.ebi.uniprot.flatfile.parser.impl.os.OsLineObject;
import uk.ac.ebi.uniprot.flatfile.parser.impl.ox.OxLineObject;
import uk.ac.ebi.uniprot.flatfile.parser.impl.pe.PeLineObject;
import uk.ac.ebi.uniprot.flatfile.parser.impl.ra.RaLineObject;
import uk.ac.ebi.uniprot.flatfile.parser.impl.rc.RcLineObject;
import uk.ac.ebi.uniprot.flatfile.parser.impl.rg.RgLineObject;
import uk.ac.ebi.uniprot.flatfile.parser.impl.rl.RlLineObject;
import uk.ac.ebi.uniprot.flatfile.parser.impl.rn.RnLineObject;
import uk.ac.ebi.uniprot.flatfile.parser.impl.rp.RpLineObject;
import uk.ac.ebi.uniprot.flatfile.parser.impl.rt.RtLineObject;
import uk.ac.ebi.uniprot.flatfile.parser.impl.rx.RxLineObject;
import uk.ac.ebi.uniprot.flatfile.parser.impl.sq.SqLineObject;
import uk.ac.ebi.uniprot.flatfile.parser.impl.ss.SsLineObject;

import java.util.ArrayList;
import java.util.List;

/**
 * <p/>
 * User: wudong, Date: 16/09/13, Time: 10:46
 */
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

     public static class ReferenceObject{
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

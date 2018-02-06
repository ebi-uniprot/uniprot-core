package uk.ac.ebi.uniprot.parser.impl.entry;

import uk.ac.ebi.uniprot.parser.impl.ac.AcLineObject;
import uk.ac.ebi.uniprot.parser.impl.cc.CcLineObject;
import uk.ac.ebi.uniprot.parser.impl.de.DeLineObject;
import uk.ac.ebi.uniprot.parser.impl.dr.DrLineObject;
import uk.ac.ebi.uniprot.parser.impl.dt.DtLineObject;
import uk.ac.ebi.uniprot.parser.impl.ft.FtLineObject;
import uk.ac.ebi.uniprot.parser.impl.gn.GnLineObject;
import uk.ac.ebi.uniprot.parser.impl.id.IdLineObject;
import uk.ac.ebi.uniprot.parser.impl.kw.KwLineObject;
import uk.ac.ebi.uniprot.parser.impl.oc.OcLineObject;
import uk.ac.ebi.uniprot.parser.impl.og.OgLineObject;
import uk.ac.ebi.uniprot.parser.impl.oh.OhLineObject;
import uk.ac.ebi.uniprot.parser.impl.os.OsLineObject;
import uk.ac.ebi.uniprot.parser.impl.ox.OxLineObject;
import uk.ac.ebi.uniprot.parser.impl.pe.PeLineObject;
import uk.ac.ebi.uniprot.parser.impl.ra.RaLineObject;
import uk.ac.ebi.uniprot.parser.impl.rc.RcLineObject;
import uk.ac.ebi.uniprot.parser.impl.rg.RgLineObject;
import uk.ac.ebi.uniprot.parser.impl.rl.RlLineObject;
import uk.ac.ebi.uniprot.parser.impl.rn.RnLineObject;
import uk.ac.ebi.uniprot.parser.impl.rp.RpLineObject;
import uk.ac.ebi.uniprot.parser.impl.rt.RtLineObject;
import uk.ac.ebi.uniprot.parser.impl.rx.RxLineObject;
import uk.ac.ebi.uniprot.parser.impl.sq.SqLineObject;
import uk.ac.ebi.uniprot.parser.impl.ss.SsLineObject;

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

package org.uniprot.core.flatfile.parser;

import java.io.Serializable;

import org.uniprot.core.flatfile.parser.impl.ac.AcLineObject;
import org.uniprot.core.flatfile.parser.impl.cc.CcLineObject;
import org.uniprot.core.flatfile.parser.impl.de.DeLineObject;
import org.uniprot.core.flatfile.parser.impl.dr.DrLineObject;
import org.uniprot.core.flatfile.parser.impl.dt.DtLineObject;
import org.uniprot.core.flatfile.parser.impl.entry.EntryObject;
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

/**
 * <p/>
 * User: wudong, Date: 19/08/13, Time: 15:49
 */
public interface UniprotLineParserFactory extends Serializable{
	public  UniprotLineParser<AcLineObject> createAcLineParser();

	UniprotLineParser<DrLineObject> createDrLineParser();

	UniprotLineParser<DeLineObject> createDeLineParser();

	UniprotLineParser<DtLineObject> createDtLineParser();

	UniprotLineParser<GnLineObject> createGnLineParser();

	UniprotLineParser<IdLineObject> createIdLineParser();

	UniprotLineParser<OsLineObject> createOsLineParser();

	UniprotLineParser<PeLineObject> createPeLineParser();

	UniprotLineParser<SqLineObject> createSqLineParser();

	UniprotLineParser<OgLineObject> createOgLineParser();

	UniprotLineParser<KwLineObject> createKwLineParser();

    UniprotLineParser<RnLineObject> createRnLineParser();

    UniprotLineParser<RtLineObject> createRtLineParser();

    UniprotLineParser<RpLineObject> createRpLineParser();

    UniprotLineParser<RaLineObject> createRaLineParser();

    UniprotLineParser<RgLineObject> createRgLineParser();

    UniprotLineParser<RcLineObject> createRcLineParser();

    UniprotLineParser<RxLineObject> createRxLineParser();

    UniprotLineParser<RlLineObject> createRlLineParser();

	UniprotLineParser<FtLineObject> createFtLineParser();

	UniprotLineParser<OcLineObject> createOcLineParser();

	UniprotLineParser<OxLineObject> createOxLineParser();

	UniprotLineParser<OhLineObject> createOhLineParser();

	UniprotLineParser<CcLineObject> createCcLineParser();

	UniprotLineParser<EntryObject> createEntryParser();

	UniprotLineParser<SsLineObject> createSsLineParser();
}

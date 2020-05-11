package org.uniprot.core.flatfile.parser;

import org.uniprot.core.flatfile.parser.impl.ac.AcLineObject;
import org.uniprot.core.flatfile.parser.impl.cc.cclineobject.CcLineObject;
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

import java.io.Serializable;

/** User: wudong, Date: 19/08/13, Time: 15:49 */
public interface UniprotKBLineParserFactory extends Serializable {
    public UniprotKBLineParser<AcLineObject> createAcLineParser();

    UniprotKBLineParser<DrLineObject> createDrLineParser();

    UniprotKBLineParser<DeLineObject> createDeLineParser();

    UniprotKBLineParser<DtLineObject> createDtLineParser();

    UniprotKBLineParser<GnLineObject> createGnLineParser();

    UniprotKBLineParser<IdLineObject> createIdLineParser();

    UniprotKBLineParser<OsLineObject> createOsLineParser();

    UniprotKBLineParser<PeLineObject> createPeLineParser();

    UniprotKBLineParser<SqLineObject> createSqLineParser();

    UniprotKBLineParser<OgLineObject> createOgLineParser();

    UniprotKBLineParser<KwLineObject> createKwLineParser();

    UniprotKBLineParser<RnLineObject> createRnLineParser();

    UniprotKBLineParser<RtLineObject> createRtLineParser();

    UniprotKBLineParser<RpLineObject> createRpLineParser();

    UniprotKBLineParser<RaLineObject> createRaLineParser();

    UniprotKBLineParser<RgLineObject> createRgLineParser();

    UniprotKBLineParser<RcLineObject> createRcLineParser();

    UniprotKBLineParser<RxLineObject> createRxLineParser();

    UniprotKBLineParser<RlLineObject> createRlLineParser();

    UniprotKBLineParser<FtLineObject> createFtLineParser();

    UniprotKBLineParser<OcLineObject> createOcLineParser();

    UniprotKBLineParser<OxLineObject> createOxLineParser();

    UniprotKBLineParser<OhLineObject> createOhLineParser();

    UniprotKBLineParser<CcLineObject> createCcLineParser();

    UniprotKBLineParser<EntryObject> createEntryParser();

    UniprotKBLineParser<SsLineObject> createSsLineParser();
}

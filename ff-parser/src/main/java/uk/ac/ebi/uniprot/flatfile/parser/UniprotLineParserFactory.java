package uk.ac.ebi.uniprot.flatfile.parser;

import java.io.Serializable;

import uk.ac.ebi.uniprot.flatfile.parser.impl.ac.AcLineObject;
import uk.ac.ebi.uniprot.flatfile.parser.impl.cc.CcLineObject;
import uk.ac.ebi.uniprot.flatfile.parser.impl.de.DeLineObject;
import uk.ac.ebi.uniprot.flatfile.parser.impl.dr.DrLineObject;
import uk.ac.ebi.uniprot.flatfile.parser.impl.dt.DtLineObject;
import uk.ac.ebi.uniprot.flatfile.parser.impl.entry.EntryObject;
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

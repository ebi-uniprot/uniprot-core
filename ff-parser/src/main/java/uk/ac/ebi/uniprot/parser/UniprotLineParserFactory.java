package uk.ac.ebi.uniprot.parser;

import uk.ac.ebi.uniprot.parser.impl.ac.AcLineObject;
import uk.ac.ebi.uniprot.parser.impl.cc.CcLineObject;
import uk.ac.ebi.uniprot.parser.impl.de.DeLineObject;
import uk.ac.ebi.uniprot.parser.impl.dr.DrLineObject;
import uk.ac.ebi.uniprot.parser.impl.dt.DtLineObject;
import uk.ac.ebi.uniprot.parser.impl.entry.EntryObject;
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

/**
 * <p/>
 * User: wudong, Date: 19/08/13, Time: 15:49
 */
public interface UniprotLineParserFactory {
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

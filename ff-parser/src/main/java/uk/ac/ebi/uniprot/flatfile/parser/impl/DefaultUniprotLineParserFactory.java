package uk.ac.ebi.uniprot.flatfile.parser.impl;

import uk.ac.ebi.uniprot.flatfile.antlr.*;
import uk.ac.ebi.uniprot.flatfile.parser.GrammarFactory;
import uk.ac.ebi.uniprot.flatfile.parser.UniprotLineParser;
import uk.ac.ebi.uniprot.flatfile.parser.UniprotLineParserFactory;
import uk.ac.ebi.uniprot.flatfile.parser.impl.ac.AcLineModelListener;
import uk.ac.ebi.uniprot.flatfile.parser.impl.ac.AcLineObject;
import uk.ac.ebi.uniprot.flatfile.parser.impl.cc.CcLineModelListener;
import uk.ac.ebi.uniprot.flatfile.parser.impl.cc.CcLineObject;
import uk.ac.ebi.uniprot.flatfile.parser.impl.de.DeLineModelListener;
import uk.ac.ebi.uniprot.flatfile.parser.impl.de.DeLineObject;
import uk.ac.ebi.uniprot.flatfile.parser.impl.dr.DrLineModelListener;
import uk.ac.ebi.uniprot.flatfile.parser.impl.dr.DrLineObject;
import uk.ac.ebi.uniprot.flatfile.parser.impl.dt.DtLineModelListener;
import uk.ac.ebi.uniprot.flatfile.parser.impl.dt.DtLineObject;
import uk.ac.ebi.uniprot.flatfile.parser.impl.entry.EntryModelListener;
import uk.ac.ebi.uniprot.flatfile.parser.impl.entry.EntryObject;
import uk.ac.ebi.uniprot.flatfile.parser.impl.ft.FtLineModelListener;
import uk.ac.ebi.uniprot.flatfile.parser.impl.ft.FtLineObject;
import uk.ac.ebi.uniprot.flatfile.parser.impl.gn.GnLineModelListener;
import uk.ac.ebi.uniprot.flatfile.parser.impl.gn.GnLineObject;
import uk.ac.ebi.uniprot.flatfile.parser.impl.id.IdLineModelListener;
import uk.ac.ebi.uniprot.flatfile.parser.impl.id.IdLineObject;
import uk.ac.ebi.uniprot.flatfile.parser.impl.kw.KwLineModelListener;
import uk.ac.ebi.uniprot.flatfile.parser.impl.kw.KwLineObject;
import uk.ac.ebi.uniprot.flatfile.parser.impl.oc.OcLineModelListener;
import uk.ac.ebi.uniprot.flatfile.parser.impl.oc.OcLineObject;
import uk.ac.ebi.uniprot.flatfile.parser.impl.og.OgLineModelListener;
import uk.ac.ebi.uniprot.flatfile.parser.impl.og.OgLineObject;
import uk.ac.ebi.uniprot.flatfile.parser.impl.oh.OhLineModelListener;
import uk.ac.ebi.uniprot.flatfile.parser.impl.oh.OhLineObject;
import uk.ac.ebi.uniprot.flatfile.parser.impl.os.OsLineModelListener;
import uk.ac.ebi.uniprot.flatfile.parser.impl.os.OsLineObject;
import uk.ac.ebi.uniprot.flatfile.parser.impl.ox.OxLineModelListener;
import uk.ac.ebi.uniprot.flatfile.parser.impl.ox.OxLineObject;
import uk.ac.ebi.uniprot.flatfile.parser.impl.pe.PeLineModelListener;
import uk.ac.ebi.uniprot.flatfile.parser.impl.pe.PeLineObject;
import uk.ac.ebi.uniprot.flatfile.parser.impl.ra.RaLineModelListener;
import uk.ac.ebi.uniprot.flatfile.parser.impl.ra.RaLineObject;
import uk.ac.ebi.uniprot.flatfile.parser.impl.rc.RcLineModelListener;
import uk.ac.ebi.uniprot.flatfile.parser.impl.rc.RcLineObject;
import uk.ac.ebi.uniprot.flatfile.parser.impl.rg.RgLineModelListener;
import uk.ac.ebi.uniprot.flatfile.parser.impl.rg.RgLineObject;
import uk.ac.ebi.uniprot.flatfile.parser.impl.rl.RlLineModelListener;
import uk.ac.ebi.uniprot.flatfile.parser.impl.rl.RlLineObject;
import uk.ac.ebi.uniprot.flatfile.parser.impl.rn.RnLineModelListener;
import uk.ac.ebi.uniprot.flatfile.parser.impl.rn.RnLineObject;
import uk.ac.ebi.uniprot.flatfile.parser.impl.rp.RpLineModelListener;
import uk.ac.ebi.uniprot.flatfile.parser.impl.rp.RpLineObject;
import uk.ac.ebi.uniprot.flatfile.parser.impl.rt.RtLineModelListener;
import uk.ac.ebi.uniprot.flatfile.parser.impl.rt.RtLineObject;
import uk.ac.ebi.uniprot.flatfile.parser.impl.rx.RxLineModelListener;
import uk.ac.ebi.uniprot.flatfile.parser.impl.rx.RxLineObject;
import uk.ac.ebi.uniprot.flatfile.parser.impl.sq.SqLineModelListener;
import uk.ac.ebi.uniprot.flatfile.parser.impl.sq.SqLineObject;
import uk.ac.ebi.uniprot.flatfile.parser.impl.ss.SsLineModelListener;
import uk.ac.ebi.uniprot.flatfile.parser.impl.ss.SsLineObject;

/**
 * <p/>
 * User: wudong, Date: 19/08/13, Time: 15:55
 */
public class DefaultUniprotLineParserFactory implements UniprotLineParserFactory {

	@Override
	public UniprotLineParser<EntryObject> createEntryParser() {
		return new DefaultUniprotLineParser<EntryObject, UniprotLexer, UniprotParser>(
				GrammarFactory.GrammarFactoryEnum.Uniprot.getFactory(),
				new EntryModelListener()
		);
	}

	@Override
	public UniprotLineParser<AcLineObject> createAcLineParser() {
		return new DefaultUniprotLineParser<AcLineObject, AcLineLexer, AcLineParser>(
				GrammarFactory.GrammarFactoryEnum.Ac.getFactory(),
				new AcLineModelListener()
		);
	}

	@Override
	public UniprotLineParser<DrLineObject> createDrLineParser() {
		return new DefaultUniprotLineParser<DrLineObject, DrLineLexer, DrLineParser>(
				GrammarFactory.GrammarFactoryEnum.Dr.getFactory(),
				new DrLineModelListener()
		);
	}

	@Override
	public UniprotLineParser<DeLineObject> createDeLineParser() {
		return new DefaultUniprotLineParser<DeLineObject, DeLineLexer, DeLineParser>(
				GrammarFactory.GrammarFactoryEnum.De.getFactory(),
				new DeLineModelListener()
		);
	}

	@Override
	public UniprotLineParser<DtLineObject> createDtLineParser() {
		return new DefaultUniprotLineParser<DtLineObject, DtLineLexer, DtLineParser>(
				GrammarFactory.GrammarFactoryEnum.Dt.getFactory(),
				new DtLineModelListener()
		);
	}

	@Override
	public UniprotLineParser<GnLineObject> createGnLineParser() {
		return new DefaultUniprotLineParser<GnLineObject, GnLineLexer, GnLineParser>(
				GrammarFactory.GrammarFactoryEnum.Gn.getFactory(),
				new GnLineModelListener()
		);
	}

	@Override
	public UniprotLineParser<IdLineObject> createIdLineParser() {
		return new DefaultUniprotLineParser<IdLineObject, IdLineLexer, IdLineParser>(
				GrammarFactory.GrammarFactoryEnum.Id.getFactory(),
				new IdLineModelListener()
		);
	}

	@Override
	public UniprotLineParser<KwLineObject> createKwLineParser() {
		return new DefaultUniprotLineParser<KwLineObject, KwLineLexer, KwLineParser>(
				GrammarFactory.GrammarFactoryEnum.Kw.getFactory(),
				new KwLineModelListener()
		);
	}

	@Override
	public UniprotLineParser<OgLineObject> createOgLineParser() {
		return new DefaultUniprotLineParser<OgLineObject, OgLineLexer, OgLineParser>(
				GrammarFactory.GrammarFactoryEnum.Og.getFactory(),
				new OgLineModelListener()
		);
	}

	@Override
	public UniprotLineParser<OsLineObject> createOsLineParser() {
		return new DefaultUniprotLineParser<OsLineObject, OsLineLexer, OsLineParser>(
				GrammarFactory.GrammarFactoryEnum.Os.getFactory(),
				new OsLineModelListener()
		);
	}

	@Override
	public UniprotLineParser<PeLineObject> createPeLineParser() {
		return new DefaultUniprotLineParser<PeLineObject, PeLineLexer, PeLineParser>(
				GrammarFactory.GrammarFactoryEnum.Pe.getFactory(),
				new PeLineModelListener()
		);
	}

	@Override
	public UniprotLineParser<SqLineObject> createSqLineParser() {
		return new DefaultUniprotLineParser<SqLineObject, SqLineLexer, SqLineParser>(
				GrammarFactory.GrammarFactoryEnum.Sq.getFactory(),
				new SqLineModelListener()
		);
	}

	@Override
	public UniprotLineParser<RnLineObject> createRnLineParser() {
		return new DefaultUniprotLineParser<RnLineObject, RnLineLexer, RnLineParser>(
				GrammarFactory.GrammarFactoryEnum.Rn.getFactory(),
				new RnLineModelListener()
		);
	}

	@Override
	public UniprotLineParser<RtLineObject> createRtLineParser() {
		return new DefaultUniprotLineParser<RtLineObject, RtLineLexer, RtLineParser>(
				GrammarFactory.GrammarFactoryEnum.Rt.getFactory(),
				new RtLineModelListener()
		);
	}


	@Override
	public UniprotLineParser<RpLineObject> createRpLineParser() {
		return new DefaultUniprotLineParser<RpLineObject, RpLineLexer, RpLineParser>(
				GrammarFactory.GrammarFactoryEnum.Rp.getFactory(),
				new RpLineModelListener()
		);
	}

	@Override
	public UniprotLineParser<RaLineObject> createRaLineParser() {
		return new DefaultUniprotLineParser<RaLineObject, RaLineLexer, RaLineParser>(
				GrammarFactory.GrammarFactoryEnum.Ra.getFactory(),
				new RaLineModelListener()
		);
	}

	@Override
	public UniprotLineParser<RgLineObject> createRgLineParser() {
		return new DefaultUniprotLineParser<RgLineObject, RgLineLexer, RgLineParser>(
				GrammarFactory.GrammarFactoryEnum.Rg.getFactory(),
				new RgLineModelListener()
		);
	}

	@Override
	public UniprotLineParser<RcLineObject> createRcLineParser() {
		return new DefaultUniprotLineParser<RcLineObject, RcLineLexer, RcLineParser>(
				GrammarFactory.GrammarFactoryEnum.Rc.getFactory(),
				new RcLineModelListener()
		);
	}

	@Override
	public UniprotLineParser<RxLineObject> createRxLineParser() {
		return new DefaultUniprotLineParser<RxLineObject, RxLineLexer, RxLineParser>(
				GrammarFactory.GrammarFactoryEnum.Rx.getFactory(),
				new RxLineModelListener()
		);
	}

	@Override
	public UniprotLineParser<RlLineObject> createRlLineParser() {
		return new DefaultUniprotLineParser<RlLineObject, RlLineLexer, RlLineParser>(
				GrammarFactory.GrammarFactoryEnum.Rl.getFactory(),
				new RlLineModelListener()
		);
	}

	@Override
	public UniprotLineParser<FtLineObject> createFtLineParser() {
		return new DefaultUniprotLineParser<FtLineObject, FtLineLexer, FtLineParser>(
				GrammarFactory.GrammarFactoryEnum.Ft.getFactory(),
				new FtLineModelListener()
		);
	}

	@Override
	public UniprotLineParser<OcLineObject> createOcLineParser() {
		return new DefaultUniprotLineParser<OcLineObject, OcLineLexer, OcLineParser>(
				GrammarFactory.GrammarFactoryEnum.Oc.getFactory(),
				new OcLineModelListener()
		);
	}

	@Override
	public UniprotLineParser<OxLineObject> createOxLineParser() {
		return new DefaultUniprotLineParser<OxLineObject, OxLineLexer, OxLineParser>(
				GrammarFactory.GrammarFactoryEnum.Ox.getFactory(),
				new OxLineModelListener()
		);
	}

	@Override
	public UniprotLineParser<OhLineObject> createOhLineParser() {
		return new DefaultUniprotLineParser<OhLineObject, OhLineLexer, OhLineParser>(
				GrammarFactory.GrammarFactoryEnum.Oh.getFactory(),
				new OhLineModelListener()
		);
	}

	@Override
	public UniprotLineParser<CcLineObject> createCcLineParser() {
		return new DefaultUniprotLineParser<CcLineObject, CcLineLexer, CcLineParser>(
				GrammarFactory.GrammarFactoryEnum.Cc.getFactory(),
				new CcLineModelListener()
		);
	}

	@Override
	public UniprotLineParser<SsLineObject> createSsLineParser() {
		return new DefaultUniprotLineParser<SsLineObject, SsLineLexer, SsLineParser>(
				GrammarFactory.GrammarFactoryEnum.Ss.getFactory(),
				new SsLineModelListener()
		);
	}


}

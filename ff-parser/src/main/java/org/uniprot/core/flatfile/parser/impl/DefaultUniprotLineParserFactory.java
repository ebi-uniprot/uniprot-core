package org.uniprot.core.flatfile.parser.impl;

import org.uniprot.core.flatfile.antlr.*;
import org.uniprot.core.flatfile.parser.GrammarFactory;
import org.uniprot.core.flatfile.parser.UniprotLineParser;
import org.uniprot.core.flatfile.parser.UniprotLineParserFactory;
import org.uniprot.core.flatfile.parser.impl.ac.AcLineModelListener;
import org.uniprot.core.flatfile.parser.impl.ac.AcLineObject;
import org.uniprot.core.flatfile.parser.impl.cc.CcLineModelListener;
import org.uniprot.core.flatfile.parser.impl.cc.CcLineObject;
import org.uniprot.core.flatfile.parser.impl.de.DeLineModelListener;
import org.uniprot.core.flatfile.parser.impl.de.DeLineObject;
import org.uniprot.core.flatfile.parser.impl.dr.DrLineModelListener;
import org.uniprot.core.flatfile.parser.impl.dr.DrLineObject;
import org.uniprot.core.flatfile.parser.impl.dt.DtLineModelListener;
import org.uniprot.core.flatfile.parser.impl.dt.DtLineObject;
import org.uniprot.core.flatfile.parser.impl.entry.EntryModelListener;
import org.uniprot.core.flatfile.parser.impl.entry.EntryObject;
import org.uniprot.core.flatfile.parser.impl.ft.FtLineModelListener;
import org.uniprot.core.flatfile.parser.impl.ft.FtLineObject;
import org.uniprot.core.flatfile.parser.impl.gn.GnLineModelListener;
import org.uniprot.core.flatfile.parser.impl.gn.GnLineObject;
import org.uniprot.core.flatfile.parser.impl.id.IdLineModelListener;
import org.uniprot.core.flatfile.parser.impl.id.IdLineObject;
import org.uniprot.core.flatfile.parser.impl.kw.KwLineModelListener;
import org.uniprot.core.flatfile.parser.impl.kw.KwLineObject;
import org.uniprot.core.flatfile.parser.impl.oc.OcLineModelListener;
import org.uniprot.core.flatfile.parser.impl.oc.OcLineObject;
import org.uniprot.core.flatfile.parser.impl.og.OgLineModelListener;
import org.uniprot.core.flatfile.parser.impl.og.OgLineObject;
import org.uniprot.core.flatfile.parser.impl.oh.OhLineModelListener;
import org.uniprot.core.flatfile.parser.impl.oh.OhLineObject;
import org.uniprot.core.flatfile.parser.impl.os.OsLineModelListener;
import org.uniprot.core.flatfile.parser.impl.os.OsLineObject;
import org.uniprot.core.flatfile.parser.impl.ox.OxLineModelListener;
import org.uniprot.core.flatfile.parser.impl.ox.OxLineObject;
import org.uniprot.core.flatfile.parser.impl.pe.PeLineModelListener;
import org.uniprot.core.flatfile.parser.impl.pe.PeLineObject;
import org.uniprot.core.flatfile.parser.impl.ra.RaLineModelListener;
import org.uniprot.core.flatfile.parser.impl.ra.RaLineObject;
import org.uniprot.core.flatfile.parser.impl.rc.RcLineModelListener;
import org.uniprot.core.flatfile.parser.impl.rc.RcLineObject;
import org.uniprot.core.flatfile.parser.impl.rg.RgLineModelListener;
import org.uniprot.core.flatfile.parser.impl.rg.RgLineObject;
import org.uniprot.core.flatfile.parser.impl.rl.RlLineModelListener;
import org.uniprot.core.flatfile.parser.impl.rl.RlLineObject;
import org.uniprot.core.flatfile.parser.impl.rn.RnLineModelListener;
import org.uniprot.core.flatfile.parser.impl.rn.RnLineObject;
import org.uniprot.core.flatfile.parser.impl.rp.RpLineModelListener;
import org.uniprot.core.flatfile.parser.impl.rp.RpLineObject;
import org.uniprot.core.flatfile.parser.impl.rt.RtLineModelListener;
import org.uniprot.core.flatfile.parser.impl.rt.RtLineObject;
import org.uniprot.core.flatfile.parser.impl.rx.RxLineModelListener;
import org.uniprot.core.flatfile.parser.impl.rx.RxLineObject;
import org.uniprot.core.flatfile.parser.impl.sq.SqLineModelListener;
import org.uniprot.core.flatfile.parser.impl.sq.SqLineObject;
import org.uniprot.core.flatfile.parser.impl.ss.SsLineModelListener;
import org.uniprot.core.flatfile.parser.impl.ss.SsLineObject;

import uk.ac.ebi.uniprot.flatfile.antlr.AcLineLexer;
import uk.ac.ebi.uniprot.flatfile.antlr.AcLineParser;
import uk.ac.ebi.uniprot.flatfile.antlr.CcLineLexer;
import uk.ac.ebi.uniprot.flatfile.antlr.CcLineParser;
import uk.ac.ebi.uniprot.flatfile.antlr.DeLineLexer;
import uk.ac.ebi.uniprot.flatfile.antlr.DeLineParser;
import uk.ac.ebi.uniprot.flatfile.antlr.DrLineLexer;
import uk.ac.ebi.uniprot.flatfile.antlr.DrLineParser;
import uk.ac.ebi.uniprot.flatfile.antlr.DtLineLexer;
import uk.ac.ebi.uniprot.flatfile.antlr.DtLineParser;
import uk.ac.ebi.uniprot.flatfile.antlr.FtLineLexer;
import uk.ac.ebi.uniprot.flatfile.antlr.FtLineParser;
import uk.ac.ebi.uniprot.flatfile.antlr.GnLineLexer;
import uk.ac.ebi.uniprot.flatfile.antlr.GnLineParser;
import uk.ac.ebi.uniprot.flatfile.antlr.IdLineLexer;
import uk.ac.ebi.uniprot.flatfile.antlr.IdLineParser;
import uk.ac.ebi.uniprot.flatfile.antlr.KwLineLexer;
import uk.ac.ebi.uniprot.flatfile.antlr.KwLineParser;
import uk.ac.ebi.uniprot.flatfile.antlr.OcLineLexer;
import uk.ac.ebi.uniprot.flatfile.antlr.OcLineParser;
import uk.ac.ebi.uniprot.flatfile.antlr.OgLineLexer;
import uk.ac.ebi.uniprot.flatfile.antlr.OgLineParser;
import uk.ac.ebi.uniprot.flatfile.antlr.OhLineLexer;
import uk.ac.ebi.uniprot.flatfile.antlr.OhLineParser;
import uk.ac.ebi.uniprot.flatfile.antlr.OsLineLexer;
import uk.ac.ebi.uniprot.flatfile.antlr.OsLineParser;
import uk.ac.ebi.uniprot.flatfile.antlr.OxLineLexer;
import uk.ac.ebi.uniprot.flatfile.antlr.OxLineParser;
import uk.ac.ebi.uniprot.flatfile.antlr.PeLineLexer;
import uk.ac.ebi.uniprot.flatfile.antlr.PeLineParser;
import uk.ac.ebi.uniprot.flatfile.antlr.RaLineLexer;
import uk.ac.ebi.uniprot.flatfile.antlr.RaLineParser;
import uk.ac.ebi.uniprot.flatfile.antlr.RcLineLexer;
import uk.ac.ebi.uniprot.flatfile.antlr.RcLineParser;
import uk.ac.ebi.uniprot.flatfile.antlr.RgLineLexer;
import uk.ac.ebi.uniprot.flatfile.antlr.RgLineParser;
import uk.ac.ebi.uniprot.flatfile.antlr.RlLineLexer;
import uk.ac.ebi.uniprot.flatfile.antlr.RlLineParser;
import uk.ac.ebi.uniprot.flatfile.antlr.RnLineLexer;
import uk.ac.ebi.uniprot.flatfile.antlr.RnLineParser;
import uk.ac.ebi.uniprot.flatfile.antlr.RpLineLexer;
import uk.ac.ebi.uniprot.flatfile.antlr.RpLineParser;
import uk.ac.ebi.uniprot.flatfile.antlr.RtLineLexer;
import uk.ac.ebi.uniprot.flatfile.antlr.RtLineParser;
import uk.ac.ebi.uniprot.flatfile.antlr.RxLineLexer;
import uk.ac.ebi.uniprot.flatfile.antlr.RxLineParser;
import uk.ac.ebi.uniprot.flatfile.antlr.SqLineLexer;
import uk.ac.ebi.uniprot.flatfile.antlr.SqLineParser;
import uk.ac.ebi.uniprot.flatfile.antlr.SsLineLexer;
import uk.ac.ebi.uniprot.flatfile.antlr.SsLineParser;
import uk.ac.ebi.uniprot.flatfile.antlr.UniprotLexer;
import uk.ac.ebi.uniprot.flatfile.antlr.UniprotParser;

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

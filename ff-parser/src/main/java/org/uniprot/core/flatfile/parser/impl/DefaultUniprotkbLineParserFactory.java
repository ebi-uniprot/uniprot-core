package org.uniprot.core.flatfile.parser.impl;

import org.uniprot.core.flatfile.antlr.AcLineLexer;
import org.uniprot.core.flatfile.antlr.AcLineParser;
import org.uniprot.core.flatfile.antlr.CcLineLexer;
import org.uniprot.core.flatfile.antlr.CcLineParser;
import org.uniprot.core.flatfile.antlr.DeLineLexer;
import org.uniprot.core.flatfile.antlr.DeLineParser;
import org.uniprot.core.flatfile.antlr.DrLineLexer;
import org.uniprot.core.flatfile.antlr.DrLineParser;
import org.uniprot.core.flatfile.antlr.DtLineLexer;
import org.uniprot.core.flatfile.antlr.DtLineParser;
import org.uniprot.core.flatfile.antlr.FtLineLexer;
import org.uniprot.core.flatfile.antlr.FtLineParser;
import org.uniprot.core.flatfile.antlr.GnLineLexer;
import org.uniprot.core.flatfile.antlr.GnLineParser;
import org.uniprot.core.flatfile.antlr.IdLineLexer;
import org.uniprot.core.flatfile.antlr.IdLineParser;
import org.uniprot.core.flatfile.antlr.KwLineLexer;
import org.uniprot.core.flatfile.antlr.KwLineParser;
import org.uniprot.core.flatfile.antlr.OcLineLexer;
import org.uniprot.core.flatfile.antlr.OcLineParser;
import org.uniprot.core.flatfile.antlr.OgLineLexer;
import org.uniprot.core.flatfile.antlr.OgLineParser;
import org.uniprot.core.flatfile.antlr.OhLineLexer;
import org.uniprot.core.flatfile.antlr.OhLineParser;
import org.uniprot.core.flatfile.antlr.OsLineLexer;
import org.uniprot.core.flatfile.antlr.OsLineParser;
import org.uniprot.core.flatfile.antlr.OxLineLexer;
import org.uniprot.core.flatfile.antlr.OxLineParser;
import org.uniprot.core.flatfile.antlr.PeLineLexer;
import org.uniprot.core.flatfile.antlr.PeLineParser;
import org.uniprot.core.flatfile.antlr.RaLineLexer;
import org.uniprot.core.flatfile.antlr.RaLineParser;
import org.uniprot.core.flatfile.antlr.RcLineLexer;
import org.uniprot.core.flatfile.antlr.RcLineParser;
import org.uniprot.core.flatfile.antlr.RgLineLexer;
import org.uniprot.core.flatfile.antlr.RgLineParser;
import org.uniprot.core.flatfile.antlr.RlLineLexer;
import org.uniprot.core.flatfile.antlr.RlLineParser;
import org.uniprot.core.flatfile.antlr.RnLineLexer;
import org.uniprot.core.flatfile.antlr.RnLineParser;
import org.uniprot.core.flatfile.antlr.RpLineLexer;
import org.uniprot.core.flatfile.antlr.RpLineParser;
import org.uniprot.core.flatfile.antlr.RtLineLexer;
import org.uniprot.core.flatfile.antlr.RtLineParser;
import org.uniprot.core.flatfile.antlr.RxLineLexer;
import org.uniprot.core.flatfile.antlr.RxLineParser;
import org.uniprot.core.flatfile.antlr.SqLineLexer;
import org.uniprot.core.flatfile.antlr.SqLineParser;
import org.uniprot.core.flatfile.antlr.SsLineLexer;
import org.uniprot.core.flatfile.antlr.SsLineParser;
import org.uniprot.core.flatfile.antlr.UniprotLexer;
import org.uniprot.core.flatfile.antlr.UniprotParser;
import org.uniprot.core.flatfile.parser.GrammarFactory;
import org.uniprot.core.flatfile.parser.UniprotkbLineParser;
import org.uniprot.core.flatfile.parser.UniprotkbLineParserFactory;
import org.uniprot.core.flatfile.parser.impl.ac.AcLineModelListener;
import org.uniprot.core.flatfile.parser.impl.ac.AcLineObject;
import org.uniprot.core.flatfile.parser.impl.cc.CcLineModelListener;
import org.uniprot.core.flatfile.parser.impl.cc.cclineobject.CcLineObject;
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

/** User: wudong, Date: 19/08/13, Time: 15:55 */
public class DefaultUniprotkbLineParserFactory implements UniprotkbLineParserFactory {

    @Override
    public UniprotkbLineParser<EntryObject> createEntryParser() {
        return new DefaultUniprotkbLineParser<EntryObject, UniprotLexer, UniprotParser>(
                GrammarFactory.GrammarFactoryEnum.Uniprot.getFactory(), new EntryModelListener());
    }

    @Override
    public UniprotkbLineParser<AcLineObject> createAcLineParser() {
        return new DefaultUniprotkbLineParser<AcLineObject, AcLineLexer, AcLineParser>(
                GrammarFactory.GrammarFactoryEnum.Ac.getFactory(), new AcLineModelListener());
    }

    @Override
    public UniprotkbLineParser<DrLineObject> createDrLineParser() {
        return new DefaultUniprotkbLineParser<DrLineObject, DrLineLexer, DrLineParser>(
                GrammarFactory.GrammarFactoryEnum.Dr.getFactory(), new DrLineModelListener());
    }

    @Override
    public UniprotkbLineParser<DeLineObject> createDeLineParser() {
        return new DefaultUniprotkbLineParser<DeLineObject, DeLineLexer, DeLineParser>(
                GrammarFactory.GrammarFactoryEnum.De.getFactory(), new DeLineModelListener());
    }

    @Override
    public UniprotkbLineParser<DtLineObject> createDtLineParser() {
        return new DefaultUniprotkbLineParser<DtLineObject, DtLineLexer, DtLineParser>(
                GrammarFactory.GrammarFactoryEnum.Dt.getFactory(), new DtLineModelListener());
    }

    @Override
    public UniprotkbLineParser<GnLineObject> createGnLineParser() {
        return new DefaultUniprotkbLineParser<GnLineObject, GnLineLexer, GnLineParser>(
                GrammarFactory.GrammarFactoryEnum.Gn.getFactory(), new GnLineModelListener());
    }

    @Override
    public UniprotkbLineParser<IdLineObject> createIdLineParser() {
        return new DefaultUniprotkbLineParser<IdLineObject, IdLineLexer, IdLineParser>(
                GrammarFactory.GrammarFactoryEnum.Id.getFactory(), new IdLineModelListener());
    }

    @Override
    public UniprotkbLineParser<KwLineObject> createKwLineParser() {
        return new DefaultUniprotkbLineParser<KwLineObject, KwLineLexer, KwLineParser>(
                GrammarFactory.GrammarFactoryEnum.Kw.getFactory(), new KwLineModelListener());
    }

    @Override
    public UniprotkbLineParser<OgLineObject> createOgLineParser() {
        return new DefaultUniprotkbLineParser<OgLineObject, OgLineLexer, OgLineParser>(
                GrammarFactory.GrammarFactoryEnum.Og.getFactory(), new OgLineModelListener());
    }

    @Override
    public UniprotkbLineParser<OsLineObject> createOsLineParser() {
        return new DefaultUniprotkbLineParser<OsLineObject, OsLineLexer, OsLineParser>(
                GrammarFactory.GrammarFactoryEnum.Os.getFactory(), new OsLineModelListener());
    }

    @Override
    public UniprotkbLineParser<PeLineObject> createPeLineParser() {
        return new DefaultUniprotkbLineParser<PeLineObject, PeLineLexer, PeLineParser>(
                GrammarFactory.GrammarFactoryEnum.Pe.getFactory(), new PeLineModelListener());
    }

    @Override
    public UniprotkbLineParser<SqLineObject> createSqLineParser() {
        return new DefaultUniprotkbLineParser<SqLineObject, SqLineLexer, SqLineParser>(
                GrammarFactory.GrammarFactoryEnum.Sq.getFactory(), new SqLineModelListener());
    }

    @Override
    public UniprotkbLineParser<RnLineObject> createRnLineParser() {
        return new DefaultUniprotkbLineParser<RnLineObject, RnLineLexer, RnLineParser>(
                GrammarFactory.GrammarFactoryEnum.Rn.getFactory(), new RnLineModelListener());
    }

    @Override
    public UniprotkbLineParser<RtLineObject> createRtLineParser() {
        return new DefaultUniprotkbLineParser<RtLineObject, RtLineLexer, RtLineParser>(
                GrammarFactory.GrammarFactoryEnum.Rt.getFactory(), new RtLineModelListener());
    }

    @Override
    public UniprotkbLineParser<RpLineObject> createRpLineParser() {
        return new DefaultUniprotkbLineParser<RpLineObject, RpLineLexer, RpLineParser>(
                GrammarFactory.GrammarFactoryEnum.Rp.getFactory(), new RpLineModelListener());
    }

    @Override
    public UniprotkbLineParser<RaLineObject> createRaLineParser() {
        return new DefaultUniprotkbLineParser<RaLineObject, RaLineLexer, RaLineParser>(
                GrammarFactory.GrammarFactoryEnum.Ra.getFactory(), new RaLineModelListener());
    }

    @Override
    public UniprotkbLineParser<RgLineObject> createRgLineParser() {
        return new DefaultUniprotkbLineParser<RgLineObject, RgLineLexer, RgLineParser>(
                GrammarFactory.GrammarFactoryEnum.Rg.getFactory(), new RgLineModelListener());
    }

    @Override
    public UniprotkbLineParser<RcLineObject> createRcLineParser() {
        return new DefaultUniprotkbLineParser<RcLineObject, RcLineLexer, RcLineParser>(
                GrammarFactory.GrammarFactoryEnum.Rc.getFactory(), new RcLineModelListener());
    }

    @Override
    public UniprotkbLineParser<RxLineObject> createRxLineParser() {
        return new DefaultUniprotkbLineParser<RxLineObject, RxLineLexer, RxLineParser>(
                GrammarFactory.GrammarFactoryEnum.Rx.getFactory(), new RxLineModelListener());
    }

    @Override
    public UniprotkbLineParser<RlLineObject> createRlLineParser() {
        return new DefaultUniprotkbLineParser<RlLineObject, RlLineLexer, RlLineParser>(
                GrammarFactory.GrammarFactoryEnum.Rl.getFactory(), new RlLineModelListener());
    }

    @Override
    public UniprotkbLineParser<FtLineObject> createFtLineParser() {
        return new DefaultUniprotkbLineParser<FtLineObject, FtLineLexer, FtLineParser>(
                GrammarFactory.GrammarFactoryEnum.Ft.getFactory(), new FtLineModelListener());
    }

    @Override
    public UniprotkbLineParser<OcLineObject> createOcLineParser() {
        return new DefaultUniprotkbLineParser<OcLineObject, OcLineLexer, OcLineParser>(
                GrammarFactory.GrammarFactoryEnum.Oc.getFactory(), new OcLineModelListener());
    }

    @Override
    public UniprotkbLineParser<OxLineObject> createOxLineParser() {
        return new DefaultUniprotkbLineParser<OxLineObject, OxLineLexer, OxLineParser>(
                GrammarFactory.GrammarFactoryEnum.Ox.getFactory(), new OxLineModelListener());
    }

    @Override
    public UniprotkbLineParser<OhLineObject> createOhLineParser() {
        return new DefaultUniprotkbLineParser<OhLineObject, OhLineLexer, OhLineParser>(
                GrammarFactory.GrammarFactoryEnum.Oh.getFactory(), new OhLineModelListener());
    }

    @Override
    public UniprotkbLineParser<CcLineObject> createCcLineParser() {
        return new DefaultUniprotkbLineParser<CcLineObject, CcLineLexer, CcLineParser>(
                GrammarFactory.GrammarFactoryEnum.Cc.getFactory(), new CcLineModelListener());
    }

    @Override
    public UniprotkbLineParser<SsLineObject> createSsLineParser() {
        return new DefaultUniprotkbLineParser<SsLineObject, SsLineLexer, SsLineParser>(
                GrammarFactory.GrammarFactoryEnum.Ss.getFactory(), new SsLineModelListener());
    }
}

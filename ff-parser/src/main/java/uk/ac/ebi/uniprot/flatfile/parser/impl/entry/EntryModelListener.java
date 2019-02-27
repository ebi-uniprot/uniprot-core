package uk.ac.ebi.uniprot.flatfile.parser.impl.entry;

import org.antlr.v4.runtime.misc.NotNull;
import uk.ac.ebi.uniprot.flatfile.antlr.UniprotParser;
import uk.ac.ebi.uniprot.flatfile.antlr.UniprotParserBaseListener;
import uk.ac.ebi.uniprot.flatfile.parser.ParseTreeObjectExtractor;
import uk.ac.ebi.uniprot.flatfile.parser.UniprotLineParser;
import uk.ac.ebi.uniprot.flatfile.parser.impl.DefaultUniprotLineParserFactory;
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

/**
 * Created with IntelliJ IDEA.
 * User: wudong
 * Date: 08/08/13
 * Time: 12:26
 * To change this template use File | Settings | File Templates.
 */
public class EntryModelListener extends UniprotParserBaseListener implements ParseTreeObjectExtractor<EntryObject> {

	private EntryObject object ;
	private EntryObject.ReferenceObject ref;
	private DefaultUniprotLineParserFactory parserFactory = new DefaultUniprotLineParserFactory();

    @Override
	public void enterEntry(@NotNull UniprotParser.EntryContext ctx) {
		object = new EntryObject();
	}

    private boolean enableKw = true;
    private UniprotLineParser<KwLineObject> kwLineParser = parserFactory.createKwLineParser();
	@Override
	public void exitKw(@NotNull UniprotParser.KwContext ctx) {
        if (this.enableKw){
		    KwLineObject parse = kwLineParser.parse(ctx.getText());
		    object.kw = parse;
        }
	}

    private boolean enableDt = true;
    private UniprotLineParser<DtLineObject> dtLineParser = parserFactory.createDtLineParser();
	@Override
	public void exitDt(@NotNull UniprotParser.DtContext ctx) {
        if (this.enableDt){
		    DtLineObject parse = dtLineParser.parse(ctx.getText());
		    object.dt = parse;
        }
	}

    private boolean enableRp = true;
    private UniprotLineParser<RpLineObject> rpLineParser = parserFactory.createRpLineParser();
	@Override
	public void exitRp(@NotNull UniprotParser.RpContext ctx) {
        if (this.enableReference &&this.enableRp) {
            RpLineObject parse = rpLineParser.parse(ctx.getText());
            ref.rp = parse;
        }
	}

    private boolean enableOs = true;
    private UniprotLineParser<OsLineObject> osLIneParser = parserFactory.createOsLineParser();
	@Override
	public void exitOs(@NotNull UniprotParser.OsContext ctx) {
        if (this.enableOs){
		    OsLineObject parse = osLIneParser.parse(ctx.getText());
		    object.os = parse;
        }
	}

    private boolean enableRn = true;
    private UniprotLineParser<RnLineObject> rnLineParser = parserFactory.createRnLineParser();
	@Override
	public void exitRn(@NotNull UniprotParser.RnContext ctx) {
        if (this.enableReference && this.enableRn){
            RnLineObject parse = rnLineParser.parse(ctx.getText());
            ref.rn = parse;
        }
	}

    private boolean enableRl = true;
    private UniprotLineParser<RlLineObject> rlLineParser = parserFactory.createRlLineParser();
	@Override
	public void exitRl(@NotNull UniprotParser.RlContext ctx) {
        if (this.enableReference && this.enableRl){
		    RlLineObject parse = rlLineParser.parse(ctx.getText());
		    ref.rl = parse;
        }
	}

    private boolean enableSq = true;
    private UniprotLineParser<SqLineObject> seLineParser = parserFactory.createSqLineParser();
	@Override
	public void exitSq(@NotNull UniprotParser.SqContext ctx) {
        if (this.enableSq){
		    SqLineObject parse = seLineParser.parse(ctx.getText());
		    object.sq = parse;
        }
	}

    private boolean enableRg = true;
    private UniprotLineParser<RgLineObject> rgLineParser = parserFactory.createRgLineParser();
	@Override
	public void exitRg(@NotNull UniprotParser.RgContext ctx) {
        if (this.enableReference && this.enableRg){
		    RgLineObject parse = rgLineParser.parse(ctx.getText());
		    ref.rg = parse;
        }
	}

    private boolean enableOx = true;
    private UniprotLineParser<OxLineObject> oxLineParser = parserFactory.createOxLineParser();
	@Override
	public void exitOx(@NotNull UniprotParser.OxContext ctx) {
        if (this.enableOx){
		    OxLineObject parse = oxLineParser.parse(ctx.getText());
		    object.ox = parse;
        }
	}

    private boolean enableRc = true;
    private UniprotLineParser<RcLineObject> rcLineParser = parserFactory.createRcLineParser();
	@Override
	public void exitRc(@NotNull UniprotParser.RcContext ctx) {
        if (this.enableReference && this.enableRc) {
            RcLineObject parse = rcLineParser.parse(ctx.getText());
            ref.rc = parse;
        }
	}

    private boolean enableReference = true;
	@Override
	public void enterReference(@NotNull UniprotParser.ReferenceContext ctx) {
        if (this.enableReference) {
            this.ref = new EntryObject.ReferenceObject();
        }
	}

	@Override
	public void exitReference(@NotNull UniprotParser.ReferenceContext ctx) {
        if (this.enableReference) {
            object.ref.add(this.ref);
            this.ref = null;
        }
	}

    private boolean enableId = true;
    private UniprotLineParser<IdLineObject> idLineParser = parserFactory.createIdLineParser();
	@Override
	public void exitId(@NotNull UniprotParser.IdContext ctx) {
        if (this.enableId) {
            IdLineObject parse = idLineParser.parse(ctx.getText());
            object.id = parse;
        }
	}

    private boolean enableDe = true;
    private UniprotLineParser<DeLineObject> deLineParser = parserFactory.createDeLineParser();
	@Override
	public void exitDe(@NotNull UniprotParser.DeContext ctx) {
        if (this.enableDe) {
            DeLineObject parse = deLineParser.parse(ctx.getText());
            object.de = parse;
        }
	}

    private boolean enableGn = true;
    private UniprotLineParser<GnLineObject> gnLineParser = parserFactory.createGnLineParser();
	@Override
	public void exitGn(@NotNull UniprotParser.GnContext ctx) {
        if (this.enableGn) {
            GnLineObject parse = gnLineParser.parse(ctx.getText());
            object.gn = parse;
        }
	}

    private boolean enableRa = true;
    private UniprotLineParser<RaLineObject> raLineParser = parserFactory.createRaLineParser();
    @Override
	public void exitRa(@NotNull UniprotParser.RaContext ctx) {
        if (this.enableReference && this.enableRa) {
            RaLineObject parse = raLineParser.parse(ctx.getText());
            ref.ra = parse;
        }
	}

    private boolean enablePe = true;
    UniprotLineParser<PeLineObject> peLineParser = parserFactory.createPeLineParser();
	@Override
	public void exitPe(@NotNull UniprotParser.PeContext ctx) {
        if (this.enablePe){
		    PeLineObject parse = peLineParser.parse(ctx.getText());
		    object.pe = parse;
        }
	}

    private boolean enableOc = true;
    private UniprotLineParser<OcLineObject> ocLineParser = parserFactory.createOcLineParser();
	@Override
	public void exitOc(@NotNull UniprotParser.OcContext ctx) {
        if (this.enableOc) {
            OcLineObject parse = ocLineParser.parse(ctx.getText());
            object.oc = parse;
        }
	}
    private boolean enableAc = true;
    private UniprotLineParser<AcLineObject> acLineParser = parserFactory.createAcLineParser();
	@Override
	public void exitAc(@NotNull UniprotParser.AcContext ctx) {
        if (this.enableAc) {
            AcLineObject parse = acLineParser.parse(ctx.getText());
            object.ac = parse;
        }
	}

    private boolean enableRt = true;
    private UniprotLineParser<RtLineObject> rtLineParser = parserFactory.createRtLineParser();
	@Override
	public void exitRt(@NotNull UniprotParser.RtContext ctx) {
        if (this.enableReference && this.enableRt) {
            RtLineObject parse = rtLineParser.parse(ctx.getText());
            ref.rt = parse;
        }
	}

    private boolean enableFt = true;
    private  UniprotLineParser<FtLineObject> ftLineParser = parserFactory.createFtLineParser();
	@Override
	public void exitFt(@NotNull UniprotParser.FtContext ctx) {
        if (this.enableFt) {
            FtLineObject parse = ftLineParser.parse(ctx.getText());
            object.ft = parse;
        }
	}

    private boolean enableRx = true;
    private UniprotLineParser<RxLineObject> rxLineParser = parserFactory.createRxLineParser();
	@Override
	public void exitRx(@NotNull UniprotParser.RxContext ctx) {
        if (this.enableReference && this.enableRx) {
            RxLineObject parse = rxLineParser.parse(ctx.getText());
            ref.rx = parse;
        }
	}

    private boolean enableCc = true;
    private UniprotLineParser<CcLineObject> ccLineParser = parserFactory.createCcLineParser();
	@Override
	public void exitCc(@NotNull UniprotParser.CcContext ctx) {
        if (this.enableCc) {
            CcLineObject parse = ccLineParser.parse(ctx.getText());
            object.cc = parse;
        }
	}

    private boolean enableOg = true;
    private UniprotLineParser<OgLineObject> ogLineParser = parserFactory.createOgLineParser();
	@Override
	public void exitOg(@NotNull UniprotParser.OgContext ctx) {
        if (this.enableOg) {
            OgLineObject parse = ogLineParser.parse(ctx.getText());
            object.og = parse;
        }
	}

    private boolean enableDr = true;
    private UniprotLineParser<DrLineObject> drLineParser = parserFactory.createDrLineParser();
	@Override
	public void exitDr(@NotNull UniprotParser.DrContext ctx) {
        if (this.enableDr) {
            DrLineObject parse = drLineParser.parse(ctx.getText());
            object.dr = parse;
        }
	}

    private boolean enableOh = true;
    private UniprotLineParser<OhLineObject> ohLIneParser = parserFactory.createOhLineParser();
	@Override
	public void exitOh(@NotNull UniprotParser.OhContext ctx) {
        if (this.enableOh) {
            OhLineObject parse = ohLIneParser.parse(ctx.getText());
            object.oh = parse;
        }
	}

    private boolean enableSs = true;
    private UniprotLineParser<SsLineObject> ssLineParser = parserFactory.createSsLineParser();
	@Override
	public void exitSs(@NotNull UniprotParser.SsContext ctx) {
        if (this.enableSs) {
            SsLineObject parse = ssLineParser.parse(ctx.getText());
            object.ss = parse;
        }
	}

	@Override
	public EntryObject getObject() {
        return this.object;
    }
}

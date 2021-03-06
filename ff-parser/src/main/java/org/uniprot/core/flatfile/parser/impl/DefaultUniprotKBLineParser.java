package org.uniprot.core.flatfile.parser.impl;

import org.antlr.v4.runtime.*;
import org.uniprot.core.flatfile.antlr.AcLineParser;
import org.uniprot.core.flatfile.antlr.CcLineLexer;
import org.uniprot.core.flatfile.antlr.CcLineParser;
import org.uniprot.core.flatfile.antlr.DeLineParser;
import org.uniprot.core.flatfile.antlr.DrLineParser;
import org.uniprot.core.flatfile.antlr.DtLineParser;
import org.uniprot.core.flatfile.antlr.FtLineLexer;
import org.uniprot.core.flatfile.antlr.FtLineParser;
import org.uniprot.core.flatfile.antlr.GnLineParser;
import org.uniprot.core.flatfile.antlr.IdLineParser;
import org.uniprot.core.flatfile.antlr.KwLineParser;
import org.uniprot.core.flatfile.antlr.OcLineParser;
import org.uniprot.core.flatfile.antlr.OgLineParser;
import org.uniprot.core.flatfile.antlr.OhLineParser;
import org.uniprot.core.flatfile.antlr.OsLineParser;
import org.uniprot.core.flatfile.antlr.OxLineParser;
import org.uniprot.core.flatfile.antlr.PeLineParser;
import org.uniprot.core.flatfile.antlr.RaLineParser;
import org.uniprot.core.flatfile.antlr.RcLineParser;
import org.uniprot.core.flatfile.antlr.RgLineParser;
import org.uniprot.core.flatfile.antlr.RlLineParser;
import org.uniprot.core.flatfile.antlr.RnLineParser;
import org.uniprot.core.flatfile.antlr.RpLineParser;
import org.uniprot.core.flatfile.antlr.RtLineParser;
import org.uniprot.core.flatfile.antlr.RxLineParser;
import org.uniprot.core.flatfile.antlr.SqLineParser;
import org.uniprot.core.flatfile.antlr.SsLineParser;
import org.uniprot.core.flatfile.antlr.UniprotLexer;
import org.uniprot.core.flatfile.antlr.UniprotParser;
import org.uniprot.core.flatfile.parser.*;
import org.uniprot.core.flatfile.parser.impl.cc.CcLineErrorListener;
import org.uniprot.core.flatfile.parser.impl.entry.EntryErrorListener;
import org.uniprot.core.flatfile.parser.impl.ft.FtLineErrorListener;

/**
 * Created with IntelliJ IDEA. User: wudong Date: 08/08/13 Time: 15:30 To change this template use
 * File | Settings | File Templates.
 */
public class DefaultUniprotKBLineParser<T, L extends Lexer, P extends Parser>
        implements UniprotKBLineParser<T> {

    private final GrammarFactory<L, P> factory;
    private final P parser;

    private ParseTreeObjectExtractor<T> listener;
    private static final org.slf4j.Logger logger =
            org.slf4j.LoggerFactory.getLogger(DefaultUniprotKBLineParser.class);

    private L lexer;

    protected DefaultUniprotKBLineParser(
            GrammarFactory<L, P> factory, ParseTreeObjectExtractor<T> listener) {
        this.factory = factory;
        this.listener = listener;

        this.parser = this.createParserFromInput(new ANTLRInputStream(), factory);
        this.parser.addParseListener(this.listener);
    }

    /**
     * Create parser directly from input.
     *
     * @param in
     * @param factory
     * @return
     */
    public P createParserFromInput(CharStream in, GrammarFactory<L, P> factory) {
        lexer = factory.createLexer(in);
        lexer.removeErrorListeners();

        if (lexer instanceof FtLineLexer) {
            lexer.addErrorListener(new FtLineErrorListener());
        } else if (lexer instanceof CcLineLexer) {
            lexer.addErrorListener(new CcLineErrorListener());
        } else if (lexer instanceof UniprotLexer) {
            lexer.addErrorListener(new EntryErrorListener());
        } else {
            lexer.addErrorListener(new DefaultErrorListener());
        }

        CommonTokenStream commonTokenStream = new CommonTokenStream(lexer);
        P parser = factory.createParser(commonTokenStream);
        parser.removeErrorListeners();

        if (parser instanceof FtLineParser) {
            parser.addErrorListener(new FtLineErrorListener());
        } else if (parser instanceof CcLineParser) {
            parser.addErrorListener(new CcLineErrorListener());
        } else if (parser instanceof UniprotParser) {
            parser.addErrorListener(new EntryErrorListener());
        } else {
            parser.addErrorListener(new DefaultErrorListener());
        }

        return parser;
    }

    @Override
    public T parse(String s) {
        ANTLRInputStream antlrInputStream = new ANTLRInputStream(s);
        this.lexer.setInputStream(antlrInputStream);

        CommonTokenStream commonTokenStream = new CommonTokenStream(this.lexer);
        this.parser.setTokenStream(commonTokenStream);

        T t = processWithParser(this.parser, s);
        return t;
    }

    protected T processWithParser(P parser, String originString) {

        // remove thie reflection to improve error handling and parsing speed.
        try {
            if (parser instanceof AcLineParser) {
                ((AcLineParser) parser).ac_ac();
            } else if (parser instanceof CcLineParser) {
                ((CcLineParser) parser).cc_cc();
            } else if (parser instanceof DeLineParser) {
                ((DeLineParser) parser).de_de();
            } else if (parser instanceof DrLineParser) {
                ((DrLineParser) parser).dr_dr();
            } else if (parser instanceof DtLineParser) {
                ((DtLineParser) parser).dt_dt();
            } else if (parser instanceof FtLineParser) {
                ((FtLineParser) parser).ft_ft();
            } else if (parser instanceof GnLineParser) {
                ((GnLineParser) parser).gn_gn();
            } else if (parser instanceof IdLineParser) {
                ((IdLineParser) parser).id_id();
            } else if (parser instanceof KwLineParser) {
                ((KwLineParser) parser).kw_kw();
            } else if (parser instanceof OcLineParser) {
                ((OcLineParser) parser).oc_oc();
            } else if (parser instanceof OgLineParser) {
                ((OgLineParser) parser).og_og();
            } else if (parser instanceof OhLineParser) {
                ((OhLineParser) parser).oh_oh();
            } else if (parser instanceof OsLineParser) {
                ((OsLineParser) parser).os_os();
            } else if (parser instanceof OxLineParser) {
                ((OxLineParser) parser).ox_ox();
            } else if (parser instanceof PeLineParser) {
                ((PeLineParser) parser).pe_pe();
            } else if (parser instanceof RaLineParser) {
                ((RaLineParser) parser).ra_ra();
            } else if (parser instanceof RcLineParser) {
                ((RcLineParser) parser).rc_rc();
            } else if (parser instanceof RgLineParser) {
                ((RgLineParser) parser).rg_rg();
            } else if (parser instanceof RlLineParser) {
                ((RlLineParser) parser).rl_rl();
            } else if (parser instanceof RnLineParser) {
                ((RnLineParser) parser).rn_rn();
            } else if (parser instanceof RpLineParser) {
                ((RpLineParser) parser).rp_rp();
            } else if (parser instanceof RtLineParser) {
                ((RtLineParser) parser).rt_rt();
            } else if (parser instanceof RxLineParser) {
                ((RxLineParser) parser).rx_rx();
            } else if (parser instanceof SqLineParser) {
                ((SqLineParser) parser).sq_sq();
            } else if (parser instanceof SsLineParser) {
                ((SsLineParser) parser).ss_ss();
            } else if (parser instanceof UniprotParser) {
                ((UniprotParser) parser).entry();
            } else {
                throw new RuntimeException(
                        "Parser's type : " + parser.getClass() + " is not recognized.");
            }
        } catch (NullPointerException e) {
            throw new RuntimeException(
                    "Unexpected Error, Checking the format of string: " + originString, e);
        } catch (ParseException e) {
            logger.trace(
                    "Parsing Error In {} for string: \n{}",
                    parser.getClass().getSimpleName(),
                    originString);
            throw new RuntimeException("ParseException", e);
        }

        return listener.getObject();
    }
}

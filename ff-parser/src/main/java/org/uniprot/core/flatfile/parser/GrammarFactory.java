package org.uniprot.core.flatfile.parser;

import java.io.Serializable;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.atn.ATN;
import org.antlr.v4.runtime.atn.LexerATNSimulator;
import org.antlr.v4.runtime.atn.ParserATNSimulator;
import org.antlr.v4.runtime.atn.PredictionContextCache;
import org.antlr.v4.runtime.dfa.DFA;
import org.uniprot.core.flatfile.antlr.*;
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

/**
 * Created with IntelliJ IDEA. User: wudong Date: 08/08/13 Time: 15:34 To change this template use
 * File | Settings | File Templates.
 */
public interface GrammarFactory<L extends Lexer, P extends Parser> extends Serializable {

    // public static final String packageName = "uk.ac.ebi.uniprot.parser.antlr";

    public static enum GrammarFactoryEnum implements Serializable {
        Uniprot,
        Ac,
        Id,
        Dt,
        Kw,
        Dr,
        Sq,
        Gn,
        Pe,
        Os,
        Og,
        Rn,
        Rt,
        Rp,
        Ra,
        Rg,
        Rc,
        Rx,
        De,
        Rl,
        Ft,
        Oc,
        Ox,
        Oh,
        Cc,
        Ss;

        @SuppressWarnings("rawtypes")
        private GrammarFactory factory;

        @SuppressWarnings("rawtypes")
        private GrammarFactory createFactory() {
            //       final String name = this.name();

            // final String lexerName = packageName + "." + name +
            // (name.equals("Uniprot")?"":"Line") + "Lexer";
            // final String parserName = packageName + "." + name +
            // (name.equals("Uniprot")?"":"Line") +"Parser";

            return new GrammarFactory() {
                @Override
                public Lexer createLexer(CharStream in) {
                    switch (GrammarFactoryEnum.this) {
                        case Uniprot:
                            return wrapLexer(new UniprotLexer(in), UniprotLexer._ATN);
                        case Ac:
                            return wrapLexer(new AcLineLexer(in), AcLineLexer._ATN);
                        case Id:
                            return wrapLexer(new IdLineLexer(in), IdLineLexer._ATN);
                        case Dt:
                            return wrapLexer(new DtLineLexer(in), DtLineLexer._ATN);
                        case Kw:
                            return wrapLexer(new KwLineLexer(in), KwLineLexer._ATN);
                        case Dr:
                            return wrapLexer(new DrLineLexer(in), DrLineLexer._ATN);
                        case Sq:
                            return wrapLexer(new SqLineLexer(in), SqLineLexer._ATN);
                        case Gn:
                            return wrapLexer(new GnLineLexer(in), GnLineLexer._ATN);
                        case Pe:
                            return wrapLexer(new PeLineLexer(in), PeLineLexer._ATN);
                        case Os:
                            return wrapLexer(new OsLineLexer(in), OsLineLexer._ATN);
                        case Og:
                            return wrapLexer(new OgLineLexer(in), OgLineLexer._ATN);
                        case Rn:
                            return wrapLexer(new RnLineLexer(in), RnLineLexer._ATN);
                        case Rt:
                            return wrapLexer(new RtLineLexer(in), RtLineLexer._ATN);
                        case Rp:
                            return wrapLexer(new RpLineLexer(in), RpLineLexer._ATN);
                        case Ra:
                            return wrapLexer(new RaLineLexer(in), RaLineLexer._ATN);
                        case Rg:
                            return wrapLexer(new RgLineLexer(in), RgLineLexer._ATN);
                        case Rc:
                            return wrapLexer(new RcLineLexer(in), RcLineLexer._ATN);
                        case Rx:
                            return wrapLexer(new RxLineLexer(in), RxLineLexer._ATN);
                        case De:
                            return wrapLexer(new DeLineLexer(in), DeLineLexer._ATN);
                        case Rl:
                            return wrapLexer(new RlLineLexer(in), RlLineLexer._ATN);
                        case Ft:
                            return wrapLexer(new FtLineLexer(in), FtLineLexer._ATN);
                        case Oc:
                            return wrapLexer(new OcLineLexer(in), OcLineLexer._ATN);
                        case Ox:
                            return wrapLexer(new OxLineLexer(in), OxLineLexer._ATN);
                        case Oh:
                            return wrapLexer(new OhLineLexer(in), OhLineLexer._ATN);
                        case Cc:
                            return wrapLexer(new CcLineLexer(in), CcLineLexer._ATN);
                        case Ss:
                            return wrapLexer(new SsLineLexer(in), SsLineLexer._ATN);

                        default:
                            throw new RuntimeException(
                                    "Lexer is not defined for: " + GrammarFactoryEnum.this);
                    }
                }

                @Override
                public Parser createParser(CommonTokenStream tokens) {
                    switch (GrammarFactoryEnum.this) {
                        case Uniprot:
                            return new UniprotParser(tokens);
                        case Ac:
                            return new AcLineParser(tokens);
                        case Id:
                            return new IdLineParser(tokens);
                        case Dt:
                            return new DtLineParser(tokens);
                        case Kw:
                            return new KwLineParser(tokens);
                        case Dr:
                            return new DrLineParser(tokens);
                        case Sq:
                            return new SqLineParser(tokens);
                        case Gn:
                            return new GnLineParser(tokens);
                        case Pe:
                            return new PeLineParser(tokens);
                        case Os:
                            return new OsLineParser(tokens);
                        case Og:
                            return new OgLineParser(tokens);
                        case Rn:
                            return new RnLineParser(tokens);
                        case Rt:
                            return new RtLineParser(tokens);
                        case Rp:
                            return new RpLineParser(tokens);
                        case Ra:
                            return new RaLineParser(tokens);
                        case Rg:
                            return new RgLineParser(tokens);
                        case Rc:
                            return new RcLineParser(tokens);
                        case Rx:
                            return new RxLineParser(tokens);
                        case De:
                            return new DeLineParser(tokens);
                        case Rl:
                            return new RlLineParser(tokens);
                        case Ft:
                            return new FtLineParser(tokens);
                        case Oc:
                            return new OcLineParser(tokens);
                        case Ox:
                            return new OxLineParser(tokens);
                        case Oh:
                            return new OhLineParser(tokens);
                        case Cc:
                            return new CcLineParser(tokens);
                        case Ss:
                            return new SsLineParser(tokens);

                        default:
                            throw new RuntimeException(
                                    "Parser is not defined for: " + GrammarFactoryEnum.this);
                    }
                }

                //				@Override
                //				public String getTopRuleName() {
                //					if (name.equals("Uniprot")) return "entry";
                //					return (name + "_" + name).toLowerCase();
                //				}
            };
        }

        @SuppressWarnings("rawtypes")
        public synchronized GrammarFactory getFactory() {
            if (factory == null) {
                factory = createFactory();
            }
            return factory;
        }

        public static Lexer wrapLexer(Lexer lexer, ATN _ATN) {
            DFA[] dfAfromATN = createDFAfromATN(_ATN);
            LexerATNSimulator lexerATNSimulator =
                    new LexerATNSimulator(lexer, _ATN, dfAfromATN, new PredictionContextCache());
            lexer.setInterpreter(lexerATNSimulator);
            return lexer;
        }

        public static Parser wrapParser(Parser parser, ATN _ATN) {
            DFA[] dfAfromATN = createDFAfromATN(_ATN);
            ParserATNSimulator parserATNSimulator =
                    new ParserATNSimulator(parser, _ATN, dfAfromATN, new PredictionContextCache());
            parser.setInterpreter(parserATNSimulator);
            return parser;
        }

        public static DFA[] createDFAfromATN(ATN _ATN) {
            DFA[] dfas = new DFA[_ATN.getNumberOfDecisions()];
            for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
                dfas[i] = new DFA(_ATN.getDecisionState(i), i);
            }
            return dfas;
        }
    }

    public L createLexer(CharStream in);

    public P createParser(CommonTokenStream tokens);

    // public String getTopRuleName();

}

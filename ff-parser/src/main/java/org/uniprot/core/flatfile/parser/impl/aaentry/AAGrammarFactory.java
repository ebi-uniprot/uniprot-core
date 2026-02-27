package org.uniprot.core.flatfile.parser.impl.aaentry;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.atn.ATN;
import org.antlr.v4.runtime.atn.LexerATNSimulator;
import org.antlr.v4.runtime.atn.PredictionContextCache;
import org.antlr.v4.runtime.dfa.DFA;
import org.uniprot.core.flatfile.antlr.AcLineLexer;
import org.uniprot.core.flatfile.antlr.AcLineParser;
import org.uniprot.core.flatfile.antlr.CcLineLexer;
import org.uniprot.core.flatfile.antlr.CcLineParser;
import org.uniprot.core.flatfile.antlr.DeLineLexer;
import org.uniprot.core.flatfile.antlr.DeLineParser;
import org.uniprot.core.flatfile.antlr.FtLineLexer;
import org.uniprot.core.flatfile.antlr.FtLineParser;
import org.uniprot.core.flatfile.antlr.GnLineLexer;
import org.uniprot.core.flatfile.antlr.GnLineParser;
import org.uniprot.core.flatfile.antlr.KwLineLexer;
import org.uniprot.core.flatfile.antlr.KwLineParser;
import org.uniprot.core.flatfile.antlr.UniprotAALexer;
import org.uniprot.core.flatfile.antlr.UniprotAAParser;




public interface AAGrammarFactory<L extends Lexer, P extends Parser>  {
    enum GrammarFactoryEnum {
        UNIPROT_AA, AC, KW, GN, DE, FT, CC;

        private AAGrammarFactory factory;

        private AAGrammarFactory createFactory() {

            return new AAGrammarFactory() {
                @Override
                public Lexer createLexer(CharStream in) {
                    switch (GrammarFactoryEnum.this) {
                    case UNIPROT_AA:
                        return wrapLexer(new UniprotAALexer(in), UniprotAALexer._ATN);
                    case AC:
                        return wrapLexer(new AcLineLexer(in), AcLineLexer._ATN);
                                     
                    case KW:
                        return wrapLexer(new KwLineLexer(in), KwLineLexer._ATN);
                                       
                    case GN:
                        return wrapLexer(new GnLineLexer(in), GnLineLexer._ATN);

                    case DE:
                        return wrapLexer(new DeLineLexer(in), DeLineLexer._ATN);
                    
                    case FT:
                        return wrapLexer(new FtLineLexer(in), FtLineLexer._ATN);
                   
                    case CC:
                        return wrapLexer(new CcLineLexer(in), CcLineLexer._ATN);
                   
                    default:
                        throw new RuntimeException("Lexer is not defined for: " + GrammarFactoryEnum.this);
                    }

                }

                @Override
                public Parser createParser(CommonTokenStream tokens) {
                    switch (GrammarFactoryEnum.this) {
                    case UNIPROT_AA:
                        return new UniprotAAParser(tokens);
                    case AC:
                        return new AcLineParser(tokens);
                    case KW:
                        return new KwLineParser(tokens);
                   
                    case GN:
                        return new GnLineParser(tokens);                   
                    
                    case DE:
                        return new DeLineParser(tokens);
                    
                    case FT:
                        return new FtLineParser(tokens);
                    
                    case CC:
                        return new CcLineParser(tokens);
                    
                    default:
                        throw new RuntimeException("Parser is not defined for: " + GrammarFactoryEnum.this);
                    }
                }
            };
        }

        public synchronized AAGrammarFactory getFactory() {
            if (factory == null) {
                factory = createFactory();
            }
            return factory;
        }

        public static Lexer wrapLexer(Lexer lexer, ATN atn) {
            DFA[] dfAfromATN = createDFAfromATN(atn);
            LexerATNSimulator lexerATNSimulator = new LexerATNSimulator(lexer, atn, dfAfromATN,
                    new PredictionContextCache());
            lexer.setInterpreter(lexerATNSimulator);
            return lexer;
        }

        public static DFA[] createDFAfromATN(ATN atn) {
            DFA[] dfas = new DFA[atn.getNumberOfDecisions()];
            for (int i = 0; i < atn.getNumberOfDecisions(); i++) {
                dfas[i] = new DFA(atn.getDecisionState(i), i);
            }
            return dfas;
        }
    }

    public L createLexer(CharStream in);

    public P createParser(CommonTokenStream tokens);

}

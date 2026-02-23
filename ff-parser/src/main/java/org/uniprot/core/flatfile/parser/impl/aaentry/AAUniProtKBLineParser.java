package org.uniprot.core.flatfile.parser.impl.aaentry;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.Parser;
import org.uniprot.core.flatfile.antlr.AcLineParser;
import org.uniprot.core.flatfile.antlr.CcLineLexer;
import org.uniprot.core.flatfile.antlr.CcLineParser;
import org.uniprot.core.flatfile.antlr.DeLineParser;
import org.uniprot.core.flatfile.antlr.FtLineLexer;
import org.uniprot.core.flatfile.antlr.FtLineParser;
import org.uniprot.core.flatfile.antlr.GnLineParser;
import org.uniprot.core.flatfile.antlr.KwLineParser;
import org.uniprot.core.flatfile.antlr.UniprotAALexer;
import org.uniprot.core.flatfile.antlr.UniprotAAParser;
import org.uniprot.core.flatfile.parser.DefaultErrorListener;
import org.uniprot.core.flatfile.parser.ParseTreeObjectExtractor;
import org.uniprot.core.flatfile.parser.UniprotKBLineParser;
import org.uniprot.core.flatfile.parser.impl.cc.CcLineErrorListener;
import org.uniprot.core.flatfile.parser.impl.entry.EntryErrorListener;
import org.uniprot.core.flatfile.parser.impl.ft.FtLineErrorListener;



public class AAUniProtKBLineParser <T, L extends Lexer, P extends Parser> implements UniprotKBLineParser<T> {

    private final P parser;

    private ParseTreeObjectExtractor<T> listener;

    private L lexer;

    protected AAUniProtKBLineParser(AAGrammarFactory<L, P> factory, ParseTreeObjectExtractor<T> listener) {
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
    public P createParserFromInput(CharStream in, AAGrammarFactory<L, P> factory) {
        lexer = factory.createLexer(in);
        lexer.removeErrorListeners();

        if (lexer instanceof FtLineLexer) {
            lexer.addErrorListener(new FtLineErrorListener());
        } else if (lexer instanceof CcLineLexer) {
            lexer.addErrorListener(new CcLineErrorListener());
        } else if (lexer instanceof UniprotAALexer) {
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
        } else if (parser instanceof UniprotAAParser) {
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

    private T processWithParserNoHeader(P parser) {

        if (parser instanceof CcLineParser lineParser) {
            lineParser.cc_lines();
        } else {
            throw new RuntimeException("Parser's type : " + parser.getClass()
                    + " is not recognized or not supported for No header parse.");
        }
        return listener.getObject();
    }
    protected T processWithParser(P parser, String originString) {

       
        try {
            if (parser instanceof AcLineParser lineParser) {
                lineParser.ac_ac();
            } else if (parser instanceof CcLineParser lineParser) {
                lineParser.cc_cc();
            } else if (parser instanceof DeLineParser lineParser) {
                lineParser.de_de();            
            } else if (parser instanceof FtLineParser lineParser) {
                lineParser.ft_ft();
            } else if (parser instanceof GnLineParser lineParser) {
                lineParser.gn_gn();
           
            } else if (parser instanceof KwLineParser lineParser) {
                lineParser.kw_kw();
          
            } else if (parser instanceof UniprotAAParser uniprotParser) {
                uniprotParser.entry();
            } else {
                throw new RuntimeException("Parser's type : " + parser.getClass() + " is not recognized.");
            }
        } catch (NullPointerException e) {
            throw new RuntimeException("Unexpected Error, Checking the format of string: " + originString, e);
        }
            
        return listener.getObject();
    }

}

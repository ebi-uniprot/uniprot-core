package org.uniprot.core.flatfile.parser.impl.aaentry;

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
import org.uniprot.core.flatfile.parser.UniprotKBLineParser;
import org.uniprot.core.flatfile.parser.impl.ac.AcLineModelListener;
import org.uniprot.core.flatfile.parser.impl.ac.AcLineObject;
import org.uniprot.core.flatfile.parser.impl.cc.CcLineModelListener;
import org.uniprot.core.flatfile.parser.impl.cc.cclineobject.CcLineObject;
import org.uniprot.core.flatfile.parser.impl.de.DeLineModelListener;
import org.uniprot.core.flatfile.parser.impl.de.DeLineObject;
import org.uniprot.core.flatfile.parser.impl.ft.FtLineModelListener;
import org.uniprot.core.flatfile.parser.impl.ft.FtLineObject;
import org.uniprot.core.flatfile.parser.impl.gn.GnLineModelListener;
import org.uniprot.core.flatfile.parser.impl.gn.GnLineObject;
import org.uniprot.core.flatfile.parser.impl.kw.KwLineModelListener;
import org.uniprot.core.flatfile.parser.impl.kw.KwLineObject;

public class DefaultAAUniProtKBLineParserFactory implements AAUniProtKBLineParserFactory {

    @Override
    public UniprotKBLineParser<AAEntryObject> createEntryParser() {
        return new AAUniProtKBLineParser<AAEntryObject, UniprotAALexer, UniprotAAParser>(
                AAGrammarFactory.GrammarFactoryEnum.UNIPROT_AA.getFactory(),
                new AAEntryModelListener());
    }

    @Override
    public UniprotKBLineParser<AcLineObject> createAcLineParser() {
        return new AAUniProtKBLineParser<AcLineObject, AcLineLexer, AcLineParser>(
                AAGrammarFactory.GrammarFactoryEnum.AC.getFactory(), new AcLineModelListener());
    }


    @Override
    public UniprotKBLineParser<DeLineObject> createDeLineParser() {
        return new AAUniProtKBLineParser<DeLineObject, DeLineLexer, DeLineParser>(
                AAGrammarFactory.GrammarFactoryEnum.DE.getFactory(), new DeLineModelListener());
    }


    @Override
    public UniprotKBLineParser<GnLineObject> createGnLineParser() {
        return new AAUniProtKBLineParser<GnLineObject, GnLineLexer, GnLineParser>(
                AAGrammarFactory.GrammarFactoryEnum.GN.getFactory(), new GnLineModelListener());
    }



    @Override
    public UniprotKBLineParser<KwLineObject> createKwLineParser() {
        return new AAUniProtKBLineParser<KwLineObject, KwLineLexer, KwLineParser>(
                AAGrammarFactory.GrammarFactoryEnum.KW.getFactory(), new KwLineModelListener());
    }




    @Override
    public UniprotKBLineParser<FtLineObject> createFtLineParser() {
        return new AAUniProtKBLineParser<FtLineObject, FtLineLexer, FtLineParser>(
                AAGrammarFactory.GrammarFactoryEnum.FT.getFactory(), new FtLineModelListener());
    }


    @Override
    public UniprotKBLineParser<CcLineObject> createCcLineParser() {
        return new AAUniProtKBLineParser<CcLineObject, CcLineLexer, CcLineParser>(
                AAGrammarFactory.GrammarFactoryEnum.CC.getFactory(), new CcLineModelListener());
    }


}


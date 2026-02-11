package org.uniprot.core.flatfile.parser.impl.aaentry;

import org.uniprot.core.flatfile.antlr.UniprotAAParser;
import org.uniprot.core.flatfile.antlr.UniprotAAParserBaseListener;
import org.uniprot.core.flatfile.parser.ParseTreeObjectExtractor;
import org.uniprot.core.flatfile.parser.UniprotKBLineParser;
import org.uniprot.core.flatfile.parser.impl.ac.AcLineObject;
import org.uniprot.core.flatfile.parser.impl.cc.cclineobject.CcLineObject;
import org.uniprot.core.flatfile.parser.impl.de.DeLineObject;
import org.uniprot.core.flatfile.parser.impl.ft.FtLineObject;
import org.uniprot.core.flatfile.parser.impl.gn.GnLineObject;
import org.uniprot.core.flatfile.parser.impl.kw.KwLineObject;

public class AAEntryModelListener extends UniprotAAParserBaseListener implements ParseTreeObjectExtractor<AAEntryObject> {

    private AAEntryObject object;
    private DefaultAAUniProtKBLineParserFactory parserFactory = new DefaultAAUniProtKBLineParserFactory();

    @Override
    public void enterEntry(UniprotAAParser.EntryContext ctx) {
        object = new AAEntryObject();
    }
    
    @Override
    public AAEntryObject getObject() {
        return this.object;
    }    

    private boolean enableAc = true;
    private UniprotKBLineParser<AcLineObject> acLineParser = parserFactory.createAcLineParser();

    @Override
    public void exitAc(UniprotAAParser.AcContext ctx) {
        
        if (this.enableAc) {
            AcLineObject parse = acLineParser.parse(ctx.getText());
            object.ac = parse;
        }
    }

    private boolean enableDe = true;
    private UniprotKBLineParser<DeLineObject> deLineParser = parserFactory.createDeLineParser();

    @Override
    public void exitDe(UniprotAAParser.DeContext ctx) {
       
        if (this.enableDe) {
            DeLineObject parse = deLineParser.parse(ctx.getText());
            object.de = parse;
        }
    }
    
    
    private boolean enableGn = true;
    private UniprotKBLineParser<GnLineObject> gnLineParser = parserFactory.createGnLineParser();

    @Override
    public void exitGn(UniprotAAParser.GnContext ctx) {
       
        if (this.enableGn) {
            GnLineObject parse = gnLineParser.parse(ctx.getText());
            object.gn = parse;
        }
    }
    
    private boolean enableCc = true;
    private UniprotKBLineParser<CcLineObject> ccLineParser = parserFactory.createCcLineParser();

    @Override
    public void exitCc(UniprotAAParser.CcContext ctx) {
        
        if (this.enableCc) {
            CcLineObject parse = ccLineParser.parse(ctx.getText());
            object.cc = parse;
        }
    }
    
    private boolean enableKw = true;
    private UniprotKBLineParser<KwLineObject> kwLineParser = parserFactory.createKwLineParser();

    @Override
    public void exitKw(UniprotAAParser.KwContext ctx) {
       
        if (this.enableKw) {
            KwLineObject parse = kwLineParser.parse(ctx.getText());
            object.kw = parse;
        }
    }

    private boolean enableFt = true;
    private UniprotKBLineParser<FtLineObject> ftLineParser = parserFactory.createFtLineParser();

    @Override
    public void exitFt(UniprotAAParser.FtContext ctx) {
       
        if (this.enableFt) {
            FtLineObject parse = ftLineParser.parse(ctx.getText());
            object.ft = parse;
        }
    }



}


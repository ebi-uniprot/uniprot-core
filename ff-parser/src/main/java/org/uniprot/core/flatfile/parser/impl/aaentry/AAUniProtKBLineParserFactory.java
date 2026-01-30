package org.uniprot.core.flatfile.parser.impl.aaentry;

import org.uniprot.core.flatfile.parser.UniprotKBLineParser;
import org.uniprot.core.flatfile.parser.impl.ac.AcLineObject;
import org.uniprot.core.flatfile.parser.impl.cc.cclineobject.CcLineObject;
import org.uniprot.core.flatfile.parser.impl.de.DeLineObject;
import org.uniprot.core.flatfile.parser.impl.ft.FtLineObject;
import org.uniprot.core.flatfile.parser.impl.gn.GnLineObject;
import org.uniprot.core.flatfile.parser.impl.kw.KwLineObject;

public interface AAUniProtKBLineParserFactory {
    public UniprotKBLineParser<AcLineObject> createAcLineParser();

    UniprotKBLineParser<DeLineObject> createDeLineParser();

    UniprotKBLineParser<GnLineObject> createGnLineParser();

    UniprotKBLineParser<KwLineObject> createKwLineParser();

    UniprotKBLineParser<FtLineObject> createFtLineParser();
 
    UniprotKBLineParser<CcLineObject> createCcLineParser();

    UniprotKBLineParser<AAEntryObject> createEntryParser();

   
}

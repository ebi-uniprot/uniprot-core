package org.uniprot.core.flatfile.parser.impl.aaentry;

import org.uniprot.core.flatfile.parser.impl.ac.AcLineObject;
import org.uniprot.core.flatfile.parser.impl.cc.cclineobject.CcLineObject;
import org.uniprot.core.flatfile.parser.impl.de.DeLineObject;
import org.uniprot.core.flatfile.parser.impl.ft.FtLineObject;
import org.uniprot.core.flatfile.parser.impl.gn.GnLineObject;
import org.uniprot.core.flatfile.parser.impl.kw.KwLineObject;

@SuppressWarnings("java:S1104")
public class AAEntryObject {
    public AcLineObject ac;
    public CcLineObject cc;
    public DeLineObject de;
    public FtLineObject ft;
    public GnLineObject gn;
    public KwLineObject kw;
   
}

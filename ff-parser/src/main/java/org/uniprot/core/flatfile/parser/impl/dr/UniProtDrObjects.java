package org.uniprot.core.flatfile.parser.impl.dr;

import org.uniprot.core.uniprotkb.InternalLine;
import org.uniprot.core.uniprotkb.xdb.UniProtKBCrossReference;

import java.util.ArrayList;
import java.util.List;

public class UniProtDrObjects {
    public List<UniProtKBCrossReference> drObjects = new ArrayList<>();
    public List<InternalLine> ssProsites = null;
}

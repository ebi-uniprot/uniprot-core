package org.uniprot.core.flatfile.parser.impl.dr;

import java.util.ArrayList;
import java.util.List;

import org.uniprot.core.uniprotkb.InternalLine;
import org.uniprot.core.uniprotkb.xdb.UniProtkbCrossReference;

public class UniProtDrObjects {
    public List<UniProtkbCrossReference> drObjects = new ArrayList<>();
    public List<InternalLine> ssProsites = null;
}

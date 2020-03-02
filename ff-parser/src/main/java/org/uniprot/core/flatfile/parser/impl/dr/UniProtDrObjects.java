package org.uniprot.core.flatfile.parser.impl.dr;

import java.util.ArrayList;
import java.util.List;

import org.uniprot.core.uniprot.InternalLine;
import org.uniprot.core.uniprot.xdb.UniProtCrossReference;

public class UniProtDrObjects {
    public List<UniProtCrossReference> drObjects = new ArrayList<>();
    public List<InternalLine> ssProsites = null;
}

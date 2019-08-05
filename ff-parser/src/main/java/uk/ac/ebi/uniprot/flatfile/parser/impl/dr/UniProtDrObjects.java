package uk.ac.ebi.uniprot.flatfile.parser.impl.dr;

import java.util.ArrayList;
import java.util.List;

import org.uniprot.core.uniprot.InternalLine;
import org.uniprot.core.uniprot.xdb.UniProtDBCrossReference;

public class UniProtDrObjects {
	public List<UniProtDBCrossReference> drObjects =new ArrayList<>();
	public List<InternalLine> ssProsites =null;
}

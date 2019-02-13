package uk.ac.ebi.uniprot.flatfile.parser.impl.dr;

import uk.ac.ebi.uniprot.domain.uniprot.InternalLine;
import uk.ac.ebi.uniprot.domain.uniprot.xdb.UniProtDBCrossReference;

import java.util.ArrayList;
import java.util.List;

public class UniProtDrObjects {
	public List<UniProtDBCrossReference> drObjects =new ArrayList<>();
	public List<InternalLine> ssProsites =null;
}

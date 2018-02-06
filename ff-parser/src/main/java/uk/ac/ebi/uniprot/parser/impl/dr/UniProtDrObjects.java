package uk.ac.ebi.uniprot.parser.impl.dr;

import java.util.ArrayList;
import java.util.List;

import uk.ac.ebi.uniprot.domain.uniprot.InternalLine;
import uk.ac.ebi.uniprot.domain.uniprot.xdb.UniProtDBCrossReference;

public class UniProtDrObjects {
	public List<UniProtDBCrossReference> drObjects =new ArrayList<>();
	public List<InternalLine> ssProsites =null;
}

package uk.ac.ebi.uniprot.flatfile.parser.impl.rn;

import uk.ac.ebi.uniprot.flatfile.parser.impl.EvidenceInfo;
import uk.ac.ebi.uniprot.flatfile.parser.impl.HasEvidenceInfo;

/**
 * Created with IntelliJ IDEA.
 * User: wudong
 * Date: 08/08/13
 * Time: 11:50
 * To change this template use File | Settings | File Templates.
 */
public class RnLineObject implements HasEvidenceInfo{
    public int number;

	public EvidenceInfo evidenceInfo = new EvidenceInfo();

	@Override
	public EvidenceInfo getEvidenceInfo() {
		return evidenceInfo;
	}
}

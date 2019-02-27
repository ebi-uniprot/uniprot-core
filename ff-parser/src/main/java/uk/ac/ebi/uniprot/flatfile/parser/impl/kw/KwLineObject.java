package uk.ac.ebi.uniprot.flatfile.parser.impl.kw;

import uk.ac.ebi.uniprot.flatfile.parser.impl.EvidenceInfo;
import uk.ac.ebi.uniprot.flatfile.parser.impl.HasEvidenceInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: wudong
 * Date: 08/08/13
 * Time: 11:50
 * To change this template use File | Settings | File Templates.
 */
public class KwLineObject implements HasEvidenceInfo{
    public List<String> keywords = new ArrayList<String>();

	public EvidenceInfo evidenceInfo = new EvidenceInfo();

	@Override
	public EvidenceInfo getEvidenceInfo() {
		return evidenceInfo;
	}

}

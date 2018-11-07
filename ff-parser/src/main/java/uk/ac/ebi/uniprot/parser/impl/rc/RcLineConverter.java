package uk.ac.ebi.uniprot.parser.impl.rc;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import uk.ac.ebi.uniprot.domain.uniprot.ReferenceComment;
import uk.ac.ebi.uniprot.domain.uniprot.ReferenceCommentType;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.factory.UniProtReferenceFactory;
import uk.ac.ebi.uniprot.parser.Converter;
import uk.ac.ebi.uniprot.parser.impl.EvidenceHelper;
import uk.ac.ebi.uniprot.parser.impl.EvidenceCollector;

public class RcLineConverter  extends EvidenceCollector  implements Converter<RcLineObject, List<ReferenceComment>> {
	@Override
	public List<ReferenceComment> convert(RcLineObject f) {
		List<ReferenceComment> sss = new ArrayList<> ();
		for (RcLineObject.RC rc:f.rcs){
			ReferenceCommentType type =convert(rc.tokenType);
			Map<Object, List<Evidence> > evidences = EvidenceHelper.convert(rc.getEvidenceInfo());
			this.addAll( evidences.values());
			for(String val: rc.values){
				ReferenceComment refComment = UniProtReferenceFactory.INSTANCE.createReferenceComment(type, val, evidences.get(val));

				sss.add(refComment);
			}
			
		}
		return sss;
	}

	private ReferenceCommentType convert(RcLineObject.RcTokenEnum type){
		switch(type){
		case STRAIN:
			return ReferenceCommentType.STRAIN;
		case PLASMID:
			return ReferenceCommentType.PLASMID;
		case TRANSPOSON:
			return ReferenceCommentType.TRANSPOSON;
		case TISSUE:
			return ReferenceCommentType.TISSUE;
		default:
			return ReferenceCommentType.STRAIN;
		}
	
	}
}

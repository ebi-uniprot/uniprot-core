package uk.ac.ebi.uniprot.parser.impl.cc;

import uk.ac.ebi.uniprot.domain.uniprot.comment.FreeTextComment;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.EvidencedValue;
import uk.ac.ebi.uniprot.parser.ffwriter.impl.FFLineWrapper;

import java.util.ArrayList;
import java.util.List;

import static uk.ac.ebi.uniprot.parser.ffwriter.impl.FFLineConstant.*;

public class CCFreeTextCommentLineBuilder extends CCLineBuilderAbstr<FreeTextComment> {

	@Override
	protected List<String> buildCommentLines(FreeTextComment comment,  boolean includeFFMarkings, boolean showEvidence) {
		StringBuilder sb = new StringBuilder();

		addFlatFileMarkingsIfRequired(includeFFMarkings, sb);
		
		addCommentTypeName(comment, sb);
		boolean first =true;
		for(EvidencedValue text: comment.getTexts()){
			if(!first)
				sb.append(SPACE);
			sb.append(text.getValue());
		//	sb.append(buildCommentStatus(text));
			appendIfNot(sb, '.');
			sb = addEvidence(text, sb, showEvidence, STOP);
			first =false;
		
		}
		List<String> lines = new ArrayList<>();
		if (includeFFMarkings) {
			lines.addAll(FFLineWrapper.buildLines(sb.toString(), SEPS,
					linePrefix, LINE_LENGTH));
		}else{
			lines.add(sb.toString());
		}
		return lines;
	}

}

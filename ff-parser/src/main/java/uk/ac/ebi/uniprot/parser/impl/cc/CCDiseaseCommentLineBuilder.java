package uk.ac.ebi.uniprot.parser.impl.cc;

import java.util.ArrayList;
import java.util.List;

import uk.ac.ebi.uniprot.domain.uniprot.EvidencedValue;
import uk.ac.ebi.uniprot.domain.uniprot.comments.Disease;
import uk.ac.ebi.uniprot.domain.uniprot.comments.DiseaseComment;
import uk.ac.ebi.uniprot.ffwriter.line.FFLineWrapper;

import static uk.ac.ebi.uniprot.ffwriter.line.FFLineConstant.*;

public class CCDiseaseCommentLineBuilder extends CCLineBuilderAbstr<DiseaseComment> {

	@Override
	protected List<String> buildCommentLines(DiseaseComment comment,
			boolean includeFFMarkings, boolean showEvidence) {
		StringBuilder sb = new StringBuilder();
		if(includeFFMarkings) {
			addFlatFileMarkingsIfRequired(includeFFMarkings, sb);
		}
			addCommentTypeName(comment, sb);


		//if the disease is defined then in needs to be represented in the string
		boolean needSpace =false;
		if(comment.hasDefinedDisease()) {
			sb.append(createDiseaseString(comment.getDisease()));
			sb =addEvidence(comment.getDisease().getDescription(), sb, showEvidence, STOP);
			needSpace =true;
		}

		//append the note
		if(comment.getNote().isPresent()) {
			if(needSpace)
				sb.append(SPACE);
			sb.append(NOTE);
			boolean isfirst =true;
			for(EvidencedValue val: comment.getNote().get().getTexts()){
				if(!isfirst)
					sb.append(SPACE);
				sb.append(val.getValue());
				appendIfNot(sb, STOP);
				sb =addEvidence(val, sb, showEvidence, STOP);
				isfirst =false;
			}
		}
		if (includeFFMarkings) {
			return   FFLineWrapper.buildLines(sb.toString(), SEPS, linePrefix, LINE_LENGTH);
		}else{
			List<String> lines =new ArrayList<>();
			lines.add(sb.toString());
			return lines;
		}
	}
	private String createDiseaseString(Disease disease) {
		String diseaseString = "";

		diseaseString += disease.getDiseaseId().getValue() + " "
				+ "(" + disease.getAcronym() + ") "
				+ "[" + disease.getReference().getDiseaseReferenceType().toDisplayName() + ":" + disease.getReference().getDiseaseReferenceId()+ "]: "
				+ disease.getDescription().getValue();

		return diseaseString;
	}

}

package uk.ac.ebi.uniprot.flatfile.parser.impl.cc;

import uk.ac.ebi.uniprot.domain.uniprot.comment.Interaction;
import uk.ac.ebi.uniprot.domain.uniprot.comment.InteractionComment;
import uk.ac.ebi.uniprot.domain.uniprot.comment.InteractionType;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author jieluo
 *CC   -!- INTERACTION:
      |CC       G5EC23:hcf-1; NbExp=2; IntAct=EBI-318108, EBI-4480523;
      |CC       Q11184:let-756; NbExp=3; IntAct=EBI-318108, EBI-3843983;
      |CC       Q10666:pop-1; NbExp=2; IntAct=EBI-318108, EBI-317870;
      |CC       Q21921:sir-2.1; NbExp=3; IntAct=EBI-318108, EBI-966082;
 */

public class CCInteractionCommentLineBuilder extends CCLineBuilderAbstr<InteractionComment> {

	@Override
	protected List<String> buildCommentLines(InteractionComment comment, boolean includeFlatFileMarkings, boolean showEvidence, boolean includeCommentType){
		List<String> lines = new ArrayList<>();
		//first line
		if(includeCommentType)
			lines.add(buildStart(comment, includeFlatFileMarkings));

		for (Interaction act : comment.getInteractions()) {
			StringBuilder sb = new StringBuilder();
			if (includeFlatFileMarkings)
				sb.append(this.linePrefix);
			if (act.getType().equals(InteractionType.SELF)) {
				sb.append("Self");
			} else {
				sb.append(act.getUniProtAccession().getValue());
				sb.append(":");
				sb.append(act.getGeneName());
			}
			if (act.getType().equals(InteractionType.XENO)) {
				sb.append(" (xeno)");
			}
			sb.append("; NbExp=");
			sb.append(act.getNumberOfExperiments());
			sb.append("; IntAct");
			sb.append("=");
			sb.
			append(act.getFirstInteractor().getValue());
			if (act.getSecondInteractor() != null) {
				sb.append(", ");
				sb.append(act.getSecondInteractor().getValue());
			}
			sb.append(";");
			lines.add(sb.toString());

		}
		return lines;
	}
}

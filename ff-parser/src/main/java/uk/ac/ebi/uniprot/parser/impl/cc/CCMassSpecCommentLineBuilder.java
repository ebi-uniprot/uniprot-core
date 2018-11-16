package uk.ac.ebi.uniprot.parser.impl.cc;

import static uk.ac.ebi.uniprot.parser.ffwriter.impl.FFLineConstant.LINE_LENGTH;
import static uk.ac.ebi.uniprot.parser.ffwriter.impl.FFLineConstant.SEMI_COMA;
import static uk.ac.ebi.uniprot.parser.ffwriter.impl.FFLineConstant.SEPARATOR_COMA;
import static uk.ac.ebi.uniprot.parser.ffwriter.impl.FFLineConstant.SEPARATOR_SEMICOMA;
import static uk.ac.ebi.uniprot.parser.ffwriter.impl.FFLineConstant.SEPS;
import static uk.ac.ebi.uniprot.parser.ffwriter.impl.FFLineConstant.SPACE;

import java.util.ArrayList;
import java.util.List;

import uk.ac.ebi.uniprot.domain.uniprot.comment.MassSpectrometryComment;
import uk.ac.ebi.uniprot.domain.uniprot.comment.MassSpectrometryRange;
import uk.ac.ebi.uniprot.parser.ffwriter.impl.FFLineWrapper;
/**
 * 
 * @author jieluo
 *CC   -!- MASS SPECTROMETRY: Mass=514.2; Method=Electrospray; Range=51-54,
                  |CC       71-74, 91-94, 132-135, 148-151; Note=The measured mass is that of
                  |CC       RPGW-amide; Source=PubMed:10799681;
 */
public class CCMassSpecCommentLineBuilder extends CCLineBuilderAbstr<MassSpectrometryComment> {

	private static final String EVIDENCE = "Evidence=";
	private static final String RANGE2 = "Range=";
	private static final String METHOD = "Method=";
	private static final String MASS_ERROR = "Mass_error=";
	private static final String MASS = "Mass=";
	@Override
	protected List<String> buildCommentLines(MassSpectrometryComment comment, boolean includeFlatFileMarkings, boolean showEvidence){
		List<String> lines = new ArrayList<>();
		StringBuilder sb = new StringBuilder("");
		if(includeFlatFileMarkings){
			addFlatFileMarkingsIfRequired(includeFlatFileMarkings, sb);
		}
		sb.append(comment.getCommentType().toDisplayName());
		sb.append(": ");
		
		sb.append(MASS);
		sb.append(getSigDig(comment.getMolWeight()));
		if(comment.getMolWeightError().isPresent() &&
				 Math.abs(comment.getMolWeightError().get()) > 10 * Double.MIN_VALUE){
			sb.append(SEPARATOR_SEMICOMA);
			sb.append(MASS_ERROR);
			
			sb.append(getSigDig(comment.getMolWeightError().get()));
		}
//		if(comment.getNote().isPresent()) {
			sb.append(SEPARATOR_SEMICOMA);
			sb.append(METHOD);
			sb.append(comment.getMethod().getValue());

	//	}
		if ((comment.getRanges() != null) &&(comment.getRanges().size()>0) ){
			boolean isfirst =true;
			sb.append(SEPARATOR_SEMICOMA);
			sb.append(RANGE2);
			for (MassSpectrometryRange range : comment.getRanges()) {
				if(!isfirst)
					sb.append(SEPARATOR_COMA);

				if (range.isStartUnknown()) {
					sb.append("?");
				} else {
					sb.append(range.getStart());

				}
				sb.append("-");

				if (range.isEndUnknown()) {
					sb.append("?");
				} else {
					sb.append(range.getEnd());

				}

				if (range.hasIsoformId()) {
					sb.append(" (");
					sb.append(range.getIsoformId());
					sb.append(")");
				}
				isfirst = false;
			}
			sb.append(SEMI_COMA);
		}
		if(comment.getNote().isPresent()) {
			sb.append(SPACE);
			sb.append(NOTE);
			sb.append(comment.getNote().get());
			sb.append(SEMI_COMA);

		}
		if(!comment.getEvidences().isEmpty())
		if ((comment.getEvidences() != null) && (comment.getEvidences().size() > 0)) {
			sb.append(SPACE);
			sb.append(EVIDENCE);
			sb.append("{");
			for (int i = 0; i < comment.getEvidences().size(); i++) {
				if (i > 0)
					sb.append(SEPARATOR_COMA);
			
				sb.append(comment.getEvidences().get(i).getValue());
			}
			sb.append("}");
			sb.append(SEMI_COMA);
		} 
		if(includeFlatFileMarkings){
			lines.addAll(FFLineWrapper.buildLines(sb.toString(), SEPS, this.linePrefix, LINE_LENGTH));
		}else{
			lines.add(sb.toString());
		}
		return lines;
	}
	
	public static String getSigDig(Double number){
		String temp = number.toString();
		if (temp.indexOf(".") > 0){
			while (temp.endsWith("0")){
				temp = temp.substring(0, temp.length()-1);
			}
			if (temp.endsWith(".")){
				temp = temp.substring(0, temp.length()-1);
			}
		}
		return temp;
	}
}

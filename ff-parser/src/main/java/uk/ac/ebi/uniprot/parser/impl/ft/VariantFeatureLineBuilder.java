package uk.ac.ebi.uniprot.parser.impl.ft;

import static uk.ac.ebi.uniprot.parser.ffwriter.impl.FFLineConstant.DASH;
import static uk.ac.ebi.uniprot.parser.ffwriter.impl.FFLineConstant.LINE_LENGTH;
import static uk.ac.ebi.uniprot.parser.ffwriter.impl.FFLineConstant.SEPARATOR;
import static uk.ac.ebi.uniprot.parser.ffwriter.impl.FFLineConstant.SPACE;
import static uk.ac.ebi.uniprot.parser.ffwriter.impl.FFLineConstant.STOP;

import java.util.ArrayList;
import java.util.List;

import uk.ac.ebi.uniprot.domain.uniprot.feature.Feature;

import uk.ac.ebi.uniprot.parser.ffwriter.impl.FFLineWrapper;
import uk.ac.ebi.uniprot.parser.ffwriter.impl.LineBuilderHelper;
public class VariantFeatureLineBuilder 
extends AbstractFeatureLineBuilder{
	@Override
	protected List<String> buildLines(Feature f, boolean includeFFMarkings, 
			boolean addEvidence) {
		StringBuilder sb = new StringBuilder();
		sb.append(FTLineBuilderHelper.buildFeatureCommon(f, includeFFMarkings));
	//	StringBuilder extra =FTLineBuilderHelper.buildExtra(f);
		String evIds ="";
		if(addEvidence){
			evIds = LineBuilderHelper.export(f.getEvidences());
		}
		if(includeFFMarkings){
				sb.append(FTLineBuilderHelper.SPACE_LOCATION_DESCRIPTION);
		}else{
			sb.append(SPACE);
		}
	//	sb.append(extra);
		List<String> lines = new ArrayList<>();
		List<String> lines2 = FTLineBuilderHelper.addAlternativeSequence(sb, f, includeFFMarkings );
		for(int i=0; i<lines2.size(); i++){
			if(i== (lines2.size()-1)){
				sb =new StringBuilder(lines2.get(i));
			}else{
				lines.add(lines2.get(i));
			}
		}
		String report = getReports(f);
		if (!report.isEmpty()) {
		    sb.append(" (");
		    sb.append(report);
		    sb.append(")");
		}
		sb.append(STOP);
		if(evIds.length()>0){
			sb.append(evIds);
			sb.append(STOP);
		}
		
		String[] seps = {SEPARATOR, DASH};
		
		List<String> lines3 =FFLineWrapper.buildLines(sb.toString(),  seps, 
				FTLineBuilderHelper.FT_LINE_PREFIX_2, LINE_LENGTH);
		if(includeFFMarkings)
			lines.addAll(lines3);
		else
			lines.add(sb.toString());
		StringBuilder featureId = FTLineBuilderHelper.getFeatureId(f, includeFFMarkings);
		if(featureId.length()>0){
			lines.add(featureId.toString());
		}
		return lines;
	}	
	private String getReports(Feature feature) {
		return feature.getDescription().getValue();
//		StringBuilder temp = new StringBuilder();
//		boolean first = true;
//
//		for (String c : feature.getAlternativeSequence().getReport().getValue()) {
//			if (first) {
//				first = false;
//			} else {
//				temp.append("; ");
//			}
//			temp.append(c);
//		}
//		return temp.toString();
	}
}

package uk.ac.ebi.uniprot.parser.impl.ft;

import static uk.ac.ebi.uniprot.parser.ffwriter.impl.FFLineConstant.DASH;
import static uk.ac.ebi.uniprot.parser.ffwriter.impl.FFLineConstant.SEPARATOR;
import static uk.ac.ebi.uniprot.parser.ffwriter.impl.FFLineConstant.SPACE;
import static uk.ac.ebi.uniprot.parser.ffwriter.impl.FFLineConstant.STOP;

import java.util.ArrayList;
import java.util.List;

import uk.ac.ebi.uniprot.domain.uniprot.feature.Feature;
import uk.ac.ebi.uniprot.parser.ffwriter.impl.FFLineConstant;
import uk.ac.ebi.uniprot.parser.ffwriter.impl.FFLineWrapper;
import uk.ac.ebi.uniprot.parser.ffwriter.impl.LineBuilderHelper;

public class VarSeqFeatureBuilder 
extends AbstractFeatureLineBuilder {
	
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
		List<String> lines2 = FTLineBuilderHelper.addAlternativeSequence(sb, f ,includeFFMarkings);
		for(int i=0; i<lines2.size(); i++){
			if(i== (lines2.size()-1)){
				sb =new StringBuilder(lines2.get(i));
			}else{
				lines.add(lines2.get(i));
			}
		}
		if(hasAltSequenceReport(f)) {
			sb.append(" (");
			sb.append(getStringIsoformsVarSplicFeature(f));
			sb.append(")");

		}
		sb.append(STOP);
		if(evIds.length()>0){
			sb.append(evIds);
			sb.append(STOP);
		}
		
		String[] seps = {SEPARATOR, DASH};
		List<String> lines3 =FFLineWrapper.buildLines(sb.toString(),  seps, 
				FTLineBuilderHelper.FT_LINE_PREFIX_2, FFLineConstant.LINE_LENGTH);
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
	private String getStringIsoformsVarSplicFeature(Feature feature) {
		return feature.getDescription().getValue();
//	        StringBuilder temp = new StringBuilder();
//	        int numberReports = feature.getAlternativeSequence().getReport().getValue().size();
//	        if (numberReports == 0) return "";
//
//	        int count = 0;
//	        for (String isoform : feature.getAlternativeSequence().getReport().getValue()) {
//	            if (count == 0) {
//	                temp.append("in ");
//	            } else {
//	                if (count < numberReports - 1) {
//	                    temp.append(", ");
//	                } else {
//	                    temp.append(" and ");
//	                }
//	            }
//	            temp.append("isoform ");
//	            temp.append(isoform);
//	            count++;
//
//	        }
//	        return temp.toString();
	    }
}

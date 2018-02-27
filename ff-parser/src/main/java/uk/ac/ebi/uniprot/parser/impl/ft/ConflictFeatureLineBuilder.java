package uk.ac.ebi.uniprot.parser.impl.ft;

import static uk.ac.ebi.uniprot.ffwriter.line.FFLineConstant.COLON;
import static uk.ac.ebi.uniprot.ffwriter.line.FFLineConstant.DASH;
import static uk.ac.ebi.uniprot.ffwriter.line.FFLineConstant.LINE_LENGTH;
import static uk.ac.ebi.uniprot.ffwriter.line.FFLineConstant.SEPARATOR;
import static uk.ac.ebi.uniprot.ffwriter.line.FFLineConstant.SPACE;
import static uk.ac.ebi.uniprot.ffwriter.line.FFLineConstant.STOP;

import java.util.ArrayList;
import java.util.List;

import uk.ac.ebi.uniprot.domain.feature.ConflictFeature;
import uk.ac.ebi.uniprot.domain.uniprot.UniProtFeature;
import uk.ac.ebi.uniprot.ffwriter.line.FFLineWrapper;
import uk.ac.ebi.uniprot.ffwriter.line.LineBuilderHelper;

public class ConflictFeatureLineBuilder 
extends AbstractFeatureLineBuilder<UniProtFeature<ConflictFeature>> {
	
	@Override
	protected List<String> buildLines(UniProtFeature<ConflictFeature> f, boolean includeFFMarkings, 
			boolean addEvidence) {
		StringBuilder sb = new StringBuilder();
		sb.append(FTLineBuilderHelper.buildFeatureCommon(f.getFeature(), includeFFMarkings));
		StringBuilder extra =FTLineBuilderHelper.buildExtra(f.getFeature());
		String evIds ="";
		if(addEvidence){
			evIds = LineBuilderHelper.export(f.getEvidences());
		}
		if(includeFFMarkings){
				sb.append(FTLineBuilderHelper.SPACE_LOCATION_DESCRIPTION);
		}else{
			sb.append(SPACE);
		}
		sb.append(extra);
		List<String> lines = new ArrayList<>();
		List<String> lines2 = FTLineBuilderHelper.addAlternativeSequence(sb, f.getFeature(),  includeFFMarkings);
		for(int i=0; i<lines2.size(); i++){
			if(i== (lines2.size()-1)){
				sb =new StringBuilder(lines2.get(i));
			}else{
				lines.add(lines2.get(i));
			}
		}
		sb.append(" (");
		sb.append(getStringConflictReports(f.getFeature()));
		sb.append(")");
		sb.append(STOP);
		if(evIds.length()>0){
			sb.append(evIds);
			sb.append(STOP);
		}
		String[] seps = {SEPARATOR, DASH, COLON};
		
		List<String> lines3 =FFLineWrapper.buildLines(sb.toString(),  seps,
				FTLineBuilderHelper.FT_LINE_PREFIX_2, LINE_LENGTH);
		if(includeFFMarkings)
			lines.addAll(lines3);
		else
			lines.add(sb.toString());
		StringBuilder featureId = FTLineBuilderHelper.getFeatureId(f.getFeature(), includeFFMarkings);
		if(featureId.length()>0){
			lines.add(featureId.toString());
		}
		return lines;
	}
	 private String getStringConflictReports(ConflictFeature feature) {
		 
	        StringBuilder temp = new StringBuilder();
	        boolean first = true;
	        if((feature.getReport() ==null) || (feature.getReport().getValue().isEmpty())) {
	        		return "";
	        }
	        int size = feature.getReport().getValue().size();
	        for(int i =0; i<size; i++) {
	        		if(i==0) {
	        			temp.append("in Ref. ");
	        		}else if (i==(size-1)) {
	        			temp.append(" and ");
	        		}else {
	        			temp.append(", ");
	        		}
	        		 temp.append(feature.getReport().getValue().get(i));
	        }
	        
	        return temp.toString();
	    }

}

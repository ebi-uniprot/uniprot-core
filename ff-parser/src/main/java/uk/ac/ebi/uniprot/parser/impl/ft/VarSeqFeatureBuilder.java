package uk.ac.ebi.uniprot.parser.impl.ft;

import static uk.ac.ebi.uniprot.ffwriter.line.FFLineConstant.COLON;
import static uk.ac.ebi.uniprot.ffwriter.line.FFLineConstant.DASH;
import static uk.ac.ebi.uniprot.ffwriter.line.FFLineConstant.SEPARATOR;
import static uk.ac.ebi.uniprot.ffwriter.line.FFLineConstant.SPACE;
import static uk.ac.ebi.uniprot.ffwriter.line.FFLineConstant.STOP;

import java.util.ArrayList;
import java.util.List;

import uk.ac.ebi.uniprot.domain.feature.VarSeqFeature;
import uk.ac.ebi.uniprot.domain.uniprot.UniProtFeature;
import uk.ac.ebi.uniprot.ffwriter.line.FFLineConstant;
import uk.ac.ebi.uniprot.ffwriter.line.FFLineWrapper;
import uk.ac.ebi.uniprot.ffwriter.line.LineBuilderHelper;

public class VarSeqFeatureBuilder 
extends AbstractFeatureLineBuilder<UniProtFeature<VarSeqFeature>> {
	
	@Override
	protected List<String> buildLines(UniProtFeature<VarSeqFeature> f, boolean includeFFMarkings, 
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
		List<String> lines2 = FTLineBuilderHelper.addAlternativeSequence(sb, f.getFeature() ,includeFFMarkings);
		for(int i=0; i<lines2.size(); i++){
			if(i== (lines2.size()-1)){
				sb =new StringBuilder(lines2.get(i));
			}else{
				lines.add(lines2.get(i));
			}
		}
		if((f.getFeature().getReport() !=null) &&
		  (f.getFeature().getReport().getValue().size() > 0)) {
			sb.append(" (");
			sb.append(getStringIsoformsVarSplicFeature(f.getFeature()));
			sb.append(")");

		}
		sb.append(STOP);
		if(evIds.length()>0){
			sb.append(evIds);
			sb.append(STOP);
		}
		
		String[] seps = {SEPARATOR, DASH, COLON};
		List<String> lines3 =FFLineWrapper.buildLines(sb.toString(),  seps, 
				FTLineBuilderHelper.FT_LINE_PREFIX_2, FFLineConstant.LINE_LENGTH);
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
	private String getStringIsoformsVarSplicFeature(VarSeqFeature feature) {
		
	        StringBuilder temp = new StringBuilder();
	        int numberReports = feature.getReport().getValue().size();
	        if (numberReports == 0) return "";

	        int count = 0;
	        for (String isoform : feature.getReport().getValue()) {
	            if (count == 0) {
	                temp.append("in ");
	            } else {
	                if (count < numberReports - 1) {
	                    temp.append(", ");
	                } else {
	                    temp.append(" and ");
	                }
	            }
	            temp.append("isoform ");
	            temp.append(isoform);
	            count++;

	        }
	        return temp.toString();
	    }
}

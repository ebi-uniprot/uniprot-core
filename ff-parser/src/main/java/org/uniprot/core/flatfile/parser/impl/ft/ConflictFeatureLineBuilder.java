package org.uniprot.core.flatfile.parser.impl.ft;

import static org.uniprot.core.flatfile.writer.impl.FFLineConstant.*;

import java.util.ArrayList;
import java.util.List;

import org.uniprot.core.flatfile.writer.impl.FFLineWrapper;
import org.uniprot.core.flatfile.writer.impl.LineBuilderHelper;
import org.uniprot.core.uniprot.feature.Feature;

public class ConflictFeatureLineBuilder extends AbstractFeatureLineBuilder {

    @Override
    protected List<String> buildLines(Feature f, boolean includeFFMarkings, boolean addEvidence) {
        StringBuilder sb = new StringBuilder();
        sb.append(FTLineBuilderHelper.buildFeatureCommon(f, includeFFMarkings));
        //	StringBuilder extra =FTLineBuilderHelper.buildExtra(f);
        String evIds = "";
        if (addEvidence) {
            evIds = LineBuilderHelper.export(f.getEvidences());
        }
        if (includeFFMarkings) {
            sb.append(FTLineBuilderHelper.SPACE_LOCATION_DESCRIPTION);
        } else {
            sb.append(SPACE);
        }
        //	sb.append(extra);
        List<String> lines = new ArrayList<>();
        List<String> lines2 = FTLineBuilderHelper.addAlternativeSequence(sb, f, includeFFMarkings);
        for (int i = 0; i < lines2.size(); i++) {
            if (i == (lines2.size() - 1)) {
                sb = new StringBuilder(lines2.get(i));
            } else {
                lines.add(lines2.get(i));
            }
        }
        sb.append(" (");
        sb.append(getStringConflictReports(f));
        sb.append(")");
        sb.append(STOP);
        if (evIds.length() > 0) {
            sb.append(evIds);
            sb.append(STOP);
        }
        String[] seps = {SEPARATOR, DASH};

        List<String> lines3 =
                FFLineWrapper.buildLines(
                        sb.toString(), seps, FTLineBuilderHelper.FT_LINE_PREFIX_2, LINE_LENGTH);
        if (includeFFMarkings) lines.addAll(lines3);
        else lines.add(sb.toString());
        StringBuilder featureId = FTLineBuilderHelper.getFeatureId(f, includeFFMarkings);
        if (featureId.length() > 0) {
            lines.add(featureId.toString());
        }
        return lines;
    }

    private String getStringConflictReports(Feature feature) {
        return feature.getDescription().getValue();
        //	        StringBuilder temp = new StringBuilder();
        //	        boolean first = true;
        //	        if(feature.getAlternativeSequence().getReport().getValue().isEmpty())
        //	        	return "";
        //
        //	        int size = feature.getAlternativeSequence().getReport().getValue().size();
        //	        for(int i =0; i<size; i++) {
        //	        		if(i==0) {
        //	        			temp.append("in Ref. ");
        //	        		}else if (i==(size-1)) {
        //	        			temp.append(" and ");
        //	        		}else {
        //	        			temp.append(", ");
        //	        		}
        //	        		 temp.append(feature.getAlternativeSequence().getReport().getValue().get(i));
        //	        }
        //
        //	        return temp.toString();
    }
}

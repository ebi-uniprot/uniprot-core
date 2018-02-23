package uk.ac.ebi.uniprot.parser.impl.rp;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import uk.ac.ebi.uniprot.ffwriter.line.FFLineWrapper;
import uk.ac.ebi.uniprot.ffwriter.line.LineType;
import uk.ac.ebi.uniprot.ffwriter.line.RLine;

import static uk.ac.ebi.uniprot.ffwriter.line.FFLineConstant.*;
public class RPLineBuilder
	implements RLine <List<String> >{
	 private final static Pattern RP_BAD_COLON_POSITION_SEPARATION = Pattern.compile(";(\\d+)-");
	 private final String linePrefix = LineType.RP + DEFAUT_LINESPACE;
	@Override
	public List<String> buildLine(List<String> f, boolean includeFFMarkup, boolean showEvidence) {
		StringBuilder rpLine = new StringBuilder();
		if (includeFFMarkup)
			rpLine.append(linePrefix);
		for (int i = 0; i < f.size(); i++) {
			String rpValue = f.get(i);

			rpLine.append(correctPositionSemiColonSeparators(rpValue));
			if (i < f.size() - 2) {
				rpLine.append(SEPARATOR_COMA);
			} else if (i < f.size() - 1) {
				rpLine.append(COMA).append(SEPARATOR__CAP_AND);
			} else {
				rpLine.append(STOP);
			}
		}
		List<String> lines =new ArrayList<>();
		if(includeFFMarkup){
			lines.addAll( FFLineWrapper.buildLines(rpLine.toString(), SEPARATOR, linePrefix));
		}else{

			lines.add(rpLine.toString());			
		}
		return lines;

	}
	 private String correctPositionSemiColonSeparators(String rpValue) {
	        Matcher mat = RP_BAD_COLON_POSITION_SEPARATION.matcher(rpValue);
	        StringBuilder b = new StringBuilder();
	        int start = 0;
	        int end = 0;
	        int lastEnd = 0;
	        while(mat.find()) {
	            start = mat.start();
	            end = mat.end();
	            b.append(rpValue.substring(lastEnd, start)).append("; ").append(mat.group(1)).append("-");            
	            lastEnd = end;
	        }
	        
	        b.append(rpValue.substring(lastEnd));
	        return b.toString();
	    }
}

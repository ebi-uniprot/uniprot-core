package uk.ac.ebi.uniprot.ffwriter.line.impl;


import java.time.format.DateTimeFormatter;
import java.util.Locale;

import uk.ac.ebi.uniprot.domain.uniprot.HasEvidences;
import uk.ac.ebi.uniprot.ffwriter.line.LineBuilderHelper;
import uk.ac.ebi.uniprot.ffwriter.line.FFLine;
import uk.ac.ebi.uniprot.ffwriter.line.FFLineBuilder;
import uk.ac.ebi.uniprot.ffwriter.line.LineType;

import static uk.ac.ebi.uniprot.ffwriter.line.FFLineConstant.*;

public abstract class FFLineBuilderAbstr <T> implements FFLineBuilder<T> {
	protected abstract FFLine buildLine(T f, boolean showEvidence);
	final protected  DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy",  Locale.ENGLISH);
	
	protected final LineType lineType ;
	protected final String linePrefix ;
	

	
	
	public FFLineBuilderAbstr(LineType lineType){
		this(lineType, DEFAUT_LINESPACE);
	}
	public FFLineBuilderAbstr(LineType lineType, String linespace){
		this.lineType = lineType;
		this.linePrefix = lineType + linespace;
	}
	@Override
	public FFLine build( T f) {
		return buildLine(f, false);
	}

	@Override
	public FFLine buildWithEvidence(T f) {
		return buildLine(f, true);
	}
	protected StringBuilder addEvidences(StringBuilder sb, HasEvidences he, boolean showEvidence){
		if(!showEvidence)
			return sb;
		sb.append(LineBuilderHelper.export(he.getEvidences()));
		return sb;
	
	}
	

	protected void appendIfNot(StringBuilder sb, char c){
		
		  if(sb.charAt(sb.length()-1) !=c){
			  sb.append(c);
		  }		  
	}
	protected void appendIfNot(StringBuilder sb, String c){
		if(!sb.toString().endsWith(c)){
			sb.append(c);
		}		  
	}
}

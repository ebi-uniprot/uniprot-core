package uk.ac.ebi.uniprot.parser.impl.oc;

import static uk.ac.ebi.uniprot.parser.ffwriter.impl.FFLineConstant.*;

import java.util.List;
import uk.ac.ebi.uniprot.domain.taxonomy.TaxonName;
import uk.ac.ebi.uniprot.parser.ffwriter.FFLine;
import uk.ac.ebi.uniprot.parser.ffwriter.FFLineBuilder;
import uk.ac.ebi.uniprot.parser.ffwriter.LineType;
import uk.ac.ebi.uniprot.parser.ffwriter.impl.FFLineBuilderAbstr;
import uk.ac.ebi.uniprot.parser.ffwriter.impl.FFLineWrapper;
import uk.ac.ebi.uniprot.parser.ffwriter.impl.FFLines;

public class OCLineBuilder extends FFLineBuilderAbstr< List<TaxonName> > implements FFLineBuilder< List<TaxonName> > {
	private static final String UNCLASSIFIED = "unclassified";
	
	public OCLineBuilder(){
		super(LineType.OC);
	}
	
	@Override
	protected FFLine buildLine(List<TaxonName> f, boolean showEvidence){
		StringBuilder sb = build(f, showEvidence, true );
		List<String> lls = FFLineWrapper.buildLines(sb, SEPARATOR_SEMICOMA, linePrefix);
		return FFLines.create(lls);
	}
	@Override
	public String buildString(List<TaxonName> f) {
		return build(f, false, false).toString();
	}
	
	@Override
	public String buildStringWithEvidence(List<TaxonName> f) {
		return build(f, true, false).toString();
	}
	
	private StringBuilder build(List<TaxonName> f, boolean showEvidence, boolean includeFFMarkup){
		StringBuilder sb = new StringBuilder();
		if(includeFFMarkup){
			sb.append(linePrefix);
		}
		if ((f.size() != 0)) {
			boolean isFirst =true;
			for (TaxonName taxon:f){
				if(!isFirst){
					sb.append(SEPARATOR_SEMICOMA);
				}
				sb.append(taxon.getValue());
				isFirst =false;
			}
		}else{
			sb.append(UNCLASSIFIED);
		}
		sb.append(STOP);
		return sb;
	}
}

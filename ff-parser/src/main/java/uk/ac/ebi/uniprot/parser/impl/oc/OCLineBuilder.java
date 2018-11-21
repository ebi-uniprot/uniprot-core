package uk.ac.ebi.uniprot.parser.impl.oc;

import static uk.ac.ebi.uniprot.parser.ffwriter.impl.FFLineConstant.*;

import java.util.List;
import uk.ac.ebi.uniprot.domain.taxonomy.OrganismName;
import uk.ac.ebi.uniprot.parser.ffwriter.FFLine;
import uk.ac.ebi.uniprot.parser.ffwriter.FFLineBuilder;
import uk.ac.ebi.uniprot.parser.ffwriter.LineType;
import uk.ac.ebi.uniprot.parser.ffwriter.impl.FFLineBuilderAbstr;
import uk.ac.ebi.uniprot.parser.ffwriter.impl.FFLineWrapper;
import uk.ac.ebi.uniprot.parser.ffwriter.impl.FFLines;

public class OCLineBuilder extends FFLineBuilderAbstr< List<OrganismName> > implements FFLineBuilder< List<OrganismName> > {
	private static final String UNCLASSIFIED = "unclassified";
	
	public OCLineBuilder(){
		super(LineType.OC);
	}
	
	@Override
	protected FFLine buildLine(List<OrganismName> f, boolean showEvidence){
		StringBuilder sb = build(f, showEvidence, true );
		List<String> lls = FFLineWrapper.buildLines(sb, SEPARATOR_SEMICOMA, linePrefix);
		return FFLines.create(lls);
	}
	@Override
	public String buildString(List<OrganismName> f) {
		return build(f, false, false).toString();
	}
	
	@Override
	public String buildStringWithEvidence(List<OrganismName> f) {
		return build(f, true, false).toString();
	}
	
	private StringBuilder build(List<OrganismName> f, boolean showEvidence, boolean includeFFMarkup){
		StringBuilder sb = new StringBuilder();
		if(includeFFMarkup){
			sb.append(linePrefix);
		}
		if ((f.size() != 0)) {
			boolean isFirst =true;
			for (OrganismName taxon:f){
				if(!isFirst){
					sb.append(SEPARATOR_SEMICOMA);
				}
				sb.append(taxon.getName());
				isFirst =false;
			}
		}else{
			sb.append(UNCLASSIFIED);
		}
		sb.append(STOP);
		return sb;
	}
}

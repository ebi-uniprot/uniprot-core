package uk.ac.ebi.uniprot.parser.impl.os;

import static uk.ac.ebi.uniprot.parser.ffwriter.impl.FFLineConstant.*;

import java.util.List;
import java.util.stream.Collectors;

import uk.ac.ebi.uniprot.domain.taxonomy.Organism;
import uk.ac.ebi.uniprot.parser.ffwriter.FFLine;
import uk.ac.ebi.uniprot.parser.ffwriter.FFLineBuilder;
import uk.ac.ebi.uniprot.parser.ffwriter.LineType;
import uk.ac.ebi.uniprot.parser.ffwriter.impl.FFLineBuilderAbstr;
import uk.ac.ebi.uniprot.parser.ffwriter.impl.FFLineWrapper;
import uk.ac.ebi.uniprot.parser.ffwriter.impl.FFLines;

public class OSLineBuilder extends FFLineBuilderAbstr<Organism> implements FFLineBuilder<Organism> {
;
	
	public OSLineBuilder(){
		super(LineType.OS);
	}
	
	@Override
	protected FFLine buildLine(Organism f, boolean showEvidence){
		StringBuilder sb = build(f, showEvidence, true );
		List<String> lls = FFLineWrapper.buildLines(sb, SEPARATOR, linePrefix);
		return FFLines.create(lls);
	}
	@Override
	public String buildString(Organism f) {
		return build(f, false, false).toString();
	}
	
	@Override
	public String buildStringWithEvidence(Organism f) {
		return build(f, true, false).toString();
	}
	
	private  StringBuilder build(Organism organism, boolean showEvidence, boolean includeFFMarkup) {
        StringBuilder sb = new StringBuilder();
        if (includeFFMarkup)
            sb.append(linePrefix);
        if (organism.getScientificName() != null) {
            sb.append(organism.getScientificName());
        }
        if (organism.getCommonName() != null && !organism.getCommonName().equals("")) {
            sb.append(" (");
            sb.append(organism.getCommonName());
            sb.append(")");
        }
        if(!organism.getSynonyms().isEmpty()) {
        	String val = organism.getSynonyms().stream().collect(Collectors.joining(", "));
        		sb.append(" (");
            sb.append(val);
            sb.append(")");
        }
      
        appendIfNot(sb, '.');
    
        return sb;
    }
}

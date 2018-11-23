package uk.ac.ebi.uniprot.parser.impl.oh;

import static uk.ac.ebi.uniprot.parser.ffwriter.impl.FFLineConstant.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


import uk.ac.ebi.uniprot.parser.ffwriter.FFLine;
import uk.ac.ebi.uniprot.parser.ffwriter.FFLineBuilder;
import uk.ac.ebi.uniprot.parser.ffwriter.LineType;
import uk.ac.ebi.uniprot.parser.ffwriter.impl.FFLineBuilderAbstr;
import uk.ac.ebi.uniprot.parser.ffwriter.impl.FFLines;

import uk.ac.ebi.uniprot.domain.taxonomy.Organism;
import uk.ac.ebi.uniprot.domain.taxonomy.OrganismName;

public class OHLineBuilder extends FFLineBuilderAbstr< List<Organism> >
	implements FFLineBuilder<List<Organism> > {
	private static final String NCBI_TAX_ID = "NCBI_TaxID=";
	public OHLineBuilder(){
		super(LineType.OH);
	}
	@Override
	protected FFLine buildLine(List<Organism> f, boolean showEvidence) {
		List<String> lls =new ArrayList<>();
		for(Organism oh:f){
			lls.add(build(oh, showEvidence, true).toString());
		}
		return FFLines.create(lls);
	}
	@Override
	public String buildString(List<Organism> f) {
		StringBuilder sb =new StringBuilder();
		for(Organism oh:f){
			sb.append(build(oh, false, false));
		}
		return sb.toString();
	}
	
	@Override
	public String buildStringWithEvidence(List<Organism> f) {
		StringBuilder sb =new StringBuilder();
		for(Organism oh:f){
			sb.append(build(oh, true, false));
		}
		return sb.toString();
	}
	
	private  StringBuilder build(Organism organismHost, boolean showEvidence, boolean includeFFMarkup) {
        StringBuilder sb = new StringBuilder();
        if (includeFFMarkup)
            sb.append(linePrefix);
  
        sb.append(NCBI_TAX_ID);
        sb.append(organismHost.getTaxonId());
        sb.append("; ");
        OrganismName organism = organismHost.getName();
        if (organism != null) {
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
          
        }
        sb.append(STOP);
        return sb;
    }

	
}

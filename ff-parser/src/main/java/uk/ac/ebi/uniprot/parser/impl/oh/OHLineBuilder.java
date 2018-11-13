package uk.ac.ebi.uniprot.parser.impl.oh;

import static uk.ac.ebi.uniprot.parser.ffwriter.impl.FFLineConstant.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import uk.ac.ebi.uniprot.domain.uniprot.OrganismName;
import uk.ac.ebi.uniprot.domain.uniprot.OrganismHost;
import uk.ac.ebi.uniprot.parser.ffwriter.FFLine;
import uk.ac.ebi.uniprot.parser.ffwriter.FFLineBuilder;
import uk.ac.ebi.uniprot.parser.ffwriter.LineType;
import uk.ac.ebi.uniprot.parser.ffwriter.impl.FFLineBuilderAbstr;
import uk.ac.ebi.uniprot.parser.ffwriter.impl.FFLines;

public class OHLineBuilder extends FFLineBuilderAbstr< List<OrganismHost> >
	implements FFLineBuilder<List<OrganismHost> > {
	private static final String NCBI_TAX_ID = "NCBI_TaxID=";
	public OHLineBuilder(){
		super(LineType.OH);
	}
	@Override
	protected FFLine buildLine(List<OrganismHost> f, boolean showEvidence) {
		List<String> lls =new ArrayList<>();
		for(OrganismHost oh:f){
			lls.add(build(oh, showEvidence, true).toString());
		}
		return FFLines.create(lls);
	}
	@Override
	public String buildString(List<OrganismHost> f) {
		StringBuilder sb =new StringBuilder();
		for(OrganismHost oh:f){
			sb.append(build(oh, false, false));
		}
		return sb.toString();
	}
	
	@Override
	public String buildStringWithEvidence(List<OrganismHost> f) {
		StringBuilder sb =new StringBuilder();
		for(OrganismHost oh:f){
			sb.append(build(oh, true, false));
		}
		return sb.toString();
	}
	
	private  StringBuilder build(OrganismHost organismHost, boolean showEvidence, boolean includeFFMarkup) {
        StringBuilder sb = new StringBuilder();
        if (includeFFMarkup)
            sb.append(linePrefix);
  
        sb.append(NCBI_TAX_ID);
        sb.append(organismHost.getTaxonId().getTaxonId());
        sb.append("; ");
        OrganismName organism = organismHost.getOrganism();
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

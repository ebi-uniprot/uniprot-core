package uk.ac.ebi.uniprot.parser.impl.ox;

import uk.ac.ebi.uniprot.domain.taxonomy.Organism;
import uk.ac.ebi.uniprot.parser.ffwriter.FFLine;
import uk.ac.ebi.uniprot.parser.ffwriter.FFLineBuilder;
import uk.ac.ebi.uniprot.parser.ffwriter.LineType;
import uk.ac.ebi.uniprot.parser.ffwriter.impl.FFLineBuilderAbstr;
import uk.ac.ebi.uniprot.parser.ffwriter.impl.FFLines;

import java.util.ArrayList;
import java.util.List;



public class OXLineBuilder extends FFLineBuilderAbstr< Organism > implements FFLineBuilder<Organism> {
	final private static String NAME ="NCBI_TaxID=";
	final private static String STOP = ";";
	
	public OXLineBuilder(){
		super(LineType.OX);
	}
	@Override
	protected FFLine buildLine(Organism f, boolean showEvidence){
		StringBuilder sb = build(f, showEvidence, true );
		List<String> lls = new ArrayList<> ();
		lls.add(sb.toString());
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
	
	
	private StringBuilder build(Organism f, boolean showEvidence, boolean includeFFMarkup){
		StringBuilder sb = new StringBuilder();
		if(includeFFMarkup)
			sb.append(linePrefix);
		sb.append(NAME);
	
			sb.append(f.getTaxonId());
			addEvidences(sb, f, showEvidence);

		sb.append(STOP);
		return sb;
	}
	
}

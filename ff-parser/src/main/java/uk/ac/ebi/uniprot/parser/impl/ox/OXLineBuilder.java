package uk.ac.ebi.uniprot.parser.impl.ox;

import java.util.ArrayList;
import java.util.List;

import uk.ac.ebi.uniprot.domain.uniprot.UniProtTaxonId;
import uk.ac.ebi.uniprot.ffwriter.line.FFLine;
import uk.ac.ebi.uniprot.ffwriter.line.FFLineBuilder;
import uk.ac.ebi.uniprot.ffwriter.line.FFLines;
import uk.ac.ebi.uniprot.ffwriter.line.LineType;
import uk.ac.ebi.uniprot.ffwriter.line.impl.FFLineBuilderAbstr;



public class OXLineBuilder extends FFLineBuilderAbstr< UniProtTaxonId > implements FFLineBuilder< UniProtTaxonId> {
	final private static String NAME ="NCBI_TaxID=";
	final private static String STOP = ";";
	
	public OXLineBuilder(){
		super(LineType.OX);
	}
	@Override
	protected FFLine buildLine(UniProtTaxonId f, boolean showEvidence){
		StringBuilder sb = build(f, showEvidence, true );
		List<String> lls = new ArrayList<> ();
		lls.add(sb.toString());
		return FFLines.create(lls);
	}
	@Override
	public String buildString(UniProtTaxonId f) {
		return build(f, false, false).toString();
	}
	@Override
	public String buildStringWithEvidence(UniProtTaxonId f) {
		return build(f, true, false).toString();
	}
	
	
	private StringBuilder build(UniProtTaxonId f, boolean showEvidence, boolean includeFFMarkup){
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

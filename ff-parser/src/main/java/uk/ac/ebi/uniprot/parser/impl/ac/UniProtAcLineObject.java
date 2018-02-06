package uk.ac.ebi.uniprot.parser.impl.ac;

import java.util.List;

import uk.ac.ebi.uniprot.domain.uniprot.UniProtAccession;


public class UniProtAcLineObject {
	
	private final UniProtAccession primaryAccession;
	private final List<UniProtAccession> secondAccessions ;
	
	public UniProtAcLineObject(UniProtAccession primaryAccession, List<UniProtAccession> secondAccessions) {
		this.primaryAccession = primaryAccession;
		this.secondAccessions = secondAccessions;
	}

	public UniProtAccession getPrimaryAccession() {
		return primaryAccession;
	}

	public List<UniProtAccession> getSecondAccessions() {
		return secondAccessions;
	}
	
}

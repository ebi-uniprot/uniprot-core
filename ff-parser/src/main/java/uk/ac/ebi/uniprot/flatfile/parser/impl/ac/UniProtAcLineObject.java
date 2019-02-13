package uk.ac.ebi.uniprot.flatfile.parser.impl.ac;

import uk.ac.ebi.uniprot.domain.uniprot.UniProtAccession;

import java.util.List;


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

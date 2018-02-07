package uk.ac.ebi.uniprot.ffwriter.line;

public interface FFLineBuilder<F> {
	FFLine build(F f);
	FFLine buildWithEvidence(F f);
	String buildString(F f);
	String buildStringWithEvidence(F f);
}

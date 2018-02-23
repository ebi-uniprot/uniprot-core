package uk.ac.ebi.uniprot.ffwriter.line;

import java.util.List;

public interface RLine <T> {
	public List<String> buildLine(T f, boolean includeFFMarkup, boolean showEvidence);
}

package uk.ac.ebi.uniprot.ffwriter.line.impl.rlines;

import java.util.List;

public interface RLine <T> {
	public List<String> buildLine(T f, boolean includeFFMarkup, boolean showEvidence);
}

package uk.ac.ebi.uniprot.parser.impl.rx;

import static uk.ac.ebi.uniprot.parser.ffwriter.impl.FFLineConstant.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import uk.ac.ebi.uniprot.domain.citation.CitationXref;
import uk.ac.ebi.uniprot.domain.citation.CitationXrefType;
import uk.ac.ebi.uniprot.domain.citation.CitationXrefs;
import uk.ac.ebi.uniprot.parser.ffwriter.LineType;
import uk.ac.ebi.uniprot.parser.ffwriter.impl.RLine;

public class RXLineBuilder implements RLine<CitationXrefs> {
	private final LineType lineType = LineType.RX;
	private final String linePrefix = lineType + DEFAUT_LINESPACE;

	@Override
	public List<String> buildLine(CitationXrefs xrefs, boolean includeFFMarkup, boolean showEvidence) {
		List<String> lines = new ArrayList<>();
		if(xrefs ==null)
			return lines;
		StringBuilder line = new StringBuilder();
		line = buildLine(lines, line, xrefs.getTyped(CitationXrefType.PUBMED), includeFFMarkup);
		line = buildLine(lines, line, xrefs.getTyped(CitationXrefType.AGRICOLA), includeFFMarkup);
		line = buildLine(lines, line, xrefs.getTyped(CitationXrefType.DOI), includeFFMarkup);
		if (line.length() > 0) {
			lines.add(line.toString());
		}
		return lines;
	}

	private StringBuilder buildLine(List<String> lines, StringBuilder line, Optional<CitationXref> xref,
			boolean includeFFMarkup) {
		if (xref.isPresent()) {
			if ((line.length() > 0) && ((getString(xref.get()).length() + line.length()) >= LINE_LENGTH - 1)) {
				lines.add(line.toString());
				line = new StringBuilder();
			}
			if (line.length() == 0) {
				if (includeFFMarkup)
					line.append(linePrefix);
			} else {
				line.append(SPACE);
			}
			line.append(getString(xref.get()));

			line.append(SEMI_COMA);
			return line;
		} else
			return line;
	}

	private String getString(CitationXref xref) {
		StringBuilder sb = new StringBuilder();
		sb.append(xref.getXrefType().getValue()).append(EQUAL_SIGN).append(xref.getId());
		return sb.toString();
	}
}

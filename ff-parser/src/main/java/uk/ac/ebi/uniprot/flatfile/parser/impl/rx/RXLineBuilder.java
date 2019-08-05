package uk.ac.ebi.uniprot.flatfile.parser.impl.rx;

import uk.ac.ebi.uniprot.flatfile.parser.ffwriter.LineType;
import uk.ac.ebi.uniprot.flatfile.parser.ffwriter.impl.RLine;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.uniprot.core.DBCrossReference;
import org.uniprot.core.citation.Citation;
import org.uniprot.core.citation.CitationXrefType;

import static uk.ac.ebi.uniprot.flatfile.parser.ffwriter.impl.FFLineConstant.*;

public class RXLineBuilder implements RLine<Citation> {
    private final LineType lineType = LineType.RX;
    private final String linePrefix = lineType + DEFAUT_LINESPACE;

    @Override
    public List<String> buildLine(Citation xrefs, boolean includeFFMarkup, boolean showEvidence) {
        List<String> lines = new ArrayList<>();
        if (xrefs == null)
            return lines;
        StringBuilder line = new StringBuilder();
        line = buildLine(lines, line, xrefs.getCitationXrefsByType(CitationXrefType.PUBMED), includeFFMarkup);
        line = buildLine(lines, line, xrefs.getCitationXrefsByType(CitationXrefType.AGRICOLA), includeFFMarkup);
        line = buildLine(lines, line, xrefs.getCitationXrefsByType(CitationXrefType.DOI), includeFFMarkup);
        if (line.length() > 0) {
            lines.add(line.toString());
        }
        return lines;
    }

    private StringBuilder buildLine(List<String> lines, StringBuilder line, Optional<DBCrossReference<CitationXrefType>> xref,
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

            line.append(SEMICOLON);
            return line;
        } else
            return line;
    }

    private String getString(DBCrossReference<CitationXrefType> xref) {
        StringBuilder sb = new StringBuilder();
        sb.append(xref.getDatabaseType().getName()).append(EQUAL_SIGN).append(xref.getId());
        return sb.toString();
    }
}

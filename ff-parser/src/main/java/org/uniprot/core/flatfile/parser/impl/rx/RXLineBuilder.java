package org.uniprot.core.flatfile.parser.impl.rx;

import static org.uniprot.core.flatfile.writer.impl.FFLineConstant.*;

import org.uniprot.core.CrossReference;
import org.uniprot.core.citation.Citation;
import org.uniprot.core.citation.CitationDatabase;
import org.uniprot.core.flatfile.writer.LineType;
import org.uniprot.core.flatfile.writer.impl.RLine;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RXLineBuilder implements RLine<Citation> {
    private final LineType lineType = LineType.RX;
    private final String linePrefix = lineType + DEFAUT_LINESPACE;

    @Override
    public List<String> buildLine(
            Citation citation, boolean includeFFMarkup, boolean showEvidence) {
        List<String> lines = new ArrayList<>();
        if (citation == null) return lines;
        StringBuilder line = new StringBuilder();
        line =
                buildLine(
                        lines,
                        line,
                        citation.getCitationCrossReferenceByType(CitationDatabase.PUBMED),
                        includeFFMarkup);
        line =
                buildLine(
                        lines,
                        line,
                        citation.getCitationCrossReferenceByType(CitationDatabase.AGRICOLA),
                        includeFFMarkup);
        line =
                buildLine(
                        lines,
                        line,
                        citation.getCitationCrossReferenceByType(CitationDatabase.DOI),
                        includeFFMarkup);
        if (line.length() > 0) {
            lines.add(line.toString());
        }
        return lines;
    }

    private StringBuilder buildLine(
            List<String> lines,
            StringBuilder line,
            Optional<CrossReference<CitationDatabase>> xref,
            boolean includeFFMarkup) {
        if (xref.isPresent()) {
            if ((line.length() > 0)
                    && ((getString(xref.get()).length() + line.length()) >= LINE_LENGTH - 1)) {
                lines.add(line.toString());
                line = new StringBuilder();
            }
            if (line.length() == 0) {
                if (includeFFMarkup) line.append(linePrefix);
            } else {
                line.append(SPACE);
            }
            line.append(getString(xref.get()));

            line.append(SEMICOLON);
            return line;
        } else return line;
    }

    private String getString(CrossReference<CitationDatabase> xref) {
        StringBuilder sb = new StringBuilder();
        sb.append(xref.getDatabase().getName()).append(EQUAL_SIGN).append(xref.getId());
        return sb.toString();
    }
}

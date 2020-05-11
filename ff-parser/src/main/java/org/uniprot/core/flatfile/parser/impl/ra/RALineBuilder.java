package org.uniprot.core.flatfile.parser.impl.ra;

import static org.uniprot.core.flatfile.writer.impl.FFLineConstant.*;

import org.uniprot.core.citation.Author;
import org.uniprot.core.flatfile.writer.LineType;
import org.uniprot.core.flatfile.writer.impl.FFLineWrapper;
import org.uniprot.core.flatfile.writer.impl.RLine;

import java.util.ArrayList;
import java.util.List;

public class RALineBuilder implements RLine<List<Author>> {
    private final LineType lineType = LineType.RA;
    private final String linePrefix = lineType + DEFAUT_LINESPACE;

    @Override
    public List<String> buildLine(List<Author> f, boolean includeFFMarkup, boolean showEvidence) {
        if ((f == null) || (f.size() == 0)) {
            new ArrayList<>();
        }

        List<String> tokens = new ArrayList<>();
        for (Author author : f) {
            tokens.add(author.getValue());
        }
        if (includeFFMarkup)
            return FFLineWrapper.buildLine(
                    tokens, COMA, SPACE, SEMICOLON, linePrefix, LINE_LENGTH, includeFFMarkup);
        else {
            return FFLineWrapper.buildLine(
                    tokens, COMA, SPACE, SEMICOLON, "", LINE_LENGTH, includeFFMarkup);
        }
    }
}

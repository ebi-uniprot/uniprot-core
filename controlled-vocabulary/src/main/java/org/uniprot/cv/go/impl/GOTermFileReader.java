package org.uniprot.cv.go.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.uniprot.core.cv.go.GeneOntologyEntry;
import org.uniprot.core.cv.go.impl.GeneOntologyEntryBuilder;
import org.uniprot.core.util.Utils;
import org.uniprot.cv.common.AbstractFileReader;

class GOTermFileReader extends AbstractFileReader<GeneOntologyEntry> {
    private static final String COMMENT_PREFIX = "!";
    private static final String SEPARATOR = "\t";
    private static final String NOT_OBSOLETE = "N";
    private static final String FILENAME = "GO.terms";

    @Override
    public List<GeneOntologyEntry> parse(String filename) {
        return super.parse(filename + "/" + FILENAME);
    }

    @Override
    public List<GeneOntologyEntry> parseLines(List<String> lines) {
        return lines.stream()
                .map(this::readLine)
                .filter(Utils::notNull)
                .collect(Collectors.toList());
    }

    private GeneOntologyEntry readLine(String line) {
        if (line.startsWith(COMMENT_PREFIX)) return null;

        String[] tokens = line.split(SEPARATOR);
        if (tokens.length == 4) {
            if (tokens[1].equals(NOT_OBSOLETE)) {
                return new GeneOntologyEntryBuilder().id(tokens[0]).name(tokens[2]).build();
            }
        }
        return null;
    }
}

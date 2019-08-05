package org.uniprot.core.cv.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.uniprot.core.cv.chebi.Chebi;
import org.uniprot.core.cv.chebi.ChebiBuilder;

import static uk.ac.ebi.uniprot.common.Utils.nonNull;

public class ChebiFileReader extends AbstractFileReader<Chebi> {
    private static final String ID_PREFIX = "id: CHEBI:";
    private static final String NAME_PREFIX = "name: ";
    private static final Pattern INCHI_PATTERN = Pattern
            .compile("^property_value: \\S+chebi/inchikey\\s+\"(.*)\"\\s.*$");

    @Override
    List<Chebi> parseLines(List<String> lines) {
        List<Chebi> chebiList = new ArrayList<>();
        ChebiBuilder chebiBuilder = null;
        for (String line : lines) {
            if (line.startsWith("[Term]")) {
                if (nonNull(chebiBuilder)) {
                    chebiList.add(chebiBuilder.build());
                }
                // start of new term
                chebiBuilder = new ChebiBuilder();
            }
            if (nonNull(chebiBuilder)) {
                Matcher inchiMatcher = INCHI_PATTERN.matcher(line);
                if (line.startsWith(ID_PREFIX)) {
                    chebiBuilder.id(line.substring(ID_PREFIX.length()));
                } else if (line.startsWith(NAME_PREFIX)) {
                    chebiBuilder.name(line.substring(NAME_PREFIX.length()));
                } else if (inchiMatcher.matches()) {
                    chebiBuilder.inchiKey(inchiMatcher.group(1));
                }
            }
        }

        // add the most recently created builder
        chebiList.add(chebiBuilder.build());

        return chebiList;
    }
}

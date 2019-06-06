package uk.ac.ebi.uniprot.cv.impl;

import uk.ac.ebi.uniprot.cv.chebi.Chebi;
import uk.ac.ebi.uniprot.cv.chebi.impl.ChebiImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static uk.ac.ebi.uniprot.common.Utils.nonNull;

public class ChebiFileReader extends AbstractFileReader<Chebi> {
    private static final Pattern VALUE_PATTERN = Pattern.compile("^\\w+:\\s+(.*)$");
    private static final Pattern INCHI_PATTERN = Pattern.compile("^property_value: \\S+chebi/inchikey\\s+\"(.*)\"\\s.*$");

    @Override
    List<Chebi> parseLines(List<String> lines) {
        List<Chebi> chebiList = new ArrayList<>();
        ChebiImpl.Builder chebiBuilder = null;
        for (String line : lines) {
            if (line.startsWith("[Term]")) {
                if (nonNull(chebiBuilder)) {
                    chebiList.add(chebiBuilder.build());
                }
                // start of new term
                chebiBuilder = new ChebiImpl.Builder();
            }
            if (nonNull(chebiBuilder)) {
                Matcher inchiMatcher = INCHI_PATTERN.matcher(line);
                if (line.startsWith("id:")) {
                    chebiBuilder.id(extractValue(line));
                } else if (line.startsWith("name:")) {
                    chebiBuilder.name(extractValue(line));
                } else if (inchiMatcher.matches()) {
                    chebiBuilder.inchiKey(inchiMatcher.group(1));
                }
            }
        }

        // add the most recently created builder
        chebiList.add(chebiBuilder.build());

        return chebiList;
    }

    private String extractValue(String line) {
        Matcher valueMatcher = VALUE_PATTERN.matcher(line);
        if (valueMatcher.matches()) {
            return valueMatcher.group(1);
        } else {
            return null;
        }
    }
}

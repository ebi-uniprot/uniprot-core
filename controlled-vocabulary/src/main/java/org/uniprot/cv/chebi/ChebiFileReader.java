package org.uniprot.cv.chebi;

import static org.uniprot.core.util.Utils.notNull;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.uniprot.core.cv.chebi.ChebiEntry;
import org.uniprot.core.cv.chebi.impl.ChebiEntryBuilder;
import org.uniprot.cv.common.AbstractFileReader;

public class ChebiFileReader extends AbstractFileReader<ChebiEntry> {
    private static final String ID_PREFIX = "id: CHEBI:";
    private static final String NAME_PREFIX = "name: ";
    private static final String RELATED_PREFIX = "is_a: CHEBI:";
    private static final String RELATED_MICROSPECIES_PREFIX =
            "relationship: has_major_microspecies_at_pH_7_3 CHEBI:";
    private static final Pattern SYNONYM_PATTERN =
            Pattern.compile("^property_value: hasSynonym\\s{1,4}\"(.{1,300})\"\\s.{1,300}$");
    private static final Pattern INCHI_PATTERN =
            Pattern.compile("^property_value: \\S+chebi/inchikey\\s+\"(.*)\"\\s.*$");

    @Override
    public List<ChebiEntry> parseLines(List<String> lines) {
        List<ChebiEntry> chebiList = new ArrayList<>();
        ChebiEntryBuilder chebiBuilder = null;
        for (String line : lines) {
            if (line.startsWith("[Term]")) {
                if (notNull(chebiBuilder)) {
                    chebiList.add(chebiBuilder.build());
                }
                // start of new term
                chebiBuilder = new ChebiEntryBuilder();
            }
            if (Objects.nonNull(chebiBuilder)) {
                Matcher inchiMatcher = INCHI_PATTERN.matcher(line);
                Matcher synonymMatcher = SYNONYM_PATTERN.matcher(line);
                if (line.startsWith(ID_PREFIX)) {
                    chebiBuilder.id(line.substring(ID_PREFIX.length()));
                } else if (line.startsWith(NAME_PREFIX)) {
                    chebiBuilder.name(line.substring(NAME_PREFIX.length()));
                } else if (line.startsWith(RELATED_PREFIX)) {
                    chebiBuilder.relatedIdsAdd(getRelatedEntry(line, RELATED_PREFIX));
                } else if (line.startsWith(RELATED_MICROSPECIES_PREFIX)) {
                    chebiBuilder.majorMicrospeciesAdd(getRelatedEntry(line, RELATED_MICROSPECIES_PREFIX));
                } else if (inchiMatcher.matches()) {
                    chebiBuilder.inchiKey(inchiMatcher.group(1));
                } else if (synonymMatcher.matches()) {
                    chebiBuilder.synonymsAdd(synonymMatcher.group(1));
                }
            }
        }

        if (chebiBuilder != null) { // add the most recently created builder
            chebiList.add(chebiBuilder.build());
        }
        return chebiList;
    }

    private ChebiEntry getRelatedEntry(String line, String relatedIdPrefix) {
        String id = line.substring(relatedIdPrefix.length());
        if (id.contains("!")) {
            id = id.substring(0, id.indexOf("!")).strip();
        }
        return new ChebiEntryBuilder().id(id).build();
    }
}

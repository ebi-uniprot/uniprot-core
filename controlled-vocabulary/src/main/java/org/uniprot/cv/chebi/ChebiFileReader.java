package org.uniprot.cv.chebi;

import static org.uniprot.core.util.Utils.notNull;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.uniprot.core.cv.chebi.ChebiEntry;
import org.uniprot.core.cv.chebi.impl.ChebiEntryBuilder;
import org.uniprot.core.util.Utils;
import org.uniprot.cv.common.AbstractFileReader;

import javax.annotation.Nonnull;

public class ChebiFileReader extends AbstractFileReader<ChebiEntry> {
    private static final String ID_PREFIX = "id: CHEBI:";
    private static final String NAME_PREFIX = "name: ";
    private static final String RELATED_PREFIX = "is_a: CHEBI:";
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
            if (notNull(chebiBuilder)) {
                Matcher inchiMatcher = INCHI_PATTERN.matcher(line);
                if (line.startsWith(ID_PREFIX)) {
                    chebiBuilder.id(line.substring(ID_PREFIX.length()));
                } else if (line.startsWith(NAME_PREFIX)) {
                    chebiBuilder.name(line.substring(NAME_PREFIX.length()));
                } else if (line.startsWith(RELATED_PREFIX)) {
                    String id = line.substring(RELATED_PREFIX.length());
                    chebiBuilder.relatedIdsAdd(new ChebiEntryBuilder().id(id).build());
                } else if (inchiMatcher.matches()) {
                    chebiBuilder.inchiKey(inchiMatcher.group(1));
                }
            }
        }

        if (chebiBuilder != null) { // add the most recently created builder
            chebiList.add(chebiBuilder.build());
        }
        return chebiList;
    }

/*
    private static class ChebiWithRelationBuilder extends ChebiEntryBuilder {
        List<ChebiFileReader.ChebiWithRelationBuilder> relationBuilders = new ArrayList<>();
        private static final List<ChebiEntry> completedRelation = new ArrayList<>();

        private Optional<ChebiEntry> findInCompletedRelated(ChebiEntry child) {
            return completedRelation.stream()
                    .filter(chebiEntry -> chebiEntry.getId().equals(child.getId()))
                    .findFirst();
        }

        private void addInCompletedRelatedIfAbsent(ChebiEntry chebiEntry) {
            Optional<ChebiEntry> found = findInCompletedRelated(chebiEntry);
            if (!found.isPresent()) {
                completedRelation.add(chebiEntry);
            }
        }

        private ChebiEntry retainAllChildrenWithConcreteImplementation(
                ChebiFileReader.ChebiWithRelationBuilder currentChebi, List<ChebiFileReader.ChebiWithRelationBuilder> children) {

            Optional<ChebiEntry> currentInCompletedChildren =
                    findInCompletedRelated(currentChebi.build());
            if (currentInCompletedChildren.isPresent()) {
                return currentInCompletedChildren.get();
            }

            if (children.isEmpty()) {
                ChebiEntry retChebi = currentChebi.build();
                addInCompletedRelatedIfAbsent(retChebi);
                return retChebi;
            }

            for (ChebiFileReader.ChebiWithRelationBuilder child : children) {
                ChebiEntry completed =
                        retainAllChildrenWithConcreteImplementation(child, child.relationBuilders);
                currentChebi.relatedIdsAdd(completed);
            }

            ChebiEntry current = currentChebi.build();
            addInCompletedRelatedIfAbsent(current);
            return current;
        }

        @Nonnull
        @Override
        public ChebiFileReader.ChebiWithRelationBuilder relatedIdsAdd(ChebiFileReader.ChebiWithRelationBuilder relatedId) {
            relationBuilders.add(relatedId);
            return this;
        }

        ChebiEntry builderLimitedChebiEntry() {
            retainAllChildrenWithConcreteImplementation(this, this.relationBuilders);
            return super.build();
        }
    }
 */
}

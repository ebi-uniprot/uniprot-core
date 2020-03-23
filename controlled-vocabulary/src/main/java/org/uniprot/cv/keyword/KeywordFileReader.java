package org.uniprot.cv.keyword;

import java.util.*;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.uniprot.core.cv.go.GoTerm;
import org.uniprot.core.cv.go.impl.GoTermBuilder;
import org.uniprot.core.cv.keyword.KeywordCategory;
import org.uniprot.core.cv.keyword.KeywordEntry;
import org.uniprot.core.cv.keyword.KeywordId;
import org.uniprot.core.cv.keyword.impl.KeywordEntryBuilder;
import org.uniprot.core.cv.keyword.impl.KeywordIdBuilder;
import org.uniprot.core.util.Pair;
import org.uniprot.core.util.PairImpl;
import org.uniprot.cv.common.AbstractFileReader;

public class KeywordFileReader extends AbstractFileReader<KeywordEntry> {
    private static final String WW_LINE = "WW";
    private static final String CA_LINE = "CA";
    private static final String GO_LINE = "GO";
    private static final String HI_LINE = "HI";
    private static final String SY_LINE = "SY";
    private static final String DE_LINE = "DE";
    private static final String AC_LINE = "AC";
    private static final String IC_LINE = "IC";
    private static final String ID_LINE = "ID";
    private static final String SPLIT_SPACES = "   ";
    private static final String CATEGORY_SEPARATOR = ":";
    private static final String HIERARCHY_SEPARATOR = ";";
    private static final Logger LOG = LoggerFactory.getLogger(KeywordFileReader.class);

    public List<KeywordEntry> parseLines(List<String> lines) {
        List<KeyFileEntry> rawList = convertLinesIntoInMemoryObjectList(lines);
        List<KEFRBuilder> inMemoryGraph = parseKeywordFileEntryList(rawList);
        updateListWithRelationShips(inMemoryGraph, rawList);
        return inMemoryGraph.stream()
                .map(KEFRBuilder::builderLimitedKeywordEntry)
                .collect(Collectors.toList());
    }

    public Map<String, Pair<String, KeywordCategory>> parseFileToAccessionMap(String fileName) {
        List<KeywordEntry> keywordEntryList = parse(fileName);
        return keywordEntryList.stream()
                .collect(
                        Collectors.toMap(
                                KeywordFileReader::getId,
                                KeywordFileReader::getAccessionCategoryPair));
    }

    public static String getId(KeywordEntry keyword) {
        return keyword.getKeyword().getName();
    }

    public static Pair<String, KeywordCategory> getAccessionCategoryPair(KeywordEntry keyword) {
        String accession = keyword.getAccession();
        KeywordId kcategory = keyword.getCategory();
        KeywordCategory category;
        if (kcategory != null) {
            category = KeywordCategory.fromValue(kcategory.getName());
        } else {
            category = KeywordCategory.fromValue(keyword.getKeyword().getName());
        }

        return new PairImpl<>(accession, category);
    }

    private void updateListWithRelationShips(List<KEFRBuilder> list, List<KeyFileEntry> rawList) {
        for (KeyFileEntry raw : rawList) {
            // category will not have relationship, so ignore them
            if (raw.hi.isEmpty()) {
                continue;
            }

            // Only getting keywords
            KEFRBuilder target = findByIdentifier(list, raw.id);
            assert (target != null);

            // Setting the category
            KEFRBuilder category = findByIdentifier(list, raw.ca);
            if (category != null) {
                target.category(category.build().getKeyword());
            }

            final List<String> withOutCategory =
                    raw.hi.stream()
                            .map(s -> s.substring(s.indexOf(CATEGORY_SEPARATOR) + 1))
                            .collect(Collectors.toList());

            final Set<String> directRelations =
                    withOutCategory.stream()
                            .map(s -> s.split(HIERARCHY_SEPARATOR))
                            .filter(arr -> arr.length >= 2)
                            .map(arr -> arr[arr.length - 2])
                            .map(this::trimSpacesAndRemoveLastDot)
                            .collect(Collectors.toSet());

            // getting relationships and update it parents for directRelations
            final List<KEFRBuilder> relations =
                    directRelations.stream()
                            .map(s -> findByIdentifier(list, s))
                            .filter(Objects::nonNull)
                            .peek(
                                    kwb -> {
                                        kwb.parentsAdd(target);
                                    })
                            .collect(Collectors.toList());

            // Only setting hierarchy if present
            relations.forEach(target::childrenAdd);

            // no relationship means children is category
            if (relations.isEmpty() && (category != null)) {
                target.childrenAdd(category);
                category.parentsAdd(target);
            }
        }
    }

    private KEFRBuilder findByIdentifier(List<KEFRBuilder> list, String id) {
        for (KEFRBuilder builder : list) {
            if (builder.build().getKeyword().getName().equals(trimSpacesAndRemoveLastDot(id)))
                return builder;
        }
        return null;
    }

    private List<KEFRBuilder> parseKeywordFileEntryList(List<KeyFileEntry> rawList) {
        return rawList.stream().map(this::parseKeywordFileEntry).collect(Collectors.toList());
    }

    private KEFRBuilder parseKeywordFileEntry(KeyFileEntry entry) {
        final String identifier = entry.id != null ? entry.id : entry.ic;
        KEFRBuilder retObj = new KEFRBuilder();
        KeywordId keyword =
                new KeywordIdBuilder()
                        .id(trimSpacesAndRemoveLastDot(identifier))
                        .accession(entry.ac)
                        .build();
        retObj.keyword(keyword);
        // definition
        String def = String.join(" ", entry.de);
        retObj.definition(def.isEmpty() ? null : def);

        // Synonyms
        List<String> synList =
                entry.sy.stream()
                        .flatMap(s -> Arrays.asList(s.split(";")).stream())
                        .map(this::trimSpacesAndRemoveLastDot)
                        .collect(Collectors.toList());
        retObj.synonymsSet(synList);

        // GoMapping
        List<GoTerm> goList =
                entry.go.stream().map(this::parseGeneOntology).collect(Collectors.toList());
        retObj.geneOntologiesSet(goList);

        // Sites
        retObj.sitesSet(entry.ww);

        return retObj;
    }

    private String trimSpacesAndRemoveLastDot(String str) {
        if (str == null) {
            return null;
        }
        str = str.trim();
        return str.endsWith(".") ? str.substring(0, str.length() - 1) : str;
    }

    private GoTerm parseGeneOntology(String go) {
        String[] tokens = go.split(";");
        return new GoTermBuilder().id(tokens[0]).name(tokens[1].trim()).build();
    }

    private List<KeyFileEntry> convertLinesIntoInMemoryObjectList(List<String> lines) {
        // At the time of writing code there was 1200 entries in file
        List<KeyFileEntry> retList = new ArrayList<>(1250);

        int i = 0;

        // Ignore the header lines and information
        for (; i < lines.size(); i++) {
            String lineIgnore = lines.get(i);
            if (lineIgnore.startsWith("______")) {
                break;
            }
        }

        // Ignore underscore ___ line
        i++;

        // reached entries lines
        KeyFileEntry entry = new KeyFileEntry();

        // create in memory list of objects
        while (i < lines.size()) {
            String line = lines.get(i);
            if (COPYRIGHT_LINES.contains(line)) {
                i++;
                continue;
            }
            // For terminating line no need to complete loop
            if (line.equals("//")) {
                retList.add(entry);
                entry = new KeyFileEntry();
                i++;
                continue;
            }

            String[] tokens = line.split(SPLIT_SPACES);
            switch (tokens[0]) {
                case ID_LINE:
                    entry.id = tokens[1];
                    break;
                case IC_LINE:
                    entry.ic = tokens[1];
                    break;
                case AC_LINE:
                    entry.ac = tokens[1];
                    break;
                case DE_LINE:
                    entry.de.add(tokens[1]);
                    break;
                case SY_LINE:
                    entry.sy.add(tokens[1]);
                    break;
                case HI_LINE:
                    entry.hi.add(tokens[1]);
                    break;
                case GO_LINE:
                    entry.go.add(tokens[1]);
                    break;
                case CA_LINE:
                    entry.ca = tokens[1];
                    break;
                case WW_LINE:
                    entry.ww.add(tokens[1]);
                    break;
                default:
                    LOG.info("Unhandle line found while parsing file: {}", line);
            }

            // read and save next line
            i++;
        }
        return retList;
    }

    private static class KeyFileEntry {
        String id;
        String ic;
        String ac;
        List<String> de;
        List<String> sy;
        List<String> hi;
        List<String> go;
        List<String> ww;
        String ca;

        KeyFileEntry() {
            de = new ArrayList<>();
            sy = new ArrayList<>();
            hi = new ArrayList<>();
            go = new ArrayList<>();
            ww = new ArrayList<>();
        }
    }

    private class KEFRBuilder extends KeywordEntryBuilder {
        List<KEFRBuilder> parents = new ArrayList<>();
        List<KEFRBuilder> children = new ArrayList<>();
        private final List<KeywordEntry> completedChildren = new ArrayList<>();

        private Optional<KeywordEntry> findInCompletedChildren(KeywordEntry child) {
            return completedChildren.stream()
                    .filter(
                            keywordEntry ->
                                    keywordEntry
                                                    .getKeyword()
                                                    .getId()
                                                    .equals(child.getKeyword().getId())
                                            && keywordEntry
                                                    .getKeyword()
                                                    .getName()
                                                    .equals(child.getKeyword().getName()))
                    .findFirst();
        }

        private void addInCompletedChildrenIfAbsent(KeywordEntry keywordEntry) {
            Optional<KeywordEntry> found = findInCompletedChildren(keywordEntry);
            if (!found.isPresent()) {
                completedChildren.add(keywordEntry);
            }
        }

        private KeywordEntry retainAllChildrenWithConcreteImplementation(
                KEFRBuilder currentKeyword, List<KEFRBuilder> children) {

            Optional<KeywordEntry> currentInCompletedChildren =
                    findInCompletedChildren(currentKeyword.build());
            if (currentInCompletedChildren.isPresent()) {
                return currentInCompletedChildren.get();
            }

            if (children.isEmpty()) {
                KeywordEntry retKeyword = currentKeyword.build();
                addInCompletedChildrenIfAbsent(retKeyword);
                return retKeyword;
            }

            for (KEFRBuilder child : children) {
                KeywordEntry completed =
                        retainAllChildrenWithConcreteImplementation(child, child.children);
                currentKeyword.childrenAdd(completed);
            }

            KeywordEntry current = currentKeyword.build();
            addInCompletedChildrenIfAbsent(current);
            return current;
        }

        private void retainImmediateParentsOnlyWithOutChildren(
                KEFRBuilder currentKeyword, List<KEFRBuilder> parents) {
            for (KEFRBuilder key : parents) {
                KeywordEntry limitedParent =
                        KeywordEntryBuilder.from(key.build())
                                .childrenSet(Collections.emptyList())
                                .parentsSet(Collections.emptySet())
                                .build();
                currentKeyword.parentsAdd(limitedParent);
            }
        }

        KEFRBuilder parentsAdd(KEFRBuilder parent) {
            parents.add(parent);
            return this;
        }

        KEFRBuilder childrenAdd(KEFRBuilder child) {
            children.add(child);
            return this;
        }

        KeywordEntry builderLimitedKeywordEntry() {
            retainAllChildrenWithConcreteImplementation(this, this.children);
            retainImmediateParentsOnlyWithOutChildren(this, this.parents);
            return super.build();
        }
    }
}

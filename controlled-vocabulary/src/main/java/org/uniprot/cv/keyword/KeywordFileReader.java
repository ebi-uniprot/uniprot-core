package org.uniprot.cv.keyword;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
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
import org.uniprot.core.util.Utils;
import org.uniprot.cv.common.AbstractFileReader;

@SuppressWarnings("squid:S3011")
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
        List<KeywordFileEntry> rawKeywordList = convertLinesIntoInMemoryObjectList(lines);
        Map<String, KeywordFileEntry> idRawKeywordMap = getIdRawKeywordMap(rawKeywordList);

        List<KeywordEntry> entryList =
                parseRawKeywordList(rawKeywordList); // thin, it doesn't have hierarchical
        Map<String, KeywordEntry> nameEntryMap = getNameEntryMap(entryList);

        Set<String> processedKeywordIds = new HashSet<>();
        try {
            for (KeywordFileEntry rawKeyword : rawKeywordList) {
                updateRelationships(rawKeyword, idRawKeywordMap, processedKeywordIds, nameEntryMap);
            }
            cleanParent(entryList);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
        return entryList;
    }

    public Map<String, Pair<String, KeywordCategory>> parseFileToAccessionMap(String fileName) {
        List<KeywordEntry> keywordEntryList = parse(fileName);
        return keywordEntryList.stream()
                .collect(
                        Collectors.toMap(
                                KeywordFileReader::getId,
                                KeywordFileReader::getAccessionCategoryPair));
    }

    private void updateRelationships(
            KeywordFileEntry rawKeyword,
            Map<String, KeywordFileEntry> idRawKeywordMap,
            Set<String> processedKeywordIds,
            Map<String, KeywordEntry> nameEntryMap)
            throws NoSuchFieldException, IllegalAccessException {

        KeywordEntry currentEntry =
                nameEntryMap.get(trimSpacesAndRemoveLastDot(rawKeyword.getIdentifier()));
        String category = trimSpacesAndRemoveLastDot(rawKeyword.ca);
        if (Utils.notNullNotEmpty(category) && nameEntryMap.containsKey(category)) {
            updateCategory(currentEntry, nameEntryMap.get(category));
        }
        if (Objects.nonNull(currentEntry)
                && !processedKeywordIds.contains(currentEntry.getKeyword().getName())) {
            processedKeywordIds.add(currentEntry.getKeyword().getName()); // set as processed
            List<String> hierarchies = rawKeyword.hi;

            String categoryAsParent =
                    hierarchies.stream()
                            .map(hierarchy -> hierarchy.split(HIERARCHY_SEPARATOR))
                            .filter(ancestors -> ancestors.length == 1)
                            .findAny()
                            .map(hierarchy -> hierarchy[0].split(CATEGORY_SEPARATOR))
                            .map(tokens -> tokens[0])
                            .orElse(null);

            List<String> hierarchiesMinusCat =
                    hierarchies.stream()
                            .map(
                                    hierarchy ->
                                            hierarchy.substring(
                                                    hierarchy.indexOf(CATEGORY_SEPARATOR) + 1))
                            .collect(Collectors.toList());
            Set<String> immediateParents =
                    hierarchiesMinusCat.stream()
                            .map(hierarchy -> hierarchy.split(HIERARCHY_SEPARATOR))
                            .filter(ancestors -> ancestors.length >= 2)
                            .map(ancestors -> ancestors[ancestors.length - 2])
                            .map(this::trimSpacesAndRemoveLastDot)
                            .collect(Collectors.toSet());
            if (Objects.nonNull(categoryAsParent)) {
                immediateParents.add(categoryAsParent);
            }
            for (String parent : immediateParents) {
                KeywordEntry parentEntry = nameEntryMap.get(trimSpacesAndRemoveLastDot(parent));
                if (Objects.nonNull(parentEntry)) {
                    updateParentChild(currentEntry, parentEntry);
                    KeywordFileEntry parentRawKeyword = idRawKeywordMap.get(parent);
                    if (Objects.nonNull(parentRawKeyword)) {
                        // call for the parent
                        updateRelationships(
                                parentRawKeyword,
                                idRawKeywordMap,
                                processedKeywordIds,
                                nameEntryMap);
                    }
                }
            }
        }
    }

    private void updateParentChild(KeywordEntry currentEntry, KeywordEntry parentEntry)
            throws NoSuchFieldException, IllegalAccessException {
        // add as a parent
        Field parentsField = currentEntry.getClass().getDeclaredField("parents");
        List<KeywordEntry> parentEntries =
                currentEntry.getParents().isEmpty() ? new ArrayList<>() : currentEntry.getParents();
        parentEntries.add(parentEntry);
        parentsField.setAccessible(true);
        parentsField.set(currentEntry, parentEntries);
        // add current node as a child of parent
        Field childrenField = parentEntry.getClass().getDeclaredField("children");
        List<KeywordEntry> childrenEntries =
                parentEntry.getChildren().isEmpty() ? new ArrayList<>() : parentEntry.getChildren();
        KeywordEntry thinEntry = getThinEntry(currentEntry);
        childrenEntries.add(thinEntry);
        childrenField.setAccessible(true);
        childrenField.set(parentEntry, childrenEntries);
    }

    private KeywordEntry getThinEntry(KeywordEntry currentEntry) {
        KeywordEntryBuilder builder = new KeywordEntryBuilder();
        builder.keyword(currentEntry.getKeyword());
        return builder.build();
    }

    private void updateCategory(KeywordEntry currentEntry, KeywordEntry categoryEntry)
            throws NoSuchFieldException, IllegalAccessException {
        Field categoryField = currentEntry.getClass().getDeclaredField("category");
        categoryField.setAccessible(true);
        categoryField.set(
                currentEntry, KeywordCategory.typeOf(categoryEntry.getKeyword().getName()));
    }

    private Map<String, KeywordEntry> getNameEntryMap(List<KeywordEntry> entryList) {
        return entryList.stream()
                .collect(
                        Collectors.toMap(
                                entry -> entry.getKeyword().getName(), Function.identity()));
    }

    private Map<String, KeywordFileEntry> getIdRawKeywordMap(
            List<KeywordFileEntry> rawKeywordList) {
        Map<String, KeywordFileEntry> idFileEntryMap =
                rawKeywordList.stream()
                        .collect(
                                Collectors.toMap(
                                        KeywordFileEntry::getIdentifier, Function.identity()));
        return idFileEntryMap;
    }

    private void cleanParent(List<KeywordEntry> list)
            throws NoSuchFieldException, IllegalAccessException {
        for (KeywordEntry entry : list) {
            List<KeywordEntry> newParentList = cleanParentChildren(entry.getParents());
            Field parentsField = entry.getClass().getDeclaredField("parents");
            parentsField.setAccessible(true);
            parentsField.set(entry, newParentList);
        }
    }

    private List<KeywordEntry> cleanParentChildren(List<KeywordEntry> list) {
        List<KeywordEntry> result = new ArrayList<>();
        for (KeywordEntry entry : list) {
            KeywordEntryBuilder builder = new KeywordEntryBuilder();
            builder.keyword(entry.getKeyword());
            builder.parentsSet(cleanParentChildren(entry.getParents()));
            result.add(builder.build());
        }
        return result;
    }

    public static String getId(KeywordEntry keyword) {
        return keyword.getKeyword().getName();
    }

    public static Pair<String, KeywordCategory> getAccessionCategoryPair(KeywordEntry keyword) {
        String accession = keyword.getAccession();
        KeywordId kcategory = keyword.getCategory();
        KeywordCategory category;
        if (kcategory != null) {
            category = KeywordCategory.typeOf(kcategory.getName());
        } else {
            category = KeywordCategory.typeOf(keyword.getKeyword().getName());
        }

        return new PairImpl<>(accession, category);
    }

    private List<KeywordEntry> parseRawKeywordList(List<KeywordFileEntry> rawList) {
        return rawList.stream().map(this::parseKeywordFileEntry).collect(Collectors.toList());
    }

    private KeywordEntry parseKeywordFileEntry(KeywordFileEntry entry) {
        final String identifier = entry.getIdentifier();
        KeywordEntryBuilder retObj = new KeywordEntryBuilder();
        KeywordId keyword =
                new KeywordIdBuilder()
                        .name(trimSpacesAndRemoveLastDot(identifier))
                        .id(entry.ac)
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
        retObj.linksSet(entry.ww);

        return retObj.build();
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

    private List<KeywordFileEntry> convertLinesIntoInMemoryObjectList(List<String> lines) {
        // At the time of writing code there was 1200 entries in file
        List<KeywordFileEntry> retList = new ArrayList<>(1250);

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
        KeywordFileEntry entry = new KeywordFileEntry();

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
                entry = new KeywordFileEntry();
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

    private class KeywordFileEntry {
        String id;
        String ic;
        String ac;
        List<String> de;
        List<String> sy;
        List<String> hi;
        List<String> go;
        List<String> ww;
        String ca;

        KeywordFileEntry() {
            de = new ArrayList<>();
            sy = new ArrayList<>();
            hi = new ArrayList<>();
            go = new ArrayList<>();
            ww = new ArrayList<>();
        }

        public String getIdentifier() {
            String identifier = Utils.notNullNotEmpty(this.id) ? this.id : this.ic;
            return trimSpacesAndRemoveLastDot(identifier);
        }
    }
}

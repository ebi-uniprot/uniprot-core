package org.uniprot.cv.subcell;

import static org.uniprot.cv.go.RelationshipType.IS_A;
import static org.uniprot.cv.go.RelationshipType.PART_OF;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.uniprot.core.cv.go.GeneOntologyEntry;
import org.uniprot.core.cv.go.GoTerm;
import org.uniprot.core.cv.go.impl.GeneOntologyEntryBuilder;
import org.uniprot.core.cv.keyword.impl.KeywordIdBuilder;
import org.uniprot.core.cv.subcell.SubcellLocationCategory;
import org.uniprot.core.cv.subcell.SubcellularLocationEntry;
import org.uniprot.core.cv.subcell.impl.SubcellularLocationEntryBuilder;
import org.uniprot.core.cv.subcell.impl.SubcellularLocationEntryImpl;
import org.uniprot.cv.common.AbstractFileReader;
import org.uniprot.cv.go.RelationshipType;

public class SubcellularLocationFileReader extends AbstractFileReader<SubcellularLocationEntry> {
    private static final String HP_LINE = "HP";
    private static final String KW_LINE = "KW";
    private static final String GO_LINE = "GO";
    private static final String AN_LINE = "AN";
    private static final String RX_LINE = "RX";
    private static final String WW_LINE = "WW";
    private static final String HI_LINE = "HI";
    private static final String SL_LINE = "SL";
    private static final String SY_LINE = "SY";
    private static final String DE_LINE = "DE";
    private static final String AC_LINE = "AC";
    private static final String IO_LINE = "IO";
    private static final String IT_LINE = "IT";
    private static final String ID_LINE = "ID";
    private static final String SPLIT_SPACES = "   ";
    private static final Logger LOG = LoggerFactory.getLogger(SubcellularLocationFileReader.class);

    public List<SubcellularLocationEntry> parseLines(List<String> lines) {
        List<SubcellularFileEntry> rawEntryList = convertLinesIntoInMemoryObjectList(lines);
        Map<String, SubcellularFileEntry> idFileEntryMap = getIdFileEntryMap(rawEntryList);
        List<SubcellularLocationEntryImpl> entryList = parseSubcellularFileEntryList(rawEntryList);
        Map<String, SubcellularLocationEntryImpl> nameEntryMap = getNameEntryMap(entryList);
        Set<String> processedIsAIds = new HashSet<>();
        Set<String> processedPartOfIds = new HashSet<>();
        for (SubcellularFileEntry rawEntry : rawEntryList) {
            try {
                updateRelationship(
                        IS_A, rawEntry.hi, rawEntry, idFileEntryMap, processedIsAIds, nameEntryMap);
                updateRelationship(
                        PART_OF,
                        rawEntry.hp,
                        rawEntry,
                        idFileEntryMap,
                        processedPartOfIds,
                        nameEntryMap);
            } catch (Exception e) {
                throw new IllegalArgumentException(e);
            }
        }

        return entryList.stream()
                .map(SubcellularLocationEntry.class::cast)
                .collect(Collectors.toList());
    }

    public Map<String, String> parseFileToAccessionMap(String fileName) {
        List<SubcellularLocationEntry> list = parse(fileName);
        return list.stream()
                .collect(
                        Collectors.toMap(
                                SubcellularLocationEntry::getContent,
                                SubcellularLocationEntry::getId));
    }

    /**
     * @param relationType isA or partOf
     * @param relatedIds list of names of entry which is either part of or is a
     * @param rawEntry object representation of an entry from input file
     * @param idFileEntryMap map which has id as key and raw entry as value
     * @param processedIds - list of ids which has processed isA or partOf relationship
     * @param nameEntryMap - map with key as entry name and value as SubcellularFileEntry
     * @throws Exception
     */
    private void updateRelationship(
            RelationshipType relationType,
            List<String> relatedIds,
            SubcellularFileEntry rawEntry,
            Map<String, SubcellularFileEntry> idFileEntryMap,
            Set<String> processedIds,
            Map<String, SubcellularLocationEntryImpl> nameEntryMap)
            throws Exception {

        SubcellularLocationEntryImpl node =
                nameEntryMap.get(trimSpacesAndRemoveLastDot(getIdentifier(rawEntry)));
        if (Objects.nonNull(node) && !processedIds.contains(node.getId())) {
            processedIds.add(node.getId());
            if (!relatedIds.isEmpty()) { // process children in dfs fashion
                for (String relatedId : relatedIds) {
                    SubcellularLocationEntryImpl relatedSLEntry =
                            nameEntryMap.get(trimSpacesAndRemoveLastDot(relatedId));
                    Field relationField;
                    List<SubcellularLocationEntry> relatedSLEntries;
                    if (relationType == IS_A) {
                        relationField = node.getClass().getDeclaredField("isA");
                        relatedSLEntries =
                                node.getIsA().isEmpty() ? new ArrayList<>() : node.getIsA();
                    } else {
                        relationField = node.getClass().getDeclaredField("partOf");
                        relatedSLEntries =
                                node.getPartOf().isEmpty() ? new ArrayList<>() : node.getPartOf();
                    }
                    relationField.setAccessible(true);
                    relatedSLEntries.add(relatedSLEntry);
                    relationField.set(node, relatedSLEntries);
                    SubcellularFileEntry relatedSLFileEntry = idFileEntryMap.get(relatedId);
                    if (Objects.nonNull(relatedSLFileEntry)) {
                        List<String> childRelatedIds;
                        if (relationType == IS_A) {
                            childRelatedIds = relatedSLFileEntry.hi;
                        } else {
                            childRelatedIds = relatedSLFileEntry.hp;
                        }
                        // call for the child
                        updateRelationship(
                                relationType,
                                childRelatedIds,
                                relatedSLFileEntry,
                                idFileEntryMap,
                                processedIds,
                                nameEntryMap);
                    }
                }
            }
        }
    }

    private Map<String, SubcellularFileEntry> getIdFileEntryMap(
            List<SubcellularFileEntry> rawList) {
        Map<String, SubcellularFileEntry> idFileEntryMap = new HashMap<>();
        for (SubcellularFileEntry raw : rawList) {
            idFileEntryMap.put(getIdentifier(raw), raw);
        }
        return idFileEntryMap;
    }

    private Map<String, SubcellularLocationEntryImpl> getNameEntryMap(
            List<SubcellularLocationEntryImpl> entryList) {
        return entryList.stream()
                .collect(
                        Collectors.toMap(
                                SubcellularLocationEntryImpl::getName, Function.identity()));
    }

    private List<SubcellularFileEntry> convertLinesIntoInMemoryObjectList(List<String> lines) {
        // At the time of writing code there was 513 entries per line
        List<SubcellularFileEntry> retList = new ArrayList<>(550);

        int i = 0;

        // Ignore the header lines and information
        for (; i < lines.size(); i++) {
            String lineIgnore = lines.get(i);
            if (lineIgnore.startsWith("______")) break;
        }

        // Ignore underscore ___ line
        i++;

        // reached entries lines
        SubcellularFileEntry entry = new SubcellularFileEntry();

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
                entry = new SubcellularFileEntry();
                i++;
                continue;
            }

            String[] tokens = line.split(SPLIT_SPACES);
            switch (tokens[0]) {
                case ID_LINE:
                    entry.id = tokens[1];
                    break;
                case IT_LINE:
                    entry.it = tokens[1];
                    break;
                case IO_LINE:
                    entry.io = tokens[1];
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
                case SL_LINE:
                    entry.sl = tokens[1];
                    break;
                case HI_LINE:
                    entry.hi.add(tokens[1]);
                    break;
                case HP_LINE:
                    entry.hp.add(tokens[1]);
                    break;
                case KW_LINE:
                    entry.kw = tokens[1];
                    break;
                case GO_LINE:
                    entry.go.add(tokens[1]);
                    break;
                case AN_LINE:
                    entry.an.add(tokens[1]);
                    break;
                case RX_LINE:
                    entry.rx.add(tokens[1]);
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

    private List<SubcellularLocationEntryImpl> parseSubcellularFileEntryList(
            List<SubcellularFileEntry> list) {
        return list.stream().map(this::parseSubcellularFileEntry).collect(Collectors.toList());
    }

    /**
     * In case properties (strings or list) are empty setting it null. OGM will not insert null
     * properties in neo4j node
     *
     * @param entry SubcellularFileEntry
     * @return SubcellularLocationEntry
     */
    private SubcellularLocationEntryImpl parseSubcellularFileEntry(SubcellularFileEntry entry) {
        SubcellularLocationEntryBuilder retObj = new SubcellularLocationEntryBuilder();
        retObj.id(entry.ac);
        retObj.content(trimSpacesAndRemoveLastDot(entry.sl));

        if (entry.id != null) {
            retObj.name(trimSpacesAndRemoveLastDot(entry.id));
            retObj.category(SubcellLocationCategory.LOCATION);
        } else if (entry.it != null) {
            retObj.name(trimSpacesAndRemoveLastDot(entry.it));
            retObj.category(SubcellLocationCategory.TOPOLOGY);
        } else {
            retObj.name(trimSpacesAndRemoveLastDot(entry.io));
            retObj.category(SubcellLocationCategory.ORIENTATION);
        }
        // definition
        String def = String.join(" ", entry.de);
        retObj.definition(def.isEmpty() ? null : def);

        // Keyword is a single string will null by default
        if ((entry.kw != null) && !entry.kw.isEmpty())
            retObj.keyword(
                    new KeywordIdBuilder().name(retObj.build().getName()).id(entry.kw).build());

        // Links
        retObj.linksSet(entry.ww);

        // Notes
        String note = String.join(" ", entry.an);
        retObj.note(note.isEmpty() ? null : note);

        // Interesting references
        List<String> refList =
                entry.rx.stream()
                        .flatMap(s -> Arrays.stream(s.split(";")))
                        .collect(Collectors.toList());
        retObj.referencesSet(refList);

        // GoMapping
        List<GoTerm> goList =
                entry.go.stream().map(this::parseGeneOntology).collect(Collectors.toList());
        retObj.geneOntologiesSet(goList);

        // Synonyms
        List<String> synList =
                entry.sy.stream()
                        .flatMap(s -> Arrays.stream(s.split(";")))
                        .map(this::trimSpacesAndRemoveLastDot)
                        .collect(Collectors.toList());
        retObj.synonymsSet(synList);

        return (SubcellularLocationEntryImpl) retObj.build();
    }

    private String getIdentifier(SubcellularFileEntry raw) {
        if (raw.id != null) return raw.id;
        return raw.it != null ? raw.it : raw.io;
    }

    private GeneOntologyEntry parseGeneOntology(String go) {
        String[] tokens = go.split(";");
        return new GeneOntologyEntryBuilder().id(tokens[0]).name(tokens[1].trim()).build();
    }

    private String trimSpacesAndRemoveLastDot(String str) {
        if (str == null) return null;
        str = str.trim();
        return str.endsWith(".") ? str.substring(0, str.length() - 1) : str;
    }
}

class SubcellularFileEntry {
    String id;
    String it;
    String io;
    String ac;
    List<String> de;
    List<String> sy;
    String sl;
    List<String> hi;
    List<String> hp;
    String kw;
    List<String> go;
    List<String> an;
    List<String> rx;
    List<String> ww;

    SubcellularFileEntry() {
        de = new ArrayList<>();
        sy = new ArrayList<>();
        hi = new ArrayList<>();
        hp = new ArrayList<>();
        go = new ArrayList<>();
        an = new ArrayList<>();
        rx = new ArrayList<>();
        ww = new ArrayList<>();
    }
}

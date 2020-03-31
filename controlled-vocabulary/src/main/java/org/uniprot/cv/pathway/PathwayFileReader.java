package org.uniprot.cv.pathway;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.uniprot.core.cv.disease.DiseaseCrossReference;
import org.uniprot.core.cv.disease.impl.DiseaseCrossReferenceBuilder;
import org.uniprot.core.cv.pathway.PathwayEntry;
import org.uniprot.core.cv.pathway.impl.PathwayEntryBuilder;
import org.uniprot.cv.common.AbstractFileReader;

public class PathwayFileReader extends AbstractFileReader<PathwayEntry> {
    private static final String ID_LINE = "ID";
    private static final String AC_LINE = "AC";
    private static final String CL_LINE = "CL";
    private static final String DE_LINE = "DE";
    private static final String SY_LINE = "SY";
    private static final String HI_LINE = "HI";
    private static final String HP_LINE = "HP";
    private static final String DR_LINE = "DR";
    private static final String SPLIT_SPACES = "   ";
    private static final String SEMICOLON = ";";
    private static final Logger LOG = LoggerFactory.getLogger(PathwayFileReader.class);
    public static final List<String> UNIPATHWAY_COMMENT_LINES =
            Collections.unmodifiableList(
                    Arrays.asList(
                            "This controlled vocabulary is provided by the UniPathway project",
                            "(http://www.grenoble.prabi.fr/obiwarehouse/unipathway), a collaborative",
                            "effort involving the SIB (http://www.isb-sib.ch/) and the INRIA",
                            "(http://www.inrialpes.fr/)"));

    @Override
    public List<PathwayEntry> parseLines(List<String> lines) {
        List<PathwayFileEntry> rawList = convertLinesIntoInMemoryObjectList(lines);
        List<PathwayEntry> list = parsePathwayFileEntryList(rawList);
        //	updateCategories(list);
        return updateListWithRelationShips(list, rawList);
    }

    private List<PathwayEntry> parsePathwayFileEntryList(List<PathwayFileEntry> rawList) {
        return rawList.stream().map(this::parsePathwayFileEntry).collect(Collectors.toList());
    }

    private PathwayEntry parsePathwayFileEntry(PathwayFileEntry entry) {
        PathwayEntryBuilder retObj = new PathwayEntryBuilder();
        retObj.id(entry.ac);
        retObj.name(trimSpacesAndRemoveLastDot(entry.id));
        retObj.pathwayClass(entry.cl);
        // definition
        String def = String.join(" ", entry.de);
        retObj.definition(def.isEmpty() ? null : def);

        // Synonyms
        List<String> synList =
                entry.sy.stream()
                        .flatMap(s -> Arrays.stream(s.split(";")))
                        .map(this::trimSpacesAndRemoveLastDot)
                        .collect(Collectors.toList());
        retObj.synonymsSet(synList);

        // Cross-reference(s)
        List<DiseaseCrossReference> crList =
                remergeDrLine(entry.dr).stream()
                        .map(this::parseCrossReference)
                        .filter(Objects::nonNull)
                        .collect(Collectors.toList());
        retObj.crossReferencesSet(crList);
        return retObj.build();
    }

    private List<String> remergeDrLine(List<String> dr) {
        if (dr.isEmpty() || dr.size() == 1) return dr;
        List<String> newDr = new ArrayList<>();
        String line = dr.get(0);
        int i = 1;
        do {
            String nextline = dr.get(i);
            if (nextline.contains("; ")) {
                newDr.add(line);
                line = nextline;

            } else {
                line += " ";
                line += nextline;
            }
            i++;
        } while (i < dr.size());
        if (!line.isEmpty()) {
            newDr.add(line);
        }
        return newDr;
    }

    private DiseaseCrossReference parseCrossReference(String cr) {
        if (!cr.contains(SEMICOLON)) {
            System.out.println(cr);
            return null;
        }
        final String[] tokens = cr.split(SEMICOLON);
        final String type = trimSpacesAndRemoveLastDot(tokens[0]);
        final String id = trimSpacesAndRemoveLastDot(tokens[1]);
        final List<String> des =
                Stream.of(Arrays.copyOfRange(tokens, 2, tokens.length))
                        .map(this::trimSpacesAndRemoveLastDot)
                        .collect(Collectors.toList());

        return new DiseaseCrossReferenceBuilder()
                .databaseType(type)
                .id(id)
                .propertiesSet(des)
                .build();
    }

    private List<PathwayEntry> updateListWithRelationShips(
            List<PathwayEntry> list, List<PathwayFileEntry> rawList) {
        List<PathwayEntry> retList = new ArrayList<>(list.size());

        for (PathwayFileEntry raw : rawList) {
            PathwayEntry target = findByIdentifier(list, raw.ac);
            assert (target != null);
            if (raw.hi.isEmpty() && raw.hp.isEmpty()) {
                retList.add(target);
                continue;
            }

            final List<String> hiList =
                    raw.hi.stream()
                            .filter(val -> val.contains(SEMICOLON))
                            .map(val -> val.substring(0, val.indexOf(SEMICOLON)))
                            .collect(Collectors.toList());

            final List<String> hpList =
                    raw.hp.stream()
                            .filter(val -> val.contains(SEMICOLON))
                            .map(val -> val.substring(0, val.indexOf(SEMICOLON)))
                            .collect(Collectors.toList());

            List<PathwayEntry> isAParents =
                    hiList.stream()
                            .map(val -> findByIdentifier(list, val))
                            .filter(Objects::nonNull)
                            .collect(Collectors.toList());

            List<PathwayEntry> partOfParents =
                    hpList.stream()
                            .map(val -> findByIdentifier(list, val))
                            .filter(Objects::nonNull)
                            .collect(Collectors.toList());

            retList.add(
                    PathwayEntryBuilder.from(target)
                            .isAParentsSet(isAParents)
                            .partOfParentsSet(partOfParents)
                            .build());
        }
        return retList;
    }

    private PathwayEntry findByIdentifier(List<PathwayEntry> list, String ac) {
        return list.stream().filter(s -> s.getId().equals(ac)).findFirst().orElse(null);
    }

    private String trimSpacesAndRemoveLastDot(String str) {
        if (str == null) {
            return null;
        }
        str = str.trim();
        return str.endsWith(".") ? str.substring(0, str.length() - 1) : str;
    }

    private List<PathwayFileEntry> convertLinesIntoInMemoryObjectList(List<String> lines) {
        // At the time of writing code there was 5047 entries in file
        List<PathwayFileEntry> retList = new ArrayList<>(5200);

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
        PathwayFileEntry entry = new PathwayFileEntry();

        // create in memory list of objects
        while (i < lines.size()) {
            String line = lines.get(i).trim();
            if (line.isEmpty()) {
                i++;
                continue;
            } else if (COPYRIGHT_LINES.contains(line)) {
                i++;
                continue;
            } else if (UNIPATHWAY_COMMENT_LINES.contains(line)) {
                i++;
                continue;
            }
            // For terminating line no need to complete loop
            if (line.equals("//")) {
                retList.add(entry);
                entry = new PathwayFileEntry();
                i++;
                continue;
            }

            String[] tokens = line.split(SPLIT_SPACES);
            switch (tokens[0]) {
                case ID_LINE:
                    entry.id = tokens[1];
                    break;

                case AC_LINE:
                    entry.ac = tokens[1];
                    break;
                case CL_LINE:
                    entry.cl = tokens[1];
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
                case HP_LINE:
                    entry.hp.add(tokens[1]);
                    break;
                case DR_LINE:
                    entry.dr.add(tokens[1]);
                    break;

                default:
                    LOG.info("Unhandle line found while parsing file: {}", line);
            }
            i++;
        }
        return retList;
    }

    private static class PathwayFileEntry {
        String id;
        String ac;
        String cl;
        List<String> de;
        List<String> sy;
        List<String> hi;
        List<String> hp;
        List<String> dr;

        PathwayFileEntry() {
            de = new ArrayList<>();
            sy = new ArrayList<>();
            hi = new ArrayList<>();
            hp = new ArrayList<>();
            dr = new ArrayList<>();
        }
    }
}

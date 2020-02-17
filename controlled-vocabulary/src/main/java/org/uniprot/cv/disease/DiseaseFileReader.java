package org.uniprot.cv.disease;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.uniprot.core.cv.disease.DiseaseCrossReference;
import org.uniprot.core.cv.disease.DiseaseEntry;
import org.uniprot.core.cv.disease.builder.DiseaseCrossReferenceBuilder;
import org.uniprot.core.cv.disease.impl.DiseaseCrossReferenceImpl;
import org.uniprot.core.cv.disease.impl.DiseaseEntryImpl;
import org.uniprot.core.cv.keyword.Keyword;
import org.uniprot.core.cv.keyword.impl.KeywordImpl;
import org.uniprot.cv.common.AbstractFileReader;

public final class DiseaseFileReader extends AbstractFileReader<DiseaseEntry> {
    private static final String KW_LINE = "KW";
    private static final String DR_LINE = "DR";
    private static final String SY_LINE = "SY";
    private static final String DE_LINE = "DE";
    private static final String AC_LINE = "AC";
    private static final String AR_LINE = "AR";
    private static final String ID_LINE = "ID";
    private static final String SPLIT_SPACES = "   ";
    private static final String COLON = ":";
    private static final String SEMICOLON = ";";
    private static final Logger LOG = LoggerFactory.getLogger(DiseaseFileReader.class);

    public List<DiseaseEntry> parseLines(List<String> lines) {
        return convertLinesIntoInMemoryObjectList(lines).stream()
                .map(this::parseDiseaseFileEntry)
                .collect(Collectors.toList());
    }

    public Map<String, String> parseFileToAccessionMap(String filename) {
        List<DiseaseEntry> diseaseList = parse(filename);
        return diseaseList.stream()
                .collect(Collectors.toMap(DiseaseEntry::getId, DiseaseEntry::getAccession));
    }

    public Iterator<DiseaseEntry> getDiseaseIterator(String fileName) {

        List<DiseaseEntry> diseaseList = parse(fileName);
        return diseaseList.iterator();
    }

    private DiseaseEntry parseDiseaseFileEntry(DiseaseFileEntry entry) {
        String id = trimSpacesAndRemoveLastDot(entry.id);
        String accession = entry.ac;
        String acronym = trimSpacesAndRemoveLastDot(entry.ar);
        String definition = String.join(" ", entry.de);
        List<String> synonyms =
                entry.sy.stream()
                        .map(this::trimSpacesAndRemoveLastDot)
                        .collect(Collectors.toList());

        // Cross-reference(s)
        List<DiseaseCrossReference> crList =
                entry.dr.stream().map(this::parseCrossReference).collect(Collectors.toList());

        List<Keyword> kwList =
                entry.kw.stream().map(this::parseKeyword).collect(Collectors.toList());

        return new DiseaseEntryImpl(id, accession, acronym, definition, synonyms, crList, kwList);
    }

    private Keyword parseKeyword(String kw) {
        final String[] tokens = kw.split(COLON);
        return new KeywordImpl(trimSpacesAndRemoveLastDot(tokens[1]), tokens[0]);
    }

    private DiseaseCrossReference parseCrossReference(String cr) {
        final String[] tokens = cr.split(SEMICOLON);
        final String type = trimSpacesAndRemoveLastDot(tokens[0]);
        final String id = trimSpacesAndRemoveLastDot(tokens[1]);
        final List<String> des =
                Stream.of(Arrays.copyOfRange(tokens, 2, tokens.length))
                        .map(this::trimSpacesAndRemoveLastDot)
                        .collect(Collectors.toList());

        return new DiseaseCrossReferenceBuilder().databaseType(type).id(id).propertiesSet(des).build();
    }

    private String trimSpacesAndRemoveLastDot(String str) {
        if (str == null) {
            return null;
        }
        str = str.trim();
        return str.endsWith(".") ? str.substring(0, str.length() - 1) : str;
    }

    private List<DiseaseFileEntry> convertLinesIntoInMemoryObjectList(List<String> lines) {
        // At the time of writing code there was 5047 entries in file
        List<DiseaseFileEntry> retList = new ArrayList<>(5200);

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
        DiseaseFileEntry entry = new DiseaseFileEntry();

        // create in memory list of objects
        while (i < lines.size()) {
            String line = lines.get(i);
            if (COPYRIGHT_LINES.contains(line)) {
                i++;
                continue;
            } else if (line.trim().isEmpty()) {
                i++;
                continue;
            }
            // For terminating line no need to complete loop
            if (line.equals("//")) {
                retList.add(entry);
                entry = new DiseaseFileEntry();
                i++;
                continue;
            }

            String[] tokens = line.split(SPLIT_SPACES);
            switch (tokens[0]) {
                case ID_LINE:
                    entry.id = tokens[1];
                    break;
                case AR_LINE:
                    entry.ar = tokens[1];
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
                case DR_LINE:
                    entry.dr.add(tokens[1]);
                    break;
                case KW_LINE:
                    entry.kw.add(tokens[1]);
                    break;
                default:
                    LOG.info("Unhandle line found while parsing file: {}", line);
            }

            // read and save next line
            i++;
        }
        return retList;
    }

    private static class DiseaseFileEntry {
        String id;
        String ac;
        String ar;
        List<String> de;
        List<String> sy;
        List<String> dr;
        List<String> kw;

        DiseaseFileEntry() {
            de = new ArrayList<>();
            sy = new ArrayList<>();
            dr = new ArrayList<>();
            kw = new ArrayList<>();
        }
    }
}

package org.uniprot.core.cv.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.uniprot.core.cv.keyword.GeneOntology;
import org.uniprot.core.cv.keyword.impl.GeneOntologyImpl;
import org.uniprot.core.cv.keyword.impl.KeywordImpl;
import org.uniprot.core.cv.subcell.SubcellLocationCategory;
import org.uniprot.core.cv.subcell.SubcellularLocationEntry;
import org.uniprot.core.cv.subcell.impl.SubcellularLocationEntryImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


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
        List<SubcellularFileEntry> rawList = convertLinesIntoInMemoryObjectList(lines);
        List<SubcellularLocationEntry> list = parseSubcellularFileEntryList(rawList);
        updateListWithRelationShips(list, rawList);
        return list;
    }

    public Map<String,String> parseFileToAccessionMap(String fileName) {
        List<SubcellularLocationEntry> list = parse(fileName);
        return list.stream()
                .collect(Collectors.toMap(SubcellularLocationEntry::getContent, SubcellularLocationEntry::getAccession));
    }

    private List<SubcellularFileEntry> convertLinesIntoInMemoryObjectList(List<String> lines) {
        // At the time of writing code there was 513 entries per line
        List<SubcellularFileEntry> retList = new ArrayList<>(550);

        int i = 0;

        // Ignore the header lines and information
        for (; i < lines.size(); i++) {
            String lineIgnore = lines.get(i);
            if (lineIgnore.startsWith("______"))
                break;
        }

        // Ignore underscore ___ line
        i++;

        // reached entries lines
        SubcellularFileEntry entry = new SubcellularFileEntry();

        // create in memory list of objects
        while (i < lines.size()) {
            String line = lines.get(i);
        	if(COPYRIGHT_LINES.contains(line)) {
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

    private List<SubcellularLocationEntry> parseSubcellularFileEntryList(List<SubcellularFileEntry> list) {
        return list.stream().map(this::parseSubcellularFileEntry).collect(Collectors.toList());
    }

    /**
     * In case properties (strings or list) are empty setting it null. OGM will not insert null properties in neo4j node
     * 
     * @param entry
     * @return
     */
    private SubcellularLocationEntry parseSubcellularFileEntry(SubcellularFileEntry entry) {
        SubcellularLocationEntryImpl retObj = new SubcellularLocationEntryImpl();
        retObj.setAccession(entry.ac);
        retObj.setContent(trimSpacesAndRemoveLastDot(entry.sl));

        if (entry.id != null) {           
            retObj.setId(trimSpacesAndRemoveLastDot(entry.id));
            retObj.setCategory(SubcellLocationCategory.LOCATION);
        } else if  (entry.it != null){
        	  retObj.setId(trimSpacesAndRemoveLastDot(entry.it));
            retObj.setCategory(SubcellLocationCategory.TOPOLOGY);
        }else {
        	retObj.setId(trimSpacesAndRemoveLastDot(entry.io));
            retObj.setCategory(SubcellLocationCategory.ORIENTATION);
        }
        // definition
        String def = String.join(" ", entry.de);
        retObj.setDefinition(def.isEmpty() ? null : def);

        // Keyword is a single string will null by default
        if((entry.kw !=null) && !entry.kw.isEmpty())
        	retObj.setKeyword(new KeywordImpl(retObj.getId(), entry.kw));

        // Links
        retObj.setLinks(entry.ww.isEmpty() ? null : entry.ww);

        // Notes
        String note = entry.an.stream().collect(Collectors.joining(" "));
        retObj.setNote(note.isEmpty() ? null : note);

        // Interesting references
        List<String> refList =
                entry.rx.stream().flatMap(s -> Arrays.asList(s.split(";")).stream()).collect(Collectors.toList());
        retObj.setReferences(refList.isEmpty() ? null : refList);

        // GoMapping
        List<GeneOntology> goList = entry.go.stream().map(this::parseGeneOntology).collect(Collectors.toList());
        retObj.setGeneOntologies(goList.isEmpty()? null : goList);

        // Synonyms
        List<String> synList =
                entry.sy.stream().flatMap(s -> Arrays.asList(s.split(";")).stream())
                        .map(this::trimSpacesAndRemoveLastDot)
                        .collect(Collectors.toList());
        retObj.setSynonyms(synList.isEmpty() ? null : synList);

        return retObj;
    }

    private void updateListWithRelationShips(List<SubcellularLocationEntry> list, List<SubcellularFileEntry> rawList) {
        for (SubcellularFileEntry raw : rawList) {

            // Only check for those who have relationships
            if (raw.hi.isEmpty() && raw.hp.isEmpty()) {
                continue;
            }

            SubcellularLocationEntryImpl target = (SubcellularLocationEntryImpl) findByIdentifier(list, getIdentifier(raw));
            
            assert(target !=null);
            
            if (!raw.hi.isEmpty()) {
                List<SubcellularLocationEntry> isA = new ArrayList<>();

                for (String id : raw.hi) {
                    isA.add(findByIdentifier(list, id));
                }
                target.setIsA(isA);
            }
            if (!raw.hp.isEmpty()) {
                target.setPartOf(new ArrayList<>());
                for (String id : raw.hp) {
                    target.getPartOf().add(findByIdentifier(list, id));
                }
            }
        }
    }

    private String getIdentifier(SubcellularFileEntry raw) {
        if (raw.id != null)
            return raw.id;
        return raw.it != null ? raw.it : raw.io;
    }

    private SubcellularLocationEntry findByIdentifier(List<SubcellularLocationEntry> list, String id) {
        return list.stream().filter(
                s -> s.getId().equals(trimSpacesAndRemoveLastDot(id)))
                .findFirst().orElse(null);
    }

    private GeneOntology parseGeneOntology(String go) {
        String[] tokens = go.split(";");
        return new GeneOntologyImpl(tokens[0], tokens[1].trim());
    }

    private String trimSpacesAndRemoveLastDot(String str) {
        if (str == null)
            return null;
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

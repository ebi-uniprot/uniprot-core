package uk.ac.ebi.uniprot.cv.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.ac.ebi.uniprot.cv.keyword.GeneOntology;
import uk.ac.ebi.uniprot.cv.keyword.impl.GeneOntologyImpl;
import uk.ac.ebi.uniprot.cv.keyword.impl.KeywordImpl;
import uk.ac.ebi.uniprot.cv.subcell.SubcellLocationType;
import uk.ac.ebi.uniprot.cv.subcell.SubcellularLocation;
import uk.ac.ebi.uniprot.cv.subcell.impl.SubcellularLocationImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class SubcellularLocationFileReader extends AbstractFileReader<SubcellularLocation> {
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

    public List<SubcellularLocation> parseLines(List<String> lines) {
        List<SubcellularFileEntry> rawList = convertLinesIntoInMemoryObjectList(lines);
        List<SubcellularLocation> list = parseSubcellularFileEntryList(rawList);
        updateListWithRelationShips(list, rawList);
        return list;
    }

    public Map<String,String> parseFileToAccessionMap(String fileName) {
        List<SubcellularLocation> list = parse(fileName);
        return list.stream()
                .collect(Collectors.toMap(SubcellularLocation::getId,SubcellularLocation::getAccession));
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
                    entry.ID = tokens[1];
                    break;
                case IT_LINE:
                    entry.IT = tokens[1];
                    break;
                case IO_LINE:
                    entry.IO = tokens[1];
                    break;
                case AC_LINE:
                    entry.AC = tokens[1];
                    break;
                case DE_LINE:
                    entry.DE.add(tokens[1]);
                    break;
                case SY_LINE:
                    entry.SY.add(tokens[1]);
                    break;
                case SL_LINE:
                    entry.SL = tokens[1];
                    break;
                case HI_LINE:
                    entry.HI.add(tokens[1]);
                    break;
                case HP_LINE:
                    entry.HP.add(tokens[1]);
                    break;
                case KW_LINE:
                    entry.KW = tokens[1];
                    break;
                case GO_LINE:
                    entry.GO.add(tokens[1]);
                    break;
                case AN_LINE:
                    entry.AN.add(tokens[1]);
                    break;
                case RX_LINE:
                    entry.RX.add(tokens[1]);
                    break;
                case WW_LINE:
                    entry.WW.add(tokens[1]);
                    break;
                default:
                    LOG.info("Unhandle line found while parsing file: {}", line);

            }

            // read and save next line
            i++;
        }
        return retList;
    }

    private List<SubcellularLocation> parseSubcellularFileEntryList(List<SubcellularFileEntry> list) {
        return list.stream().map(this::parseSubcellularFileEntry).collect(Collectors.toList());
    }

    /**
     * In case properties (strings or list) are empty setting it null. OGM will not insert null properties in neo4j node
     * 
     * @param entry
     * @return
     */
    private SubcellularLocation parseSubcellularFileEntry(SubcellularFileEntry entry) {
        SubcellularLocationImpl retObj =new SubcellularLocationImpl();
        retObj.setAccession(entry.AC);
        retObj.setContent(trimSpacesAndRemoveLastDot(entry.SL));

        if (entry.ID != null) {           
            retObj.setId(trimSpacesAndRemoveLastDot(entry.ID));
            retObj.setType(SubcellLocationType.LOCATION);
        } else if  (entry.IT != null){
        	  retObj.setId(trimSpacesAndRemoveLastDot(entry.IT));
              retObj.setType(SubcellLocationType.TOPOLOGY);
        }else {
        	retObj.setId(trimSpacesAndRemoveLastDot(entry.IO));
            retObj.setType(SubcellLocationType.ORIENTATION);
        }
        // definition
        String def = String.join(" ", entry.DE);
        retObj.setDefinition(def.isEmpty() ? null : def);

        // Keyword is a single string will null by default
        if((entry.KW !=null) && !entry.KW.isEmpty())
        	retObj.setKeyword(new KeywordImpl(retObj.getId(), entry.KW));

        // Links
        retObj.setLinks(entry.WW.isEmpty() ? null : entry.WW);

        // Notes
        String note = entry.AN.stream().collect(Collectors.joining(" "));
        retObj.setNote(note.isEmpty() ? null : note);

        // Interesting references
        List<String> refList =
                entry.RX.stream().flatMap(s -> Arrays.asList(s.split(";")).stream()).collect(Collectors.toList());
        retObj.setReferences(refList.isEmpty() ? null : refList);

        // GoMapping
        List<GeneOntology> goList = entry.GO.stream().map(this::parseGeneOntology).collect(Collectors.toList());
        retObj.setGeneOntologies(goList.isEmpty()? null : goList);

        // Synonyms
        List<String> synList =
                entry.SY.stream().flatMap(s -> Arrays.asList(s.split(";")).stream())
                        .map(this::trimSpacesAndRemoveLastDot)
                        .collect(Collectors.toList());
        retObj.setSynonyms(synList.isEmpty() ? null : synList);

        return retObj;
    }

    private void updateListWithRelationShips(List<SubcellularLocation> list, List<SubcellularFileEntry> rawList) {
        for (SubcellularFileEntry raw : rawList) {

            // Only check for those who have relationships
            if (raw.HI.isEmpty() && raw.HP.isEmpty()) {
                continue;
            }
            
            SubcellularLocationImpl target = (SubcellularLocationImpl) findByIdentifier(list, getIdentifier(raw));
            
            assert(target !=null);
            
            if (!raw.HI.isEmpty()) {
            	List<SubcellularLocation> isA= new ArrayList<>();

                for (String id : raw.HI) {
                    isA.add(findByIdentifier(list, id));
                }
                target.setIsA(isA);
            }
            if (!raw.HP.isEmpty()) {
                target.setPartOf(new ArrayList<>());
                for (String id : raw.HP) {
                    target.getPartOf().add(findByIdentifier(list, id));
                }
            }
        }
    }

    private String getIdentifier(SubcellularFileEntry raw) {
        if (raw.ID != null)
            return raw.ID;
        return raw.IT != null ? raw.IT : raw.IO;
    }

    private SubcellularLocation findByIdentifier(List<SubcellularLocation> list, String id) {
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
    String ID;
    String IT;
    String IO;
    String AC;
    List<String> DE;
    List<String> SY;
    String SL;
    List<String> HI;
    List<String> HP;
    String KW;
    List<String> GO;
    List<String> AN;
    List<String> RX;
    List<String> WW;

    SubcellularFileEntry() {
        DE = new ArrayList<>();
        SY = new ArrayList<>();
        HI = new ArrayList<>();
        HP = new ArrayList<>();
        GO = new ArrayList<>();
        AN = new ArrayList<>();
        RX = new ArrayList<>();
        WW = new ArrayList<>();
    }
}

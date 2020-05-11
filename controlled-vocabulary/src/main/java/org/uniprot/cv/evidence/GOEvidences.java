package org.uniprot.cv.evidence;

import org.uniprot.cv.common.AbstractFileReader;
import org.uniprot.cv.common.CVSystemProperties;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public enum GOEvidences {
    INSTANCE;

    private static final String COLON = ":";
    private static final String DEFAULT = "Default";
    private static final String GAF_ECO_URL =
            "https://raw.githubusercontent.com/evidenceontology/evidenceontology/master/gaf-eco-mapping.txt";
    private Map<String, String> defaultMapping = new HashMap<>();
    private Map<String, String> defaultMappingFromEco = new HashMap<>();
    private Map<String, String> otherMapping = new HashMap<>();

    GOEvidences() {
        init();
    }

    public Optional<String> convertGAFToECO(String threeLet) {
        return convertGAFToECO(threeLet, null);
    }

    public Optional<String> convertGAFToECO(String threeLet, String goRef) {
        if ((goRef == null) || (goRef.isEmpty()) || (DEFAULT.equalsIgnoreCase(goRef))) {
            return Optional.ofNullable(defaultMapping.get(threeLet));
        }
        String ecoCode = otherMapping.get(threeLet + COLON + goRef);
        if (ecoCode != null) return Optional.ofNullable(ecoCode);
        else return Optional.ofNullable(defaultMapping.get(threeLet));
    }

    public Optional<String> convertECOToGAF(String ecoCode) {
        String val = defaultMappingFromEco.get(ecoCode);
        if (val == null) {
            for (Map.Entry<String, String> entry : otherMapping.entrySet()) {
                if (entry.getValue().equals(ecoCode)) {
                    val = entry.getKey().substring(0, entry.getKey().indexOf(COLON));
                }
            }
        }
        return Optional.ofNullable(val);
    }

    private void init() {
        List<String> lines =
                AbstractFileReader.readLines(
                        Optional.ofNullable(CVSystemProperties.getGafEcoLocation())
                                .orElse(GAF_ECO_URL));
        for (String line : lines) {
            line = line.trim();
            if ((line.length() == 0) || line.startsWith("#")) continue;
            parseLine(line);
        }
    }

    private void parseLine(String line) {
        String[] tokens = line.split("\t");
        if (tokens.length < 3) return;
        if (tokens[1].equalsIgnoreCase(DEFAULT)) {
            defaultMapping.put(tokens[0], tokens[2]);
            defaultMappingFromEco.put(tokens[2], tokens[0]);
        } else {
            String val = tokens[0] + COLON + tokens[1];
            otherMapping.put(val, tokens[2]);
        }
    }
}

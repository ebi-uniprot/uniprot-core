package uk.ac.ebi.uniprot.domain.uniprot.xdb;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public enum GoEvidences {

    INSTANCE;
    private static final String COLON = ":";
    private static final String DEFAULT = "Default";
    private static final String GAF_ECO_URL = "https://raw.githubusercontent.com/evidenceontology/evidenceontology/master/gaf-eco-mapping.txt";
    private static final String LOCAL_GAF_ECO_FILE = "META-INF/gaf-eco-mapping.txt";
    private Map<String, String> defaultMapping = new HashMap<>();
    private Map<String, String> defaultMappingFromEco = new HashMap<>();
    private Map<String, String> otherMapping = new HashMap<>();

    private GoEvidences() {
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
        if (ecoCode != null)
            return Optional.ofNullable(ecoCode);
        else
            return Optional.ofNullable(defaultMapping.get(threeLet));
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
        try {

            InputStream is = getInputStreamFromURL();
            if (is == null) {
                // logger.warn(GAF_ECO_URL +" cannot be accessed");
                is = this.getClass().getClassLoader().getResourceAsStream(LOCAL_GAF_ECO_FILE);
            }
            if (is == null)
                throw new IllegalArgumentException("GAF_ECO file cannot be accessed.");
            BufferedReader bif = new BufferedReader(new InputStreamReader(is));
            String l;

            while ((l = bif.readLine()) != null) {
                l = l.trim();
                if ((l.length() == 0) || l.startsWith("#"))
                    continue;
                parseLine(l);
            }

        } catch (Exception e) {

        }
    }

    private InputStream getInputStreamFromURL() {
        try {
            URL url = new URL(GAF_ECO_URL);
            return url.openStream();
        } catch (Exception e) {

        }

        return null;
    }

    private void parseLine(String line) {
        String[] tokens = line.split("\t");
        if (tokens.length < 3)
            return;
        if (tokens[1].equalsIgnoreCase(DEFAULT)) {
            defaultMapping.put(tokens[0], tokens[2]);
            defaultMappingFromEco.put(tokens[2], tokens[0]);
        } else {
            String val = tokens[0] + COLON + tokens[1];
            otherMapping.put(val, tokens[2]);
        }
    }
}

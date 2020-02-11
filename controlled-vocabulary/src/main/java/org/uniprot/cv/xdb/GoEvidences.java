package org.uniprot.cv.xdb;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum GoEvidences {
    INSTANCE;
    private static final Logger LOGGER = LoggerFactory.getLogger(GoEvidences.class);
    private static final String COLON = ":";
    private static final String DEFAULT = "Default";
    private static final String GAF_ECO_URL =
            "https://raw.githubusercontent.com/evidenceontology/evidenceontology/master/gaf-eco-mapping.txt";
    private static final String LOCAL_GAF_ECO_FILE = "META-INF/gaf-eco-mapping.txt";
    private Map<String, String> defaultMapping = new HashMap<>();
    private Map<String, String> defaultMappingFromEco = new HashMap<>();
    private Map<String, String> otherMapping = new HashMap<>();

    GoEvidences() {
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
        try {
            InputStream is = getInputStreamFromURL();
            if (is == null) {
                is = this.getClass().getClassLoader().getResourceAsStream(LOCAL_GAF_ECO_FILE);

                if (is == null) {
                    throw new IllegalArgumentException("GAF_ECO file cannot be accessed.");
                }
            }

            try (BufferedReader bif = new BufferedReader(new InputStreamReader(is))) {
                String line;

                while ((line = bif.readLine()) != null) {
                    line = line.trim();
                    if ((line.length() == 0) || line.startsWith("#")) continue;
                    parseLine(line);
                }
            }

        } catch (Exception e) {
            LOGGER.error("Error encountered when initialising GO evidences", e);
        }
    }

    private InputStream getInputStreamFromURL() {
        try {
            URL url = new URL(GAF_ECO_URL);
            return url.openStream();
        } catch (Exception e) {
            LOGGER.warn("Could not open stream for {}", GAF_ECO_URL);
        }

        return null;
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

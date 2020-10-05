package org.uniprot.core.flatfile.tool.ca;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

import com.google.common.collect.Lists;

// import static uk.ac.ebi.kraken.datamining.cv.CatalyticActivityUtils.RHEA_PREFIX;

/**
 * Created by Hermann Zellner on 27/09/18. Provides the controlled vocabulary for Structured
 * Catalytic Activity comment
 */
public class CatalyticActivityFileRepository implements CatalyticActivityRepository {

    private final Map<String, CatalyticActivity> rheaToCa = new TreeMap<>();
    private Map<String, CatalyticActivity> oldTextToEcs;

    private static final String SPLITTER = "\t";
    private static final String SUB_SPLITTER = ",";
    private static final String RHEA_PREFIX = "RHEA:";

    private static final String CV_HEADER =
            "Rhea Id\tEC numbers\tEquation\tParticipants Ids\tLeftToRight reaction Id\tRightToLeft reaction Id\t"
                    + "EC reaction mapping status";

    /**
     * @param rheaStream Controlled vocabulary (CV) for catalytic activity comment splitted by line
     *     break. Each line contains either data related to one Rhea entry or an old catalytic
     *     activity text, which currently cannot be mapped to Rhea and an EC number.
     */
    public CatalyticActivityFileRepository(InputStream rheaStream) {
        oldTextToEcs = new HashMap<>();
        readRheaMapping(rheaStream);
    }

    /**
     * Retrieves RheaData for a Rhea-ID (e.g. RHEA:10000)
     *
     * @param rheaId
     * @return RheaData for rheaId
     */
    @Override
    public CatalyticActivity getByRheaId(String rheaId) {
        return rheaToCa.get(rheaId);
    }

    /**
     * Retrieves RheaData for a valid Catalytic Activity Text, which is not mapped to Rhea
     *
     * @param text
     * @return RheaData
     */
    @Override
    public CatalyticActivity getByOldText(String text) {
        return oldTextToEcs.getOrDefault(text, oldTextToEcs.get(modifyOldText(text)));
    }

    private String modifyOldText(String text) {
        return text;
    }

    private void readRheaMapping(InputStream rheaStream) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(rheaStream))) {
            String line = reader.readLine();
            checkHeaderCv(line);

            while ((line = reader.readLine()) != null) {
                String[] splittedLine = line.split(SPLITTER);
                verifyFormat(splittedLine);

                CatalyticActivity data = extractMapping(splittedLine);
                if (data.getRheaUn() == null) {
                    addToRheaData(oldTextToEcs, data.getText(), data);
                } else {
                    addToRheaData(rheaToCa, data.getRheaUn(), data);
                }
            }
        } catch (IOException e) {
            throw new CatalyticActivityMappingException("Error while Reading CV for CA.", e);
        }
    }

    private void addToRheaData(
            Map<String, CatalyticActivity> map, String key, CatalyticActivity data) {
        if (map.containsKey(key)) {
            data = mergeRheaMapping(map.get(key), data);
        }
        map.put(key, data);
    }

    private void checkHeaderCv(String line) {
        if (!line.equals(CV_HEADER)) {
            throw new CatalyticActivityMappingException(
                    String.format("Invalid header in mapping file: %s", line));
        }
    }

    private void verifyFormat(String[] splittedLine) {
        if (splittedLine.length < 4 || splittedLine.length > 7) {
            throw new CatalyticActivityMappingException(
                    String.format(
                            "Malformed line. Expected between 4 and 6 columns splitted by TAB but found %d. "
                                    + "Line elements are %s - %s",
                            splittedLine.length, splittedLine[0], splittedLine[1]));
        }

        if (splittedLine[2].length() == 0) {
            throw new CatalyticActivityMappingException(
                    "Empty text for Catalytic Activity Comment");
        }

        verifyRheaId(splittedLine[0]);
        if (splittedLine.length > 4) {
            verifyRheaId(splittedLine[4]);
        }
        if (splittedLine.length > 5) {
            verifyRheaId(splittedLine[5]);
        }
    }

    private CatalyticActivity extractMapping(String[] splittedLine) {
        String rheaUn = extractRheaId(splittedLine, 0);
        String text = splittedLine[2];
        String rheaLr = null;
        String rheaRl = null;
        if (splittedLine.length > 4) {
            rheaLr = extractRheaId(splittedLine, 4);
        }
        if (splittedLine.length > 5) {
            rheaRl = extractRheaId(splittedLine, 5);
        }

        List<String> ecs = extractMultipleString(splittedLine[1]);
        List<String> reactantIds = extractMultipleString(splittedLine[3]);

        return new CatalyticActivity(rheaUn, text, reactantIds, ecs, rheaLr, rheaRl);
    }

    private CatalyticActivity mergeRheaMapping(
            CatalyticActivity rheaData1, CatalyticActivity rheaData2) {
        if (rheaData1.getRheaUn() != null
                || rheaData2.getRheaUn() != null
                || rheaData1.getRheaLr() != null
                || rheaData2.getRheaLr() != null
                || rheaData1.getRheaRl() != null
                || rheaData2.getRheaRl() != null) {
            throw new CatalyticActivityMappingException(
                    "Expected to merge RheaData only for Free-Text comments!");
        }
        if (!rheaData2.getReactantIds().isEmpty() || !rheaData1.getReactantIds().isEmpty()) {
            throw new CatalyticActivityMappingException(
                    "Free-Text comments are not expected to map to Reactant-IDs");
        }

        List<String> reactants = new ArrayList<>(rheaData1.getReactantIds());
        reactants.addAll(rheaData2.getReactantIds());
        List<String> ecs = new ArrayList<>(rheaData1.getEcs());
        ecs.addAll(rheaData2.getEcs());

        return new CatalyticActivity(
                null, rheaData1.getText(), rheaData2.getReactantIds(), ecs, null, null);
    }

    private void verifyRheaId(String s) {
        if (!s.isEmpty() && !parsableAsInteger(s)) {
            throw new CatalyticActivityMappingException(
                    String.format("RheaId column is not an Integer: %s", s));
        }
    }

    private boolean parsableAsInteger(String s) {
        try {
            Integer.valueOf(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private String extractRheaId(String[] splittedString, int pos) {
        String rheaId = null;
        if (splittedString[pos].length() > 0) {
            rheaId = splittedString[pos];
            try {
                Integer.parseInt(rheaId);
            } catch (NumberFormatException e) {
                throw new CatalyticActivityMappingException(
                        String.format("Expected integer as Rhea-ID but got %s.", rheaId));
            }
        }

        return rheaId == null ? rheaId : RHEA_PREFIX + rheaId;
    }

    private List<String> extractMultipleString(String values) {

        List<String> splitted =
                Arrays.stream(values.split(SUB_SPLITTER))
                        .map(String::trim)
                        .filter(s -> !s.isEmpty())
                        .collect(Collectors.toList());

        List<String> result = Lists.newArrayList();
        result.addAll(splitted);

        return result;
    }
}

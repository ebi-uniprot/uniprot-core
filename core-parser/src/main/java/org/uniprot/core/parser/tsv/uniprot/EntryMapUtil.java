package org.uniprot.core.parser.tsv.uniprot;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.uniprot.core.uniprotkb.taxonomy.OrganismName;
import org.uniprot.core.util.Utils;

public class EntryMapUtil {
    private static final String EMPTY_STRING = "";
    private static final String OPEN_BRACKET = "(";
    private static final String CLOSE_BRACKET = ")";
    private static final String SPACE = " ";
    private static final String SEMI_COLON_WITH_SPACE = "; ";
    private static final String COMMA_WITH_SPACE = ", ";

    public static String convertOrganism(OrganismName organism) {
        StringBuilder sb = new StringBuilder();
        if (Utils.notNullNotEmpty(organism.getScientificName())) {
            sb.append(organism.getScientificName());
        }
        if (Utils.notNullNotEmpty(organism.getCommonName())) {
            sb.append(SPACE).append(OPEN_BRACKET);
            sb.append(organism.getCommonName());
            sb.append(CLOSE_BRACKET);
        }
        List<String> synonyms = organism.getSynonyms();
        if (Utils.notNullNotEmpty(synonyms)) {
            sb.append(SPACE).append(OPEN_BRACKET);
            sb.append(String.join(COMMA_WITH_SPACE, synonyms)).append(CLOSE_BRACKET);
        }
        return sb.toString();
    }

    /**
     * Converts a map<string,int> to tsv format map sorted by key e.g. Active site (1); Binding site
     * (4); Chain (1); Erroneous initiation (1); Metal binding (4)
     *
     * @param countByTypeMap
     * @return
     */
    public static String convertMapToTSVString(Map<String, Integer> countByTypeMap) {
        if (Utils.notNullNotEmpty(countByTypeMap)) {
            return countByTypeMap.entrySet().stream()
                    .sorted(Map.Entry.comparingByKey())
                    .map(EntryMapUtil::toTSVKeyValue)
                    .collect(Collectors.joining(SEMI_COLON_WITH_SPACE));
        } else {
            return EMPTY_STRING;
        }
    }

    private static String toTSVKeyValue(Map.Entry<String, Integer> entry) {
        StringBuilder builder = new StringBuilder();
        builder.append(entry.getKey());
        builder.append(SPACE).append(OPEN_BRACKET);
        builder.append(entry.getValue());
        builder.append(CLOSE_BRACKET);
        return builder.toString();
    }

    public static String convertToTSVString(
            String extraAttribName, Map<String, Object> extraAttributes) {
        Object attribValue = extraAttributes.getOrDefault(extraAttribName, new Object());
        if (attribValue instanceof Map) {
            return convertMapToTSVString((Map<String, Integer>) attribValue);
        } else {
            return attribValue.toString();
        }
    }
}

package org.uniprot.core.flatfile.parser.impl;

import java.util.Arrays;
import java.util.List;

import org.uniprot.core.uniprot.taxonomy.OrganismName;
import org.uniprot.core.uniprot.taxonomy.builder.OrganismBuilder;
import org.uniprot.core.util.Pair;
import org.uniprot.core.util.PairImpl;

public class OrganismNameLineParser {

    private static final List<String> STRAINS = Arrays.asList(" (strain", " (isolate");

    public static OrganismName createFromOrganismLine(String organismName) {
        OrganismBuilder builder = new OrganismBuilder();
        String value = organismName;
        if ((value.endsWith(".") && (value.charAt(value.length() - 2) != ')'))
                || (value.charAt(value.length() - 1) != ')')) {
            builder.scientificName(value);
            return builder.build();
        }
        if ((value.endsWith(".") && (value.charAt(value.length() - 2) == ')'))
                || (value.charAt(value.length() - 1) == ')')) {
            if (value.indexOf(" (") == -1) {
                builder.scientificName(value);
                return builder.build();
            }
        }
        if (value.endsWith(".")) {
            value = value.substring(0, value.length() - 1);
        }

        String rest = value.trim();
        int strainStart = -1;
        int start = 0;
        int startBracketIndex = -1;
        Pair<String, Integer> pair = containStrain(rest);
        if (pair != null) {
            strainStart = pair.getValue();
            start = getEndBlacket(rest, strainStart + pair.getKey().length());
            if (start == -1) {
                builder.scientificName(value);
                return builder.build();
            }
        }
        startBracketIndex = rest.indexOf("(", start + 1);
        if (startBracketIndex == -1) {
            builder.scientificName(value);
            return builder.build();
        }
        String scientificName = rest.substring(0, startBracketIndex).trim();

        int endBracketIndex = getEndBlacket(rest, startBracketIndex + 1);
        if (endBracketIndex == -1) {
            throw new IllegalArgumentException("organism name: " + organismName + " is not right");
        }
        String commonName = rest.substring(startBracketIndex + 1, endBracketIndex).trim();
        rest = rest.substring(endBracketIndex + 1).trim();
        String synonym = null;
        if (!rest.isEmpty()) {
            startBracketIndex = rest.indexOf("(");
            if (startBracketIndex == -1) {
                throw new IllegalArgumentException(
                        "organism name: " + organismName + " is not right");
            }
            endBracketIndex = getEndBlacket(rest, startBracketIndex + 1);
            if (endBracketIndex == -1) {
                throw new IllegalArgumentException(
                        "organism name: " + organismName + " is not right");
            }
            synonym = rest.substring(startBracketIndex + 1, endBracketIndex).trim();
            builder.scientificName(scientificName);
            builder.commonName(commonName);
            builder.synonymsSet(Arrays.asList(synonym.split(", ")));
            return builder.build();
        } else {
            builder.scientificName(scientificName);
            builder.commonName(commonName);
            return builder.build();
        }
    }

    private static Pair<String, Integer> containStrain(String value) {
        int index = value.indexOf(" (");
        if (index == -1) {
            return null;
        }
        String subStr = value.substring(index);

        for (String str : STRAINS) {
            if (subStr.startsWith(str)) {
                return new PairImpl<>(str, index);
            }
        }
        return null;
    }

    private static int getEndBlacket(String val, int start) {
        char endC = ')';
        char startC = '(';
        String value = val;
        if (value.endsWith(".")) {
            value = value.substring(0, value.length() - 1);
        }
        if (value.charAt(value.length() - 1) != endC) return -1;
        int countStart = 0;
        int countEnd = 0;
        for (int i = start; i < value.length(); i++) {
            if (value.charAt(i) == endC) {
                if (countStart == countEnd) return i;
                else countEnd++;
            } else if (value.charAt(i) == startC) {
                countStart++;
            }
        }

        return -1;
    }
}

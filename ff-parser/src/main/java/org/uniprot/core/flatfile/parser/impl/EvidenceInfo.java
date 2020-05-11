package org.uniprot.core.flatfile.parser.impl;

import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** Keep the evidence Info for the LineObject. */
public class EvidenceInfo {
    // put the evidence for a certain object.
    // object is the value, List<String> is the list of evidence it has.
    private Map<Object, List<String>> evidences = new HashMap<>();
    public static Pattern pattern = Pattern.compile("(-|:)\n[A-Z][A-Z] +");

    public Map<Object, List<String>> getEvidences() {
        return evidences;
    }

    public static void processEvidence(
            EvidenceInfo ev, Object key, List<TerminalNode> terminalNodes) {
        ev.evidences.put(key, processEvidence(terminalNodes));
    }

    public static List<String> processEvidence(List<TerminalNode> terminalNodes) {
        List<String> strings = new ArrayList<String>();
        for (TerminalNode terminalNode : terminalNodes) {
            String text = terminalNode.getText();
            strings.add(removeHypen(text));
        }
        return strings;
    }

    public static void processEvidenceString(
            EvidenceInfo ev, Object key, List<String> terminalNodes) {
        List<String> strings = new ArrayList<String>();
        for (String s : terminalNodes) {
            strings.add(removeHypen(s));
        }

        ev.evidences.put(key, strings);
    }

    public static String removeHypen(String s) {
        Matcher matcher = pattern.matcher(s);

        if (matcher.find()) {
            int start = matcher.start() + 1;
            int end = matcher.end();
            String substring1 = s.substring(0, start);
            String substring2 = s.substring(end);
            return substring1 + substring2;
        }

        return s;
    }

    public String retrieveEvidenceString(String nameWithEvidence) {
        String[] strs = splitEvidenceString(nameWithEvidence);
        if (strs.length == 2) {
            this.evidences.put(strs[0], parseEvidenceFromString(strs[1]));
            return strs[0];
        } else {
            return nameWithEvidence;
        }
    }

    public static String[] splitEvidenceString(String nameWithEvidence) {
        if (nameWithEvidence.endsWith("}")) {
            int i = nameWithEvidence.lastIndexOf("{ECO");
            if (i >= 0) { // possible that there is only evidence in the name.
                String name = nameWithEvidence.substring(0, i).trim();
                String evidence = nameWithEvidence.substring(i);
                return new String[] {name, evidence};
            }
        }
        return new String[] {nameWithEvidence};
    }

    // parsing the evidence string //in the format of {}
    // {ECO:0000256|PIRNR:PIRNR038994, ECO:0000256|PIRNR:PIRNR038995}
    public static List<String> parseEvidenceFromString(String evString) {
        assert evString.startsWith("{") && evString.endsWith("}") : "not a valid string";
        String substring = evString.substring(1, evString.length() - 1);
        // remove space, which can happens when there is change-of-line hapenning in the evidence.
        // change-of-line is normally converted into a space.
        return Arrays.asList(substring.replace(" ", "").split(","));
    }
}

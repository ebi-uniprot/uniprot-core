package org.uniprot.core.xml.uniprot;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class FileMerger {

    public static void main(String[] args) throws IOException {
//        String fullFile = "/Users/sahmad/work/uniprot-core/xml-parser/src/test/java/org/uniprot/core/xml/uniprot/full.txt";
//        String fullFile = getFullFileById("A0A6A5BR32");
//        String predictedFile = "/Users/sahmad/work/uniprot-core/xml-parser/src/test/java/org/uniprot/core/xml/uniprot/predicted.txt";
        File inputDir = new File("/Users/sahmad/work/protnlm-data/google/flatfile");
        File outputDir = new File("/Users/sahmad/work/protnlm-data/google/merged");
        File[] flatFiles = inputDir.listFiles((dir, name) -> name.toLowerCase().endsWith(".txt"));
//        String mergedFile = "merged.txt";
        int counter = 0;
        int skipped = 0;
        for(File flatFile : flatFiles) {
            String accession = flatFile.getName().split("\\.")[0];
            List<String> fullEntry = getFullFileById(accession);
            if(fullEntry.isEmpty()) {
                skipped++;
                System.out.println("Skipping " + flatFile.getName() + " because it has no entry in UniProt");
                continue;
            }
            List<String> predictedEntry = Files.readAllLines(flatFile.toPath());

            List<String> merged = new ArrayList<>();

            removeTrailingSlash(fullEntry);
            removeTrailingSlash(predictedEntry);

            // Merge lines by prefix, keeping all predicted lines even if identical
            Map<String, List<String>> mergedGroups = mergeByPrefixKeepAll(fullEntry, predictedEntry);

            // Write merged lines prefix by prefix
            for (String prefix : mergedGroups.keySet()) {
                merged.addAll(mergedGroups.get(prefix));
            }
            merged.add(""); // blank line between entries


            merged.add("//"); // single trailing //
            // Build output file path
            Path outputPath = new File(outputDir, accession+".txt").toPath();
            Files.write(outputPath, merged);
            System.out.println("Merged file created: " + outputPath.toAbsolutePath());
            counter++;
        }
        System.out.println("Total merged files: " + counter);
        System.out.println("Total merged files skipped: " + skipped);
    }

    private static void removeTrailingSlash(List<String> entry) {
        if (!entry.isEmpty() && entry.get(entry.size() - 1).trim().equals("//")) {
            entry.remove(entry.size() - 1);
        }
    }

    // Merge lines by prefix, always keeping predicted lines even if identical
    private static Map<String, List<String>> mergeByPrefixKeepAll(List<String> full, List<String> predicted) {
        Map<String, List<String>> merged = new LinkedHashMap<>();
        Map<String, List<String>> fullGroups = groupLinesByPrefix(full);
        Map<String, List<String>> predictedGroups = groupLinesByPrefix(predicted);

        Set<String> allPrefixes = new LinkedHashSet<>();
        allPrefixes.addAll(fullGroups.keySet());
        allPrefixes.addAll(predictedGroups.keySet());

        for (String prefix : allPrefixes) {
            List<String> mergedBlock = new ArrayList<>();
            List<String> fullBlock = fullGroups.getOrDefault(prefix, new ArrayList<>());
            List<String> predictedBlock = predictedGroups.getOrDefault(prefix, new ArrayList<>());

            mergedBlock.addAll(fullBlock);
            if(!ignorePredictedBlock(predictedBlock)) {
                for (String line : predictedBlock) {
                    mergedBlock.add(line + " (Predicted)");
                }
            }
            merged.put(prefix, mergedBlock);
        }
        return merged;
    }

    private static boolean ignorePredictedBlock(List<String> predictedBlock) {
        return predictedBlock.stream().anyMatch(line ->
                line.startsWith("ID") || line.startsWith("AC") || line.startsWith("DT") ||
                        line.startsWith("OS") || line.startsWith("OC") ||
                        line.startsWith("OX") || line.startsWith("RN") ||
                        line.startsWith("RP") || line.startsWith("RL") ||
                        line.startsWith("PE") || line.startsWith("SQ") || (line.startsWith("DE") && line.contains("Uncharacterized protein"))
        );
    }

    // Group lines by prefix, including continuation lines for multiline blocks
    private static Map<String, List<String>> groupLinesByPrefix(List<String> lines) {
        Map<String, List<String>> map = new LinkedHashMap<>();
        String currentPrefix = null;
        for (String line : lines) {
            if (line.trim().isEmpty()) continue;

            if (line.startsWith(" ") || line.startsWith("\t")) {
                // continuation line for previous prefix
                if (currentPrefix != null) {
                    map.get(currentPrefix).add(line);
                }
            } else {
                // new prefix line
                String firstWord = line.trim().split("\\s+")[0];
                currentPrefix = firstWord;
                map.computeIfAbsent(currentPrefix, k -> new ArrayList<>()).add(line);
            }
        }
        return map;
    }

    public static List<String> getFullFileById(String id) throws IOException {
        // Example UniProt REST endpoint for plain text entries
        String apiUrl = "https://rest.uniprot.org/uniprotkb/" + id + ".txt";

        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Accept", "text/plain");

        int responseCode = connection.getResponseCode();
        if (responseCode == 404) {
            return new ArrayList<>();
        } else if (responseCode != 200) {
            throw new IOException("Failed to fetch entry for ID: " + id +
                    ", HTTP response code: " + responseCode);
        }

        List<String> result = new ArrayList<>();
        try (BufferedReader in = new BufferedReader(
                new InputStreamReader(connection.getInputStream()))) {
            String line;
            while ((line = in.readLine()) != null) {
                result.add(line);
            }
        }
        connection.disconnect();

        return result;
    }
}

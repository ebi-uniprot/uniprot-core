package org.uniprot.core.flatfile.parser.impl;

import static org.uniprot.core.uniprot.evidence.impl.EvidenceHelper.parseEvidenceLine;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.uniprot.core.cv.disease.DiseaseFileReader;
import org.uniprot.core.cv.keyword.KeywordCategory;
import org.uniprot.core.cv.keyword.KeywordFileReader;
import org.uniprot.core.cv.subcell.SubcellularLocationFileReader;
import org.uniprot.core.flatfile.parser.SupportingDataMap;
import org.uniprot.core.uniprot.evidence.Evidence;
import org.uniprot.core.util.Pair;
import org.uniprot.core.util.Utils;

/**
 * This class contains all the external supporting data map required to parse from flat file to
 * Entry object.
 *
 * @author lgonzales
 */
public class SupportingDataMapImpl implements SupportingDataMap {

    private static final Logger LOGGER = LoggerFactory.getLogger(SupportingDataMapImpl.class);

    private Map<String, Pair<String, KeywordCategory>> keywordMap = new HashMap<>();
    private Map<String, String> diseaseMap = new HashMap<>();;
    private Map<String, Map<String, List<Evidence>>> goEvidencesMap = new HashMap<>();;
    private Map<String, String> subcellularLocationMap = new HashMap<>();;

    public SupportingDataMapImpl() {
        this(null, null, null, null);
    }

    public SupportingDataMapImpl(
            String keywordFile,
            String diseaseFile,
            String goEvidencesMap,
            String subcellularLocationFile) {
        loadKeywordMap(keywordFile);
        loadDiseaseMap(diseaseFile);
        loadGoEvidenceMap(goEvidencesMap);
        loadSubcellularLocationMap(subcellularLocationFile);
    }

    private void loadSubcellularLocationMap(String subcellularLocationFile) {
        if (Utils.notNullOrEmpty(subcellularLocationFile)) {
            subcellularLocationMap.putAll(
                    new SubcellularLocationFileReader()
                            .parseFileToAccessionMap(subcellularLocationFile));
            LOGGER.info("Loaded " + subcellularLocationMap.size() + " Subcellular Location Map");
        } else {
            LOGGER.warn("Subcellular Location File was not loaded");
        }
    }

    private void loadKeywordMap(String keywordFile) {
        if (Utils.notNullOrEmpty(keywordFile)) {
            keywordMap.putAll(new KeywordFileReader().parseFileToAccessionMap(keywordFile));
            LOGGER.info("Loaded " + keywordMap.size() + " keyword Map");
        } else {
            LOGGER.warn("Subcellular Location File was not loaded");
        }
    }

    private void loadDiseaseMap(String diseaseFile) {
        if (Utils.notNullOrEmpty(diseaseFile)) {
            diseaseMap.putAll(new DiseaseFileReader().parseFileToAccessionMap(diseaseFile));
            LOGGER.info("Loaded " + diseaseMap.size() + " disease Map");
        } else {
            LOGGER.warn("diseaseFile path must not be null or empty");
        }
    }

    private void loadGoEvidenceMap(String goPubmedFile) {
        if (Utils.notNullOrEmpty(goPubmedFile)) {
            try (BufferedReader br =
                    Files.newBufferedReader(Paths.get(goPubmedFile), StandardCharsets.UTF_8)) {
                for (String line = null; (line = br.readLine()) != null; ) {
                    String[] splitedLine = line.split("\t");
                    if (splitedLine.length >= 7) {
                        String accession = splitedLine[0];
                        String goId = splitedLine[1];
                        String evidenceValue = splitedLine[6].replace("PMID", "ECO:0000269|PubMed");
                        Evidence evidence = parseEvidenceLine(evidenceValue);
                        addEvidence(accession, goId, evidence);
                    } else {
                        LOGGER.warn("unable to parse line: '" + line + "' at file " + goPubmedFile);
                    }
                }
            } catch (IOException e) {
                LOGGER.warn("Error while loading Go pubmed file file on path: " + goPubmedFile, e);
            }
            LOGGER.info("Loaded " + goEvidencesMap.size() + " goEvidences Map");
        } else {
            LOGGER.warn("Go pubmed file is not defined");
        }
    }

    private void addEvidence(String accession, String goTerm, Evidence evidence) {
        if (goEvidencesMap.containsKey(accession)) {
            Map<String, List<Evidence>> accessionTerms = goEvidencesMap.get(accession);
            if (accessionTerms.containsKey(goTerm)) {
                List<Evidence> evidenceIds = accessionTerms.get(goTerm);
                evidenceIds.add(evidence);
            } else {
                List<Evidence> evidenceIds = new ArrayList<>();
                evidenceIds.add(evidence);
                accessionTerms.put(goTerm, evidenceIds);
            }
        } else {
            List<Evidence> evidenceIds = new ArrayList<>();
            evidenceIds.add(evidence);

            Map<String, List<Evidence>> term = new HashMap<>();
            term.put(goTerm, evidenceIds);

            goEvidencesMap.put(accession, term);
        }
    }

    @Override
    public Map<String, Pair<String, KeywordCategory>> getKeywordMap() {
        return keywordMap;
    }

    @Override
    public Map<String, String> getDiseaseMap() {
        return diseaseMap;
    }

    @Override
    public Map<String, Map<String, List<Evidence>>> getGoEvidencesMap() {
        return goEvidencesMap;
    }

    @Override
    public Map<String, String> getSubcellularLocationMap() {
        return subcellularLocationMap;
    }
}

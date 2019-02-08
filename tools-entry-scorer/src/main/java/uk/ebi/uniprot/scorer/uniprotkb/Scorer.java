package uk.ebi.uniprot.scorer.uniprotkb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.ac.ebi.uniprot.domain.uniprot.UniProtEntry;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.EvidenceType;
import uk.ac.ebi.uniprot.parser.UniProtEntryIterator;
import uk.ac.ebi.uniprot.parser.impl.DefaultUniProtEntryIterator;

import java.io.*;
import java.util.*;

import static uk.ac.ebi.uniprot.common.Utils.nullOrEmpty;

/**
 * Created by IntelliJ IDEA. User: spatient Date: 08-Mar-2010 Time: 14:29:39 To change this template use File | Settings
 * | File Templates.
 */
public class Scorer {

    public static final String BANNER = "OpenWFE ScoreEntry 0.0.1 - simple java entry scorer";

    private static final Logger LOG = LoggerFactory.getLogger(Scorer.class);
    Map<SetScore.Type, SetScore> setScores;

    public Scorer() {
        resetSetScore();
    }

    private void resetSetScore() {
        setScores = new TreeMap<>();
        setScores.put(SetScore.Type.CITATION_SCORE, new SetScore(SetScore.Type.CITATION_SCORE));
        setScores.put(SetScore.Type.COMMENT_SCORE, new SetScore(SetScore.Type.COMMENT_SCORE));
        setScores.put(SetScore.Type.DESCRIPTION_SCORE, new SetScore(SetScore.Type.DESCRIPTION_SCORE));
        setScores.put(SetScore.Type.FEATURE_SCORE, new SetScore(SetScore.Type.FEATURE_SCORE));
        setScores.put(SetScore.Type.GENE_SCORE, new SetScore(SetScore.Type.GENE_SCORE));
        setScores.put(SetScore.Type.KEYWORD_SCORE, new SetScore(SetScore.Type.KEYWORD_SCORE));
        setScores.put(SetScore.Type.XREF_SCORE, new SetScore(SetScore.Type.XREF_SCORE));
        setScores.put(SetScore.Type.GO_SCORE, new SetScore(SetScore.Type.GO_SCORE));
        setScores.put(SetScore.Type.TOTAL_SCORE, new SetScore(SetScore.Type.TOTAL_SCORE));
    }

    public static void main(String[] args) throws IOException {

        OutputStream out = null;
        List<EvidenceType> evidenceTypes = null;
        ScoreConfigure configure = ScoreConfigureImpl.fromCommandLine(args);
        if (!configure.validate()) {
            LOG.error(configure.getUsage());
            System.exit(1);
        }

        String inputFilePath = configure.getInputFile();

        if (!nullOrEmpty(configure.getOutputFile())) {
            out = new FileOutputStream(configure.getOutputFile());
        } else {
            out = System.out;
        }

        LOG.info("InputFile: {}", inputFilePath);

        evidenceTypes = convertEvidenceTypes(configure.getEvidences());
        UniProtEntryIterator entryIterator = new DefaultUniProtEntryIterator(16, 10000, 50000);
        entryIterator.setInput(inputFilePath, null, null, null);

        Scorer scorer = new Scorer();
        scorer.score(entryIterator, out, evidenceTypes);

        if (out != null)
            out.close();

    }

    private static List<EvidenceType> convertEvidenceTypes(List<String> evidences) {
        if ((evidences == null) || (evidences.isEmpty())) {
            return null;
        }
        List<EvidenceType> evidenceTypes = new ArrayList<>();
        for (String evidence : evidences) {
            if ("AA".equals(evidence)) {
                evidenceTypes.addAll(getAllAAEvidenceTypes());
            }
            try {
                EvidenceType type = new EvidenceType(evidence);
                evidenceTypes.add(type);
            } catch (Exception e) {
                LOG.error("Swallowed exception", e);
            }
        }
        return evidenceTypes;
    }

    private static List<EvidenceType> getAllAAEvidenceTypes() {
        List<EvidenceType> evidenceTypes = new ArrayList<>();
        evidenceTypes.add(new EvidenceType("SAM"));
        evidenceTypes.add(new EvidenceType("PIRNR"));
        evidenceTypes.add(new EvidenceType("PIRSR"));
        evidenceTypes.add(new EvidenceType("RULEBASE"));
        evidenceTypes.add(new EvidenceType("SAAS"));
        evidenceTypes.add(new EvidenceType("UniRule"));
        evidenceTypes.add(new EvidenceType("HAMAP_RULE"));
        evidenceTypes.add(new EvidenceType("PROSITE_PRORULE"));
        evidenceTypes.add(new EvidenceType("PROSITE"));
        evidenceTypes.add(new EvidenceType("SMART"));
        evidenceTypes.add(new EvidenceType("PFAM"));
        return evidenceTypes;
    }

    private void addScore(EntryScore score) {
        setScores.get(SetScore.Type.CITATION_SCORE).addScore(score.citiationScore);
        setScores.get(SetScore.Type.COMMENT_SCORE).addScore(score.commentScore);
        setScores.get(SetScore.Type.DESCRIPTION_SCORE).addScore(score.descriptionScore);
        setScores.get(SetScore.Type.FEATURE_SCORE).addScore(score.featureScore);
        setScores.get(SetScore.Type.GENE_SCORE).addScore(score.geneScore);
        setScores.get(SetScore.Type.KEYWORD_SCORE).addScore(score.keywordScore);
        setScores.get(SetScore.Type.XREF_SCORE).addScore(score.xrefScore);
        setScores.get(SetScore.Type.GO_SCORE).addScore(score.goScore);
        setScores.get(SetScore.Type.TOTAL_SCORE).addScore(score.totalScore);
    }

    public SetScore score(Iterator<UniProtEntry> is, OutputStream out, List<EvidenceType> evidenceTypes)
            throws IOException {

        int counter = 0;

        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out))) {
            writer.write("accession, description, gene, comment, xref, goxref,  keyword, feature, citation, total");
            writer.newLine();

            while (is.hasNext()) {
                UniProtEntry entry = is.next();
                LOG.debug("****************************************************************");
                EntryScore scored = new UniProtEntryScored(entry, evidenceTypes).getEntryScore();
                addScore(scored);
                writer.write(scored.toString());
                writer.newLine();

                if (++counter % 100 == 0) {
                    writer.flush();
                    LOG.info("{} entries processed", counter);
                }
                LOG.debug("{} score is {}", scored.accession, scored.totalScore);
            }
            writer.newLine();
            writer.newLine();
            writer.write("type,count, sum, mean, std, max, min");
            writer.newLine();
            for (SetScore setScore : setScores.values()) {
                writer.write(setScore.toString());
                writer.newLine();
            }

            writer.flush();
            return setScores.get(SetScore.Type.TOTAL_SCORE);
        }
    }
}

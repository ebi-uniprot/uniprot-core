package org.uniprot.core.scorer.uniprotkb;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.uniprot.core.uniprot.evidence.EvidenceDatabase;

/**
 * Created by IntelliJ IDEA. User: spatient Date: 08-Mar-2010 Time: 14:29:39 To change this template
 * use File | Settings | File Templates.
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
        setScores.put(
                SetScore.Type.DESCRIPTION_SCORE, new SetScore(SetScore.Type.DESCRIPTION_SCORE));
        setScores.put(SetScore.Type.FEATURE_SCORE, new SetScore(SetScore.Type.FEATURE_SCORE));
        setScores.put(SetScore.Type.GENE_SCORE, new SetScore(SetScore.Type.GENE_SCORE));
        setScores.put(SetScore.Type.KEYWORD_SCORE, new SetScore(SetScore.Type.KEYWORD_SCORE));
        setScores.put(SetScore.Type.XREF_SCORE, new SetScore(SetScore.Type.XREF_SCORE));
        setScores.put(SetScore.Type.GO_SCORE, new SetScore(SetScore.Type.GO_SCORE));
        setScores.put(SetScore.Type.TOTAL_SCORE, new SetScore(SetScore.Type.TOTAL_SCORE));
    }

    //    public static void main(String[] args) throws IOException {
    //
    //        OutputStream out = null;
    //        List<EvidenceDatabase> evidenceDatabases = null;
    //        ScoreConfigure configure = ScoreConfigureImpl.fromCommandLine(args);
    //        if (!configure.validate()) {
    //            LOG.error(configure.getUsage());
    //            System.exit(1);
    //        }
    //
    //        String inputFilePath = configure.getInputFile();
    //
    //        if (!nullOrEmpty(configure.getOutputFile())) {
    //            out = new FileOutputStream(configure.getOutputFile());
    //        } else {
    //            out = System.out;
    //        }
    //
    //        LOG.info("InputFile: {}", inputFilePath);
    //
    //        evidenceDatabases = convertEvidenceTypes(configure.getEvidences());
    //        UniProtEntryIterator entryIterator = new DefaultUniProtEntryIterator(16, 10000,
    // 50000);
    //        entryIterator.setInput(inputFilePath, null, null, null);
    //
    //        Scorer scorer = new Scorer();
    //        scorer.score(entryIterator, out, evidenceDatabases);
    //
    //        if (out != null)
    //            out.close();
    //
    //    }

    private static List<EvidenceDatabase> convertEvidenceTypes(List<String> evidences) {
        if ((evidences == null) || (evidences.isEmpty())) {
            return null;
        }
        List<EvidenceDatabase> evidenceDatabases = new ArrayList<>();
        for (String evidence : evidences) {
            if ("AA".equals(evidence)) {
                evidenceDatabases.addAll(getAllAAEvidenceTypes());
            }
            try {
                EvidenceDatabase type = new EvidenceDatabase(evidence);
                evidenceDatabases.add(type);
            } catch (Exception e) {
                LOG.error("Swallowed exception", e);
            }
        }
        return evidenceDatabases;
    }

    private static List<EvidenceDatabase> getAllAAEvidenceTypes() {
        List<EvidenceDatabase> evidenceDatabases = new ArrayList<>();
        evidenceDatabases.add(new EvidenceDatabase("SAM"));
        evidenceDatabases.add(new EvidenceDatabase("PIRNR"));
        evidenceDatabases.add(new EvidenceDatabase("PIRSR"));
        evidenceDatabases.add(new EvidenceDatabase("RULEBASE"));
        evidenceDatabases.add(new EvidenceDatabase("SAAS"));
        evidenceDatabases.add(new EvidenceDatabase("UniRule"));
        evidenceDatabases.add(new EvidenceDatabase("HAMAP_RULE"));
        evidenceDatabases.add(new EvidenceDatabase("PROSITE_PRORULE"));
        evidenceDatabases.add(new EvidenceDatabase("PROSITE"));
        evidenceDatabases.add(new EvidenceDatabase("SMART"));
        evidenceDatabases.add(new EvidenceDatabase("PFAM"));
        return evidenceDatabases;
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

    //    public SetScore score(Iterator<UniProtEntry> is, OutputStream out, List<EvidenceDatabase>
    // evidenceDatabases)
    //            throws IOException {
    //
    //        int counter = 0;
    //
    //        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out))) {
    //            writer.write("accession, description, gene, comment, xref, goxref,  keyword,
    // feature, citation, total");
    //            writer.newLine();
    //
    //            while (is.hasNext()) {
    //                UniProtEntry entry = is.next();
    //                LOG.debug("****************************************************************");
    //                EntryScore scored = new UniProtEntryScored(entry,
    // evidenceDatabases).getEntryScore();
    //                addScore(scored);
    //                writer.write(scored.toString());
    //                writer.newLine();
    //
    //                if (++counter % 100 == 0) {
    //                    writer.flush();
    //                    LOG.info("{} entries processed", counter);
    //                }
    //                LOG.debug("{} score is {}", scored.accession, scored.totalScore);
    //            }
    //            writer.newLine();
    //            writer.newLine();
    //            writer.write("type,count, sum, mean, std, max, min");
    //            writer.newLine();
    //            for (SetScore setScore : setScores.values()) {
    //                writer.write(setScore.toString());
    //                writer.newLine();
    //            }
    //
    //            writer.flush();
    //            return setScores.get(SetScore.Type.TOTAL_SCORE);
    //        }
    //    }
}

package org.uniprot.core.scorer.uniprotkb;

import com.google.common.base.Strings;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.uniprot.core.flatfile.parser.impl.DefaultUniProtEntryIterator;
import org.uniprot.core.uniprotkb.UniProtKBEntry;
import org.uniprot.core.uniprotkb.evidence.EvidenceDatabase;

import java.io.*;
import java.time.Duration;
import java.time.LocalTime;
import java.util.*;

/** based on https://swissprot.isb-sib.ch/wiki/display/sdu/Annotation+Scores+Before+Evidences */
public class UniProtEntryScorer {

    enum state {
        CREATED,
        STARTED,
        FINISHED
    }

    public static final String BANNER = "OpenWFE ScoreEntry 0.0.1 - simple java entry scorer";

    private static final Logger LOG = LoggerFactory.getLogger(UniProtEntryScorer.class);
    Map<SetScore.Type, SetScore> setScores;
    BufferedWriter writer;
    state itState = state.CREATED;

    boolean keepSetScores = false;
    boolean withTaxId = false;
    private final List<EvidenceDatabase> evidenceDatabases;

    public UniProtEntryScorer(OutputStream out) {
        this(out, true);
    }

    public UniProtEntryScorer(OutputStream out, boolean keepSetScores) {
        this(out, keepSetScores, null);
    }

    public UniProtEntryScorer(
            OutputStream out, boolean keepSetScores, List<EvidenceDatabase> evidenceDatabases) {
        withTaxId = false;
        this.keepSetScores = keepSetScores;
        this.evidenceDatabases = evidenceDatabases;
        init();
        writer = new BufferedWriter(new OutputStreamWriter(out));
    }

    public UniProtEntryScorer(Writer out, boolean keepSetScores) {
        this(out, keepSetScores, null);
    }

    public UniProtEntryScorer(
            Writer out, boolean keepSetScores, List<EvidenceDatabase> evidenceDatabases) {

        this.keepSetScores = keepSetScores;
        withTaxId = false;
        this.evidenceDatabases = evidenceDatabases;
        init();
        writer = new BufferedWriter(out);
    }

    private void init() {
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

    public void setWithTaxId(boolean withTaxId) {
        this.withTaxId = withTaxId;
    }

    /**
     * Prints to the standard output how the GetEntries class should be run from the command line.
     */
    public static void usage() {
        final String cmd = "java " + UniProtEntryScorer.class.getName();

        System.out.println();
        System.out.println(BANNER);
        System.out.println();
        System.out.println("USAGE :");
        System.out.println();
        System.out.print(cmd);
        System.out.println(" [-f FILE | -u URL | -db DB]");
        System.out.println();
        System.out.println("Scorer is a java entry scorer.");
        System.out.println();
        System.out.println("  -v : verbose");
        System.out.println("  -h : prints this usage and exits");
        System.out.println();
    }

    public static void main(String[] args) throws IOException {
        ScoreConfigure configure = ScoreConfigureImpl.fromCommandLine(args);
        if (!configure.validate()) {
            System.out.println(configure.getUsage());
            System.exit(1);
        }
        LocalTime start = LocalTime.now();
        OutputStream out = null;
        try {
            String fileName = configure.getInputFile();
            String keywordFile = configure.getKeywordFile();
            String diseaseFile = configure.getDiseaseFile();
            String goFile = configure.getGOFile();
            String subcellFile = configure.getSubcellLocationFile();

            if (!Strings.isNullOrEmpty(configure.getOutputFile())) {
                out = new FileOutputStream(configure.getOutputFile());
            } else {
                out = System.out;
            }

            LOG.info("InputFile: {}", configure.getInputFile());
            if (!Strings.isNullOrEmpty(configure.getOutputFile()))
                LOG.info("OutputFile: {}", configure.getOutputFile());

            DefaultUniProtEntryIterator newEntryIterator =
                    new DefaultUniProtEntryIterator(8, 10000, 50000);
            newEntryIterator.setInput(fileName, keywordFile, diseaseFile, goFile, subcellFile);
            List<EvidenceDatabase> evidenceDatabases =
                    convertEvidenceTypes(configure.getEvidences());
            UniProtEntryScorer scorer = new UniProtEntryScorer(out, true, evidenceDatabases);
            scorer.setWithTaxId(true);
            scorer.startUp();
            scorer.scoreEntries(newEntryIterator);
            scorer.shutDown();

        } finally {
            if (out != null) out.close();
        }
        LocalTime end = LocalTime.now();
        LOG.info("Total process time: {}", Duration.between(start, end));
    }

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

            }
        }
        return evidenceDatabases;
    }

    private static List<EvidenceDatabase> getAllAAEvidenceTypes() {
        List<EvidenceDatabase> evidenceDatabases = new ArrayList<>();
        evidenceDatabases.add(new EvidenceDatabase("SAM"));
        evidenceDatabases.add(new EvidenceDatabase("PIRNR"));
        evidenceDatabases.add(new EvidenceDatabase("PIRSR"));
        evidenceDatabases.add(new EvidenceDatabase("RuleBase"));
        evidenceDatabases.add(new EvidenceDatabase("SAAS"));
        evidenceDatabases.add(new EvidenceDatabase("UniRule"));
        evidenceDatabases.add(new EvidenceDatabase("HAMAP-Rule"));
        evidenceDatabases.add(new EvidenceDatabase("PROSITE-ProRule"));
        evidenceDatabases.add(new EvidenceDatabase("PROSITE"));
        evidenceDatabases.add(new EvidenceDatabase("SMART"));
        evidenceDatabases.add(new EvidenceDatabase("Pfam"));
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

    public UniProtEntryScorer startUp() throws IOException {
        if (itState != state.CREATED)
            throw new IllegalStateException("You can only start a new UniProtKBEntry Scorer");
        if (withTaxId)
            writer.write(
                    "accession, taxonomy, description, gene, comment, xref, goxref, keyword ,"
                        + " feature, citation, total");
        else
            writer.write(
                    "accession, description, gene, comment, xref, goxref, keyword , feature,"
                        + " citation, total");
        writer.newLine();
        itState = state.STARTED;
        return this;
    }

    public UniProtEntryScorer shutDown() throws IOException {
        writer.flush();
        if (itState != state.STARTED)
            throw new IllegalStateException("You can only shutdowm a started UniProt Entry Scorer");

        writer.newLine();
        writer.newLine();
        writer.write("type,count, sum, mean, std, max, min");
        writer.newLine();
        for (SetScore setScore : setScores.values()) {
            writer.write(setScore.toString());
            writer.newLine();
        }
        writer.flush();
        itState = state.FINISHED;
        return this;
    }

    public void scoreEntries(Iterator<UniProtKBEntry> is) throws IOException {
        if (itState != state.STARTED)
            throw new IllegalStateException("You need to start the scorer before scoring entries");
        int counter = 0;

        while (is.hasNext()) {
            try {
                UniProtKBEntry entry = is.next();
                if (entry == null) continue;
                EntryScore scored =
                        new UniProtEntryScored(entry, evidenceDatabases).getEntryScore();
                if (keepSetScores) addScore(scored);
                if (withTaxId) {
                    writer.write(scored.toStringWithTaxId());
                } else writer.write(scored.toString());
                writer.newLine();
                if (entry != null) {
                    counter++;
                }
                if (counter % 200 == 0) {
                    writer.flush();
                    LOG.info("{} entries processed", counter);
                }
            } catch (Exception ce) {
                LOG.error("Exiting now: unable to get score entry because ", ce);
                return;
            }
        }
        writer.flush();
    }

    public synchronized void scoreEntry(UniProtKBEntry entry) throws IOException {
        if (itState != state.STARTED)
            throw new IllegalStateException("You need to start the scorer before scoring entries");
        UniProtEntryScored entryScored = new UniProtEntryScored(entry);
        EntryScore scored = entryScored.getEntryScore();
        if (keepSetScores) addScore(scored);
        String data = "";
        if (this.withTaxId) data = scored.toStringWithTaxId() + "\n";
        else data = scored.toString() + "\n";

        writer.write(data);
    }

    public SetScore getTotalScore() {
        if (itState != state.FINISHED)
            throw new IllegalStateException(
                    "You need to shutdown the scorer before getting the total score");
        return setScores.get(SetScore.Type.TOTAL_SCORE);
    }
}

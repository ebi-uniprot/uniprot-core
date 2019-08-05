package org.uniprot.core.scorer.uniprotkb;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;

import static org.uniprot.core.util.Utils.nullOrEmpty;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ScoreConfigureImpl implements ScoreConfigure {
    @Parameter(names = "-e", description = "a list of evidence types")
    private List<String> evidences = new ArrayList<>();

    @Parameter(required = true, names = "-f", description = "input entries file")
    private String inputFile = null;

    @Parameter(required = true, names = "-k", description = "input keyword file file")
    private String keywordFile = null;

    @Parameter(required = true, names = "-d", description = "input disease file file")
    private String diseaseFile = null;

    @Parameter(required = true, names = "-g", description = "input go term file")
    private String goFile = null;
    
    @Parameter(required = true, names = "-s", description = "input subcellular location file file")
    private String subcellLocationFile = null;

    @Parameter(names = "-o", description = "output file")
    private String outputfile = null;

    @Parameter(names = "--help", help = true)
    private boolean help;
    private JCommander jCommander;

    private ScoreConfigureImpl() {

    }

    public static final ScoreConfigure fromCommandLine(String[] args) {
        ScoreConfigureImpl configurator = new ScoreConfigureImpl();
        configurator.jCommander = JCommander.newBuilder()
                .addObject(configurator).build();
        configurator.jCommander.parse(args);

        return configurator;
    }

    @Override
    public String getUsage() {
        StringBuilder out = new StringBuilder();
        jCommander.usage(out);

        return out.toString();
    }

    @Override
    public String getKeywordFile() {
        return keywordFile;
    }

    @Override
    public String getDiseaseFile() {
        return diseaseFile;
    }

    @Override
    public String getGOFile() {
        return goFile;
    }

    @Override
    public String getInputFile() {
        return this.inputFile;
    }

    @Override
    public String getOutputFile() {
        return this.outputfile;
    }

    @Override
    public List<String> getEvidences() {
        return this.evidences;
    }

    @Override
    public boolean validate() {
        if (nullOrEmpty(this.getInputFile()))
            return false;
        File file = new File(this.getInputFile());
        return (file.isFile());
    }

	@Override
	public String getSubcellLocationFile() {
		return this.subcellLocationFile;
	}
}

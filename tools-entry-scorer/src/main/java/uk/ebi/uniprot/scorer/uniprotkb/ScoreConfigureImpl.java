package uk.ebi.uniprot.scorer.uniprotkb;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static uk.ac.ebi.uniprot.common.Utils.nullOrEmpty;

public class ScoreConfigureImpl implements ScoreConfigure {
    @Parameter(names = "-e", description = "a list of evidence types")
    private List<String> evidences = new ArrayList<>();

    @Parameter(names = "-f", description = "input entries file")
    private String inputFile = null;

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
}

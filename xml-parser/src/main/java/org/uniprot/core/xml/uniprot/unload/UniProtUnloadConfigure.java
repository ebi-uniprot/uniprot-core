package org.uniprot.core.xml.uniprot.unload;

import java.util.List;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.converters.CommaParameterSplitter;
import com.google.common.base.Strings;

public class UniProtUnloadConfigure {
    @Parameter(
            names = "-if",
            splitter = CommaParameterSplitter.class,
            description = "input flat file (s), comma separated")
    private List<String> inputFiles;

    @Parameter(names = "-of", description = "output xml file")
    private String outputFile;

    @Parameter(names = "-hdf", description = "UniProt human disease file, humdisease.txt")
    private String humdiseaseFile;

    @Parameter(names = "-kwf", description = "UniProt keyword file, keywlist.txt")
    private String keywordFile;

    @Parameter(names = "-n", description = "number of threads, default size is 8")
    private int nthreads = 8;

    @Parameter(names = "-nohf", description = "not include Xml header and footer")
    private boolean noIncludeHeaderFooter;

    @Parameter(names = "-kmf", description = "keep intermediate xml files")
    private boolean keepIntermediate;

    @Parameter(names = "-imf", description = "intermediate xml file prefix, default is: uniprot")
    private String intermFile = "uniprot";

    @Parameter(names = "-mr", description = "metrics report interval in seconds")
    private int interval = 60 * 5;

    private JCommander jCommander;

    public static final UniProtUnloadConfigure fromCommandLine(String[] args) {
        UniProtUnloadConfigure configurator = new UniProtUnloadConfigure();
        configurator.jCommander = new JCommander(configurator, args);
        return configurator;
    }

    public boolean isValid() {
        if ((inputFiles == null)
                || inputFiles.isEmpty()
                || Strings.isNullOrEmpty(outputFile)
                || Strings.isNullOrEmpty(humdiseaseFile)
                || Strings.isNullOrEmpty(keywordFile)) return false;
        return true;
    }

    public String getUsage() {
        StringBuilder out = new StringBuilder();
        jCommander.usage(out);
        return out.toString();
    }

    public String getHumdiseaseFilePath() {
        return this.humdiseaseFile;
    }

    public String getKeywordFilePath() {
        return this.keywordFile;
    }

    public int nThreads() {
        return this.nthreads;
    }

    public boolean includeHeaderFooder() {
        return !this.noIncludeHeaderFooter;
    }

    public List<String> getUniProtInputFFilePath() {
        return this.inputFiles;
    }

    public String getUniProtXmlOutputFilePath() {
        return this.outputFile;
    }

    public int metricsReportInterval() {
        return this.interval;
    }

    public boolean keepIntermFiles() {
        return keepIntermediate;
    }

    public String getIntermFilePrefix() {
        return this.intermFile;
    }
}

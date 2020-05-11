package org.uniprot.core.xml.uniprot.unload;

import com.codahale.metrics.Counter;
import com.codahale.metrics.Slf4jReporter;
import com.codahale.metrics.Timer;

import org.uniprot.core.xml.XmlBuildStats;

import java.util.concurrent.TimeUnit;

public class UniProtXmlBuildStats implements XmlBuildStats {
    private static final String REPORT_TITLE = "UniProt XML Build Report";
    private static final long DEFAULT_REPORT_TIME = 60L * 5L;
    private final Counter failedCounter;
    private final Counter succeededCounter;
    private final Counter ffCounter;
    private final Timer entryParseTimer;
    private final Timer xmlWriteTimer;
    private final Timer ffReaderTimer;
    private final Slf4jReporter reporter;
    private String failedEntryFile;
    private String outputFile;
    private final String title;

    public UniProtXmlBuildStats() {
        this(REPORT_TITLE, DEFAULT_REPORT_TIME);
    }

    public UniProtXmlBuildStats(long reportTimeInSecond) {
        this(REPORT_TITLE, reportTimeInSecond);
    }

    public UniProtXmlBuildStats(String title, long reportTimeInSecond) {
        this.title = title;
        Slf4jReporter localReporter =
                Slf4jReporter.forRegistry(MetricsUtil.getMetricRegistryInstance()).build();
        localReporter.start(reportTimeInSecond, TimeUnit.SECONDS);
        this.reporter = localReporter;

        this.failedCounter = MetricsUtil.getMetricRegistryInstance().counter("entry-failed");
        this.succeededCounter = MetricsUtil.getMetricRegistryInstance().counter("entry-succeeded");
        this.ffCounter = MetricsUtil.getMetricRegistryInstance().counter("flatfile_entry");
        this.ffReaderTimer = MetricsUtil.getMetricRegistryInstance().timer("flatfile-read-time");
        this.entryParseTimer =
                MetricsUtil.getMetricRegistryInstance().timer("entry-parse-convert-time");
        this.xmlWriteTimer = MetricsUtil.getMetricRegistryInstance().timer("xml-write-time");
    }

    @Override
    public String getOuputFile() {
        return this.outputFile;
    }

    public void setOutputFile(String outputFile) {
        this.outputFile = outputFile;
    }

    @Override
    public String getFailedEntryFile() {
        return this.failedEntryFile;
    }

    public void setFailedEntryFile(String failedEntryFile) {
        this.failedEntryFile = failedEntryFile;
    }

    @Override
    public long getNumberOfEntrySucceeded() {
        return this.succeededCounter.getCount();
    }

    @Override
    public long getNumberOfEntryFailed() {
        return this.getFailedCounter().getCount();
    }

    @Override
    public String getReport() {
        StringBuilder sb = new StringBuilder();
        sb.append(title).append("\n");
        sb.append("Total number of flatfile entries: ")
                .append(this.getFlatfileEntryCounter().getCount())
                .append("\n");
        sb.append("Entries have successfully been built: ")
                .append(this.getNumberOfEntrySucceeded())
                .append("\n");
        sb.append("Entries failed: ").append(this.getNumberOfEntryFailed()).append("\n");
        sb.append("The built XML file: ").append(this.getOuputFile()).append("\n");
        if (this.getNumberOfEntryFailed() > 0) {
            sb.append("The failed entry file: ").append(this.getFailedEntryFile()).append("\n");
        } else {
            sb.append("The XML build succeeded!").append("\n");
        }
        return sb.toString();
    }

    public void metricsReport() {
        reporter.report();
    }

    public Counter getFailedCounter() {
        return failedCounter;
    }

    public Counter getSucceededCounter() {
        return succeededCounter;
    }

    public Counter getFlatfileEntryCounter() {
        return this.ffCounter;
    }

    public Timer getEntryParseTimer() {
        return entryParseTimer;
    }

    public Timer getXmlWriteTimer() {
        return xmlWriteTimer;
    }

    public Timer getFfReadTimer() {
        return ffReaderTimer;
    }
}

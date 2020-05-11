package org.uniprot.core.xml.uniprot.unload;

import com.codahale.metrics.Timer;
import com.sun.xml.bind.marshaller.DataWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.uniprot.core.flatfile.parser.impl.EntryBufferedReader2;
import org.uniprot.core.xml.DefaultXmlFileMerger;
import org.uniprot.core.xml.XmlBuildStats;
import org.uniprot.core.xml.XmlBuilder;
import org.uniprot.core.xml.XmlFileMerger;
import org.uniprot.core.xml.jaxb.uniprot.Entry;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

public class UniProtFFToXmlBuilder implements XmlBuilder {
    private static final Logger LOGGER = LoggerFactory.getLogger(UniProtFFToXmlBuilder.class);
    private static final String FILE_CANNOT_BE_DELETED = "File: {} cannot be deleted";
    private final String keywordFile;
    private final String diseaseFile;
    private final int nthread;
    private String tempFilePref;
    private final UniProtXmlBuildStats xmlBuildStats;
    private static final String XML_FILE_PREX_RETRY = "_retry";
    private static final String FAILED_ENTRY_FILE_PREV = "failed_entries";
    private static final int MAX_RETRY = 3;
    private static final String XML_FILEEXT = ".xml";
    private static final String TARGET_PACKAGE = "uk.ac.ebi.uniprot.xml.jaxb.uniprot";
    private final int BATCH_SIZE = 20;

    private String failedFileName;

    public UniProtFFToXmlBuilder(String keywordFile, String diseaseFile, int nthread) {
        this.keywordFile = keywordFile;
        this.diseaseFile = diseaseFile;
        this.nthread = nthread;
        tempFilePref = "uniprot" + LocalTime.now().toNanoOfDay() / 1000000;
        xmlBuildStats = new UniProtXmlBuildStats(60 * 5);
    }

    @Override
    public XmlBuildStats build(List<String> dataFiles, String xmlFile) {
        LOGGER.info("UniProt Xml build start... ");

        String failedEntryFile = getFailedEntryFileName(0);
        List<String> outputFiles = new ArrayList<>();
        int inc = 0;
        for (String dataFile : dataFiles) {
            try {
                LOGGER.info("Start building xml from ... {}", dataFile);
                String threadNamePrefix = tempFilePref + "-" + inc++ + "-";
                outputFiles.addAll(
                        multithreadBuild(dataFile, threadNamePrefix, nthread, failedEntryFile));
            } catch (Exception e) {
                LOGGER.error("dataFile: " + dataFile + " failed to build. ", e);
            }
        }
        if (xmlBuildStats.getFailedCounter().getCount() > 0) {
            outputFiles.addAll(retryBuild(failedEntryFile));
        } else {
            File file = new File(failedEntryFile);
            try {
                Files.deleteIfExists(file.toPath());
            } catch (IOException e) {
                LOGGER.warn(FILE_CANNOT_BE_DELETED, failedEntryFile);
            }
        }

        mergeFiles(xmlFile, outputFiles);

        deleteFiles(outputFiles);
        updateStats(xmlFile, this.failedFileName);
        xmlBuildStats.metricsReport();
        return xmlBuildStats;
    }

    private void updateStats(String xmlFile, String failedEntryFile) {
        File file = new File(xmlFile);
        if (file.exists()) {
            xmlBuildStats.setOutputFile(file.getAbsolutePath());
        } else {
            xmlBuildStats.setOutputFile(xmlFile);
        }
        if (failedEntryFile != null) {
            file = new File(failedEntryFile);
            if (file.exists()) {
                xmlBuildStats.setFailedEntryFile(file.getAbsolutePath());
            } else {
                xmlBuildStats.setFailedEntryFile(failedEntryFile);
            }
        }
    }

    private void mergeFiles(String xmlFile, List<String> inputFiles) {
        LOGGER.info("merge files from ... {}", inputFiles);
        LOGGER.info("merge to ... {}", xmlFile);
        LocalTime start = LocalTime.now();
        XmlFileMerger xmFileMerger =
                new DefaultXmlFileMerger(UniProtXmlConstants.HEADER, UniProtXmlConstants.FOOTER);
        try {
            xmFileMerger.mergeFiles(xmlFile, inputFiles);
        } catch (IOException e) {
            throw new UniProtFFToXmlBuildException("failed to merge files", e);
        } finally {
            Duration duration = Duration.between(start, LocalTime.now());
            LOGGER.info("Time for merging file: {} seconds", duration.getSeconds());
        }
    }

    private void deleteFiles(List<String> inputFiles) {
        for (String inputFile : inputFiles) {
            File file = new File(inputFile);
            try {
                Files.deleteIfExists(file.toPath());
            } catch (IOException e) {
                LOGGER.warn(FILE_CANNOT_BE_DELETED, inputFile);
            }
        }
    }

    private String getFailedEntryFileName(int retry) {
        return FAILED_ENTRY_FILE_PREV + LocalTime.now().toSecondOfDay() + +retry + ".txt";
    }

    private List<String> retryBuild(String failedEntryFile) {
        int nretry = 1;
        List<String> outputFiles = new ArrayList<>();
        String dataFile = failedEntryFile;
        do {
            LOGGER.info(
                    "Number of failed Entries: {}", xmlBuildStats.getFailedCounter().getCount());
            LOGGER.info("Retry build failed entries: {}", nretry);
            String newFailedEntryFile = getFailedEntryFileName(nretry);
            String xmlfilePrex = tempFilePref + XML_FILE_PREX_RETRY + nretry;
            outputFiles.addAll(multithreadBuild(dataFile, xmlfilePrex, 1, newFailedEntryFile));
            if (xmlBuildStats.getFailedCounter().getCount() > 0) {
                nretry++;
                deletePreviousFailedEntryFile(dataFile);
                dataFile = newFailedEntryFile;
            } else {
                break;
            }
        } while (nretry >= MAX_RETRY);

        return outputFiles;
    }

    private void deletePreviousFailedEntryFile(String file) {
        try {
            Files.deleteIfExists(Paths.get(file));
        } catch (IOException e) {
            LOGGER.warn(FILE_CANNOT_BE_DELETED, file);
        }
    }

    private Optional<PrintWriter> printWriterFromFile(String file) {
        try {
            return Optional.of(new PrintWriter((new FileWriter(new File(file)))));
        } catch (IOException e) {
            return Optional.empty();
        }
    }

    private PrintWriter printWriterFromOutputStream(OutputStream os) {
        return new PrintWriter(new OutputStreamWriter(os));
    }

    private List<String> multithreadBuild(
            String dataFile, String xmlfilePrev, int nthread, String failedEntryFile) {
        this.failedFileName = failedEntryFile;
        long failed = xmlBuildStats.getFailedCounter().getCount();
        xmlBuildStats.getFailedCounter().dec(failed);
        LimitedQueue<Runnable> queue = new LimitedQueue<>(10000);
        ExecutorService executorService =
                new ThreadPoolExecutor(
                        nthread,
                        32,
                        30,
                        TimeUnit.SECONDS,
                        queue,
                        new NamedThreadFactory(xmlfilePrev));
        EntryBufferedReader2 entryBufferReader2 = null;
        PrintWriter failedEntryWriter =
                printWriterFromFile(failedFileName).orElse(printWriterFromOutputStream(System.out));

        try {
            entryBufferReader2 = new EntryBufferedReader2(dataFile);
        } catch (Exception e) {
            throw new UniProtFFToXmlBuildException("Parsing Flatfile failure.", e);
        }
        int counter = 0;
        do {
            List<String> ffEntries = getNextListFFEntries(entryBufferReader2);
            if (ffEntries.isEmpty()) break;
            this.xmlBuildStats.getFlatfileEntryCounter().inc(ffEntries.size());
            executorService.submit(
                    new UniProtFF2XmlWriter(
                            ffEntries,
                            this.diseaseFile,
                            this.keywordFile,
                            failedEntryWriter,
                            xmlBuildStats));
            counter++;
        } while (true);
        executorService.shutdown();
        try {
            executorService.awaitTermination(30, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        failedEntryWriter.close();
        xmlBuildStats.metricsReport();
        return getxmlFiles(xmlfilePrev, nthread > counter ? counter : nthread);
    }

    private List<String> getxmlFiles(String xmlfilePrev, int nthread) {
        return IntStream.range(1, nthread + 1)
                .mapToObj(val -> (xmlfilePrev + val + XML_FILEEXT))
                .collect(Collectors.toList());
    }

    private List<String> getNextListFFEntries(EntryBufferedReader2 entryBufferReader2) {
        Timer.Context time = this.xmlBuildStats.getFfReadTimer().time();
        List<String> entries = new ArrayList<>(BATCH_SIZE);
        int count = 0;
        do {
            String next = null;
            try {
                next = entryBufferReader2.next();
            } catch (Exception e) {
                LOGGER.debug("Problem iterating over entries", e);
            }
            if (next == null) {
                break;
            } else {
                entries.add(next);
                if (++count >= BATCH_SIZE) {
                    break;
                }
            }
        } while (true);
        time.stop();
        return entries;
    }

    static class UniProtFF2XmlWriter implements Runnable {
        private static final ConcurrentHashMap<String, UniProtFFToXmlConverter> converters =
                new ConcurrentHashMap<>();
        private static final ConcurrentHashMap<String, Marshaller> marshallers =
                new ConcurrentHashMap<>();
        private static final ConcurrentHashMap<String, DataWriter> writers =
                new ConcurrentHashMap<>();

        private final String humdiseaseFilePath;
        private final String keywordFilePath;
        private final List<String> ffEntries;
        private final PrintWriter failedEntryWriter;
        private UniProtXmlBuildStats metrics;

        public UniProtFF2XmlWriter(
                List<String> ffEntries,
                String humdiseaseFilePath,
                String keywordFilePath,
                PrintWriter failedEntryWriter,
                UniProtXmlBuildStats metrics) {
            this.ffEntries = ffEntries;
            this.keywordFilePath = keywordFilePath;
            this.humdiseaseFilePath = humdiseaseFilePath;
            this.failedEntryWriter = failedEntryWriter;
            this.metrics = metrics;
        }

        @Override
        public void run() {
            Thread thread = Thread.currentThread();
            String threadName = thread.getName();
            UniProtFFToXmlConverter converter = getUniprotFFToXmlConverter(threadName);

            DataWriter writer = getWriter(threadName);
            Marshaller marshaller = getMarshaller(threadName);
            for (String ffEntry : ffEntries) {
                Timer.Context time1 = metrics.getEntryParseTimer().time();
                Entry xmlEntry = null;
                try {
                    xmlEntry = converter.apply(ffEntry);
                } catch (Exception e) {
                    metrics.getFailedCounter().inc();
                    writeFailedFFEntry(ffEntry);
                } finally {
                    time1.stop();
                }
                if (xmlEntry != null) {
                    Timer.Context time2 = metrics.getXmlWriteTimer().time();
                    try {
                        marshaller.marshal(xmlEntry, writer);
                        writer.characters("\n");
                        writer.flush();
                        metrics.getSucceededCounter().inc();
                    } catch (Exception e) {
                        LOGGER.warn(
                                "Entry write to XML Failed. {}", xmlEntry.getAccession().get(0));
                        metrics.getFailedCounter().inc();
                        writeFailedFFEntry(ffEntry);
                    } finally {
                        time2.stop();
                    }
                }
            }
        }

        private void writeFailedFFEntry(String ffEntry) {
            synchronized (failedEntryWriter) {
                failedEntryWriter.write(ffEntry);
                failedEntryWriter.flush();
            }
        }

        private DataWriter getWriter(String name) {
            DataWriter writer = writers.get(name);
            if (writer == null) {
                try {
                    writer = getXmlWriter(name + XML_FILEEXT);
                    writers.put(name, writer);

                } catch (Exception e) {
                    throw new UniProtFFToXmlBuildException(e.getMessage());
                }
            }
            return writer;
        }

        public void setWriter(String name, DataWriter writer) {
            writers.put(name, writer);
        }

        private DataWriter getXmlWriter(String filename) throws IOException {
            Writer out = new BufferedWriter(new FileWriter(new File(filename)));
            DataWriter writer = new DataWriter(out, "UTF-8");
            writer.setIndentStep("  ");
            return writer;
        }

        private UniProtFFToXmlConverter getUniprotFFToXmlConverter(String name) {
            return converters.computeIfAbsent(
                    name,
                    n -> {
                        UniProtFFToXmlConverter uniProtFFToXmlConverter =
                                new UniProtFFToXmlConverter(humdiseaseFilePath, keywordFilePath);
                        return converters.put(name, uniProtFFToXmlConverter);
                    });
        }

        private Marshaller getMarshaller(String name) {
            Marshaller marshaller = marshallers.get(name);
            if (marshaller == null) {
                marshaller = initXmlMarshaller();
                marshallers.put(name, marshaller);
            }
            return marshaller;
        }

        private Marshaller initXmlMarshaller() {
            try {
                JAXBContext jaxbContext = JAXBContext.newInstance(TARGET_PACKAGE);
                Marshaller marshaller = jaxbContext.createMarshaller();
                marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
                marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
                return marshaller;
            } catch (Exception e) {
                throw new UniProtFFToXmlBuildException("JAXB initiallation failed", e);
            }
        }
    }

    private static class UniProtFFToXmlBuildException extends RuntimeException {
        /** */
        private static final long serialVersionUID = -2716084900878573322L;

        UniProtFFToXmlBuildException(String message) {
            super(message);
        }

        UniProtFFToXmlBuildException(String message, Throwable throwable) {
            super(message, throwable);
        }
    }
}

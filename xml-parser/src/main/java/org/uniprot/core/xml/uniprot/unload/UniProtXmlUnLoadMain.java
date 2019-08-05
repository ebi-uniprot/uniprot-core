package org.uniprot.core.xml.uniprot.unload;

import java.time.Duration;
import java.time.LocalTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.uniprot.core.xml.XmlBuildStats;



public class UniProtXmlUnLoadMain {
	private static final Logger LOGGER = LoggerFactory.getLogger(UniProtXmlUnLoadMain.class);

    public static void main(String[] args) {
        LocalTime start = LocalTime.now();
        UniProtUnloadConfigure configure = UniProtUnloadConfigure.fromCommandLine(args);
        if (!configure.isValid()) {
            System.out.println(configure.getUsage());
            System.exit(1);
        }
        UniProtFFToXmlBuilder builder = new UniProtFFToXmlBuilder(configure.getKeywordFilePath(), 
        		configure.getHumdiseaseFilePath(), configure.nThreads() );
        try {
            XmlBuildStats stats = builder.build(configure.getUniProtInputFFilePath(),
                    configure.getUniProtXmlOutputFilePath());
            System.out.println(stats.getReport());
            Duration duration = Duration.between(start, LocalTime.now());
            LOGGER.info("Total time: " + duration.getSeconds() + " seconds");
            if (stats.getNumberOfEntryFailed() > 0) {
                System.exit(1);
            }
        } catch (RuntimeException e) {
            LOGGER.error("Executing failure", e);
            System.exit(1);
        }
    }
}

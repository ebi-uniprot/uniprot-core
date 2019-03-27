package uk.ac.ebi.uniprot.xml;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.commons.compress.utils.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.ebi.uniprot.xml.uniprot.unload.UniProtFFToXmlBuilder;


public class DefaultXmlFileMerger implements XmlFileMerger {
    private final String header;
    private final String footer;
    private static final int BUFFER_SIZE = 1024*128;
    private static final Logger LOGGER = LoggerFactory.getLogger(UniProtFFToXmlBuilder.class);
    public DefaultXmlFileMerger(String header, String footer){
        this.header = header;
        this.footer = footer;
    }
    @Override
    public void mergeFiles(String outputFile, List<String> inputFiles) throws IOException {
    	  LOGGER.info("merge file to: " + outputFile);
          try (BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(new File(outputFile), true))) {
              if ((header != null) && (!header.isEmpty())) {
                  ByteArrayInputStream headerStream = new ByteArrayInputStream(header.getBytes());
                  IOUtils.copy(headerStream, out, BUFFER_SIZE);
              }
              for (String inputFile : inputFiles) {
                  LOGGER.info("merge file from: " + inputFile);
                  try (InputStream inputStream = new BufferedInputStream(new FileInputStream(new File(inputFile)))) {
                      IOUtils.copy(inputStream, out, BUFFER_SIZE);
                  } catch (FileNotFoundException ex) {
                      LOGGER.error("File {} does not exist", ex);
                  }
              }
              if ((footer != null) && (!footer.isEmpty())) {
                  ByteArrayInputStream footerStream = new ByteArrayInputStream(footer.getBytes());
                  IOUtils.copy(footerStream, out, BUFFER_SIZE);
              }
          }

    }

}

package org.uniprot.core.xml.uniprot;

import org.uniprot.core.flatfile.writer.FlatfileWriter;
import org.uniprot.core.flatfile.writer.impl.UniProtFlatfileWriter;
import org.uniprot.core.uniprotkb.UniProtKBEntry;
import org.uniprot.core.xml.jaxb.uniprot.Uniprot;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class UniProtKBXMLToFlatFileConverter {

    public static void convertFolder(String inputDirPath, String outputDirPath) {
        try {
            File inputDir = new File(inputDirPath);
            File outputDir = new File(outputDirPath);
            // Setup JAXB context and unmarshaller
            JAXBContext jaxbContext = JAXBContext.newInstance("org.uniprot.core.xml.jaxb.uniprot");
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

            // Converter + Writer
            GoogleUniProtEntryConverter converter = new GoogleUniProtEntryConverter();
            FlatfileWriter<UniProtKBEntry> flatfileWriter = new UniProtFlatfileWriter();

            // Iterate over all XML files in folder
            File[] xmlFiles = inputDir.listFiles((dir, name) -> name.toLowerCase().endsWith(".xml"));
            if (xmlFiles == null) {
                System.err.println("No XML files found in " + inputDirPath);
                return;
            }
            int count = 0;

            for (File xmlFile : xmlFiles) {
                try (InputStream inputStream = new FileInputStream(xmlFile)) {
                    // Parse XML → UniProt object
                    Uniprot xmlEntry = (Uniprot) jaxbUnmarshaller.unmarshal(inputStream);

                    List<UniProtKBEntry> uniProtEntries =
                            xmlEntry.getEntry().stream().map(converter::fromXml).toList();

                    if (uniProtEntries.isEmpty()) {
                        System.err.println("No entries found in " + xmlFile.getName());
                        continue;
                    }

                    // Update and convert to flat file
                    UniProtKBEntry updatedEntry =
                            converter.updateUniProtEntry(xmlEntry.getEntry().get(0), uniProtEntries.get(0));

                    String ffEntry = flatfileWriter.write(updatedEntry, false);

                    // Write to .txt file with same base name
                    String outputFileName = xmlFile.getName().replace(".xml", ".txt");
                    Path outputFile = Paths.get(outputDir.getAbsolutePath(), outputFileName);

                    Files.writeString(outputFile, ffEntry);
                    System.out.println("Converted: " + xmlFile.getName() + " -> " + outputFileName);
                    count++;
                }
            }
            System.out.println("Converted " + count + " uniprot entries.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Example usage
    public static void main(String[] args) {
        String inputFolder = "/Users/sahmad/work/protnlm-data/google/xml";
        String outputFolder = "/Users/sahmad/work/protnlm-data/google/flatfile";
        convertFolder(inputFolder, outputFolder);
    }
}


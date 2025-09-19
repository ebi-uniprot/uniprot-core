package org.uniprot.core.xml.uniprot;

import org.junit.jupiter.api.Test;
import org.uniprot.core.flatfile.writer.FlatfileWriter;
import org.uniprot.core.flatfile.writer.impl.UniProtFlatfileWriter;
import org.uniprot.core.uniprotkb.UniProtKBEntry;
import org.uniprot.core.uniprotkb.impl.UniProtKBEntryBuilder;
import org.uniprot.core.xml.jaxb.uniprot.ObjectFactory;
import org.uniprot.core.xml.jaxb.uniprot.Uniprot;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.InputStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UniProtKBXMLEntryToFFConverterTest {
    @Test
    void testXMLEntryToFF() throws JAXBException {
        String file = "/uniprot/google/A0A6A5BR32_latest.xml";
        InputStream inputStream = GoogleUniProtEntryConverterTest.class.getResourceAsStream(file);

        JAXBContext jaxbContext = JAXBContext.newInstance("org.uniprot.core.xml.jaxb.uniprot");

        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        Uniprot xmlEntry = (Uniprot) jaxbUnmarshaller.unmarshal(inputStream);
        assertNotNull(xmlEntry);

        GoogleUniProtEntryConverter converter = new GoogleUniProtEntryConverter();
        List<UniProtKBEntry> uniProtEntries =
                xmlEntry.getEntry().stream().map(converter::fromXml).toList();
        assertNotNull(uniProtEntries);
        assertEquals(1, uniProtEntries.size());
        UniProtKBEntry uniProtEntry = uniProtEntries.get(0);
        assertNotNull(uniProtEntry);
        // Add missing mandatory fields
        UniProtKBEntry updatedUniProtEntry = converter.updateUniProtEntry(xmlEntry.getEntry().get(0), uniProtEntry);
        FlatfileWriter<UniProtKBEntry> flatfileWriter = new UniProtFlatfileWriter();
        String ffEntry = flatfileWriter.write(updatedUniProtEntry, false);
        assertNotNull(ffEntry);
        System.out.println(ffEntry);
    }
}

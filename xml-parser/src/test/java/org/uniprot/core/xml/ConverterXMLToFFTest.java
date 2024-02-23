package org.uniprot.core.xml;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.InputStream;
import java.time.LocalDate;


import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import org.junit.jupiter.api.Test;
import org.uniprot.core.flatfile.writer.impl.UniProtFlatfileWriter;
import org.uniprot.core.uniprotkb.UniProtKBEntry;
import org.uniprot.core.uniprotkb.impl.EntryAuditBuilder;
import org.uniprot.core.uniprotkb.impl.UniProtKBEntryBuilder;
import org.uniprot.core.xml.jaxb.uniprot.Uniprot;
import org.uniprot.core.xml.uniprot.UniProtEntryConverter;

class ConverterXMLToFFTest {
    //XSD --> https://ftp.uniprot.org/pub/databases/uniprot/current_release/knowledgebase/complete/uniprot.xsd

    @Test
    void testXMLToFF() throws Exception {
        String file = "/google/entry_v4_modified.xml";
        InputStream inputStream = ConverterXMLToFFTest.class.getResourceAsStream(file);
        JAXBContext jaxbContext = JAXBContext.newInstance("org.uniprot.core.xml.jaxb.uniprot");

        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        Uniprot xmlEntry = (Uniprot) jaxbUnmarshaller.unmarshal(inputStream);
        assertNotNull(xmlEntry);

        UniProtEntryConverter converter = new UniProtEntryConverter();
        UniProtKBEntry uniprotEntry =
                xmlEntry.getEntry().stream().map(converter::fromXml).findFirst().orElse(null);
        assertNotNull(uniprotEntry);

        UniProtKBEntry auditedEntry =
                UniProtKBEntryBuilder.from(uniprotEntry)
                        .entryAudit(
                                new EntryAuditBuilder()
                                        .firstPublic(LocalDate.now())
                                        .lastAnnotationUpdate(LocalDate.now())
                                        .lastSequenceUpdate(LocalDate.now())
                                        .build())
                        .build();

        // "XML FILE" --> "FF PARTIAL" (Curator can use to validate)

        String ffResult = UniProtFlatfileWriter.write(auditedEntry);
        assertNotNull(ffResult);
        System.out.println(ffResult);
    }

    protected Marshaller createMarshaller(JAXBContext jaxbContext) {
        try {
            Marshaller contextMarshaller = jaxbContext.createMarshaller();
            contextMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            contextMarshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
            return contextMarshaller;
        } catch (Exception e) {
            throw new RuntimeException("JAXB marshaller creation failed", e);
        }
    }
}

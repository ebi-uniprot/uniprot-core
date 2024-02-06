package org.uniprot.core.xml.writer;

import java.io.IOException;
import java.io.Writer;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;

import org.uniprot.core.xml.XmlWriter;
import org.uniprot.core.xml.XmlWriterException;

/**
 * @author jluo
 * @date: 15 Aug 2019
 */
public class DefaultXmlWriter<T> implements XmlWriter<T> {
    private final Writer writer;
    private Marshaller marshaller;
    private final String jaxbPackage;

    public DefaultXmlWriter(Writer writer, String jaxbPackage) {
        this.writer = writer;
        marshaller = null;
        this.jaxbPackage = jaxbPackage;
    }

    @Override
    public void write(T entry) {
        try {
            this.marshaller.marshal(entry, writer);
            writer.write("\n");
        } catch (Exception e) {
            throw new XmlWriterException(e);
        }
    }

    @Override
    public void writeHeader(String header) {
        try {
            writer.write(header);
        } catch (IOException e) {
            throw new XmlWriterException("Failed to writer header", e);
        }
    }

    @Override
    public void init() {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(jaxbPackage);
            marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
        } catch (Exception e) {
            throw new XmlWriterException("JAXB initiallation failed", e);
        }
    }

    @Override
    public int write(Stream<T> entries) {
        final AtomicInteger count = new AtomicInteger();
        entries.forEach(
                entry -> {
                    write(entry);
                    count.incrementAndGet();
                });
        return count.get();
    }

    @Override
    public void writeFooter(String footer) {
        try {
            writer.write(footer);
        } catch (IOException e) {
            throw new XmlWriterException("Failed to writer footer", e);
        }
    }
}

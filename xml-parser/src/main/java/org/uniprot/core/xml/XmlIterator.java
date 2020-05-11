package org.uniprot.core.xml;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Function;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

public class XmlIterator<T, R> implements Iterator<R> {

    private static final Logger logger = LoggerFactory.getLogger(XmlIterator.class);
    private T xmlEntry;

    // set up a StAX reader
    private final XMLStreamReader staxStreamReader;
    protected final Unmarshaller unmarshaller;
    private final Class<T> clazz;
    private final String startElement;
    private final Function<T, R> converter;

    public XmlIterator(
            InputStream inputStream, Class<T> clazz, String startElement, Function<T, R> converter)
            throws XMLStreamException, JAXBException {
        XMLInputFactory xif = XMLInputFactory.newFactory();
        staxStreamReader = xif.createXMLStreamReader(inputStream);
        JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
        unmarshaller = jaxbContext.createUnmarshaller();
        this.clazz = clazz;
        this.startElement = startElement;
        this.converter = converter;
    }

    @Override
    public boolean hasNext() {
        if (this.xmlEntry == null) this.xmlEntry = getNextEntry();

        boolean result = this.xmlEntry != null;
        if (!result) {
            try {
                staxStreamReader.close();
            } catch (XMLStreamException e) {
                logger.warn("error while closing stax stream", e);
            }
        }
        return result;
    }

    @Override
    public R next() {
        T localXmlEntry = this.xmlEntry;
        this.xmlEntry = getNextEntry();
        if (localXmlEntry != null) return converter.apply(localXmlEntry);
        else throw new NoSuchElementException();
    }

    private T getNextEntry() {
        try {
            while (staxStreamReader.hasNext()) {

                if (staxStreamReader.getEventType() == XMLStreamConstants.START_ELEMENT
                        && staxStreamReader.getLocalName().equals(startElement)) {
                    break;
                }
                staxStreamReader.next();
            }

            if (staxStreamReader.getEventType() == XMLStreamConstants.END_DOCUMENT) return null;

            JAXBElement<T> treeObject = unmarshaller.unmarshal(staxStreamReader, clazz);

            if (treeObject != null) return treeObject.getValue();
            else return null;
        } catch (JAXBException | XMLStreamException e) {
            logger.error("Parser error {}", e);
        }

        return null;
    }
}

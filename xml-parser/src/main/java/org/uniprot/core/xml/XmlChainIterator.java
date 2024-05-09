package org.uniprot.core.xml;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import java.util.zip.GZIPInputStream;

import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlChainIterator<T, R> implements Iterator<R> {
    private static final Logger logger = LoggerFactory.getLogger(XmlChainIterator.class);
    private final Iterator<InputStream> inputStreamIterator;
    private final Class<T> clazz;
    private final String startElement;
    private final Function<T, R> converter;
    private XmlIterator<T, R> entryIterator;
    private InputStream currentStream;

    public XmlChainIterator(
            Iterator<InputStream> inputStreamIterator,
            Class<T> clazz,
            String startElement,
            Function<T, R> converter) {
        this.inputStreamIterator = inputStreamIterator;
        this.clazz = clazz;
        this.startElement = startElement;
        this.converter = converter;
    }

    @Override
    public boolean hasNext() {
        if (this.entryIterator != null && this.entryIterator.hasNext()) return true;
        else {

            // close the previous stream.
            if (this.entryIterator != null) { // => !this.entryIterator.hasNext()
                closeCurrentStream();
            }

            if (!this.inputStreamIterator.hasNext()) {
                return false;
            }

            try {
                this.currentStream = inputStreamIterator.next();
                this.entryIterator =
                        new XmlIterator<>(this.currentStream, clazz, startElement, converter);
            } catch (XMLStreamException | JAXBException e) {
                return false;
            }

            boolean hasNext = this.entryIterator.hasNext();
            if (!hasNext) {
                closeCurrentStream();
            }
            return hasNext;
        }
    }

    @Override
    public R next() {
        return this.entryIterator.next();
    }

    private void closeCurrentStream() {
        try {
            this.currentStream.close();
        } catch (IOException e) {
            logger.error("Problem closing stream:", e);
        }
    }

    public static class FileInputStreamIterator implements Iterator<InputStream> {

        private final List<String> files = new ArrayList<>();

        public FileInputStreamIterator(List<String> files) {
            this.files.addAll(files);
        }

        @Override
        public boolean hasNext() {
            return !this.files.isEmpty();
        }

        @Override
        public InputStream next() {
            try {
                String file = files.remove(0);

                InputStream is = XmlChainIterator.class.getClassLoader().getResourceAsStream(file);
                if (is != null) return is;

                if (file.endsWith(".gzip") || file.endsWith(".gz")) {
                    return new GZIPInputStream(new FileInputStream(file));
                } else {
                    return new FileInputStream(file);
                }

            } catch (IOException e) {
                throw new RuntimeException("Cannot find the specified file:", e);
            }
        }
    }
}

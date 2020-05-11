package org.uniprot.core.xml;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.uniprot.core.xml.jaxb.proteome.Proteome;
import org.uniprot.core.xml.jaxb.uniref.Entry;
import org.uniprot.core.xml.writer.DefaultXmlWriter;

import java.io.InputStream;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

/**
 * @author jluo
 * @date: 11 Jun 2019
 */
class XmlChainIteratorTest {
    static final String PROTEOME_ROOT_ELEMENT = "proteome";
    static final String UNIREF_ROOT_ELEMENT = "entry";

    @Test
    void testProteomeXmlReader() throws Exception {
        String file = "/proteome/proteome_example.xml";
        InputStream is = XmlChainIteratorTest.class.getResourceAsStream(file);

        assertNotNull(is);

        List<InputStream> iss = Arrays.asList(is);

        XmlChainIterator<Proteome, Proteome> chainingIterators =
                new XmlChainIterator<>(
                        iss.iterator(), Proteome.class, PROTEOME_ROOT_ELEMENT, Function.identity());
        int count = 0;
        while (chainingIterators.hasNext()) {
            Proteome proteome = chainingIterators.next();
            count++;
        }
        is.close();
        assertTrue(count > 0);
    }

    @Test
    void testUniRefXmlReader() throws Exception {
        String file = "/uniref/50_Q9EPS7_Q95604.xml";
        InputStream is = XmlChainIteratorTest.class.getResourceAsStream(file);

        assertNotNull(is);

        List<InputStream> iss = Arrays.asList(is);

        XmlChainIterator<Entry, Entry> chainingIterators =
                new XmlChainIterator<>(
                        iss.iterator(), Entry.class, UNIREF_ROOT_ELEMENT, Function.identity());
        int count = 0;
        StringWriter writer = new StringWriter();
        DefaultXmlWriter<Entry> xmlWriter =
                new DefaultXmlWriter<>(writer, "org.uniprot.core.xml.jaxb.uniref");
        xmlWriter.init();
        while (chainingIterators.hasNext()) {
            Entry entry = chainingIterators.next();
            count++;
            xmlWriter.write(entry);
        }
        is.close();
        System.out.println(writer.toString());

        assertEquals(2, count);
    }
}

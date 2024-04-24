package org.uniprot.core.xml.uniparc;

import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Marshaller;
import javax.xml.namespace.QName;

/**
 * @author jluo
 * @date: 24 May 2019
 */
class UniParcXmlTestHelper {
    private static final String TARGET_PACKAGE = "org.uniprot.core.xml.jaxb.uniparc";

    @SuppressWarnings("unchecked")
    static <T> String toXmlString(T obj, Class<T> clazz, String element) {
        try {
            StringWriter stringWriter = new StringWriter();
            JAXBContext jc = JAXBContext.newInstance(TARGET_PACKAGE);
            Marshaller marshaller = jc.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
            @SuppressWarnings("rawtypes")
            JAXBElement jbe =
                    new JAXBElement(new QName("http://uniprot.org/uniprot", element), clazz, obj);
            marshaller.marshal(jbe, stringWriter);
            stringWriter.close();
            return stringWriter.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

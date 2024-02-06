package org.uniprot.core.xml.uniprot;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.Marshaller;

import java.io.StringWriter;

import javax.xml.namespace.QName;

public class UniProtXmlTestHelper {
    public static final String TARGET_PACKAGE = "org.uniprot.core.xml.jaxb.uniprot";

    @SuppressWarnings("unchecked")
    public static <T> String toXmlString(T obj, Class<T> clazz, String element) {
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

package org.uniprot.core.xml;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.bbottema.loremipsumobjects.ClassBindings;
import org.bbottema.loremipsumobjects.LoremIpsumObjectCreator;
import org.bbottema.loremipsumobjects.typefactories.MethodBasedFactory;
import org.uniprot.core.xml.jaxb.unirule.ObjectFactory;

public abstract class AbstractConverterTest {

    protected static ObjectFactory uniRuleObjectFactory;
    protected static org.uniprot.core.xml.jaxb.uniprot.ObjectFactory uniProtObjectFactory;
    protected static LoremIpsumObjectCreator objectCreator;
    protected static ClassBindings classBindings;
    protected static Converter converter;

    static {
        classBindings = new ClassBindings();
        try {
            classBindings.bind(
                    XMLGregorianCalendar.class,
                    new MethodBasedFactory<>(
                            AbstractConverterTest.class.getMethod("createXMLGregorianCalendar")));
        } catch (NoSuchMethodException nse) {
            nse.printStackTrace();
            System.exit(0);
        }
        objectCreator = new LoremIpsumObjectCreator(classBindings);
        uniRuleObjectFactory = new ObjectFactory();
        uniProtObjectFactory = new org.uniprot.core.xml.jaxb.uniprot.ObjectFactory();
    }

    public static XMLGregorianCalendar createXMLGregorianCalendar()
            throws DatatypeConfigurationException {
        return DatatypeFactory.newInstance().newXMLGregorianCalendar();
    }
}

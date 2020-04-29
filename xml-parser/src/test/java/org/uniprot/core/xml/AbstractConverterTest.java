package org.uniprot.core.xml;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.bbottema.loremipsumobjects.ClassBindings;
import org.bbottema.loremipsumobjects.LoremIpsumObjectCreator;
import org.bbottema.loremipsumobjects.typefactories.MethodBasedFactory;
import org.uniprot.core.xml.jaxb.uniprot.MoleculeType;
import org.uniprot.core.xml.jaxb.uniprot.PropertyType;
import org.uniprot.core.xml.jaxb.unirule.ObjectFactory;

public abstract class AbstractConverterTest {

    protected static ObjectFactory uniRuleObjectFactory;
    protected static org.uniprot.core.xml.jaxb.uniprot.ObjectFactory uniProtObjectFactory;
    protected static String random;
    protected static LoremIpsumObjectCreator objectCreator;
    protected static ClassBindings classBindings;

    static {
        classBindings = new ClassBindings();
        try {
            classBindings.bind(
                    XMLGregorianCalendar.class,
                    new MethodBasedFactory<>(
                            AbstractConverterTest.class.getMethod("createXMLGregorianCalendar")));
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            System.exit(0);
        }
        objectCreator = new LoremIpsumObjectCreator(classBindings);
        uniRuleObjectFactory = new ObjectFactory();
        uniProtObjectFactory = new org.uniprot.core.xml.jaxb.uniprot.ObjectFactory();
        random = UUID.randomUUID().toString().substring(0, 4);
    }

    public static XMLGregorianCalendar createXMLGregorianCalendar()
            throws DatatypeConfigurationException {
        return DatatypeFactory.newInstance().newXMLGregorianCalendar();
    }

    public static MoleculeType createMoleculeType() {
        // molecule
        MoleculeType molecule = uniProtObjectFactory.createMoleculeType();
        String mid = "mid-" + random;
        String mvalue = "mvalue-" + random;
        molecule.setValue(mvalue);
        molecule.setId(mid);
        return molecule;
    }

    public static List<Integer> createListOfInt(int listSize) {
        return IntStream.range(0, listSize)
                .mapToObj(i -> ThreadLocalRandom.current().nextInt())
                .collect(Collectors.toList());
    }

    public static List<PropertyType> createListOfPropertyType(int listSize) {
        return IntStream.range(0, listSize)
                .mapToObj(i -> createPropertyType())
                .collect(Collectors.toList());
    }

    public static PropertyType createPropertyType() {
        String type = "type-" + random;
        String value = "value-" + random;
        PropertyType propertyType = uniProtObjectFactory.createPropertyType();
        propertyType.setType(type);
        propertyType.setValue(value);
        return propertyType;
    }
}

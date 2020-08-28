package org.uniprot.core.xml.proteome;

import org.uniprot.core.xml.jaxb.proteome.ObjectFactory;
import org.uniprot.core.xml.jaxb.proteome.PropertyType;

/**
 * @author lgonzales
 * @since 16/04/2020
 */
class PropertyConverter {

    private final ObjectFactory xmlFactory;

    PropertyConverter() {
        this(new ObjectFactory());
    }

    PropertyConverter(ObjectFactory xmlFactory) {
        this.xmlFactory = xmlFactory;
    }

    PropertyType createProperty(String name, int value) {
        return createProperty(name, String.valueOf(value));
    }

    PropertyType createProperty(String name, double value) {
        return createProperty(name, String.valueOf(value));
    }

    PropertyType createProperty(String name, String value) {
        PropertyType property = xmlFactory.createPropertyType();
        property.setName(name);
        property.setValue(value);
        return property;
    }
}

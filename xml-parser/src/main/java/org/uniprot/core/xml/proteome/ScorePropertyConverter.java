package org.uniprot.core.xml.proteome;

import org.uniprot.core.xml.jaxb.proteome.ObjectFactory;
import org.uniprot.core.xml.jaxb.proteome.ScorePropertyType;

/**
 * @author lgonzales
 * @since 16/04/2020
 */
class ScorePropertyConverter {

    private final ObjectFactory xmlFactory;

    ScorePropertyConverter() {
        this(new ObjectFactory());
    }

    ScorePropertyConverter(ObjectFactory xmlFactory) {
        this.xmlFactory = xmlFactory;
    }

    ScorePropertyType createProperty(String name, int value) {
        return createProperty(name, String.valueOf(value));
    }

    ScorePropertyType createProperty(String name, double value) {
        return createProperty(name, String.valueOf(value));
    }

    ScorePropertyType createProperty(String name, String value) {
        ScorePropertyType property = xmlFactory.createScorePropertyType();
        property.setName(name);
        property.setValue(value);
        return property;
    }
}

package org.uniprot.core.xml.feature.antigen;

import org.uniprot.core.CrossReference;
import org.uniprot.core.Property;
import org.uniprot.core.antigen.AntigenDatabase;
import org.uniprot.core.impl.CrossReferenceBuilder;
import org.uniprot.core.util.Utils;
import org.uniprot.core.xml.Converter;
import org.uniprot.core.xml.jaxb.feature.DbReferenceType;
import org.uniprot.core.xml.jaxb.feature.ObjectFactory;
import org.uniprot.core.xml.jaxb.feature.PropertyType;

import java.util.stream.Collectors;

public class CrossReferenceConverter
        implements Converter<DbReferenceType, CrossReference<AntigenDatabase>> {
    private final ObjectFactory xmlFactory;

    public CrossReferenceConverter() {
        this(new ObjectFactory());
    }

    public CrossReferenceConverter(ObjectFactory xmlFactory) {
        this.xmlFactory = xmlFactory;
    }

    @Override
    public CrossReference<AntigenDatabase> fromXml(DbReferenceType xmlObj) {
        CrossReferenceBuilder<AntigenDatabase> builder = new CrossReferenceBuilder<>();
        if(Utils.notNullNotEmpty(xmlObj.getType())) {
            builder.database(AntigenDatabase.typeOf(xmlObj.getType()));
        }
        if(Utils.notNullNotEmpty(xmlObj.getId())) {
            builder.id(xmlObj.getId());
        }
        if(Utils.notNullNotEmpty(xmlObj.getProperty())) {
            builder.propertiesSet(
                    xmlObj.getProperty().stream()
                            .map(this::fromXmlProperty)
                            .collect(Collectors.toList()));
        }

        return builder.build();
    }

    @Override
    public DbReferenceType toXml(CrossReference<AntigenDatabase> uniObj) {
        DbReferenceType xmlObj = xmlFactory.createDbReferenceType();
        if(uniObj.hasDatabase()) {
            xmlObj.setType(uniObj.getDatabase().getDisplayName());
        }
        if(uniObj.hasId()) {
            xmlObj.setId(uniObj.getId());
        }
        if(uniObj.hasProperties()) {
            uniObj.getProperties().stream()
                    .map(this::toXmlProperty)
                    .forEach(val -> xmlObj.getProperty().add(val));
        }

        return xmlObj;
    }

    private Property fromXmlProperty(PropertyType property) {
        return new Property(property.getType(), property.getValue());
    }

    private PropertyType toXmlProperty(Property property) {
        PropertyType ptype = xmlFactory.createPropertyType();
        ptype.setType(property.getKey());
        ptype.setValue(property.getValue());
        return ptype;
    }
}

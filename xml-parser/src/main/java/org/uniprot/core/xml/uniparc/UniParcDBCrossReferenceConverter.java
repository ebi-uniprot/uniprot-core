package org.uniprot.core.xml.uniparc;

import java.util.stream.Collectors;

import org.uniprot.core.Property;
import org.uniprot.core.uniparc.UniParcDBCrossReference;
import org.uniprot.core.uniparc.UniParcDatabaseType;
import org.uniprot.core.uniparc.builder.UniParcDBCrossReferenceBuilder;
import org.uniprot.core.xml.Converter;
import org.uniprot.core.xml.jaxb.uniparc.DbReferenceType;
import org.uniprot.core.xml.jaxb.uniparc.ObjectFactory;
import org.uniprot.core.xml.jaxb.uniparc.PropertyType;
import org.uniprot.core.xml.uniprot.XmlConverterHelper;

/**
 * @author jluo
 * @date: 23 May 2019
 */
public class UniParcDBCrossReferenceConverter
        implements Converter<DbReferenceType, UniParcDBCrossReference> {
    private final PropertyConverter propertyConverter;
    private final ObjectFactory xmlFactory;

    public UniParcDBCrossReferenceConverter() {
        this(new ObjectFactory());
    }

    public UniParcDBCrossReferenceConverter(ObjectFactory xmlFactory) {
        this.xmlFactory = xmlFactory;
        this.propertyConverter = new PropertyConverter(xmlFactory);
    }

    @Override
    public UniParcDBCrossReference fromXml(DbReferenceType xmlObj) {
        UniParcDBCrossReferenceBuilder builder = new UniParcDBCrossReferenceBuilder();
        builder.databaseType(UniParcDatabaseType.typeOf(xmlObj.getType()))
                .id(xmlObj.getId())
                .active(xmlObj.getActive().equals("Y"))
                .versionI(xmlObj.getVersionI())
                .created(XmlConverterHelper.dateFromXml(xmlObj.getCreated()))
                .lastUpdated(XmlConverterHelper.dateFromXml(xmlObj.getLast()))
                .properties(
                        xmlObj.getProperty().stream()
                                .map(propertyConverter::fromXml)
                                .collect(Collectors.toList()));
        if (xmlObj.getVersion() != null) builder.version(xmlObj.getVersion());
        return builder.build();
    }

    @Override
    public DbReferenceType toXml(UniParcDBCrossReference uniObj) {
        DbReferenceType xmlObj = xmlFactory.createDbReferenceType();
        xmlObj.setActive(uniObj.isActive() ? "Y" : "N");
        xmlObj.setId(uniObj.getId());
        xmlObj.setType(uniObj.getDatabaseType().toDisplayName());
        xmlObj.setVersionI(uniObj.getVersionI());
        if (uniObj.getVersion() != null) xmlObj.setVersion(uniObj.getVersion());
        xmlObj.setCreated(XmlConverterHelper.dateToXml(uniObj.getCreated()));
        xmlObj.setLast(XmlConverterHelper.dateToXml(uniObj.getLastUpdated()));
        uniObj.getProperties().stream()
                .map(propertyConverter::toXml)
                .forEach(val -> xmlObj.getProperty().add(val));
        return xmlObj;
    }

    class PropertyConverter implements Converter<PropertyType, Property> {
        private final ObjectFactory xmlFactory;

        public PropertyConverter() {
            this(new ObjectFactory());
        }

        public PropertyConverter(ObjectFactory xmlFactory) {
            this.xmlFactory = xmlFactory;
        }

        @Override
        public Property fromXml(PropertyType xmlObj) {
            return new Property(xmlObj.getType(), xmlObj.getValue());
        }

        @Override
        public PropertyType toXml(Property uniObj) {
            PropertyType xmlObj = xmlFactory.createPropertyType();
            xmlObj.setType(uniObj.getKey());
            xmlObj.setValue(uniObj.getValue());
            return xmlObj;
        }
    }
}

package org.uniprot.core.xml.uniparc;

import org.uniprot.core.Property;
import org.uniprot.core.uniparc.UniParcCrossReference;
import org.uniprot.core.uniparc.UniParcDatabase;
import org.uniprot.core.uniparc.impl.UniParcCrossReferenceBuilder;
import org.uniprot.core.xml.Converter;
import org.uniprot.core.xml.jaxb.uniparc.DbReferenceType;
import org.uniprot.core.xml.jaxb.uniparc.ObjectFactory;
import org.uniprot.core.xml.jaxb.uniparc.PropertyType;
import org.uniprot.core.xml.uniprot.XmlConverterHelper;

import java.util.stream.Collectors;

/**
 * @author jluo
 * @date: 23 May 2019
 */
public class UniParcDBCrossReferenceConverter
        implements Converter<DbReferenceType, UniParcCrossReference> {
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
    public UniParcCrossReference fromXml(DbReferenceType xmlObj) {
        UniParcCrossReferenceBuilder builder = new UniParcCrossReferenceBuilder();
        builder.database(UniParcDatabase.typeOf(xmlObj.getType()))
                .id(xmlObj.getId())
                .active(xmlObj.getActive().equals("Y"))
                .versionI(xmlObj.getVersionI())
                .created(XmlConverterHelper.dateFromXml(xmlObj.getCreated()))
                .lastUpdated(XmlConverterHelper.dateFromXml(xmlObj.getLast()))
                .propertiesSet(
                        xmlObj.getProperty().stream()
                                .map(propertyConverter::fromXml)
                                .collect(Collectors.toList()));
        if (xmlObj.getVersion() != null) builder.version(xmlObj.getVersion());
        return builder.build();
    }

    @Override
    public DbReferenceType toXml(UniParcCrossReference uniObj) {
        DbReferenceType xmlObj = xmlFactory.createDbReferenceType();
        xmlObj.setActive(uniObj.isActive() ? "Y" : "N");
        xmlObj.setId(uniObj.getId());
        xmlObj.setType(uniObj.getDatabase().getDisplayName());
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

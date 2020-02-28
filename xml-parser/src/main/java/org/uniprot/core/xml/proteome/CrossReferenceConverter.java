package org.uniprot.core.xml.proteome;

import java.util.stream.Collectors;

import org.uniprot.core.DBCrossReference;
import org.uniprot.core.Property;
import org.uniprot.core.builder.DBCrossReferenceBuilder;
import org.uniprot.core.proteome.ProteomeDatabase;
import org.uniprot.core.xml.Converter;
import org.uniprot.core.xml.jaxb.proteome.DbReferenceType;
import org.uniprot.core.xml.jaxb.proteome.ObjectFactory;
import org.uniprot.core.xml.jaxb.proteome.PropertyType;

public class CrossReferenceConverter
        implements Converter<DbReferenceType, DBCrossReference<ProteomeDatabase>> {
    private static final String GC_SET_ACC = "GCSetAcc";
    private final ObjectFactory xmlFactory;

    public CrossReferenceConverter() {
        this(new ObjectFactory());
    }

    public CrossReferenceConverter(ObjectFactory xmlFactory) {
        this.xmlFactory = xmlFactory;
    }

    @Override
    public DBCrossReference<ProteomeDatabase> fromXml(DbReferenceType xmlObj) {
        DBCrossReferenceBuilder<ProteomeDatabase> builder =
                new DBCrossReferenceBuilder<ProteomeDatabase>()
                        .databaseType(fromXml(xmlObj.getType()))
                        .id(xmlObj.getId());
        builder.propertiesSet(
                xmlObj.getProperty().stream()
                        .map(this::fromXmlProperty)
                        .collect(Collectors.toList()));

        return builder.build();
    }

    @Override
    public DbReferenceType toXml(DBCrossReference<ProteomeDatabase> uniObj) {
        DbReferenceType xmlObj = xmlFactory.createDbReferenceType();
        xmlObj.setType(toXml(uniObj.getDatabaseType()));
        xmlObj.setId(uniObj.getId());
        uniObj.getProperties().stream()
                .map(this::toXmlProperty)
                .forEach(val -> xmlObj.getProperty().add(val));

        return xmlObj;
    }

    private String toXml(ProteomeDatabase type) {
        if (type == ProteomeDatabase.GENOME_ASSEMBLY) return GC_SET_ACC;
        else return type.getName();
    }

    private ProteomeDatabase fromXml(String type) {
        if (GC_SET_ACC.equals(type)) {
            return ProteomeDatabase.GENOME_ASSEMBLY;
        } else return ProteomeDatabase.fromValue(type);
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

package org.uniprot.core.xml.proteome;

import org.uniprot.core.CrossReference;
import org.uniprot.core.Property;
import org.uniprot.core.impl.CrossReferenceBuilder;
import org.uniprot.core.proteome.ProteomeDatabase;
import org.uniprot.core.xml.Converter;
import org.uniprot.core.xml.jaxb.proteome.DbReferenceType;
import org.uniprot.core.xml.jaxb.proteome.ObjectFactory;
import org.uniprot.core.xml.jaxb.proteome.PropertyType;

import java.util.stream.Collectors;

public class CrossReferenceConverter
        implements Converter<DbReferenceType, CrossReference<ProteomeDatabase>> {
    private static final String GC_SET_ACC = "GCSetAcc";
    private final ObjectFactory xmlFactory;

    public CrossReferenceConverter() {
        this(new ObjectFactory());
    }

    public CrossReferenceConverter(ObjectFactory xmlFactory) {
        this.xmlFactory = xmlFactory;
    }

    @Override
    public CrossReference<ProteomeDatabase> fromXml(DbReferenceType xmlObj) {
        CrossReferenceBuilder<ProteomeDatabase> builder =
                new CrossReferenceBuilder<ProteomeDatabase>()
                        .database(fromXml(xmlObj.getType()))
                        .id(xmlObj.getId());
        builder.propertiesSet(
                xmlObj.getProperty().stream()
                        .map(this::fromXmlProperty)
                        .collect(Collectors.toList()));

        return builder.build();
    }

    @Override
    public DbReferenceType toXml(CrossReference<ProteomeDatabase> uniObj) {
        DbReferenceType xmlObj = xmlFactory.createDbReferenceType();
        xmlObj.setType(toXml(uniObj.getDatabase()));
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
        } else return ProteomeDatabase.typeOf(type);
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

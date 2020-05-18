package org.uniprot.core.xml.feature;

/**
 * @author lgonzales
 * @since 16/05/2020
 */
import org.uniprot.core.CrossReference;
import org.uniprot.core.Property;
import org.uniprot.core.impl.CrossReferenceBuilder;
import org.uniprot.core.uniprotkb.evidence.EvidenceDatabase;
import org.uniprot.core.util.Utils;
import org.uniprot.core.xml.Converter;
import org.uniprot.core.xml.jaxb.feature.DbReferenceType;
import org.uniprot.core.xml.jaxb.feature.ObjectFactory;
import org.uniprot.core.xml.jaxb.feature.PropertyType;

public class EvidenceCrossRefConverter
        implements Converter<DbReferenceType, CrossReference<EvidenceDatabase>> {
    private final ObjectFactory xmlFactory;

    public EvidenceCrossRefConverter() {
        this(new ObjectFactory());
    }

    public EvidenceCrossRefConverter(ObjectFactory xmlUniprotFactory) {
        this.xmlFactory = xmlUniprotFactory;
    }

    @Override
    public CrossReference<EvidenceDatabase> fromXml(DbReferenceType xmlObj) {
        CrossReferenceBuilder<EvidenceDatabase> builder = new CrossReferenceBuilder<>();
        if (Utils.notNullNotEmpty(xmlObj.getId())) {
            builder.id(xmlObj.getId());
        }

        if (Utils.notNullNotEmpty(xmlObj.getType())) {
            builder.database(new EvidenceDatabase(xmlObj.getType()));
        }

        if (Utils.notNullNotEmpty(xmlObj.getProperty())) {
            xmlObj.getProperty().stream()
                    .map(this::convertProperty)
                    .forEach(builder::propertiesAdd);
        }
        return builder.build();
    }

    @Override
    public DbReferenceType toXml(CrossReference<EvidenceDatabase> uniObj) {
        DbReferenceType dbRef = xmlFactory.createDbReferenceType();
        if (uniObj.hasId()) {
            dbRef.setId(uniObj.getId());
        }
        if (uniObj.hasDatabase()) {
            dbRef.setType(uniObj.getDatabase().getName());
        }
        if (uniObj.hasProperties()) {
            uniObj.getProperties().stream()
                    .map(this::convertyPropertyType)
                    .forEach(dbRef.getProperty()::add);
        }
        return dbRef;
    }

    private PropertyType convertyPropertyType(Property property) {
        PropertyType propertyType = xmlFactory.createPropertyType();
        propertyType.setType(Utils.emptyOrString(property.getKey()));
        propertyType.setValue(Utils.emptyOrString(property.getValue()));
        return propertyType;
    }

    private Property convertProperty(PropertyType propertyType) {
        String id = Utils.emptyOrString(propertyType.getType());
        String value = Utils.emptyOrString(propertyType.getValue());
        return new Property(id, value);
    }
}

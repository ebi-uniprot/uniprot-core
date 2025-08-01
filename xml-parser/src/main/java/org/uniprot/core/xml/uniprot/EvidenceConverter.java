package org.uniprot.core.xml.uniprot;

import java.math.BigInteger;
import java.util.List;

import org.uniprot.core.CrossReference;
import org.uniprot.core.Property;
import org.uniprot.core.impl.CrossReferenceBuilder;
import org.uniprot.core.uniprotkb.evidence.Evidence;
import org.uniprot.core.uniprotkb.evidence.EvidenceCode;
import org.uniprot.core.uniprotkb.evidence.EvidenceDatabase;
import org.uniprot.core.uniprotkb.evidence.impl.EvidenceBuilder;
import org.uniprot.core.xml.Converter;
import org.uniprot.core.xml.jaxb.uniprot.*;

public class EvidenceConverter implements Converter<EvidenceType, Evidence> {
    private final ObjectFactory xmlUniprotFactory;
    private final EvidenceCrossRefConverter xrefConverter;

    public EvidenceConverter() {
        this(new ObjectFactory());
    }

    public EvidenceConverter(ObjectFactory xmlUniprotFactory) {
        this.xmlUniprotFactory = xmlUniprotFactory;
        this.xrefConverter = new EvidenceCrossRefConverter(xmlUniprotFactory);
    }

    @Override
    public Evidence fromXml(EvidenceType xmlObj) {
        EvidenceCode evCode = EvidenceCode.typeOf(xmlObj.getType());

        EvidenceBuilder evidenceBuilder = new EvidenceBuilder().evidenceCode(evCode);
        if (xmlObj.getSource() != null) {
            CrossReference<EvidenceDatabase> xref = xrefConverter.fromXml(xmlObj.getSource());
            evidenceBuilder.databaseId(xref.getId()).databaseName(xref.getDatabase().getName()).crossReference(xref);
        }

        return evidenceBuilder.build();
    }

    @Override
    public EvidenceType toXml(Evidence uniObj) {
        EvidenceType xmlObj = xmlUniprotFactory.createEvidenceType();
        xmlObj.setType(uniObj.getEvidenceCode().getCode());
        if (uniObj.getEvidenceCrossReference() != null) {
            xmlObj.setSource(xrefConverter.toXml(uniObj.getEvidenceCrossReference()));
        }

        xmlObj.setKey(BigInteger.valueOf(1)); // set default key
        return xmlObj;
    }

    public static class EvidenceCrossRefConverter
            implements Converter<SourceType, CrossReference<EvidenceDatabase>> {
        private static final String REFERENCE = "Reference";
        private static final String REF = "Ref.";
        private final ObjectFactory xmlUniprotFactory;

        public EvidenceCrossRefConverter() {
            this(new ObjectFactory());
        }

        public EvidenceCrossRefConverter(ObjectFactory xmlUniprotFactory) {
            this.xmlUniprotFactory = xmlUniprotFactory;
        }

        @Override
        public CrossReference<EvidenceDatabase> fromXml(SourceType xmlObj) {
            if (xmlObj.getDbReference() != null) {
                List<Property> properties = xmlObj.getDbReference().getProperty()
                        .stream()
                        .map(propertyType -> new Property(propertyType.getType(), propertyType.getValue()))
                        .toList();
                return new CrossReferenceBuilder<EvidenceDatabase>()
                        .database(new EvidenceDatabase(xmlObj.getDbReference().getType()))
                        .id(xmlObj.getDbReference().getId())
                        .propertiesSet(properties)
                        .build();
            } else {
                String attr = REF + xmlObj.getRef().toString();
                return new CrossReferenceBuilder<EvidenceDatabase>()
                        .database(new EvidenceDatabase(REFERENCE))
                        .id(attr)
                        .build();
            }
        }

        @Override
        public SourceType toXml(CrossReference<EvidenceDatabase> uniObj) {
            SourceType source = xmlUniprotFactory.createSourceType();
            if (uniObj.getDatabase().getName().equals(REFERENCE)) {
                String val = uniObj.getId().substring(4).trim();
                BigInteger bi = new BigInteger(val);
                source.setRef(bi);

            } else {
                DbReferenceType dbRef = xmlUniprotFactory.createDbReferenceType();
                dbRef.setType(uniObj.getDatabase().getName());
                dbRef.setId(uniObj.getId());
                if(uniObj.getProperties() != null && !uniObj.getProperties().isEmpty()) {
                    List<Property> properties = uniObj.getProperties();
                    for (Property property : properties) {
                        PropertyType propertyType = xmlUniprotFactory.createPropertyType();
                        propertyType.setType(property.getKey());
                        propertyType.setValue(property.getValue());
                        dbRef.getProperty().add(propertyType);
                    }
                }
                source.setDbReference(dbRef);
            }
            return source;
        }
    }
}

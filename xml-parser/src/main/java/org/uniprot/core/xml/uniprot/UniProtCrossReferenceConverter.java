package org.uniprot.core.xml.uniprot;

import java.util.List;
import java.util.Optional;

import org.uniprot.core.cv.xdb.UniProtDatabaseAttribute;
import org.uniprot.core.cv.xdb.UniProtDatabaseDetail;
import org.uniprot.core.uniprotkb.evidence.Evidence;
import org.uniprot.core.uniprotkb.xdb.*;
import org.uniprot.core.uniprotkb.xdb.impl.UniProtCrossReferenceBuilder;
import org.uniprot.core.xml.Converter;
import org.uniprot.core.xml.jaxb.uniprot.DbReferenceType;
import org.uniprot.core.xml.jaxb.uniprot.MoleculeType;
import org.uniprot.core.xml.jaxb.uniprot.ObjectFactory;
import org.uniprot.core.xml.jaxb.uniprot.PropertyType;
import org.uniprot.cv.evidence.GOEvidences;
import org.uniprot.cv.xdb.UniProtDatabaseTypes;
import org.uniprot.cv.xdb.UniProtKBDatabaseImpl;

import com.google.common.base.Strings;

public class UniProtCrossReferenceConverter
        implements Converter<DbReferenceType, UniProtKBCrossReference> {
    private static final String GO = "GO";
    private static final String DASH = "-";
    private final ObjectFactory xmlUniprotFactory;
    private final EvidenceIndexMapper evRefMapper;

    public UniProtCrossReferenceConverter(EvidenceIndexMapper evRefMapper) {
        this(evRefMapper, new ObjectFactory());
    }

    public UniProtCrossReferenceConverter(EvidenceIndexMapper evRefMapper, ObjectFactory xmlUniprotFactory) {
        this.xmlUniprotFactory = xmlUniprotFactory;
        this.evRefMapper = evRefMapper;
    }

    @Override
    public UniProtKBCrossReference fromXml(DbReferenceType xmlObj) {
        UniProtDatabaseDetail xdbType =
                UniProtDatabaseTypes.INSTANCE.getDbTypeByName(xmlObj.getType());

        String databaseName = xdbType.getName();
        String id = xmlObj.getId();
        String description = DASH;
        String thirdAttribute = null;
        String fourthAttribute = null;
        String isoformId = null;
        if (xmlObj.getMolecule() != null) isoformId = xmlObj.getMolecule().getId();

        for (int i = 0; i < xdbType.getAttributes().size(); i++) {
            String xmlTag = xdbType.getAttributes().get(i).getXmlTag();
            String val = getValue(xmlObj, xmlTag);
            if (i == 0) description = val;
            else if (i == 1) {
                if (GO.equals(databaseName)) {
                    thirdAttribute = getGOThirdAttribute(xmlObj, xdbType);
                } else thirdAttribute = val;
            } else if (i == 2) {
                if (!GO.equals(databaseName)) {
                    fourthAttribute = val;
                }
            }
        }
        List<Evidence> evidences = this.evRefMapper.parseEvidenceIds(xmlObj.getEvidence());
        UniProtKBDatabase type = new UniProtKBDatabaseImpl(databaseName);
        return new UniProtCrossReferenceBuilder()
                .database(type)
                .isoformId(isoformId)
                .id(id)
                .propertiesAdd(type.getUniProtDatabaseAttribute(0), description)
                .propertiesAdd(type.getUniProtDatabaseAttribute(1), thirdAttribute)
                .propertiesAdd(type.getUniProtDatabaseAttribute(2), fourthAttribute)
                .evidencesSet(evidences)
                .build();
    }

    private String getGOThirdAttribute(DbReferenceType xmlObj, UniProtDatabaseDetail xdbType) {
        String evXmlTag = xdbType.getAttributes().get(1).getXmlTag();
        String eco = getValue(xmlObj, evXmlTag);
        String projectXmlTag = xdbType.getAttributes().get(2).getXmlTag();
        Optional<String> gaf = GOEvidences.INSTANCE.convertECOToGAF(eco);
        String project = getValue(xmlObj, projectXmlTag);
        return gaf.orElse("") + ":" + project;
    }

    private String getValue(DbReferenceType xmlObj, String xmlTag) {
        Optional<PropertyType> xmlProperty =
                xmlObj.getProperty().stream()
                        .filter(val -> val.getType().equals(xmlTag))
                        .findFirst();
        if (xmlProperty.isPresent()) {
            return xmlProperty.get().getValue();
        } else return DASH;
    }

    private String getValue(UniProtKBCrossReference uniObj, int nProperty) {
        if (GO.equals(uniObj.getDatabase().getName())) {
            return getGOValue(uniObj, nProperty);
        }
        if (nProperty >= uniObj.getProperties().size()) {
            return DASH;
        } else return uniObj.getProperties().get(nProperty).getValue();
    }

    private String getGOValue(UniProtKBCrossReference uniObj, int nProperty) {
        if (nProperty == 1) {
            String value = uniObj.getProperties().get(1).getValue();
            String[] tokens = value.split(":");
            Optional<String> evidence = GOEvidences.INSTANCE.convertGAFToECO(tokens[0]);
            return evidence.orElse("");
        } else if (nProperty == 2) {
            String value = uniObj.getProperties().get(1).getValue();
            String[] tokens = value.split(":");
            if (tokens.length == 2) return tokens[1];
            else return "";
        } else {
            return uniObj.getProperties().get(nProperty).getValue();
        }
    }

    @Override
    public DbReferenceType toXml(UniProtKBCrossReference uniObj) {
        DbReferenceType xmlReference = xmlUniprotFactory.createDbReferenceType();
        xmlReference.setType(uniObj.getDatabase().getUniProtDatabaseDetail().getDisplayName());
        xmlReference.setId(uniObj.getId());
        if (!Strings.isNullOrEmpty(uniObj.getIsoformId())) {
            MoleculeType mol = xmlUniprotFactory.createMoleculeType();
            mol.setId(uniObj.getIsoformId());
            xmlReference.setMolecule(mol);
        }
        addProperties(xmlReference, uniObj);
        return xmlReference;
    }

    private void addProperties(DbReferenceType xmlReference, UniProtKBCrossReference uniObj) {
        UniProtDatabaseDetail xdbTypeDetail = uniObj.getDatabase().getUniProtDatabaseDetail();
        List<UniProtDatabaseAttribute> attributes = xdbTypeDetail.getAttributes();
        int size = attributes.size();
        for (int i = 0; i < size; i++) {
            String val = getValue(uniObj, i);
            String xmltagName = attributes.get(i).getXmlTag();
            if (i == 0) {
                if (size == 1) {
                    if (!Strings.isNullOrEmpty(val) && !DASH.equals(val)) {
                        addProperty(xmlReference, xmltagName, val);
                    }
                } else {
                    addProperty(xmlReference, xmltagName, val);
                }
            } else {
                addProperty(xmlReference, xmltagName, val);
            }
        }
    }

    private void addProperty(DbReferenceType xmlReference, String propertyName, String value) {
        if ((propertyName != null) && (!propertyName.isEmpty())) {
            if (!value.equals("") && !value.equals(DASH)) {
                PropertyType property = xmlUniprotFactory.createPropertyType();
                property.setType(propertyName);
                property.setValue(value);
                xmlReference.getProperty().add(property);
            }
        }
    }
}

package org.uniprot.core.xml.uniprot;

import java.util.List;
import java.util.Optional;

import org.uniprot.core.cv.xdb.DBXRefTypeAttribute;
import org.uniprot.core.cv.xdb.UniProtXDbTypeDetail;
import org.uniprot.core.cv.xdb.UniProtXDbTypes;
import org.uniprot.core.uniprot.xdb.*;
import org.uniprot.core.uniprot.xdb.builder.UniProtDBCrossReferenceBuilder;
import org.uniprot.core.xml.Converter;
import org.uniprot.core.xml.jaxb.uniprot.DbReferenceType;
import org.uniprot.core.xml.jaxb.uniprot.MoleculeType;
import org.uniprot.core.xml.jaxb.uniprot.ObjectFactory;
import org.uniprot.core.xml.jaxb.uniprot.PropertyType;

import com.google.common.base.Strings;

public class UniProtCrossReferenceConverter
        implements Converter<DbReferenceType, UniProtDBCrossReference> {
    private static final String GO = "GO";
    private static final String DASH = "-";
    private final ObjectFactory xmlUniprotFactory;

    public UniProtCrossReferenceConverter() {
        this(new ObjectFactory());
    }

    public UniProtCrossReferenceConverter(ObjectFactory xmlUniprotFactory) {
        this.xmlUniprotFactory = xmlUniprotFactory;
    }

    @Override
    public UniProtDBCrossReference fromXml(DbReferenceType xmlObj) {
        UniProtXDbTypeDetail xdbType = UniProtXDbTypes.INSTANCE.getType(xmlObj.getType());

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

        UniProtXDbType type = new UniProtXDbType(databaseName);
        return new UniProtDBCrossReferenceBuilder()
                .databaseType(type)
                .isoformId(isoformId)
                .id(id)
                .addProperty(type.getAttribute(0), description)
                .addProperty(type.getAttribute(1), thirdAttribute)
                .addProperty(type.getAttribute(2), fourthAttribute)
                .build();
    }

    private String getGOThirdAttribute(DbReferenceType xmlObj, UniProtXDbTypeDetail xdbType) {
        String evXmlTag = xdbType.getAttributes().get(1).getXmlTag();
        String eco = getValue(xmlObj, evXmlTag);
        String projectXmlTag = xdbType.getAttributes().get(2).getXmlTag();
        Optional<String> gaf = GoEvidences.INSTANCE.convertECOToGAF(eco);
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

    private String getValue(UniProtDBCrossReference uniObj, int nProperty) {
        if (GO.equals(uniObj.getDatabaseType().getName())) {
            return getGOValue(uniObj, nProperty);
        }
        if (nProperty >= uniObj.getProperties().size()) {
            return DASH;
        } else return uniObj.getProperties().get(nProperty).getValue();
    }

    private String getGOValue(UniProtDBCrossReference uniObj, int nProperty) {
        if (nProperty == 1) {
            String value = uniObj.getProperties().get(1).getValue();
            String[] tokens = value.split(":");
            Optional<String> evidence = GoEvidences.INSTANCE.convertGAFToECO(tokens[0]);
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
    public DbReferenceType toXml(UniProtDBCrossReference uniObj) {
        DbReferenceType xmlReference = xmlUniprotFactory.createDbReferenceType();
        xmlReference.setType(uniObj.getDatabaseType().getDetail().getDisplayName());
        xmlReference.setId(uniObj.getId());
        if (!Strings.isNullOrEmpty(uniObj.getIsoformId())) {
            MoleculeType mol = xmlUniprotFactory.createMoleculeType();
            mol.setId(uniObj.getIsoformId());
            xmlReference.setMolecule(mol);
        }
        addProperties(xmlReference, uniObj);
        return xmlReference;
    }

    private void addProperties(DbReferenceType xmlReference, UniProtDBCrossReference uniObj) {
        UniProtXDbTypeDetail xdbTypeDetail = uniObj.getDatabaseType().getDetail();
        List<DBXRefTypeAttribute> attributes = xdbTypeDetail.getAttributes();
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

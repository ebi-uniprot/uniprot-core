package org.uniprot.core.xml.uniparc;

import org.uniprot.core.uniparc.ProteomeIdComponent;
import org.uniprot.core.uniparc.UniParcCrossReference;
import org.uniprot.core.uniparc.UniParcDatabase;
import org.uniprot.core.uniparc.impl.UniParcCrossReferenceBuilder;
import org.uniprot.core.util.Utils;
import org.uniprot.core.xml.Converter;
import org.uniprot.core.xml.CrossReferenceConverterUtils;
import org.uniprot.core.xml.jaxb.uniparc.DbReferenceType;
import org.uniprot.core.xml.jaxb.uniparc.ObjectFactory;
import org.uniprot.core.xml.jaxb.uniparc.PropertyType;
import org.uniprot.core.xml.uniprot.XmlConverterHelper;
import org.uniprot.cv.taxonomy.TaxonomyRepo;

import java.util.ArrayList;
import java.util.List;

import static org.uniprot.core.xml.CrossReferenceConverterUtils.*;

/**
 * @author jluo
 * @date: 23 May 2019
 */
public class UniParcDBCrossReferenceConverter
        implements Converter<DbReferenceType, UniParcCrossReference> {
    private final ObjectFactory xmlFactory;
    private final TaxonomyRepo taxonomyRepo;

    public UniParcDBCrossReferenceConverter() {
        this(new ObjectFactory(), null);
    }

    public UniParcDBCrossReferenceConverter(ObjectFactory xmlFactory, TaxonomyRepo taxonomyRepo) {
        this.xmlFactory = xmlFactory;
        this.taxonomyRepo = taxonomyRepo;
    }

    @Override
    public UniParcCrossReference fromXml(DbReferenceType xmlObj) {
        UniParcCrossReferenceBuilder builder = new UniParcCrossReferenceBuilder();
        builder.database(UniParcDatabase.typeOf(xmlObj.getType()))
                .id(xmlObj.getId())
                .active(xmlObj.getActive().equals("Y"))
                .versionI(xmlObj.getVersionI())
                .created(XmlConverterHelper.dateFromXml(xmlObj.getCreated()))
                .lastUpdated(XmlConverterHelper.dateFromXml(xmlObj.getLast()));

        for (PropertyType property : xmlObj.getProperty()) {
            CrossReferenceConverterUtils.populateUniParcCrossReferenceBuilder(property.getType(), property.getValue(), builder, taxonomyRepo);
        }
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

        List<PropertyType> properties = new ArrayList<>();
        if (Utils.notNullNotEmpty(uniObj.getGeneName())) {
            properties.add(createProperty(PROPERTY_GENE_NAME, uniObj.getGeneName()));
        }
        if (Utils.notNullNotEmpty(uniObj.getProteinName())) {
            properties.add(createProperty(PROPERTY_PROTEIN_NAME, uniObj.getProteinName()));
        }
        if (Utils.notNullNotEmpty(uniObj.getChain())) {
            properties.add(createProperty(PROPERTY_CHAIN, uniObj.getChain()));
        }
        if (Utils.notNullNotEmpty(uniObj.getNcbiGi())) {
            properties.add(createProperty(PROPERTY_NCBI_GI, uniObj.getNcbiGi()));
        }
        if (Utils.notNullNotEmpty(uniObj.getProteomeIdComponents())) {
            for (ProteomeIdComponent proteomeIdComponent : uniObj.getProteomeIdComponents()) {
                properties.add(createProperty(PROPERTY_PROTEOMEID_COMPONENT, proteomeIdComponent));
            }

        }

        if (Utils.notNull(uniObj.getOrganism())) {
            String taxonId = String.valueOf(uniObj.getOrganism().getTaxonId());
            properties.add(createProperty(PROPERTY_NCBI_TAXONOMY_ID, taxonId));
        }
        xmlObj.getProperty().addAll(properties);
        if (Utils.notNullNotEmpty(uniObj.getProperties())) {
            uniObj.getProperties().stream()
                    .filter(prop -> !PROPERTY_SOURCES.equals(prop.getKey()))
                    .map(prop -> createProperty(prop.getKey(), prop.getValue()))
                    .forEach(val -> xmlObj.getProperty().add(val));
        }

        return xmlObj;
    }


    private PropertyType createProperty(String key, String value) {
        PropertyType xmlObj = xmlFactory.createPropertyType();
        xmlObj.setType(key);
        xmlObj.setValue(value);
        return xmlObj;
    }

    private PropertyType createProperty(String key, ProteomeIdComponent proteomeIdComponent) {
        PropertyType xmlObj = xmlFactory.createPropertyType();
        xmlObj.setType(key);
        xmlObj.setValue(proteomeIdComponent.getProteomeId() + ":" + proteomeIdComponent.getComponent());
        return xmlObj;
    }
}

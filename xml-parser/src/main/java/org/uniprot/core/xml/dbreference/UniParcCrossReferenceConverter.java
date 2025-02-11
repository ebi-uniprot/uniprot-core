package org.uniprot.core.xml.dbreference;

import org.uniprot.core.uniparc.UniParcCrossReference;
import org.uniprot.core.uniparc.UniParcDatabase;
import org.uniprot.core.uniparc.impl.UniParcCrossReferenceBuilder;
import org.uniprot.core.util.Utils;
import org.uniprot.core.xml.Converter;
import org.uniprot.core.xml.CrossReferenceConverterUtils;
import org.uniprot.core.xml.jaxb.dbreference.DbReference;
import org.uniprot.core.xml.jaxb.dbreference.ObjectFactory;
import org.uniprot.core.xml.jaxb.dbreference.PropertyType;
import org.uniprot.core.xml.uniprot.XmlConverterHelper;
import org.uniprot.cv.taxonomy.TaxonomyRepo;

import java.util.ArrayList;
import java.util.List;

import static org.uniprot.core.xml.CrossReferenceConverterUtils.*;

public class UniParcCrossReferenceConverter
        implements Converter<DbReference, UniParcCrossReference> {
    private final ObjectFactory xmlFactory;
    private final TaxonomyRepo taxonomyRepo;

    public UniParcCrossReferenceConverter() {
        this(new ObjectFactory(), null);
    }

    public UniParcCrossReferenceConverter(ObjectFactory xmlFactory, TaxonomyRepo taxonomyRepo) {
        this.xmlFactory = xmlFactory;
        this.taxonomyRepo = taxonomyRepo;
    }

    @Override
    public UniParcCrossReference fromXml(DbReference xmlObj) {
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
    public DbReference toXml(UniParcCrossReference uniObj) {
        DbReference xmlObj = xmlFactory.createDbReference();
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
        if (Utils.notNullNotEmpty(uniObj.getProteomeId())) {
            properties.add(createProperty(PROPERTY_PROTEOME_ID, uniObj.getProteomeId()));
        }
        if (Utils.notNullNotEmpty(uniObj.getComponent())) {
            properties.add(createProperty(PROPERTY_COMPONENT, uniObj.getComponent()));
        }
        if (Utils.notNull(uniObj.getOrganism())) {
            String taxonId = String.valueOf(uniObj.getOrganism().getTaxonId());
            properties.add(createProperty(PROPERTY_NCBI_TAXONOMY_ID, taxonId));
        }
        xmlObj.getProperty().addAll(properties);
        if (Utils.notNullNotEmpty(uniObj.getProperties())) {
            uniObj.getProperties().stream()
                    .filter(prop -> !SOURCES.equals(prop.getKey()))
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
}

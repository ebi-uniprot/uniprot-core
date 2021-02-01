package org.uniprot.core.xml.uniparc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.uniprot.core.uniparc.UniParcCrossReference;
import org.uniprot.core.uniparc.UniParcDatabase;
import org.uniprot.core.uniparc.impl.UniParcCrossReferenceBuilder;
import org.uniprot.core.uniprotkb.taxonomy.Organism;
import org.uniprot.core.uniprotkb.taxonomy.impl.OrganismBuilder;
import org.uniprot.core.util.Utils;
import org.uniprot.core.xml.Converter;
import org.uniprot.core.xml.XmlReaderException;
import org.uniprot.core.xml.jaxb.uniparc.DbReferenceType;
import org.uniprot.core.xml.jaxb.uniparc.ObjectFactory;
import org.uniprot.core.xml.jaxb.uniparc.PropertyType;
import org.uniprot.core.xml.uniprot.XmlConverterHelper;
import org.uniprot.cv.taxonomy.TaxonomicNode;
import org.uniprot.cv.taxonomy.TaxonomyRepo;

import com.google.common.base.Strings;

/**
 * @author jluo
 * @date: 23 May 2019
 */
public class UniParcDBCrossReferenceConverter
        implements Converter<DbReferenceType, UniParcCrossReference> {

    public static final String PROPERTY_GENE_NAME = "gene_name";
    public static final String PROPERTY_PROTEIN_NAME = "protein_name";
    public static final String PROPERTY_CHAIN = "chain";
    public static final String PROPERTY_NCBI_GI = "NCBI_GI";
    public static final String PROPERTY_PROTEOME_ID = "proteome_id";
    public static final String PROPERTY_COMPONENT = "component";
    public static final String PROPERTY_NCBI_TAXONOMY_ID = "NCBI_taxonomy_id";

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
            switch (property.getType()) {
                case PROPERTY_GENE_NAME:
                    builder.geneName(property.getValue());
                    break;
                case PROPERTY_PROTEIN_NAME:
                    builder.proteinName(property.getValue());
                    break;
                case PROPERTY_CHAIN:
                    builder.chain(property.getValue());
                    break;
                case PROPERTY_NCBI_GI:
                    builder.ncbiGi(property.getValue());
                    break;
                case PROPERTY_PROTEOME_ID:
                    builder.proteomeId(property.getValue());
                    break;
                case PROPERTY_COMPONENT:
                    builder.component(property.getValue());
                    break;
                case PROPERTY_NCBI_TAXONOMY_ID:
                    builder.taxonomy(convertTaxonomy(property.getValue()));
                    break;
                default:
                    throw new XmlReaderException(
                            "Unable to read xml property: "
                                    + xmlObj.getType()
                                    + "value: "
                                    + property.getValue());
            }
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
        if (Utils.notNullNotEmpty(uniObj.getProteomeId())) {
            properties.add(createProperty(PROPERTY_PROTEOME_ID, uniObj.getProteomeId()));
        }
        if (Utils.notNullNotEmpty(uniObj.getComponent())) {
            properties.add(createProperty(PROPERTY_COMPONENT, uniObj.getComponent()));
        }
        if (Utils.notNull(uniObj.getTaxonomy())) {
            String taxonId = String.valueOf(uniObj.getTaxonomy().getTaxonId());
            properties.add(createProperty(PROPERTY_NCBI_TAXONOMY_ID, taxonId));
        }
        xmlObj.getProperty().addAll(properties);

        return xmlObj;
    }

    private Organism convertTaxonomy(String taxId) {
        OrganismBuilder builder = new OrganismBuilder().taxonId(Long.parseLong(taxId));
        Optional<TaxonomicNode> opNode = getTaxonomyNode(taxId);
        if (opNode.isPresent()) {
            TaxonomicNode node = opNode.get();
            builder.scientificName(node.scientificName());
            if (!Strings.isNullOrEmpty(node.commonName())) {
                builder.commonName(node.commonName());
            }
            if (!Strings.isNullOrEmpty(node.synonymName())) {
                builder.synonymsAdd(node.synonymName());
            }
        }

        return builder.build();
    }

    private Optional<TaxonomicNode> getTaxonomyNode(String taxId) {
        if (taxonomyRepo == null) {
            return Optional.empty();
        } else return taxonomyRepo.retrieveNodeUsingTaxID(Integer.parseInt(taxId));
    }

    private PropertyType createProperty(String key, String value) {
        PropertyType xmlObj = xmlFactory.createPropertyType();
        xmlObj.setType(key);
        xmlObj.setValue(value);
        return xmlObj;
    }
}

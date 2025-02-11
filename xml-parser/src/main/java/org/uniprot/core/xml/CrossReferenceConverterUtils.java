package org.uniprot.core.xml;

import com.google.common.base.Strings;
import org.uniprot.core.uniparc.UniParcCrossReference;
import org.uniprot.core.uniparc.impl.UniParcCrossReferenceBuilder;
import org.uniprot.core.uniprotkb.taxonomy.Organism;
import org.uniprot.core.uniprotkb.taxonomy.impl.OrganismBuilder;
import org.uniprot.cv.taxonomy.TaxonomicNode;
import org.uniprot.cv.taxonomy.TaxonomyRepo;

import java.util.Optional;

public class CrossReferenceConverterUtils {
    public static final String PROPERTY_GENE_NAME = "gene_name";
    public static final String PROPERTY_PROTEIN_NAME = "protein_name";
    public static final String PROPERTY_CHAIN = "chain";
    public static final String PROPERTY_NCBI_GI = "NCBI_GI";
    public static final String PROPERTY_PROTEOME_ID = "proteome_id";
    public static final String PROPERTY_COMPONENT = "component";
    public static final String PROPERTY_NCBI_TAXONOMY_ID = "NCBI_taxonomy_id";
    public static final String PROPERTY_UNIPROTKB_ACCESSION = "UniProtKB_accession";
    public static final String PROPERTY_SOURCES = UniParcCrossReference.PROPERTY_SOURCES;
    public static final String SOURCES = "sources";

    private CrossReferenceConverterUtils(){}

    public static void populateUniParcCrossReferenceBuilder(String propertyType, String propertyValue, UniParcCrossReferenceBuilder builder, TaxonomyRepo taxonomyRepo) {
        switch (propertyType) {
            case PROPERTY_GENE_NAME:
                builder.geneName(propertyValue);
                break;
            case PROPERTY_PROTEIN_NAME:
                builder.proteinName(propertyValue);
                break;
            case PROPERTY_CHAIN:
                builder.chain(propertyValue);
                break;
            case PROPERTY_NCBI_GI:
                builder.ncbiGi(propertyValue);
                break;
            case PROPERTY_PROTEOME_ID:
                builder.proteomeId(propertyValue);
                break;
            case PROPERTY_COMPONENT:
                builder.component(propertyValue);
                break;
            case PROPERTY_NCBI_TAXONOMY_ID:
                builder.organism(CrossReferenceConverterUtils.convertTaxonomy(propertyValue, taxonomyRepo));
                break;
            case PROPERTY_UNIPROTKB_ACCESSION:
                builder.propertiesAdd(PROPERTY_UNIPROTKB_ACCESSION, propertyValue);
                break;
            case PROPERTY_SOURCES:
                builder.propertiesAdd(PROPERTY_SOURCES, propertyValue);
                break;
            default:
                throw new XmlReaderException(
                        "Unable to read xml property: "
                                + propertyType
                                + "value: "
                                + propertyValue);
        }
    }

    private static Organism convertTaxonomy(String taxId, TaxonomyRepo taxonomyRepo) {
        OrganismBuilder builder = new OrganismBuilder().taxonId(Long.parseLong(taxId));
        Optional<TaxonomicNode> opNode = getTaxonomyNode(taxId, taxonomyRepo);
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

    private static Optional<TaxonomicNode> getTaxonomyNode(String taxId, TaxonomyRepo taxonomyRepo) {
        if (taxonomyRepo == null) {
            return Optional.empty();
        } else return taxonomyRepo.retrieveNodeUsingTaxID(Integer.parseInt(taxId));
    }
}

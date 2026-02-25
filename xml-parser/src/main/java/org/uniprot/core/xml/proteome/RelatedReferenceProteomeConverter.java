package org.uniprot.core.xml.proteome;

import org.uniprot.core.proteome.RelatedProteome;
import org.uniprot.core.proteome.impl.ProteomeIdBuilder;
import org.uniprot.core.proteome.impl.RelatedProteomeBuilder;
import org.uniprot.core.uniprotkb.taxonomy.Taxonomy;
import org.uniprot.core.uniprotkb.taxonomy.impl.TaxonomyBuilder;
import org.uniprot.core.xml.Converter;
import org.uniprot.core.xml.jaxb.proteome.ObjectFactory;
import org.uniprot.core.xml.jaxb.proteome.RelatedReferenceProteome;

public class RelatedReferenceProteomeConverter implements Converter<RelatedReferenceProteome, RelatedProteome> {

    private ObjectFactory objectFactory;

    public RelatedReferenceProteomeConverter() {
        this(new ObjectFactory());
    }

    public RelatedReferenceProteomeConverter(ObjectFactory objectFactory) {
        this.objectFactory = objectFactory;
    }
    @Override
    public RelatedProteome fromXml(RelatedReferenceProteome xmlObj) {
        RelatedProteomeBuilder builder = new RelatedProteomeBuilder();
        builder.proteomeId(new ProteomeIdBuilder(xmlObj.getUpid()).build());
        builder.similarity(Float.valueOf(xmlObj.getSimilarity()));
        builder.taxonomy(getTaxonomy(xmlObj.getTaxon()));
        return builder.build();
    }

    @Override
    public RelatedReferenceProteome toXml(RelatedProteome uniObj) {
        RelatedReferenceProteome rrp = objectFactory.createRelatedReferenceProteome();
        rrp.setUpid(uniObj.getId().getValue());
        rrp.setSimilarity(String.valueOf(uniObj.getSimilarity()));
        rrp.setTaxon(Long.valueOf(uniObj.getTaxId().getTaxonId()));
        return rrp;
    }

    private Taxonomy getTaxonomy(Long taxonId) {
        if (taxonId != null && taxonId > 0L) {
            TaxonomyBuilder builder = new TaxonomyBuilder();
            return builder.taxonId(taxonId).build();
        } else {
            return null;
        }
    }
}

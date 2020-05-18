package org.uniprot.core.xml.feature.antigen;

import org.uniprot.core.antigen.AntigenEntry;
import org.uniprot.core.antigen.AntigenFeature;
import org.uniprot.core.antigen.impl.AntigenEntryBuilder;
import org.uniprot.core.uniprotkb.taxonomy.Organism;
import org.uniprot.core.uniprotkb.taxonomy.impl.OrganismBuilder;
import org.uniprot.core.util.Utils;
import org.uniprot.core.xml.Converter;
import org.uniprot.core.xml.feature.FeatureSequenceConverter;
import org.uniprot.core.xml.jaxb.feature.EntryFeature;
import org.uniprot.core.xml.jaxb.feature.FeatureType;
import org.uniprot.core.xml.jaxb.feature.ObjectFactory;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author lgonzales
 * @since 14/05/2020
 */
public class AntigenEntryConverter implements Converter<EntryFeature, AntigenEntry> {

    private final ObjectFactory xmlFactory;
    private final FeatureConverter antigenFeatureConverter;
    private final FeatureSequenceConverter sequenceConverter;

    public AntigenEntryConverter() {
        this(new ObjectFactory());
    }

    public AntigenEntryConverter(ObjectFactory xmlFactory) {
        this.xmlFactory = xmlFactory;
        this.antigenFeatureConverter = new FeatureConverter(xmlFactory);
        this.sequenceConverter = new FeatureSequenceConverter(xmlFactory);
    }

    @Override
    public AntigenEntry fromXml(EntryFeature xmlObj) {
        AntigenEntryBuilder builder = new AntigenEntryBuilder();
        if(Utils.notNullNotEmpty(xmlObj.getAccession())){
            builder.primaryAccession(xmlObj.getAccession());
        }
        if(Utils.notNullNotEmpty(xmlObj.getName())){
            builder.uniProtkbId(xmlObj.getName());
        }
        if(Utils.notNull(xmlObj.getTaxid()) || Utils.notNullNotEmpty(xmlObj.getOrganismName())) {
            OrganismBuilder organismBuilder = new OrganismBuilder();
            if (Utils.notNull(xmlObj.getTaxid())) {
                organismBuilder.taxonId(xmlObj.getTaxid());
            }
            if (Utils.notNullNotEmpty(xmlObj.getOrganismName())) {
                organismBuilder.scientificName(xmlObj.getOrganismName());
            }
            builder.organism(organismBuilder.build());
        }

        if(Utils.notNull(xmlObj.getSequence())){
            builder.sequence(sequenceConverter.fromXml(xmlObj.getSequence()));
        }

        if(Utils.notNull(xmlObj.getFeature())){
            List<AntigenFeature> features = xmlObj.getFeature().stream()
                    .map(antigenFeatureConverter::fromXml)
                    .collect(Collectors.toList());
            builder.featuresSet(features);
        }

        return builder.build();
    }

    @Override
    public EntryFeature toXml(AntigenEntry antigenObj) {
        EntryFeature xmlObj = xmlFactory.createEntryFeature();
        if(antigenObj.hasPrimaryAccession()) {
            xmlObj.setAccession(antigenObj.getPrimaryAccession().getValue());
        }

        if(antigenObj.hasUniProtkbId()) {
            xmlObj.setName(antigenObj.getUniProtkbId().getValue());
        }

        if(antigenObj.hasOrganism()) {
            Organism organism = antigenObj.getOrganism();
            xmlObj.setTaxid((int) organism.getTaxonId());
            if(organism.hasScientificName()){
                xmlObj.setOrganismName(organism.getScientificName());
            }
        }

        if(antigenObj.hasSequence()){
            xmlObj.setSequence(sequenceConverter.toXml(antigenObj.getSequence()));
        }

        if(antigenObj.hasFeatures()){
            List<FeatureType> features = antigenObj.getFeatures().stream()
                    .map(antigenFeatureConverter::toXml)
                    .collect(Collectors.toList());
            xmlObj.getFeature().addAll(features);
        }

        return xmlObj;
    }

}

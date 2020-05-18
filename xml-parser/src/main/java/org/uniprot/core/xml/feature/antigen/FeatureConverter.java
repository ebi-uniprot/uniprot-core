package org.uniprot.core.xml.feature.antigen;

import org.uniprot.core.CrossReference;
import org.uniprot.core.antigen.AntigenDatabase;
import org.uniprot.core.antigen.AntigenFeature;
import org.uniprot.core.antigen.AntigenFeatureType;
import org.uniprot.core.antigen.impl.AntigenFeatureBuilder;
import org.uniprot.core.uniprotkb.evidence.Evidence;
import org.uniprot.core.util.Utils;
import org.uniprot.core.xml.Converter;
import org.uniprot.core.xml.feature.FeatureEvidenceConverter;
import org.uniprot.core.xml.feature.FeatureLocationConverter;
import org.uniprot.core.xml.jaxb.feature.*;

import java.util.List;
import java.util.stream.Collectors;

public class FeatureConverter implements Converter<FeatureType, AntigenFeature> {

    private final ObjectFactory xmlFactory;
    private final FeatureLocationConverter locationConverter;
    private final FeatureEvidenceConverter evidenceConverter;
    private final CrossReferenceConverter crossReferenceConverter;

    public FeatureConverter() {
        this(new ObjectFactory());
    }

    public FeatureConverter(ObjectFactory xmlFactory) {
        this.xmlFactory = xmlFactory;
        this.locationConverter = new FeatureLocationConverter(xmlFactory);
        this.evidenceConverter = new FeatureEvidenceConverter(xmlFactory);
        this.crossReferenceConverter = new CrossReferenceConverter(xmlFactory);
    }

    @Override
    public AntigenFeature fromXml(FeatureType xmlObj) {
        AntigenFeatureBuilder builder = new AntigenFeatureBuilder();
        if(Utils.notNull(xmlObj.getType())){
            builder.type(AntigenFeatureType.typeOf(xmlObj.getType()));
        }

        if(Utils.notNull(xmlObj.getLocation())) {
            builder.location(locationConverter.fromXml(xmlObj.getLocation()));
        }

        if (Utils.notNullNotEmpty(xmlObj.getDescription())) {
            String matchScore = xmlObj.getDescription().replace("%", "");
            builder.matchScore(Integer.parseInt(matchScore));
        }

        if(Utils.notNullNotEmpty(xmlObj.getXrefs())){
            CrossReference<AntigenDatabase> features = crossReferenceConverter.fromXml(xmlObj.getXrefs().get(0));
            builder.featureCrossReference(features);
        }

        if (Utils.notNullNotEmpty(xmlObj.getEvidence())) {
            List<Evidence> evidences = xmlObj.getEvidence().stream()
                    .map(evidenceConverter::fromXml)
                    .collect(Collectors.toList());
            builder.evidencesSet(evidences);
        }

        if(Utils.notNull(xmlObj.getPeptide()) &&
                Utils.notNullNotEmpty(xmlObj.getPeptide().getPeptideSequence())){
            builder.antigenSequence(xmlObj.getPeptide().getPeptideSequence());
        }

        return builder.build();
    }

    @Override
    public FeatureType toXml(AntigenFeature uniObj) {
        FeatureType xmlFeature = xmlFactory.createFeatureType();

        // feature type
        if (Utils.notNull(uniObj.getType())) {
            xmlFeature.setType(uniObj.getType().getValue());
        }
        if(uniObj.hasLocation()) {
            xmlFeature.setLocation(locationConverter.toXml(uniObj.getLocation()));
        }

        if (uniObj.hasMatchScore()) {
            xmlFeature.setDescription(uniObj.getMatchScore() + "%");
        }

        if(uniObj.hasFeatureCrossReference()){
            DbReferenceType crossReference = crossReferenceConverter.toXml(uniObj.getFeatureCrossReference());
            xmlFeature.getXrefs().add(crossReference);
        }

        // feature xml evidence tags set
        if (uniObj.hasEvidences()) {
            List<EvidenceType> features = uniObj.getEvidences().stream()
                    .map(evidenceConverter::toXml)
                    .collect(Collectors.toList());
            xmlFeature.getEvidence().addAll(features);
        }

        if(uniObj.hasAntigenSequence()){
            PeptideType peptideType = xmlFactory.createPeptideType();
            peptideType.setPeptideSequence(uniObj.getAntigenSequence());
            xmlFeature.setPeptide(peptideType);
        }
        return xmlFeature;
    }

}

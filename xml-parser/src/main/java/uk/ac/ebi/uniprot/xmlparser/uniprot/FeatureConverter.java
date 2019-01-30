package uk.ac.ebi.uniprot.xmlparser.uniprot;

import com.google.common.base.Strings;
import uk.ac.ebi.uniprot.domain.Range;
import uk.ac.ebi.uniprot.domain.uniprot.evidence2.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.feature.AlternativeSequence;
import uk.ac.ebi.uniprot.domain.uniprot.feature.AlternativeSequenceHelper;
import uk.ac.ebi.uniprot.domain.uniprot.feature.Feature;
import uk.ac.ebi.uniprot.domain.uniprot.feature.FeatureId;
import uk.ac.ebi.uniprot.domain.uniprot.feature.builder.AlternativeSequenceBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.feature.builder.FeatureBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.feature.builder.FeatureIdBuilder;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.FeatureType;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.ObjectFactory;
import uk.ac.ebi.uniprot.xmlparser.Converter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FeatureConverter implements Converter<FeatureType, Feature> {

    private static final String STOP = ".";
    private final EvidenceIndexMapper evRefMapper;
    private final ObjectFactory xmlUniprotFactory;
    private final FeatureLocationConverter locationConverter;

    public FeatureConverter(EvidenceIndexMapper evRefMapper) {
        this(evRefMapper, new ObjectFactory());
    }

    public FeatureConverter(EvidenceIndexMapper evRefMapper,
                            ObjectFactory xmlUniprotFactory) {
        this.locationConverter = new FeatureLocationConverter(xmlUniprotFactory);
        this.evRefMapper = evRefMapper;
        this.xmlUniprotFactory = xmlUniprotFactory;

    }

    @Override
    public Feature fromXml(FeatureType xmlObj) {
        uk.ac.ebi.uniprot.domain.uniprot.feature.FeatureType type = uk.ac.ebi.uniprot.domain.uniprot.feature.FeatureType
                .typeOf(xmlObj.getType());
        String description = "";
        if (xmlObj.getDescription() != null) {
            description = xmlObj.getDescription();
            if (AlternativeSequenceHelper.hasAlternativeSequence(type)) {
                description = XmlConverterHelper.removeIfPostfix(description, STOP);
                if (type != uk.ac.ebi.uniprot.domain.uniprot.feature.FeatureType.MUTAGEN)
                    description = XmlConverterHelper.lowercaseFirstLetter(description);
            }
        }

        Range location = locationConverter.fromXml(xmlObj.getLocation());
        List<Evidence> evidences = evRefMapper.parseEvidenceIds(xmlObj.getEvidence());
        String ftid = xmlObj.getId();
        FeatureId featureId = null;
        if (!Strings.isNullOrEmpty(ftid)) {
            featureId = new FeatureIdBuilder(ftid).build();
        }
        AlternativeSequence altSeq = null;
        if (!Strings.isNullOrEmpty(xmlObj.getOriginal())) {
            altSeq = new AlternativeSequenceBuilder()
                    .original(xmlObj.getOriginal())
                    .alternatives(xmlObj.getVariation())
                    .build();
        } else if (AlternativeSequenceHelper.hasAlternativeSequence(type)) {
            altSeq = new AlternativeSequenceBuilder()
                    .original("")
                    .alternatives(Collections.emptyList())
                    .build();
        }

        return new FeatureBuilder()
                .type(type)
                .location(location)
                .featureId(featureId)
                .description(description)
                .alternativeSequence(altSeq)
                .evidences(evidences)
                .build();
    }

    @Override
    public FeatureType toXml(Feature uniObj) {
        FeatureType xmlFeature = xmlUniprotFactory.createFeatureType();

        // feature type
        if (uniObj.getType() != null) {
            xmlFeature.setType(uniObj.getType().getValue());
        }
        xmlFeature.setLocation(locationConverter.toXml(uniObj.getLocation()));
        if ((uniObj.getFeatureId() != null) && !Strings.isNullOrEmpty(uniObj.getFeatureId().getValue())) {
            xmlFeature.setId(uniObj.getFeatureId().getValue());
        }
        if ((uniObj.getDescription() != null) && !uniObj.getDescription().getValue().isEmpty()) {
            String val = uniObj.getDescription().getValue();
            if (AlternativeSequenceHelper.hasAlternativeSequence(uniObj.getType())) {
                val = XmlConverterHelper.addIfNoPostfix(val, STOP);

                if (uniObj.getType() != uk.ac.ebi.uniprot.domain.uniprot.feature.FeatureType.MUTAGEN)
                    val = XmlConverterHelper.uppercaseFirstLetter(val);
            }
            xmlFeature.setDescription(val);
        }
        // feature xml evidence tags set
        if (!uniObj.getEvidences().isEmpty()) {
            List<Integer> evs = evRefMapper.writeEvidences(uniObj.getEvidences());
            if (!evs.isEmpty())
                xmlFeature.getEvidence().addAll(evs);
        }
        if (uniObj.getAlternativeSequence() != null) {
            AlternativeSequence altSeq = uniObj.getAlternativeSequence();
            if (!Strings.isNullOrEmpty(altSeq.getOriginalSequence()))
                xmlFeature.setOriginal(altSeq.getOriginalSequence());
            if (!altSeq.getAlternativeSequences().isEmpty())
                xmlFeature.getVariation().addAll(altSeq.getAlternativeSequences());
        }
        updateConflictFeature(xmlFeature, uniObj);
        return xmlFeature;
    }

    private void updateConflictFeature(FeatureType featureType, Feature feature) {
        if (feature.getType() != uk.ac.ebi.uniprot.domain.uniprot.feature.FeatureType.CONFLICT) {
            return;
        }
        if ((feature.getDescription() == null) || Strings.isNullOrEmpty(feature.getDescription().getValue())) {
            return;
        }
        String description = feature.getDescription().getValue();
        List<Integer> refs = extractConflictReference(description);
        StringBuilder sb = new StringBuilder();
        for (Integer reference : refs) {
            sb.append(reference);
            sb.append(" ");

        }
        featureType.setRef(sb.toString().trim());
    }

    public static List<Integer> extractConflictReference(String description) {
        String regex = ", | and ";
        List<Integer> refs = new ArrayList<>();
        String[] tokens = description.split(regex);
        for (int i = 0; i < tokens.length; i++) {
            String token = tokens[i].trim();
            int index = token.lastIndexOf(";");
            if (index != -1) {
                token = token.substring(0, index).trim();
            }
            index = token.lastIndexOf(" ");
            if (index != -1) {
                token = token.substring(index + 1).trim();
            }
            refs.add(Integer.parseInt(token));

        }

        return refs;
    }
}

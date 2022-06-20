package org.uniprot.core.xml.uniprot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.uniprot.core.feature.FeatureLocation;
import org.uniprot.core.uniprotkb.evidence.Evidence;
import org.uniprot.core.uniprotkb.feature.*;
import org.uniprot.core.uniprotkb.feature.AlternativeSequence;
import org.uniprot.core.uniprotkb.feature.FeatureId;
import org.uniprot.core.uniprotkb.feature.impl.AlternativeSequenceBuilder;
import org.uniprot.core.uniprotkb.feature.impl.FeatureIdBuilder;
import org.uniprot.core.uniprotkb.feature.impl.UniProtKBFeatureBuilder;
import org.uniprot.core.util.Utils;
import org.uniprot.core.xml.Converter;
import org.uniprot.core.xml.jaxb.uniprot.FeatureType;
import org.uniprot.core.xml.jaxb.uniprot.ObjectFactory;

import com.google.common.base.Strings;

public class FeatureConverter implements Converter<FeatureType, UniProtKBFeature> {

    private static final String STOP = ".";
    private final EvidenceIndexMapper evRefMapper;
    private final ObjectFactory xmlUniprotFactory;
    private final FeatureLocationConverter locationConverter;
    private final LigandConverter ligandConverter;
    private final LigandPartConverter ligandPartConverter;

    public FeatureConverter(EvidenceIndexMapper evRefMapper) {
        this(evRefMapper, new ObjectFactory());
    }

    public FeatureConverter(EvidenceIndexMapper evRefMapper, ObjectFactory xmlUniprotFactory) {
        this.locationConverter = new FeatureLocationConverter(xmlUniprotFactory);
        this.evRefMapper = evRefMapper;
        this.xmlUniprotFactory = xmlUniprotFactory;
        this.ligandConverter = new LigandConverter(xmlUniprotFactory);
        this.ligandPartConverter = new LigandPartConverter(xmlUniprotFactory);
    }

    @Override
    public UniProtKBFeature fromXml(FeatureType xmlObj) {
        UniprotKBFeatureType type = UniprotKBFeatureType.typeOf(xmlObj.getType());
        String description = "";
        if (xmlObj.getDescription() != null) {
            description = xmlObj.getDescription();
            if (UniprotKBAlternativeSequenceHelper.hasAlternativeSequence(type)) {
                description = XmlConverterHelper.removeIfPostfix(description, STOP);
                if (type != UniprotKBFeatureType.MUTAGEN)
                    description = XmlConverterHelper.lowercaseFirstLetter(description);
            }
        }

        FeatureLocation location = locationConverter.fromXml(xmlObj.getLocation());
        List<Evidence> evidences = evRefMapper.parseEvidenceIds(xmlObj.getEvidence());
        String ftid = xmlObj.getId();
        FeatureId featureId = null;
        if (!Strings.isNullOrEmpty(ftid)) {
            featureId = new FeatureIdBuilder(ftid).build();
        }
        AlternativeSequence altSeq = null;
        if (!Strings.isNullOrEmpty(xmlObj.getOriginal())) {
            altSeq =
                    new AlternativeSequenceBuilder()
                            .original(xmlObj.getOriginal())
                            .alternativeSequencesSet(xmlObj.getVariation())
                            .build();
        } else if (UniprotKBAlternativeSequenceHelper.hasAlternativeSequence(type)) {
            altSeq =
                    new AlternativeSequenceBuilder()
                            .original("")
                            .alternativeSequencesSet(Collections.emptyList())
                            .build();
        }
        Ligand ligand = null;
        if (Utils.notNull(xmlObj.getLigand())) {
            ligand = this.ligandConverter.fromXml(xmlObj.getLigand());
        }
        LigandPart ligandPart = null;
        if (Utils.notNull(xmlObj.getLigandPart())) {
            ligandPart = this.ligandPartConverter.fromXml(xmlObj.getLigandPart());
        }
        return new UniProtKBFeatureBuilder()
                .type(type)
                .location(location)
                .featureId(featureId)
                .description(description)
                .alternativeSequence(altSeq)
                .evidencesSet(evidences)
                .ligand(ligand)
                .ligandPart(ligandPart)
                .build();
    }

    @Override
    public FeatureType toXml(UniProtKBFeature uniObj) {
        FeatureType xmlFeature = xmlUniprotFactory.createFeatureType();

        // feature type
        if (uniObj.getType() != null) {
            xmlFeature.setType(uniObj.getType().getValue());
        }
        xmlFeature.setLocation(locationConverter.toXml(uniObj.getLocation()));
        if ((uniObj.getFeatureId() != null)
                && !Strings.isNullOrEmpty(uniObj.getFeatureId().getValue())) {
            xmlFeature.setId(uniObj.getFeatureId().getValue());
        }
        if ((uniObj.getDescription() != null) && !uniObj.getDescription().getValue().isEmpty()) {
            String val = uniObj.getDescription().getValue();
            if (UniprotKBAlternativeSequenceHelper.hasAlternativeSequence(uniObj.getType())) {
                val = XmlConverterHelper.addIfNoPostfix(val, STOP);

                if (uniObj.getType() != UniprotKBFeatureType.MUTAGEN)
                    val = XmlConverterHelper.uppercaseFirstLetter(val);
            }
            xmlFeature.setDescription(val);
        }
        // feature xml evidence tags set
        if (!uniObj.getEvidences().isEmpty()) {
            List<Integer> evs = evRefMapper.writeEvidences(uniObj.getEvidences());
            if (!evs.isEmpty()) xmlFeature.getEvidence().addAll(evs);
        }
        if (uniObj.getAlternativeSequence() != null) {
            AlternativeSequence altSeq = uniObj.getAlternativeSequence();
            if (!Strings.isNullOrEmpty(altSeq.getOriginalSequence()))
                xmlFeature.setOriginal(altSeq.getOriginalSequence());
            if (!altSeq.getAlternativeSequences().isEmpty())
                xmlFeature.getVariation().addAll(altSeq.getAlternativeSequences());
        }
        if (Utils.notNull(uniObj.getLigand())) {
            xmlFeature.setLigand(ligandConverter.toXml(uniObj.getLigand()));
        }
        if (Utils.notNull(uniObj.getLigandPart())) {
            xmlFeature.setLigandPart(ligandPartConverter.toXml(uniObj.getLigandPart()));
        }

        updateConflictFeature(xmlFeature, uniObj);
        return xmlFeature;
    }

    private void updateConflictFeature(FeatureType featureType, UniProtKBFeature feature) {
        if (feature.getType() != UniprotKBFeatureType.CONFLICT) {
            return;
        }
        if ((feature.getDescription() == null)
                || Strings.isNullOrEmpty(feature.getDescription().getValue())) {
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

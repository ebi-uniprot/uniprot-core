package org.uniprot.core.xml.unirule;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.uniprot.core.uniprotkb.description.FlagType;
import org.uniprot.core.uniprotkb.description.ProteinDescription;
import org.uniprot.core.uniprotkb.description.impl.ProteinDescriptionBuilder;
import org.uniprot.core.xml.Converter;
import org.uniprot.core.xml.jaxb.uniprot.EvidencedStringType;
import org.uniprot.core.xml.jaxb.unirule.ObjectFactory;
import org.uniprot.core.xml.jaxb.unirule.ProteinType;
import org.uniprot.core.xml.uniprot.EvidenceIndexMapper;
import org.uniprot.core.xml.uniprot.description.ProteinDescriptionConverter;

public class ProteinConverter implements Converter<ProteinType, ProteinDescription> {
    private final ObjectFactory uniRuleObjectFactory;
    private final org.uniprot.core.xml.jaxb.uniprot.ObjectFactory uniProtObjectFactory;
    private final EvidenceIndexMapper evRefMapper;
    private final ProteinDescriptionConverter proteinConverter;

    public ProteinConverter() {
        this(new ObjectFactory());
    }

    public ProteinConverter(ObjectFactory objectFactory) {
        this.uniRuleObjectFactory = objectFactory;
        this.uniProtObjectFactory = new org.uniprot.core.xml.jaxb.uniprot.ObjectFactory();
        this.evRefMapper = new EvidenceIndexMapper();
        this.proteinConverter =
                new ProteinDescriptionConverter(this.evRefMapper, uniProtObjectFactory);
    }

    @Override
    public ProteinDescription fromXml(ProteinType xmlObj) {
        if (Objects.isNull(xmlObj)) return null;
        org.uniprot.core.xml.jaxb.uniprot.ProteinType uniProtProteinType =
                this.uniProtObjectFactory.createProteinType();
        org.uniprot.core.xml.jaxb.uniprot.ProteinType.RecommendedName uniProtRecName =
                toUniProtType(xmlObj.getRecommendedName());
        uniProtProteinType.setRecommendedName(uniProtRecName);

        uniProtProteinType
                .getAlternativeName()
                .addAll(toUniProtTypeList(xmlObj.getAlternativeName()));

        uniProtProteinType.setAllergenName(xmlObj.getAllergenName());
        uniProtProteinType.setBiotechName(xmlObj.getBiotechName());
        uniProtProteinType.getCdAntigenName().addAll(xmlObj.getCdAntigenName());
        uniProtProteinType.getInnName().addAll(xmlObj.getInnName());

        List<org.uniprot.core.xml.jaxb.uniprot.ProteinType.Domain> uniProtDomains =
                xmlObj.getDomain().stream().map(this::toUniProtType).collect(Collectors.toList());
        uniProtProteinType.getDomain().addAll(uniProtDomains);

        List<org.uniprot.core.xml.jaxb.uniprot.ProteinType.Component> uniProtComps =
                xmlObj.getComponent().stream()
                        .map(this::toUniProtType)
                        .collect(Collectors.toList());
        uniProtProteinType.getComponent().addAll(uniProtComps);

        ProteinDescription proteinDescription = this.proteinConverter.fromXml(uniProtProteinType);

        if (Objects.nonNull(xmlObj.getFlag())) { // inject flag
            ProteinDescriptionBuilder builder = ProteinDescriptionBuilder.from(proteinDescription);
            String flag =
                    xmlObj.getFlag()
                            .getValue()
                            .get(0); // some issue with UniRule schema file, it will always have
            // PreCursor
            builder.flag(FlagType.typeOf(flag));
            proteinDescription = builder.build();
        }

        return proteinDescription;
    }

    @Override
    public ProteinType toXml(ProteinDescription uniObj) {
        if (Objects.isNull(uniObj)) return null;
        org.uniprot.core.xml.jaxb.uniprot.ProteinType uniProtProteinType =
                this.proteinConverter.toXml(uniObj);

        ProteinType proteinType = this.uniRuleObjectFactory.createProteinType();
        ProteinType.RecommendedName recommendedName =
                toUniRuleType(uniProtProteinType.getRecommendedName());
        proteinType.setRecommendedName(recommendedName);

        proteinType
                .getAlternativeName()
                .addAll(toUniRuleTypeList(uniProtProteinType.getAlternativeName()));

        proteinType.setAllergenName(uniProtProteinType.getAllergenName());
        proteinType.setBiotechName(uniProtProteinType.getBiotechName());
        proteinType.getCdAntigenName().addAll(uniProtProteinType.getCdAntigenName());
        proteinType.getInnName().addAll(uniProtProteinType.getInnName());

        List<ProteinType.Domain> domains =
                uniProtProteinType.getDomain().stream()
                        .map(this::toUniRuleType)
                        .collect(Collectors.toList());
        proteinType.getDomain().addAll(domains);

        List<ProteinType.Component> components =
                uniProtProteinType.getComponent().stream()
                        .map(this::toUniRuleType)
                        .collect(Collectors.toList());
        proteinType.getComponent().addAll(components);

        if (Objects.nonNull(uniObj.getFlag())) { // inject flag
            ProteinType.Flag flag = this.uniRuleObjectFactory.createProteinTypeFlag();
            flag.getValue().add(uniObj.getFlag().getType().getDisplayName());
            proteinType.setFlag(flag);
        }

        return proteinType;
    }

    private org.uniprot.core.xml.jaxb.uniprot.ProteinType.Domain toUniProtType(
            ProteinType.Domain domain) {
        if (Objects.isNull(domain)) return null;

        org.uniprot.core.xml.jaxb.uniprot.ProteinType.Domain uniProtDom =
                this.uniProtObjectFactory.createProteinTypeDomain();

        uniProtDom.setRecommendedName(toUniProtType(domain.getRecommendedName()));
        uniProtDom.getAlternativeName().addAll(toUniProtTypeList(domain.getAlternativeName()));
        uniProtDom.setAllergenName(domain.getAllergenName());
        uniProtDom.setBiotechName(domain.getBiotechName());
        uniProtDom.getCdAntigenName().addAll(domain.getCdAntigenName());
        uniProtDom.getInnName().addAll(domain.getInnName());

        return uniProtDom;
    }

    private ProteinType.Domain toUniRuleType(
            org.uniprot.core.xml.jaxb.uniprot.ProteinType.Domain domain) {
        if (Objects.isNull(domain)) return null;
        ProteinType.Domain uniRuleDom = this.uniRuleObjectFactory.createProteinTypeDomain();

        uniRuleDom.setRecommendedName(toUniRuleType(domain.getRecommendedName()));
        uniRuleDom.getAlternativeName().addAll(toUniRuleTypeList(domain.getAlternativeName()));
        uniRuleDom.setAllergenName(domain.getAllergenName());
        uniRuleDom.setBiotechName(domain.getBiotechName());
        uniRuleDom.getCdAntigenName().addAll(domain.getCdAntigenName());
        uniRuleDom.getInnName().addAll(domain.getInnName());

        return uniRuleDom;
    }

    private org.uniprot.core.xml.jaxb.uniprot.ProteinType.Component toUniProtType(
            ProteinType.Component component) {
        if (Objects.isNull(component)) return null;
        org.uniprot.core.xml.jaxb.uniprot.ProteinType.Component uniProtComp =
                this.uniProtObjectFactory.createProteinTypeComponent();

        uniProtComp.setRecommendedName(toUniProtType(component.getRecommendedName()));
        uniProtComp.getAlternativeName().addAll(toUniProtTypeList(component.getAlternativeName()));
        uniProtComp.setAllergenName(component.getAllergenName());
        uniProtComp.setBiotechName(component.getBiotechName());
        uniProtComp.getCdAntigenName().addAll(component.getCdAntigenName());
        uniProtComp.getInnName().addAll(component.getInnName());

        return uniProtComp;
    }

    private ProteinType.Component toUniRuleType(
            org.uniprot.core.xml.jaxb.uniprot.ProteinType.Component component) {
        if (Objects.isNull(component)) return null;
        ProteinType.Component uniRuleComp = this.uniRuleObjectFactory.createProteinTypeComponent();
        uniRuleComp.setRecommendedName(toUniRuleType(component.getRecommendedName()));
        uniRuleComp.getAlternativeName().addAll(toUniRuleTypeList(component.getAlternativeName()));
        uniRuleComp.setAllergenName(component.getAllergenName());
        uniRuleComp.setBiotechName(component.getBiotechName());
        uniRuleComp.getCdAntigenName().addAll(component.getCdAntigenName());
        uniRuleComp.getInnName().addAll(component.getInnName());

        return uniRuleComp;
    }

    private List<ProteinType.AlternativeName> toUniRuleTypeList(
            List<org.uniprot.core.xml.jaxb.uniprot.ProteinType.AlternativeName> alternativeNames) {
        return alternativeNames.stream().map(this::toUniRuleType).collect(Collectors.toList());
    }

    private List<org.uniprot.core.xml.jaxb.uniprot.ProteinType.AlternativeName> toUniProtTypeList(
            List<ProteinType.AlternativeName> alternativeNames) {
        return alternativeNames.stream().map(this::toUniProtType).collect(Collectors.toList());
    }

    private org.uniprot.core.xml.jaxb.uniprot.ProteinType.AlternativeName toUniProtType(
            ProteinType.AlternativeName alternativeName) {
        if (Objects.isNull(alternativeName)) return null;
        org.uniprot.core.xml.jaxb.uniprot.ProteinType.AlternativeName uniProtAltName =
                this.uniProtObjectFactory.createProteinTypeAlternativeName();
        uniProtAltName.setFullName(alternativeName.getFullName());
        uniProtAltName.getShortName().addAll(alternativeName.getShortName());
        uniProtAltName.getEcNumber().addAll(alternativeName.getEcNumber());
        return uniProtAltName;
    }

    private ProteinType.AlternativeName toUniRuleType(
            org.uniprot.core.xml.jaxb.uniprot.ProteinType.AlternativeName alternativeName) {
        if (Objects.isNull(alternativeName)) return null;
        ProteinType.AlternativeName uniRuleAltName =
                this.uniRuleObjectFactory.createProteinTypeAlternativeName();
        uniRuleAltName.setFullName(alternativeName.getFullName());
        uniRuleAltName.getShortName().addAll(alternativeName.getShortName());
        uniRuleAltName.getEcNumber().addAll(alternativeName.getEcNumber());
        return uniRuleAltName;
    }

    private org.uniprot.core.xml.jaxb.uniprot.ProteinType.RecommendedName toUniProtType(
            ProteinType.RecommendedName recommendedName) {
        if (Objects.isNull(recommendedName)) return null;
        org.uniprot.core.xml.jaxb.uniprot.ProteinType.RecommendedName uniProtRecName =
                this.uniProtObjectFactory.createProteinTypeRecommendedName();
        // full name is mandatory in UniProt's ProteinType.RecommendedName but not in UniRule's
        // ProteinType.RecommendedName. Set empty if missing to satisfy the condition.
        if (Objects.isNull(recommendedName.getFullName())) {
            EvidencedStringType evidenceType =
                    this.uniProtObjectFactory.createEvidencedStringType();
            evidenceType.setValue("");
            uniProtRecName.setFullName(evidenceType);
        } else {
            uniProtRecName.setFullName(recommendedName.getFullName());
        }
        uniProtRecName.getShortName().addAll(recommendedName.getShortName());
        uniProtRecName.getEcNumber().addAll(recommendedName.getEcNumber());

        return uniProtRecName;
    }

    private ProteinType.RecommendedName toUniRuleType(
            org.uniprot.core.xml.jaxb.uniprot.ProteinType.RecommendedName recommendedName) {
        if (Objects.isNull(recommendedName)) return null;

        ProteinType.RecommendedName uniRuleRecName =
                this.uniRuleObjectFactory.createProteinTypeRecommendedName();
        uniRuleRecName.setFullName(recommendedName.getFullName());
        uniRuleRecName.getShortName().addAll(recommendedName.getShortName());
        uniRuleRecName.getEcNumber().addAll(recommendedName.getEcNumber());

        return uniRuleRecName;
    }
}

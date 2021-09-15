package org.uniprot.core.xml.unirule;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.uniprot.core.unirule.*;
import org.uniprot.core.unirule.impl.UniRuleEntryBuilder;
import org.uniprot.core.unirule.impl.UniRuleIdBuilder;
import org.uniprot.core.util.Utils;
import org.uniprot.core.xml.Converter;
import org.uniprot.core.xml.jaxb.unirule.*;
import org.uniprot.core.xml.uniprot.XmlConverterHelper;

public class UniRuleEntryConverter implements Converter<UniRuleType, UniRuleEntry> {
    private final ObjectFactory objectFactory;
    private final InformationConverter informationConverter;
    private final MainTypeConverter mainTypeConverter;
    private final CaseTypeConverter caseTypeConverter;
    private final SamFeatureSetConverter samFeatureSetConverter;
    private final PositionalFeatureSetConverter positionalFeatureSetConverter;

    public UniRuleEntryConverter() {
        this(new ObjectFactory());
    }

    public UniRuleEntryConverter(ObjectFactory objectFactory) {
        this.objectFactory = objectFactory;
        this.informationConverter = new InformationConverter(objectFactory);
        this.mainTypeConverter = new MainTypeConverter(objectFactory);
        this.caseTypeConverter = new CaseTypeConverter(objectFactory);
        this.samFeatureSetConverter = new SamFeatureSetConverter(objectFactory);
        this.positionalFeatureSetConverter = new PositionalFeatureSetConverter(objectFactory);
    }

    @Override
    public UniRuleEntry fromXml(UniRuleType xmlObj) {
        if (Objects.isNull(xmlObj)) return null;

        UniRuleId uniRuleId = new UniRuleIdBuilder(xmlObj.getId()).build();
        Information information = this.informationConverter.fromXml(xmlObj.getInformation());
        Rule rule = this.mainTypeConverter.fromXml(xmlObj.getMain());

        UniRuleEntryBuilder builder =
                new UniRuleEntryBuilder(uniRuleId, information, rule);

        if (Objects.nonNull(xmlObj.getCases())) {
            List<CaseRule> caseRules =
                    xmlObj.getCases().getCase().stream()
                            .map(this.caseTypeConverter::fromXml)
                            .collect(Collectors.toList());
            builder.otherRulesSet(caseRules);
        }

        List<SamFeatureSet> samFeatureSets =
                xmlObj.getSamFeatureSet().stream()
                        .map(this.samFeatureSetConverter::fromXml)
                        .collect(Collectors.toList());
        builder.samFeatureSetsSet(samFeatureSets);

        List<PositionFeatureSet> positionFeatureSets =
                xmlObj.getPositionalFeatureSet().stream()
                        .map(this.positionalFeatureSetConverter::fromXml)
                        .collect(Collectors.toList());
        positionFeatureSets.removeAll(Collections.singleton(null));
        if (!positionFeatureSets.isEmpty()) {
            builder.positionFeatureSetsSet(positionFeatureSets);
        }
        builder.createdDate(XmlConverterHelper.dateFromXml(xmlObj.getCreated()));
        builder.modifiedDate(XmlConverterHelper.dateFromXml(xmlObj.getModified()));

        return builder.build();
    }

    @Override
    public UniRuleType toXml(UniRuleEntry uniObj) {
        if (Objects.isNull(uniObj)) return null;

        UniRuleType xmlObj = this.objectFactory.createUniRuleType();
        InformationType informationType = this.informationConverter.toXml(uniObj.getInformation());
        xmlObj.setInformation(informationType);
        MainType mainType = this.mainTypeConverter.toXml(uniObj.getMainRule());
        xmlObj.setMain(mainType);
        if (Utils.notNullNotEmpty(uniObj.getOtherRules())) {
            List<CaseType> caseTypes =
                    uniObj.getOtherRules().stream()
                            .map(this.caseTypeConverter::toXml)
                            .collect(Collectors.toList());
            CasesType cases = this.objectFactory.createCasesType();
            cases.getCase().addAll(caseTypes);
            xmlObj.setCases(cases);
        }

        if (Utils.notNullNotEmpty(uniObj.getPositionFeatureSets())) {
            List<SamFeatureSetType> samFeatureSetTypes =
                    uniObj.getSamFeatureSets().stream()
                            .map(this.samFeatureSetConverter::toXml)
                            .collect(Collectors.toList());
            xmlObj.getSamFeatureSet().addAll(samFeatureSetTypes);
        }

        if (Utils.notNullNotEmpty(uniObj.getPositionFeatureSets())) {
            List<PositionalFeatureSetType> positionalFeatureSetTypes =
                    uniObj.getPositionFeatureSets().stream()
                            .map(this.positionalFeatureSetConverter::toXml)
                            .collect(Collectors.toList());
            xmlObj.getPositionalFeatureSet().addAll(positionalFeatureSetTypes);
        }

        xmlObj.setId(uniObj.getUniRuleId().getValue());
        xmlObj.setCreated(XmlConverterHelper.dateToXml(uniObj.getCreatedDate()));
        xmlObj.setModified(XmlConverterHelper.dateToXml(uniObj.getModifiedDate()));

        return xmlObj;
    }
}

package org.uniprot.core.xml.unirule;

import org.uniprot.core.unirule.*;
import org.uniprot.core.unirule.impl.UniRuleEntryBuilder;
import org.uniprot.core.unirule.impl.UniRuleIdBuilder;
import org.uniprot.core.xml.Converter;
import org.uniprot.core.xml.jaxb.unirule.ObjectFactory;
import org.uniprot.core.xml.jaxb.unirule.UniRuleType;
import org.uniprot.core.xml.uniprot.XmlConverterHelper;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class UniRuleEntryConverter implements Converter<UniRuleType, UniRuleEntry> {
    private final ObjectFactory objectFactory;
    private final InformationConverter informationConverter;
    private final RuleStatusConverter ruleStatusConverter;
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
        this.ruleStatusConverter = new RuleStatusConverter(objectFactory);
        this.mainTypeConverter = new MainTypeConverter(objectFactory);
        this.caseTypeConverter = new CaseTypeConverter(objectFactory);
        this.samFeatureSetConverter = new SamFeatureSetConverter(objectFactory);
        this.positionalFeatureSetConverter = new PositionalFeatureSetConverter(objectFactory);
    }

    @Override
    public UniRuleEntry fromXml(UniRuleType xmlObj) {
        if (Objects.isNull(xmlObj)) return null;

        UniRuleId uniRuleId = new UniRuleIdBuilder(xmlObj.getId()).build();
        RuleStatus ruleStatus = this.ruleStatusConverter.fromXml(xmlObj.getStatus());
        Information information = this.informationConverter.fromXml(xmlObj.getInformation());
        Rule rule = this.mainTypeConverter.fromXml(xmlObj.getMain());

        UniRuleEntryBuilder builder = new UniRuleEntryBuilder(uniRuleId, ruleStatus, information, rule);

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

        builder.positionFeatureSetsSet(positionFeatureSets);
        builder.createdBy(xmlObj.getCreator());
        builder.modifiedBy(xmlObj.getModifiedBy());
        builder.createdDate(XmlConverterHelper.dateFromXml(xmlObj.getCreated()));
        builder.modifiedDate(XmlConverterHelper.dateFromXml(xmlObj.getModified()));

        return builder.build();
    }

    @Override
    public UniRuleType toXml(UniRuleEntry uniRuleEntry) {
        return null;
    }
}

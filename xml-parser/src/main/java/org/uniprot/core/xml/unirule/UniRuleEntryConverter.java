package org.uniprot.core.xml.unirule;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.uniprot.core.unirule.*;
import org.uniprot.core.unirule.impl.UniRuleEntryBuilder;
import org.uniprot.core.unirule.impl.UniRuleIdBuilder;
import org.uniprot.core.xml.Converter;
import org.uniprot.core.xml.jaxb.unirule.ObjectFactory;
import org.uniprot.core.xml.jaxb.unirule.UniRuleType;
import org.uniprot.core.xml.uniprot.XmlConverterHelper;

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
        UniRuleId uniRuleId = new UniRuleIdBuilder(xmlObj.getId()).build();
        RuleStatus ruleStatus = this.ruleStatusConverter.fromXml(xmlObj.getStatus());
        Information information = this.informationConverter.fromXml(xmlObj.getInformation());
        Rule rule = this.mainTypeConverter.fromXml(xmlObj.getMain());

        List<CaseRule> caseRules = null;

        if (Objects.nonNull(xmlObj.getCases())) {
            caseRules =
                    xmlObj.getCases().getCase().stream()
                            .map(this.caseTypeConverter::fromXml)
                            .collect(Collectors.toList());
        }

        List<SamFeatureSet> samFeatureSets =
                xmlObj.getSamFeatureSet().stream()
                        .map(this.samFeatureSetConverter::fromXml)
                        .collect(Collectors.toList());

        List<PositionFeatureSet> positionFeatureSets =
                xmlObj.getPositionalFeatureSet().stream()
                        .map(this.positionalFeatureSetConverter::fromXml)
                        .collect(Collectors.toList());

        String createdBy = xmlObj.getCreator();
        String modifiedBy = xmlObj.getModifiedBy();
        LocalDate createdDate =
                Objects.nonNull(xmlObj.getCreated())
                        ? XmlConverterHelper.dateFromXml(xmlObj.getCreated())
                        : null;
        LocalDate modifiedDate =
                Objects.nonNull(xmlObj.getModified())
                        ? XmlConverterHelper.dateFromXml(xmlObj.getModified())
                        : null;

        UniRuleEntryBuilder builder =
                new UniRuleEntryBuilder(uniRuleId, ruleStatus, information, rule);
        builder.otherRulesSet(caseRules);
        builder.samFeatureSetsSet(samFeatureSets).positionFeatureSetsSet(positionFeatureSets);
        builder.createdBy(createdBy).modifiedBy(modifiedBy);
        builder.createdDate(createdDate).modifiedDate(modifiedDate);

        return builder.build();
    }

    @Override
    public UniRuleType toXml(UniRuleEntry uniRuleEntry) {
        return null;
    }
}

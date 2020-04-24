package org.uniprot.core.xml.unirule;

import org.uniprot.core.unirule.Annotation;
import org.uniprot.core.unirule.CaseRule;
import org.uniprot.core.unirule.impl.CaseRuleBuilder;
import org.uniprot.core.xml.Converter;
import org.uniprot.core.xml.jaxb.unirule.CaseType;
import org.uniprot.core.xml.jaxb.unirule.ObjectFactory;

import java.util.Objects;

public class CaseTypeConverter implements Converter<CaseType, CaseRule> {

    private final ObjectFactory objectFactory;
    private final MainTypeConverter mainTypeConverter;


    public CaseTypeConverter() {
        this(new ObjectFactory());
    }

    public CaseTypeConverter(ObjectFactory objectFactory) {
        this.objectFactory = objectFactory;
        this.mainTypeConverter = new MainTypeConverter(objectFactory);
    }

    @Override
    public CaseRule fromXml(CaseType xmlObj) {
        if (Objects.isNull(xmlObj)) return null;
        CaseRuleBuilder<Annotation> builder = new CaseRuleBuilder<Annotation>(this.mainTypeConverter.fromXml(xmlObj).getConditionSets());
        builder.overallStatsExempted(xmlObj.isOverallStatsExempted());
        return builder.build();
    }

    @Override
    public CaseType toXml(CaseRule uniObj) {
        return null;
    }
}

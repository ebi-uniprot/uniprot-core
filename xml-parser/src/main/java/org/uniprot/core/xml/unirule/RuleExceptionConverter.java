package org.uniprot.core.xml.unirule;

import org.uniprot.core.uniprotkb.UniProtKBAccession;
import org.uniprot.core.unirule.Annotation;
import org.uniprot.core.unirule.PositionalFeature;
import org.uniprot.core.unirule.RuleException;
import org.uniprot.core.unirule.RuleExceptionAnnotation;
import org.uniprot.core.unirule.impl.RuleExceptionAnnotationType;
import org.uniprot.core.unirule.impl.RuleExceptionBuilder;
import org.uniprot.core.xml.Converter;
import org.uniprot.core.xml.jaxb.unirule.AnnotationType;
import org.uniprot.core.xml.jaxb.unirule.ObjectFactory;
import org.uniprot.core.xml.jaxb.unirule.PositionalFeatureType;
import org.uniprot.core.xml.jaxb.unirule.RuleExceptionType;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class RuleExceptionConverter implements Converter<RuleExceptionType, RuleException> {
    private final ObjectFactory objectFactory;
    private final AnnotationConverter annotationConverter;
    private final PositionalFeatureConverter positionalFeatureConverter;
    private final UniProtKBAccessionConverter accessionConverter;

    public RuleExceptionConverter() {
        this(new ObjectFactory());
    }

    public RuleExceptionConverter(ObjectFactory objectFactory) {
        this.objectFactory = objectFactory;
        this.annotationConverter = new AnnotationConverter(objectFactory);
        this.positionalFeatureConverter = new PositionalFeatureConverter(objectFactory);
        this.accessionConverter = new UniProtKBAccessionConverter(objectFactory);
    }

    @Override
    public RuleException fromXml(RuleExceptionType xmlObj) {
        if (Objects.isNull(xmlObj)) return null;

        RuleExceptionBuilder builder = new RuleExceptionBuilder(xmlObj.getCategory());

        List<UniProtKBAccession> uniProtKBAccessionList =
                xmlObj.getAccession().stream()
                        .map(this.accessionConverter::fromXml)
                        .collect(Collectors.toList());
        if (Objects.nonNull(xmlObj.getAnnotation())) {
            builder.annotation(this.annotationConverter.fromXml(xmlObj.getAnnotation()));
        } else {
            builder.annotation(
                    this.positionalFeatureConverter.fromXml(xmlObj.getPositionalFeature()));
        }
        builder.accessionsSet(uniProtKBAccessionList);
        builder.note(xmlObj.getNote());
        return builder.build();
    }

    @Override
    public RuleExceptionType toXml(RuleException uniObj) {
        if (Objects.isNull(uniObj)) return null;

        RuleExceptionType ruleExceptionType = this.objectFactory.createRuleExceptionType();
        String category = uniObj.getCategory();
        List<UniProtKBAccession> uniProtKBAccessions = uniObj.getAccessions();
        List<String> accessions =
                uniProtKBAccessions.stream()
                        .map(this.accessionConverter::toXml)
                        .collect(Collectors.toList());
        // get Annotation or positional feature
        if (isAnnotationType(uniObj.getAnnotation())) { // annotation
            Annotation annotation = (Annotation) uniObj.getAnnotation();
            AnnotationType annotationType = this.annotationConverter.toXml(annotation);
            ruleExceptionType.setAnnotation(annotationType);
        } else { // positional feature
            PositionalFeature positionalFeature = (PositionalFeature) uniObj.getAnnotation();
            PositionalFeatureType positionalFeatureType =
                    this.positionalFeatureConverter.toXml(positionalFeature);
            ruleExceptionType.setPositionalFeature(positionalFeatureType);
        }
        ruleExceptionType.setNote(uniObj.getNote());
        ruleExceptionType.setCategory(category);
        List<String> xmlAccessions = ruleExceptionType.getAccession();
        xmlAccessions.addAll(accessions);

        return ruleExceptionType;
    }

    private boolean isAnnotationType(RuleExceptionAnnotation ruleExceptionAnnotation) {
        return Objects.nonNull(ruleExceptionAnnotation)
                && ruleExceptionAnnotation.getAnnotationType()
                        == RuleExceptionAnnotationType.ANNOTATION;
    }
}

package org.uniprot.core.xml.unirule;

import org.uniprot.core.unirule.Annotation;
import org.uniprot.core.unirule.impl.AnnotationBuilder;
import org.uniprot.core.xml.Converter;
import org.uniprot.core.xml.jaxb.unirule.AnnotationType;
import org.uniprot.core.xml.jaxb.unirule.ObjectFactory;
import org.uniprot.core.xml.uniprot.EvidenceIndexMapper;
import org.uniprot.core.xml.uniprot.GeneConverter;
import org.uniprot.core.xml.uniprot.KeywordConverter;
import org.uniprot.core.xml.uniprot.UniProtCrossReferenceConverter;

import java.util.Objects;

public class AnnotationConverter implements Converter<AnnotationType, Annotation> {
    private final ObjectFactory objectFactory;
    private final KeywordConverter keywordConverter;
    private final EvidenceIndexMapper evRefMapper;
    private final GeneConverter geneConverter;
    private final UniProtCrossReferenceConverter crossReferenceConverter;
    private final CommentConverter commentConverter;

    public AnnotationConverter() {
        this(new ObjectFactory());
    }

    public AnnotationConverter(ObjectFactory objectFactory) {
        this.objectFactory = objectFactory;
        this.commentConverter = new CommentConverter();
        this.evRefMapper = new EvidenceIndexMapper();
        this.keywordConverter = new KeywordConverter(this.evRefMapper);
        this.geneConverter = new GeneConverter(this.evRefMapper);
        this.crossReferenceConverter = new UniProtCrossReferenceConverter();
    }

    @Override
    public Annotation fromXml(AnnotationType xmlObj) {
        if (Objects.isNull(xmlObj)) return null;

        AnnotationBuilder builder = new AnnotationBuilder();

        if (Objects.nonNull(xmlObj.getComment())) {
            builder.comment(this.commentConverter.fromXml(xmlObj.getComment()));
        }


        if (Objects.nonNull(xmlObj.getKeyword())) {
            builder.keyword(this.keywordConverter.fromXml(xmlObj.getKeyword()));
        }

        if (Objects.nonNull(xmlObj.getGene())) {
            builder.gene(this.geneConverter.fromXml(xmlObj.getGene()));
        }

        if (Objects.nonNull(xmlObj.getDbReference())) {
            builder.dbReference(this.crossReferenceConverter.fromXml(xmlObj.getDbReference()));
        }

        return builder.build();
    }

    @Override
    public AnnotationType toXml(Annotation uniObj) {
        return null;
    }
}

package org.uniprot.core.xml.uniprot.comment;

import org.uniprot.core.CrossReference;
import org.uniprot.core.builder.CrossReferenceBuilder;
import org.uniprot.core.uniprot.comment.Disease;
import org.uniprot.core.uniprot.comment.DiseaseDatabase;
import org.uniprot.core.uniprot.comment.builder.DiseaseBuilder;
import org.uniprot.core.xml.Converter;
import org.uniprot.core.xml.jaxb.uniprot.CommentType;
import org.uniprot.core.xml.jaxb.uniprot.DbReferenceType;
import org.uniprot.core.xml.jaxb.uniprot.ObjectFactory;

public class DiseaseConverter implements Converter<CommentType.Disease, Disease> {
    private final ObjectFactory xmlUniprotFactory;

    public DiseaseConverter() {
        this(new ObjectFactory());
    }

    public DiseaseConverter(ObjectFactory xmlUniprotFactory) {
        this.xmlUniprotFactory = xmlUniprotFactory;
    }

    @Override
    public Disease fromXml(CommentType.Disease xmlDisease) {
        if (xmlDisease == null) return null;
        DiseaseBuilder builder = new DiseaseBuilder();
        builder.acronym(xmlDisease.getAcronym())
                .diseaseAc(xmlDisease.getId())
                .diseaseId(xmlDisease.getName())
                .description(xmlDisease.getDescription());

        if (xmlDisease.getDbReference() != null) {
            DiseaseDatabase type = DiseaseDatabase.typeOf(xmlDisease.getDbReference().getType());
            CrossReference<DiseaseDatabase> reference =
                    new CrossReferenceBuilder<DiseaseDatabase>()
                            .database(type)
                            .id(xmlDisease.getDbReference().getId())
                            .build();
            builder.diseaseCrossReference(reference);
        }
        return builder.build();
    }

    @Override
    public CommentType.Disease toXml(Disease disease) {
        if (disease == null) return null;

        CommentType.Disease xmlDisease = xmlUniprotFactory.createCommentTypeDisease();
        xmlDisease.setAcronym(disease.getAcronym());
        xmlDisease.setName(disease.getDiseaseId());
        xmlDisease.setDescription(disease.getDescription());
        xmlDisease.setId(disease.getDiseaseAccession());

        DbReferenceType dbReferenceType = xmlUniprotFactory.createDbReferenceType();
        dbReferenceType.setId(disease.getDiseaseCrossReference().getId());
        dbReferenceType.setType(disease.getDiseaseCrossReference().getDatabase().name());
        xmlDisease.setDbReference(dbReferenceType);

        return xmlDisease;
    }
}

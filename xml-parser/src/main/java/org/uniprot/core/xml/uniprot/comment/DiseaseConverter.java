package org.uniprot.core.xml.uniprot.comment;

import org.uniprot.core.DBCrossReference;
import org.uniprot.core.builder.DBCrossReferenceBuilder;
import org.uniprot.core.uniprot.comment.Disease;
import org.uniprot.core.uniprot.comment.DiseaseReferenceType;
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
        if (xmlDisease == null)
            return null;
        DiseaseBuilder builder = new DiseaseBuilder();
        builder.acronym(xmlDisease.getAcronym())
                .diseaseAc(xmlDisease.getId())
                .diseaseId(xmlDisease.getName())
                .description(xmlDisease.getDescription());

        if (xmlDisease.getDbReference() != null) {
            DiseaseReferenceType type = DiseaseReferenceType.typeOf(xmlDisease.getDbReference().getType());
            DBCrossReference<DiseaseReferenceType> reference =
                    new DBCrossReferenceBuilder<DiseaseReferenceType>()
                            .databaseType(type)
                            .id(xmlDisease.getDbReference().getId())
                            .build();
            builder.reference(reference);
        }
        return builder.build();
    }

    @Override
    public CommentType.Disease toXml(Disease disease) {
        if (disease == null)
            return null;

        CommentType.Disease xmlDisease = xmlUniprotFactory.createCommentTypeDisease();
        xmlDisease.setAcronym(disease.getAcronym());
        xmlDisease.setName(disease.getDiseaseId());
        xmlDisease.setDescription(disease.getDescription());
        xmlDisease.setId(disease.getDiseaseAccession());

        DbReferenceType dbReferenceType = xmlUniprotFactory.createDbReferenceType();
        dbReferenceType.setId(disease.getReference().getId());
        dbReferenceType.setType(disease.getReference().getDatabaseType().name());
        xmlDisease.setDbReference(dbReferenceType);

        return xmlDisease;
    }

}

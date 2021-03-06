package org.uniprot.core.xml.uniprot.comment;

import org.uniprot.core.uniprotkb.comment.Comment;
import org.uniprot.core.uniprotkb.comment.CommentType;
import org.uniprot.core.xml.jaxb.uniprot.ObjectFactory;
import org.uniprot.core.xml.uniprot.EvidenceIndexMapper;

public enum CommentConverterFactory {
    INSTANCE;

    @SuppressWarnings("unchecked")
    public <T extends Comment> CommentConverter<T> createCommentConverter(
            CommentType type, EvidenceIndexMapper evRefMapper, ObjectFactory xmlUniprotFactory) {
        return createCommentConverter(type, evRefMapper, xmlUniprotFactory, "");
    }

    @SuppressWarnings("unchecked")
    public <T extends Comment> CommentConverter<T> createCommentConverter(
            CommentType type,
            EvidenceIndexMapper evRefMapper,
            ObjectFactory xmlUniprotFactory,
            String subcellFile) {
        switch (type) {
            case CATALYTIC_ACTIVITY:
                return (CommentConverter<T>)
                        new CatalyticActivityCommentConverter(evRefMapper, xmlUniprotFactory);
            case COFACTOR:
                return (CommentConverter<T>)
                        new CofactorCommentConverter(evRefMapper, xmlUniprotFactory);
            case BIOPHYSICOCHEMICAL_PROPERTIES:
                return (CommentConverter<T>)
                        new BPCPCommentConverter(evRefMapper, xmlUniprotFactory);
            case INTERACTION:
                return null;
            case SUBCELLULAR_LOCATION:
                return (CommentConverter<T>)
                        new SCLCommentConverter(
                                evRefMapper,
                                xmlUniprotFactory,
                                new SubcellLocationNameMapImpl(subcellFile));
            case ALTERNATIVE_PRODUCTS:
                return (CommentConverter<T>) new APCommentConverter(evRefMapper, xmlUniprotFactory);
            case RNA_EDITING:
                return (CommentConverter<T>)
                        new RnaEditingCommentConverter(evRefMapper, xmlUniprotFactory);
            case MASS_SPECTROMETRY:
                return (CommentConverter<T>) new MSCommentConverter(evRefMapper, xmlUniprotFactory);
            case DISEASE:
                return (CommentConverter<T>)
                        new DiseaseCommentConverter(evRefMapper, xmlUniprotFactory);
            case SEQUENCE_CAUTION:
                return (CommentConverter<T>) new SCCommentConverter(evRefMapper, xmlUniprotFactory);
            case WEBRESOURCE:
                return (CommentConverter<T>) new WRCommentConverter(xmlUniprotFactory);
            default:
                return (CommentConverter<T>)
                        new FreeTextCommentConverter(evRefMapper, xmlUniprotFactory);
        }
    }

    @SuppressWarnings("unchecked")
    public <T extends Comment> CommentConverter<T> createCommentConverter(
            CommentType type, EvidenceIndexMapper evRefMapper) {
        return createCommentConverter(type, evRefMapper, "");
    }

    @SuppressWarnings("unchecked")
    public <T extends Comment> CommentConverter<T> createCommentConverter(
            CommentType type, EvidenceIndexMapper evRefMapper, String subcellFile) {
        switch (type) {
            case CATALYTIC_ACTIVITY:
                return (CommentConverter<T>) new CatalyticActivityCommentConverter(evRefMapper);
            case COFACTOR:
                return (CommentConverter<T>) new CofactorCommentConverter(evRefMapper);
            case BIOPHYSICOCHEMICAL_PROPERTIES:
                return (CommentConverter<T>) new BPCPCommentConverter(evRefMapper);
            case INTERACTION:
                return null;
            case SUBCELLULAR_LOCATION:
                return (CommentConverter<T>)
                        new SCLCommentConverter(
                                evRefMapper, new SubcellLocationNameMapImpl(subcellFile));
            case ALTERNATIVE_PRODUCTS:
                return (CommentConverter<T>) new APCommentConverter(evRefMapper);
            case RNA_EDITING:
                return (CommentConverter<T>) new RnaEditingCommentConverter(evRefMapper);
            case MASS_SPECTROMETRY:
                return (CommentConverter<T>) new MSCommentConverter(evRefMapper);
            case DISEASE:
                return (CommentConverter<T>) new DiseaseCommentConverter(evRefMapper);
            case SEQUENCE_CAUTION:
                return (CommentConverter<T>) new SCCommentConverter(evRefMapper);
            case WEBRESOURCE:
                return (CommentConverter<T>) new WRCommentConverter();
            default:
                return (CommentConverter<T>) new FreeTextCommentConverter(evRefMapper);
        }
    }

    public InteractionCommentConverter createInteractionCommentConverter() {
        return new InteractionCommentConverter();
    }

    public InteractionCommentConverter createInteractionCommentConverter(
            ObjectFactory xmlUniprotFactory) {
        return new InteractionCommentConverter(xmlUniprotFactory);
    }
}

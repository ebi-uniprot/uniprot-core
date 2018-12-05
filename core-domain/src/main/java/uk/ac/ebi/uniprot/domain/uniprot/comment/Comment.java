package uk.ac.ebi.uniprot.domain.uniprot.comment;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.As.PROPERTY;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = NAME, include = PROPERTY, property = "type")
@JsonSubTypes({
  @JsonSubTypes.Type(value=uk.ac.ebi.uniprot.domain.uniprot.comment.impl.AlternativeProductsCommentImpl.class, name = "APCommentImpl"),
  @JsonSubTypes.Type(value=uk.ac.ebi.uniprot.domain.uniprot.comment.impl.BPCPCommentImpl.class, name = "BPCPCommentImpl"),
  @JsonSubTypes.Type(value=uk.ac.ebi.uniprot.domain.uniprot.comment.impl.CatalyticActivityCommentImpl.class, name = "CatalyticActivityCommentImpl"),
  @JsonSubTypes.Type(value=uk.ac.ebi.uniprot.domain.uniprot.comment.impl.CofactorCommentImpl.class, name = "CofactorCommentImpl"),
  @JsonSubTypes.Type(value=uk.ac.ebi.uniprot.domain.uniprot.comment.impl.DiseaseCommentImpl.class, name = "DiseaseCommentImpl"),
  @JsonSubTypes.Type(value=uk.ac.ebi.uniprot.domain.uniprot.comment.impl.FreeTextCommentImpl.class, name = "FreeTextCommentImpl"),
  @JsonSubTypes.Type(value=uk.ac.ebi.uniprot.domain.uniprot.comment.impl.InteractionCommentImpl.class, name = "InteractionCommentImpl"),
  @JsonSubTypes.Type(value=uk.ac.ebi.uniprot.domain.uniprot.comment.impl.MassSpectrometryCommentImpl.class, name = "MassSpectrometryCommentImpl"),
  @JsonSubTypes.Type(value=uk.ac.ebi.uniprot.domain.uniprot.comment.impl.RnaEditingCommentImpl.class, name = "RnaEditingCommentImpl"),  
  @JsonSubTypes.Type(value=uk.ac.ebi.uniprot.domain.uniprot.comment.impl.SequenceCautionCommentImpl.class, name = "SequenceCautionCommentImpl"),
  @JsonSubTypes.Type(value=uk.ac.ebi.uniprot.domain.uniprot.comment.impl.SubcellularLocationCommentImpl.class, name = "SubcellularLocationCommentImpl"),
  @JsonSubTypes.Type(value=uk.ac.ebi.uniprot.domain.uniprot.comment.impl.WebResourceCommentImpl.class, name = "WebResourceCommentImpl")
})
public interface Comment {
    public CommentType getCommentType();


}

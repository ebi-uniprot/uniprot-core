package uk.ac.ebi.uniprot.domain.uniprot.comment;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.As.PROPERTY;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME;

@JsonTypeInfo(use = NAME, include = PROPERTY, property = "type")
@JsonSubTypes({
  @JsonSubTypes.Type(value=uk.ac.ebi.uniprot.domain.uniprot.comment.impl.AlternativeProductsCommentImpl.class, name = "APComment"),
  @JsonSubTypes.Type(value=uk.ac.ebi.uniprot.domain.uniprot.comment.impl.BPCPCommentImpl.class, name = "BPCPComment"),
  @JsonSubTypes.Type(value=uk.ac.ebi.uniprot.domain.uniprot.comment.impl.CatalyticActivityCommentImpl.class, name = "CatalyticActivityComment"),
  @JsonSubTypes.Type(value=uk.ac.ebi.uniprot.domain.uniprot.comment.impl.CofactorCommentImpl.class, name = "CofactorComment"),
  @JsonSubTypes.Type(value=uk.ac.ebi.uniprot.domain.uniprot.comment.impl.DiseaseCommentImpl.class, name = "DiseaseComment"),
  @JsonSubTypes.Type(value=uk.ac.ebi.uniprot.domain.uniprot.comment.impl.FreeTextCommentImpl.class, name = "FreeTextComment"),
  @JsonSubTypes.Type(value=uk.ac.ebi.uniprot.domain.uniprot.comment.impl.InteractionCommentImpl.class, name = "InteractionComment"),
  @JsonSubTypes.Type(value=uk.ac.ebi.uniprot.domain.uniprot.comment.impl.MassSpectrometryCommentImpl.class, name = "MassSpectrometryComment"),
  @JsonSubTypes.Type(value=uk.ac.ebi.uniprot.domain.uniprot.comment.impl.RnaEditingCommentImpl.class, name = "RnaEditingComment"),
  @JsonSubTypes.Type(value=uk.ac.ebi.uniprot.domain.uniprot.comment.impl.SequenceCautionCommentImpl.class, name = "SequenceCautionComment"),
  @JsonSubTypes.Type(value=uk.ac.ebi.uniprot.domain.uniprot.comment.impl.SubcellularLocationCommentImpl.class, name = "SubcellularLocationComment"),
  @JsonSubTypes.Type(value=uk.ac.ebi.uniprot.domain.uniprot.comment.impl.WebResourceCommentImpl.class, name = "WebResourceComment")
})
public interface Comment {
    public CommentType getCommentType();


}

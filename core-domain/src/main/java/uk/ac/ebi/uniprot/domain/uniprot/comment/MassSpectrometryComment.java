package uk.ac.ebi.uniprot.domain.uniprot.comment;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.As.PROPERTY;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;
@JsonTypeInfo(use = NAME, include = PROPERTY)
@JsonSubTypes({
  @JsonSubTypes.Type(value=uk.ac.ebi.uniprot.domain.uniprot.comment.impl.MassSpectrometryCommentImpl.class, name = "MassSpectrometryCommentImpl")
})
public interface MassSpectrometryComment extends Comment {

	public Double getMolWeight();

	public Double getMolWeightError();

	public String getNote();

	public List<MassSpectrometryRange> getRanges();

	public MassSpectrometryMethod getMethod();

	public List<Evidence> getEvidences();

}

package uk.ac.ebi.uniprot.domain.uniprot.comment;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.As.PROPERTY;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;


/**
 * CC -!- RNA EDITING: Modified_positions=x[, y, z][; Note=].
 */


@JsonTypeInfo(use = NAME, include = PROPERTY)
@JsonSubTypes({
  @JsonSubTypes.Type(value=uk.ac.ebi.uniprot.domain.uniprot.comment.impl.RnaEditingCommentImpl.class, name = "RnaEditingCommentImpl")
})
public interface RnaEditingComment  extends Comment {

    public final static String POSITIONS_PREFIX = "Modified_positions=";
    
    public RnaEditingLocationType getLocationType();

    public List<RnaEdPosition> getPositions();

    public Note getNote();
}

package uk.ac.ebi.uniprot.domain.uniprot.comment.impl;

import uk.ac.ebi.uniprot.domain.uniprot.EvidencedValue;
import uk.ac.ebi.uniprot.domain.uniprot.comment.CommentType;
import uk.ac.ebi.uniprot.domain.uniprot.comment.FreeTextComment;
import uk.ac.ebi.uniprot.domain.uniprot.impl.FreeTextImpl;

import java.util.EnumSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class FreeTextCommentImpl extends FreeTextImpl implements FreeTextComment {
	private static final Set<CommentType> VALID_COMMENT_TYPES =
			EnumSet.of(CommentType.ALLERGEN, CommentType.BIOTECHNOLOGY,
					CommentType.CATALYTIC_ACTIVITY,
					CommentType.CAUTION,
					CommentType.DEVELOPMENTAL_STAGE,
					CommentType.DISRUPTION_PHENOTYPE,
					CommentType.DOMAIN,
					CommentType.ENZYME_REGULATION,
					CommentType.FUNCTION,
					CommentType.INDUCTION,
					CommentType.INDUCTION,
					CommentType.MISCELLANEOUS,
					CommentType.PATHWAY,
					CommentType.PHARMACEUTICAL,
					CommentType.POLYMORPHISM,
					CommentType.PTM,
					CommentType.SIMILARITY,
					CommentType.SUBUNIT,
					CommentType.TISSUE_SPECIFICITY,
					CommentType.TOXIC_DOSE
					);
	
	public static boolean isFreeTextCommentType (CommentType type) {
		return VALID_COMMENT_TYPES.contains(type);
	}
    private final CommentType commentType;
    @JsonCreator
    public FreeTextCommentImpl(@JsonProperty("commentType") CommentType type,
    		@JsonProperty("texts") List<EvidencedValue> texts) {
        super(texts);
        this.commentType = type;
       
    }
    @Override
    public CommentType getCommentType() {
        return commentType;
    }
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((commentType == null) ? 0 : commentType.hashCode());
        return result;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        FreeTextCommentImpl other = (FreeTextCommentImpl) obj;
        if (commentType != other.commentType)
            return false;
        return true;
    }


}

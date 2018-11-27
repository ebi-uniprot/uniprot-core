package uk.ac.ebi.uniprot.domain.uniprot.comment.builder;


import java.util.EnumSet;
import java.util.List;
import java.util.Set;

import uk.ac.ebi.uniprot.domain.uniprot.EvidencedValue;
import uk.ac.ebi.uniprot.domain.uniprot.comment.CommentType;
import uk.ac.ebi.uniprot.domain.uniprot.comment.FreeTextComment;
import uk.ac.ebi.uniprot.domain.uniprot.comment.impl.FreeTextCommentImpl;


public  class FreeTextCommentBuilder implements CommentBuilder<FreeTextComment> {
	private static final Set<CommentType> VALID_COMMENT_TYPES =
			EnumSet.of(CommentType.ALLERGEN, CommentType.BIOTECHNOLOGY,
					CommentType.CATALYTIC_ACTIVITY,
					CommentType.CAUTION,
					CommentType.DEVELOPMENTAL_STAGE,
					CommentType.DISRUPTION_PHENOTYPE,
					CommentType.DOMAIN,
					CommentType.ACTIVITY_REGULATION,
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
    public static FreeTextCommentBuilder newInstance(){
        return new FreeTextCommentBuilder();
    }
    private CommentType commentType;
    private List<EvidencedValue> texts;
    @Override
    public FreeTextComment build() {
        return buildFreeTextComment(commentType, texts);
    }
    
    public FreeTextCommentBuilder commentType(CommentType commentType){
        this.commentType  = commentType;
        return this;
    }
    public FreeTextCommentBuilder texts(List<EvidencedValue> texts){
        this.texts  = texts;
        return this;
    }
	public static FreeTextComment buildFreeTextComment(CommentType commentType,
            List<EvidencedValue> texts){
        if(!isFreeTextCommentType(commentType)) {
        	throw new IllegalArgumentException(commentType + " is not free text comment");
        }
        return new FreeTextCommentImpl(commentType, texts);
      
    }
  
}

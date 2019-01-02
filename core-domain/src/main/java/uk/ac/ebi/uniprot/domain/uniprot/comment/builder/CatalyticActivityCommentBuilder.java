package uk.ac.ebi.uniprot.domain.uniprot.comment.builder;

import java.util.List;

import uk.ac.ebi.uniprot.domain.DBCrossReference;
import uk.ac.ebi.uniprot.domain.ECNumber;
import uk.ac.ebi.uniprot.domain.uniprot.comment.CatalyticActivityComment;
import uk.ac.ebi.uniprot.domain.uniprot.comment.PhysiologicalDirectionType;
import uk.ac.ebi.uniprot.domain.uniprot.comment.PhysiologicalReaction;
import uk.ac.ebi.uniprot.domain.uniprot.comment.Reaction;
import uk.ac.ebi.uniprot.domain.uniprot.comment.ReactionReferenceType;
import uk.ac.ebi.uniprot.domain.uniprot.comment.impl.CatalyticActivityCommentImpl;
import uk.ac.ebi.uniprot.domain.uniprot.comment.impl.PhysiologicalReactionImpl;
import uk.ac.ebi.uniprot.domain.uniprot.comment.impl.ReactionImpl;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;

public class CatalyticActivityCommentBuilder implements CommentBuilder<CatalyticActivityComment> {

	public static CatalyticActivityCommentBuilder newInstance() {
		return new CatalyticActivityCommentBuilder();
	}
	
	public static Reaction createReaction(String name,
			 List<DBCrossReference<ReactionReferenceType>> reactionReferences,
			 ECNumber ecNumber, List<Evidence> evidences) {
		return new ReactionImpl(name, reactionReferences, ecNumber, evidences);
		
	}
	public static PhysiologicalReaction createPhysiologicalReaction(PhysiologicalDirectionType directionType,
			DBCrossReference<ReactionReferenceType> reactionReference,
			List<Evidence> evidences) {
		
		return new PhysiologicalReactionImpl(directionType,reactionReference, evidences );
	}
	private Reaction reaction;
	private List<PhysiologicalReaction>  physiologicalReactions;
	@Override
	public CatalyticActivityComment build() {
		return new CatalyticActivityCommentImpl(reaction, physiologicalReactions);
	}

	public CatalyticActivityCommentBuilder reaction(Reaction reaction ) {
		this.reaction = reaction;
		return this;
	}
	public CatalyticActivityCommentBuilder physiologicalReactions(List<PhysiologicalReaction>  physiologicalReactions) {
		this.physiologicalReactions = physiologicalReactions;
		return this;
	}
}

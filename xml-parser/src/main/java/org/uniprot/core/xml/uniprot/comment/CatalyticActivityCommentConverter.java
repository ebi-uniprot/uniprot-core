package org.uniprot.core.xml.uniprot.comment;

import java.util.List;
import java.util.stream.Collectors;

import org.uniprot.core.uniprot.comment.CatalyticActivityComment;
import org.uniprot.core.uniprot.comment.PhysiologicalReaction;
import org.uniprot.core.uniprot.comment.Reaction;
import org.uniprot.core.uniprot.comment.builder.CatalyticActivityCommentBuilder;
import org.uniprot.core.xml.jaxb.uniprot.CommentType;
import org.uniprot.core.xml.jaxb.uniprot.ObjectFactory;
import org.uniprot.core.xml.uniprot.EvidenceIndexMapper;

public class CatalyticActivityCommentConverter implements CommentConverter<CatalyticActivityComment> {
	private final ObjectFactory xmlUniprotFactory;
	private final CAReactionConverter reactionConverter;
	private final CAPhysioReactionConverter physioReactionConverter;

	public CatalyticActivityCommentConverter(EvidenceIndexMapper evRefMapper) {
		this(evRefMapper, new ObjectFactory());
	}

	public CatalyticActivityCommentConverter(EvidenceIndexMapper evRefMapper, ObjectFactory xmlUniprotFactory) {
		this.xmlUniprotFactory = xmlUniprotFactory;
		this.reactionConverter = new CAReactionConverter(evRefMapper, xmlUniprotFactory);
		this.physioReactionConverter = new CAPhysioReactionConverter(evRefMapper, xmlUniprotFactory);
	}

	@Override
	public CatalyticActivityComment fromXml(CommentType xmlObj) {
		Reaction reaction = reactionConverter.fromXml(xmlObj.getReaction());
		List<PhysiologicalReaction> physioReactions = xmlObj.getPhysiologicalReaction().stream()
				.map(physioReactionConverter::fromXml).collect(Collectors.toList());

		CatalyticActivityCommentBuilder builder = new CatalyticActivityCommentBuilder();
		return builder.reaction(reaction).physiologicalReactions(physioReactions).build();

	}

	@Override
	public CommentType toXml(CatalyticActivityComment uniObj) {
		CommentType commentType = xmlUniprotFactory.createCommentType();
		commentType.setType(uniObj.getCommentType().toDisplayName().toLowerCase());
		commentType.setReaction(reactionConverter.toXml(uniObj.getReaction()));
		uniObj.getPhysiologicalReactions()
				.forEach(val -> commentType.getPhysiologicalReaction().add(physioReactionConverter.toXml(val)));

		return commentType;
	}

}

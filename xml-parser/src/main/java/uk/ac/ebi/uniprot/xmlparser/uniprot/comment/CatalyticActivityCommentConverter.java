package uk.ac.ebi.uniprot.xmlparser.uniprot.comment;

import java.util.List;
import java.util.stream.Collectors;

import uk.ac.ebi.uniprot.domain.uniprot.comment.CatalyticActivityComment;
import uk.ac.ebi.uniprot.domain.uniprot.comment.PhysiologicalReaction;
import uk.ac.ebi.uniprot.domain.uniprot.comment.Reaction;
import uk.ac.ebi.uniprot.domain.uniprot.comment.builder.CatalyticActivityCommentBuilder;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.CommentType;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.ObjectFactory;
import uk.ac.ebi.uniprot.xmlparser.Converter;
import uk.ac.ebi.uniprot.xmlparser.uniprot.EvidenceIndexMapper;

public class CatalyticActivityCommentConverter implements Converter<CommentType, CatalyticActivityComment> {
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

		CatalyticActivityCommentBuilder builder = CatalyticActivityCommentBuilder.newInstance();
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

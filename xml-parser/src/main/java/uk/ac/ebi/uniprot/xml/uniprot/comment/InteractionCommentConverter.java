package uk.ac.ebi.uniprot.xml.uniprot.comment;

import uk.ac.ebi.uniprot.xml.Converter;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.CommentType;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.ObjectFactory;

import java.util.List;
import java.util.stream.Collectors;

import org.uniprot.core.uniprot.comment.InteractionComment;
import org.uniprot.core.uniprot.comment.builder.InteractionCommentBuilder;

public class InteractionCommentConverter implements Converter<List<CommentType>, InteractionComment> {
	private final InteractionConverter interactionConverter;

	public InteractionCommentConverter() {
		this(new ObjectFactory());
	}

	public InteractionCommentConverter(ObjectFactory xmlUniprotFactory) {
		this.interactionConverter = new InteractionConverter(xmlUniprotFactory);

	}

	@Override
	public InteractionComment fromXml(List<CommentType> xmlObj) {
		if ((xmlObj == null) || xmlObj.isEmpty())
			return null;
		InteractionCommentBuilder builder = new InteractionCommentBuilder();
		return builder.interactions(xmlObj.stream().map(interactionConverter::fromXml).collect(Collectors.toList()))
				.build();
	}

	@Override
	public List<CommentType> toXml(InteractionComment uniObj) {
		if (uniObj == null)
			return null;
		return uniObj.getInteractions().stream().map(interactionConverter::toXml).collect(Collectors.toList());
	}

}

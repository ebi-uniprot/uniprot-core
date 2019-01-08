package uk.ac.ebi.uniprot.xmlparser.uniprot.comment;

import java.util.stream.Collectors;

import uk.ac.ebi.uniprot.domain.uniprot.comment.Note;
import uk.ac.ebi.uniprot.domain.uniprot.comment.RnaEditingComment;
import uk.ac.ebi.uniprot.domain.uniprot.comment.RnaEditingLocationType;
import uk.ac.ebi.uniprot.domain.uniprot.comment.builder.RnaEditingCommentBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.factory.CommentFactory;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.CommentType;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.ObjectFactory;
import uk.ac.ebi.uniprot.xmlparser.uniprot.EvidenceIndexMapper;
import uk.ac.ebi.uniprot.xmlparser.uniprot.EvidencedValueConverter;

public class RnaEditingCommentConverter implements CommentConverter<RnaEditingComment> {

	private final ObjectFactory xmlUniprotFactory;
	private final RnaEdPositionConverter positionConverter;
	private final EvidencedValueConverter evValueConverter;

	public RnaEditingCommentConverter(EvidenceIndexMapper evRefMapper) {
		this(evRefMapper, new ObjectFactory());
	}

	public RnaEditingCommentConverter(EvidenceIndexMapper evRefMapper, ObjectFactory xmlUniprotFactory) {
		this.xmlUniprotFactory = xmlUniprotFactory;
		this.positionConverter = new RnaEdPositionConverter(evRefMapper, xmlUniprotFactory);
		evValueConverter = new EvidencedValueConverter(evRefMapper, xmlUniprotFactory, true);
	}

	@Override
	public RnaEditingComment fromXml(CommentType xmlObj) {
		if (xmlObj == null)
			return null;
		RnaEditingCommentBuilder builder = RnaEditingCommentBuilder.newInstance();
		if (xmlObj.getLocation() != null && !xmlObj.getLocation().isEmpty()) {
			builder.locations(
					xmlObj.getLocation().stream().map(positionConverter::fromXml).collect(Collectors.toList()));
		}
		if (xmlObj.getLocationType() != null) {
			builder.rnaEditingLocationType(RnaEditingLocationType.getType(xmlObj.getLocationType()));
		}

		// Note
		if (!xmlObj.getText().isEmpty()) {
			Note note = CommentFactory.INSTANCE
					.createNote(xmlObj.getText().stream().map(evValueConverter::fromXml).collect(Collectors.toList()));
			builder.note(note);
		}
		return builder.build();

	}

	@Override
	public CommentType toXml(RnaEditingComment uniObj) {
		if (uniObj == null)
			return null;

		CommentType commentXML = xmlUniprotFactory.createCommentType();
		commentXML.setType("RNA editing");
		
		if ((uniObj.getNote() != null) && (!uniObj.getNote().getTexts().isEmpty())) {
			uniObj.getNote().getTexts().forEach(val -> commentXML.getText().add(evValueConverter.toXml(val)));
		}
		// LocationType - enum
		if (uniObj.getLocationType() == RnaEditingLocationType.Not_applicable
				|| uniObj.getLocationType() == RnaEditingLocationType.Undetermined) {
			commentXML.setLocationType(uniObj.getLocationType().name());
		} else {
			uniObj.getPositions().forEach(pos -> commentXML.getLocation().add(positionConverter.toXml(pos)));

		}

		return commentXML;

	}

}

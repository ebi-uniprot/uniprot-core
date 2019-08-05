package uk.ac.ebi.uniprot.xml.uniprot.comment;

import uk.ac.ebi.uniprot.xml.jaxb.uniprot.CommentType;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.ObjectFactory;
import uk.ac.ebi.uniprot.xml.uniprot.EvidenceIndexMapper;
import uk.ac.ebi.uniprot.xml.uniprot.EvidencedValueConverter;

import java.util.stream.Collectors;

import org.uniprot.core.uniprot.comment.Note;
import org.uniprot.core.uniprot.comment.RnaEditingComment;
import org.uniprot.core.uniprot.comment.RnaEditingLocationType;
import org.uniprot.core.uniprot.comment.builder.NoteBuilder;
import org.uniprot.core.uniprot.comment.builder.RnaEditingCommentBuilder;

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
		RnaEditingCommentBuilder builder = new RnaEditingCommentBuilder();
		if (xmlObj.getLocation() != null && !xmlObj.getLocation().isEmpty()) {
			builder.positions(
					xmlObj.getLocation().stream().map(positionConverter::fromXml).collect(Collectors.toList()));
		}
		if (xmlObj.getLocationType() != null) {
			builder.locationType(RnaEditingLocationType.getType(xmlObj.getLocationType()));
		}

		// Note
		if (!xmlObj.getText().isEmpty()) {
			Note note = new NoteBuilder(xmlObj.getText().stream().map(evValueConverter::fromXml).collect(Collectors.toList())).build();
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

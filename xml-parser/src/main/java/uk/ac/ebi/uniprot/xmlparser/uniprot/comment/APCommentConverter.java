package uk.ac.ebi.uniprot.xmlparser.uniprot.comment;

import java.util.stream.Collectors;

import uk.ac.ebi.uniprot.domain.uniprot.comment.APEventType;
import uk.ac.ebi.uniprot.domain.uniprot.comment.AlternativeProductsComment;
import uk.ac.ebi.uniprot.domain.uniprot.comment.Note;
import uk.ac.ebi.uniprot.domain.uniprot.comment.builder.APCommentBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.factory.CommentFactory;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.CommentType;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.EventType;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.ObjectFactory;
import uk.ac.ebi.uniprot.xmlparser.uniprot.EvidenceIndexMapper;
import uk.ac.ebi.uniprot.xmlparser.uniprot.EvidencedValueConverter;

public class APCommentConverter implements CommentConverter<AlternativeProductsComment> {
	private static final String ALTERNATIVE_PROMOTER = "alternative promoter";
	private final ObjectFactory xmlUniprotFactory;
	private final APIsoformConverter isoformConverter;
	private final EvidencedValueConverter evValueConverter;

	public APCommentConverter(EvidenceIndexMapper evRefMapper) {
		this(evRefMapper, new ObjectFactory());
	}

	public APCommentConverter(EvidenceIndexMapper evRefMapper, ObjectFactory xmlUniprotFactory) {
		this.xmlUniprotFactory = xmlUniprotFactory;
		this.isoformConverter = new APIsoformConverter(evRefMapper, xmlUniprotFactory);
		this.evValueConverter = new EvidencedValueConverter(evRefMapper, xmlUniprotFactory, false);
	}

	@Override
	public AlternativeProductsComment fromXml(CommentType xmlObj) {
		APCommentBuilder builder = APCommentBuilder.newInstance();

		if (xmlObj.getEvent() != null && !xmlObj.getEvent().isEmpty()) {
			builder.events(xmlObj.getEvent().stream().map(val -> eventFromXml(val)).collect(Collectors.toList()));
		}

		if (xmlObj.getIsoform() != null && !xmlObj.getIsoform().isEmpty()) {
			builder.isoforms(xmlObj.getIsoform().stream().map(isoformConverter::fromXml).collect(Collectors.toList()));
		}

		if (!xmlObj.getText().isEmpty()) {
			Note note = CommentFactory.INSTANCE
					.createNote(xmlObj.getText().stream().map(evValueConverter::fromXml).collect(Collectors.toList()));
			builder.note(note);
		}
		return builder.build();
	}

	@Override
	public CommentType toXml(AlternativeProductsComment comment) {
		if (comment == null)
			return null;
		CommentType commentXML = xmlUniprotFactory.createCommentType();
		commentXML.setType(comment.getCommentType().toDisplayName().toLowerCase());

		if (comment.getEvents() != null) {
			comment.getEvents().forEach(event -> commentXML.getEvent().add(eventToXml(event)));
		}

		if (comment.getIsoforms() != null) {
			comment.getIsoforms().forEach(isoform -> commentXML.getIsoform().add(isoformConverter.toXml(isoform)));
		}

		Note note = comment.getNote();
		if ((note != null) && (!note.getTexts().isEmpty())) {
			note.getTexts().forEach(val -> commentXML.getText().add(evValueConverter.toXml(val)));
		}

		return commentXML;

	}

	private APEventType eventFromXml(EventType xmlObj) {
		String eventType = xmlObj.getType();
		if (ALTERNATIVE_PROMOTER.equals(eventType))
			return APEventType.ALTERNATIVE_PROMOTER_USAGE;
		return APEventType.typeOf(eventType);
	}

	private EventType eventToXml(APEventType uniObj) {
		EventType eventXML = xmlUniprotFactory.createEventType();
		if (uniObj == APEventType.ALTERNATIVE_PROMOTER_USAGE) {
			eventXML.setType(ALTERNATIVE_PROMOTER);
		} else {
			eventXML.setType(uniObj.toDisplayName().toLowerCase());
		}

		return eventXML;
	}
}

package uk.ac.ebi.uniprot.xmlparser.uniprot.comment;

import com.google.common.base.Strings;
import uk.ac.ebi.uniprot.domain.uniprot.comment.SequenceCautionComment;
import uk.ac.ebi.uniprot.domain.uniprot.comment.SequenceCautionType;
import uk.ac.ebi.uniprot.domain.uniprot.comment.builder.SequenceCautionCommentBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.evidence2.Evidence;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.CommentType;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.EvidencedStringType;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.LocationType;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.ObjectFactory;
import uk.ac.ebi.uniprot.xmlparser.uniprot.EvidenceIndexMapper;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SCCommentConverter implements CommentConverter< SequenceCautionComment> {

	private final ObjectFactory xmlUniprotFactory;
	private final SCConflictConverter conflictConverter;
	private final EvidenceIndexMapper evRefMapper;
	private final SCPositionConverter positionConverter;

	public SCCommentConverter(EvidenceIndexMapper evRefMapper) {
		this(evRefMapper, new ObjectFactory());
	}

	public SCCommentConverter(EvidenceIndexMapper evRefMapper, ObjectFactory xmlUniprotFactory) {
		this.xmlUniprotFactory = xmlUniprotFactory;
		this.conflictConverter = new SCConflictConverter(xmlUniprotFactory);
		this.evRefMapper = evRefMapper;
		this.positionConverter = new SCPositionConverter(xmlUniprotFactory);
	}

	@Override
	public SequenceCautionComment fromXml(CommentType xmlObj) {
		if (xmlObj == null)
			return null;

		SequenceCautionCommentBuilder builder = new SequenceCautionCommentBuilder();
		CommentType.Conflict conflict = xmlObj.getConflict();
		SequenceCautionType scType = SequenceCautionType.UNKNOWN;
		if (conflict != null) {
			builder.sequence(conflictConverter.fromXml(conflict));
			scType = SequenceCautionType.typeOf(conflict.getType());
			builder.sequenceCautionType(scType);

		}
		if (!xmlObj.getText().isEmpty()) {
			String text = xmlObj.getText().get(0).getValue();
//			if(text.endsWith(".")){
//				text= text.substring(0, text.length() - 1);
//			}
			builder.note(text);
	
		}

		List<String> positions = xmlObj.getLocation().stream().map(positionConverter::fromXml)
				.collect(Collectors.toList());
		builder.positions(positions);
		if (positions.isEmpty() && scType == SequenceCautionType.FRAMESHIFT) {
			builder.positions(Arrays.asList("Several"));
		}
		if (!xmlObj.getEvidence().isEmpty()) {
			List<Evidence> evidences = evRefMapper.parseEvidenceIds(xmlObj.getEvidence());
			builder.evidences(evidences);
		}
		return builder.build();
	}

	@Override
	public CommentType toXml(SequenceCautionComment uniObj) {
		if (uniObj == null)
			return null;
		CommentType commentXML = xmlUniprotFactory.createCommentType();
		commentXML.setType(uniObj.getCommentType().toDisplayName().toLowerCase());
		// Sequence Conflict
		if (uniObj.getSequence() != null) {
			commentXML.setConflict(conflictConverter.toXml(uniObj.getSequence()));
		}

		if (uniObj.getSequenceCautionType() != null) {
			// set display name to lower case to match enumerated values from uniprot.xsd
			commentXML.getConflict().setType(uniObj.getSequenceCautionType().toDisplayName().toLowerCase());
		}
		if (!Strings.isNullOrEmpty(uniObj.getNote())) {
			EvidencedStringType text = xmlUniprotFactory.createEvidencedStringType();
			String note = uniObj.getNote();
//			if (!note.endsWith(".")) {
//				note += ".";
//			}
			text.setValue(note);
			commentXML.getText().add(text);
		}

		// Positions
		if (uniObj.getPositions() != null && !uniObj.getPositions().isEmpty()) {
			for (String position : uniObj.getPositions()) {
				final LocationType location = positionConverter.toXml(position);
				if (location.getPosition() != null && location.getPosition().getPosition() != null) {
					commentXML.getLocation().add(location);
				}
			}
		}

		// Evidences
		if (!uniObj.getEvidences().isEmpty()) {
			commentXML.getEvidence().addAll(evRefMapper.writeEvidences(uniObj.getEvidences()));
		}

		return commentXML;

	}

}

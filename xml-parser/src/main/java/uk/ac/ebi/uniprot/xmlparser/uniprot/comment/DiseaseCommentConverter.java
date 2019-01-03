package uk.ac.ebi.uniprot.xmlparser.uniprot.comment;

import java.util.List;
import java.util.stream.Collectors;

import uk.ac.ebi.uniprot.domain.uniprot.comment.Disease;
import uk.ac.ebi.uniprot.domain.uniprot.comment.DiseaseComment;
import uk.ac.ebi.uniprot.domain.uniprot.comment.Note;
import uk.ac.ebi.uniprot.domain.uniprot.comment.builder.DiseaseBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.comment.builder.DiseaseCommentBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.factory.CommentFactory;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.CommentType;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.ObjectFactory;
import uk.ac.ebi.uniprot.xmlparser.uniprot.EvidenceIndexMapper;
import uk.ac.ebi.uniprot.xmlparser.uniprot.EvidencedValueConverter;

public class DiseaseCommentConverter implements CommentConverter< DiseaseComment> {
	private final ObjectFactory xmlUniprotFactory;
	private final DiseaseConverter diseaseConverter;
	private final EvidencedValueConverter evValueConverter;
	private final EvidenceIndexMapper evRefMapper;

	public DiseaseCommentConverter(EvidenceIndexMapper evRefMapper) {
		this(evRefMapper, new ObjectFactory());
	}

	public DiseaseCommentConverter(EvidenceIndexMapper evRefMapper, ObjectFactory xmlUniprotFactory) {
		this.evRefMapper = evRefMapper;
		this.xmlUniprotFactory = xmlUniprotFactory;
		this.diseaseConverter = new DiseaseConverter(xmlUniprotFactory);
		evValueConverter = new EvidencedValueConverter(evRefMapper, xmlUniprotFactory, true);
	}

	@Override
	public DiseaseComment fromXml(CommentType xmlComment) {

		if (xmlComment == null)
			return null;

		DiseaseCommentBuilder builder = DiseaseCommentBuilder.newInstance();

		// We do not want to se disease as null in kraken objects, but as empty.
		// Control vocabulary checks are based only on name and id.
		if (xmlComment.getDisease() != null) {
			Disease disease = diseaseConverter.fromXml(xmlComment.getDisease());
			if (!xmlComment.getEvidence().isEmpty()) {
				List<Evidence> evidences = evRefMapper.parseEvidenceIds(xmlComment.getEvidence());
				disease = diseaseSetEvidence(disease, evidences);
				builder.disease(disease);
			} else {
				builder.disease(disease);
			}

		}
		if (!xmlComment.getText().isEmpty()) {
			Note note = CommentFactory.INSTANCE.createNote(
					xmlComment.getText().stream().map(evValueConverter::fromXml).collect(Collectors.toList()));
			builder.note(note);
		}
		return builder.build();
	}

	private Disease diseaseSetEvidence(Disease disease, List<Evidence> evidences) {
		DiseaseBuilder builder = DiseaseBuilder.newInstance();
		builder.acronym(disease.getAcronym()).description(disease.getDescription())
				.diseaseAc(disease.getDiseaseAccession()).diseaseId(disease.getDiseaseId())
				.reference(disease.getReference()).evidences(evidences);
		return builder.build();
	}

	@Override
	public CommentType toXml(DiseaseComment comment) {
		if (comment == null)
			return null;

		CommentType xmlComment = xmlUniprotFactory.createCommentType();

		// comment type
		xmlComment.setType(comment.getCommentType().toDisplayName().toLowerCase());

		if (comment.hasDefinedDisease()) {
			xmlComment.setDisease(diseaseConverter.toXml(comment.getDisease()));
			List<Evidence> evidenceIds = comment.getDisease().getEvidences();
			if ((evidenceIds != null) && !evidenceIds.isEmpty()) {
				List<Integer> evs = evRefMapper.writeEvidences(evidenceIds);
				if (!evs.isEmpty())
					xmlComment.getEvidence().addAll(evs);
			}

		}

		// Note
		Note note = comment.getNote();
		if ((note != null) && (!note.getTexts().isEmpty())) {
			note.getTexts().forEach(val -> xmlComment.getText().add(evValueConverter.toXml(val)));
		}
		return xmlComment;
	}

}

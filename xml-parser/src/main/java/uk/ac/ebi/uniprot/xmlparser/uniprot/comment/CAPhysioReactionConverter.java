package uk.ac.ebi.uniprot.xmlparser.uniprot.comment;

import java.util.List;

import uk.ac.ebi.uniprot.domain.DBCrossReference;
import uk.ac.ebi.uniprot.domain.uniprot.comment.PhysiologicalDirectionType;
import uk.ac.ebi.uniprot.domain.uniprot.comment.PhysiologicalReaction;
import uk.ac.ebi.uniprot.domain.uniprot.comment.ReactionReferenceType;
import uk.ac.ebi.uniprot.domain.uniprot.comment.builder.CatalyticActivityCommentBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.ObjectFactory;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.PhysiologicalReactionType;
import uk.ac.ebi.uniprot.xmlparser.Converter;
import uk.ac.ebi.uniprot.xmlparser.uniprot.EvidenceIndexMapper;

public class CAPhysioReactionConverter implements Converter<PhysiologicalReactionType, PhysiologicalReaction> {
	private final EvidenceIndexMapper evRefMapper;
	private final ObjectFactory xmlUniprotFactory;
	private final CAReactionReferenceConverter refConverter;

	public CAPhysioReactionConverter(EvidenceIndexMapper evRefMapper) {
		this(evRefMapper, new ObjectFactory());
	}

	public CAPhysioReactionConverter(EvidenceIndexMapper evRefMapper, ObjectFactory xmlUniprotFactory) {
		this.evRefMapper = evRefMapper;
		this.xmlUniprotFactory = xmlUniprotFactory;
		this.refConverter = new CAReactionReferenceConverter(xmlUniprotFactory);
	}

	@Override
	public PhysiologicalReaction fromXml(PhysiologicalReactionType xmlObj) {

		PhysiologicalDirectionType directionType =PhysiologicalDirectionType.typeOf(xmlObj.getDirection());
		DBCrossReference<ReactionReferenceType> reactionReference =
				refConverter.fromXml(xmlObj.getDbReference());
		// Evidences
		List<Evidence> evidences = evRefMapper.parseEvidenceIds(xmlObj.getEvidence());
		return CatalyticActivityCommentBuilder.createPhysiologicalReaction(directionType, reactionReference, evidences);
	}

	@Override
	public PhysiologicalReactionType toXml(PhysiologicalReaction uniObj) {
		PhysiologicalReactionType physioReactionType = xmlUniprotFactory.createPhysiologicalReactionType();
		physioReactionType.setDirection(uniObj.getDirectionType().toDisplayName());
		physioReactionType.setDbReference(refConverter.toXml(uniObj.getReactionReference()));
		List<Evidence> evidenceIds = uniObj.getEvidences();
		if ((evidenceIds != null) && !evidenceIds.isEmpty()) {
			List<Integer> evs = evRefMapper.writeEvidences(evidenceIds);
			if (!evs.isEmpty())
				physioReactionType.getEvidence().addAll(evs);
		}
		return physioReactionType;
	}

}

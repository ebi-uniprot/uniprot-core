package uk.ac.ebi.uniprot.xmlparser.uniprot.comment;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import uk.ac.ebi.uniprot.domain.DBCrossReference;
import uk.ac.ebi.uniprot.domain.ECNumber;
import uk.ac.ebi.uniprot.domain.impl.ECNumberImpl;
import uk.ac.ebi.uniprot.domain.uniprot.comment.Reaction;
import uk.ac.ebi.uniprot.domain.uniprot.comment.ReactionReferenceType;
import uk.ac.ebi.uniprot.domain.uniprot.comment.builder.CatalyticActivityCommentBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.DbReferenceType;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.ObjectFactory;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.ReactionType;
import uk.ac.ebi.uniprot.xmlparser.Converter;
import uk.ac.ebi.uniprot.xmlparser.uniprot.EvidenceIndexMapper;

public class CAReactionConverter implements Converter<ReactionType, Reaction> {
	private static final String EC = "EC";
	private final EvidenceIndexMapper evRefMapper;
	private final ObjectFactory xmlUniprotFactory;
	private final CAReactionReferenceConverter refConverter;

	public CAReactionConverter(EvidenceIndexMapper evRefMapper) {
		this(evRefMapper, new ObjectFactory());
	}

	public CAReactionConverter(EvidenceIndexMapper evRefMapper, ObjectFactory xmlUniprotFactory) {
		this.evRefMapper = evRefMapper;
		this.xmlUniprotFactory = xmlUniprotFactory;
		this.refConverter = new CAReactionReferenceConverter(xmlUniprotFactory);
	}

	@Override
	public Reaction fromXml(ReactionType xmlObj) {
		String name = xmlObj.getText();
		List<DBCrossReference<ReactionReferenceType>> references = xmlObj.getDbReference().stream()
				.filter(val -> !EC.equals(val.getType())).map(val -> refConverter.fromXml(val))
				.collect(Collectors.toList());

		Optional<DbReferenceType> opEc = xmlObj.getDbReference().stream().filter(val -> EC.equals(val.getType()))
				.findFirst();
		ECNumber ecNumber = null;
		if (opEc.isPresent()) {
			ecNumber = new ECNumberImpl(opEc.get().getId());
		}
		List<Evidence> evidences = evRefMapper.parseEvidenceIds(xmlObj.getEvidence());

		return CatalyticActivityCommentBuilder.createReaction(name, references, ecNumber, evidences);
	}

	@Override
	public ReactionType toXml(Reaction uniObj) {
		ReactionType reactionType = this.xmlUniprotFactory.createReactionType();
		reactionType.setText(uniObj.getName());
		reactionType.getDbReference().addAll(uniObj.getReactionReferences().stream().map(val -> refConverter.toXml(val))
				.collect(Collectors.toList()));
		if (uniObj.getEcNumber() != null) {
			DbReferenceType dbReference = this.xmlUniprotFactory.createDbReferenceType();
			dbReference.setType(EC);
			dbReference.setId(uniObj.getEcNumber().getValue());
			reactionType.getDbReference().add(dbReference);
		}
		List<Evidence> evidenceIds = uniObj.getEvidences();
		if ((evidenceIds != null) && !evidenceIds.isEmpty()) {
			List<Integer> evs = evRefMapper.writeEvidences(evidenceIds);
			if (!evs.isEmpty())
				reactionType.getEvidence().addAll(evs);
		}
		return reactionType;
	}
}

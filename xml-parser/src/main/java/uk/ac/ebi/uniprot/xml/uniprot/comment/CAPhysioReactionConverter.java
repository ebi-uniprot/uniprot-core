package uk.ac.ebi.uniprot.xml.uniprot.comment;

import uk.ac.ebi.uniprot.xml.Converter;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.ObjectFactory;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.PhysiologicalReactionType;
import uk.ac.ebi.uniprot.xml.uniprot.EvidenceIndexMapper;

import java.util.List;

import org.uniprot.core.DBCrossReference;
import org.uniprot.core.uniprot.comment.PhysiologicalDirectionType;
import org.uniprot.core.uniprot.comment.PhysiologicalReaction;
import org.uniprot.core.uniprot.comment.ReactionReferenceType;
import org.uniprot.core.uniprot.comment.builder.PhysiologicalReactionBuilder;
import org.uniprot.core.uniprot.evidence.Evidence;

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

        PhysiologicalDirectionType directionType = PhysiologicalDirectionType.typeOf(xmlObj.getDirection());
        DBCrossReference<ReactionReferenceType> reactionReference =
                refConverter.fromXml(xmlObj.getDbReference());
        // Evidences
        List<Evidence> evidences = evRefMapper.parseEvidenceIds(xmlObj.getEvidence());
        return new PhysiologicalReactionBuilder()
                .directionType(directionType)
                .reactionReference(reactionReference)
                .evidences(evidences)
                .build();
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

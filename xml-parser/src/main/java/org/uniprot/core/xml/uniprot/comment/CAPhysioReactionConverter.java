package org.uniprot.core.xml.uniprot.comment;

import org.uniprot.core.CrossReference;
import org.uniprot.core.uniprotkb.comment.PhysiologicalDirectionType;
import org.uniprot.core.uniprotkb.comment.PhysiologicalReaction;
import org.uniprot.core.uniprotkb.comment.ReactionDatabase;
import org.uniprot.core.uniprotkb.comment.impl.PhysiologicalReactionBuilder;
import org.uniprot.core.uniprotkb.evidence.Evidence;
import org.uniprot.core.xml.Converter;
import org.uniprot.core.xml.jaxb.uniprot.ObjectFactory;
import org.uniprot.core.xml.jaxb.uniprot.PhysiologicalReactionType;
import org.uniprot.core.xml.uniprot.EvidenceIndexMapper;

import java.util.List;

public class CAPhysioReactionConverter
        implements Converter<PhysiologicalReactionType, PhysiologicalReaction> {
    private final EvidenceIndexMapper evRefMapper;
    private final ObjectFactory xmlUniprotFactory;
    private final CAReactionReferenceConverter refConverter;

    public CAPhysioReactionConverter(EvidenceIndexMapper evRefMapper) {
        this(evRefMapper, new ObjectFactory());
    }

    public CAPhysioReactionConverter(
            EvidenceIndexMapper evRefMapper, ObjectFactory xmlUniprotFactory) {
        this.evRefMapper = evRefMapper;
        this.xmlUniprotFactory = xmlUniprotFactory;
        this.refConverter = new CAReactionReferenceConverter(xmlUniprotFactory);
    }

    @Override
    public PhysiologicalReaction fromXml(PhysiologicalReactionType xmlObj) {

        PhysiologicalDirectionType directionType =
                PhysiologicalDirectionType.typeOf(xmlObj.getDirection());
        CrossReference<ReactionDatabase> reactionReference =
                refConverter.fromXml(xmlObj.getDbReference());
        // Evidences
        List<Evidence> evidences = evRefMapper.parseEvidenceIds(xmlObj.getEvidence());
        return new PhysiologicalReactionBuilder()
                .directionType(directionType)
                .reactionCrossReference(reactionReference)
                .evidencesSet(evidences)
                .build();
    }

    @Override
    public PhysiologicalReactionType toXml(PhysiologicalReaction uniObj) {
        PhysiologicalReactionType physioReactionType =
                xmlUniprotFactory.createPhysiologicalReactionType();
        physioReactionType.setDirection(uniObj.getDirectionType().getDisplayName());
        physioReactionType.setDbReference(refConverter.toXml(uniObj.getReactionCrossReference()));
        List<Evidence> evidenceIds = uniObj.getEvidences();
        if ((evidenceIds != null) && !evidenceIds.isEmpty()) {
            List<Integer> evs = evRefMapper.writeEvidences(evidenceIds);
            if (!evs.isEmpty()) physioReactionType.getEvidence().addAll(evs);
        }
        return physioReactionType;
    }
}

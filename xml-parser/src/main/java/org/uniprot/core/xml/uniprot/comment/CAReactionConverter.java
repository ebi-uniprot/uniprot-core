package org.uniprot.core.xml.uniprot.comment;

import org.uniprot.core.CrossReference;
import org.uniprot.core.ECNumber;
import org.uniprot.core.impl.ECNumberBuilder;
import org.uniprot.core.uniprotkb.comment.Reaction;
import org.uniprot.core.uniprotkb.comment.ReactionDatabase;
import org.uniprot.core.uniprotkb.comment.impl.ReactionBuilder;
import org.uniprot.core.uniprotkb.evidence.Evidence;
import org.uniprot.core.xml.Converter;
import org.uniprot.core.xml.jaxb.uniprot.DbReferenceType;
import org.uniprot.core.xml.jaxb.uniprot.ObjectFactory;
import org.uniprot.core.xml.jaxb.uniprot.ReactionType;
import org.uniprot.core.xml.uniprot.EvidenceIndexMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        List<CrossReference<ReactionDatabase>> references =
                xmlObj.getDbReference().stream()
                        .filter(val -> !EC.equals(val.getType()))
                        .map(val -> refConverter.fromXml(val))
                        .collect(Collectors.toList());

        Optional<DbReferenceType> opEc =
                xmlObj.getDbReference().stream()
                        .filter(val -> EC.equals(val.getType()))
                        .findFirst();
        ECNumber ecNumber = null;
        if (opEc.isPresent()) {
            ecNumber = new ECNumberBuilder(opEc.get().getId()).build();
        }
        List<Evidence> evidences = evRefMapper.parseEvidenceIds(xmlObj.getEvidence());

        return new ReactionBuilder()
                .name(name)
                .reactionCrossReferencesSet(references)
                .ecNumber(ecNumber)
                .evidencesSet(evidences)
                .build();
    }

    @Override
    public ReactionType toXml(Reaction uniObj) {
        ReactionType reactionType = this.xmlUniprotFactory.createReactionType();
        reactionType.setText(uniObj.getName());
        reactionType
                .getDbReference()
                .addAll(
                        uniObj.getReactionCrossReferences().stream()
                                .map(val -> refConverter.toXml(val))
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
            if (!evs.isEmpty()) reactionType.getEvidence().addAll(evs);
        }
        return reactionType;
    }
}

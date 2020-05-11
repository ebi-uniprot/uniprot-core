package org.uniprot.core.xml.uniprot.comment;

import com.google.common.base.Strings;

import org.uniprot.core.uniprotkb.comment.CatalyticActivityComment;
import org.uniprot.core.uniprotkb.comment.PhysiologicalReaction;
import org.uniprot.core.uniprotkb.comment.Reaction;
import org.uniprot.core.uniprotkb.comment.impl.CatalyticActivityCommentBuilder;
import org.uniprot.core.xml.jaxb.uniprot.CommentType;
import org.uniprot.core.xml.jaxb.uniprot.MoleculeType;
import org.uniprot.core.xml.jaxb.uniprot.ObjectFactory;
import org.uniprot.core.xml.uniprot.EvidenceIndexMapper;

import java.util.List;
import java.util.stream.Collectors;

public class CatalyticActivityCommentConverter
        implements CommentConverter<CatalyticActivityComment> {
    private final ObjectFactory xmlUniprotFactory;
    private final CAReactionConverter reactionConverter;
    private final CAPhysioReactionConverter physioReactionConverter;

    public CatalyticActivityCommentConverter(EvidenceIndexMapper evRefMapper) {
        this(evRefMapper, new ObjectFactory());
    }

    public CatalyticActivityCommentConverter(
            EvidenceIndexMapper evRefMapper, ObjectFactory xmlUniprotFactory) {
        this.xmlUniprotFactory = xmlUniprotFactory;
        this.reactionConverter = new CAReactionConverter(evRefMapper, xmlUniprotFactory);
        this.physioReactionConverter =
                new CAPhysioReactionConverter(evRefMapper, xmlUniprotFactory);
    }

    @Override
    public CatalyticActivityComment fromXml(CommentType xmlObj) {
        Reaction reaction = reactionConverter.fromXml(xmlObj.getReaction());
        List<PhysiologicalReaction> physioReactions =
                xmlObj.getPhysiologicalReaction().stream()
                        .map(physioReactionConverter::fromXml)
                        .collect(Collectors.toList());

        CatalyticActivityCommentBuilder builder = new CatalyticActivityCommentBuilder();
        // Molecule
        if (xmlObj.getMolecule() != null) {
            builder.molecule(xmlObj.getMolecule().getValue());
        }

        return builder.reaction(reaction).physiologicalReactionsSet(physioReactions).build();
    }

    @Override
    public CommentType toXml(CatalyticActivityComment uniObj) {
        CommentType commentType = xmlUniprotFactory.createCommentType();
        commentType.setType(uniObj.getCommentType().getDisplayName().toLowerCase());
        if (!Strings.isNullOrEmpty(uniObj.getMolecule())) {
            MoleculeType mol = xmlUniprotFactory.createMoleculeType();
            mol.setValue(uniObj.getMolecule());
            commentType.setMolecule(mol);
        }

        commentType.setReaction(reactionConverter.toXml(uniObj.getReaction()));
        uniObj.getPhysiologicalReactions()
                .forEach(
                        val ->
                                commentType
                                        .getPhysiologicalReaction()
                                        .add(physioReactionConverter.toXml(val)));

        return commentType;
    }
}

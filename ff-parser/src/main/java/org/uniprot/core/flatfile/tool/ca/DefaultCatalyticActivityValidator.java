package org.uniprot.core.flatfile.tool.ca;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.uniprot.core.CrossReference;
import org.uniprot.core.ECNumber;
import org.uniprot.core.impl.CrossReferenceBuilder;
import org.uniprot.core.uniprotkb.comment.CatalyticActivityComment;
import org.uniprot.core.uniprotkb.comment.PhysiologicalDirectionType;
import org.uniprot.core.uniprotkb.comment.PhysiologicalReaction;
import org.uniprot.core.uniprotkb.comment.Reaction;
import org.uniprot.core.uniprotkb.comment.ReactionDatabase;
import org.uniprot.core.uniprotkb.comment.impl.CatalyticActivityCommentBuilder;
import org.uniprot.core.uniprotkb.comment.impl.PhysiologicalReactionBuilder;
import org.uniprot.core.uniprotkb.comment.impl.ReactionBuilder;
import org.uniprot.core.util.Utils;

public class DefaultCatalyticActivityValidator implements CatalyticActivityValidator {
    private static final String RHEA_PREFIX = "RHEA:";
    private final CatalyticActivityRepository repository;

    public DefaultCatalyticActivityValidator(CatalyticActivityRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<CatalyticActivityComment> validateAndConvert(CatalyticActivityComment comment) {
        Reaction reaction = comment.getReaction();
        if (reaction.getReactionCrossReferences().isEmpty()) {
            CatalyticActivity ca = repository.getByOldText(reaction.getName());
            if (Objects.isNull(ca)) {
                return Optional.empty();
            } else {
                ECNumber ec = reaction.getEcNumber();
                if (!Utils.notNull(ec) || ca.getEcs().contains(ec.getValue())) {
                    return Optional.ofNullable(comment);
                } else {
                    return Optional.empty();
                }
            }
        }
        Optional<CrossReference<ReactionDatabase>> rheaRef =
                reaction.getReactionCrossReferences().stream()
                        .filter(
                                val ->
                                        (val.getDatabase().equals(ReactionDatabase.RHEA))
                                                && val.getId().startsWith(RHEA_PREFIX))
                        .findFirst();
        if (!rheaRef.isPresent()) {
            return Optional.empty();
        }
        CatalyticActivity ca = repository.getByRheaId(rheaRef.get().getId());
        if (ca == null) {
            return Optional.empty();
        }
        CatalyticActivityCommentBuilder builder = new CatalyticActivityCommentBuilder();
        builder.reaction(transformReaction(reaction, ca, rheaRef.get()));

        List<PhysiologicalReaction> physioReactions =
                comment.getPhysiologicalReactions().stream()
                        .map(val -> transformPhysiologicalReaction(ca, val))
                        .filter(Objects::nonNull)
                        .collect(Collectors.toList());
        builder.physiologicalReactionsSet(physioReactions);

        return Optional.ofNullable(builder.build());
    }

    private Reaction transformReaction(
            Reaction reaction, CatalyticActivity ca, CrossReference<ReactionDatabase> rhea) {
        ReactionBuilder builder = new ReactionBuilder();
        builder.name(ca.getText());
        List<CrossReference<ReactionDatabase>> xrefs = new ArrayList<>();
        xrefs.add(createReactRef(rhea.getDatabase(), rhea.getId()));
        ca.getReactantIds().stream().map(this::parseReactantId).forEach(xrefs::add);
        builder.reactionCrossReferencesSet(xrefs);
        if (Utils.notNull(reaction.getEcNumber())
                && ca.getEcs().contains(reaction.getEcNumber().getValue())) {
            builder.ecNumber(reaction.getEcNumber().getValue());
        }
        builder.evidencesSet(reaction.getEvidences());
        return builder.build();
    }

    private PhysiologicalReaction transformPhysiologicalReaction(
            CatalyticActivity ca, PhysiologicalReaction physioReaction) {
        CrossReference<ReactionDatabase> xref = physioReaction.getReactionCrossReference();
        if ((xref == null) || ReactionDatabase.RHEA != xref.getDatabase()) {
            return null;
        }
        PhysiologicalDirectionType type = physioReaction.getDirectionType();
        if ((type == PhysiologicalDirectionType.LEFT_TO_RIGHT)
                && (xref.getId().equals(ca.getRheaLr()))) {
            return PhysiologicalReactionBuilder.from(physioReaction).build();
        } else if ((type == PhysiologicalDirectionType.RIGHT_TO_LEFT)
                && (xref.getId().equals(ca.getRheaRl()))) {
            return PhysiologicalReactionBuilder.from(physioReaction).build();
        } else return null;
    }

    private CrossReference<ReactionDatabase> parseReactantId(String reactantId) {
        int index = reactantId.indexOf(':');
        String type = reactantId.substring(0, index);
        String id = reactantId.substring(index + 1);
        return createReactRef(ReactionDatabase.typeOf(type), id);
    }

    private CrossReference<ReactionDatabase> createReactRef(ReactionDatabase type, String id) {
        return new CrossReferenceBuilder<ReactionDatabase>().database(type).id(id).build();
    }
}

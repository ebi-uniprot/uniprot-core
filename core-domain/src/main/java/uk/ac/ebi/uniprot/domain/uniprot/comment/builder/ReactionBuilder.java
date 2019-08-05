package uk.ac.ebi.uniprot.domain.uniprot.comment.builder;

import uk.ac.ebi.uniprot.domain.Builder;
import uk.ac.ebi.uniprot.domain.DBCrossReference;
import uk.ac.ebi.uniprot.domain.ECNumber;
import uk.ac.ebi.uniprot.domain.impl.ECNumberImpl;
import uk.ac.ebi.uniprot.domain.uniprot.comment.Reaction;
import uk.ac.ebi.uniprot.domain.uniprot.comment.ReactionReferenceType;
import uk.ac.ebi.uniprot.domain.uniprot.comment.impl.ReactionImpl;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;

import static org.uniprot.core.common.Utils.nonNullAdd;
import static org.uniprot.core.common.Utils.nonNullList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created 15/01/19
 *
 * @author Edd
 */
public final class ReactionBuilder implements Builder<ReactionBuilder, Reaction> {
    private String name;
    private ECNumber ecNumber;
    private List<DBCrossReference<ReactionReferenceType>> reactionReferences = new ArrayList<>();
    private List<Evidence> evidences = new ArrayList<>();

    public ReactionBuilder name(String name) {
        this.name = name;
        return this;
    }

    public ReactionBuilder references(List<DBCrossReference<ReactionReferenceType>> reactionReferences) {
        this.reactionReferences = nonNullList(reactionReferences);
        return this;
    }

    public ReactionBuilder addReactionReference(DBCrossReference<ReactionReferenceType> reactionReference) {
        nonNullAdd(reactionReference, this.reactionReferences);
        return this;
    }

    public ReactionBuilder ecNumber(String ecNumber) {
        this.ecNumber = new ECNumberImpl(ecNumber);
        return this;
    }

    public ReactionBuilder ecNumber(ECNumber ecNumber) {
        this.ecNumber = ecNumber;
        return this;
    }

    public ReactionBuilder evidences(List<Evidence> evidences) {
        this.evidences = nonNullList(evidences);
        return this;
    }

    public ReactionBuilder addEvidences(Evidence evidence) {
        nonNullAdd(evidence, this.evidences);
        return this;
    }

    public ReactionImpl build() {
        return new ReactionImpl(name, reactionReferences, ecNumber, evidences);
    }

    @Override
    public ReactionBuilder from(Reaction instance) {
        evidences.clear();
        reactionReferences.clear();
        return this
                .ecNumber(instance.getEcNumber())
                .evidences(instance.getEvidences())
                .name(instance.getName())
                .references(instance.getReactionReferences());
    }
}

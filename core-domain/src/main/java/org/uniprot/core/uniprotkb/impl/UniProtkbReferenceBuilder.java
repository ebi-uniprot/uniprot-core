package org.uniprot.core.uniprotkb.impl;

import static org.uniprot.core.util.Utils.addOrIgnoreNull;
import static org.uniprot.core.util.Utils.modifiableList;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import org.uniprot.core.Builder;
import org.uniprot.core.citation.Citation;
import org.uniprot.core.uniprotkb.ReferenceComment;
import org.uniprot.core.uniprotkb.UniProtkbReference;
import org.uniprot.core.uniprotkb.evidence.Evidence;

/**
 * Created 18/01/19
 *
 * @author Edd
 */
public class UniProtkbReferenceBuilder implements Builder<UniProtkbReference> {
    private Citation citation;
    private List<String> referencePositions = new ArrayList<>();
    private List<ReferenceComment> referenceComments = new ArrayList<>();
    private List<Evidence> evidences = new ArrayList<>();

    @Override
    public @Nonnull UniProtkbReference build() {
        return new UniProtkbReferenceImpl(
                citation, referencePositions, referenceComments, evidences);
    }

    public static @Nonnull UniProtkbReferenceBuilder from(@Nonnull UniProtkbReference instance) {
        return new UniProtkbReferenceBuilder()
                .citation(instance.getCitation())
                .evidencesSet(instance.getEvidences())
                .referenceCommentsSet(instance.getReferenceComments())
                .referencePositionsSet(instance.getReferencePositions());
    }

    public @Nonnull UniProtkbReferenceBuilder citation(Citation citation) {
        this.citation = citation;
        return this;
    }

    public @Nonnull UniProtkbReferenceBuilder referencePositionsSet(
            List<String> referencePositions) {
        this.referencePositions = modifiableList(referencePositions);
        return this;
    }

    public @Nonnull UniProtkbReferenceBuilder referencePositionsAdd(String referencePosition) {
        addOrIgnoreNull(referencePosition, this.referencePositions);
        return this;
    }

    public @Nonnull UniProtkbReferenceBuilder referenceCommentsSet(
            List<ReferenceComment> referenceComments) {
        this.referenceComments = modifiableList(referenceComments);
        return this;
    }

    public @Nonnull UniProtkbReferenceBuilder referenceCommentsAdd(
            ReferenceComment referenceComment) {
        addOrIgnoreNull(referenceComment, this.referenceComments);
        return this;
    }

    public @Nonnull UniProtkbReferenceBuilder evidencesSet(List<Evidence> evidences) {
        this.evidences = modifiableList(evidences);
        return this;
    }

    public @Nonnull UniProtkbReferenceBuilder evidencesAdd(Evidence evidence) {
        addOrIgnoreNull(evidence, this.evidences);
        return this;
    }
}

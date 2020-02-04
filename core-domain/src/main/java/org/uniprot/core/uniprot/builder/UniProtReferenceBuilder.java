package org.uniprot.core.uniprot.builder;

import static org.uniprot.core.util.Utils.addOrIgnoreNull;
import static org.uniprot.core.util.Utils.modifiableList;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import org.uniprot.core.Builder;
import org.uniprot.core.citation.Citation;
import org.uniprot.core.uniprot.ReferenceComment;
import org.uniprot.core.uniprot.UniProtReference;
import org.uniprot.core.uniprot.evidence.Evidence;
import org.uniprot.core.uniprot.impl.UniProtReferenceImpl;

/**
 * Created 18/01/19
 *
 * @author Edd
 */
public class UniProtReferenceBuilder implements Builder<UniProtReference> {
    private Citation citation;
    private List<String> referencePositions = new ArrayList<>();
    private List<ReferenceComment> referenceComments = new ArrayList<>();
    private List<Evidence> evidences = new ArrayList<>();

    @Override
    public @Nonnull UniProtReference build() {
        return new UniProtReferenceImpl(citation, referencePositions, referenceComments, evidences);
    }

    public static @Nonnull UniProtReferenceBuilder from(@Nonnull UniProtReference instance) {
        return new UniProtReferenceBuilder()
                .citation(instance.getCitation())
                .evidencesSet(instance.getEvidences())
                .referenceCommentsSet(instance.getReferenceComments())
                .referencePositionsSet(instance.getReferencePositions());
    }

    public @Nonnull UniProtReferenceBuilder citation(Citation citation) {
        this.citation = citation;
        return this;
    }

    public @Nonnull UniProtReferenceBuilder referencePositionsSet(List<String> referencePositions) {
        this.referencePositions = modifiableList(referencePositions);
        return this;
    }

    public @Nonnull UniProtReferenceBuilder referencePositionsAdd(String referencePosition) {
        addOrIgnoreNull(referencePosition, this.referencePositions);
        return this;
    }

    public @Nonnull UniProtReferenceBuilder referenceCommentsSet(
            List<ReferenceComment> referenceComments) {
        this.referenceComments = modifiableList(referenceComments);
        return this;
    }

    public @Nonnull UniProtReferenceBuilder referenceCommentsAdd(
            ReferenceComment referenceComment) {
        addOrIgnoreNull(referenceComment, this.referenceComments);
        return this;
    }

    public @Nonnull UniProtReferenceBuilder evidencesSet(List<Evidence> evidences) {
        this.evidences = modifiableList(evidences);
        return this;
    }

    public @Nonnull UniProtReferenceBuilder evidencesAdd(Evidence evidence) {
        addOrIgnoreNull(evidence, this.evidences);
        return this;
    }
}

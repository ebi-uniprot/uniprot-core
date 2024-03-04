package org.uniprot.core.uniprotkb.impl;

import static org.uniprot.core.util.Utils.addOrIgnoreNull;
import static org.uniprot.core.util.Utils.modifiableList;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import org.uniprot.core.Builder;
import org.uniprot.core.citation.Citation;
import org.uniprot.core.uniprotkb.ReferenceComment;
import org.uniprot.core.uniprotkb.UniProtKBReference;
import org.uniprot.core.uniprotkb.evidence.Evidence;

/**
 * Created 18/01/19
 *
 * @author Edd
 */
public class UniProtKBReferenceBuilder implements Builder<UniProtKBReference> {
    private int referenceNumber;
    private Citation citation;
    private List<String> referencePositions = new ArrayList<>();
    private List<ReferenceComment> referenceComments = new ArrayList<>();
    private List<Evidence> evidences = new ArrayList<>();

    @Override
    public @Nonnull UniProtKBReference build() {
        return new UniProtKBReferenceImpl(
                referenceNumber, citation, referencePositions, referenceComments, evidences);
    }

    public static @Nonnull UniProtKBReferenceBuilder from(@Nonnull UniProtKBReference instance) {
        return new UniProtKBReferenceBuilder()
                .referenceNumber(instance.getReferenceNumber())
                .citation(instance.getCitation())
                .evidencesSet(instance.getEvidences())
                .referenceCommentsSet(instance.getReferenceComments())
                .referencePositionsSet(instance.getReferencePositions());
    }

    public @Nonnull UniProtKBReferenceBuilder referenceNumber(int referenceNumber) {
        this.referenceNumber = referenceNumber;
        return this;
    }

    public @Nonnull UniProtKBReferenceBuilder citation(Citation citation) {
        this.citation = citation;
        return this;
    }

    public @Nonnull UniProtKBReferenceBuilder referencePositionsSet(
            List<String> referencePositions) {
        this.referencePositions = modifiableList(referencePositions);
        return this;
    }

    public @Nonnull UniProtKBReferenceBuilder referencePositionsAdd(String referencePosition) {
        addOrIgnoreNull(referencePosition, this.referencePositions);
        return this;
    }

    public @Nonnull UniProtKBReferenceBuilder referenceCommentsSet(
            List<ReferenceComment> referenceComments) {
        this.referenceComments = modifiableList(referenceComments);
        return this;
    }

    public @Nonnull UniProtKBReferenceBuilder referenceCommentsAdd(
            ReferenceComment referenceComment) {
        addOrIgnoreNull(referenceComment, this.referenceComments);
        return this;
    }

    public @Nonnull UniProtKBReferenceBuilder evidencesSet(List<Evidence> evidences) {
        this.evidences = modifiableList(evidences);
        return this;
    }

    public @Nonnull UniProtKBReferenceBuilder evidencesAdd(Evidence evidence) {
        addOrIgnoreNull(evidence, this.evidences);
        return this;
    }
}

package org.uniprot.core.uniprot.builder;

import static org.uniprot.core.util.Utils.addOrIgnoreNull;
import static org.uniprot.core.util.Utils.nonNullList;

import java.util.ArrayList;
import java.util.List;

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
public class UniProtReferenceBuilder implements Builder<UniProtReferenceBuilder, UniProtReference> {
    private Citation citation;
    private List<String> referencePositions = new ArrayList<>();
    private List<ReferenceComment> referenceComments = new ArrayList<>();
    private List<Evidence> evidences = new ArrayList<>();

    @Override
    public UniProtReference build() {
        return new UniProtReferenceImpl(citation, referencePositions, referenceComments, evidences);
    }

    @Override
    public UniProtReferenceBuilder from(UniProtReference instance) {
        referenceComments.clear();
        referencePositions.clear();
        evidences.clear();
        return this
                .citation(instance.getCitation())
                .evidences(instance.getEvidences())
                .comments(instance.getReferenceComments())
                .positions(instance.getReferencePositions());
    }

    public UniProtReferenceBuilder citation(Citation citation) {
        this.citation = citation;
        return this;
    }

    public UniProtReferenceBuilder positions(List<String> referencePositions) {
        this.referencePositions = nonNullList(referencePositions);
        return this;
    }

    public UniProtReferenceBuilder addPositions(String referencePosition) {
        addOrIgnoreNull(referencePosition, this.referencePositions);
        return this;
    }

    public UniProtReferenceBuilder comments(List<ReferenceComment> referenceComments) {
        this.referenceComments = nonNullList(referenceComments);
        return this;
    }

    public UniProtReferenceBuilder addComment(ReferenceComment referenceComment) {
        addOrIgnoreNull(referenceComment, this.referenceComments);
        return this;
    }

    public UniProtReferenceBuilder evidences(List<Evidence> evidences) {
        this.evidences = nonNullList(evidences);
        return this;
    }

    public UniProtReferenceBuilder evidence(Evidence evidence) {
        addOrIgnoreNull(evidence, this.evidences);
        return this;
    }
}

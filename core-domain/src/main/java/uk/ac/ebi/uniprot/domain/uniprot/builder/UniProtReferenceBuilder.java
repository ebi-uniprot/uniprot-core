package uk.ac.ebi.uniprot.domain.uniprot.builder;

import uk.ac.ebi.uniprot.domain.Builder;
import uk.ac.ebi.uniprot.domain.citation.Citation;
import uk.ac.ebi.uniprot.domain.uniprot.ReferenceComment;
import uk.ac.ebi.uniprot.domain.uniprot.UniProtReference;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.impl.UniProtReferenceImpl;

import java.util.ArrayList;
import java.util.List;

import static uk.ac.ebi.uniprot.common.Utils.nonNullAdd;
import static uk.ac.ebi.uniprot.common.Utils.nonNullList;

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
        nonNullAdd(referencePosition, this.referencePositions);
        return this;
    }

    public UniProtReferenceBuilder comments(List<ReferenceComment> referenceComments) {
        this.referenceComments = nonNullList(referenceComments);
        return this;
    }

    public UniProtReferenceBuilder addComment(ReferenceComment referenceComment) {
        nonNullAdd(referenceComment, this.referenceComments);
        return this;
    }

    public UniProtReferenceBuilder evidences(List<Evidence> evidences) {
        this.evidences = nonNullList(evidences);
        return this;
    }

    public UniProtReferenceBuilder evidence(Evidence evidence) {
        nonNullAdd(evidence, this.evidences);
        return this;
    }
}

package uk.ac.ebi.uniprot.domain.uniprot.builder;

import uk.ac.ebi.uniprot.domain.Builder2;
import uk.ac.ebi.uniprot.domain.citation.Citation;
import uk.ac.ebi.uniprot.domain.uniprot.ReferenceComment;
import uk.ac.ebi.uniprot.domain.uniprot.UniProtReference;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.impl.UniProtReferenceImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * Created 18/01/19
 *
 * @author Edd
 */
public class UniProtReferenceBuilder implements Builder2<UniProtReferenceBuilder, UniProtReference> {
    private Citation citation;
    private List<String> referencePositions = new ArrayList<>();
    private List<ReferenceComment> referenceComments = new ArrayList<>();
    private List<Evidence> evidences = new ArrayList<>();

    @Override
    public UniProtReference build() {
        return new UniProtReferenceImpl(this);
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
        this.referencePositions.addAll(referencePositions);
        return this;
    }

    public UniProtReferenceBuilder addPositions(String referencePosition) {
        this.referencePositions.add(referencePosition);
        return this;
    }

    public UniProtReferenceBuilder comments(List<ReferenceComment> referenceComments) {
        this.referenceComments.addAll(referenceComments);
        return this;
    }

    public UniProtReferenceBuilder addComment(ReferenceComment referenceComment) {
        this.referenceComments.add(referenceComment);
        return this;
    }

    public UniProtReferenceBuilder evidences(List<Evidence> evidences) {
        this.evidences.addAll(evidences);
        return this;
    }

    public UniProtReferenceBuilder evidence(Evidence evidence) {
        this.evidences.add(evidence);
        return this;
    }

    public Citation getCitation() {
        return citation;
    }

    public List<String> getPositions() {
        return referencePositions;
    }

    public List<ReferenceComment> getComments() {
        return referenceComments;
    }

    public List<Evidence> getEvidences() {
        return evidences;
    }
}

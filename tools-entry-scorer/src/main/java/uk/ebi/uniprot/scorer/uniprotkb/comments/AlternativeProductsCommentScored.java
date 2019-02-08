package uk.ebi.uniprot.scorer.uniprotkb.comments;

import uk.ac.ebi.uniprot.domain.uniprot.comment.APIsoform;
import uk.ac.ebi.uniprot.domain.uniprot.comment.AlternativeProductsComment;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.EvidenceType;
import uk.ebi.uniprot.scorer.uniprotkb.Consensus;
import uk.ebi.uniprot.scorer.uniprotkb.ScoreUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by IntelliJ IDEA. User: spatient Date: 01-Mar-2010 Time: 14:32:51 To change this template use File | Settings
 * | File Templates.
 */
public class AlternativeProductsCommentScored extends CommentScoredAbstr {
    private final AlternativeProductsComment comment;

    public AlternativeProductsCommentScored(AlternativeProductsComment copy, List<EvidenceType> evidenceTypes) {
        super(copy.getCommentType(), evidenceTypes);
        this.comment = copy;
    }

    public AlternativeProductsCommentScored(AlternativeProductsComment copy) {
        this(copy, null);
    }

    @Override
    public double score() {
        double score = 0;
        score += comment.getIsoforms().stream()
                .filter(this::hasEvidence)
                .collect(Collectors.toList()).size() * 3;
        return score;
    }

    private boolean hasEvidence(APIsoform isoform) {
        List<Evidence> evidences = new ArrayList<>(isoform.getName().getEvidences());
        isoform.getSynonyms().forEach(val -> evidences.addAll(val.getEvidences()));
        isoform.getNote().getTexts().forEach(val -> evidences.addAll(val.getEvidences()));
        return ScoreUtil.hasEvidence(evidences, evidenceTypes);
    }

    @Override
    public Consensus consensus() {
        return Consensus.COMPLEX;
    }
}

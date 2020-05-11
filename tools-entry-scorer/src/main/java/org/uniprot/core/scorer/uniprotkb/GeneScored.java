package org.uniprot.core.scorer.uniprotkb;

import org.uniprot.core.gene.Gene;
import org.uniprot.core.uniprotkb.evidence.EvidenceDatabase;
import org.uniprot.core.uniprotkb.evidence.HasEvidences;

import java.util.List;

/**
 * Created by IntelliJ IDEA. User: spatient Date: 01-Mar-2010 Time: 13:37:49 To change this template
 * use File | Settings | File Templates.
 */
public class GeneScored implements HasScore {
    private final Gene gene;
    private final List<EvidenceDatabase> evidenceDatabases;

    public GeneScored(Gene gene, List<EvidenceDatabase> evidenceDatabases) {
        this.gene = gene;
        this.evidenceDatabases = evidenceDatabases;
    }

    public GeneScored(Gene gene) {
        this(gene, null);
    }

    @Override
    public double score() {

        double score = 0;

        // 2 for a genename
        if (gene.getGeneName() != null
                && gene.getGeneName().getValue().length() > 0
                && ScoreUtil.hasEvidence(gene.getGeneName().getEvidences(), evidenceDatabases)) {
            score += 2;
        }
        for (HasEvidences he : gene.getSynonyms()) {
            if (ScoreUtil.hasEvidence(he.getEvidences(), evidenceDatabases)) score += 2;
        }

        return score;
    }

    @Override
    public Consensus consensus() {
        return Consensus.COMPLEX;
    }
}

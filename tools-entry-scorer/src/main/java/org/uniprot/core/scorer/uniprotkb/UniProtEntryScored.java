package org.uniprot.core.scorer.uniprotkb;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.uniprot.core.cv.xdb.UniProtXDbTypeDetail;
import org.uniprot.core.gene.Gene;
import org.uniprot.core.scorer.uniprotkb.comments.CommentScored;
import org.uniprot.core.scorer.uniprotkb.comments.CommentScoredFactory;
import org.uniprot.core.scorer.uniprotkb.features.FeatureScored;
import org.uniprot.core.scorer.uniprotkb.xdb.EmblScored;
import org.uniprot.core.scorer.uniprotkb.xdb.GoScored;
import org.uniprot.core.scorer.uniprotkb.xdb.PDBScored;
import org.uniprot.core.scorer.uniprotkb.xdb.PDBSumScored;
import org.uniprot.core.uniprot.Keyword;
import org.uniprot.core.uniprot.UniProtEntry;
import org.uniprot.core.uniprot.UniProtEntryType;
import org.uniprot.core.uniprot.comment.CommentType;
import org.uniprot.core.uniprot.evidence.EvidenceType;
import org.uniprot.core.uniprot.feature.Feature;
import org.uniprot.core.uniprot.feature.FeatureType;
import org.uniprot.core.uniprot.xdb.UniProtDBCrossReference;
import org.uniprot.cv.xdb.UniProtXDbDisplayOrder;

/**
 * Created by IntelliJ IDEA. User: spatient Date: 01-Mar-2010 Time: 13:33:09 To change this template
 * use File | Settings | File Templates.
 */
public class UniProtEntryScored implements HasScore {

    private static final Logger LOG = LoggerFactory.getLogger(UniProtEntryScored.class);
    private final EntryScore score;
    private static final List<String> DATABASE_TYPES;

    static {
        DATABASE_TYPES = new ArrayList<>();
        List<UniProtXDbTypeDetail> allDBs = UniProtXDbDisplayOrder.INSTANCE.getOrderedDatabases();
        Set<String> excludedCrossReferences =
                new HashSet<>(Arrays.asList("GO", "EMBL", "PDB", "PDBsum"));
        for (UniProtXDbTypeDetail xdb : allDBs) {
            String name = xdb.getName();
            if (!excludedCrossReferences.contains(name)) {
                DATABASE_TYPES.add(name);
            }
        }
    }

    private final UniProtEntry entry;
    private final List<EvidenceType> evidenceTypes;

    public UniProtEntryScored(UniProtEntry copy, List<EvidenceType> evidenceTypes) {
        this.entry = copy;
        this.evidenceTypes = evidenceTypes;
        this.score = new EntryScore(copy.getPrimaryAccession().getValue());
        //        int taxId = Integer.parseInt(copy.getNcbiTaxonomyIds().get(0).getValue());
        //        this.score.taxId = taxId;

        // PROTEIN DESCRIPTION SCORE
        this.score.descriptionScore = scoreDescription();

        // GENE SCORES
        this.score.geneScore = scoreGenes();

        // COMMENTS
        this.score.commentScore = scoreComments();

        // XREFS
        this.score.xrefScore = scoreCrossReferences();

        // XREFS
        this.score.goScore = scoreGo();

        // KEYWORDS
        this.score.keywordScore = scoreKeywords();

        // FEATURES
        this.score.featureScore = scoreFeatures();

        // JOURNAL ARTICLES
        this.score.citiationScore = 0;

        this.score.totalScore =
                this.score.descriptionScore
                        + this.score.geneScore
                        + this.score.commentScore
                        + this.score.xrefScore
                        + this.score.goScore
                        + this.score.keywordScore
                        + this.score.featureScore
                        + this.score.citiationScore;
    }

    public UniProtEntryScored(UniProtEntry copy) {
        this(copy, null);
    }

    public EntryScore getEntryScore() {
        return this.score;
    }

    @Override
    public double score() {
        return this.score.totalScore;
    }

    @Override
    public Consensus consensus() {
        return Consensus.PRESENCE;
    }

    private double scoreKeywords() {
        double kscore = 0;
        for (Keyword kw : entry.getKeywords()) {
            KeywordScored scored = new KeywordScored(kw, evidenceTypes);
            kscore += scored.score();
            if (scored.consensus() == Consensus.PRESENCE) break;
        }
        return kscore;
    }

    private double scoreDescription() {
        ProteinDescriptionScored pd =
                new ProteinDescriptionScored(entry.getProteinDescription(), evidenceTypes);
        double localScore = pd.score();
        return localScore;
    }

    private double scoreGenes() {
        double gscore = 0;
        for (Gene gene : entry.getGenes()) {
            GeneScored geneScored = new GeneScored(gene, evidenceTypes);
            gscore += geneScored.score();
            if (geneScored.consensus() == Consensus.PRESENCE) break;
        }
        return gscore;
    }

    private double scoreFeatures() {
        double oldscore = 0;
        double localScore = 0;
        boolean isSP = (entry.getEntryType() == UniProtEntryType.SWISSPROT);
        for (FeatureType type : FeatureType.values()) {
            List<HasScore> scoredList = new ArrayList<>();
            for (Feature feature :
                    entry.getFeatures().stream()
                            .filter(f -> f.getType().equals(type))
                            .collect(Collectors.toList())) {
                FeatureScored scored = new FeatureScored(feature, evidenceTypes);
                scored.setIsSwissProt(isSP);
                scoredList.add(scored);
            }
            oldscore = localScore;
            localScore += this.scoreList(scoredList);
            if (Math.abs(oldscore - localScore) > 0.001)
                LOG.debug("Feature score for [{}] {}", type, localScore - oldscore);
        }
        return localScore;
    }

    private double scoreGo() {
        double localScore = 0;
        {
            List<UniProtDBCrossReference> dbxs = entry.getDatabaseCrossReferencesByType("GO");
            localScore = new GoScored(dbxs, evidenceTypes).score();
        }
        return localScore;
    }

    private double scoreCrossReferences() {
        double oldscore = 0;
        double localScore = 0;
        {
            List<UniProtDBCrossReference> dbxs = entry.getDatabaseCrossReferencesByType("EMBL");
            oldscore = localScore;
            localScore += new EmblScored(dbxs, evidenceTypes).score();
            if (Math.abs(oldscore - localScore) > 0.001)
                LOG.debug("Xref score for EMBL {}", localScore - oldscore);
        }

        {
            List<UniProtDBCrossReference> dbxs = entry.getDatabaseCrossReferencesByType("PDB");
            oldscore = localScore;
            localScore += new PDBScored(dbxs, evidenceTypes).score();
            if (Math.abs(oldscore - localScore) > 0.001)
                LOG.debug("Xref score for PDB {}", localScore - oldscore);
        }
        {
            List<UniProtDBCrossReference> dbxs = entry.getDatabaseCrossReferencesByType("PDBsum");
            oldscore = localScore;
            localScore += new PDBSumScored(dbxs, evidenceTypes).score();
            if (Math.abs(oldscore - localScore) > 0.001)
                LOG.debug("Xref score for PDBSum {}", localScore - oldscore);
        }

        oldscore = localScore;
        for (String type : DATABASE_TYPES) {
            List<UniProtDBCrossReference> xrefs = entry.getDatabaseCrossReferencesByType(type);
            if (!xrefs.isEmpty() && hasEvidences(xrefs)) localScore += 0.1;
        }
        if (Math.abs(oldscore - localScore) > 0.001)
            LOG.debug("Xref score for all other {}", localScore - oldscore);

        return localScore;
    }

    private boolean hasEvidences(List<UniProtDBCrossReference> xrefs) {
        for (UniProtDBCrossReference xref : xrefs) {
            if (ScoreUtil.hasEvidence(xref.getEvidences(), evidenceTypes)) {
                return true;
            }
        }
        return false;
    }

    private double scoreComments(boolean isSP, CommentType type, String name) {
        List<HasScore> scoredList = new ArrayList<>();
        entry.getComments().stream()
                .filter(c -> c.getCommentType().equals(type))
                .forEach(
                        c -> {
                            CommentScored scored = CommentScoredFactory.create(c, evidenceTypes);
                            scored.setIsSwissProt(isSP);
                            scoredList.add(scored);
                        });
        double score = this.scoreList(scoredList);
        if (Math.abs(score) > 0.001) LOG.debug("Comment score for [{}] {}", name, score);
        return score;
    }

    private double scoreComments() {
        double localScore = 0.0;
        boolean isSP = (entry.getEntryType() == UniProtEntryType.SWISSPROT);
        localScore += scoreComments(isSP, CommentType.ALLERGEN, "Allergen");
        localScore += scoreComments(isSP, CommentType.ALTERNATIVE_PRODUCTS, "Alternative Products");
        localScore +=
                scoreComments(
                        isSP,
                        CommentType.BIOPHYSICOCHEMICAL_PROPERTIES,
                        "BioPhysicoChemicalProperties");
        localScore += scoreComments(isSP, CommentType.BIOTECHNOLOGY, "BioTech");
        localScore += scoreComments(isSP, CommentType.CATALYTIC_ACTIVITY, "Catalytic Activity");
        localScore += scoreComments(isSP, CommentType.CAUTION, "Caution");
        localScore += scoreComments(isSP, CommentType.COFACTOR, "Cofator");
        localScore += scoreComments(isSP, CommentType.DEVELOPMENTAL_STAGE, "Developmental Stage");
        localScore += scoreComments(isSP, CommentType.DISEASE, "DiseaseEntry");
        localScore += scoreComments(isSP, CommentType.DISRUPTION_PHENOTYPE, "Disruption");
        localScore += scoreComments(isSP, CommentType.DOMAIN, "Domain");
        localScore += scoreComments(isSP, CommentType.ACTIVITY_REGULATION, "Enzyme");
        localScore += scoreComments(isSP, CommentType.FUNCTION, "Function");
        localScore += scoreComments(isSP, CommentType.INDUCTION, "Induction");
        localScore += scoreComments(isSP, CommentType.INTERACTION, "Interaction");
        localScore += scoreComments(isSP, CommentType.MASS_SPECTROMETRY, "Mass Spec");
        localScore += scoreComments(isSP, CommentType.MISCELLANEOUS, "Misc");
        localScore += scoreComments(isSP, CommentType.PATHWAY, "PathwayEntry");
        localScore += scoreComments(isSP, CommentType.PHARMACEUTICAL, "Pharma");
        localScore += scoreComments(isSP, CommentType.POLYMORPHISM, "Polym");
        localScore += scoreComments(isSP, CommentType.PTM, "PTM");
        localScore += scoreComments(isSP, CommentType.RNA_EDITING, "RNA");
        localScore += scoreComments(isSP, CommentType.SEQUENCE_CAUTION, "Sequence caution");
        localScore += scoreComments(isSP, CommentType.SIMILARITY, "Similarity");
        localScore += scoreComments(isSP, CommentType.SUBCELLULAR_LOCATION, "Subcellular Location");
        localScore += scoreComments(isSP, CommentType.SUBUNIT, "Subunit");
        localScore += scoreComments(isSP, CommentType.TISSUE_SPECIFICITY, "Tissue");
        localScore += scoreComments(isSP, CommentType.TOXIC_DOSE, "Toxic");
        localScore += scoreComments(isSP, CommentType.WEBRESOURCE, "Web");

        return localScore;
    }

    private double scoreList(List<HasScore> objects) {
        double localScore = 0;
        for (HasScore scored : objects) {
            if (scored.consensus() == Consensus.PRESENCE)
                localScore = scored.score() > localScore ? scored.score() : localScore;
            else localScore += scored.score();
        }
        return localScore;
    }
}

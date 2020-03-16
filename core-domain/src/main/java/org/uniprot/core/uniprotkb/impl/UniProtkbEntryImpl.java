package org.uniprot.core.uniprotkb.impl;

import static org.uniprot.core.util.Utils.notNull;
import static org.uniprot.core.util.Utils.nullOrEmpty;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.uniprot.core.Sequence;
import org.uniprot.core.citation.CitationType;
import org.uniprot.core.gene.Gene;
import org.uniprot.core.taxonomy.TaxonomyLineage;
import org.uniprot.core.uniprotkb.*;
import org.uniprot.core.uniprotkb.comment.APIsoform;
import org.uniprot.core.uniprotkb.comment.AlternativeProductsComment;
import org.uniprot.core.uniprotkb.comment.BPCPComment;
import org.uniprot.core.uniprotkb.comment.CatalyticActivityComment;
import org.uniprot.core.uniprotkb.comment.CofactorComment;
import org.uniprot.core.uniprotkb.comment.Comment;
import org.uniprot.core.uniprotkb.comment.CommentType;
import org.uniprot.core.uniprotkb.comment.DiseaseComment;
import org.uniprot.core.uniprotkb.comment.FreeText;
import org.uniprot.core.uniprotkb.comment.FreeTextComment;
import org.uniprot.core.uniprotkb.comment.MassSpectrometryComment;
import org.uniprot.core.uniprotkb.comment.Note;
import org.uniprot.core.uniprotkb.comment.RnaEditingComment;
import org.uniprot.core.uniprotkb.comment.SequenceCautionComment;
import org.uniprot.core.uniprotkb.comment.SubcellularLocation;
import org.uniprot.core.uniprotkb.comment.SubcellularLocationComment;
import org.uniprot.core.uniprotkb.description.ProteinAltName;
import org.uniprot.core.uniprotkb.description.ProteinDescription;
import org.uniprot.core.uniprotkb.description.ProteinRecName;
import org.uniprot.core.uniprotkb.description.ProteinSection;
import org.uniprot.core.uniprotkb.description.ProteinSubName;
import org.uniprot.core.uniprotkb.evidence.Evidence;
import org.uniprot.core.uniprotkb.evidence.HasEvidences;
import org.uniprot.core.uniprotkb.feature.Feature;
import org.uniprot.core.uniprotkb.feature.FeatureType;
import org.uniprot.core.uniprotkb.taxonomy.Organism;
import org.uniprot.core.uniprotkb.taxonomy.OrganismHost;
import org.uniprot.core.uniprotkb.xdb.UniProtkbCrossReference;
import org.uniprot.core.uniprotkb.xdb.UniProtkbDatabase;
import org.uniprot.core.util.Utils;

public class UniProtkbEntryImpl implements UniProtkbEntry {
    private static final long serialVersionUID = 3240727057252439286L;
    private UniProtkbEntryType entryType;
    private UniProtkbAccession primaryAccession;
    private List<UniProtkbAccession> secondaryAccessions;
    private UniProtkbId uniProtkbId;
    private EntryAudit entryAudit;
    private double annotationScore;
    private Organism organism;
    private List<OrganismHost> organismHosts;

    private ProteinExistence proteinExistence;

    private ProteinDescription proteinDescription;
    private List<Gene> genes;
    private List<Comment> comments;
    private List<Feature> features;
    private List<GeneLocation> geneLocations;

    private List<Keyword> keywords;
    private List<UniProtkbReference> references;
    private List<UniProtkbCrossReference> uniProtkbCrossReferences;
    private Sequence sequence;

    private InternalSection internalSection;
    private EntryInactiveReason inactiveReason;
    private List<TaxonomyLineage> lineages;

    // no arg constructor for JSON deserialization
    UniProtkbEntryImpl() {
        secondaryAccessions = Collections.emptyList();
        organismHosts = Collections.emptyList();
        genes = Collections.emptyList();
        comments = Collections.emptyList();
        references = Collections.emptyList();
        uniProtkbCrossReferences = Collections.emptyList();
        features = Collections.emptyList();
        geneLocations = Collections.emptyList();
        keywords = Collections.emptyList();
        lineages = Collections.emptyList();
    }

    UniProtkbEntryImpl(
            UniProtkbEntryType entryType,
            UniProtkbAccession primaryAccession,
            List<UniProtkbAccession> secondaryAccessions,
            UniProtkbId uniProtkbId,
            EntryAudit entryAudit,
            double annotationScore,
            Organism organism,
            List<OrganismHost> organismHosts,
            ProteinExistence proteinExistence,
            ProteinDescription proteinDescription,
            List<Gene> genes,
            List<Comment> comments,
            List<Feature> features,
            List<GeneLocation> geneLocations,
            List<Keyword> keywords,
            List<UniProtkbReference> references,
            List<UniProtkbCrossReference> uniProtkbCrossReferences,
            Sequence sequence,
            InternalSection internalSection,
            List<TaxonomyLineage> lineages,
            EntryInactiveReason inactiveReason) {
        if (Objects.isNull(entryType)) {
            throw new IllegalArgumentException("entryType is Mandatory for uniprot entry.");
        } else if (Objects.isNull(primaryAccession) || nullOrEmpty(primaryAccession.getValue())) {
            throw new IllegalArgumentException("primaryAccession is Mandatory for uniprot entry.");
        } else if ((Objects.isNull(uniProtkbId) || nullOrEmpty(uniProtkbId.getValue()))
                && entryType != UniProtkbEntryType.INACTIVE) {
            throw new IllegalArgumentException("uniProtkbId is Mandatory for uniprot entry.");
        } else if (notNull(inactiveReason) && entryType != UniProtkbEntryType.INACTIVE) {
            throw new IllegalArgumentException("Inactive entry must have type INACTIVE");
        } else if (Objects.isNull(inactiveReason) && entryType == UniProtkbEntryType.INACTIVE) {
            throw new IllegalArgumentException("Active entry must NOT have type INACTIVE");
        }
        this.entryType = entryType;
        this.primaryAccession = primaryAccession;
        this.secondaryAccessions = Utils.unmodifiableList(secondaryAccessions);
        this.uniProtkbId = uniProtkbId;
        this.entryAudit = entryAudit;
        this.annotationScore = annotationScore;
        this.organism = organism;
        this.organismHosts = Utils.unmodifiableList(organismHosts);
        this.proteinExistence = proteinExistence;
        this.proteinDescription = proteinDescription;
        this.genes = Utils.unmodifiableList(genes);
        this.comments = Utils.modifiableList(comments);
        this.features = Utils.modifiableList(features);
        this.geneLocations = Utils.unmodifiableList(geneLocations);
        this.keywords = Utils.unmodifiableList(keywords);
        this.references = Utils.unmodifiableList(references);
        this.uniProtkbCrossReferences = Utils.modifiableList(uniProtkbCrossReferences);
        this.sequence = sequence;
        this.internalSection = internalSection;
        this.lineages = Utils.unmodifiableList(lineages);
        this.inactiveReason = inactiveReason;
    }

    @Override
    public UniProtkbEntryType getEntryType() {
        return entryType;
    }

    @Override
    public UniProtkbAccession getPrimaryAccession() {
        return primaryAccession;
    }

    @Override
    public List<UniProtkbAccession> getSecondaryAccessions() {
        return secondaryAccessions;
    }

    @Override
    public UniProtkbId getUniProtkbId() {
        return uniProtkbId;
    }

    @Override
    public double getAnnotationScore() {
        return this.annotationScore;
    }

    @Override
    public EntryAudit getEntryAudit() {
        return entryAudit;
    }

    @Override
    public Organism getOrganism() {
        return organism;
    }

    @Override
    public List<OrganismHost> getOrganismHosts() {
        return organismHosts;
    }

    @Override
    public ProteinExistence getProteinExistence() {
        return proteinExistence;
    }

    @Override
    public ProteinDescription getProteinDescription() {
        return proteinDescription;
    }

    @Override
    public List<Gene> getGenes() {
        return genes;
    }

    @Override
    public List<Comment> getComments() {
        return comments;
    }

    @Override
    public <T extends Comment> List<T> getCommentsByType(CommentType type) {
        List<Comment> typedComments =
                comments.stream()
                        .filter(val -> val.getCommentType().equals(type))
                        .collect(Collectors.toList());

        return (List<T>) typedComments;
    }

    @Override
    public List<Feature> getFeatures() {
        return features;
    }

    public List<Feature> getFeaturesByType(FeatureType type) {
        return features.stream()
                .filter(feature -> notNull(feature.getType()))
                .filter(feature -> feature.getType().equals(type))
                .collect(Collectors.toList());
    }

    @Override
    public List<GeneLocation> getGeneLocations() {
        return geneLocations;
    }

    @Override
    public List<Keyword> getKeywords() {
        return keywords;
    }

    @Override
    public List<UniProtkbReference> getReferences() {
        return references;
    }

    @Override
    public List<UniProtkbReference> getReferencesByType(CitationType type) {
        return this.references.stream()
                .filter(uniProtReference -> notNull(uniProtReference.getCitation()))
                .filter(
                        uniProtReference ->
                                notNull(uniProtReference.getCitation().getCitationType()))
                .filter(val -> val.getCitation().getCitationType().equals(type))
                .collect(Collectors.toList());
    }

    @Override
    public List<UniProtkbCrossReference> getUniProtkbCrossReferences() {
        return uniProtkbCrossReferences;
    }

    @Override
    public List<UniProtkbCrossReference> getUniProtCrossReferencesByType(UniProtkbDatabase type) {
        return getUniProtCrossReferencesByType(type.getName());
    }

    @Override
    public List<UniProtkbCrossReference> getUniProtCrossReferencesByType(String dbName) {
        return this.uniProtkbCrossReferences.stream()
                .filter(val -> notNull(val.getDatabase()))
                .filter(val -> dbName.equals(val.getDatabase().getName()))
                .collect(Collectors.toList());
    }

    @Override
    public Sequence getSequence() {
        return sequence;
    }

    @Override
    public InternalSection getInternalSection() {
        return internalSection;
    }

    @Override
    public Boolean isFragment() {
        return !getFeaturesByType(FeatureType.NON_TER).isEmpty();
    }

    @Override
    public EntryInactiveReason getInactiveReason() {
        return inactiveReason;
    }

    @Override
    public boolean isActive() {
        return inactiveReason == null;
    }

    @Override
    public boolean hasSecondaryAccessions() {
        return Utils.notNullNotEmpty(secondaryAccessions);
    }

    @Override
    public boolean hasAnnotationScore() {
        return this.annotationScore > 0;
    }

    @Override
    public boolean hasOrganism() {
        return this.organism != null;
    }

    @Override
    public boolean hasOrganismHosts() {
        return Utils.notNullNotEmpty(this.organismHosts);
    }

    @Override
    public boolean hasProteinExistence() {
        return this.proteinExistence != null;
    }

    @Override
    public boolean hasProteinDescription() {
        return this.proteinDescription != null;
    }

    @Override
    public boolean hasGenes() {
        return Utils.notNullNotEmpty(this.genes);
    }

    @Override
    public boolean hasComments() {
        return Utils.notNullNotEmpty(this.comments);
    }

    @Override
    public boolean hasFeatures() {
        return Utils.notNullNotEmpty(this.features);
    }

    @Override
    public boolean hasGeneLocations() {
        return Utils.notNullNotEmpty(this.geneLocations);
    }

    @Override
    public boolean hasKeywords() {
        return Utils.notNullNotEmpty(this.keywords);
    }

    @Override
    public boolean hasReferences() {
        return Utils.notNullNotEmpty(this.references);
    }

    @Override
    public boolean hasUniProtCrossReferences() {
        return Utils.notNullNotEmpty(this.uniProtkbCrossReferences);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UniProtkbEntryImpl that = (UniProtkbEntryImpl) o;
        return Double.compare(that.annotationScore, annotationScore) == 0
                && entryType == that.entryType
                && Objects.equals(primaryAccession, that.primaryAccession)
                && Objects.equals(secondaryAccessions, that.secondaryAccessions)
                && Objects.equals(uniProtkbId, that.uniProtkbId)
                && Objects.equals(entryAudit, that.entryAudit)
                && Objects.equals(organism, that.organism)
                && Objects.equals(organismHosts, that.organismHosts)
                && proteinExistence == that.proteinExistence
                && Objects.equals(proteinDescription, that.proteinDescription)
                && Objects.equals(genes, that.genes)
                && Objects.equals(comments, that.comments)
                && Objects.equals(features, that.features)
                && Objects.equals(geneLocations, that.geneLocations)
                && Objects.equals(keywords, that.keywords)
                && Objects.equals(references, that.references)
                && Objects.equals(uniProtkbCrossReferences, that.uniProtkbCrossReferences)
                && Objects.equals(sequence, that.sequence)
                && Objects.equals(internalSection, that.internalSection)
                && Objects.equals(inactiveReason, that.inactiveReason);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                entryType,
                primaryAccession,
                secondaryAccessions,
                uniProtkbId,
                entryAudit,
                annotationScore,
                organism,
                organismHosts,
                proteinExistence,
                proteinDescription,
                genes,
                comments,
                features,
                geneLocations,
                keywords,
                references,
                uniProtkbCrossReferences,
                sequence,
                internalSection,
                inactiveReason);
    }

    @Override
    public List<TaxonomyLineage> getLineages() {
        return lineages;
    }

    @Override
    public List<Evidence> gatherEvidences() {
        Set<Evidence> evidences = new TreeSet<>();
        updateHasEvidence(evidences, this.getOrganism());
        updateProteinDescriptionEvidences(evidences, this.getProteinDescription());
        if (this.getGenes() != null) {
            this.getGenes().forEach(val -> updateGeneEvidences(evidences, val));
        }
        if (this.getComments() != null) {
            this.getComments().forEach(val -> updateCommentEvidences(evidences, val));
        }
        if (this.getFeatures() != null) {
            this.getFeatures().forEach(val -> updateHasEvidence(evidences, val));
        }
        if (this.getKeywords() != null) {
            this.getKeywords().forEach(val -> updateHasEvidence(evidences, val));
        }
        this.getReferences().forEach(val -> updateReferenceEvidences(evidences, val));
        if (this.getGeneLocations() != null) {
            this.getGeneLocations().forEach(val -> updateHasEvidence(evidences, val));
        }
        return new ArrayList<>(evidences);
    }

    private void updateReferenceEvidences(Set<Evidence> evidences, UniProtkbReference ref) {
        updateHasEvidence(evidences, ref);
        if (ref != null) {
            updateHasEvidences(evidences, ref.getReferenceComments());
        }
    }

    private <T extends Comment> void updateCommentEvidences(Set<Evidence> evidences, T comment) {
        if (comment instanceof FreeTextComment) {
            FreeTextComment ftComment = (FreeTextComment) comment;
            updateFreeTextEvidences(evidences, ftComment);
        } else if (comment instanceof AlternativeProductsComment) {
            AlternativeProductsComment apComment = (AlternativeProductsComment) comment;
            apComment.getIsoforms().forEach(val -> updateAPIsoformEvidences(evidences, val));
            updateNoteEvidences(evidences, apComment.getNote());
        } else if (comment instanceof BPCPComment) {
            BPCPComment bpcpComment = (BPCPComment) comment;
            updateHasEvidence(evidences, bpcpComment.getAbsorption());
            if (bpcpComment.getKineticParameters() != null) {
                bpcpComment
                        .getKineticParameters()
                        .getMaximumVelocities()
                        .forEach(val -> updateHasEvidence(evidences, val));

                bpcpComment
                        .getKineticParameters()
                        .getMichaelisConstants()
                        .forEach(val -> updateHasEvidence(evidences, val));
                updateNoteEvidences(evidences, bpcpComment.getKineticParameters().getNote());
            }

            updateFreeTextEvidences(evidences, bpcpComment.getPhDependence());
            updateFreeTextEvidences(evidences, bpcpComment.getRedoxPotential());
            updateFreeTextEvidences(evidences, bpcpComment.getTemperatureDependence());
        } else if (comment instanceof CatalyticActivityComment) {
            CatalyticActivityComment caComment = (CatalyticActivityComment) comment;
            updateHasEvidence(evidences, caComment.getReaction());
            updateHasEvidences(evidences, caComment.getPhysiologicalReactions());
        } else if (comment instanceof CofactorComment) {
            CofactorComment cfComment = (CofactorComment) comment;
            updateNoteEvidences(evidences, cfComment.getNote());
            updateHasEvidences(evidences, cfComment.getCofactors());
        } else if (comment instanceof DiseaseComment) {
            DiseaseComment diComment = (DiseaseComment) comment;
            updateNoteEvidences(evidences, diComment.getNote());
            updateHasEvidence(evidences, diComment.getDisease());
        } else if (comment instanceof MassSpectrometryComment) {
            MassSpectrometryComment msComment = (MassSpectrometryComment) comment;
            evidences.addAll(msComment.getEvidences());
        } else if (comment instanceof SequenceCautionComment) {
            SequenceCautionComment csComment = (SequenceCautionComment) comment;
            updateHasEvidence(evidences, csComment);
        } else if (comment instanceof RnaEditingComment) {
            RnaEditingComment reComment = (RnaEditingComment) comment;
            updateNoteEvidences(evidences, reComment.getNote());
            updateHasEvidences(evidences, reComment.getPositions());
        } else if (comment instanceof SubcellularLocationComment) {
            SubcellularLocationComment slComment = (SubcellularLocationComment) comment;
            updateNoteEvidences(evidences, slComment.getNote());
            slComment
                    .getSubcellularLocations()
                    .forEach(val -> updateSubcellularLocationEvidence(evidences, val));
        }
    }

    private void updateSubcellularLocationEvidence(
            Set<Evidence> evidences, SubcellularLocation sl) {
        updateHasEvidence(evidences, sl.getLocation());
        updateHasEvidence(evidences, sl.getOrientation());
        updateHasEvidence(evidences, sl.getTopology());
    }

    private void updateAPIsoformEvidences(Set<Evidence> evidences, APIsoform ap) {
        updateHasEvidence(evidences, ap.getName());
        updateHasEvidences(evidences, ap.getSynonyms());
        updateNoteEvidences(evidences, ap.getNote());
    }

    private void updateGeneEvidences(Set<Evidence> evidences, Gene gene) {
        updateHasEvidence(evidences, gene.getGeneName());
        updateHasEvidences(evidences, gene.getSynonyms());
        updateHasEvidences(evidences, gene.getOrfNames());
        updateHasEvidences(evidences, gene.getOrderedLocusNames());
    }

    private void updateProteinDescriptionEvidences(Set<Evidence> evidences, ProteinDescription pd) {
        if (pd == null) return;
        updateProteinRecNameEvidences(evidences, pd.getRecommendedName());
        if (pd.getAlternativeNames() != null) {
            pd.getAlternativeNames().forEach(val -> updateProteinAltNameEvidences(evidences, val));
        }
        if (pd.getSubmissionNames() != null) {
            pd.getSubmissionNames().forEach(val -> updateProteinSubNameEvidences(evidences, val));
        }
        if (pd.getIncludes() != null) {
            pd.getIncludes().forEach(val -> updateProteinSectionEvidences(evidences, val));
        }
        if (pd.getContains() != null) {
            pd.getContains().forEach(val -> updateProteinSectionEvidences(evidences, val));
        }
        if (pd.getAllergenName() != null) {
            updateHasEvidence(evidences, pd.getAllergenName());
        }
        if (pd.getBiotechName() != null) {
            updateHasEvidence(evidences, pd.getBiotechName());
        }
        if ((pd.getCdAntigenNames() != null) && !pd.getCdAntigenNames().isEmpty()) {
            updateHasEvidences(evidences, pd.getCdAntigenNames());
        }

        if ((pd.getInnNames() != null) && !pd.getInnNames().isEmpty()) {
            updateHasEvidences(evidences, pd.getInnNames());
        }
    }

    private void updateProteinSectionEvidences(Set<Evidence> evidences, ProteinSection ps) {
        updateProteinRecNameEvidences(evidences, ps.getRecommendedName());
        if (ps.getAlternativeNames() != null) {
            ps.getAlternativeNames().forEach(val -> updateProteinAltNameEvidences(evidences, val));
        }
    }

    private void updateProteinRecNameEvidences(Set<Evidence> evidences, ProteinRecName pn) {
        if (pn == null) {
            return;
        }
        updateHasEvidence(evidences, pn.getFullName());
        updateHasEvidences(evidences, pn.getShortNames());
        updateHasEvidences(evidences, pn.getEcNumbers());
    }

    private void updateProteinAltNameEvidences(Set<Evidence> evidences, ProteinAltName pn) {
        updateHasEvidence(evidences, pn.getFullName());
        updateHasEvidences(evidences, pn.getShortNames());
        updateHasEvidences(evidences, pn.getEcNumbers());
    }

    private void updateProteinSubNameEvidences(Set<Evidence> evidences, ProteinSubName pn) {
        updateHasEvidence(evidences, pn.getFullName());
        updateHasEvidences(evidences, pn.getEcNumbers());
    }

    private void updateFreeTextEvidences(Set<Evidence> evidences, FreeText text) {
        if (text != null) {
            updateHasEvidences(evidences, text.getTexts());
        }
    }

    private void updateNoteEvidences(Set<Evidence> evidences, Note note) {
        if (note != null) {
            updateHasEvidences(evidences, note.getTexts());
        }
    }

    private void updateHasEvidences(Set<Evidence> evidences, List<? extends HasEvidences> hes) {
        if (hes != null) {
            hes.forEach(val -> updateHasEvidence(evidences, val));
        }
    }

    private void updateHasEvidence(Set<Evidence> evidences, HasEvidences he) {
        if (he != null) {
            evidences.addAll(he.getEvidences());
        }
    }
}

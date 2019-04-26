package uk.ac.ebi.uniprot.domain.uniprot.impl;

import uk.ac.ebi.uniprot.common.Utils;
import uk.ac.ebi.uniprot.domain.Sequence;
import uk.ac.ebi.uniprot.domain.citation.CitationType;
import uk.ac.ebi.uniprot.domain.gene.Gene;
import uk.ac.ebi.uniprot.domain.uniprot.*;
import uk.ac.ebi.uniprot.domain.uniprot.comment.Comment;
import uk.ac.ebi.uniprot.domain.uniprot.comment.CommentType;
import uk.ac.ebi.uniprot.domain.uniprot.description.ProteinDescription;
import uk.ac.ebi.uniprot.domain.uniprot.feature.Feature;
import uk.ac.ebi.uniprot.domain.uniprot.feature.FeatureType;
import uk.ac.ebi.uniprot.domain.uniprot.taxonomy.Organism;
import uk.ac.ebi.uniprot.domain.uniprot.taxonomy.OrganismHost;
import uk.ac.ebi.uniprot.domain.uniprot.xdb.UniProtDBCrossReference;
import uk.ac.ebi.uniprot.domain.uniprot.xdb.UniProtXDbType;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


public class UniProtEntryImpl implements UniProtEntry {
    private static final long serialVersionUID = 3240727057252439286L;
    private UniProtEntryType entryType;
    private UniProtAccession primaryAccession;
    private List<UniProtAccession> secondaryAccessions;
    private UniProtId uniProtId;
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
    private List<UniProtReference> references;
    private List<UniProtDBCrossReference> databaseCrossReferences;
    private Sequence sequence;

    private InternalSection internalSection;
    private EntryInactiveReason inactiveReason;

    private UniProtEntryImpl() {
        secondaryAccessions = Collections.emptyList();
        organismHosts = Collections.emptyList();
        genes = Collections.emptyList();
        comments = Collections.emptyList();
        references = Collections.emptyList();
        databaseCrossReferences = Collections.emptyList();
        features = Collections.emptyList();
        geneLocations = Collections.emptyList();
        keywords = Collections.emptyList();
    }

    public UniProtEntryImpl(UniProtAccession primaryAccession,
                            UniProtId uniProtId,
                            EntryInactiveReason inactiveReason) {
        this(UniProtEntryType.UNKNOWN, primaryAccession, null, uniProtId,
             null, 0,null, null, null, null,
             null, null, null, null, null,
             null, null, null, null, inactiveReason);
    }

    public UniProtEntryImpl(UniProtEntryType entryType,
                            UniProtAccession primaryAccession,
                            List<UniProtAccession> secondaryAccessions,
                            UniProtId uniProtId,
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
                            List<UniProtReference> references,
                            List<UniProtDBCrossReference> databaseCrossReferences,
                            Sequence sequence,
                            InternalSection internalSection) {
        this(entryType, primaryAccession, secondaryAccessions, uniProtId,
             entryAudit, annotationScore, organism, organismHosts, proteinExistence,
             proteinDescription, genes, comments, features, geneLocations, keywords,
             references, databaseCrossReferences, sequence, internalSection, null);

    }

    public UniProtEntryImpl(UniProtEntryType entryType,
                            UniProtAccession primaryAccession,
                            List<UniProtAccession> secondaryAccessions,
                            UniProtId uniProtId, EntryAudit entryAudit,double annotationScore,
                            Organism organism,
                            List<OrganismHost> organismHosts,
                            ProteinExistence proteinExistence,
                            ProteinDescription proteinDescription,
                            List<Gene> genes, List<Comment> comments,
                            List<Feature> features, List<GeneLocation> geneLocations,
                            List<Keyword> keywords, List<UniProtReference> references,
                            List<UniProtDBCrossReference> databaseCrossReferences,
                            Sequence sequence,
                            InternalSection internalSection,
                            EntryInactiveReason inactiveReason) {
        this.entryType = entryType;
        this.primaryAccession = primaryAccession;
        this.secondaryAccessions = Utils.nonNullUnmodifiableList(secondaryAccessions);
        this.uniProtId = uniProtId;
        this.entryAudit = entryAudit;
        this.annotationScore = annotationScore;
        this.organism = organism;
        this.organismHosts = Utils.nonNullUnmodifiableList(organismHosts);
        this.proteinExistence = proteinExistence;
        this.proteinDescription = proteinDescription;
        this.genes = Utils.nonNullUnmodifiableList(genes);
        this.comments = Utils.nonNullUnmodifiableList(comments);
        this.features = Utils.nonNullUnmodifiableList(features);
        this.geneLocations = Utils.nonNullUnmodifiableList(geneLocations);
        this.keywords = Utils.nonNullUnmodifiableList(keywords);
        this.references = Utils.nonNullUnmodifiableList(references);
        this.databaseCrossReferences = Utils.nonNullUnmodifiableList(databaseCrossReferences);
        this.sequence = sequence;
        this.internalSection = internalSection;
        this.inactiveReason = inactiveReason;
    }

    @Override
    public UniProtEntryType getEntryType() {
        return entryType;
    }

    @Override
    public UniProtAccession getPrimaryAccession() {
        return primaryAccession;
    }

    @Override
    public List<UniProtAccession> getSecondaryAccessions() {
        return secondaryAccessions;
    }

    @Override
    public UniProtId getUniProtId() {
        return uniProtId;
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
    public <T extends Comment> List<T> getCommentByType(CommentType type) {
        List<Comment> typedComments = comments.stream()
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
    public List<UniProtReference> getReferences() {
        return references;
    }

    @Override
    public List<UniProtReference> getReferencesByType(CitationType type) {
        return this.references.stream()
                .filter(val -> val.getCitation().getCitationType().equals(type))
                .collect(Collectors.toList());
    }

    @Override
    public List<UniProtDBCrossReference> getDatabaseCrossReferences() {
        return databaseCrossReferences;
    }

    @Override
    public List<UniProtDBCrossReference> getDatabaseCrossReferencesByType(UniProtXDbType type) {
        return getDatabaseCrossReferencesByType(type.getName());
    }

    @Override
    public List<UniProtDBCrossReference> getDatabaseCrossReferencesByType(String dbName) {
        return this.databaseCrossReferences.stream()
                .filter(val -> dbName.equals(val.getDatabaseType().getName()))
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
        return Utils.notEmpty(secondaryAccessions);
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
        return Utils.notEmpty(this.organismHosts);
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
        return Utils.notEmpty(this.genes);
    }

    @Override
    public boolean hasComments() {
        return Utils.notEmpty(this.comments);
    }

    @Override
    public boolean hasFeatures() {
        return Utils.notEmpty(this.features);
    }

    @Override
    public boolean hasGeneLocations() {
        return Utils.notEmpty(this.geneLocations);
    }

    @Override
    public boolean hasKeywords() {
        return Utils.notEmpty(this.keywords);
    }

    @Override
    public boolean hasReferences() {
        return Utils.notEmpty(this.references);
    }

    @Override
    public boolean hasDatabaseCrossReferences() {
        return Utils.notEmpty(this.databaseCrossReferences);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UniProtEntryImpl that = (UniProtEntryImpl) o;
        return Double.compare(that.annotationScore, annotationScore) == 0 &&
                entryType == that.entryType &&
                Objects.equals(primaryAccession, that.primaryAccession) &&
                Objects.equals(secondaryAccessions, that.secondaryAccessions) &&
                Objects.equals(uniProtId, that.uniProtId) &&
                Objects.equals(entryAudit, that.entryAudit) &&
                Objects.equals(organism, that.organism) &&
                Objects.equals(organismHosts, that.organismHosts) &&
                proteinExistence == that.proteinExistence &&
                Objects.equals(proteinDescription, that.proteinDescription) &&
                Objects.equals(genes, that.genes) &&
                Objects.equals(comments, that.comments) &&
                Objects.equals(features, that.features) &&
                Objects.equals(geneLocations, that.geneLocations) &&
                Objects.equals(keywords, that.keywords) &&
                Objects.equals(references, that.references) &&
                Objects.equals(databaseCrossReferences, that.databaseCrossReferences) &&
                Objects.equals(sequence, that.sequence) &&
                Objects.equals(internalSection, that.internalSection) &&
                Objects.equals(inactiveReason, that.inactiveReason);
    }

    @Override
    public int hashCode() {
        return Objects.hash(entryType, primaryAccession, secondaryAccessions, uniProtId, entryAudit, annotationScore,
                organism, organismHosts, proteinExistence, proteinDescription, genes, comments, features,
                geneLocations, keywords, references, databaseCrossReferences, sequence, internalSection, inactiveReason);
    }
}

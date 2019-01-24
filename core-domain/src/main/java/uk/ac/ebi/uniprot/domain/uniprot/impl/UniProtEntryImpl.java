package uk.ac.ebi.uniprot.domain.uniprot.impl;

import uk.ac.ebi.uniprot.domain.Sequence;
import uk.ac.ebi.uniprot.domain.citation.CitationType;
import uk.ac.ebi.uniprot.domain.gene.Gene;
import uk.ac.ebi.uniprot.domain.taxonomy.Organism;
import uk.ac.ebi.uniprot.domain.taxonomy.OrganismHost;
import uk.ac.ebi.uniprot.domain.uniprot.*;
import uk.ac.ebi.uniprot.domain.uniprot.comment.Comment;
import uk.ac.ebi.uniprot.domain.uniprot.comment.CommentType;
import uk.ac.ebi.uniprot.domain.uniprot.description.ProteinDescription;
import uk.ac.ebi.uniprot.domain.uniprot.feature.Feature;
import uk.ac.ebi.uniprot.domain.uniprot.feature.FeatureType;
import uk.ac.ebi.uniprot.domain.uniprot.xdb.UniProtDBCrossReference;
import uk.ac.ebi.uniprot.domain.uniprot.xdb.UniProtXDbType;
import uk.ac.ebi.uniprot.domain.util.Utils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


public class UniProtEntryImpl implements UniProtEntry {
    private UniProtEntryType entryType;
    private UniProtAccession primaryAccession;
    private List<UniProtAccession> secondaryAccessions;
    private UniProtId uniProtId;
    private EntryAudit entryAudit;

    private Organism organism;
    private List<OrganismHost> organismHosts;

    private ProteinExistence proteinExistence;

    private ProteinDescription proteinDescription;
    private List<Gene> genes;
    private List<Comment> comments;
    private List<Feature> features;
    private List<Organelle> organelles;

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
        organelles = Collections.emptyList();
        keywords = Collections.emptyList();
    }

    public UniProtEntryImpl(UniProtAccession primaryAccession,
                            UniProtId uniProtId,
                            EntryInactiveReason inactiveReason) {
        this(UniProtEntryType.UNKNOWN, primaryAccession, null, uniProtId,
             null, null, null, null, null,
             null, null, null, null, null,
             null, null, null, null, inactiveReason);
    }

    public UniProtEntryImpl(UniProtEntryType entryType,
                            UniProtAccession primaryAccession,
                            List<UniProtAccession> secondaryAccessions,
                            UniProtId uniProtId,
                            EntryAudit entryAudit,
                            Organism organism,
                            List<OrganismHost> organismHosts,
                            ProteinExistence proteinExistence,
                            ProteinDescription proteinDescription,
                            List<Gene> genes,
                            List<Comment> comments,
                            List<Feature> features,
                            List<Organelle> organelles,
                            List<Keyword> keywords,
                            List<UniProtReference> references,
                            List<UniProtDBCrossReference> databaseCrossReferences,
                            Sequence sequence,
                            InternalSection internalSection) {
        this(entryType, primaryAccession, secondaryAccessions, uniProtId,
             entryAudit, organism, organismHosts, proteinExistence,
             proteinDescription, genes, comments, features, organelles, keywords,
             references, databaseCrossReferences, sequence, internalSection, null);

    }

    public UniProtEntryImpl(UniProtEntryType entryType,
                            UniProtAccession primaryAccession,
                            List<UniProtAccession> secondaryAccessions,
                            UniProtId uniProtId, EntryAudit entryAudit,
                            Organism organism,
                            List<OrganismHost> organismHosts,
                            ProteinExistence proteinExistence,
                            ProteinDescription proteinDescription,
                            List<Gene> genes, List<Comment> comments,
                            List<Feature> features, List<Organelle> organelles,
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
        this.organism = organism;
        this.organismHosts = Utils.nonNullUnmodifiableList(organismHosts);
        this.proteinExistence = proteinExistence;
        this.proteinDescription = proteinDescription;
        this.genes = Utils.nonNullUnmodifiableList(genes);
        this.comments = Utils.nonNullUnmodifiableList(comments);
        this.features = Utils.nonNullUnmodifiableList(features);
        this.organelles = Utils.nonNullUnmodifiableList(organelles);
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
    public List<Organelle> getOrganelles() {
        return organelles;
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
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((comments == null) ? 0 : comments.hashCode());
        result = prime * result + ((databaseCrossReferences == null) ? 0 : databaseCrossReferences.hashCode());
        result = prime * result + ((entryAudit == null) ? 0 : entryAudit.hashCode());
        result = prime * result + ((entryType == null) ? 0 : entryType.hashCode());
        result = prime * result + ((features == null) ? 0 : features.hashCode());
        result = prime * result + ((genes == null) ? 0 : genes.hashCode());
        result = prime * result + ((inactiveReason == null) ? 0 : inactiveReason.hashCode());
        result = prime * result + ((internalSection == null) ? 0 : internalSection.hashCode());
        result = prime * result + ((keywords == null) ? 0 : keywords.hashCode());
        result = prime * result + ((organelles == null) ? 0 : organelles.hashCode());
        result = prime * result + ((organism == null) ? 0 : organism.hashCode());
        result = prime * result + ((organismHosts == null) ? 0 : organismHosts.hashCode());
        result = prime * result + ((primaryAccession == null) ? 0 : primaryAccession.hashCode());
        result = prime * result + ((proteinDescription == null) ? 0 : proteinDescription.hashCode());
        result = prime * result + ((proteinExistence == null) ? 0 : proteinExistence.hashCode());
        result = prime * result + ((references == null) ? 0 : references.hashCode());
        result = prime * result + ((secondaryAccessions == null) ? 0 : secondaryAccessions.hashCode());
        result = prime * result + ((sequence == null) ? 0 : sequence.hashCode());
        result = prime * result + ((uniProtId == null) ? 0 : uniProtId.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        UniProtEntryImpl other = (UniProtEntryImpl) obj;
        if (comments == null) {
            if (other.comments != null)
                return false;
        } else if (!comments.equals(other.comments))
            return false;
        if (databaseCrossReferences == null) {
            if (other.databaseCrossReferences != null)
                return false;
        } else if (!databaseCrossReferences.equals(other.databaseCrossReferences))
            return false;
        if (entryAudit == null) {
            if (other.entryAudit != null)
                return false;
        } else if (!entryAudit.equals(other.entryAudit))
            return false;
        if (entryType != other.entryType)
            return false;
        if (features == null) {
            if (other.features != null)
                return false;
        } else if (!features.equals(other.features))
            return false;
        if (genes == null) {
            if (other.genes != null)
                return false;
        } else if (!genes.equals(other.genes))
            return false;
        if (inactiveReason == null) {
            if (other.inactiveReason != null)
                return false;
        } else if (!inactiveReason.equals(other.inactiveReason))
            return false;
        if (internalSection == null) {
            if (other.internalSection != null)
                return false;
        } else if (!internalSection.equals(other.internalSection))
            return false;
        if (keywords == null) {
            if (other.keywords != null)
                return false;
        } else if (!keywords.equals(other.keywords))
            return false;
        if (organelles == null) {
            if (other.organelles != null)
                return false;
        } else if (!organelles.equals(other.organelles))
            return false;
        if (organism == null) {
            if (other.organism != null)
                return false;
        } else if (!organism.equals(other.organism))
            return false;
        if (organismHosts == null) {
            if (other.organismHosts != null)
                return false;
        } else if (!organismHosts.equals(other.organismHosts))
            return false;
        if (primaryAccession == null) {
            if (other.primaryAccession != null)
                return false;
        } else if (!primaryAccession.equals(other.primaryAccession))
            return false;
        if (proteinDescription == null) {
            if (other.proteinDescription != null)
                return false;
        } else if (!proteinDescription.equals(other.proteinDescription))
            return false;
        if (proteinExistence != other.proteinExistence)
            return false;
        if (references == null) {
            if (other.references != null)
                return false;
        } else if (!references.equals(other.references))
            return false;
        if (secondaryAccessions == null) {
            if (other.secondaryAccessions != null)
                return false;
        } else if (!secondaryAccessions.equals(other.secondaryAccessions))
            return false;
        if (sequence == null) {
            if (other.sequence != null)
                return false;
        } else if (!sequence.equals(other.sequence))
            return false;
        if (uniProtId == null) {
            if (other.uniProtId != null)
                return false;
        } else if (!uniProtId.equals(other.uniProtId))
            return false;
        return true;
    }

}

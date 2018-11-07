package uk.ac.ebi.uniprot.domain.uniprot.impl;

import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import uk.ac.ebi.uniprot.domain.common.Sequence;
import uk.ac.ebi.uniprot.domain.feature.FeatureType;
import uk.ac.ebi.uniprot.domain.gene.Gene;
import uk.ac.ebi.uniprot.domain.taxonomy.Organism;
import uk.ac.ebi.uniprot.domain.taxonomy.OrganismHost;
import uk.ac.ebi.uniprot.domain.taxonomy.TaxonName;
import uk.ac.ebi.uniprot.domain.uniprot.EntryAudit;
import uk.ac.ebi.uniprot.domain.uniprot.InternalSection;
import uk.ac.ebi.uniprot.domain.uniprot.Keyword;
import uk.ac.ebi.uniprot.domain.uniprot.Organelle;
import uk.ac.ebi.uniprot.domain.uniprot.ProteinExistence;
import uk.ac.ebi.uniprot.domain.uniprot.UniProtAccession;
import uk.ac.ebi.uniprot.domain.uniprot.UniProtDBCrossReferences;
import uk.ac.ebi.uniprot.domain.uniprot.UniProtEntry;
import uk.ac.ebi.uniprot.domain.uniprot.UniProtEntryType;
import uk.ac.ebi.uniprot.domain.uniprot.UniProtFeatures;
import uk.ac.ebi.uniprot.domain.uniprot.UniProtId;
import uk.ac.ebi.uniprot.domain.uniprot.UniProtReferences;
import uk.ac.ebi.uniprot.domain.uniprot.UniProtTaxonId;
import uk.ac.ebi.uniprot.domain.uniprot.comments.Comments;
import uk.ac.ebi.uniprot.domain.uniprot.comments.impl.CommentsImpl;
import uk.ac.ebi.uniprot.domain.uniprot.description.ProteinDescription;
import uk.ac.ebi.uniprot.domain.uniprot.xdb.impl.UniProtDBCrossReferencesImpl;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UniProtEntryImpl implements UniProtEntry {
    private final UniProtEntryType entryType;
    private final UniProtAccession accession;
    private final List<UniProtAccession> secondaryAccessions;
    private final UniProtId uniprotId;
    private final List<TaxonName> lineage;
    private final ProteinExistence proteinExistance;
    private final EntryAudit entryAudit;
    private final List<Organelle> organelles;
    private final  List<Keyword> keywords;
    private final ProteinDescription description;
    private final Comments comments;
    private final UniProtReferences references;
    private final List<Gene> genes;
    private final Organism organism;
    private final List<OrganismHost> organismHosts;
    private final UniProtDBCrossReferences xrefs;
    private final Sequence sequence;
    private final UniProtTaxonId taxonId;
    @JsonIgnore
    private final InternalSection internalSection;
    private final UniProtFeatures features;
    public UniProtEntryImpl(UniProtEntryType entryType,
            UniProtAccession accession,
            List<UniProtAccession> secondaryAccessions,
            UniProtId uniprotId,
            List<TaxonName> lineage,
            ProteinExistence proteinExistance,
            EntryAudit entryAudit,
            List<Organelle> organelles,
            List<Keyword> keywords,
            ProteinDescription description,
            Comments comments,
            UniProtReferences references,
            List<Gene> genes,
            UniProtFeatures features,
            Organism organism,
            List<OrganismHost> organismHosts,
            UniProtDBCrossReferences xrefs,
            Sequence sequence,
            UniProtTaxonId taxonId,
            InternalSection internalSection
            ){
        this.entryType = entryType;
        this.accession = accession;
        if ((secondaryAccessions == null) || secondaryAccessions.isEmpty()) {
            this.secondaryAccessions = Collections.emptyList();
        } else {
            this.secondaryAccessions = Collections.unmodifiableList(secondaryAccessions);
        }
        this.uniprotId =uniprotId;
        if ((lineage == null) || lineage.isEmpty()) {
            this.lineage = Collections.emptyList();
        } else {
            this.lineage = Collections.unmodifiableList(lineage);
        }
        this.proteinExistance = proteinExistance;
        this.entryAudit =entryAudit;
        if ((organelles == null) || organelles.isEmpty()) {
            this.organelles = Collections.emptyList();
        } else {
            this.organelles = Collections.unmodifiableList(organelles);
        }
        if ((keywords == null) || keywords.isEmpty()) {
            this.keywords = Collections.emptyList();
        } else {
            this.keywords = Collections.unmodifiableList(keywords);
        }
        this.description = description;
        if(comments !=null)
            this.comments = comments;
        else
            this.comments = new CommentsImpl(null);
        
        if(references !=null)
            this.references = references;
        else
            this.references = new UniProtReferencesImpl(null);
        if ((genes == null) || genes.isEmpty()) {
            this.genes = Collections.emptyList();
        } else {
            this.genes = Collections.unmodifiableList(genes);
        }
        if(features !=null)
            this.features = features;
        else
            this.features = new UniProtFeaturesImpl(null);
        this.organism = organism;
        if ((organismHosts == null) || organismHosts.isEmpty()) {
            this.organismHosts = Collections.emptyList();
        } else {
            this.organismHosts = Collections.unmodifiableList(organismHosts);
        }
        if(xrefs !=null)
            this.xrefs = xrefs;
        else
            this.xrefs = new UniProtDBCrossReferencesImpl(null);

        this.sequence = sequence;
        this.taxonId = taxonId;
        this.internalSection =internalSection;
    }
    @Override
    public UniProtAccession getPrimaryUniProtAccession() {
       return accession;
    }

    @Override
    public List<UniProtAccession> getSecondaryUniProtAccessions() {
        return secondaryAccessions;
    }

    @Override
    public UniProtId getUniProtId() {
        return uniprotId;
    }

    @Override
    public List<TaxonName> getTaxonomyLineage() {
        return lineage;
    }

    @Override
    public ProteinExistence getProteinExistence() {
        return proteinExistance;
    }

    @Override
    public UniProtEntryType getType() {
        return entryType;
    }

    @Override
    public EntryAudit getEntryAudit() {
        return entryAudit;
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
    public ProteinDescription getProteinDescription() {
        return description;
    }

    @Override
    public Comments getComments() {
        return comments;
    }

    @Override
    public UniProtReferences getReferences() {
       return references;
    }

    @Override
    public List<Gene> getGenes() {
        return genes;
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
    public UniProtDBCrossReferences getDatabaseCrossReferences() {
        return xrefs;
    }

    @Override
    public Sequence getSequence() {
        return sequence;
    }

    @Override
    public UniProtTaxonId getTaxonId() {
        return taxonId;
    }

    @Override
    public Boolean isFragment() {
        return !features.getFeaturesByType(FeatureType.NON_TER).isEmpty();
    }

    @Override
    public InternalSection getInternalSection() {
        return  internalSection;
    }
    @Override
    public UniProtFeatures getFeatures() {
       return features;
    }
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((accession == null) ? 0 : accession.hashCode());
        result = prime * result + ((comments == null) ? 0 : comments.hashCode());
        result = prime * result + ((description == null) ? 0 : description.hashCode());
        result = prime * result + ((entryAudit == null) ? 0 : entryAudit.hashCode());
        result = prime * result + ((entryType == null) ? 0 : entryType.hashCode());
        result = prime * result + ((features == null) ? 0 : features.hashCode());
        result = prime * result + ((genes == null) ? 0 : genes.hashCode());
        result = prime * result + ((internalSection == null) ? 0 : internalSection.hashCode());
        result = prime * result + ((keywords == null) ? 0 : keywords.hashCode());
        result = prime * result + ((organelles == null) ? 0 : organelles.hashCode());
        result = prime * result + ((organism == null) ? 0 : organism.hashCode());
        result = prime * result + ((organismHosts == null) ? 0 : organismHosts.hashCode());
        result = prime * result + ((proteinExistance == null) ? 0 : proteinExistance.hashCode());
        result = prime * result + ((references == null) ? 0 : references.hashCode());
        result = prime * result + ((secondaryAccessions == null) ? 0 : secondaryAccessions.hashCode());
        result = prime * result + ((sequence == null) ? 0 : sequence.hashCode());
        result = prime * result + ((taxonId == null) ? 0 : taxonId.hashCode());
        result = prime * result + ((lineage == null) ? 0 : lineage.hashCode());
        result = prime * result + ((uniprotId == null) ? 0 : uniprotId.hashCode());
        result = prime * result + ((xrefs == null) ? 0 : xrefs.hashCode());
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
        if (accession == null) {
            if (other.accession != null)
                return false;
        } else if (!accession.equals(other.accession))
            return false;
        if (comments == null) {
            if (other.comments != null)
                return false;
        } else if (!comments.equals(other.comments))
            return false;
        if (description == null) {
            if (other.description != null)
                return false;
        } else if (!description.equals(other.description))
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
        if (proteinExistance != other.proteinExistance)
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
        if (taxonId == null) {
            if (other.taxonId != null)
                return false;
        } else if (!taxonId.equals(other.taxonId))
            return false;
        if (lineage == null) {
            if (other.lineage != null)
                return false;
        } else if (!lineage.equals(other.lineage))
            return false;
        if (uniprotId == null) {
            if (other.uniprotId != null)
                return false;
        } else if (!uniprotId.equals(other.uniprotId))
            return false;
        if (xrefs == null) {
            if (other.xrefs != null)
                return false;
        } else if (!xrefs.equals(other.xrefs))
            return false;
        return true;
    }

}

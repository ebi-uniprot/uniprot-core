package uk.ac.ebi.uniprot.domain.uniprot;

import uk.ac.ebi.uniprot.domain.Sequence;
import uk.ac.ebi.uniprot.domain.citation.CitationType;
import uk.ac.ebi.uniprot.domain.gene.Gene;
import uk.ac.ebi.uniprot.domain.taxonomy.Organism;
import uk.ac.ebi.uniprot.domain.taxonomy.OrganismHost;
import uk.ac.ebi.uniprot.domain.uniprot.comment.Comment;
import uk.ac.ebi.uniprot.domain.uniprot.comment.CommentType;
import uk.ac.ebi.uniprot.domain.uniprot.description.ProteinDescription;
import uk.ac.ebi.uniprot.domain.uniprot.feature.Feature;
import uk.ac.ebi.uniprot.domain.uniprot.feature.FeatureType;
import uk.ac.ebi.uniprot.domain.uniprot.xdb.UniProtDBCrossReference;
import uk.ac.ebi.uniprot.domain.uniprot.xdb.UniProtXDbType;

import java.io.Serializable;
import java.util.List;

/**
 * @author jieluo
 * @date 17 Jan 2017
 * @time 18:41:20
 */
public interface UniProtEntry extends Serializable {
    UniProtEntryType getEntryType();

    UniProtAccession getPrimaryAccession();

    List<UniProtAccession> getSecondaryAccessions();

    UniProtId getUniProtId();

    EntryAudit getEntryAudit();

    Organism getOrganism();

    List<OrganismHost> getOrganismHosts();

    ProteinExistence getProteinExistence();

    ProteinDescription getProteinDescription();

    List<Gene> getGenes();

    List<Comment> getComments();

    <T extends Comment> List<T> getCommentByType(CommentType type);

    List<Feature> getFeatures();

    List<Feature> getFeaturesByType(FeatureType type);

    List<GeneLocation> getGeneLocations();

    List<Keyword> getKeywords();

    List<UniProtReference> getReferences();

    List<UniProtReference> getReferencesByType(CitationType type);

    List<UniProtDBCrossReference> getDatabaseCrossReferences();

    List<UniProtDBCrossReference> getDatabaseCrossReferencesByType(UniProtXDbType type);

    List<UniProtDBCrossReference> getDatabaseCrossReferencesByType(String dbName);

    Sequence getSequence();

    InternalSection getInternalSection();

    Boolean isFragment();

    EntryInactiveReason getInactiveReason();

    boolean isActive();

    boolean hasSecondaryAccessions();

    boolean hasOrganism();

    boolean hasOrganismHosts();

    boolean hasProteinExistence();

    boolean hasProteinDescription();

    boolean hasGenes();

    boolean hasComments();

    boolean hasFeatures();

    boolean hasGeneLocations();

    boolean hasKeywords();

    boolean hasReferences();

    boolean hasDatabaseCrossReferences();

}

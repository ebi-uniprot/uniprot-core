package org.uniprot.core.uniprot;

import java.io.Serializable;
import java.util.List;

import org.uniprot.core.Sequence;
import org.uniprot.core.citation.CitationType;
import org.uniprot.core.gene.Gene;
import org.uniprot.core.uniprot.comment.Comment;
import org.uniprot.core.uniprot.comment.CommentType;
import org.uniprot.core.uniprot.description.ProteinDescription;
import org.uniprot.core.uniprot.feature.Feature;
import org.uniprot.core.uniprot.feature.FeatureType;
import org.uniprot.core.uniprot.taxonomy.Organism;
import org.uniprot.core.uniprot.taxonomy.OrganismHost;
import org.uniprot.core.uniprot.xdb.UniProtDBCrossReference;
import org.uniprot.core.uniprot.xdb.UniProtXDbType;

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

    double getAnnotationScore();

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

    boolean hasAnnotationScore();

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
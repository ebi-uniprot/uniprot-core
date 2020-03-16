package org.uniprot.core.uniprotkb;

import java.io.Serializable;
import java.util.List;

import org.uniprot.core.Sequence;
import org.uniprot.core.citation.CitationType;
import org.uniprot.core.gene.Gene;
import org.uniprot.core.taxonomy.TaxonomyLineage;
import org.uniprot.core.uniprotkb.comment.Comment;
import org.uniprot.core.uniprotkb.comment.CommentType;
import org.uniprot.core.uniprotkb.description.ProteinDescription;
import org.uniprot.core.uniprotkb.evidence.Evidence;
import org.uniprot.core.uniprotkb.feature.Feature;
import org.uniprot.core.uniprotkb.feature.FeatureType;
import org.uniprot.core.uniprotkb.taxonomy.Organism;
import org.uniprot.core.uniprotkb.taxonomy.OrganismHost;
import org.uniprot.core.uniprotkb.xdb.UniProtkbCrossReference;
import org.uniprot.core.uniprotkb.xdb.UniProtkbDatabase;

/**
 * @author jieluo
 * @date 17 Jan 2017
 * @time 18:41:20
 */
public interface UniProtkbEntry extends Serializable {
    UniProtkbEntryType getEntryType();

    UniProtkbAccession getPrimaryAccession();

    List<UniProtkbAccession> getSecondaryAccessions();

    UniProtkbId getUniProtkbId();

    double getAnnotationScore();

    EntryAudit getEntryAudit();

    Organism getOrganism();

    List<OrganismHost> getOrganismHosts();

    ProteinExistence getProteinExistence();

    ProteinDescription getProteinDescription();

    List<Gene> getGenes();

    List<Comment> getComments();

    <T extends Comment> List<T> getCommentsByType(CommentType type);

    List<Feature> getFeatures();

    List<Feature> getFeaturesByType(FeatureType type);

    List<GeneLocation> getGeneLocations();

    List<Keyword> getKeywords();

    List<UniProtkbReference> getReferences();

    List<UniProtkbReference> getReferencesByType(CitationType type);

    List<UniProtkbCrossReference> getUniProtkbCrossReferences();

    List<UniProtkbCrossReference> getUniProtCrossReferencesByType(UniProtkbDatabase type);

    List<UniProtkbCrossReference> getUniProtCrossReferencesByType(String dbName);

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

    boolean hasUniProtCrossReferences();

    List<Evidence> gatherEvidences();

    List<TaxonomyLineage> getLineages();
}

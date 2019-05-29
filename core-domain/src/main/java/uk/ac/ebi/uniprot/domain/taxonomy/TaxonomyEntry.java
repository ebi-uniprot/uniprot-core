package uk.ac.ebi.uniprot.domain.taxonomy;

import uk.ac.ebi.uniprot.domain.uniprot.taxonomy.Taxonomy;

import java.io.Serializable;
import java.util.List;
/**
 *
 * @author lgonzales
 */
public interface TaxonomyEntry extends Taxonomy, Serializable {

    long getParentId();

    TaxonomyRank getRank();

    boolean isHidden();

    boolean isActive();

    List<String> getOtherNames();

    List<TaxonomyLineage> getLineage();

    List<TaxonomyStrain> getStrains();

    List<Taxonomy> getHosts();

    List<String> getLinks();

    TaxonomyStatistics getStatistics();

    TaxonomyInactiveReason getInactiveReason();

    boolean hasParentId();

    boolean hasRank();

    boolean hasOtherNames();

    boolean hasLineage();

    boolean hasStrains();

    boolean hasHosts();

    boolean hasLinks();

    boolean hasStatistics();

    boolean hasInactiveReason();

}

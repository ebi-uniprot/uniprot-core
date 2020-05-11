package org.uniprot.core.taxonomy;

import org.uniprot.core.uniprotkb.taxonomy.Taxonomy;

import java.io.Serializable;
import java.util.List;

/** @author lgonzales */
public interface TaxonomyEntry extends Taxonomy, Serializable {

    Long getParentId();

    TaxonomyRank getRank();

    Boolean isHidden();

    Boolean isActive();

    List<String> getOtherNames();

    List<TaxonomyLineage> getLineages();

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

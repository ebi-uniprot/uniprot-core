package org.uniprot.core.taxonomy;

import java.io.Serializable;
import java.util.List;

import org.uniprot.core.uniprotkb.taxonomy.Taxonomy;

/** @author lgonzales */
public interface TaxonomyEntry extends Taxonomy, Serializable {

    Taxonomy getParent();

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

    boolean hasParent();

    boolean hasRank();

    boolean hasOtherNames();

    boolean hasLineage();

    boolean hasStrains();

    boolean hasHosts();

    boolean hasLinks();

    boolean hasStatistics();

    boolean hasInactiveReason();
}

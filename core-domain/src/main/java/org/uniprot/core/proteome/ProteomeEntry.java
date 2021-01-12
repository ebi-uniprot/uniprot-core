package org.uniprot.core.proteome;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import org.uniprot.core.citation.Citation;
import org.uniprot.core.taxonomy.TaxonomyLineage;
import org.uniprot.core.uniprotkb.taxonomy.Taxonomy;

public interface ProteomeEntry extends Serializable {
    ProteomeId getId();

    Taxonomy getTaxonomy();

    String getDescription();

    LocalDate getModified();

    ProteomeType getProteomeType();

    ProteomeId getRedundantTo();

    String getStrain();

    String getIsolate();

    List<Component> getComponents();

    List<Citation> getCitations();

    List<RedundantProteome> getRedudantProteomes();

    ProteomeId getPanproteome();

    Integer getAnnotationScore();

    Superkingdom getSuperkingdom();

    Integer getProteinCount();

    Integer getGeneCount();

    List<TaxonomyLineage> getTaxonLineages();

    ProteomeCompletenessReport getProteomeCompletenessReport();

    GenomeAssembly getGenomeAssembly();

    GenomeAnnotation getGenomeAnnotation();

    List<ExclusionReason> getExclusionReasons();
}

package org.uniprot.core.proteome;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import org.uniprot.core.DBCrossReference;
import org.uniprot.core.citation.Citation;
import org.uniprot.core.taxonomy.TaxonomyLineage;
import org.uniprot.core.uniprot.taxonomy.Taxonomy;

public interface ProteomeEntry extends Serializable {
    ProteomeId getId();

    Taxonomy getTaxonomy();

    String getDescription();

    LocalDate getModified();

    ProteomeType getProteomeType();

    ProteomeId getRedundantTo();

    String getStrain();

    String getIsolate();

    List<DBCrossReference<ProteomeDatabase>> getDbXReferences();

    List<Component> getComponents();

    List<Citation> getReferences();

    List<RedundantProteome> getRedudantProteomes();

    ProteomeId getPanproteome();

    int getAnnotationScore();

    Superkingdom getSuperkingdom();

    int getProteinCount();

    int getGeneCount();

    List<TaxonomyLineage> getTaxonLineages();

    List<CanonicalProtein> getCanonicalProteins();

    String getSourceDb();
}

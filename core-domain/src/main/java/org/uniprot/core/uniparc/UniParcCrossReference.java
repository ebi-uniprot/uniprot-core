package org.uniprot.core.uniparc;

import java.time.LocalDate;

import org.uniprot.core.CrossReference;
import org.uniprot.core.uniprotkb.taxonomy.Organism;

/**
 * @author jluo
 * @date: 21 May 2019
 */
public interface UniParcCrossReference extends CrossReference<UniParcDatabase> {

    int getVersionI();

    Integer getVersion();

    boolean isActive();

    LocalDate getCreated();

    LocalDate getLastUpdated();

    String getGeneName();

    String getProteinName();

    Organism getTaxonomy();

    String getChain();

    String getNcbiGi();

    String getProteomeId();

    String getComponent();

}

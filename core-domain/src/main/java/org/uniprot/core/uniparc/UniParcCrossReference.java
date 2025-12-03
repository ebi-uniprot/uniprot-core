package org.uniprot.core.uniparc;

import java.time.LocalDate;
import java.util.List;

import org.uniprot.core.CrossReference;
import org.uniprot.core.uniprotkb.taxonomy.Organism;
import org.uniprot.core.util.Pair;

/**
 * @author jluo
 * @date: 21 May 2019
 */
public interface UniParcCrossReference extends CrossReference<UniParcDatabase> {

    /**
     * sources property will store UniParcDatabaseName:sourceXrefId:ProteomeId:ComponentName
     * For example: UniProtKB/Swiss-Prot:ABC01415:UP000005640:Chromosome 1
     */
    String PROPERTY_SOURCES = "sources";

    int getVersionI();

    Integer getVersion();

    boolean isActive();

    LocalDate getCreated();

    LocalDate getLastUpdated();

    String getGeneName();

    String getProteinName();

    Organism getOrganism();

    String getChain();

    String getNcbiGi();

    List<Pair<String, String>> getProteomeIdComponentPairs();

}

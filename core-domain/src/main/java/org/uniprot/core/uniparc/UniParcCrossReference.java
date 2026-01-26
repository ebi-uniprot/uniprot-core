package org.uniprot.core.uniparc;

import org.uniprot.core.CrossReference;
import org.uniprot.core.uniprotkb.taxonomy.Organism;

import java.time.LocalDate;
import java.util.List;

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

    List<Proteome> getProteomes();

}

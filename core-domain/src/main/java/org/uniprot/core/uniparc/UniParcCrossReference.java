package org.uniprot.core.uniparc;

import org.uniprot.core.CrossReference;

import java.time.LocalDate;

/**
 * @author jluo
 * @date: 21 May 2019
 */
public interface UniParcCrossReference extends CrossReference<UniParcDatabase> {
    String PROPERTY_GENE_NAME = "gene_name";
    String PROPERTY_PROTEIN_NAME = "protein_name";
    String PROPERTY_UNIPROT_KB_ACCESSION = "UniProtKB_accession";
    String PROPERTY_CHAIN = "chain";
    String PROPERTY_NCBI_GI = "NCBI_GI";
    String PROPERTY_PROTEOME_ID = "proteome_id";
    String PROPERTY_COMPONENT = "component";
    String PROPERTY_NCBI_TAXONOMY_ID = "NCBI_taxonomy_id";

    int getVersionI();

    Integer getVersion();

    boolean isActive();

    LocalDate getCreated();

    LocalDate getLastUpdated();
}

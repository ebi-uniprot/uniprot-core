package org.uniprot.core.uniparc;

import java.time.LocalDate;

import org.uniprot.core.DBCrossReference;

/**
 * @author jluo
 * @date: 21 May 2019
 */
public interface UniParcDBCrossReference extends DBCrossReference<UniParcDatabaseType> {
    public final String PROPERTY_GENE_NAME = "gene_name";

    public final String PROPERTY_PROTEIN_NAME = "protein_name";

    public final String PROPERTY_UNIPROT_KB_ACCESSION = "UniProtKB_accession";

    public final String PROPERTY_CHAIN = "chain";

    public final String PROPERTY_NCBI_GI = "NCBI_GI";

    public final String PROPERTY_PROTEOME_ID = "proteome_id";
    public final String PROPERTY_COMPONENT = "component";

    public final String PROPERTY_NCBI_TAXONOMY_ID = "NCBI_taxonomy_id";

    int getVersionI();

    Integer getVersion();

    boolean isActive();

    LocalDate getCreated();

    LocalDate getLastUpdated();
}

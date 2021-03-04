package org.uniprot.core.publication;

import java.io.Serializable;
import java.util.Set;

import org.uniprot.core.uniprotkb.UniProtKBAccession;

/**
 * Created 02/12/2020
 *
 * @author Edd
 */
public interface MappedReference extends Serializable {
    UniProtKBAccession getUniProtKBAccession();

    MappedSource getSource();

    String getPubMedId();

    Set<String> getSourceCategories();
}

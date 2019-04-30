package uk.ac.ebi.uniprot.domain.taxonomy;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author lgonzales
 */
public interface TaxonomyStrain extends Serializable {

    String getName();

    boolean hasName();

    List<String> getSynonyms();

    boolean hasSynonyms();
}

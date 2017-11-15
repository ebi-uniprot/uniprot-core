package uk.ac.ebi.uniprot.domain.citation;

import java.util.List;
import java.util.Optional;

/**
 * Created by IntelliJ IDEA.
 * User: barrera
 * Date: 08/06/11
 * Time: 11:18
 */

public interface CitationXrefs {
    List<CitationXref> getAllXrefs();
    Optional<CitationXref> hasXref(CitationXrefType type);
}

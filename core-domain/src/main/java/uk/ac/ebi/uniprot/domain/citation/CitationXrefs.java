package uk.ac.ebi.uniprot.domain.citation;

import java.util.List;
import java.util.Optional;


public interface CitationXrefs {
    List<CitationXref> getAllXrefs();
    Optional<CitationXref> getTyped(CitationXrefType type);
}

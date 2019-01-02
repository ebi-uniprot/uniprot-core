package uk.ac.ebi.uniprot.domain.citation;

import uk.ac.ebi.uniprot.domain.DBCrossReference;

import java.util.List;
import java.util.Optional;

public interface CitationXrefs {
    List<DBCrossReference<CitationXrefType>> getXrefs();

    Optional<DBCrossReference<CitationXrefType>> getTyped(CitationXrefType type);
}

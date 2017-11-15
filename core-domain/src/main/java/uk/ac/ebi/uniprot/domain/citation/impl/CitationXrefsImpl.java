package uk.ac.ebi.uniprot.domain.citation.impl;

import uk.ac.ebi.uniprot.domain.citation.CitationXref;
import uk.ac.ebi.uniprot.domain.citation.CitationXrefType;
import uk.ac.ebi.uniprot.domain.citation.CitationXrefs;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class CitationXrefsImpl implements CitationXrefs {
    private final List<CitationXref> xrefs;

    public CitationXrefsImpl(List<CitationXref> xrefs) {
        if ((xrefs == null) || xrefs.isEmpty()) {
            this.xrefs = Collections.emptyList();
        } else
            this.xrefs = Collections.unmodifiableList(xrefs);
    }

    @Override
    public List<CitationXref> getAllXrefs() {
        return xrefs;
    }

    @Override
    public Optional<CitationXref> hasXref(CitationXrefType type) {
        return xrefs.stream().filter(xref -> xref.getXrefType() == type)
                .findAny();
    }

}

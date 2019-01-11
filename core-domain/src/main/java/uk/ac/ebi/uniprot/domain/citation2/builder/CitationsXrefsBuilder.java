package uk.ac.ebi.uniprot.domain.citation2.builder;

import uk.ac.ebi.uniprot.domain.DBCrossReference;
import uk.ac.ebi.uniprot.domain.citation2.CitationXrefType;
import uk.ac.ebi.uniprot.domain.citation2.CitationXrefs;
import uk.ac.ebi.uniprot.domain.citation2.impl.CitationXrefsImpl;

import java.util.List;

/**
 * Created 10/01/19
 *
 * @author Edd
 */
public class CitationsXrefsBuilder implements Builder<CitationsXrefsBuilder, CitationXrefs> {
    private List<DBCrossReference<CitationXrefType>> xRefs;

    @Override
    public CitationXrefs build() {
        return new CitationXrefsImpl(xRefs);
    }

    @Override
    public CitationsXrefsBuilder from(CitationXrefs instance) {
        return new CitationsXrefsBuilder().xRefs(instance.getXrefs());
    }

    public CitationsXrefsBuilder xRefs(List<DBCrossReference<CitationXrefType>> xRefs) {
        this.xRefs.addAll(xRefs);
        return this;
    }

    public CitationsXrefsBuilder addXRefs(DBCrossReference<CitationXrefType> xRef) {
        this.xRefs.add(xRef);
        return this;
    }
}

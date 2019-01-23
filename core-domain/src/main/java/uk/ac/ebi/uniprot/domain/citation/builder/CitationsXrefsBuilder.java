package uk.ac.ebi.uniprot.domain.citation.builder;

import uk.ac.ebi.uniprot.domain.Builder2;
import uk.ac.ebi.uniprot.domain.DBCrossReference;
import uk.ac.ebi.uniprot.domain.citation.CitationXrefType;
import uk.ac.ebi.uniprot.domain.citation.CitationXrefs;
import uk.ac.ebi.uniprot.domain.citation.impl.CitationXrefsImpl;

import java.util.ArrayList;
import java.util.List;

import static uk.ac.ebi.uniprot.domain.util.Utils.nonNullAddAll;

/**
 * Created 10/01/19
 *
 * @author Edd
 */
public class CitationsXrefsBuilder implements Builder2<CitationsXrefsBuilder, CitationXrefs> {
    private List<DBCrossReference<CitationXrefType>> xRefs = new ArrayList<>();

    @Override
    public CitationXrefs build() {
        return new CitationXrefsImpl(xRefs);
    }

    @Override
    public CitationsXrefsBuilder from(CitationXrefs instance) {
        return this.xRefs(instance.getXrefs());
    }

    public CitationsXrefsBuilder xRefs(List<DBCrossReference<CitationXrefType>> xRefs) {
        nonNullAddAll(xRefs, this.xRefs);
        return this;
    }

    public CitationsXrefsBuilder addXRef(DBCrossReference<CitationXrefType> xRef) {
        this.xRefs.add(xRef);
        return this;
    }
}

package org.uniprot.core.publication.impl;

import org.uniprot.core.citation.Citation;
import org.uniprot.core.publication.MappedSource;
import org.uniprot.core.publication.UniProtKBMappedReference;
import org.uniprot.core.uniprotkb.ReferenceComment;
import org.uniprot.core.uniprotkb.UniProtKBAccession;

import java.util.List;
import java.util.Objects;
import java.util.Set;

import static java.util.Collections.emptyList;
import static java.util.Collections.emptySet;

/**
 * Created 02/12/2020
 *
 * @author Edd
 */
public class UniProtKBMappedReferenceImpl extends AbstractMappedReference
        implements UniProtKBMappedReference {
    private final List<ReferenceComment> referenceComments;
    private final List<String> referencePositions;
    private final int referenceNumber;
    private Citation citation;

    public UniProtKBMappedReferenceImpl() {
        this(null, null, null, 0, emptySet(), emptyList(), emptyList(), null);
    }

    public UniProtKBMappedReferenceImpl(
            MappedSource source,
            String pubMedId,
            UniProtKBAccession uniProtKBAccession,
            int referenceNumber,
            Set<String> sourceCategories,
            List<ReferenceComment> referenceComments,
            List<String> referencePositions,
            Citation citation) {
        super(source, pubMedId, uniProtKBAccession, sourceCategories);
        this.referenceNumber = referenceNumber;
        this.referenceComments = referenceComments;
        this.referencePositions = referencePositions;
        this.citation = citation;
    }

    @Override
    public List<ReferenceComment> getReferenceComments() {
        return referenceComments;
    }

    @Override
    public List<String> getReferencePositions() {
        return referencePositions;
    }

    @Override
    public int getReferenceNumber() {
        return referenceNumber;
    }

    @Override
    public Citation getCitation() { return citation;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UniProtKBMappedReferenceImpl that = (UniProtKBMappedReferenceImpl) o;
        return referenceNumber == that.referenceNumber &&
                Objects.equals(referenceComments, that.referenceComments) &&
                Objects.equals(referencePositions, that.referencePositions) &&
                Objects.equals(citation, that.getCitation());
    }

    @Override
    public int hashCode() {
        return Objects.hash(referenceComments, referencePositions, referenceNumber, citation);
    }
}

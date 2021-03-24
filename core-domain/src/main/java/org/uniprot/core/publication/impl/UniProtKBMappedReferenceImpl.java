package org.uniprot.core.publication.impl;

import static java.util.Collections.emptyList;
import static java.util.Collections.emptySet;

import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.uniprot.core.publication.MappedSource;
import org.uniprot.core.publication.UniProtKBMappedReference;
import org.uniprot.core.uniprotkb.ReferenceComment;
import org.uniprot.core.uniprotkb.UniProtKBAccession;

/**
 * Created 02/12/2020
 *
 * @author Edd
 */
public class UniProtKBMappedReferenceImpl extends AbstractMappedReference
        implements UniProtKBMappedReference {
    private static final long serialVersionUID = 2716462716407632063L;
    private final List<ReferenceComment> referenceComments;
    private final List<String> referencePositions;
    private final int referenceNumber;

    public UniProtKBMappedReferenceImpl() {
        this(null, null, null , 0, emptySet(), emptyList(), emptyList());
    }

    public UniProtKBMappedReferenceImpl(
            String citationId,
            MappedSource source,
            UniProtKBAccession uniProtKBAccession,
            int referenceNumber,
            Set<String> sourceCategories,
            List<ReferenceComment> referenceComments,
            List<String> referencePositions) {
        super(source, citationId, uniProtKBAccession, sourceCategories);
        this.referenceNumber = referenceNumber;
        this.referenceComments = referenceComments;
        this.referencePositions = referencePositions;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UniProtKBMappedReferenceImpl that = (UniProtKBMappedReferenceImpl) o;
        return referenceNumber == that.referenceNumber
                && Objects.equals(referenceComments, that.referenceComments)
                && Objects.equals(referencePositions, that.referencePositions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(referenceComments, referencePositions, referenceNumber);
    }
}

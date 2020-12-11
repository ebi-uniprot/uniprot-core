package org.uniprot.core.publication.impl;

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

    public UniProtKBMappedReferenceImpl() {
        this(null, null, null, emptySet(), emptyList(), emptyList());
    }

    public UniProtKBMappedReferenceImpl(
            MappedSource source,
            String pubMedId,
            UniProtKBAccession uniProtKBAccession,
            Set<String> sourceCategories,
            List<ReferenceComment> referenceComments,
            List<String> referencePositions) {
        super(source, pubMedId, uniProtKBAccession, sourceCategories);
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UniProtKBMappedReferenceImpl that = (UniProtKBMappedReferenceImpl) o;
        return Objects.equals(referenceComments, that.referenceComments)
                && Objects.equals(referencePositions, that.referencePositions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(referenceComments, referencePositions);
    }
}
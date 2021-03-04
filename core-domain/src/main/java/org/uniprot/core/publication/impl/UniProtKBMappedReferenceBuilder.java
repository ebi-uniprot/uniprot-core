package org.uniprot.core.publication.impl;

import static org.uniprot.core.util.Utils.addOrIgnoreNull;
import static org.uniprot.core.util.Utils.modifiableList;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import org.uniprot.core.citation.Citation;
import org.uniprot.core.publication.UniProtKBMappedReference;
import org.uniprot.core.uniprotkb.ReferenceComment;

/**
 * Created 02/12/2020
 *
 * @author Edd
 */
public class UniProtKBMappedReferenceBuilder
        extends AbstractMappedReferenceBuilder<
                UniProtKBMappedReferenceBuilder, UniProtKBMappedReference> {
    private List<ReferenceComment> referenceComments = new ArrayList<>();
    private List<String> referencePositions = new ArrayList<>();
    private int referenceNumber;
    private Citation citation;

    public UniProtKBMappedReferenceBuilder referenceCommentsAdd(ReferenceComment referenceComment) {
        addOrIgnoreNull(referenceComment, this.referenceComments);
        return getThis();
    }

    public UniProtKBMappedReferenceBuilder referenceCommentsSet(
            List<ReferenceComment> referenceComments) {
        this.referenceComments = modifiableList(referenceComments);
        return getThis();
    }

    public UniProtKBMappedReferenceBuilder referencePositionsAdd(String referencePosition) {
        addOrIgnoreNull(referencePosition, this.referencePositions);
        return getThis();
    }

    public UniProtKBMappedReferenceBuilder referencePositionsSet(List<String> referencePositions) {
        this.referencePositions = modifiableList(referencePositions);
        return getThis();
    }

    public UniProtKBMappedReferenceBuilder referenceNumber(int referenceNumber) {
        this.referenceNumber = referenceNumber;
        return getThis();
    }

    public UniProtKBMappedReferenceBuilder citation(Citation citation) {
        this.citation = citation;
        return getThis();
    }

    @Nonnull
    @Override
    public UniProtKBMappedReference build() {
        return new UniProtKBMappedReferenceImpl(
                source,
                pubMedId,
                uniProtKBAccession,
                referenceNumber,
                sourceCategories,
                referenceComments,
                referencePositions,
                citation);
    }

    @Nonnull
    @Override
    protected UniProtKBMappedReferenceBuilder getThis() {
        return this;
    }

    public static UniProtKBMappedReferenceBuilder from(@Nonnull UniProtKBMappedReference instance) {
        UniProtKBMappedReferenceBuilder builder = new UniProtKBMappedReferenceBuilder();
        return AbstractMappedReferenceBuilder.from(builder, instance)
                .referenceNumber(instance.getReferenceNumber())
                .referenceCommentsSet(instance.getReferenceComments())
                .referencePositionsSet(instance.getReferencePositions())
                .citation(instance.getCitation());
    }
}

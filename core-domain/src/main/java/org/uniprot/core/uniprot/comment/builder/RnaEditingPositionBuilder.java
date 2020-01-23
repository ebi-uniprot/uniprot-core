package org.uniprot.core.uniprot.comment.builder;

import static org.uniprot.core.util.Utils.addOrIgnoreNull;
import static org.uniprot.core.util.Utils.modifiableList;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import org.uniprot.core.Builder;
import org.uniprot.core.uniprot.comment.RnaEdPosition;
import org.uniprot.core.uniprot.comment.impl.RnaEditingCommentImpl;
import org.uniprot.core.uniprot.evidence.Evidence;

/**
 * Created 16/01/19
 *
 * @author Edd
 */
public class RnaEditingPositionBuilder
        implements Builder<RnaEditingPositionBuilder, RnaEdPosition> {
    private String position;
    private List<Evidence> evidences = new ArrayList<>();

    @Override
    public @Nonnull RnaEdPosition build() {
        return new RnaEditingCommentImpl.RnaEdPositionImpl(position, evidences);
    }

    public static @Nonnull RnaEditingPositionBuilder from(@Nonnull RnaEdPosition instance) {
        return new RnaEditingPositionBuilder().evidences(instance.getEvidences()).position(instance.getPosition());
    }

    public @Nonnull RnaEditingPositionBuilder position(String position) {
        this.position = position;
        return this;
    }

    public @Nonnull RnaEditingPositionBuilder evidences(List<Evidence> evidences) {
        this.evidences = modifiableList(evidences);
        return this;
    }

    public @Nonnull RnaEditingPositionBuilder addEvidence(Evidence evidence) {
        addOrIgnoreNull(evidence, this.evidences);
        return this;
    }
}

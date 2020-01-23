package org.uniprot.core.uniprot.comment.builder;

import static org.uniprot.core.util.Utils.addOrIgnoreNull;
import static org.uniprot.core.util.Utils.modifiableList;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import org.uniprot.core.uniprot.comment.SequenceCautionComment;
import org.uniprot.core.uniprot.comment.SequenceCautionType;
import org.uniprot.core.uniprot.comment.impl.SequenceCautionCommentImpl;
import org.uniprot.core.uniprot.evidence.Evidence;

public final class SequenceCautionCommentBuilder
        implements CommentBuilder<SequenceCautionCommentBuilder, SequenceCautionComment> {
    String molecule;
    private SequenceCautionType sequenceCautionType;
    private String sequence;
    private String note;
    private List<Evidence> evidences = new ArrayList<>();

    public @Nonnull SequenceCautionComment build() {
        return new SequenceCautionCommentImpl(
                molecule, sequenceCautionType, sequence, note, evidences);
    }

    public static @Nonnull SequenceCautionCommentBuilder from(@Nonnull SequenceCautionComment instance) {
        return new SequenceCautionCommentBuilder().sequenceCautionType(instance.getSequenceCautionType())
                .sequence(instance.getSequence())
                .evidences(instance.getEvidences())
                .note(instance.getNote())
                .molecule(instance.getMolecule());
    }

    public @Nonnull SequenceCautionCommentBuilder sequenceCautionType(
            SequenceCautionType sequenceCautionType) {
        this.sequenceCautionType = sequenceCautionType;
        return this;
    }

    public @Nonnull SequenceCautionCommentBuilder molecule(String molecule) {
        this.molecule = molecule;
        return this;
    }

    public @Nonnull SequenceCautionCommentBuilder sequence(String sequence) {
        this.sequence = sequence;
        return this;
    }

    public @Nonnull SequenceCautionCommentBuilder note(String note) {
        this.note = note;
        return this;
    }

    public @Nonnull SequenceCautionCommentBuilder evidences(List<Evidence> evidences) {
        this.evidences = modifiableList(evidences);
        return this;
    }

    public @Nonnull SequenceCautionCommentBuilder addEvidence(Evidence evidence) {
        addOrIgnoreNull(evidence, this.evidences);
        return this;
    }
}

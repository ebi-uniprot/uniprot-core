package org.uniprot.core.uniprot.comment.builder;

import static org.uniprot.core.util.Utils.addOrIgnoreNull;
import static org.uniprot.core.util.Utils.modifiableList;

import java.util.ArrayList;
import java.util.List;

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

    public SequenceCautionComment build() {
        return new SequenceCautionCommentImpl(molecule,
                sequenceCautionType, sequence, note, evidences);
    }

    @Override
    public SequenceCautionCommentBuilder from(SequenceCautionComment instance) {
        evidences.clear();
        return this.sequenceCautionType(instance.getSequenceCautionType())
                .sequence(instance.getSequence())
                .evidences(instance.getEvidences())
                .note(instance.getNote())
                .molecule(instance.getMolecule());
    }

    public SequenceCautionCommentBuilder sequenceCautionType(
            SequenceCautionType sequenceCautionType) {
        this.sequenceCautionType = sequenceCautionType;
        return this;
    }

    public SequenceCautionCommentBuilder molecule(String molecule) {
        this.molecule = molecule;
        return this;
    }
    
    public SequenceCautionCommentBuilder sequence(String sequence) {
        this.sequence = sequence;
        return this;
    }

    public SequenceCautionCommentBuilder note(String note) {
        this.note = note;
        return this;
    }

    public SequenceCautionCommentBuilder evidences(List<Evidence> evidences) {
        this.evidences = modifiableList(evidences);
        return this;
    }

    public SequenceCautionCommentBuilder addEvidence(Evidence evidence) {
        addOrIgnoreNull(evidence, this.evidences);
        return this;
    }
}

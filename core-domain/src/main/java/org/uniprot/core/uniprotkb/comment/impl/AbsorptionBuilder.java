package org.uniprot.core.uniprotkb.comment.impl;

import static org.uniprot.core.util.Utils.addOrIgnoreNull;
import static org.uniprot.core.util.Utils.modifiableList;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import org.uniprot.core.Builder;
import org.uniprot.core.uniprotkb.comment.Absorption;
import org.uniprot.core.uniprotkb.comment.Note;
import org.uniprot.core.uniprotkb.evidence.Evidence;

/**
 * Created 15/01/19
 *
 * @author Edd
 */
public class AbsorptionBuilder implements Builder<Absorption> {
    private int max;
    private boolean approximate;
    private Note note;
    private List<Evidence> evidences = new ArrayList<>();

    @Override
    public @Nonnull Absorption build() {
        return new AbsorptionImpl(max, approximate, note, evidences);
    }

    public static @Nonnull AbsorptionBuilder from(@Nonnull Absorption instance) {
        return new AbsorptionBuilder()
                .note(instance.getNote())
                .approximate(instance.isApproximate())
                .max(instance.getMax())
                .evidencesSet(instance.getEvidences());
    }

    public @Nonnull AbsorptionBuilder max(int max) {
        this.max = max;
        return this;
    }

    public @Nonnull AbsorptionBuilder approximate(boolean approximate) {
        this.approximate = approximate;
        return this;
    }

    public @Nonnull AbsorptionBuilder note(Note note) {
        this.note = note;
        return this;
    }

    public @Nonnull AbsorptionBuilder evidencesSet(List<Evidence> evidences) {
        this.evidences = modifiableList(evidences);
        return this;
    }

    public @Nonnull AbsorptionBuilder evidencesAdd(Evidence evidence) {
        addOrIgnoreNull(evidence, this.evidences);
        return this;
    }
}

package org.uniprot.core.uniprot.comment.builder;

import java.util.ArrayList;
import java.util.List;

import org.uniprot.core.Builder;
import org.uniprot.core.uniprot.comment.Absorption;
import org.uniprot.core.uniprot.comment.Note;
import org.uniprot.core.uniprot.comment.impl.AbsorptionImpl;
import org.uniprot.core.uniprot.evidence.Evidence;

import javax.annotation.Nonnull;

/**
 * Created 15/01/19
 *
 * @author Edd
 */
public class AbsorptionBuilder implements Builder<AbsorptionBuilder, Absorption> {
    private int max;
    private boolean approximate;
    private Note note;
    private List<Evidence> evidences = new ArrayList<>();

    @Override
    public @Nonnull Absorption build() {
        return new AbsorptionImpl(max, approximate, note, evidences);
    }

    @Override
    public @Nonnull AbsorptionBuilder from(@Nonnull Absorption instance) {
        evidences.clear();
        return this.note(instance.getNote())
                .approximate(instance.isApproximate())
                .max(instance.getMax())
                .evidences(instance.getEvidences());
    }

    public AbsorptionBuilder max(int max) {
        this.max = max;
        return this;
    }

    public AbsorptionBuilder approximate(boolean approximate) {
        this.approximate = approximate;
        return this;
    }

    public AbsorptionBuilder note(Note note) {
        this.note = note;
        return this;
    }

    public AbsorptionBuilder evidences(List<Evidence> evidences) {
        this.evidences = evidences;
        return this;
    }
}

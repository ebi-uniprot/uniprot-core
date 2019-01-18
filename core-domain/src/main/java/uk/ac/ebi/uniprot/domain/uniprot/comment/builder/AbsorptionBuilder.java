package uk.ac.ebi.uniprot.domain.uniprot.comment.builder;

import uk.ac.ebi.uniprot.domain.Builder2;
import uk.ac.ebi.uniprot.domain.uniprot.comment.Absorption;
import uk.ac.ebi.uniprot.domain.uniprot.comment.Note;
import uk.ac.ebi.uniprot.domain.uniprot.comment.impl.AbsorptionImpl;
import uk.ac.ebi.uniprot.domain.uniprot.evidence2.Evidence;

import java.util.ArrayList;
import java.util.List;

/**
 * Created 15/01/19
 *
 * @author Edd
 */
public class AbsorptionBuilder implements Builder2<AbsorptionBuilder, Absorption> {
    private int max;
    private boolean approximate;
    private Note note;
    private List<Evidence> evidences = new ArrayList<>();

    @Override
    public Absorption build() {
        return new AbsorptionImpl(this);
    }

    @Override
    public AbsorptionBuilder from(Absorption instance) {
        evidences.clear();
        return this
                .note(instance.getNote())
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

    public int getMax() {
        return max;
    }

    public boolean isApproximate() {
        return approximate;
    }

    public Note getNote() {
        return note;
    }

    public List<Evidence> getEvidences() {
        return evidences;
    }
}

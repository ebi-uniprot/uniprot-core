package uk.ac.ebi.uniprot.domain.uniprot.comment2.impl;

import uk.ac.ebi.uniprot.domain.uniprot.comment2.Absorption;
import uk.ac.ebi.uniprot.domain.uniprot.comment2.Note;
import uk.ac.ebi.uniprot.domain.uniprot.comment2.builder.AbsorptionBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class AbsorptionImpl implements Absorption {
    private int max;
    private boolean approximate;
    private Note note;
    private List<Evidence> evidences;

    private AbsorptionImpl() {
        this.evidences = Collections.emptyList();
    }

    public AbsorptionImpl(int max, Note note, List<Evidence> evidences) {
        this(new AbsorptionBuilder()
                     .max(max)
                     .approximate(false)
                     .note(note).evidences(evidences));
    }

    public AbsorptionImpl(AbsorptionBuilder builder) {
        this.max = builder.getMax();
        this.approximate = builder.isApproximate();
        this.note = builder.getNote();
        if ((builder.getEvidences() == null) || builder.getEvidences().isEmpty()) {
            this.evidences = Collections.emptyList();
        } else
            this.evidences = Collections.unmodifiableList(builder.getEvidences());
    }

    @Override
    public List<Evidence> getEvidences() {
        return this.evidences;
    }

    @Override
    public int getMax() {
        return max;
    }

    @Override
    public Note getNote() {
        return note;
    }

    @Override
    public boolean isApproximate() {
        return this.approximate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbsorptionImpl that = (AbsorptionImpl) o;
        return max == that.max &&
                approximate == that.approximate &&
                Objects.equals(note, that.note) &&
                Objects.equals(evidences, that.evidences);
    }

    @Override
    public int hashCode() {
        return Objects.hash(max, approximate, note, evidences);
    }
}

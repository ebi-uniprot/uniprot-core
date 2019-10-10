package org.uniprot.core.uniprot.comment.impl;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.uniprot.core.uniprot.comment.Absorption;
import org.uniprot.core.uniprot.comment.Note;
import org.uniprot.core.uniprot.evidence.Evidence;
import org.uniprot.core.util.Utils;

public class AbsorptionImpl implements Absorption {
    private static final long serialVersionUID = -7439273402053104732L;
    private int max;
    private boolean approximate;
    private Note note;
    private List<Evidence> evidences;

    // no arg constructor for JSON deserialization
    AbsorptionImpl() {
        this.evidences = Collections.emptyList();
    }

    public AbsorptionImpl(int max, boolean approximate, Note note, List<Evidence> evidences) {
        this.max = max;
        this.approximate = approximate;
        this.note = note;
        this.evidences = Utils.unmodifiableList(evidences);
    }

    @Override
    public List<Evidence> getEvidences() {
        return this.evidences;
    }

    @Override
    public boolean hasEvidences() {
        return Utils.notNullOrEmpty(this.evidences);
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
    public boolean hasMax() {
        return this.max > 0;
    }

    @Override
    public boolean hasNote() {
        return this.note != null && this.note.hasTexts();
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
        return max == that.max
                && approximate == that.approximate
                && Objects.equals(note, that.note)
                && Objects.equals(evidences, that.evidences);
    }

    @Override
    public int hashCode() {
        return Objects.hash(max, approximate, note, evidences);
    }
}

package uk.ac.ebi.uniprot.domain.uniprot.comment.impl;

import uk.ac.ebi.uniprot.domain.uniprot.comment.Absorption;
import uk.ac.ebi.uniprot.domain.uniprot.comment.Note;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.uniprot.core.common.Utils;

public class AbsorptionImpl implements Absorption {
    private static final long serialVersionUID = -7439273402053104732L;
    private int max;
    private boolean approximate;
    private Note note;
    private List<Evidence> evidences;

    private AbsorptionImpl() {
        this.evidences = Collections.emptyList();
    }

    public AbsorptionImpl(int max, Note note, List<Evidence> evidences) {
        this(max, false, note, evidences);
    }

    public AbsorptionImpl(int max, boolean approximate, Note note, List<Evidence> evidences) {
        this.max = max;
        this.approximate = approximate;
        this.note = note;
        if ((evidences == null) || evidences.isEmpty()) {
            this.evidences = Collections.emptyList();
        } else
            this.evidences = Collections.unmodifiableList(evidences);
    }

    @Override
    public List<Evidence> getEvidences() {
        return this.evidences;
    }

    @Override
    public boolean hasEvidences() {
        return Utils.notEmpty(this.evidences);
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

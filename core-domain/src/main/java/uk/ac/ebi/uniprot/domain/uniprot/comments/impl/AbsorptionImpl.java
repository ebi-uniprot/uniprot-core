package uk.ac.ebi.uniprot.domain.uniprot.comments.impl;

import uk.ac.ebi.uniprot.domain.uniprot.EvidencedValue;
import uk.ac.ebi.uniprot.domain.uniprot.comments.Absorption;
import uk.ac.ebi.uniprot.domain.uniprot.comments.AbsorptionNote;
import uk.ac.ebi.uniprot.domain.uniprot.evidences.Evidence;

import java.util.Collections;
import java.util.List;

public class AbsorptionImpl implements Absorption {
    private final int max;
    private final boolean approximate;
    private final AbsorptionNote note;

    private List<Evidence> evidences;

    public static AbsorptionNote createAbsorptionNote(List<EvidencedValue> texts) {
        return new AbsorptionNoteImpl(texts);
    }

    public AbsorptionImpl(int max, boolean approximate, AbsorptionNote note, List<Evidence> evidences) {
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
        return this.getEvidences();
    }

    @Override
    public int getMax() {
        return max;
    }

    @Override
    public AbsorptionNote getNote() {
        return note;
    }

    @Override
    public boolean hasNote() {
        if ((note == null) || note.getTexts().isEmpty())
            return false;
        else
            return true;
    }

    @Override
    public boolean isApproximation() {
        return this.approximate;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (approximate ? 1231 : 1237);
        result = prime * result + ((evidences == null) ? 0 : evidences.hashCode());
        result = prime * result + max;
        result = prime * result + ((note == null) ? 0 : note.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        AbsorptionImpl other = (AbsorptionImpl) obj;
        if (approximate != other.approximate)
            return false;
        if (evidences == null) {
            if (other.evidences != null)
                return false;
        } else if (!evidences.equals(other.evidences))
            return false;
        if (max != other.max)
            return false;
        if (note == null) {
            if (other.note != null)
                return false;
        } else if (!note.equals(other.note))
            return false;
        return true;
    }

    static class AbsorptionNoteImpl extends FreeTextImpl implements AbsorptionNote {

        public AbsorptionNoteImpl(List<EvidencedValue> texts) {
            super(texts);
        }
    }
}

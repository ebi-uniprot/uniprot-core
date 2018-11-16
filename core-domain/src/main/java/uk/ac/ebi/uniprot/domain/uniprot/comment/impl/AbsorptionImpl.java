package uk.ac.ebi.uniprot.domain.uniprot.comment.impl;

import uk.ac.ebi.uniprot.domain.uniprot.EvidencedValue;
import uk.ac.ebi.uniprot.domain.uniprot.comment.Absorption;
import uk.ac.ebi.uniprot.domain.uniprot.comment.Note;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class AbsorptionImpl implements Absorption {

    private final int max;
    private final boolean approximate;
    private final Optional<Note> note;
    private final List<Evidence> evidences;

    public AbsorptionImpl(int max, boolean approximate, Note note, List<Evidence> evidences) {
        this.max = max;
        this.approximate = approximate;
        this.note = (note == null)? Optional.empty():  Optional.of(note);
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
    public int getMax() {
        return max;
    }

    @Override
    public Optional<Note> getNote() {
        return note;
    }


    @Override
    public boolean isApproximation() {
        return this.approximate;
    }

    @Override 
    public String toString() {
    	    StringBuilder sb = new StringBuilder();
        sb.append("\nCC       Absorption:\n");
        sb.append("CC         Abs(max)=");
        if (isApproximation()) {
            sb.append("~");
        }
        sb.append(getMax());

        sb.append(" nm;");

        if (getNote().isPresent()) {
            sb.append("\nCC         Note=").append(getNote().get().toString()).append(";");
        }
        
        return sb.toString();

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
    
}

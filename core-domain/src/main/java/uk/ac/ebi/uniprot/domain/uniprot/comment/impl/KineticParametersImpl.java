package uk.ac.ebi.uniprot.domain.uniprot.comment.impl;

import uk.ac.ebi.uniprot.domain.uniprot.comment.KineticParameters;
import uk.ac.ebi.uniprot.domain.uniprot.comment.MaximumVelocity;
import uk.ac.ebi.uniprot.domain.uniprot.comment.MichaelisConstant;
import uk.ac.ebi.uniprot.domain.uniprot.comment.Note;

import java.util.Collections;
import java.util.List;

public class KineticParametersImpl implements KineticParameters {
    private List<MaximumVelocity> maximumVelocities;
    private List<MichaelisConstant> michaelisConstants;
    private Note note;

    private KineticParametersImpl() {
        this.maximumVelocities = Collections.emptyList();
        this.michaelisConstants = Collections.emptyList();
    }

    public KineticParametersImpl(List<MaximumVelocity> maximumVelocities,
                                 List<MichaelisConstant> michaelisConstants,
                                 Note note) {
        if ((maximumVelocities == null) || maximumVelocities.isEmpty()) {
            this.maximumVelocities = Collections.emptyList();
        } else {
            this.maximumVelocities = Collections.unmodifiableList(maximumVelocities);
        }
        if ((michaelisConstants == null) || michaelisConstants.isEmpty()) {
            this.michaelisConstants = Collections.emptyList();
        } else {
            this.michaelisConstants = Collections.unmodifiableList(michaelisConstants);
        }
        this.note = note;
    }

    @Override
    public List<MaximumVelocity> getMaximumVelocities() {
        return maximumVelocities;
    }

    @Override
    public List<MichaelisConstant> getMichaelisConstants() {
        return michaelisConstants;
    }

    @Override
    public Note getNote() {
        return note;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((maximumVelocities == null) ? 0 : maximumVelocities.hashCode());
        result = prime * result + ((michaelisConstants == null) ? 0 : michaelisConstants.hashCode());
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
        KineticParametersImpl other = (KineticParametersImpl) obj;
        if (maximumVelocities == null) {
            if (other.maximumVelocities != null)
                return false;
        } else if (!maximumVelocities.equals(other.maximumVelocities))
            return false;
        if (michaelisConstants == null) {
            if (other.michaelisConstants != null)
                return false;
        } else if (!michaelisConstants.equals(other.michaelisConstants))
            return false;
        if (note == null) {
            if (other.note != null)
                return false;
        } else if (!note.equals(other.note))
            return false;
        return true;
    }


}

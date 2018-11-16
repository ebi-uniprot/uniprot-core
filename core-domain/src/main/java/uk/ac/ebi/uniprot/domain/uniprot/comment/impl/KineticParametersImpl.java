package uk.ac.ebi.uniprot.domain.uniprot.comment.impl;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import uk.ac.ebi.uniprot.domain.uniprot.comment.KineticParameters;
import uk.ac.ebi.uniprot.domain.uniprot.comment.MaximumVelocity;
import uk.ac.ebi.uniprot.domain.uniprot.comment.MichaelisConstant;
import uk.ac.ebi.uniprot.domain.uniprot.comment.Note;

public class KineticParametersImpl implements KineticParameters {
    private final List<MaximumVelocity> velocities;
    private final List<MichaelisConstant> mConstants;
    private final Optional<Note> note;
    public KineticParametersImpl(List<MaximumVelocity> velocities, List<MichaelisConstant> mConstants,
            Note note){
        if((velocities ==null) || velocities.isEmpty()){
            this.velocities = Collections.emptyList();
        }else{
            this.velocities = Collections.unmodifiableList(velocities);
        }
        if((mConstants ==null) || mConstants.isEmpty()){
            this.mConstants = Collections.emptyList();
        }else{
            this.mConstants = Collections.unmodifiableList(mConstants);
        }
        this.note = (note == null)? Optional.empty():  Optional.of(note);
    }
    @Override
    public List<MaximumVelocity> getMaximumVelocities() {
       return velocities;
    }

    @Override
    public List<MichaelisConstant> getMichaelisConstants() {
        return mConstants;
    }

    @Override
    public Optional<Note> getNote() {
    		return note;
    }

    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((mConstants == null) ? 0 : mConstants.hashCode());
        result = prime * result + ((note == null) ? 0 : note.hashCode());
        result = prime * result + ((velocities == null) ? 0 : velocities.hashCode());
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
        if (mConstants == null) {
            if (other.mConstants != null)
                return false;
        } else if (!mConstants.equals(other.mConstants))
            return false;
        if (note == null) {
            if (other.note != null)
                return false;
        } else if (!note.equals(other.note))
            return false;
        if (velocities == null) {
            if (other.velocities != null)
                return false;
        } else if (!velocities.equals(other.velocities))
            return false;
        return true;
    }
}

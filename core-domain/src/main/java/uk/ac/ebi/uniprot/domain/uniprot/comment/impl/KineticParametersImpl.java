package uk.ac.ebi.uniprot.domain.uniprot.comment.impl;

import uk.ac.ebi.uniprot.domain.uniprot.comment.KineticParameters;
import uk.ac.ebi.uniprot.domain.uniprot.comment.MaximumVelocity;
import uk.ac.ebi.uniprot.domain.uniprot.comment.MichaelisConstant;
import uk.ac.ebi.uniprot.domain.uniprot.comment.Note;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        KineticParametersImpl that = (KineticParametersImpl) o;
        return Objects.equals(maximumVelocities, that.maximumVelocities) &&
                Objects.equals(michaelisConstants, that.michaelisConstants) &&
                Objects.equals(note, that.note);
    }

    @Override
    public int hashCode() {
        return Objects.hash(maximumVelocities, michaelisConstants, note);
    }
}

package uk.ac.ebi.uniprot.domain.uniprot.comment.impl;

import uk.ac.ebi.uniprot.domain.uniprot.comment.KineticParameters;
import uk.ac.ebi.uniprot.domain.uniprot.comment.MaximumVelocity;
import uk.ac.ebi.uniprot.domain.uniprot.comment.MichaelisConstant;
import uk.ac.ebi.uniprot.domain.uniprot.comment.Note;
import uk.ac.ebi.uniprot.domain.uniprot.comment.builder.KineticParametersBuilder;

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

    public KineticParametersImpl(KineticParametersBuilder builder) {
        if ((builder.getMaximumVelocities() == null) || builder.getMaximumVelocities().isEmpty()) {
            this.maximumVelocities = Collections.emptyList();
        } else {
            this.maximumVelocities = Collections.unmodifiableList(builder.getMaximumVelocities());
        }
        if ((builder.getMichaelisConstants() == null) || builder.getMichaelisConstants().isEmpty()) {
            this.michaelisConstants = Collections.emptyList();
        } else {
            this.michaelisConstants = Collections.unmodifiableList(builder.getMichaelisConstants());
        }
        this.note = builder.getNote();
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

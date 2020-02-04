package org.uniprot.core.uniprot.comment.impl;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.uniprot.core.uniprot.comment.KineticParameters;
import org.uniprot.core.uniprot.comment.MaximumVelocity;
import org.uniprot.core.uniprot.comment.MichaelisConstant;
import org.uniprot.core.uniprot.comment.Note;
import org.uniprot.core.util.Utils;

public class KineticParametersImpl implements KineticParameters {
    private static final long serialVersionUID = 6933033384155525116L;
    private List<MaximumVelocity> maximumVelocities;
    private List<MichaelisConstant> michaelisConstants;
    private Note note;

    // no arg constructor for JSON deserialization
    KineticParametersImpl() {
        this.maximumVelocities = Collections.emptyList();
        this.michaelisConstants = Collections.emptyList();
    }

    public KineticParametersImpl(
            List<MaximumVelocity> maximumVelocities,
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
    public boolean hasMaximumVelocities() {
        return Utils.notNullNotEmpty(this.maximumVelocities);
    }

    @Override
    public boolean hasMichaelisConstants() {
        return Utils.notNullNotEmpty(this.michaelisConstants);
    }

    @Override
    public boolean hasNote() {
        return this.note != null && this.note.hasTexts();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        KineticParametersImpl that = (KineticParametersImpl) o;
        return Objects.equals(maximumVelocities, that.maximumVelocities)
                && Objects.equals(michaelisConstants, that.michaelisConstants)
                && Objects.equals(note, that.note);
    }

    @Override
    public int hashCode() {
        return Objects.hash(maximumVelocities, michaelisConstants, note);
    }
}

package org.uniprot.core.uniprotkb.comment.impl;

import org.uniprot.core.uniprotkb.comment.KineticParameters;
import org.uniprot.core.uniprotkb.comment.MaximumVelocity;
import org.uniprot.core.uniprotkb.comment.MichaelisConstant;
import org.uniprot.core.uniprotkb.comment.Note;
import org.uniprot.core.util.Utils;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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

    KineticParametersImpl(
            List<MaximumVelocity> maximumVelocities,
            List<MichaelisConstant> michaelisConstants,
            Note note) {
        this.maximumVelocities = Utils.unmodifiableList(maximumVelocities);
        this.michaelisConstants = Utils.unmodifiableList(michaelisConstants);
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(
                michaelisConstants.stream()
                        .map(val -> val.toString())
                        .collect(Collectors.joining("\n")));
        if (hasMaximumVelocities()) {
            if (hasMichaelisConstants()) {
                sb.append("\n");
            }
            sb.append(
                    maximumVelocities.stream()
                            .map(val -> val.toString())
                            .collect(Collectors.joining("\n")));
        }
        if (hasNote()) {
            if (hasMaximumVelocities()) {
                sb.append("\n");
            }

            sb.append("note=");
            sb.append(
                            note.getTexts().stream()
                                    .map(val -> val.getValue())
                                    .collect(Collectors.joining(". ")))
                    .append(";");
        }

        return sb.toString();
    }
}

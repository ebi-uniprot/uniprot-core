package org.uniprot.core.unirule.impl;

import java.util.Objects;

import org.uniprot.core.Range;
import org.uniprot.core.uniprotkb.feature.Ligand;
import org.uniprot.core.uniprotkb.feature.LigandPart;
import org.uniprot.core.unirule.PositionalFeature;

public class PositionalFeatureImpl extends RuleExceptionAnnotationImpl
        implements PositionalFeature {

    private static final long serialVersionUID = -7323690314197824278L;
    private Range position;

    private String pattern;

    private boolean inGroup;

    private String type;

    private Ligand ligand;

    private LigandPart ligandPart;

    private String description;

    PositionalFeatureImpl() {
        super(RuleExceptionAnnotationType.POSITIONAL_FEATURE);
    }

    PositionalFeatureImpl(
            Range position,
            String pattern,
            boolean inGroup,
            Ligand ligand,
            LigandPart ligandPart,
            String description,
            String type) {
        super(RuleExceptionAnnotationType.POSITIONAL_FEATURE);

        if (Objects.isNull(position)) {
            throw new IllegalArgumentException(
                    "position is mandatory parameter for a PositionalFeature entry");
        }

        this.position = position;
        this.pattern = pattern;
        this.inGroup = inGroup;
        this.ligand = ligand;
        this.ligandPart = ligandPart;
        this.description = description;
        this.type = type;
    }

    @Override
    public Range getPosition() {
        return position;
    }

    @Override
    public String getPattern() {
        return pattern;
    }

    @Override
    public boolean isInGroup() {
        return inGroup;
    }

    @Override
    public Ligand getLigand() {
        return ligand;
    }

    @Override
    public LigandPart getLigandPart() {
        return ligandPart;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PositionalFeatureImpl)) return false;
        if (!super.equals(o)) return false;
        PositionalFeatureImpl that = (PositionalFeatureImpl) o;
        return inGroup == that.inGroup
                && Objects.equals(position, that.position)
                && Objects.equals(pattern, that.pattern)
                && Objects.equals(ligand, that.ligand)
                && Objects.equals(ligandPart, that.ligandPart)
                && Objects.equals(description, that.description)
                && Objects.equals(type, that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                super.hashCode(),
                position,
                pattern,
                inGroup,
                ligand,
                ligandPart,
                description,
                type);
    }
}

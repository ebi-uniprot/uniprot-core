package org.uniprot.core.unirule.impl;

import static org.uniprot.core.util.Utils.nullThrowIllegalArgument;

import javax.annotation.Nonnull;

import org.uniprot.core.Range;
import org.uniprot.core.uniprotkb.feature.Ligand;
import org.uniprot.core.uniprotkb.feature.LigandPart;
import org.uniprot.core.unirule.PositionalFeature;
import org.uniprot.core.unirule.RuleExceptionAnnotationBuilder;

public class PositionalFeatureBuilder implements RuleExceptionAnnotationBuilder<PositionalFeature> {
    private Range position;

    private String pattern;

    private boolean inGroup;

    private String type;

    private Ligand ligand;

    private LigandPart ligandPart;

    private String description;

    public PositionalFeatureBuilder(Range position) {
        this.position = position;
    }

    public @Nonnull PositionalFeatureBuilder position(Range position) {
        this.position = position;
        return this;
    }

    public @Nonnull PositionalFeatureBuilder pattern(String pattern) {
        this.pattern = pattern;
        return this;
    }

    public @Nonnull PositionalFeatureBuilder inGroup(boolean inGroup) {
        this.inGroup = inGroup;
        return this;
    }

    public @Nonnull PositionalFeatureBuilder type(String type) {
        this.type = type;
        return this;
    }

    public @Nonnull PositionalFeatureBuilder ligand(Ligand ligand) {
        this.ligand = ligand;
        return this;
    }

    public @Nonnull PositionalFeatureBuilder ligandPart(LigandPart ligandPart) {
        this.ligandPart = ligandPart;
        return this;
    }

    public @Nonnull PositionalFeatureBuilder description(String description) {
        this.description = description;
        return this;
    }

    @Nonnull
    @Override
    public PositionalFeature build() {
        return new PositionalFeatureImpl(
                position, pattern, inGroup, ligand, ligandPart, description, type);
    }

    public static @Nonnull PositionalFeatureBuilder from(@Nonnull PositionalFeature instance) {
        nullThrowIllegalArgument(instance);
        PositionalFeatureBuilder builder = new PositionalFeatureBuilder(instance.getPosition());
        builder.pattern(instance.getPattern())
                .inGroup(instance.isInGroup())
                .ligand(instance.getLigand())
                .ligandPart(instance.getLigandPart())
                .description(instance.getDescription())
                .type(instance.getType());
        return builder;
    }
}

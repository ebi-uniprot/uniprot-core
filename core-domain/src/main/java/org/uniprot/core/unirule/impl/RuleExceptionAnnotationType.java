package org.uniprot.core.unirule.impl;

import org.uniprot.core.util.EnumDisplay;

import javax.annotation.Nonnull;

public enum RuleExceptionAnnotationType implements EnumDisplay {
    ANNOTATION,
    POSITIONAL_FEATURE;

    @Nonnull
    @Override
    public String getName() {
        return name();
    }

    public static @Nonnull RuleExceptionAnnotationType typeOf(@Nonnull String name) {
        return EnumDisplay.typeOf(name, RuleExceptionAnnotationType.class);
    }
}

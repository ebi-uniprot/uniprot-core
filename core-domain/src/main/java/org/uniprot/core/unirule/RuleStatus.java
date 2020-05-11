package org.uniprot.core.unirule;

import org.uniprot.core.util.EnumDisplay;

import javax.annotation.Nonnull;

/** @author sahmad */
public enum RuleStatus implements EnumDisplay {
    APPLY,
    DISUSED,
    TEST;

    @Nonnull
    @Override
    public String getName() {
        return this.name();
    }

    public static @Nonnull RuleStatus typeOf(@Nonnull String name) {
        return EnumDisplay.typeOf(name, RuleStatus.class);
    }
}

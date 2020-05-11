package org.uniprot.core.proteome;

import org.uniprot.core.util.EnumDisplay;

import javax.annotation.Nonnull;

/**
 * @author jluo
 * @date: 15 Apr 2020
 */
public enum GenomeAssemblyLevel implements EnumDisplay {
    FULL("full"),
    PARTIAL("partial");

    private final String name;

    GenomeAssemblyLevel(String name) {
        this.name = name;
    }

    @Override
    public @Nonnull String getName() {
        return name;
    }

    public static @Nonnull GenomeAssemblyLevel fromValue(@Nonnull String levelValue) {
        return EnumDisplay.typeOf(levelValue, GenomeAssemblyLevel.class);
    }
}

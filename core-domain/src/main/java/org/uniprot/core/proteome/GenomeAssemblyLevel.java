package org.uniprot.core.proteome;

import javax.annotation.Nonnull;

import org.uniprot.core.util.EnumDisplay;

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
        for (GenomeAssemblyLevel level : GenomeAssemblyLevel.values()) {
            if (level.getDisplayName().equalsIgnoreCase(levelValue)) return level;
        }
        throw new IllegalArgumentException(
                "The GenomeAssemblyLevel with " + levelValue + " doesn't exist");
    }
}

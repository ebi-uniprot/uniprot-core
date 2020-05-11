package org.uniprot.core.uniprotkb.description;

import org.uniprot.core.util.EnumDisplay;

import javax.annotation.Nonnull;

/**
 * Contains a list of acceptable flag descriptors found within a DE line
 *
 * <p>current valid flags are Precursor, Fragment, Fragments
 */
public enum FlagType implements EnumDisplay {
    PRECURSOR("Precursor"),
    FRAGMENT("Fragment"),
    FRAGMENTS("Fragments"),
    FRAGMENT_PRECURSOR("Fragment,Precursor"),
    FRAGMENTS_PRECURSOR("Fragments,Precursor");

    private String name;

    FlagType(String name) {
        this.name = name;
    }

    public @Nonnull String getName() {
        return name;
    }

    public static @Nonnull FlagType typeOf(@Nonnull String name) {
        return EnumDisplay.typeOf(name, FlagType.class);
    }
}

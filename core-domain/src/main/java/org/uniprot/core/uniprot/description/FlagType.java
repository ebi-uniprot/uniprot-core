package org.uniprot.core.uniprot.description;

import javax.annotation.Nonnull;

import org.uniprot.core.util.EnumDisplay;

/**
 * Contains a list of acceptable flag descriptors found within a DE line
 *
 * <p>current valid flags are Precursor, Fragment, Fragments
 */
public enum FlagType implements EnumDisplay<FlagType> {
    PRECURSOR("Precursor"),
    FRAGMENT("Fragment"),
    FRAGMENTS("Fragments"),
    FRAGMENT_PRECURSOR("Fragment,Precursor"),
    FRAGMENTS_PRECURSOR("Fragments,Precursor");

    private String value;

    FlagType(String value) {
        this.value = value;
    }

    public @Nonnull String getValue() {
        return value;
    }

    @Override
    public @Nonnull String toDisplayName() {
        return value;
    }
}

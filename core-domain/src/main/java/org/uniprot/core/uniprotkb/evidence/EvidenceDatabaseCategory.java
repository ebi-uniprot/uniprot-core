package org.uniprot.core.uniprotkb.evidence;

import org.uniprot.core.util.EnumDisplay;

import javax.annotation.Nonnull;

public enum EvidenceDatabaseCategory implements EnumDisplay {
    I,
    C,
    A;

    public @Nonnull String getName() {
        return name();
    }

    public static @Nonnull EvidenceDatabaseCategory typeOf(@Nonnull String name) {
        return EnumDisplay.typeOf(name, EvidenceDatabaseCategory.class);
    }
}

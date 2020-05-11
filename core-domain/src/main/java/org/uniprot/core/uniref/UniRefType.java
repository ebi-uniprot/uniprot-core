package org.uniprot.core.uniref;

import javax.annotation.Nonnull;

import org.uniprot.core.util.EnumDisplay;

public enum UniRefType implements EnumDisplay {
    UniRef100("1.0"),
    UniRef90("0.9"),
    UniRef50("0.5");

    private String identity;

    UniRefType(String identity) {
        this.identity = identity;
    }

    public @Nonnull String getDisplayName() {
        return name();
    }

    public @Nonnull String getIdentity() {
        return this.identity;
    }

    public @Nonnull String getName() {
        return identity;
    }

    public static @Nonnull UniRefType typeOf(@Nonnull String name) {
        return EnumDisplay.typeOf(name, UniRefType.class);
    }
}

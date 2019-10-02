package org.uniprot.core.uniref;

import javax.annotation.Nonnull;

import org.uniprot.core.util.EnumDisplay;

public enum UniRefType implements EnumDisplay<UniRefType> {
    UniRef100("1.0"),
    UniRef90("0.9"),
    UniRef50("0.5");
    private String identity;

    UniRefType(String identity) {
        this.identity = identity;
    }

    @Override
    public @Nonnull String toDisplayName() {
        return name();
    }

    public @Nonnull String getIdentity() {
        return this.identity;
    }
}

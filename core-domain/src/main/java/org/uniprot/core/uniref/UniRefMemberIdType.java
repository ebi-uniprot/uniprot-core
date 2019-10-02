package org.uniprot.core.uniref;

import org.uniprot.core.util.EnumDisplay;

import javax.annotation.Nonnull;

public enum UniRefMemberIdType implements EnumDisplay<UniRefMemberIdType> {
    UNIPROTKB("UniProtKB ID"),
    UNIPARC("UniParc ID");
    private String displayName;

    UniRefMemberIdType(String displayName) {
        this.displayName = displayName;
    }

    public @Nonnull static UniRefMemberIdType typeOf(@Nonnull String name) {
        for (UniRefMemberIdType type : UniRefMemberIdType.values()) {
            if (type.toDisplayName().equalsIgnoreCase(name)) {
                return type;
            }
        }
        throw new IllegalArgumentException("the UniRef Member Id Type: " + name + " doesn't exist");
    }

    @Override
    public @Nonnull String toDisplayName() {
        return displayName;
    }
}

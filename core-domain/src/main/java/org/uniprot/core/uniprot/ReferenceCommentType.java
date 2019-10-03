package org.uniprot.core.uniprot;

import org.uniprot.core.util.EnumDisplay;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public enum ReferenceCommentType implements EnumDisplay<ReferenceCommentType> {
    STRAIN("STRAIN"),
    PLASMID("PLASMID"),
    TRANSPOSON("TRANSPOSON"),
    TISSUE("TISSUE");

    private String value;

    ReferenceCommentType(String type) {
        this.value = type;
    }

    public @Nonnull static ReferenceCommentType typeOf(@Nullable String value) {
        for (ReferenceCommentType type : ReferenceCommentType.values()) {
            if (type.getValue().equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("The ReferenceComment type " + value + " doesn't exist");
    }

    public @Nonnull String getValue() {
        return value;
    }

    @Override
    public @Nonnull String toDisplayName() {
        return value;
    }
}

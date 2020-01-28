package org.uniprot.core.citation;

import javax.annotation.Nonnull;

import org.uniprot.core.util.EnumDisplay;

public enum CitationType implements EnumDisplay<CitationType> {
    JOURNAL_ARTICLE("journal article"),
    BOOK("book"),
    ELECTRONIC_ARTICLE("online journal article"),
    PATENT("patent"),
    SUBMISSION("submission", "Unpublished/no plans to publish"),
    THESIS("thesis"),
    LITERATURE("UniProt indexed literatutes"),
    UNPUBLISHED("unpublished observations"),
    UNKNOWN("default as unknown");

    private String value;
    private String displayName;

    CitationType(String value, String displayName) {
        this.value = value;
        this.displayName = displayName;
    }

    CitationType(String type) {
        this(type, type);
    }

    public static @Nonnull CitationType typeOf(@Nonnull String value) {
        for (CitationType citationType : CitationType.values()) {

            if (citationType.getValue().equalsIgnoreCase(value)) {
                return citationType;
            }
        }
        throw new IllegalArgumentException(
                "The citation with the description " + value + " doesn't exist");
    }

    public @Nonnull String getValue() {
        return value;
    }

    public @Nonnull String getDisplayName() {
        return displayName;
    }

    @Override
    public @Nonnull String toDisplayName() {
        return value;
    }
}

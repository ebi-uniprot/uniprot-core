package org.uniprot.core.citation;

import javax.annotation.Nonnull;

import org.uniprot.core.util.EnumDisplay;

public enum CitationType implements EnumDisplay {
    JOURNAL_ARTICLE("journal article"),
    BOOK("book"),
    ELECTRONIC_ARTICLE("online journal article"),
    PATENT("patent"),
    SUBMISSION("submission", "Unpublished/no plans to publish"),
    THESIS("thesis"),
    LITERATURE("UniProt indexed literatures"),
    UNPUBLISHED("unpublished observations");

    private String name;
    private String description;

    CitationType(String name, String description) {
        this.name = name;
        this.description = description;
    }

    CitationType(String value) {
        this(value, value);
    }

    public @Nonnull String getDescription() {
        return description;
    }

    public @Nonnull String getName() {
        return name;
    }

    public static @Nonnull CitationType typeOf(@Nonnull String value) {
        return EnumDisplay.typeOf(value, CitationType.class);
    }
}

package org.uniprot.core.citation;

import javax.annotation.Nonnull;

import org.uniprot.core.util.EnumDisplay;

public enum CitationType implements EnumDisplay {
    JOURNAL_ARTICLE("JNL", "journal article"),
    BOOK("BOK", "book"),
    ELECTRONIC_ARTICLE("EAJ", "online journal article"),
    PATENT("PAT", "patent"),
    SUBMISSION("SUB", "submission", "Unpublished/no plans to publish"),
    THESIS("THE", "thesis"),
    LITERATURE("LIT", "UniProt indexed literatures"),
    UNPUBLISHED("UNP", "unpublished observations");

    private final String name;
    private final String prefix;
    private final String description;

    CitationType(String prefix, String name, String description) {
        this.prefix = prefix;
        this.name = name;
        this.description = description;
    }

    CitationType(String prefix, String value) {
        this(prefix, value, value);
    }

    public @Nonnull String getDescription() {
        return description;
    }

    public @Nonnull String getName() {
        return name;
    }

    public @Nonnull String getPrefix() {
        return prefix;
    }

    public static @Nonnull CitationType typeOf(@Nonnull String value) {
        return EnumDisplay.typeOf(value, CitationType.class);
    }
}

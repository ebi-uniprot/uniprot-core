package org.uniprot.core.citation;

import javax.annotation.Nonnull;

import org.uniprot.core.Database;
import org.uniprot.core.util.EnumDisplay;

public enum CitationDatabase implements Database, EnumDisplay<CitationDatabase> {
    PUBMED("PubMed"),
    DOI("DOI"),
    AGRICOLA("AGRICOLA");

    private String value;

    CitationDatabase(String type) {
        this.value = type;
    }

    public static @Nonnull CitationDatabase typeOf(@Nonnull String value) {
        for (CitationDatabase citXrefType : CitationDatabase.values()) {
            if (citXrefType.getName().equalsIgnoreCase(value)) {
                return citXrefType;
            }
        }
        throw new IllegalArgumentException(
                "the citation cross-reference type " + value + " doesn't exist");
    }

    public @Nonnull String getName() {
        return value;
    }

    @Override
    public @Nonnull String toDisplayName() {
        return getName();
    }
}

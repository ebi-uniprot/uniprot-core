package org.uniprot.core.citation;

import javax.annotation.Nonnull;

import org.uniprot.core.DatabaseType;
import org.uniprot.core.util.EnumDisplay;

public enum CitationXrefType implements DatabaseType, EnumDisplay<CitationXrefType> {
    PUBMED("PubMed"),
    DOI("DOI"),
    AGRICOLA("AGRICOLA");

    private String value;

    CitationXrefType(String type) {
        this.value = type;
    }

    public static @Nonnull CitationXrefType typeOf(@Nonnull String value) {
        for (CitationXrefType citXrefType : CitationXrefType.values()) {
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

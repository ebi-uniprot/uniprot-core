package org.uniprot.core.citation;

import org.uniprot.core.Database;
import org.uniprot.core.util.EnumDisplay;

import javax.annotation.Nonnull;

public enum CitationDatabase implements Database, EnumDisplay {
    PUBMED("PubMed"),
    DOI("DOI"),
    AGRICOLA("AGRICOLA");

    private String name;

    CitationDatabase(String name) {
        this.name = name;
    }

    public @Nonnull String getName() {
        return name;
    }

    public static @Nonnull CitationDatabase typeOf(@Nonnull String name) {
        return EnumDisplay.typeOf(name, CitationDatabase.class);
    }
}

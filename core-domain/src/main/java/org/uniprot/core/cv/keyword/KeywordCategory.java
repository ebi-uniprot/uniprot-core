package org.uniprot.core.cv.keyword;

import org.uniprot.core.util.EnumDisplay;

import javax.annotation.Nonnull;

public enum KeywordCategory implements EnumDisplay, KeywordId {
    BIOLOGICAL_PROCESS("Biological process", "KW-9999"),
    CELLULAR_COMPONENT("Cellular component", "KW-9998"),
    CODING_SEQUENCE_DIVERSITY("Coding sequence diversity", "KW-9997"),
    DEVELOPMENTAL_STAGE("Developmental stage", "KW-9996"),
    DISEASE("DiseaseEntry", "KW-9995"),
    DOMAIN("Domain", "KW-9994"),
    LIGAND("Ligand", "KW-9993"),
    MOLECULAR_FUNCTION("Molecular function", "KW-9992"),
    PTM("PTM", "KW-9991"),
    TECHNICAL_TERM("Technical term", "KW-9990"),
    UNKNOWN("Unknown", "KW-0000");

    private final String name;
    private final String id;

    KeywordCategory(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public @Nonnull String getName() {
        return name;
    }

    public @Nonnull String getId() {
        return id;
    }

    public static @Nonnull KeywordCategory typeOf(@Nonnull String name) {
        return EnumDisplay.typeOf(name, KeywordCategory.class, KeywordCategory.UNKNOWN);
    }
}

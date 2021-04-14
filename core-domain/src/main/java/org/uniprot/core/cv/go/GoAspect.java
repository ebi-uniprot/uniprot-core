package org.uniprot.core.cv.go;

import javax.annotation.Nonnull;

import org.uniprot.core.util.EnumDisplay;

public enum GoAspect implements EnumDisplay {
    FUNCTION("F", "GO Molecular Function"),
    PROCESS("P", "GO Biological Process"),
    COMPONENT("C", "GO Cellular Component");
    private final String code;
    private final String name;

    GoAspect(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public @Nonnull String getCode() {
        return code;
    }

    public @Nonnull String getName() {
        return name;
    }

    public static @Nonnull GoAspect typeOf(@Nonnull String name) {
        for (GoAspect aspect : GoAspect.values()) {
            if (aspect.getCode().equals(name)) {
                return aspect;
            }
        }
        return EnumDisplay.typeOf(name, GoAspect.class);
    }
}

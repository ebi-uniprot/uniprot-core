package org.uniprot.core.cv.go;

import javax.annotation.Nonnull;

import org.uniprot.core.util.EnumDisplay;

public enum GoAspect implements EnumDisplay {
    FUNCTION("GO Molecular Function"),
    PROCESS("GO Biological Process"),
    COMPONENT("GO Cellular Component");
    private final String name;

    GoAspect(String name) {
        this.name = name;
    }

    public @Nonnull String getName() {
        return name;
    }

    public static @Nonnull GoAspect typeOf(@Nonnull String name) {
        if (name.equals("F")) {
            return GoAspect.FUNCTION;
        } else if (name.equals("P")) {
            return GoAspect.PROCESS;
        } else if (name.equals("C")) {
            return GoAspect.COMPONENT;
        } else return EnumDisplay.typeOf(name, GoAspect.class);
    }
}

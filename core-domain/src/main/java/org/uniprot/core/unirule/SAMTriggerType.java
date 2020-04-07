package org.uniprot.core.unirule;

import javax.annotation.Nonnull;

import org.uniprot.core.util.EnumDisplay;

/** @author sahmad */
public enum SAMTriggerType implements EnumDisplay {
    TRANSMEMBRANE("transmembrane"),
    SIGNAL("signal"),
    COILED_COIL("coiledCoil");

    String name;

    SAMTriggerType(String name) {
        this.name = name;
    }

    @Nonnull
    @Override
    public String getName() {
        return name;
    }

    public static @Nonnull SAMTriggerType typeOf(@Nonnull String name) {
        return EnumDisplay.typeOf(name, SAMTriggerType.class);
    }
}

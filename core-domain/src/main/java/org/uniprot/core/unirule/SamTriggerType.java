package org.uniprot.core.unirule;

import org.uniprot.core.util.EnumDisplay;

import javax.annotation.Nonnull;

/** @author sahmad */
public enum SamTriggerType implements EnumDisplay {
    TRANSMEMBRANE("transmembrane"),
    SIGNAL("signal"),
    COILED_COIL("coiledCoil");

    String name;

    SamTriggerType(String name) {
        this.name = name;
    }

    @Nonnull
    @Override
    public String getName() {
        return name;
    }

    public static @Nonnull SamTriggerType typeOf(@Nonnull String name) {
        return EnumDisplay.typeOf(name, SamTriggerType.class);
    }
}

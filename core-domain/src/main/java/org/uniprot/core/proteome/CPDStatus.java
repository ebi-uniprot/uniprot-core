package org.uniprot.core.proteome;

import javax.annotation.Nonnull;

import org.uniprot.core.util.EnumDisplay;

/**
 * @author lgonzales
 * @since 14/04/2020
 */
public enum CPDStatus implements EnumDisplay {
    CLOSE_TO_STANDARD(1, "Close to Standard"),
    STANDARD(2, "Standard"),
    OUTLIER(3, "Outlier"),
    UNKNOWN(4, "Unknown");

    private final int id;
    private final String displayName;

    CPDStatus(int id, String displayName) {
        this.id = id;
        this.displayName = displayName;
    }

    public int getId() {
        return id;
    }

    public static @Nonnull CPDStatus fromValue(@Nonnull String type) {
        return EnumDisplay.typeOf(type, CPDStatus.class, CPDStatus.UNKNOWN);
    }

    @Nonnull
    @Override
    public String getName() {
        return displayName;
    }
}

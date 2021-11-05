package org.uniprot.core.proteome;

import javax.annotation.Nonnull;

import org.uniprot.core.util.EnumDisplay;

/**
 * @author lgonzales
 * @since 14/04/2020
 */
public enum CPDStatus implements EnumDisplay {
    STANDARD(1, "Standard"),
    CLOSE_TO_STANDARD_HIGH(2, "Close to standard (high value)"),
    CLOSE_TO_STANDARD_LOW(3, "Close to standard (low value)"),
    OUTLIER_HIGH(4, "Outlier (high value)"),
    OUTLIER_LOW(5, "Outlier (low value)"),
    UNKNOWN(6, "Unknown");

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

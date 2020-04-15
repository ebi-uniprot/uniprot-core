package org.uniprot.core.proteome;

import org.uniprot.core.util.EnumDisplay;

import javax.annotation.Nonnull;

/**
 * @author lgonzales
 * @since 14/04/2020
 */
public enum CPDStatus implements EnumDisplay<CPDStatus> {

    CLOSE_TO_STANDARD(1, "Close to Standard"),
    STANDARD(2, "Standard"),
    OUTLIER (3, "Outlier"),
    UNKNOWN (4, "Unknown");

    private final int id;
    private final String displayName;

    CPDStatus(int id, String displayName){
        this.id = id;
        this.displayName = displayName;
    }
    public int getId() {
        return id;
    }

    @Override
    public String toDisplayName() {
        return displayName;
    }

    public static @Nonnull CPDStatus fromValue(@Nonnull String type) {
        for (CPDStatus status : CPDStatus.values()) {
            if (status.toDisplayName().equalsIgnoreCase(type)) return status;
        }
        return CPDStatus.UNKNOWN;
    }
}

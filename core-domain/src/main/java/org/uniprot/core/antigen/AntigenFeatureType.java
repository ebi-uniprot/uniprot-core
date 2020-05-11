package org.uniprot.core.antigen;

import javax.annotation.Nonnull;

import org.uniprot.core.feature.FeatureType;
import org.uniprot.core.util.EnumDisplay;

/**
 * @author lgonzales
 * @since 06/05/2020
 */
public enum AntigenFeatureType implements FeatureType {
    ANTIGEN("antigen", "Antigen");

    private final String value;
    private final String displayName;

    AntigenFeatureType(String value, String displayName) {
        this.value = value;
        this.displayName = displayName;
    }

    public @Nonnull String getValue() {
        return value;
    }

    public @Nonnull String getName() {
        return name();
    }

    @Override
    public @Nonnull String getDisplayName() {
        return displayName;
    }

    @Override
    public @Nonnull String getCompareOn() {
        return value;
    }

    public static @Nonnull AntigenFeatureType typeOf(@Nonnull String name) {
        return EnumDisplay.typeOf(name, AntigenFeatureType.class);
    }
}

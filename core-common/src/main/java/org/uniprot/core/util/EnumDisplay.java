package org.uniprot.core.util;

import static org.uniprot.core.util.Utils.nullThrowIllegalArgument;

import java.util.Arrays;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public interface EnumDisplay {

    /**
     * Returns readable name of the enum
     *
     * @return the name of the enum
     */
    @Nonnull
    String getName();

    /**
     * Returns the string value of the enum to compare. This method can be used to find enum by the
     * string value.
     *
     * @return the value of the enum
     */
    default @Nonnull String getCompareOn() {
        return getName();
    }

    /**
     * Returns the name enum constant, as contained in the declaration. This method may be
     * overridden, though it typically isn't necessary or desirable. An enum type should override
     * this method when a more "programmer-friendly" string form exists.
     *
     * <p>Note this method will be used to create json object value for enum type
     *
     * @return the name of this enum constant
     */
    default @Nonnull String getDisplayName() {
        return getName();
    }

    /**
     * Returns the Enum constant by its name. String value of name is trimmed and case is ignored
     * while finding the corresponding enum constant.
     *
     * @param name string value of the enum to be returned.
     * @param enumClass the Class object of the enum type
     * @return Enum constant for the given name and enum type
     * @throws IllegalArgumentException if either name or enumClass is null. or name doesn't have
     *     corresponding enum
     */
    static <T extends Enum<T> & EnumDisplay> T typeOf(
            @Nonnull String name, @Nonnull Class<T> enumClass) {
        nullThrowIllegalArgument(name);
        nullThrowIllegalArgument(enumClass);

        RuntimeException exception =
                new IllegalArgumentException(
                        "The " + enumClass.getSimpleName() + " with " + name + " doesn't exist");

        return Arrays.stream(enumClass.getEnumConstants())
                .filter(constant -> constant.getCompareOn().equalsIgnoreCase(name.trim()))
                .findAny()
                .orElseThrow(() -> exception);
    }

    /**
     * Returns the Enum constant by its name or given defaultValue in case of no match.
     *
     * @param name string value of the enum to be returned.
     * @param enumClass the Class object of the enum type
     * @param defaultValue the default enum constant in case of no enum constant by given name and
     *     type
     * @return Enum constant for the given name and enum type Or defaultValue in case of i. no
     *     match, ii. name is null or iii. enumClass is null
     */
    static <T extends Enum<T> & EnumDisplay> T typeOf(
            @Nonnull String name, @Nonnull Class<T> enumClass, @Nullable T defaultValue) {
        try {
            return typeOf(name, enumClass);
        } catch (IllegalArgumentException ile) {
            return defaultValue;
        }
    }
}

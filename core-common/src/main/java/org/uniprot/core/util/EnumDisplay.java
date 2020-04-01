package org.uniprot.core.util;

import static org.uniprot.core.util.Utils.nullThrowIllegalArgument;

import javax.annotation.Nonnull;

public interface EnumDisplay {

    @Nonnull
    String getName();

    /**
     * Return value will be used to compare and get instance of enum
     *
     * @return string
     */
    default @Nonnull String getCompareOn() {
        return getName();
    }

    /**
     * Returns the name enum constant, as contained in the declaration. This method may be
     * overridden, though it typically isn't necessary or desirable. An enum type should override
     * this method when a more "programmer-friendly" string form exists.
     *
     * @return the name of this enum constant
     */
    default @Nonnull String getDisplayName() {
        return getName();
    }

    /**
     * ignore case and trim on passed string argument to match
     *
     * @throws IllegalArgumentException for null or not matching argument
     */
    static <T extends Enum<T> & EnumDisplay> T typeOf(@Nonnull String name, Class<T> enumClass) {
        nullThrowIllegalArgument(name);

        for (T constant : enumClass.getEnumConstants()) {
            if (constant.getCompareOn().equalsIgnoreCase(name.trim())) return constant;
        }
        throw new IllegalArgumentException(
                "The " + enumClass.getSimpleName() + " with " + name + " doesn't exist");
    }

    /**
     * ignore case and trim on passed string argument to match
     *
     * @return matching value or defaultValue in case of no match
     */
    static <T extends Enum<T> & EnumDisplay> T typeOf(@Nonnull String name, T defaultValue) {
        try {
            return typeOf(name, defaultValue.getDeclaringClass());
        } catch (IllegalArgumentException ignore) {
            return defaultValue;
        }
    }
}

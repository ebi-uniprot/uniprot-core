package org.uniprot.core.uniprot.description;

import org.uniprot.core.util.EnumDisplay;

/**
 * Contains a list of acceptable flag descriptors found within a DE line
 * <p>
 * current valid flags are Precursor, Fragment, Fragments
 */
public enum FlagType implements EnumDisplay<FlagType> {
    PRECURSOR("Precursor"),
    FRAGMENT("Fragment"),
    FRAGMENTS("Fragments"),
    FRAGMENT_PRECURSOR("Fragment,Precursor"),
    FRAGMENTS_PRECURSOR("Fragments,Precursor");


    private String value;

    FlagType(String value) {
        this.value = value;
    }

    public static FlagType typeOf(String value) {
        for (FlagType flagType : FlagType.values()) {
            if (flagType.getValue().equalsIgnoreCase(value)) {
                return flagType;
            }
        }
        throw new IllegalArgumentException(String.format("The Flagtype with value %s does not exist", value));
    }

    public static String[] displayValues() {
        String[] displayValues = new String[FlagType.values().length];

        int count = 0;

        for (FlagType flagType : FlagType.values()) {
            displayValues[count] = flagType.getValue();
            count++;
        }

        return displayValues;
    }

    /**
     * Check whether a value would be a valid value to be turned into FlagType
     *
     * @param value
     * @return
     */
    public static boolean hasA(String value) {

        for (FlagType flagType : FlagType.values()) {
            if (flagType.getValue().equalsIgnoreCase(value)) {
                return true;
            }
        }
        return false;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toDisplayName() {
        return value;
    }
}

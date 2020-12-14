package org.uniprot.core.publication;

/**
 * Created 14/12/2020
 *
 * @author Edd
 */
public enum MappedReferenceType {
    COMMUNITY(0),
    COMPUTATIONAL(1),
    UNIPROTKB_REVIEWED(2),
    UNIPROTKB_UNREVIEWED(3);

    private static final MappedReferenceType[] TYPE_ARR;

    static {
        TYPE_ARR = new MappedReferenceType[4];
        TYPE_ARR[0] = COMMUNITY;
        TYPE_ARR[1] = COMPUTATIONAL;
        TYPE_ARR[2] = UNIPROTKB_REVIEWED;
        TYPE_ARR[3] = UNIPROTKB_UNREVIEWED;
    }

    private final int intValue;

    MappedReferenceType(int intValue) {
        this.intValue = intValue;
    }

    public int getIntValue() {
        return intValue;
    }

    public static MappedReferenceType getType(int intValue) {
        if (intValue >= 0 && intValue < TYPE_ARR.length) {
            return TYPE_ARR[intValue];
        }
        throw new IllegalArgumentException(
                "Integer value does not correspond to any MappedReferenceType: " + intValue);
    }
}

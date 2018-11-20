package uk.ac.ebi.uniprot.domain.uniprot.comment;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * User: Emilio Salazar
 * Date: 14-May-2007
 */
public enum SequenceCautionType {

    FRAMESHIFT("Frameshift"),
    ERRONEOUS_INITIATION("Erroneous initiation"),
    ERRONEOUS_TERMIINATION("Erroneous termination"),
    ERRONEOUS_PREDICTION("Erroneous gene model prediction"),
    ERRONEOUS_TRANSLATION("Erroneous translation"),
    MISCELLANEOUS_DISCREPANCY("Miscellaneous discrepancy"),
    UNKNOWN ("unknown");


    private String value;

    SequenceCautionType(String value) {
        this.value = value;
    }

    /**
     * Returns the name of this enum constant, as contained in the
     * declaration.  This method may be overridden, though it typically
     * isn't necessary or desirable.  An enum type should override this
     * method when a more "programmer-friendly" string form exists.
     *
     * @return the name of this enum constant
     */
    @JsonValue
    public String toDisplayName() {
        return value;
    }



    public static SequenceCautionType typeOf(String value) {
        for (SequenceCautionType sequenceCautionType : SequenceCautionType.values()) {
            if (sequenceCautionType.toDisplayName().trim().equalsIgnoreCase(value.trim())) {
                return sequenceCautionType;
            }
        }

        throw new IllegalArgumentException("The comment type: " + value + " doesn't exist");
    }
}

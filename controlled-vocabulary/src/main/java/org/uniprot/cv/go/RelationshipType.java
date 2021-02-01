package org.uniprot.cv.go;

/**
 * Created 25/11/2020
 *
 * @author Edd
 */
public enum RelationshipType {
    IS_A("is_a"),
    PART_OF("part_of");
    private final String displayName;

    RelationshipType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}

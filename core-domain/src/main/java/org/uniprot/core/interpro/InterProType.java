package org.uniprot.core.interpro;

import javax.annotation.Nonnull;

import org.uniprot.core.util.EnumDisplay;

/** @author jluo */
public enum InterProType implements EnumDisplay {
    ACTIVE_SITE("Active site", "A"),
    BINDING_SITE("Binding site", "B"),
    CONSERVED_SITE("Conserved site", "C"),
    DOMAIN("Domain", "D"),
    FAMILY("Family", "F"),
    PTM("PTM", "P"),
    REPEAT("Repeat", "R"),
    UNKNOWN("UNKNOWN", "U");

    private String name;
    private String dbcode;

    private InterProType(String type, String dbCode) {
        this.name = type;
        this.dbcode = dbCode;
    }

    @Override
    public String getName() {
        return name;
    }

    public String getDbcode() {
        return dbcode;
    }

    public static @Nonnull InterProType typeOfDbCode(@Nonnull String dbcode) {
        for (InterProType type : InterProType.values()) {
            if (type.getDbcode().equals(dbcode)) {
                return type;
            }
        }
        // this fixes backward compatabilty issues with the unknown type code from prior
        // releases of interpro
        if ("na".equals(dbcode)) return UNKNOWN;
        throw new IllegalArgumentException(
                "the InterProType with the dbcode " + dbcode + " doesn't exist");
    }

    public static @Nonnull InterProType typeOf(@Nonnull String name) {
        return EnumDisplay.typeOf(name, InterProType.class);
    }
}

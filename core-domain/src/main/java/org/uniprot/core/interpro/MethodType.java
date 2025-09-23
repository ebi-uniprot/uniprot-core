package org.uniprot.core.interpro;

import javax.annotation.Nonnull;

import org.uniprot.core.util.EnumDisplay;

/** @author jluo */
public enum MethodType implements EnumDisplay {
    GENE3D("GENE3D", "X"),
    MOBIDB_LITE("MOBIDBLT", "g"),
    PANTHER("PANTHER", "V"),
    PFAM("PFAM", "H"),
    PRINTS("PRINTS", "F"),
    PIR("PIR", "PIR"),
    PIRSF("PIRSF", "U"),
    PREFILE("PREFILE", ""),
    PRODOM("PRODOM", "D"),
    PROFILE("PROFILE", "M"),
    PROSITE("PROSITE", "P"),
    SMART("SMART", "R"),
    SSF("SSF", "Y"),
    NCBIFAM("NCBIfam", "N"),
    HAMAP("HAMAP", "Q"),
    CDD("CDD", "J"),
    SFLD("SFLD", "B"),
    COILS("COILS", "x"),
    FUNFAM("FunFam", "f"),
    PIRSR("PIRSR", "p"),
    UNKNOWN("UNKNOWN", "na");

    private String name;
    private String dbcode;

    private MethodType(String type, String dbCode) {
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

    public static @Nonnull MethodType typeOfDbCode(@Nonnull String dbcode) {
        for (MethodType type : MethodType.values()) {
            if (type.getDbcode().equals(dbcode)) {
                return type;
            }
        }
        throw new IllegalArgumentException(
                "the MethodType with the dbcode " + dbcode + " doesn't exist");
    }

    public static @Nonnull MethodType typeOf(@Nonnull String name) {
        return EnumDisplay.typeOf(name, MethodType.class);
    }
}

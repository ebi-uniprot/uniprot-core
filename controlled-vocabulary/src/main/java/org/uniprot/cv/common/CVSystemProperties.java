package org.uniprot.cv.common;

import javax.annotation.Nullable;

public class CVSystemProperties {
    public static final String GAF_ECO_LOCATION = "cv.gaf.eco.location";
    public static final String DR_ORD_LOCATION = "cv.dr.ord.location";
    public static final String DR_DATABASE_TYPES_LOCATION = "cv.dr.db.types.location";

    public static @Nullable String getGafEcoLocation() {
        return System.getProperty(GAF_ECO_LOCATION);
    }

    public static @Nullable String getDrOrdLocation() {
        return System.getProperty(DR_ORD_LOCATION);
    }

    public static @Nullable String getDrDatabaseTypesLocation() {
        return System.getProperty(DR_DATABASE_TYPES_LOCATION);
    }
}

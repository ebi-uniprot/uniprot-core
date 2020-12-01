package org.uniprot.cv.common;

import static org.uniprot.core.util.Utils.notNull;
import static org.uniprot.cv.common.AbstractFileReader.isUrl;

import javax.annotation.Nullable;

public class CVSystemProperties {
    public static final String GO_LOCATION = "cv.go.location";
    public static final String GAF_ECO_LOCATION = "cv.gaf.eco.location";
    public static final String DR_ORD_LOCATION = "cv.dr.ord.location";
    public static final String DR_DATABASE_TYPES_LOCATION = "cv.dr.db.types.location";
    public static final String DATABASE_TYPES_LOCATION = "cv.db.types.location";

    public static @Nullable String getGOLocation() {
        return System.getProperty(GO_LOCATION);
    }

    public static @Nullable String getGafEcoLocation() {
        return System.getProperty(GAF_ECO_LOCATION);
    }

    public static @Nullable String getDrOrdLocation() {
        return System.getProperty(DR_ORD_LOCATION);
    }

    public static @Nullable String getDrDatabaseTypesLocation() {
        return System.getProperty(DR_DATABASE_TYPES_LOCATION);
    }

    public static @Nullable String getDatabaseTypesLocation() {
        String property = System.getProperty(DATABASE_TYPES_LOCATION);
        if (notNull(property) && !isUrl(property)) {
            throw new CacheFileLocationException("Code only support web url");
        }
        return property;
    }
}

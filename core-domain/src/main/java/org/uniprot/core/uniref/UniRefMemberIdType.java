package org.uniprot.core.uniref;

import java.util.Arrays;
import java.util.HashMap;

import javax.annotation.Nonnull;

import org.uniprot.core.util.EnumDisplay;
import org.uniprot.core.util.Utils;

public enum UniRefMemberIdType implements EnumDisplay {
    UNIPROTKB_SWISSPROT("Reviewed (Swiss-Prot)", null, 0),
    UNIPROTKB_TREMBL("Unreviewed (TrEMBL)", null, 1),

    // represents either Swiss-Prot or TrEMBL entities
    UNIPROTKB("UniProtKB ID", "UniProtKB ID", 2),

    UNIPARC("UniParc", "UniParc ID", 3);

    private static final HashMap<String, UniRefMemberIdType> nameTypeMap = new HashMap<>();

    static {
        Arrays.stream(UniRefMemberIdType.values())
                .forEach(
                        type -> {
                            nameTypeMap.put(type.displayName.toUpperCase(), type);
                            if (type.xmlName != null) {
                                nameTypeMap.put(type.xmlName.toUpperCase(), type);
                            }
                        });
    }

    private final String displayName;
    private final int displayOrder;
    private final String xmlName;

    UniRefMemberIdType(String displayName, String xmlName, int displayOrder) {
        this.displayName = displayName;
        this.xmlName = xmlName;
        this.displayOrder = displayOrder;
    }

    public @Nonnull String getName() {
        return displayName;
    }

    public int getDisplayOrder() {
        return displayOrder;
    }

    public static @Nonnull UniRefMemberIdType typeOf(@Nonnull String name) {
        RuntimeException ex =
                new IllegalArgumentException(
                        "The "
                                + UniRefMemberIdType.class.getSimpleName()
                                + " with "
                                + name
                                + " doesn't exist");

        if (Utils.nullOrEmpty(name)) {
            throw ex;
        } else {
            UniRefMemberIdType uniRefMemberIdType = nameTypeMap.get(name.toUpperCase());
            if (uniRefMemberIdType == null) {
                throw ex;
            } else {
                return uniRefMemberIdType;
            }
        }
    }
}

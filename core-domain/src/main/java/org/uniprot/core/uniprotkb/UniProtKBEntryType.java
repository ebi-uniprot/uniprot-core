package org.uniprot.core.uniprotkb;

import java.util.Arrays;

import javax.annotation.Nonnull;

import org.uniprot.core.util.EnumDisplay;
import org.uniprot.core.util.Utils;

public enum UniProtKBEntryType implements EnumDisplay {
    SWISSPROT("UniProtKB reviewed (Swiss-Prot)", "Swiss-Prot"),
    TREMBL("UniProtKB unreviewed (TrEMBL)", "TrEMBL"),
    INACTIVE("Inactive"),
    UNKNOWN("UNKNOWN");

    private String name;
    private String xmlName;

    UniProtKBEntryType(String type) {
        this.name = type;
        this.xmlName = type;
    }

    UniProtKBEntryType(String type, String xmlName) {
        this.name = type;
        this.xmlName = xmlName;
    }

    public @Nonnull String getName() {
        return name;
    }

    public static @Nonnull UniProtKBEntryType typeOf(@Nonnull String name) {
        if (Utils.nullOrEmpty(name)) {
            return UNKNOWN;
        }

        return Arrays.stream(UniProtKBEntryType.class.getEnumConstants())
                .filter(
                        constant ->
                                constant.getCompareOn().equalsIgnoreCase(name.trim())
                                        || constant.getXmlName().equalsIgnoreCase(name.trim()))
                .findFirst()
                .orElse(UNKNOWN);
    }

    public @Nonnull String getXmlName() {
        return xmlName;
    }
}

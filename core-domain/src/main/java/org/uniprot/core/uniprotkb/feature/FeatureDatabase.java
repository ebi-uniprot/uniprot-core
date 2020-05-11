package org.uniprot.core.uniprotkb.feature;

import org.uniprot.core.Database;
import org.uniprot.core.util.EnumDisplay;

import javax.annotation.Nonnull;

public enum FeatureDatabase implements Database, EnumDisplay {
    DBSNP("dbSNP");
    private final String name;

    FeatureDatabase(String name) {
        this.name = name;
    }

    public @Nonnull String getName() {
        return name;
    }

    public static @Nonnull FeatureDatabase typeOf(@Nonnull String name) {
        return EnumDisplay.typeOf(name, FeatureDatabase.class);
    }
}

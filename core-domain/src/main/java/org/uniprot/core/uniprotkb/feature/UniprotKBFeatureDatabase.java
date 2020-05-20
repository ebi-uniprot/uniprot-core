package org.uniprot.core.uniprotkb.feature;

import javax.annotation.Nonnull;

import org.uniprot.core.feature.FeatureDatabase;
import org.uniprot.core.util.EnumDisplay;

public enum UniprotKBFeatureDatabase implements FeatureDatabase {
    DBSNP("dbSNP");
    private final String name;

    UniprotKBFeatureDatabase(String name) {
        this.name = name;
    }

    public @Nonnull String getName() {
        return name;
    }

    public static @Nonnull UniprotKBFeatureDatabase typeOf(@Nonnull String name) {
        return EnumDisplay.typeOf(name, UniprotKBFeatureDatabase.class);
    }
}

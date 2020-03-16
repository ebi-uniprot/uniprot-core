package org.uniprot.core.uniprotkb.feature;

import javax.annotation.Nonnull;

import org.uniprot.core.Database;
import org.uniprot.core.util.EnumDisplay;

public enum FeatureDatabase implements Database, EnumDisplay<FeatureDatabase> {
    DBSNP("dbSNP");
    private final String name;

    FeatureDatabase(String name) {
        this.name = name;
    }

    public @Nonnull static FeatureDatabase typeOf(@Nonnull String type) {
        for (FeatureDatabase featuretype : FeatureDatabase.values()) {
            if (type.equalsIgnoreCase(featuretype.getName())) return featuretype;
        }
        throw new IllegalArgumentException(
                type + " is not valid Feature cross reference database type");
    }

    @Override
    public @Nonnull String getName() {
        return name;
    }

    @Override
    public @Nonnull String toDisplayName() {
        return name;
    }
}

package org.uniprot.core.uniprot.feature;

import javax.annotation.Nonnull;

import org.uniprot.core.DatabaseType;
import org.uniprot.core.util.EnumDisplay;

public enum FeatureXDbType implements DatabaseType, EnumDisplay<FeatureXDbType> {
    DBSNP("dbSNP");
    private final String name;

    FeatureXDbType(String name) {
        this.name = name;
    }

    public @Nonnull static FeatureXDbType typeOf(String type) {
        for (FeatureXDbType featuretype : FeatureXDbType.values()) {
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

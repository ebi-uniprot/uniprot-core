package org.uniprot.core.antigen;

import javax.annotation.Nonnull;

import org.uniprot.core.feature.FeatureDatabase;
import org.uniprot.core.util.EnumDisplay;

/**
 * @author lgonzales
 * @since 06/05/2020
 */
public enum AntigenDatabase implements FeatureDatabase {
    ENSEMBL("Ensembl");
    private final String name;

    AntigenDatabase(String name) {
        this.name = name;
    }

    public @Nonnull String getName() {
        return name;
    }

    public static @Nonnull AntigenDatabase typeOf(@Nonnull String name) {
        return EnumDisplay.typeOf(name, AntigenDatabase.class);
    }
}

package uk.ac.ebi.uniprot.domain.uniprot.feature;

import uk.ac.ebi.uniprot.domain.DatabaseType;
import uk.ac.ebi.uniprot.domain.EnumDisplay;

public enum FeatureXDbType implements DatabaseType, EnumDisplay<FeatureXDbType> {
    DBSNP("dbSNP");
    private final String name;

    FeatureXDbType(String name) {
        this.name = name;
    }

    public static FeatureXDbType typeOf(String type) {
        for (FeatureXDbType featuretype : FeatureXDbType.values()) {
            if (type.equals(featuretype.getName()))
                return featuretype;
        }
        throw new IllegalArgumentException(type + " is not valid Feature cross reference database type");
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String toDisplayName() {
        return name;
    }
}

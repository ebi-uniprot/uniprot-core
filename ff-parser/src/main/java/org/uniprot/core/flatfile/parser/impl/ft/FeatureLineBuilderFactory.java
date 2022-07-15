package org.uniprot.core.flatfile.parser.impl.ft;

import java.util.EnumMap;
import java.util.Map;

import org.uniprot.core.flatfile.writer.FFLineBuilder;
import org.uniprot.core.uniprotkb.feature.UniProtKBFeature;
import org.uniprot.core.uniprotkb.feature.UniprotKBFeatureType;

public class FeatureLineBuilderFactory {
    private static Map<UniprotKBFeatureType, FFLineBuilder<UniProtKBFeature>> featureBuilders =
            new EnumMap<>(UniprotKBFeatureType.class);

    static {
        featureBuilders.put(UniprotKBFeatureType.CARBOHYD, new CarbohydFeatureLineBuilder());
        featureBuilders.put(UniprotKBFeatureType.CONFLICT, new ConflictFeatureLineBuilder());
        featureBuilders.put(UniprotKBFeatureType.MUTAGEN, new MutagenFeatureLineBuilder());
        featureBuilders.put(UniprotKBFeatureType.VARIANT, new VariantFeatureLineBuilder());
        featureBuilders.put(UniprotKBFeatureType.VAR_SEQ, new VarSeqFeatureBuilder());
        featureBuilders.put(UniprotKBFeatureType.BINDING, new BindingFeatureLineBuilder());
    };

    private static final FFLineBuilder<UniProtKBFeature> defaultBuilder =
            new SimpleFeatureLineBuilder();

    public static FFLineBuilder<UniProtKBFeature> create(UniProtKBFeature feature) {
        return featureBuilders.getOrDefault(feature.getType(), defaultBuilder);
    }
}

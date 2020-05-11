package org.uniprot.core.flatfile.parser.impl.ft;

import java.util.EnumMap;
import java.util.Map;

import org.uniprot.core.flatfile.writer.FFLineBuilder;
import org.uniprot.core.uniprotkb.feature.Feature;
import org.uniprot.core.uniprotkb.feature.FeatureType;

public class FeatureLineBuilderFactory {
    private static Map<FeatureType, FFLineBuilder<Feature>> featureBuilders =
            new EnumMap<>(FeatureType.class);

    static {
        featureBuilders.put(FeatureType.CARBOHYD, new CarbohydFeatureLineBuilder());
        featureBuilders.put(FeatureType.CONFLICT, new ConflictFeatureLineBuilder());
        featureBuilders.put(FeatureType.MUTAGEN, new MutagenFeatureLineBuilder());
        featureBuilders.put(FeatureType.VARIANT, new VariantFeatureLineBuilder());
        featureBuilders.put(FeatureType.VAR_SEQ, new VarSeqFeatureBuilder());
    };

    private static final FFLineBuilder<Feature> defaultBuilder = new SimpleFeatureLineBuilder();

    public static FFLineBuilder<Feature> create(Feature feature) {
        return featureBuilders.getOrDefault(feature.getType(), defaultBuilder);
    }
}

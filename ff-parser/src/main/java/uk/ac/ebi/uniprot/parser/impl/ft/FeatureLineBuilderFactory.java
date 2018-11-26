package uk.ac.ebi.uniprot.parser.impl.ft;

import java.util.EnumMap;
import java.util.Map;

import uk.ac.ebi.uniprot.domain.uniprot.feature.Feature;
import uk.ac.ebi.uniprot.domain.uniprot.feature.FeatureType;
import uk.ac.ebi.uniprot.parser.ffwriter.FFLineBuilder;


public class FeatureLineBuilderFactory {
	private static Map<FeatureType, FFLineBuilder< Feature> > featureBuilders 
	= new EnumMap<>(FeatureType.class);
	static{
		featureBuilders.put(FeatureType.CARBOHYD,
				new CarbohydFeatureLineBuilder());
		featureBuilders.put(FeatureType.CONFLICT,
				new ConflictFeatureLineBuilder());
		featureBuilders.put(FeatureType.MUTAGEN,
				new MutagenFeatureLineBuilder());
		featureBuilders.put(FeatureType.VARIANT,
				new VariantFeatureLineBuilder());
		featureBuilders.put(FeatureType.VAR_SEQ,
				new VarSeqFeatureBuilder());
		
	};

	private static final FFLineBuilder< Feature> defaultBuilder
	= new SimpleFeatureLineBuilder();
	public static FFLineBuilder< Feature> create(Feature feature){
		return featureBuilders.getOrDefault(feature.getType(), defaultBuilder);
	}
}

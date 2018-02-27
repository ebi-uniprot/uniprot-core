package uk.ac.ebi.uniprot.parser.impl.ft;

import java.util.EnumMap;
import java.util.Map;

import uk.ac.ebi.uniprot.domain.feature.Feature;
import uk.ac.ebi.uniprot.domain.feature.FeatureType;
import uk.ac.ebi.uniprot.domain.uniprot.UniProtFeature;
import uk.ac.ebi.uniprot.ffwriter.line.FFLineBuilder;


public class FeatureLineBuilderFactory {
	private static Map<FeatureType, FFLineBuilder<? extends UniProtFeature<? extends Feature> > > featureBuilders 
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
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static final FFLineBuilder<UniProtFeature<? extends Feature>> defaultBuilder
	= new SimpleFeatureLineBuilder();
	@SuppressWarnings("unchecked")
	public static FFLineBuilder<UniProtFeature<? extends Feature>> create(Feature feature){
		FFLineBuilder<? extends UniProtFeature<? extends Feature> > builder = 
				featureBuilders.get(feature.getType());
		if(builder !=null)
			return (FFLineBuilder<UniProtFeature<? extends Feature>>) builder;
		else
			return defaultBuilder;
	}
}

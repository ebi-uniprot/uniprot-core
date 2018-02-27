package uk.ac.ebi.uniprot.parser.impl.ft;

import static uk.ac.ebi.uniprot.ffwriter.line.FFLineConstant.SPACE;

import uk.ac.ebi.uniprot.domain.feature.CarbohydFeature;
import uk.ac.ebi.uniprot.domain.feature.CarbohydLinkType;
import uk.ac.ebi.uniprot.domain.uniprot.UniProtFeature;
public class CarbohydFeatureLineBuilder 
extends AbstractFeatureLineBuilder<UniProtFeature<CarbohydFeature>> {
	@Override
	protected StringBuilder buildExtra(UniProtFeature<CarbohydFeature> uniprotFeature){
		StringBuilder sb =new StringBuilder();
		CarbohydFeature feature = uniprotFeature.getFeature();
		if (feature.getCarbohydLinkType() != CarbohydLinkType.UNKNOWN) {
		    sb.append(feature.getCarbohydLinkType().getValue());
		    sb.append(SPACE);
		    sb.append(feature.getLinkedSugar().getValue());
		}
		StringBuilder descr =FTLineBuilderHelper.getDescriptionString(feature);

		if((sb.length() >0) &&(descr.length()>0)){
			sb.append("; ");
			sb.append(descr);
		}
	
		return sb;
	}
}

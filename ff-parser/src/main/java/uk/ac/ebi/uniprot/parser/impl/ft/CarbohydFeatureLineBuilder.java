package uk.ac.ebi.uniprot.parser.impl.ft;

import uk.ac.ebi.uniprot.domain.uniprot.feature.Feature;

public class CarbohydFeatureLineBuilder 
extends AbstractFeatureLineBuilder {
	@Override
	protected StringBuilder buildExtra(Feature feature){
//		StringBuilder sb =new StringBuilder();
//
//		if (feature.getCarbohydLinkType() != CarbohydLinkType.UNKNOWN) {
//		    sb.append(feature.getCarbohydLinkType().getValue());
//		    sb.append(SPACE);
//		    sb.append(feature.getLinkedSugar().getValue());
//		}
//		StringBuilder descr =FTLineBuilderHelper.getDescriptionString(feature);
//
//		if((sb.length() >0) &&(descr.length()>0)){
//			sb.append("; ");
//			sb.append(descr);
//		}
//	
//		return sb;
		
		return FTLineBuilderHelper.buildExtra(feature);
	}
}

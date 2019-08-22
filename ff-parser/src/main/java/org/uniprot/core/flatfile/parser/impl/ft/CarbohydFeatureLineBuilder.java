package org.uniprot.core.flatfile.parser.impl.ft;

import org.uniprot.core.uniprot.feature.Feature;

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
package uk.ac.ebi.uniprot.parser.impl.ft;

import java.util.ArrayList;
import java.util.List;

import uk.ac.ebi.uniprot.domain.feature.Feature;
import uk.ac.ebi.uniprot.domain.uniprot.UniProtFeature;
import uk.ac.ebi.uniprot.parser.ffwriter.FFLine;
import uk.ac.ebi.uniprot.parser.ffwriter.FFLineBuilder;
import uk.ac.ebi.uniprot.parser.ffwriter.LineType;
import uk.ac.ebi.uniprot.parser.ffwriter.impl.FFLineBuilderAbstr;
import uk.ac.ebi.uniprot.parser.ffwriter.impl.FFLines;

public class FTLineBuilder extends FFLineBuilderAbstr< List<UniProtFeature<? extends Feature>> > implements
FFLineBuilder< List<UniProtFeature<? extends Feature>> > {	
	public FTLineBuilder(){
		super(LineType.FT);
	}
	@Override
	public String buildString(List<UniProtFeature<? extends Feature>> features) {
		List<String> lines = buildLines(features, false, false);
		return  FFLines.create(lines).toString();
	}
	@Override
	public String buildStringWithEvidence(List<UniProtFeature<? extends Feature>> features) {
		List<String> lines = buildLines(features, false, true);
		return  FFLines.create(lines).toString();
	}
	
	@Override
	protected FFLine buildLine(List<UniProtFeature<? extends Feature>> f, boolean showEvidence) {
		List<String> lines = buildLines(f, true, showEvidence);
		return  FFLines.create(lines);
	}
	
	private List<String> buildLines(List<UniProtFeature<? extends Feature>> features, boolean includeFFMarkings, boolean showEvidence) {
		List<String> lines =new ArrayList<>();
		for(UniProtFeature<? extends Feature> feature:features){
			FFLineBuilder< UniProtFeature<? extends Feature>> fbuilder =FeatureLineBuilderFactory.create(feature.getFeature());
			if(includeFFMarkings){
				if(showEvidence)
					lines.addAll(fbuilder.buildWithEvidence(feature).lines());
				else
					lines.addAll(fbuilder.build(feature).lines());
			}else{
				if(showEvidence)
					lines.add(fbuilder.buildStringWithEvidence(feature));
				else
					lines.add(fbuilder.buildString(feature));
			}
		}
		return lines;
	}
	
}

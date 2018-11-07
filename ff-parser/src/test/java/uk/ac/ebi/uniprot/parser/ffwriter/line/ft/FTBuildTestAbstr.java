package uk.ac.ebi.uniprot.parser.ffwriter.line.ft;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import uk.ac.ebi.uniprot.domain.feature.Feature;
import uk.ac.ebi.uniprot.domain.feature.FeatureLocation;
import uk.ac.ebi.uniprot.domain.feature.FeatureLocationModifier;
import uk.ac.ebi.uniprot.domain.feature.FeatureType;
import uk.ac.ebi.uniprot.domain.feature.HasAlternativeSequence;
import uk.ac.ebi.uniprot.domain.uniprot.UniProtFeature;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.factory.EvidenceFactory;
import uk.ac.ebi.uniprot.domain.uniprot.factory.FeatureFactory;
import uk.ac.ebi.uniprot.domain.uniprot.factory.UniProtFactory;
import uk.ac.ebi.uniprot.parser.ffwriter.FFLine;
import uk.ac.ebi.uniprot.parser.ffwriter.FFLineBuilder;
import uk.ac.ebi.uniprot.parser.impl.ft.FeatureLineBuilderFactory;



public class FTBuildTestAbstr {
	protected final FeatureFactory factory = FeatureFactory.INSTANCE;

	void doTest(String ftLine, UniProtFeature<? extends Feature> uniprotfeature) {
		Feature feature =uniprotfeature.getFeature();
		FFLineBuilder< UniProtFeature<? extends Feature>>   builder = FeatureLineBuilderFactory
				.create(feature);

		FFLine ffLine = builder.buildWithEvidence(uniprotfeature);
		String resultString = ffLine.toString();
		System.out.println(resultString);
		System.out.println(ftLine);
		assertEquals(ftLine, resultString);
	}

	 void doTestString(String ftLine, UniProtFeature<? extends Feature> uniprotfeature) {
			Feature feature =uniprotfeature.getFeature();
		 FFLineBuilder< UniProtFeature<? extends Feature>>  builder = FeatureLineBuilderFactory
				.create(feature);
		String value = builder.buildString(uniprotfeature);
		System.out.println(value);
		assertEquals(ftLine, value);
	}

	<T extends Feature> void doTestStringEv(String ccLine, UniProtFeature<? extends Feature> uniprotfeature) {
		Feature feature =uniprotfeature.getFeature();
		 FFLineBuilder< UniProtFeature<? extends Feature>>  builder = FeatureLineBuilderFactory
				.create(feature);
		String value = builder.buildStringWithEvidence(uniprotfeature);

		System.out.println(value);
		assertEquals(ccLine, value);
	}

	UniProtFeature<? extends Feature> createFeature(FeatureType type, int nstart, int nend,
			String description, String ftId,
			List<String> evs) {
		return createFeature(type, nstart, nend, FeatureLocationModifier.EXACT,
				FeatureLocationModifier.EXACT, description, ftId, evs);
	}

	UniProtFeature<? extends Feature> createFeature(FeatureType type, int nstart, int nend,
			FeatureLocationModifier sfModifier,
			FeatureLocationModifier efModifier, String description,
			String ftId, List<String> evs) {
		FeatureLocation location = factory.createFeatureLocation(nstart, nend, sfModifier, efModifier);
		
		Feature feature =factory.buildSimpleFeatureWithFeatureId(type, location, description, ftId);
		
		return  factory.createUniProtFeature(feature, createEvidence(evs));

	}



//	void updateAlternativeSequence(HasAlternativeSequence has, String orig,
//			String alt) {
//		List<String> alts = new ArrayList<>();
//		if ((alt != null) && (!alt.isEmpty())) {
//			alts.add(alt);
//		}
//		updateAlternativeSequence(has, orig, alts);
//	}
//
//	void updateAlternativeSequence(HasAlternativeSequence has, String orig,
//			List<String> alts) {
//		FeatureSequence origFS = factory.buildFeatureSequence(orig);
//		has.setOriginalSequence(origFS);
//		List<FeatureSequence> altFSs = new ArrayList<>();
//		for (String alt : alts) {
//			altFSs.add(factory.buildFeatureSequence(alt));
//		}
//		has.setAlternativeSequences(altFSs);
//
//	}
	protected List<Evidence> createEvidence(List<String> evIds) {
		EvidenceFactory evFactory = UniProtFactory.INSTANCE.getEvidenceFactory();
		return evIds.stream().map(val -> evFactory.createFromEvidenceLine(val)).collect(Collectors.toList());

	}
}

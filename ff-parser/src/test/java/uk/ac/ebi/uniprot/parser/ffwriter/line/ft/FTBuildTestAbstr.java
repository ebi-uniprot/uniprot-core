package uk.ac.ebi.uniprot.parser.ffwriter.line.ft;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.stream.Collectors;

import uk.ac.ebi.uniprot.domain.Position;
import uk.ac.ebi.uniprot.domain.PositionModifier;
import uk.ac.ebi.uniprot.domain.Range;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.factory.FeatureFactory;
import uk.ac.ebi.uniprot.domain.uniprot.factory.UniProtFactory;
import uk.ac.ebi.uniprot.domain.uniprot.feature.AlternativeSequence;
import uk.ac.ebi.uniprot.domain.uniprot.feature.Feature;
import uk.ac.ebi.uniprot.domain.uniprot.feature.FeatureType;
import uk.ac.ebi.uniprot.parser.ffwriter.FFLine;
import uk.ac.ebi.uniprot.parser.ffwriter.FFLineBuilder;
import uk.ac.ebi.uniprot.parser.impl.ft.FeatureLineBuilderFactory;

public class FTBuildTestAbstr {
	protected final FeatureFactory factory = FeatureFactory.INSTANCE;

	void doTest(String ftLine, Feature feature) {
		FFLineBuilder<Feature> builder = FeatureLineBuilderFactory.create(feature);

		FFLine ffLine = builder.buildWithEvidence(feature);
		String resultString = ffLine.toString();
		System.out.println(resultString);
		System.out.println(ftLine);
		assertEquals(ftLine, resultString);
	}

	void doTestString(String ftLine, Feature feature) {

		FFLineBuilder<Feature> builder = FeatureLineBuilderFactory.create(feature);
		String value = builder.buildString(feature);
		System.out.println(value);
		assertEquals(ftLine, value);
	}

	void doTestStringEv(String ccLine, Feature feature) {

		FFLineBuilder<Feature> builder = FeatureLineBuilderFactory.create(feature);
		String value = builder.buildStringWithEvidence(feature);

		System.out.println(value);
		assertEquals(ccLine, value);
	}

	Feature createFeature(FeatureType type, int nstart, int nend, String description, String ftId, List<String> evs) {
		return createFeature(type, nstart, nend, PositionModifier.EXACT, PositionModifier.EXACT, description, ftId,
				evs);
	}

	Feature createFeature(FeatureType type, int nstart, int nend, PositionModifier sfModifier,
			PositionModifier efModifier, String description, String ftId, List<String> evs) {
		Range location = new Range(new Position(nstart, sfModifier), new Position(nend, efModifier));

		return factory.createFeature(type, location, description, factory.createFeatureId(ftId), createEvidence(evs));
	}

	Feature createFeature(FeatureType type, Range location, String description, String ftId,
			AlternativeSequence alternativeSequence, List<String> evs) {
		return factory.createFeature(type, location, description, factory.createFeatureId(ftId), createEvidence(evs));
	}

	AlternativeSequence createAlternativeSequence(String originalSequence, List<String> alternativeSequences,
			List<String> report) {
		return factory.createAlternativeSequence(originalSequence, alternativeSequences, factory.createReport(report));
	}

	protected List<Evidence> createEvidence(List<String> evIds) {
		return evIds.stream().map(val -> UniProtFactory.INSTANCE.createEvidence(val)).collect(Collectors.toList());
	}
}

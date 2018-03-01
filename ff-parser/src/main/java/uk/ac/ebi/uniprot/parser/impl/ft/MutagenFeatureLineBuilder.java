package uk.ac.ebi.uniprot.parser.impl.ft;

import static uk.ac.ebi.uniprot.parser.ffwriter.impl.FFLineConstant.COLON;
import static uk.ac.ebi.uniprot.parser.ffwriter.impl.FFLineConstant.DASH;
import static uk.ac.ebi.uniprot.parser.ffwriter.impl.FFLineConstant.LINE_LENGTH;
import static uk.ac.ebi.uniprot.parser.ffwriter.impl.FFLineConstant.SEPARATOR;
import static uk.ac.ebi.uniprot.parser.ffwriter.impl.FFLineConstant.SPACE;
import static uk.ac.ebi.uniprot.parser.ffwriter.impl.FFLineConstant.STOP;

import java.util.ArrayList;
import java.util.List;

import uk.ac.ebi.uniprot.domain.feature.MutagenFeature;
import uk.ac.ebi.uniprot.domain.uniprot.UniProtFeature;
import uk.ac.ebi.uniprot.parser.ffwriter.impl.FFLineWrapper;
import uk.ac.ebi.uniprot.parser.ffwriter.impl.LineBuilderHelper;

public class MutagenFeatureLineBuilder extends AbstractFeatureLineBuilder<UniProtFeature<MutagenFeature>> {
	@Override
	protected List<String> buildLines(UniProtFeature<MutagenFeature> f, boolean includeFFMarkings,
			boolean addEvidence) {
		StringBuilder sb = new StringBuilder();
		sb.append(FTLineBuilderHelper.buildFeatureCommon(f.getFeature(), includeFFMarkings));
		StringBuilder extra = FTLineBuilderHelper.buildExtra(f.getFeature());
		String evIds = "";
		if (addEvidence) {
			evIds = LineBuilderHelper.export(f.getEvidences());
		}

		if (includeFFMarkings) {
			sb.append(FTLineBuilderHelper.SPACE_LOCATION_DESCRIPTION);
			// needStop = true;
		} else {
			sb.append(SPACE);
		}
		sb.append(extra);
		List<String> lines = new ArrayList<>();
		List<String> lines2 = FTLineBuilderHelper.addAlternativeSequence(sb, f.getFeature(), includeFFMarkings);
		for (int i = 0; i < lines2.size(); i++) {
			if (i == (lines2.size() - 1)) {
				sb = new StringBuilder(lines2.get(i));
			} else {
				lines.add(lines2.get(i));
			}
		}
		sb.append(": ");
		sb.append(getReports(f.getFeature()));
		sb.append(STOP);
		if (evIds.length() > 0) {
			sb.append(evIds);
			sb.append(STOP);
		}
		String[] seps = { SEPARATOR, DASH, COLON };
		List<String> lines3 = FFLineWrapper.buildLines(sb.toString(), seps, FTLineBuilderHelper.FT_LINE_PREFIX_2,
				LINE_LENGTH);
		if (includeFFMarkings)
			lines.addAll(lines3);
		else
			lines.add(sb.toString());
		StringBuilder featureId = FTLineBuilderHelper.getFeatureId(f.getFeature(), includeFFMarkings);
		if (featureId.length() > 0) {
			lines.add(featureId.toString());
		}
		return lines;
	}

	private String getReports(MutagenFeature feature) {

		StringBuilder temp = new StringBuilder();
		boolean first = true;

		for (String c : feature.getReport().getValue()) {
			if (first) {
				first = false;
			} else {
				temp.append("; ");
			}
			temp.append(c);
		}
		return temp.toString();
	}
}

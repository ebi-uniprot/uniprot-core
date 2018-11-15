package uk.ac.ebi.uniprot.parser.impl.ft;

import uk.ac.ebi.uniprot.parser.ffwriter.FFLine;
import uk.ac.ebi.uniprot.parser.ffwriter.FFLineBuilder;
import uk.ac.ebi.uniprot.parser.ffwriter.LineType;
import uk.ac.ebi.uniprot.parser.ffwriter.impl.FFLineBuilderAbstr;
import uk.ac.ebi.uniprot.parser.ffwriter.impl.FFLineWrapper;
import uk.ac.ebi.uniprot.parser.ffwriter.impl.FFLines;
import uk.ac.ebi.uniprot.parser.ffwriter.impl.LineBuilderHelper;
import uk.ac.ebi.uniprot.domain.feature.Feature;
import uk.ac.ebi.uniprot.domain.uniprot.UniProtFeature;

import static uk.ac.ebi.uniprot.parser.ffwriter.impl.FFLineConstant.COLON;
import static uk.ac.ebi.uniprot.parser.ffwriter.impl.FFLineConstant.DASH;
import static uk.ac.ebi.uniprot.parser.ffwriter.impl.FFLineConstant.LINE_LENGTH;
import static uk.ac.ebi.uniprot.parser.ffwriter.impl.FFLineConstant.SEPARATOR;
import static uk.ac.ebi.uniprot.parser.ffwriter.impl.FFLineConstant.SPACE;
import static uk.ac.ebi.uniprot.parser.ffwriter.impl.FFLineConstant.STOP;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractFeatureLineBuilder<T extends UniProtFeature<? extends Feature>>
		extends FFLineBuilderAbstr<T> implements FFLineBuilder<T> {
	public AbstractFeatureLineBuilder() {
		super(LineType.FT);
	}

	@Override
	public String buildString(T f) {
		List<String> lines = buildLines(f, false, false);
		return FFLines.create(lines).toString();
	}

	@Override
	public String buildStringWithEvidence(T f) {
		List<String> lines = buildLines(f, false, true);
		return FFLines.create(lines).toString();
	}

	@Override
	protected FFLine buildLine(T f, boolean showEvidence) {
		List<String> lines = buildLines(f, true, showEvidence);
		return FFLines.create(lines);
	}

	protected List<String> buildLines(T f, boolean includeFFMarkings, boolean addEvidence) {
		StringBuilder sb = new StringBuilder();
		sb.append(FTLineBuilderHelper.buildFeatureCommon(f.getFeature(), includeFFMarkings));
		StringBuilder extra = buildExtra(f);
		String evIds = "";
		if (addEvidence) {
			evIds = LineBuilderHelper.export(f.getEvidences());
		}
		if (includeFFMarkings) {
			if (!evIds.isEmpty() || extra.length() > 0) {
				sb.append(FTLineBuilderHelper.SPACE_LOCATION_DESCRIPTION);

			}
		} else {
			if (extra.length() > 0) {
				sb.append(SPACE);
			}
		}
		if (extra.length() > 0) {
			sb.append(extra);
			sb.append(STOP);
		}
		if (evIds.length() > 0) {
			if (extra.length() == 0) {
				evIds = evIds.trim();
			}
			sb.append(evIds);
			sb.append(STOP);
		}
		StringBuilder featureId = FTLineBuilderHelper.getFeatureId(f.getFeature(), includeFFMarkings);
		List<String> lines = new ArrayList<>();
		if (includeFFMarkings) {
			String[] seps = { SEPARATOR, DASH, COLON };
			lines.addAll(
					FFLineWrapper.buildLines(sb.toString(), seps, FTLineBuilderHelper.FT_LINE_PREFIX_2, LINE_LENGTH));
		} else {
			lines.add(sb.toString());
		}
		if (featureId.length() > 0) {
			lines.add(featureId.toString());
		}
		return lines;

	}

	protected StringBuilder buildExtra(T f) {
		return FTLineBuilderHelper.buildExtra(f.getFeature());
	}

}

package org.uniprot.core.flatfile.parser.impl.ft;

import static org.uniprot.core.flatfile.writer.impl.FFLineConstant.*;

import java.util.ArrayList;
import java.util.List;

import org.uniprot.core.flatfile.writer.FFLine;
import org.uniprot.core.flatfile.writer.FFLineBuilder;
import org.uniprot.core.flatfile.writer.LineType;
import org.uniprot.core.flatfile.writer.impl.FFLineBuilderAbstr;
import org.uniprot.core.flatfile.writer.impl.FFLineWrapper;
import org.uniprot.core.flatfile.writer.impl.FFLines;
import org.uniprot.core.flatfile.writer.impl.LineBuilderHelper;
import org.uniprot.core.uniprot.feature.Feature;

public abstract class AbstractFeatureLineBuilder
		extends FFLineBuilderAbstr<Feature> implements FFLineBuilder<Feature> {
	public AbstractFeatureLineBuilder() {
		super(LineType.FT);
	}

	@Override
	public String buildString(Feature f) {
		List<String> lines = buildLines(f, false, false);
		return FFLines.create(lines).toString();
	}

	@Override
	public String buildStringWithEvidence(Feature f) {
		List<String> lines = buildLines(f, false, true);
		return FFLines.create(lines).toString();
	}

	@Override
	protected FFLine buildLine(Feature f, boolean showEvidence) {
		List<String> lines = buildLines(f, true, showEvidence);
		return FFLines.create(lines);
	}

	protected List<String> buildLines(Feature f, boolean includeFFMarkings, boolean addEvidence) {
		StringBuilder sb = new StringBuilder();
		sb.append(FTLineBuilderHelper.buildFeatureCommon(f, includeFFMarkings));
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
		//	if (extra.length() > 0) {
				sb.append(SPACE);
		//	}
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
		StringBuilder featureId = FTLineBuilderHelper.getFeatureId(f, includeFFMarkings);
		List<String> lines = new ArrayList<>();
		if (includeFFMarkings) {
			String[] seps = { SEPARATOR, DASH };
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

	protected StringBuilder buildExtra(Feature f) {
		return FTLineBuilderHelper.buildExtra(f);
	}

	protected boolean hasAltSequenceReport(Feature f) {
		return f.hasAlternativeSequence() && (f.getAlternativeSequence() !=null);
			//	&& (f.getAlternativeSequence().getReport() !=null) &&
			//	  (f.getAlternativeSequence().getReport().getValue().size() > 0);
	}
}
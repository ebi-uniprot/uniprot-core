package uk.ac.ebi.uniprot.parser.impl.ss;

import uk.ac.ebi.uniprot.domain.uniprot.evidence2.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.evidence2.EvidenceLine;
import uk.ac.ebi.uniprot.parser.ffwriter.FFLine;
import uk.ac.ebi.uniprot.parser.ffwriter.LineType;
import uk.ac.ebi.uniprot.parser.ffwriter.impl.FFLineBuilderAbstr;
import uk.ac.ebi.uniprot.parser.ffwriter.impl.FFLines;

import java.util.ArrayList;
import java.util.List;

import static uk.ac.ebi.uniprot.parser.ffwriter.impl.FFLineConstant.SEPARATOR_SEMICOLON;
import static uk.ac.ebi.uniprot.parser.ffwriter.impl.FFLineConstant.STOP;

public class SSEvidenceLineBuilder extends FFLineBuilderAbstr<List<EvidenceLine>> {
	private static final String REFERENCE = "Reference";
	private final static String prefix = "**EV ";
	private final static String DEFAULT_ORIG_NAME = "-";

	public SSEvidenceLineBuilder() {
		super(LineType.STAR_STAR);
	}

	@Override
	public String buildString(List<EvidenceLine> f) {
		return buildLine(f, false).toString();
	}

	@Override
	public String buildStringWithEvidence(List<EvidenceLine> f) {
		return buildLine(f, true).toString();
	}

	@Override
	protected FFLine buildLine(List<EvidenceLine> evidences, boolean showEvidence) {
		List<String> lines = new ArrayList<>();
		if ((evidences == null) || (evidences.isEmpty()))
			return FFLines.create(lines);
		for (EvidenceLine line : evidences) {
			StringBuilder sb = new StringBuilder();
			Evidence evidence = line.toEvidence();
			sb.append(prefix);

			sb.append(evidence.getEvidenceCode().getCode());
			sb.append(SEPARATOR_SEMICOLON);
			if(evidence.getSource() !=null) {
				if(!evidence.getSource().getDatabaseType().getName().equals(REFERENCE)) {
					sb.append(evidence.getSource().getDatabaseType().getName())
					.append(":");
				}
				sb.append(evidence.getSource().getId());
			}else {
				sb.append("-");
			}
			sb.append(SEPARATOR_SEMICOLON);
			if (line.getCurator() == null) {
				sb.append(DEFAULT_ORIG_NAME);
			} else
				sb.append(line.getCurator());

			sb.append(SEPARATOR_SEMICOLON);
			sb.append(dateFormatter.format(line.getCreateDate()).toUpperCase());
			sb.append(STOP);
			lines.add(sb.toString());
		}
		return FFLines.create(lines);
	}

}

package uk.ac.ebi.uniprot.parser.impl.id;

import java.util.ArrayList;
import java.util.List;

import uk.ac.ebi.uniprot.ffwriter.line.FFLine;
import uk.ac.ebi.uniprot.ffwriter.line.FFLineBuilder;
import uk.ac.ebi.uniprot.ffwriter.line.FFLines;
import uk.ac.ebi.uniprot.ffwriter.line.LineType;
import uk.ac.ebi.uniprot.ffwriter.line.impl.FFLineBuilderAbstr;

import static uk.ac.ebi.uniprot.ffwriter.line.FFLineConstant.*;

public class IDLineBuilder extends FFLineBuilderAbstr<IdLineObject> implements
		FFLineBuilder<IdLineObject> {
	
	
	private static final String AA = "AA";
	private static final String REVIEWED = "Reviewed";
	private static final String UNREVIEWED = "Unreviewed";
	private static final char SINGLE_SPACE =' ';
	private static final int UNREVIEWED_EXTRA_SPACE = 10;
	private static final int REVIEWED_EXTRA_SPACE = 12;
	private static final int ID_LENGTH = 29;

	public IDLineBuilder(){
		super(LineType.ID);
	}

	@Override
	public String buildString(IdLineObject f) {
		return export(f).toString();
	}
	
	@Override
	public String buildStringWithEvidence(IdLineObject f) {
		return export(f).toString();
	}

	@Override
	protected FFLine buildLine(IdLineObject f, boolean showEvidence) {
		List<String> lines = new ArrayList<>();
		lines.add(export(f).toString());
		return FFLines.create(lines);
	}
	
	private StringBuilder export(IdLineObject entry) {
		StringBuilder sb = new StringBuilder();
		if(entry.getReviewed()) {
			sb.append(buildIdLine(entry.getEntryName(),
					entry.getSequenceLength(), REVIEWED, REVIEWED_EXTRA_SPACE));
		}else {
			sb.append(buildIdLine(entry.getEntryName(),
					entry.getSequenceLength(),  UNREVIEWED, UNREVIEWED_EXTRA_SPACE));
		
		}
	
		return sb;
	}
	private StringBuilder buildIdLine(String uniprotId, int seqLength, String reviewType, int extraSpace){
		StringBuilder sb = new StringBuilder();
		sb.append(linePrefix);
		sb.append(uniprotId);
		for (int iii = sb.length(); iii < ID_LENGTH; iii++) {
			sb.append(SINGLE_SPACE);
		}
		sb.append(reviewType).append(SEMI_COMA);
		int lengthlength = ((new Integer(seqLength)).toString()).length();
		for (int iii = lengthlength; iii < extraSpace; iii++) {
			sb.append(SINGLE_SPACE);
		}
		sb.append(seqLength);
		sb.append(SINGLE_SPACE);
		sb.append(AA);
		sb.append(STOP);
		return sb;
	}


}

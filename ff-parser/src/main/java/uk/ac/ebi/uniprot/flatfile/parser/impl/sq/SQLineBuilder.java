package uk.ac.ebi.uniprot.flatfile.parser.impl.sq;

import uk.ac.ebi.uniprot.flatfile.parser.ffwriter.FFLine;
import uk.ac.ebi.uniprot.flatfile.parser.ffwriter.LineType;
import uk.ac.ebi.uniprot.flatfile.parser.ffwriter.impl.FFLineBuilderAbstr;
import uk.ac.ebi.uniprot.flatfile.parser.ffwriter.impl.FFLines;

import java.util.ArrayList;
import java.util.List;

import org.uniprot.core.Sequence;

import static uk.ac.ebi.uniprot.flatfile.parser.ffwriter.impl.FFLineConstant.SPACE;

public class SQLineBuilder extends FFLineBuilderAbstr<Sequence> {

	public SQLineBuilder(){
		super(LineType.SQ);
	}
	@Override
	public String buildString(Sequence f) {
		List<String> lines = buildLine(f, false, false);
		return  FFLines.create(lines).toString();
	}

	@Override
	public String buildStringWithEvidence(Sequence f) {
		List<String> lines = buildLine(f, false, true);
		return  FFLines.create(lines).toString();
	}
	@Override
	protected FFLine buildLine(Sequence f, boolean showEvidence) {
		List<String> lines = buildLine(f, true, showEvidence);
		return  FFLines.create(lines);
	}

	private List<String> buildLine(Sequence f, boolean includeFFMark, boolean showEvidence) {
		List<String> lines =new ArrayList<>();
		
		StringBuilder sq = new StringBuilder();
		if(includeFFMark){
			sq.append(linePrefix);
		}
		sq.append("SEQUENCE   ");
		sq.append(f.getLength());
		sq.append(" AA;  ");
		sq.append(f.getMolWeight());
		sq.append(" MW;  ");
		sq.append(f.getCrc64());
		sq.append(" CRC64;");
		lines.add(sq.toString());

		String sequence = f.getValue();
		int rowCounter = 0;
		sq =new StringBuilder();
		if(includeFFMark)
			sq.append("    ");
		for (int i =0; i<sequence.length();i=i+10) {
			if (rowCounter == 6) {
				lines.add(sq.toString());
				sq =new StringBuilder();
				if(includeFFMark)
					sq.append("     ");
				rowCounter = 0;
			} else {
				sq.append(SPACE);

			}
			if (sequence.length()>i+10) {

				sq.append(sequence.substring(i,i+10));
				rowCounter++;
			} else {

				sq.append(sequence.substring(i));
			}
		}
		if(sq.length()>0){
			lines.add(sq.toString());
		}
		return lines;
	}
}

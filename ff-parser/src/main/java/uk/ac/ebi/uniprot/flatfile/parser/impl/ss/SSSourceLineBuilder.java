package uk.ac.ebi.uniprot.flatfile.parser.impl.ss;

import uk.ac.ebi.uniprot.domain.uniprot.SourceLine;
import uk.ac.ebi.uniprot.flatfile.parser.ffwriter.FFLine;
import uk.ac.ebi.uniprot.flatfile.parser.ffwriter.LineType;
import uk.ac.ebi.uniprot.flatfile.parser.ffwriter.impl.FFLineBuilderAbstr;
import uk.ac.ebi.uniprot.flatfile.parser.ffwriter.impl.FFLines;

import java.util.ArrayList;
import java.util.List;

public class SSSourceLineBuilder extends FFLineBuilderAbstr<List<SourceLine> > {
	private final static String SOURCE_SECTION =
			"**   #################     SOURCE SECTION     ##################";
	private final static String prefix ="**   ";
	
	
	public SSSourceLineBuilder(){
		super(LineType.STAR_STAR);
	}
	@Override
	public String buildString(List<SourceLine> f) {
		return buildLine(f, false).toString();
	}
	@Override
	public String buildStringWithEvidence(List<SourceLine> f) {
		return buildLine(f, true).toString();
	}
	
	@Override
	protected FFLine buildLine(List<SourceLine> sourceLines, boolean showEvidence) {
		List<String> lines =new ArrayList<>();
		if (!(sourceLines.isEmpty())) {
			lines.add(SOURCE_SECTION);
			for (SourceLine line : sourceLines) {
				lines.add(prefix + line.getValue());
			}
		}
		return FFLines.create(lines);
	}

}

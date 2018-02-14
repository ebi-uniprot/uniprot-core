package uk.ac.ebi.uniprot.parser.impl.cc;

import java.util.List;

import uk.ac.ebi.uniprot.domain.uniprot.comments.Comment;
import uk.ac.ebi.uniprot.parser.LineTransformer;
import uk.ac.ebi.uniprot.parser.UniprotLineParser;
import uk.ac.ebi.uniprot.parser.impl.DefaultUniprotLineParserFactory;

public class CcLineTransformer implements LineTransformer<Comment> {
	private final UniprotLineParser<CcLineObject> parser = new DefaultUniprotLineParserFactory().createCcLineParser();
	private final CcLineFormater formater  =new CcLineFormater();
	private final CcLineConverter converter = new CcLineConverter();
	@Override
	public List<Comment> transform(String lines) {
		CcLineObject obj = parser.parse(lines);
		return converter.convert(obj);
	}

	@Override
	public List<Comment> transformNoHeader(String lines) {
		return transform(formater.format(lines));
	}

}

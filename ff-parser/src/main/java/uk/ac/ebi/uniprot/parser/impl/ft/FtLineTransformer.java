package uk.ac.ebi.uniprot.parser.impl.ft;

import java.util.List;

import uk.ac.ebi.uniprot.domain.uniprot.feature.Feature;
import uk.ac.ebi.uniprot.parser.LineTransformer;
import uk.ac.ebi.uniprot.parser.UniprotLineParser;
import uk.ac.ebi.uniprot.parser.impl.DefaultUniprotLineParserFactory;

public class FtLineTransformer implements LineTransformer<Feature> {
	private final UniprotLineParser<FtLineObject> parser = new DefaultUniprotLineParserFactory().createFtLineParser();
	private final FtLineFormater formater  =new FtLineFormater();
	private final FtLineConverter converter = new FtLineConverter();
	@Override
	public List<Feature> transform(String lines) {
		return converter.convert(parser.parse(lines));
	}

	@Override
	public List< Feature>  transformNoHeader(String lines) {
		return transform(formater.format(lines));
	}

}

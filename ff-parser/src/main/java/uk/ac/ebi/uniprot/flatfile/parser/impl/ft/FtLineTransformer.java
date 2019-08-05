package uk.ac.ebi.uniprot.flatfile.parser.impl.ft;

import uk.ac.ebi.uniprot.flatfile.parser.LineTransformer;
import uk.ac.ebi.uniprot.flatfile.parser.UniprotLineParser;
import uk.ac.ebi.uniprot.flatfile.parser.impl.DefaultUniprotLineParserFactory;

import java.util.List;

import org.uniprot.core.uniprot.feature.Feature;

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

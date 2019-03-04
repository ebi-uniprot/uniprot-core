package uk.ac.ebi.uniprot.flatfile.parser.impl.cc;

import uk.ac.ebi.uniprot.cv.impl.DiseaseFileReader;
import uk.ac.ebi.uniprot.cv.impl.SubcellularLocationFileReader;
import uk.ac.ebi.uniprot.domain.uniprot.comment.Comment;
import uk.ac.ebi.uniprot.flatfile.parser.LineTransformer;
import uk.ac.ebi.uniprot.flatfile.parser.UniprotLineParser;
import uk.ac.ebi.uniprot.flatfile.parser.impl.DefaultUniprotLineParserFactory;

import java.util.List;
import java.util.Map;

public class CcLineTransformer implements LineTransformer<Comment> {
	private final UniprotLineParser<CcLineObject> parser = new DefaultUniprotLineParserFactory().createCcLineParser();
	private final CcLineFormater formater  =new CcLineFormater();
	private final CcLineConverter converter;
	public CcLineTransformer() {
		this("", "");
	}
	
	public CcLineTransformer(String diseaseFile, String subcellularLocationFile) {
		Map<String,String> subcellularLocationMap = new SubcellularLocationFileReader().parseFileToAccessionMap(subcellularLocationFile);
		Map<String,String> diseaseMap = new DiseaseFileReader().parseFileToAccessionMap(diseaseFile);
		 converter = new CcLineConverter(diseaseMap, subcellularLocationMap);
	}

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

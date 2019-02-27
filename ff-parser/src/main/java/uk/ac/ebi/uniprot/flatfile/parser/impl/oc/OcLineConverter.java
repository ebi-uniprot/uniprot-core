package uk.ac.ebi.uniprot.flatfile.parser.impl.oc;

import uk.ac.ebi.uniprot.flatfile.parser.Converter;

import java.util.List;


public class OcLineConverter implements Converter<OcLineObject,  List<String>> {
	@Override
	public List<String> convert(OcLineObject f) {
		return f.nodes;
	}
}

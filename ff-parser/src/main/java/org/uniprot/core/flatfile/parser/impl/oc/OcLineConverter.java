package org.uniprot.core.flatfile.parser.impl.oc;

import java.util.List;

import org.uniprot.core.flatfile.parser.Converter;


public class OcLineConverter implements Converter<OcLineObject,  List<String>> {
	@Override
	public List<String> convert(OcLineObject f) {
		return f.nodes;
	}
}

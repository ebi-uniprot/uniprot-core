package uk.ac.ebi.uniprot.parser.impl.rp;

import java.util.List;
import java.util.stream.Collectors;

import uk.ac.ebi.uniprot.parser.Converter;

public class RpLineConverter implements Converter<RpLineObject, List<String>> {

	@Override
	public List<String> convert(RpLineObject f) {
		return f.scopes.stream().collect(Collectors.toList());
		
	}
}

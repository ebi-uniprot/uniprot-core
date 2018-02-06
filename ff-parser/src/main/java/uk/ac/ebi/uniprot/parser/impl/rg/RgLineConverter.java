package uk.ac.ebi.uniprot.parser.impl.rg;


import java.util.List;
import java.util.stream.Collectors;

import uk.ac.ebi.uniprot.parser.Converter;

public class RgLineConverter implements Converter<RgLineObject, List<String> > {

	@Override
	public List<String> convert(RgLineObject f) {
		return f.reference_groups.stream().collect(Collectors.toList());
		
	}

}

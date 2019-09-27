package org.uniprot.core.cv.pathway;

import org.uniprot.core.cv.common.AbstractFileReader;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class UniPathwayFileReader extends AbstractFileReader<UniPathway> {

	@Override
	public List<UniPathway> parseLines(List<String> lines) {
		List<UniPathway>  pathways = lines.stream()
		.filter(val ->!val.startsWith("**"))
		.map(this::convert)
		.collect(Collectors.toList());
		updateRelationship( pathways);
		return pathways;
	
	}
	private UniPathway convert(String line) {
		int index = line.indexOf(" ");
		String accession = line.substring(0,  index);
		String value = line.substring(index+1);		
		return new UniPathway(accession, value);
	}
	private void updateRelationship(List<UniPathway> pathways) {
		Map<String, UniPathway> pathwayIdMap = pathways.stream().collect(Collectors.toMap(UniPathway::getAccession, Function.identity()));
		for(UniPathway pathway: pathways) {
			UniPathway parent = getParent(pathway, pathwayIdMap);
			if(parent !=null) {
				pathway.setParent(parent);
			}
		}
	}
	private UniPathway getParent( UniPathway pathway, Map<String, UniPathway> pathwayIdMap  ){
		String id = pathway.getAccession();
		int index = id.lastIndexOf(".");
		if(index ==-1) {
			return null;
		}else {
			String parentId = id.substring(0, index);
		return	pathwayIdMap.get(parentId);
		}
		
	}
}

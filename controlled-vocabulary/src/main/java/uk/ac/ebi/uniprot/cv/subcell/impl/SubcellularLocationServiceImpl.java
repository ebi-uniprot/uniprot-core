package uk.ac.ebi.uniprot.cv.subcell.impl;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import uk.ac.ebi.uniprot.cv.subcell.SubcellularLocation;
import uk.ac.ebi.uniprot.cv.subcell.SubcellularLocationCache;
import uk.ac.ebi.uniprot.cv.subcell.SubcellularLocationService;

public class SubcellularLocationServiceImpl implements SubcellularLocationService {
	private List<SubcellularLocation> subcellularLocations ;
	private Map<String, SubcellularLocation> subcellularLocationIdMap;

	public SubcellularLocationServiceImpl() {
		this(SubcellularLocationCache.FTP_LOCATION);
	}
	public SubcellularLocationServiceImpl(String diseasefile) {
	
		subcellularLocations = SubcellularLocationCache.INSTANCE.get(diseasefile);
		subcellularLocationIdMap =subcellularLocations.stream().collect(Collectors.toMap(SubcellularLocation::getId, Function.identity()));
	} 
	
	
	@Override
	public List<SubcellularLocation> getAll() {
		return subcellularLocations;
	}

	@Override
	public SubcellularLocation getById(String id) {
		return subcellularLocationIdMap.get(id);
	}

}

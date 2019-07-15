package uk.ac.ebi.uniprot.cv.subcell.impl;

import uk.ac.ebi.uniprot.cv.subcell.SubcellularLocationCache;
import uk.ac.ebi.uniprot.cv.subcell.SubcellularLocationEntry;
import uk.ac.ebi.uniprot.cv.subcell.SubcellularLocationService;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class SubcellularLocationServiceImpl implements SubcellularLocationService {
	private List<SubcellularLocationEntry> subcellularLocations;
	private Map<String, SubcellularLocationEntry> subcellularLocationIdMap;

	public SubcellularLocationServiceImpl() {
		this(SubcellularLocationCache.FTP_LOCATION);
	}
	public SubcellularLocationServiceImpl(String diseasefile) {
	
		subcellularLocations = SubcellularLocationCache.INSTANCE.get(diseasefile);
		subcellularLocationIdMap = subcellularLocations.stream().collect(Collectors.toMap(SubcellularLocationEntry::getId, Function.identity()));
	} 
	
	
	@Override
	public List<SubcellularLocationEntry> getAll() {
		return subcellularLocations;
	}

	@Override
	public SubcellularLocationEntry getById(String id) {
		return subcellularLocationIdMap.get(id);
	}

}

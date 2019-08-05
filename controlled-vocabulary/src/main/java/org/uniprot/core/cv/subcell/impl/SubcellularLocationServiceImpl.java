package org.uniprot.core.cv.subcell.impl;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.uniprot.core.cv.subcell.SubcellularLocationCache;
import org.uniprot.core.cv.subcell.SubcellularLocationEntry;
import org.uniprot.core.cv.subcell.SubcellularLocationService;

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

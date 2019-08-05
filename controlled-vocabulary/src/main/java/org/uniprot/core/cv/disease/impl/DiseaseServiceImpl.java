package org.uniprot.core.cv.disease.impl;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.uniprot.core.cv.disease.Disease;
import org.uniprot.core.cv.disease.DiseaseCache;
import org.uniprot.core.cv.disease.DiseaseService;

public class DiseaseServiceImpl implements DiseaseService {
	private List<Disease> diseases ;
	private Map<String, Disease> diseaseIdMap;

	public DiseaseServiceImpl() {
		this(DiseaseCache.FTP_LOCATION);
	}
	public DiseaseServiceImpl(String diseasefile) {
	
		diseases = DiseaseCache.INSTANCE.get(diseasefile);
		diseaseIdMap =diseases.stream().collect(Collectors.toMap(Disease::getId, Function.identity()));
	} 
	@Override
	public List<Disease> getAll() {
		return diseases;
	}

	@Override
	public Disease getById(String id) {
		return diseaseIdMap.get(id);
	}

}

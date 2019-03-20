package uk.ac.ebi.uniprot.cv.ec.impl;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import uk.ac.ebi.uniprot.cv.ec.EC;
import uk.ac.ebi.uniprot.cv.ec.ECCache;
import uk.ac.ebi.uniprot.cv.ec.ECService;

public class ECServiceImpl implements ECService {
	private final Map<String, EC> ecIdMap;
	public ECServiceImpl(String dir ) {
		List<EC> eclist = ECCache.INSTANCE.get(dir);
		ecIdMap =eclist.stream().collect(Collectors.toMap(EC::id, Function.identity()));
	}
	@Override
	public EC getEC(String id) {
		return ecIdMap.get(id);
	}

}

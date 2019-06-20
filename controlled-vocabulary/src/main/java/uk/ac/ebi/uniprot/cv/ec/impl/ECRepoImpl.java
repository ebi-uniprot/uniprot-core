package uk.ac.ebi.uniprot.cv.ec.impl;

import uk.ac.ebi.uniprot.cv.ec.EC;
import uk.ac.ebi.uniprot.cv.ec.ECCache;
import uk.ac.ebi.uniprot.cv.ec.ECRepo;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ECRepoImpl implements ECRepo {
    private final Map<String, EC> ecIdMap;

    public ECRepoImpl(String dir) {
        List<EC> eclist = ECCache.INSTANCE.get(dir);
        ecIdMap = eclist.stream().collect(Collectors.toMap(EC::id, Function.identity()));
    }

    @Override
    public EC getEC(String id) {
        return ecIdMap.get(id);
    }

}

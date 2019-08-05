package org.uniprot.core.cv.ec.impl;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.uniprot.core.cv.ec.EC;
import org.uniprot.core.cv.ec.ECCache;
import org.uniprot.core.cv.ec.ECRepo;

public class ECRepoImpl implements ECRepo {
    private final Map<String, EC> ecIdMap;

    public ECRepoImpl(String dir) {
        List<EC> eclist = ECCache.INSTANCE.get(dir);
        ecIdMap = eclist.stream().collect(Collectors.toMap(EC::id, Function.identity()));
    }

    @Override
    public Optional<EC> getEC(String id) {
        if (ecIdMap.containsKey(id)) {
            return Optional.of(ecIdMap.get(id));
        } else {
            return Optional.empty();
        }
    }

}

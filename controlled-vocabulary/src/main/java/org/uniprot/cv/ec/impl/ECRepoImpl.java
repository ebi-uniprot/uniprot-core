package org.uniprot.cv.ec.impl;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.uniprot.core.cv.ec.ECEntry;
import org.uniprot.cv.ec.ECCache;
import org.uniprot.cv.ec.ECRepo;

public class ECRepoImpl implements ECRepo {
    private final Map<String, ECEntry> ecIdMap;

    public ECRepoImpl(String dir) {
        List<ECEntry> eclist = ECCache.INSTANCE.get(dir);
        ecIdMap = eclist.stream().collect(Collectors.toMap(ECEntry::id, Function.identity()));
    }

    @Override
    public Optional<ECEntry> getEC(String id) {
        if (ecIdMap.containsKey(id)) {
            return Optional.of(ecIdMap.get(id));
        } else {
            return Optional.empty();
        }
    }
}

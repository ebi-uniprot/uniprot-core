package org.uniprot.cv.chebi.impl;

import org.uniprot.core.cv.chebi.ChebiEntry;
import org.uniprot.cv.chebi.ChebiCache;
import org.uniprot.cv.chebi.ChebiRepo;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ChebiRepoImpl implements ChebiRepo {
    private final Map<String, ChebiEntry> chebiMap;

    public ChebiRepoImpl(String dir) {
        List<ChebiEntry> chebiList = ChebiCache.INSTANCE.get(dir);
        chebiMap =
                chebiList.stream()
                        .collect(Collectors.toMap(ChebiEntry::getId, Function.identity()));
    }

    @Override
    public ChebiEntry getById(String id) {
        return chebiMap.get(id);
    }
}

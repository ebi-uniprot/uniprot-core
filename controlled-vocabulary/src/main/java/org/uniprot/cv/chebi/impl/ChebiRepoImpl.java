package org.uniprot.cv.chebi.impl;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.uniprot.core.cv.chebi.Chebi;
import org.uniprot.cv.chebi.ChebiCache;
import org.uniprot.cv.chebi.ChebiRepo;

public class ChebiRepoImpl implements ChebiRepo {
    private final Map<String, Chebi> chebiMap;

    public ChebiRepoImpl(String dir) {
        List<Chebi> chebiList = ChebiCache.INSTANCE.get(dir);
        chebiMap = chebiList.stream().collect(Collectors.toMap(Chebi::getId, Function.identity()));
    }

    @Override
    public Chebi getById(String id) {
        return chebiMap.get(id);
    }
}

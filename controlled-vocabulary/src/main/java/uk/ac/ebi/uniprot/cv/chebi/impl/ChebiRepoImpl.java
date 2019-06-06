package uk.ac.ebi.uniprot.cv.chebi.impl;

import uk.ac.ebi.uniprot.cv.chebi.Chebi;
import uk.ac.ebi.uniprot.cv.chebi.ChebiCache;
import uk.ac.ebi.uniprot.cv.chebi.ChebiRepo;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ChebiRepoImpl implements ChebiRepo {
    private final Map<String, Chebi> chebiMap;

    public ChebiRepoImpl(String dir) {
        List<Chebi> chebiList = ChebiCache.INSTANCE.get(dir);
        chebiMap = chebiList.stream().collect(Collectors.toMap(Chebi::id, Function.identity()));
    }

    @Override
    public Chebi getById(String id) {
        return chebiMap.get(id);
    }
}

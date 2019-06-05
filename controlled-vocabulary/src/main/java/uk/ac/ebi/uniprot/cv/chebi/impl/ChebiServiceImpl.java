package uk.ac.ebi.uniprot.cv.chebi.impl;

import uk.ac.ebi.uniprot.cv.chebi.Chebi;
import uk.ac.ebi.uniprot.cv.chebi.ChebiCache;
import uk.ac.ebi.uniprot.cv.chebi.ChebiService;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ChebiServiceImpl implements ChebiService {
    private final Map<String, Chebi> chebiMap;

    public ChebiServiceImpl(String dir) {
        List<Chebi> chebiList = ChebiCache.INSTANCE.get(dir);
        chebiMap = chebiList.stream().collect(Collectors.toMap(Chebi::id, Function.identity()));
    }

    @Override
    public Chebi getChebi(String id) {
        return chebiMap.get(id);
    }
}

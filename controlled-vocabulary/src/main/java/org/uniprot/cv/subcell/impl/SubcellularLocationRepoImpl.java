package org.uniprot.cv.subcell.impl;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.uniprot.core.cv.subcell.SubcellularLocationEntry;
import org.uniprot.cv.subcell.SubcellularLocationCache;
import org.uniprot.cv.subcell.SubcellularLocationRepo;

public class SubcellularLocationRepoImpl implements SubcellularLocationRepo {
    private List<SubcellularLocationEntry> subcellularLocations;
    private Map<String, SubcellularLocationEntry> subcellularLocationIdMap;

    public SubcellularLocationRepoImpl(String diseasefile) {

        subcellularLocations = SubcellularLocationCache.INSTANCE.get(diseasefile);
        subcellularLocationIdMap =
                subcellularLocations.stream()
                        .collect(
                                Collectors.toMap(
                                        SubcellularLocationEntry::getName, Function.identity()));
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

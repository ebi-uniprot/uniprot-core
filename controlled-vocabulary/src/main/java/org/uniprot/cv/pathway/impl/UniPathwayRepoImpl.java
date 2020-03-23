package org.uniprot.cv.pathway.impl;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.uniprot.core.cv.pathway.UniPathway;
import org.uniprot.cv.pathway.UniPathwayCache;
import org.uniprot.cv.pathway.UniPathwayRepo;

public class UniPathwayRepoImpl implements UniPathwayRepo {
    private final Map<String, UniPathway> pathwayIdMap;
    private final Map<String, UniPathway> pathwayNameMap;

    public UniPathwayRepoImpl(String filename) {
        List<UniPathway> pathwayList = UniPathwayCache.INSTANCE.get(filename);
        pathwayIdMap =
                pathwayList.stream()
                        .collect(Collectors.toMap(UniPathway::getAccession, Function.identity()));
        pathwayNameMap =
                pathwayList.stream()
                        .collect(Collectors.toMap(UniPathway::getName, Function.identity()));
    }

    @Override
    public UniPathway getById(String id) {
        return pathwayIdMap.get(id);
    }

    @Override
    public UniPathway getByName(String name) {
        return pathwayNameMap.get(name);
    }

    @Override
    public List<UniPathway> getChildrenById(String id) {
        if ((id == null) || (id.isEmpty()) || getById(id) == null) {
            Collection<UniPathway> allPathways = pathwayIdMap.values();
            return allPathways.stream()
                    .filter(val -> val.getParent() == null)
                    .collect(Collectors.toList());
        }
        return getById(id).getChildren();
    }
}

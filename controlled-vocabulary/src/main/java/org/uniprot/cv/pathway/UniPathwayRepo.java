package org.uniprot.cv.pathway;

import org.uniprot.core.cv.pathway.UniPathway;

import java.util.List;

public interface UniPathwayRepo {
    UniPathway getById(String id);

    UniPathway getByName(String name);

    List<UniPathway> getChildrenById(String id);
}

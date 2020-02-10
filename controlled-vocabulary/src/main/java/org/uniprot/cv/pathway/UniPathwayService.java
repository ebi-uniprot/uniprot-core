package org.uniprot.cv.pathway;

import java.util.List;

import org.uniprot.core.cv.pathway.UniPathway;

public interface UniPathwayService {
    UniPathway getById(String id);

    UniPathway getByName(String name);

    List<UniPathway> getChildrenById(String id);
}

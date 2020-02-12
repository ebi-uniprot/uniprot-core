package org.uniprot.core.cv.disease;

import java.io.Serializable;
import java.util.List;

public interface DiseaseCrossReference extends Serializable {
    String getDatabaseType();

    String getId();

    List<String> getProperties();
}

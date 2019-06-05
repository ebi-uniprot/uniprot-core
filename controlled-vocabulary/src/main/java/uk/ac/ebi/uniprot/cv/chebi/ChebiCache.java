package uk.ac.ebi.uniprot.cv.chebi;

import java.util.ArrayList;
import java.util.List;

/**
 * Created 05/06/19
 *
 * @author Edd
 */
public enum ChebiCache {
    INSTANCE;

    public List<Chebi> get(String dir) {
        return new ArrayList<>();
    }
}

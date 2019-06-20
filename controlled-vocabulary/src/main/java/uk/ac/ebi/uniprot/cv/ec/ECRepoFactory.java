package uk.ac.ebi.uniprot.cv.ec;

import uk.ac.ebi.uniprot.cv.ec.impl.ECRepoImpl;

/**
 * Created 20/06/19
 *
 * @author Edd
 */
public class ECRepoFactory {
    public static ECRepo get(String filename) {
        return new ECRepoImpl(filename);
    }
}

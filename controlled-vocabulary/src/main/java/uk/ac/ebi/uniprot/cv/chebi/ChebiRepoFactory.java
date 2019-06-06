package uk.ac.ebi.uniprot.cv.chebi;

import uk.ac.ebi.uniprot.cv.chebi.impl.ChebiRepoImpl;

/**
 * Created 06/06/19
 *
 * @author Edd
 */
public class ChebiRepoFactory {
    public static ChebiRepo get(String filename) {
        return new ChebiRepoImpl(filename);
    }
}

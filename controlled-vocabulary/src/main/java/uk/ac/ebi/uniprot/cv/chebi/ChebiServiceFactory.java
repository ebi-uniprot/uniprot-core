package uk.ac.ebi.uniprot.cv.chebi;

import uk.ac.ebi.uniprot.cv.chebi.impl.ChebiServiceImpl;

/**
 * Created 06/06/19
 *
 * @author Edd
 */
public class ChebiServiceFactory {
    public static ChebiService get(String filename) {
        return new ChebiServiceImpl(filename);
    }
}

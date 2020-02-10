package org.uniprot.cv.chebi;

import org.uniprot.cv.chebi.impl.ChebiRepoImpl;

/**
 * Created 06/06/19
 *
 * @author Edd
 */
public class ChebiRepoFactory {

    private ChebiRepoFactory() {}

    public static ChebiRepo get(String filename) {
        return new ChebiRepoImpl(filename);
    }
}

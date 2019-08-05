package org.uniprot.core.cv.chebi;

import org.uniprot.core.cv.chebi.impl.ChebiRepoImpl;

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

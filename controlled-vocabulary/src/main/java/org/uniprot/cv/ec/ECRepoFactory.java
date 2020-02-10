package org.uniprot.cv.ec;

import org.uniprot.cv.ec.impl.ECRepoImpl;

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

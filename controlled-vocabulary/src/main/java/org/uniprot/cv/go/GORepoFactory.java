package org.uniprot.cv.go;

import org.uniprot.cv.go.impl.GORepoImpl;

/**
 * Created 27/11/2020
 *
 * @author Edd
 */
public class GORepoFactory {
    public static GORepo createRepo(String fileName) {
        return new GORepoImpl(fileName);
    }
}

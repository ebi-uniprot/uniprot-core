package org.uniprot.cv.go;

import org.uniprot.cv.common.CVSystemProperties;
import org.uniprot.cv.go.impl.GORepoImpl;

/**
 * Created 27/11/2020
 *
 * @author Edd
 */
public class GORepoFactory {
    public static GORepo createRepo() {
        return new GORepoImpl(CVSystemProperties.getGOLocation());
    }

    public static GORepo createRepo(String fileName) {
        return new GORepoImpl(fileName);
    }
}

package org.uniprot.utils.uniprotkb;

import org.uniprot.core.cv.go.GoTerm;
import org.uniprot.core.cv.go.impl.GoTermBuilder;
import org.uniprot.core.uniprotkb.UniProtKBEntry;
import org.uniprot.core.uniprotkb.xdb.UniProtKBCrossReference;
import org.uniprot.cv.go.GORepo;
import org.uniprot.cv.go.GORepoFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * This class provides utility methods for retrieving information from a UniProtKB entry.
 *
 * <p>Created 30/11/2020
 *
 * @author Edd
 */
public class UniProtKBEntryUtils {
    private static final GORepo GO_REPO = GORepoFactory.createRepo();

    /**
     * Retrieves a list of {@link GoTerm} objects from a {@link UniProtKBEntry} instance.
     * @param entry the entry
     * @return the list of GO terms extracted from the supplied entry
     */
    public static List<GoTerm> getGOTerms(UniProtKBEntry entry) {
        List<GoTerm> goTerms = new ArrayList<>();
        List<UniProtKBCrossReference> goXRefs = entry.getUniProtCrossReferencesByType("GO");
        for (UniProtKBCrossReference goXRef : goXRefs) {
            String name = GO_REPO.getById(goXRef.getId()).getName();
            goTerms.add(new GoTermBuilder().id(goXRef.getId()).name(name).build());
        }
        return goTerms;
    }
}

package org.uniprot.core.parser.fasta;

import org.uniprot.core.Sequence;
import org.uniprot.core.uniparc.UniParcCrossReference;
import org.uniprot.core.uniparc.UniParcEntry;
import org.uniprot.core.uniparc.UniParcEntryLight;

/**
 * @author jluo
 * @date: 24 Jun 2019
 */
public class UniParcFastaParser {
    public static String toFasta(UniParcEntry entry) {
        String status = "active";
        boolean isActive = entry.getUniParcCrossReferences()
                .stream()
                .anyMatch(UniParcCrossReference::isActive);
        if (!isActive) {
            status = "inactive";
        }
        return getFastaString(entry.getUniParcId().getValue(), status, entry.getSequence());
    }

    public static String toFasta(UniParcEntryLight entry) {
        StringBuilder sb = new StringBuilder();
        String status = "active";
        //TODO: We currently do not have active attribute in UniParcEntryLight object
        //boolean isActive =
        //        entry.getUniParcCrossReferences().stream().anyMatch(val -> val.isActive());
        //if (!isActive) {
        //    status = "inactive";
        //}
        return getFastaString(entry.getUniParcId(), status, entry.getSequence());
    }

    private static String getFastaString(String entry, String status, Sequence sequence) {
        StringBuilder sb = new StringBuilder();
        sb.append(">").append(entry).append(" ");
        sb.append("status=").append(status);
        sb.append("\n");
        int columnCounter = 0;
        for (char c : sequence.getValue().toCharArray()) {
            if (columnCounter % 60 == 0 && columnCounter > 0) {
                sb.append("\n");
            }
            sb.append(c);
            columnCounter++;
        }
        return sb.toString();
    }
}

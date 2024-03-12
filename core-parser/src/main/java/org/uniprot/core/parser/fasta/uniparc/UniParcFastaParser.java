package org.uniprot.core.parser.fasta.uniparc;

import org.uniprot.core.uniparc.UniParcEntry;

import static org.uniprot.core.parser.fasta.FastaUtils.*;

/**
 * @author jluo
 * @date: 24 Jun 2019
 */
public class UniParcFastaParser {

    public static String toFasta(UniParcEntry entry) {
        StringBuilder sb = new StringBuilder();
        String status = "active";
        boolean isActive =
                entry.getUniParcCrossReferences().stream().anyMatch(val -> val.isActive());
        if (!isActive) {
            status = "inactive";
        }
        sb.append(">").append(entry.getUniParcId().getValue()).append(" ");
        sb.append("status=").append(status);
        sb.append("\n");
        sb.append(parseSequence(entry.getSequence().getValue()));
        return sb.toString();
    }
}


package org.uniprot.core.parser.fasta;

import org.uniprot.core.uniref.UniRefEntry;
import org.uniprot.core.uniref.UniRefEntryLight;

/**
 * @author jluo
 * @date: 22 Aug 2019
 */
public class UniRefFastaParser {
    public static String toFasta(UniRefEntryLight entry) {
        StringBuilder sb = new StringBuilder();
        sb.append(getHeader(entry)).append("\n");
        int columnCounter = 0;
        String sequence = entry.getRepresentativeSequence();
        for (char c : sequence.toCharArray()) {
            if (columnCounter % 60 == 0 && columnCounter > 0) {
                sb.append("\n");
            }
            sb.append(c);
            columnCounter++;
        }
        return sb.toString();
    }

    public static String toFasta(UniRefEntry entry) {
        StringBuilder sb = new StringBuilder();
        sb.append(getHeader(entry)).append("\n");
        int columnCounter = 0;
        String sequence = entry.getRepresentativeMember().getSequence().getValue();
        for (char c : sequence.toCharArray()) {
            if (columnCounter % 60 == 0 && columnCounter > 0) {
                sb.append("\n");
            }
            sb.append(c);
            columnCounter++;
        }
        return sb.toString();
    }

    private static String getHeader(UniRefEntryLight entry) {
        StringBuilder sb = new StringBuilder();
        sb.append(">")
                .append(entry.getId().getValue())
                .append(" ")
                .append(entry.getRepresentativeProteinName())
                .append(" n=")
                .append(entry.getMemberCount());

        if (entry.getCommonTaxonId() != 1L) {
            sb.append(" Tax=")
                    .append(entry.getCommonTaxon())
                    .append(" TaxID=")
                    .append(entry.getCommonTaxonId());
        }
        sb.append(" RepID=").append(entry.getRepresentativeId());
        return sb.toString();
    }

    private static String getHeader(UniRefEntry entry) {
        StringBuilder sb = new StringBuilder();
        sb.append(">")
                .append(entry.getId().getValue())
                .append(" ")
                .append(entry.getRepresentativeMember().getProteinName())
                .append(" n=")
                .append(entry.getMembers().size() + 1);

        if (entry.getCommonTaxonId() != 1L) {
            sb.append(" Tax=")
                    .append(entry.getCommonTaxon())
                    .append(" TaxID=")
                    .append(entry.getCommonTaxonId());
        }
        sb.append(" RepID=").append(entry.getRepresentativeMember().getMemberId());
        return sb.toString();
    }
}

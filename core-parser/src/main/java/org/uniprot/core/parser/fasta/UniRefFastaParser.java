package org.uniprot.core.parser.fasta;

import org.uniprot.core.uniprotkb.taxonomy.Organism;
import org.uniprot.core.uniref.UniRefEntry;
import org.uniprot.core.uniref.UniRefEntryLight;

import static org.uniprot.core.parser.fasta.FastaUtils.parseSequence;

/**
 * @author jluo
 * @date: 22 Aug 2019
 */
public class UniRefFastaParser {

    private UniRefFastaParser() {}

    public static String toFasta(UniRefEntryLight entry) {
        StringBuilder sb = new StringBuilder();
        sb.append(getHeader(entry)).append("\n");
        sb.append(parseSequence(entry.getRepresentativeMember().getSequence().getValue()));
        return sb.toString();
    }

    public static String toFasta(UniRefEntry entry) {
        StringBuilder sb = new StringBuilder();
        sb.append(getHeader(entry)).append("\n");
        sb.append(parseSequence(entry.getRepresentativeMember().getSequence().getValue()));
        return sb.toString();
    }

    private static String getHeader(UniRefEntryLight entry) {
        StringBuilder sb = new StringBuilder();
        sb.append(">")
                .append(entry.getId().getValue())
                .append(" ")
                .append(entry.getRepresentativeMember().getProteinName())
                .append(" n=")
                .append(entry.getMemberCount());

        if (entry.getCommonTaxon() != null && entry.getCommonTaxon().getTaxonId() != 1L) {
            sb.append(" Tax=")
                    .append(entry.getCommonTaxon().getScientificName())
                    .append(" TaxID=")
                    .append(entry.getCommonTaxon().getTaxonId());
        }
        sb.append(" RepID=").append(entry.getRepresentativeMember().getMemberId());
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

        if (entry.getCommonTaxon() != null && entry.getCommonTaxon().getTaxonId() != 1L) {
            Organism commonTaxon = entry.getCommonTaxon();
            sb.append(" Tax=")
                    .append(commonTaxon.getScientificName())
                    .append(" TaxID=")
                    .append(commonTaxon.getTaxonId());
        }
        sb.append(" RepID=").append(entry.getRepresentativeMember().getMemberId());
        return sb.toString();
    }
}

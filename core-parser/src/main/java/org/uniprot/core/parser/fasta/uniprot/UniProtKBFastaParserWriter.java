package org.uniprot.core.parser.fasta.uniprot;

import org.uniprot.core.fasta.UniProtKBFasta;
import org.uniprot.core.uniprotkb.ProteinExistence;
import org.uniprot.core.uniprotkb.UniProtKBEntryType;
import org.uniprot.core.uniprotkb.description.FlagType;
import org.uniprot.core.util.Utils;

import java.util.Objects;

import static org.uniprot.core.parser.fasta.FastaUtils.*;

/**
 * @author lgonzales
 * @since 22/10/2020
 */
class UniProtKBFastaParserWriter {
    private static final String ISOFORM_ACCESSION_PART = "-";

    private UniProtKBFastaParserWriter() {}

    static String toString(UniProtKBFasta entry) {
        StringBuilder sb = new StringBuilder();
        sb.append('>');
        sb.append(getUniProtKBEntryType(entry.getEntryType()));
        sb.append('|');
        sb.append(entry.getId());
        sb.append('|');

        if (Objects.isNull(entry.getSequenceRange())) {
            sb.append(entry.getUniProtkbId().getValue());
            sb.append(' ').append(getProteinName(entry));
            sb.append(" OS=").append(entry.getOrganism().getScientificName());
            sb.append(" OX=").append(entry.getOrganism().getTaxonId());

            if (Utils.notNullNotEmpty(entry.getGeneName())) {
                sb.append(" GN=").append(entry.getGeneName());
            }

            if (isNotSwissProtIsoform(entry)) {
                sb.append(" PE=").append(getProteinExist(entry.getProteinExistence()));
                sb.append(" SV=").append(entry.getSequenceVersion());
            }
        } else {
            sb.append(entry.getSequenceRange());
        }

        sb.append("\n");
        sb.append(parseSequence(entry.getSequence().getValue()));
        return sb.toString();
    }

    private static boolean isNotSwissProtIsoform(UniProtKBFasta entry) {
        return !(entry.getId().contains(ISOFORM_ACCESSION_PART)
                && entry.getEntryType() == UniProtKBEntryType.SWISSPROT);
    }

    private static String getUniProtKBEntryType(UniProtKBEntryType entryType) {
        if (entryType == UniProtKBEntryType.SWISSPROT) {
            return "sp";
        } else {
            return "tr";
        }
    }

    private static String getProteinExist(ProteinExistence pe) {
        String peStr = "5";
        if (pe == ProteinExistence.PROTEIN_LEVEL) peStr = "1";
        else if (pe == ProteinExistence.TRANSCRIPT_LEVEL) peStr = "2";
        else if (pe == ProteinExistence.HOMOLOGY) peStr = "3";
        else if (pe == ProteinExistence.PREDICTED) peStr = "4";
        else if (pe == ProteinExistence.UNCERTAIN) peStr = "5";
        return peStr;
    }

    private static String getProteinName(UniProtKBFasta entry) {
        StringBuilder desc = new StringBuilder();
        desc.append(entry.getProteinName());
        if (entry.getFlagType() != null && entry.getFlagType() != FlagType.PRECURSOR) {
            desc.append(" (Fragment)");
        }
        return desc.toString();
    }
}

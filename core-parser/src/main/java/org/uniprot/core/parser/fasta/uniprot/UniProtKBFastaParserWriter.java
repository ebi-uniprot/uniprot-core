package org.uniprot.core.parser.fasta.uniprot;

import java.util.List;

import org.uniprot.core.Sequence;
import org.uniprot.core.fasta.UniProtKBFasta;
import org.uniprot.core.gene.Gene;
import org.uniprot.core.uniprotkb.ProteinExistence;
import org.uniprot.core.uniprotkb.UniProtKBEntry;
import org.uniprot.core.uniprotkb.UniProtKBEntryType;
import org.uniprot.core.uniprotkb.description.Flag;
import org.uniprot.core.uniprotkb.description.FlagType;
import org.uniprot.core.uniprotkb.description.Name;
import org.uniprot.core.uniprotkb.description.ProteinDescription;
import org.uniprot.core.uniprotkb.taxonomy.Organism;
import org.uniprot.core.util.Utils;

/**
 * @author lgonzales
 * @since 22/10/2020
 */
class UniProtKBFastaParserWriter {

    private UniProtKBFastaParserWriter() {}

    static String parse(UniProtKBFasta entry) {
        StringBuilder sb = new StringBuilder();
        sb.append('>');
        sb.append(getUniProtKBEntryType(entry.getEntryType()));
        sb.append('|');
        sb.append(entry.getId());
        sb.append('|');
        sb.append(entry.getUniProtkbId().getValue());
        sb.append(' ').append(getProteinName(entry));
        sb.append(getOrganism(entry.getOrganism()));

        if (Utils.notNullNotEmpty(entry.getGeneName())) {
            sb.append(" GN=").append(entry.getGeneName());
        }
        sb.append(" PE=").append(getProteinExist(entry.getProteinExistence()));
        sb.append(" SV=").append(entry.getSequenceVersion());
        sb.append("\n");
        sb.append(getSequence(entry.getSequence()));
        return sb.toString();
    }

    static String parse(UniProtKBEntry entry) {
        StringBuilder sb = new StringBuilder();
        sb.append('>');
        sb.append(getUniProtKBEntryType(entry.getEntryType()));
        sb.append('|');
        sb.append(entry.getPrimaryAccession().getValue());
        sb.append('|');
        sb.append(entry.getUniProtkbId().getValue());
        sb.append(' ').append(getDescriptionStr(entry.getProteinDescription()));
        sb.append(getOrganism(entry.getOrganism()));
        String geneStr = getGeneStr(entry.getGenes());

        if (geneStr != null) {
            sb.append(" GN=").append(geneStr);
        }
        sb.append(" PE=").append(getProteinExist(entry.getProteinExistence()));
        sb.append(" SV=").append(entry.getEntryAudit().getSequenceVersion());
        sb.append("\n");
        sb.append(getSequence(entry.getSequence()));
        return sb.toString();
    }

    private static String getOrganism(Organism organism) {
        return " OS=" + organism.getScientificName() + " OX=" + organism.getTaxonId();
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

    private static String getGeneStr(List<Gene> genes) {
        String geneName = null;
        String orfName = null;
        String olnName = null;
        for (Gene gene : genes) {
            if (gene.hasGeneName()) geneName = gene.getGeneName().getValue();
            else if (Utils.notNullNotEmpty(gene.getOrderedLocusNames())) {
                olnName = gene.getOrderedLocusNames().get(0).getValue();
            } else if (Utils.notNullNotEmpty(gene.getOrfNames())) {
                orfName = gene.getOrfNames().get(0).getValue();
            }
        }
        if (geneName != null) return geneName;
        else if (olnName != null) return olnName;
        else return orfName;
    }

    private static String getDescriptionStr(ProteinDescription pd) {
        StringBuilder desc = new StringBuilder();
        Name name;
        if (pd.getRecommendedName() != null) {
            name = pd.getRecommendedName().getFullName();
        } else {
            name = pd.getSubmissionNames().get(0).getFullName();
        }
        desc.append(name.getValue());
        Flag flag = pd.getFlag();
        if (flag != null && !flag.getType().equals(FlagType.PRECURSOR)) {
            desc.append(" (Fragment)");
        }
        return desc.toString();
    }

    private static String getProteinName(UniProtKBFasta entry) {
        StringBuilder desc = new StringBuilder();
        desc.append(entry.getProteinName());
        if (entry.getFlagType() != null && entry.getFlagType() != FlagType.PRECURSOR) {
            desc.append(" (Fragment)");
        }
        return desc.toString();
    }

    private static String getSequence(Sequence sequence) {
        StringBuilder sb = new StringBuilder();
        int columnCounter = 0;
        for (char c : sequence.getValue().toCharArray()) {
            sb.append(c);
            if ((++columnCounter % 60 == 0) && (columnCounter < sequence.getLength())) {
                sb.append("\n");
            }
        }
        return sb.toString();
    }
}

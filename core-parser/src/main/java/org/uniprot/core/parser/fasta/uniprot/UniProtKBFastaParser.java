package org.uniprot.core.parser.fasta.uniprot;

import java.util.List;

import org.uniprot.core.fasta.UniProtKBFasta;
import org.uniprot.core.fasta.impl.UniProtKBFastaBuilder;
import org.uniprot.core.gene.Gene;
import org.uniprot.core.uniprotkb.UniProtKBEntry;
import org.uniprot.core.uniprotkb.description.FlagType;
import org.uniprot.core.uniprotkb.description.Name;
import org.uniprot.core.uniprotkb.description.ProteinDescription;
import org.uniprot.core.util.Utils;

public class UniProtKBFastaParser {

    private UniProtKBFastaParser() {}

    public static String toFasta(UniProtKBEntry entry) {
        UniProtKBFasta uniProtKBFasta = toUniProtKBFasta(entry);
        return toFasta(uniProtKBFasta);
    }

    public static UniProtKBFasta toUniProtKBFasta(UniProtKBEntry entry) {
        return new UniProtKBFastaBuilder()
                .id(entry.getPrimaryAccession().getValue())
                .entryType(entry.getEntryType())
                .uniProtkbId(entry.getUniProtkbId())
                .proteinName(getProteinDescription(entry.getProteinDescription()))
                .geneName(getGeneName(entry.getGenes()))
                .organism(entry.getOrganism())
                .flagType(getProteinFlag(entry.getProteinDescription()))
                .proteinExistence(entry.getProteinExistence())
                .sequenceVersion(entry.getEntryAudit().getSequenceVersion())
                .sequence(entry.getSequence())
                .build();
    }

    public static String toFasta(UniProtKBFasta entry) {
        return UniProtKBFastaParserWriter.toString(entry);
    }

    public static UniProtKBFasta fromFasta(String fastaInput) {
        return UniProtKBFastaParserReader.parse(fastaInput);
    }

    static FlagType getProteinFlag(ProteinDescription proteinDescription) {
        FlagType result = null;
        if (proteinDescription.hasFlag()) {
            result = proteinDescription.getFlag().getType();
        }
        return result;
    }

    static String getGeneName(List<Gene> genes) {
        String geneName = null;
        String orfName = null;
        String olnName = null;
        for (Gene gene : genes) {
            if (gene.hasGeneName() && geneName == null) {
                geneName = gene.getGeneName().getValue();
            } else if (Utils.notNullNotEmpty(gene.getOrderedLocusNames()) && olnName == null) {
                olnName = gene.getOrderedLocusNames().get(0).getValue();
            } else if (Utils.notNullNotEmpty(gene.getOrfNames()) && orfName == null) {
                orfName = gene.getOrfNames().get(0).getValue();
            }
        }
        if (geneName != null) return geneName;
        else if (olnName != null) return olnName;
        else return orfName;
    }

    static String getProteinDescription(ProteinDescription pd) {
        StringBuilder desc = new StringBuilder();
        Name name;
        if (pd.getRecommendedName() != null) {
            name = pd.getRecommendedName().getFullName();
        } else {
            name = pd.getSubmissionNames().get(0).getFullName();
        }
        desc.append(name.getValue());
        return desc.toString();
    }
}

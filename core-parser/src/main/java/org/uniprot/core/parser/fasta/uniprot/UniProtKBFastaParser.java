package org.uniprot.core.parser.fasta.uniprot;

import java.util.List;


import org.uniprot.core.Sequence;
import org.uniprot.core.fasta.UniProtKBFasta;
import org.uniprot.core.fasta.impl.UniProtKBFastaBuilder;
import org.uniprot.core.gene.Gene;
import org.uniprot.core.impl.SequenceBuilder;
import org.uniprot.core.uniprotkb.UniProtKBEntry;
import org.uniprot.core.uniprotkb.description.FlagType;
import org.uniprot.core.uniprotkb.description.Name;
import org.uniprot.core.uniprotkb.description.ProteinDescription;
import org.uniprot.core.util.Utils;

public class UniProtKBFastaParser {

    private UniProtKBFastaParser() {}

    public static UniProtKBFasta toUniProtKBFasta(UniProtKBEntry entry) {
        return toUniProtKBFasta(entry, null);
    }
    public static UniProtKBFasta toUniProtKBFasta(UniProtKBEntry entry, String sequenceRange) {
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
                .sequence(getSequence(entry.getSequence(), sequenceRange))
                .sequenceRange(sequenceRange)
                .build();
    }

    public static String toFastaString(UniProtKBEntry entry) {
        UniProtKBFasta uniProtKBFasta = toUniProtKBFasta(entry);
        return toFastaString(uniProtKBFasta);
    }

    public static String toFastaString(UniProtKBFasta entry) {
        return UniProtKBFastaParserWriter.toString(entry);
    }

    public static UniProtKBFasta fromFastaString(String fastaInput) {
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

    private static Sequence getSequence(Sequence sequence, String sequenceRange) {
        if(Utils.notNullNotEmpty(sequenceRange) && sequenceRange.contains("-")) {
            String[] rangeTokens = sequenceRange.split("-");
            int start = Integer.parseInt(rangeTokens[0]);
            int end = Integer.parseInt(rangeTokens[1]);
            String sequenceString = sequence.getValue()
                    .substring(
                            Math.min(start - 1, sequence.getLength()),
                            Math.min(end, sequence.getLength()));
            return new SequenceBuilder(sequenceString).build();
        }
        return sequence;
    }
}

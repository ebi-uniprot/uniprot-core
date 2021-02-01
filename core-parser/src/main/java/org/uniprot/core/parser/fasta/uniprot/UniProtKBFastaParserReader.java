package org.uniprot.core.parser.fasta.uniprot;

import static java.util.Arrays.copyOfRange;

import org.uniprot.core.fasta.UniProtKBFasta;
import org.uniprot.core.fasta.impl.UniProtKBFastaBuilder;
import org.uniprot.core.uniprotkb.ProteinExistence;
import org.uniprot.core.uniprotkb.UniProtKBEntryType;
import org.uniprot.core.uniprotkb.description.FlagType;
import org.uniprot.core.uniprotkb.taxonomy.Organism;
import org.uniprot.core.uniprotkb.taxonomy.impl.OrganismBuilder;

/**
 * @author lgonzales
 * @since 17/10/2020
 */
class UniProtKBFastaParserReader {

    private UniProtKBFastaParserReader() {}

    static UniProtKBFasta parse(String fastaInput) {
        UniProtKBFastaBuilder builder = new UniProtKBFastaBuilder();
        String[] lines = fastaInput.split("\n");

        parseFastaHeader(builder, lines[0]);
        parseSequence(builder, copyOfRange(lines, 1, lines.length));

        return builder.build();
    }

    // >sp|P21802|FGFR2_HUMAN Fibroblast growth factor receptor 2 OS=Homo sapiens OX=9606 GN=FGFR2
    // PE=1 SV=1
    private static void parseFastaHeader(UniProtKBFastaBuilder builder, String line) {
        int spaceIndex = line.indexOf(" ");
        int geneIndex = line.indexOf(" GN=", spaceIndex);
        int proteinExistenceIndex = line.indexOf(" PE=", spaceIndex);
        int sequenceVersionIndex = line.indexOf(" SV=", proteinExistenceIndex);

        String[] proteinIds = line.substring(0, spaceIndex).split("\\|");
        builder.entryType(parseEntryType(proteinIds[0]));
        builder.id(proteinIds[1]);
        builder.uniProtkbId(proteinIds[2]);

        parseProteinName(builder, line);

        builder.organism(parseOrganism(line));

        if (geneIndex >= 0) {
            builder.geneName(line.substring(geneIndex + 4, proteinExistenceIndex));
        }

        String proteinExistence = line.substring(proteinExistenceIndex + 4, sequenceVersionIndex);
        builder.proteinExistence(parseProteinExistence(proteinExistence));

        String sequenceVersion = line.substring(sequenceVersionIndex + 4);
        builder.sequenceVersion(Integer.parseInt(sequenceVersion));
    }

    private static void parseProteinName(UniProtKBFastaBuilder builder, String line) {
        int spaceIndex = line.indexOf(" ");
        int organismNameIndex = line.indexOf(" OS=", spaceIndex);
        String proteinName = line.substring(spaceIndex + 1, organismNameIndex);
        if (proteinName.endsWith("(Fragment)")) {
            builder.flagType(FlagType.FRAGMENT);
            proteinName = proteinName.substring(0, proteinName.length() - 10);
        } else {
            builder.flagType(FlagType.PRECURSOR);
        }
        builder.proteinName(proteinName.trim());
    }

    private static Organism parseOrganism(String line) {
        int organismNameIndex = line.indexOf(" OS=");
        int organismIdIndex = line.indexOf(" OX=", organismNameIndex);
        int indexAfterOX = line.indexOf(" GN=", organismIdIndex);
        if (indexAfterOX < 0) {
            indexAfterOX = line.indexOf(" PE=", organismIdIndex);
        }

        OrganismBuilder organismBuilder = new OrganismBuilder();
        organismBuilder.scientificName(line.substring(organismNameIndex + 4, organismIdIndex));
        String taxId = line.substring(organismIdIndex + 4, indexAfterOX);
        organismBuilder.taxonId(Long.parseLong(taxId));
        return organismBuilder.build();
    }

    private static ProteinExistence parseProteinExistence(String proteinExistence) {
        ProteinExistence result;
        switch (proteinExistence) {
            case "1":
                result = ProteinExistence.PROTEIN_LEVEL;
                break;
            case "2":
                result = ProteinExistence.TRANSCRIPT_LEVEL;
                break;
            case "3":
                result = ProteinExistence.HOMOLOGY;
                break;
            case "4":
                result = ProteinExistence.PREDICTED;
                break;
            case "5":
                result = ProteinExistence.UNCERTAIN;
                break;
            default:
                result = ProteinExistence.UNKNOWN;
                break;
        }
        return result;
    }

    private static UniProtKBEntryType parseEntryType(String entryType) {
        UniProtKBEntryType result = UniProtKBEntryType.UNKNOWN;
        if (entryType.equals(">tr") || entryType.equals("tr")) {
            result = UniProtKBEntryType.TREMBL;
        } else if (entryType.equals(">sp") || entryType.equals("sp")) {
            result = UniProtKBEntryType.SWISSPROT;
        }
        return result;
    }

    private static void parseSequence(UniProtKBFastaBuilder builder, String[] sequenceLines) {
        String sequence = String.join("", sequenceLines);
        builder.sequence(sequence);
    }
}

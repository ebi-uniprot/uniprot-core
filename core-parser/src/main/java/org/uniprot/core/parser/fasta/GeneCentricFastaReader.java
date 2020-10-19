package org.uniprot.core.parser.fasta;

import org.uniprot.core.genecentric.Protein;
import org.uniprot.core.genecentric.impl.ProteinBuilder;
import org.uniprot.core.uniprotkb.ProteinExistence;
import org.uniprot.core.uniprotkb.UniProtKBEntryType;
import org.uniprot.core.uniprotkb.description.FlagType;
import org.uniprot.core.uniprotkb.taxonomy.Organism;
import org.uniprot.core.uniprotkb.taxonomy.impl.OrganismBuilder;

import static java.util.Arrays.copyOfRange;

/**
 * @author lgonzales
 * @since 17/10/2020
 */
public class GeneCentricFastaReader {

    private GeneCentricFastaReader(){

    }

    public static Protein parse(String fastaInput){
        ProteinBuilder builder = new ProteinBuilder();
        String[] lines = fastaInput.split("\n");

        parseFastaHeader(builder, lines[0]);
        parseSequence(builder, copyOfRange(lines,1,lines.length));

        return builder.build();
    }

    private static void parseFastaHeader(ProteinBuilder builder, String line) {
        int spaceIndex = line.indexOf(" ");
        int geneIndex = line.indexOf(" GN=");
        int proteinExistenceIndex = line.indexOf(" PE=");
        int sequenceVersionIndex = line.indexOf(" SV=");

        String[] proteinIds = line.substring(0, spaceIndex).split("\\|");
        builder.entryType(parseEntryType(proteinIds[0]));
        builder.accession(proteinIds[1]);
        builder.uniProtkbId(proteinIds[2]);

        parseProteinName(builder, line);

        builder.organism(parseOrganism(line));

        builder.geneName(line.substring(geneIndex + 4, proteinExistenceIndex));

        String proteinExistence = line.substring(proteinExistenceIndex +4, sequenceVersionIndex);
        builder.proteinExistence(parseProteinExistence(proteinExistence));

        String sequenceVersion = line.substring(sequenceVersionIndex+4);
        builder.sequenceVersion(Integer.parseInt(sequenceVersion));
    }

    private static void parseProteinName(ProteinBuilder builder, String line) {
        int organismNameIndex = line.indexOf(" OS=");
        int spaceIndex = line.indexOf(" ");
        String proteinName = line.substring(spaceIndex + 1, organismNameIndex);
        if(proteinName.endsWith("(Fragment)")){
            builder.flagType(FlagType.FRAGMENT);
            proteinName = proteinName.substring(0, proteinName.length() - 10);
        } else {
            builder.flagType(FlagType.PRECURSOR);
        }
        builder.proteinName(proteinName);
    }

    private static Organism parseOrganism(String line) {
        int organismIdIndex = line.indexOf(" OX=");
        int organismNameIndex = line.indexOf(" OS=");
        int geneIndex = line.indexOf(" GN=");

        OrganismBuilder organismBuilder = new OrganismBuilder();
        organismBuilder.scientificName(line.substring(organismNameIndex + 4, organismIdIndex));
        String taxId = line.substring(organismIdIndex + 4, geneIndex);
        organismBuilder.taxonId(Long.parseLong(taxId));
        return organismBuilder.build();
    }

    private static ProteinExistence parseProteinExistence(String proteinExistence) {
        ProteinExistence result;
        switch (proteinExistence){
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
        if(entryType.equals(">tr") || entryType.equals("tr")){
            result = UniProtKBEntryType.TREMBL;
        } else if(entryType.equals(">sp") || entryType.equals("sp")){
            result = UniProtKBEntryType.SWISSPROT;
        }
        return result;
    }

    private static void parseSequence(ProteinBuilder builder, String[] sequenceLines) {
        String sequence = String.join("", sequenceLines);
        builder.sequence(sequence);
    }
}

package org.uniprot.core.parser.fasta;

import org.uniprot.core.genecentric.Protein;
import org.uniprot.core.genecentric.impl.ProteinBuilder;
import org.uniprot.core.uniprotkb.ProteinExistence;
import org.uniprot.core.uniprotkb.UniProtKBEntryType;
import org.uniprot.core.uniprotkb.taxonomy.impl.OrganismBuilder;

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
        parseSequence(builder, lines);

        return builder.build();
    }

    private static void parseFastaHeader(ProteinBuilder builder, String line) {
        int spaceIndex = line.indexOf(" ");
        int organismNameIndex = line.indexOf(" OS=");
        int organismIdIndex = line.indexOf(" OX=");
        int geneIndex = line.indexOf(" GN=");
        int proteinExistenceIndex = line.indexOf(" PE=");
        int sequenceVersionIndex = line.indexOf(" SV=");

        String[] proteinIds = line.substring(0, spaceIndex).split("\\|");
        parseEntryType(builder, proteinIds[0]);
        builder.accession(proteinIds[1]);
        builder.uniProtkbId(proteinIds[2]);

        builder.proteinName(line.substring(spaceIndex + 1, organismNameIndex));

        OrganismBuilder organismBuilder = new OrganismBuilder();
        organismBuilder.scientificName(line.substring(organismNameIndex + 4, organismIdIndex));
        String taxId = line.substring(organismIdIndex + 4, geneIndex);
        organismBuilder.taxonId(Long.parseLong(taxId));
        builder.organism(organismBuilder.build());

        builder.geneName(line.substring(geneIndex + 4, proteinExistenceIndex));
        String proteinExistence = line.substring(proteinExistenceIndex +4, sequenceVersionIndex);
        builder.proteinExistence(parseProteinExistence(proteinExistence));
        String sequenceVersion = line.substring(sequenceVersionIndex+4);
        builder.sequenceVersion(Integer.parseInt(sequenceVersion));
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

    private static void parseEntryType(ProteinBuilder builder, String entryType) {
        if(entryType.equals(">tr") || entryType.equals("tr")){
            builder.entryType(UniProtKBEntryType.TREMBL);
        } else if(entryType.equals(">sp") || entryType.equals("sp")){
            builder.entryType(UniProtKBEntryType.SWISSPROT);
        } else {
            builder.entryType(UniProtKBEntryType.UNKNOWN);
        }
    }

    private static void parseSequence(ProteinBuilder builder, String[] sequenceLines) {
        //TODO: implement me
    }
}

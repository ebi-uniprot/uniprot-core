package uk.ac.ebi.uniprot.parser.fasta.uniprot;

import java.util.List;

import org.uniprot.core.gene.Gene;
import org.uniprot.core.uniprot.ProteinExistence;
import org.uniprot.core.uniprot.UniProtEntry;
import org.uniprot.core.uniprot.UniProtEntryType;
import org.uniprot.core.uniprot.description.Flag;
import org.uniprot.core.uniprot.description.FlagType;
import org.uniprot.core.uniprot.description.Name;
import org.uniprot.core.uniprot.description.ProteinDescription;

public class UniprotFastaParser {
    private final String header;
    private final String sequence;

    public static UniprotFastaParser create(UniProtEntry entry){

        StringBuilder sb = new StringBuilder();
        sb.append('>');
        if(entry.getEntryType() == UniProtEntryType.SWISSPROT) {
            sb.append("sp");
        }else {
            sb.append("tr");
        }
        sb.append('|');
        sb.append(entry.getPrimaryAccession().getValue());
        sb.append('|');
        sb.append(entry.getUniProtId().getValue());
        sb.append(' ').append(getDescriptionStr(entry.getProteinDescription()));
        sb.append(" OS=").append(entry.getOrganism().getScientificName());
        sb.append(" OX=").append(entry.getOrganism().getTaxonId());
        String geneStr = getGeneStr(entry.getGenes());

        if(geneStr !=null){
            sb.append(" GN=").append(geneStr);
        }
        sb.append(" PE=").append(getProteinExist(entry));
        sb.append(" SV=").append(entry.getEntryAudit().getSequenceVersion());

        return new UniprotFastaParser(sb.toString(), entry.getSequence().getValue());

    }

    private UniprotFastaParser(String header, String sequence){
        this.header = header;
        this.sequence = sequence;
    }

    private static String getProteinExist(UniProtEntry entry){
        ProteinExistence pe = entry.getProteinExistence();
        String peStr= "5";
        if(pe == ProteinExistence.PROTEIN_LEVEL)
            peStr ="1";
        else if(pe == ProteinExistence.TRANSCRIPT_LEVEL)
            peStr ="2";
        else if(pe == ProteinExistence.HOMOLOGY)
            peStr ="3";
        else if(pe == ProteinExistence.PREDICTED)
            peStr ="4";
        else if(pe == ProteinExistence.UNCERTAIN)
            peStr ="5";
        return peStr;
    }

    private static String getGeneStr(List<Gene> genes){
        String geneName = null;
        String orfName =null;
        String olnName = null;
        for(Gene gene: genes){
            if(gene.hasGeneName())
                geneName = gene.getGeneName().getValue();
            else if((gene.getOrderedLocusNames() !=null) &&(gene.getOrderedLocusNames().size()>0)){
                olnName =gene.getOrderedLocusNames().get(0).getValue();
            }
            else if((gene.getOrfNames() !=null) &&(gene.getOrfNames().size()>0)){
                orfName =gene.getOrfNames().get(0).getValue();
            }

        }
        if(geneName !=null)
            return geneName;
        else if(olnName !=null)
            return olnName;
        else
            return orfName;
    }
    private static String getDescriptionStr(ProteinDescription pd){
        StringBuilder  desc = new StringBuilder();
        Name name;
        if(pd.getRecommendedName()!= null){
            name = pd.getRecommendedName().getFullName();
        }else{
            name =pd.getSubmissionNames().get(0).getFullName();
        }
        desc.append(name.getValue());
        Flag flag = pd.getFlag();
        if(flag !=null && !flag.getType().equals(FlagType.PRECURSOR)){
            desc.append(" (Fragment)");
        }
        return desc.toString();
    }

    public String getHeader() {
        return header;
    }

    public String getSequence() {
        return sequence;
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(header).append("\n");
        int columnCounter = 0;
        for (char c : sequence.toCharArray()) {
            sb.append(c);
            if ((++columnCounter % 60 == 0) && (columnCounter < sequence.length())) {
                sb.append("\n");
            }
        }
        return sb.toString();
    }

}

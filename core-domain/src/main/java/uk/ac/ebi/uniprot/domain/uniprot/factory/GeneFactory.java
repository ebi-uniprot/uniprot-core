package uk.ac.ebi.uniprot.domain.uniprot.factory;

import java.util.List;

import uk.ac.ebi.uniprot.domain.gene.Gene;
import uk.ac.ebi.uniprot.domain.gene.GeneName;
import uk.ac.ebi.uniprot.domain.gene.GeneNameSynonym;
import uk.ac.ebi.uniprot.domain.gene.ORFName;
import uk.ac.ebi.uniprot.domain.gene.OrderedLocusName;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.impl.GeneImpl;
import uk.ac.ebi.uniprot.domain.uniprot.impl.GeneImpl.GeneNameImpl;
import uk.ac.ebi.uniprot.domain.uniprot.impl.GeneImpl.GeneNameSynonymImpl;
import uk.ac.ebi.uniprot.domain.uniprot.impl.GeneImpl.ORFNameImpl;
import uk.ac.ebi.uniprot.domain.uniprot.impl.GeneImpl.OrderedLocusNameImpl;

public enum GeneFactory {
    INSTANCE;
    public Gene createGene(GeneName geneName, List<GeneNameSynonym>  synonyms,
            List<OrderedLocusName> olnNames,
            List<ORFName> orfNames
            ){
    	
        return new GeneImpl(geneName,synonyms, olnNames, orfNames);
    }

    public GeneName createGeneName(String val,  List<Evidence> evidences){
      return new GeneNameImpl(val, evidences);
      
    }
    
    public GeneNameSynonym createGeneNameSynonym(String val,  List<Evidence> evidences){
        return new GeneNameSynonymImpl(val, evidences);
        
      }
      
    
    
    public OrderedLocusName createOrderedLocusName(String val,  List<Evidence> evidences){
        return new OrderedLocusNameImpl(val, evidences);
        
      }
    
    public ORFName createORFName(String val,  List<Evidence> evidences){
        return new ORFNameImpl(val, evidences);
        
      }
    
   
}

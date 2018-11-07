package uk.ac.ebi.uniprot.domain.uniprot.factory;

import uk.ac.ebi.uniprot.domain.gene.Gene;
import uk.ac.ebi.uniprot.domain.gene.GeneName;
import uk.ac.ebi.uniprot.domain.gene.GeneNameSynonym;
import uk.ac.ebi.uniprot.domain.gene.ORFName;
import uk.ac.ebi.uniprot.domain.gene.OrderedLocusName;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.impl.EvidencedValueImpl;
import uk.ac.ebi.uniprot.domain.uniprot.impl.GeneImpl;

import java.util.List;

public enum GeneFactory {
    INSTANCE;
    public Gene createGene(GeneName geneName, List<GeneNameSynonym>  synonyms,
            List<OrderedLocusName> olnNames,
            List<ORFName> orfNames
            ){
        return new GeneImpl(geneName, synonyms, olnNames, orfNames);
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
    
    private static class ORFNameImpl extends EvidencedValueImpl implements ORFName {
        public ORFNameImpl(String value, List<Evidence> evidences) {
            super(value, evidences);      
        }
    }
    
    
    private static class OrderedLocusNameImpl extends EvidencedValueImpl implements OrderedLocusName {
        public OrderedLocusNameImpl(String value, List<Evidence> evidences) {
            super(value, evidences);      
        }
    }
    
    private static class GeneNameSynonymImpl extends EvidencedValueImpl implements GeneNameSynonym {
        public GeneNameSynonymImpl(String value, List<Evidence> evidences) {
            super(value, evidences);      
        }
    }
    
    private static class GeneNameImpl extends EvidencedValueImpl implements GeneName {
        public GeneNameImpl(String value, List<Evidence> evidences) {
            super(value, evidences);      
        }
    }
}

package uk.ac.ebi.uniprot.domain.uniprot.factory;

import uk.ac.ebi.uniprot.domain.uniprot.EvidencedValue;
import uk.ac.ebi.uniprot.domain.uniprot.GeneEncodingType;
import uk.ac.ebi.uniprot.domain.uniprot.InternalLine;
import uk.ac.ebi.uniprot.domain.uniprot.InternalLineType;
import uk.ac.ebi.uniprot.domain.uniprot.InternalSection;
import uk.ac.ebi.uniprot.domain.uniprot.Organelle;
import uk.ac.ebi.uniprot.domain.uniprot.SourceLine;
import uk.ac.ebi.uniprot.domain.uniprot.UniProtAccession;
import uk.ac.ebi.uniprot.domain.uniprot.UniProtId;
import uk.ac.ebi.uniprot.domain.uniprot.evidences.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.impl.EvidencedValueImpl;
import uk.ac.ebi.uniprot.domain.uniprot.impl.InternalSectionImpl;
import uk.ac.ebi.uniprot.domain.uniprot.impl.OrganelleImpl;
import uk.ac.ebi.uniprot.domain.uniprot.impl.UniProtAccessionImpl;
import uk.ac.ebi.uniprot.domain.uniprot.impl.UniProtIdImpl;

import java.util.List;

public enum UniProtFactory {
    INSTANCE;
    public EvidenceFactory getEvidenceFactory(){
        return EvidenceFactory.INSTANCE;
    }
    
    public GeneFactory getGeneFactory(){
        return GeneFactory.INSTANCE;
    }
    
    public OrganismFactory getOrganismFactory(){
        return OrganismFactory.INSTANCE;
    }
    public ProteinDescriptionFactory getProteinDescriptionFactory(){
        return ProteinDescriptionFactory.INSTANCE;
    }
    
    public TaxonomyFactory getTaxonomyFactory(){
        return TaxonomyFactory.INSTANCE;
    }
    public UniProtDBCrossReferenceFactory getUniProtDBCrossReferenceFactory(){
        return UniProtDBCrossReferenceFactory.INSTANCE;
    }
    
    public CommentFactory getCommentFactory(){
        return CommentFactory.INSTANCE;
    }
    
    public FeatureFactory getFeatureFactory(){
        return FeatureFactory.INSTANCE;
    }
    
    
    public  EvidencedValue createEvidencedValue(String value, List<Evidence> evidences) {
        return new EvidencedValueImpl(value, evidences);
    }
    public  UniProtAccession createUniProtAccession(String value){
        return new UniProtAccessionImpl(value);
    }
    public  UniProtId createUniProtId(String value){
        return new UniProtIdImpl(value);
    }
    public Organelle createOrganelle(GeneEncodingType geneEncodingType, String value, List<Evidence> evidences){
        return new OrganelleImpl( geneEncodingType,  value, evidences);
    }
    public InternalSection createInternalSection(List<InternalLine> internalLines, List<SourceLine> sourceLines){
        return new InternalSectionImpl(internalLines, sourceLines);
    }
    public InternalLine createInternalLine(InternalLineType type, String value) {
        return InternalSectionImpl.createInternalLine(type, value);
    }
    
    public  SourceLine createSourceLine(String value){
        return InternalSectionImpl.createSourceLine(value);
    }
}

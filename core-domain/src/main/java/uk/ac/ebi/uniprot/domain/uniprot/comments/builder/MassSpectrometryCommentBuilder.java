package uk.ac.ebi.uniprot.domain.uniprot.comments.builder;

import uk.ac.ebi.uniprot.domain.uniprot.comments.MassSpectrometryComment;
import uk.ac.ebi.uniprot.domain.uniprot.comments.MassSpectrometryMethod;
import uk.ac.ebi.uniprot.domain.uniprot.comments.MassSpectrometryRange;
import uk.ac.ebi.uniprot.domain.uniprot.comments.impl.MassSpectrometryCommentImpl;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;

import java.util.List;

public final class MassSpectrometryCommentBuilder implements CommentBuilder<MassSpectrometryComment>{
    private  MassSpectrometryMethod method;
    private  Double molWeight;
    private  Double molWeightError;
    private  String note;
    private  List<MassSpectrometryRange> ranges;
    private  List<Evidence> evidences;
 
    public static MassSpectrometryCommentBuilder newInstance(){
        return new MassSpectrometryCommentBuilder();
    }
    public MassSpectrometryComment build(){
        return new  MassSpectrometryCommentImpl( method,
                 molWeight,  molWeightError,  note,
                 ranges, evidences);
    }
    public MassSpectrometryCommentBuilder massSpectrometryMethod(MassSpectrometryMethod method){
        this.method = method;
        return this; 
    }
    public MassSpectrometryCommentBuilder molWeight(Double molWeight){
        this.molWeight = molWeight;
        return this; 
    }
    public MassSpectrometryCommentBuilder molWeightError(Double molWeightError){
    		if((molWeightError !=null) &&(Math.abs(molWeightError-0.0)<= Double.MIN_VALUE)){
    			this.molWeightError =null;
    		}
        this.molWeightError = molWeightError;
        return this; 
    }
    public MassSpectrometryCommentBuilder note(String note){
        this.note = note;
        return this; 
    }
    public MassSpectrometryCommentBuilder massSpectrometryRanges(List<MassSpectrometryRange> ranges){
        this.ranges = ranges;
        return this; 
    }
    public MassSpectrometryCommentBuilder evidences(List<Evidence> evidences){
        this.evidences = evidences;
        return this; 
    }
    public static MassSpectrometryRange createMassSpectrometryRange(Integer start, Integer end, String isoformId){
        return MassSpectrometryCommentImpl.buildMassSpectrometryRange( start,  end,  isoformId);
    }
}

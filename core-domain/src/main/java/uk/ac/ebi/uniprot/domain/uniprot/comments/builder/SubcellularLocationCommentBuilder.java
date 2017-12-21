package uk.ac.ebi.uniprot.domain.uniprot.comments.builder;

import uk.ac.ebi.uniprot.domain.uniprot.comments.CommentNote;
import uk.ac.ebi.uniprot.domain.uniprot.comments.SubcellularLocation;
import uk.ac.ebi.uniprot.domain.uniprot.comments.SubcellularLocationComment;
import uk.ac.ebi.uniprot.domain.uniprot.comments.SubcellularLocationValue;
import uk.ac.ebi.uniprot.domain.uniprot.comments.impl.SubcellularLocationCommentImpl;
import uk.ac.ebi.uniprot.domain.uniprot.comments.impl.SubcellularLocationImpl;
import uk.ac.ebi.uniprot.domain.uniprot.evidences.Evidence;

import java.util.List;

public final class SubcellularLocationCommentBuilder {
    private  String molecule;
    private  List<SubcellularLocation>  subcellularLocations;
    private  CommentNote note;
    
    public SubcellularLocationComment build(){
        return  new SubcellularLocationCommentImpl( molecule, 
                 subcellularLocations,  note);
    }
    public SubcellularLocationCommentBuilder setMolecule(String molecule){
        this.molecule = molecule;
        return this;
    }
    
    public SubcellularLocationCommentBuilder setSubcellularLocations(List<SubcellularLocation>  subcellularLocations){
        this.subcellularLocations = subcellularLocations;
        return this;
    }
    
    public SubcellularLocationCommentBuilder setNote(CommentNote note){
        this.note = note;
        return this;
    }
    
    public static SubcellularLocationValue createSubcellularLocationValue(String value, List<Evidence> evidences){
        return SubcellularLocationImpl.createSubcellularLocationValue(value, evidences);
    }
    public static SubcellularLocation createSubcellularLocation(SubcellularLocationValue location,
            SubcellularLocationValue topology,
            SubcellularLocationValue orientation){
        return new SubcellularLocationImpl( location, topology, orientation);
    }
}

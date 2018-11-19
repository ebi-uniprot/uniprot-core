package uk.ac.ebi.uniprot.domain.uniprot.comment.impl;

import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Strings;

import uk.ac.ebi.uniprot.domain.uniprot.comment.Cofactor;
import uk.ac.ebi.uniprot.domain.uniprot.comment.CofactorComment;
import uk.ac.ebi.uniprot.domain.uniprot.comment.CommentType;
import uk.ac.ebi.uniprot.domain.uniprot.comment.Note;
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class CofactorCommentImpl extends CommentImpl implements CofactorComment {
    private final String molecule;
    private final  List<Cofactor> cofactors;
    private final Note note;
	@JsonCreator
    public CofactorCommentImpl(@JsonProperty("molecule") String molecule, 
    		@JsonProperty("cofactors")List<Cofactor> cofactors, 
    		@JsonProperty("note") Note note) {
        super(CommentType.COFACTOR);
        if(Strings.isNullOrEmpty(molecule))
        	this.molecule =null;
        else
        	this.molecule =molecule;
        if((cofactors ==null) || cofactors.isEmpty()){
            this.cofactors = Collections.emptyList();
        }else{
            this.cofactors =Collections.unmodifiableList(cofactors);
        }
        this.note =note;
    }

    @Override
    public String getMolecule() {
        return molecule;
    }

    @Override
    public List<Cofactor> getCofactors() {
        return cofactors;
    }

    @Override
    public Note getNote() {
        return note;
    }
	@JsonIgnore
    @Override
	public boolean isValid() {
		return !getCofactors().isEmpty() || (note !=null);
	}


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((cofactors == null) ? 0 : cofactors.hashCode());
        result = prime * result + ((molecule == null) ? 0 : molecule.hashCode());
        result = prime * result + ((note == null) ? 0 : note.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        CofactorCommentImpl other = (CofactorCommentImpl) obj;
        if (cofactors == null) {
            if (other.cofactors != null)
                return false;
        } else if (!cofactors.equals(other.cofactors))
            return false;
        if (molecule == null) {
            if (other.molecule != null)
                return false;
        } else if (!molecule.equals(other.molecule))
            return false;
        if (note == null) {
            if (other.note != null)
                return false;
        } else if (!note.equals(other.note))
            return false;
        return true;
    }

	
}

package uk.ac.ebi.uniprot.domain.uniprot.comment.impl;

import uk.ac.ebi.uniprot.domain.uniprot.comment.CommentType;
import uk.ac.ebi.uniprot.domain.uniprot.comment.MassSpectrometryComment;
import uk.ac.ebi.uniprot.domain.uniprot.comment.MassSpectrometryMethod;
import uk.ac.ebi.uniprot.domain.uniprot.comment.MassSpectrometryRange;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MassSpectrometryCommentImpl extends CommentImpl implements MassSpectrometryComment {
    private MassSpectrometryMethod method;
    private Double molWeight;
    private Double molWeightError;
    private String note;
    private List<MassSpectrometryRange> ranges;
    private List<Evidence> evidences;

    private MassSpectrometryCommentImpl(){
        super(CommentType.MASS_SPECTROMETRY);
        this.ranges = Collections.emptyList();
        this.evidences = Collections.emptyList();
    }

    public MassSpectrometryCommentImpl(MassSpectrometryMethod method, Double molWeight,
    		Double molWeightError, String note, List<MassSpectrometryRange> ranges,
    		List<Evidence> evidences) {
        super(CommentType.MASS_SPECTROMETRY);
        this.method = method;
        this.molWeight = molWeight;
        
        this.molWeightError =  molWeightError;
        this.note = note ;
        if ((ranges == null) || ranges.isEmpty()) {
            this.ranges = Collections.emptyList();
        } else {
            this.ranges = Collections.unmodifiableList(ranges);
        }
        if ((evidences == null) || evidences.isEmpty()) {
            this.evidences = Collections.emptyList();
        } else {
            this.evidences = Collections.unmodifiableList(evidences);
        }
    }

    @Override
    public Double getMolWeightError() {
        return molWeightError;
    }

    @Override
    public Double getMolWeight() {
        return molWeight;
    }

    @Override
    public String getNote() {
        return note;
    }

    @Override
    public List<MassSpectrometryRange> getRanges() {
        return ranges;
    }

    @Override
    public MassSpectrometryMethod getMethod() {
        return method;
    }

    @Override
    public List<Evidence> getEvidences() {
        return evidences;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((evidences == null) ? 0 : evidences.hashCode());
        result = prime * result + ((method == null) ? 0 : method.hashCode());
        result = prime * result + ((molWeight == null) ? 0 : molWeight.hashCode());
        result = prime * result + ((molWeightError == null) ? 0 : molWeightError.hashCode());
        result = prime * result + ((note == null) ? 0 : note.hashCode());
        result = prime * result + ((ranges == null) ? 0 : ranges.hashCode());
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
        MassSpectrometryCommentImpl other = (MassSpectrometryCommentImpl) obj;
        if (evidences == null) {
            if (other.evidences != null)
                return false;
        } else if (!evidences.equals(other.evidences))
            return false;
        if (method != other.method)
            return false;
        if (molWeight == null) {
            if (other.molWeight != null)
                return false;
        } else if (!molWeight.equals(other.molWeight))
            return false;
        if (molWeightError == null) {
            if (other.molWeightError != null)
                return false;
        } else if (!molWeightError.equals(other.molWeightError))
            return false;
        if (note == null) {
            if (other.note != null)
                return false;
        } else if (!note.equals(other.note))
            return false;
        if (ranges == null) {
            if (other.ranges != null)
                return false;
        } else if (!ranges.equals(other.ranges))
            return false;
        return true;
    }

   
}

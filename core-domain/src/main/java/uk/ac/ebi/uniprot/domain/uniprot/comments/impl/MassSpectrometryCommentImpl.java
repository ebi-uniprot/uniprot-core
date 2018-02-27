package uk.ac.ebi.uniprot.domain.uniprot.comments.impl;

import uk.ac.ebi.uniprot.domain.uniprot.comments.CommentType;
import uk.ac.ebi.uniprot.domain.uniprot.comments.MassSpectrometryComment;
import uk.ac.ebi.uniprot.domain.uniprot.comments.MassSpectrometryMethod;
import uk.ac.ebi.uniprot.domain.uniprot.comments.MassSpectrometryRange;
import uk.ac.ebi.uniprot.domain.uniprot.evidences.Evidence;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class MassSpectrometryCommentImpl extends CommentImpl implements MassSpectrometryComment {
    public static MassSpectrometryRange buildMassSpectrometryRange(Integer start, Integer end, String isoformId){
        return new MassSpectrometryRangeImpl( start,  end,  isoformId);
    }
    private final MassSpectrometryMethod method;
    private final Double molWeight;
    private final Optional<Double> molWeightError;
    private final Optional<String> note;
    private final List<MassSpectrometryRange> ranges;
    private final List<Evidence> evidences;

    public MassSpectrometryCommentImpl(MassSpectrometryMethod method,
        Double molWeight, Double molWeightError, String note,
        List<MassSpectrometryRange> ranges,
        List<Evidence> evidences) {
        super(CommentType.MASS_SPECTROMETRY);
        this.method = method;
        this.molWeight = molWeight;
        
        this.molWeightError =  (molWeightError ==null)? Optional.empty() : Optional.of(molWeightError);
        this.note = ((note ==null )|| note.isEmpty())? Optional.empty() : Optional.of(note);
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
    public Optional<Double> getMolWeightError() {
        return molWeightError;
    }

    @Override
    public Double getMolWeight() {
        return molWeight;
    }

    @Override
    public Optional<String> getNote() {
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

    static class MassSpectrometryRangeImpl implements MassSpectrometryRange {
        private final Integer start;
        private final Integer end;
        private final String isoformId;
        public MassSpectrometryRangeImpl(Integer start, Integer end, String isoformId){
            this.start = start;
            this.end =end;
            this.isoformId = isoformId;
        }
        @Override
        public Integer getStart() {
            return start;
        }

        @Override
        public boolean isStartUnknown() {
           return (start ==null) || (start ==-1);
        }

        @Override
        public Integer getEnd() {
            return end;
        }

        @Override
        public boolean isEndUnknown() {
            return (end == null) ||(end == -1);
        }

        @Override
        public boolean hasIsoformId() {
            return ((isoformId !=null) && !isoformId.isEmpty());
        }

        @Override
        public String getIsoformId() {
            return isoformId;
        }
        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + ((end == null) ? 0 : end.hashCode());
            result = prime * result + ((isoformId == null) ? 0 : isoformId.hashCode());
            result = prime * result + ((start == null) ? 0 : start.hashCode());
            return result;
        }
        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            MassSpectrometryRangeImpl other = (MassSpectrometryRangeImpl) obj;
            if (end == null) {
                if (other.end != null)
                    return false;
            } else if (!end.equals(other.end))
                return false;
            if (isoformId == null) {
                if (other.isoformId != null)
                    return false;
            } else if (!isoformId.equals(other.isoformId))
                return false;
            if (start == null) {
                if (other.start != null)
                    return false;
            } else if (!start.equals(other.start))
                return false;
            return true;
        }

    }

}

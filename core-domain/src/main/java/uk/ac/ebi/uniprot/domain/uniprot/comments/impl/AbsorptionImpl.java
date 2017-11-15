package uk.ac.ebi.uniprot.domain.uniprot.comments.impl;

import uk.ac.ebi.uniprot.domain.uniprot.comments.Absorption;
import uk.ac.ebi.uniprot.domain.uniprot.evidences.Evidence;

import java.util.ArrayList;
import java.util.List;

public class AbsorptionImpl implements Absorption {
    private int max;
    private String absorptionNote;
    private boolean approximate;
    private List<Evidence> evidenceIds;

    public AbsorptionImpl() {
        this.absorptionNote = "";
    }

    public List<Evidence> getEvidenceIds() {
        if (evidenceIds == null)
            evidenceIds = new ArrayList<>();
        return evidenceIds;
    }

    public void setEvidenceIds(List<Evidence> evidenceIds) {
        this.evidenceIds = evidenceIds;
    }

    public void setMax(int max) {
        this.max = max;
    }

    @Override
    public int getMax() {
        return this.max;
    }

    public void setNote(String note) {
        this.absorptionNote = note;
    }

    @Override
    public String getNote() {
        return this.absorptionNote;
    }

    @Override
    public boolean hasNote() {
        if (this.absorptionNote == null
                || this.absorptionNote.equals("")) {
            return false;
        }
        return true;
    }

    public void setApproximation(boolean isApproximate) {
        this.approximate = isApproximate;
    }

    @Override
    public boolean isApproximation() {
        return this.approximate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        AbsorptionImpl that = (AbsorptionImpl) o;

        if (approximate != that.approximate)
            return false;
        if (max != that.max)
            return false;
        if (absorptionNote != null ? !absorptionNote
                .equals(that.absorptionNote) : that.absorptionNote != null)
            return false;
        if (evidenceIds != null && evidenceIds.size() > 0 ? !evidenceIds
                .equals(that.evidenceIds) : that.evidenceIds != null
                        && that.evidenceIds.size() > 0)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        result = max;
        result = 31 * result
                + (absorptionNote != null ? absorptionNote.hashCode() : 0);
        result = 31 * result + (approximate ? 1 : 0);
        result = 31
                * result
                + (evidenceIds != null && evidenceIds.size() > 0 ? evidenceIds
                        .hashCode() : 0);
        return result;
    }

    @Override
    public List<Evidence> getEvidences() {
        // TODO Auto-generated method stub
        return null;
    }

}

package uk.ac.ebi.uniprot.domain.uniprot.comments.impl;

import uk.ac.ebi.uniprot.domain.uniprot.comments.Disease;
import uk.ac.ebi.uniprot.domain.uniprot.comments.DiseaseDescription;
import uk.ac.ebi.uniprot.domain.uniprot.comments.DiseaseId;
import uk.ac.ebi.uniprot.domain.uniprot.comments.DiseaseReference;
import uk.ac.ebi.uniprot.domain.uniprot.comments.DiseaseReferenceType;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.impl.ValueImpl;

import java.util.List;

import uk.ac.ebi.uniprot.domain.uniprot.impl.EvidencedValueImpl;

public class DiseaseImpl implements Disease {
    public static DiseaseId createDiseaseId(String val){
        return new DiseaseIdImpl(val);
    }
    public static DiseaseReference createDiseaseReference(DiseaseReferenceType referenceType, String referenceId){
        return new DiseaseReferenceImpl(referenceType, referenceId);
    }
    
    public static DiseaseDescription createDiseaseDescription(String val, List<Evidence> evidences){
        return new DiseaseDescriptionImpl(val, evidences);
    }
    
    private final DiseaseId diseaseId;
    private final String acronym;
    private final DiseaseDescription description;
    private final DiseaseReference reference;
    public DiseaseImpl(DiseaseId diseaseId, String acronym, DiseaseDescription description,
            DiseaseReference reference){
        this.diseaseId =diseaseId;
        this.acronym = acronym;
        this.description = description;
        this.reference = reference;
    }
    @Override
    public DiseaseId getDiseaseId() {
       return diseaseId;
    }

    @Override
    public String getAcronym() {
        return acronym;
    }

    @Override
    public DiseaseDescription getDescription() {
        return description;
    }

    @Override
    public DiseaseReference getReference() {
       return reference;
    }
    @Override
    public boolean hasDefinedDisease() {
        return (getDiseaseId() != null &&  getDiseaseId().getValue() != null && ! getDiseaseId().getValue().isEmpty()) &&  
                (getAcronym() != null && !getAcronym().isEmpty()) &&
                (getDescription() != null && getDescription().getValue() != null && !getDescription().getValue().isEmpty()) &&
                (getReference() != null && getReference().getDiseaseReferenceId() != null  && 
                !getReference().getDiseaseReferenceId().isEmpty() && getReference().getDiseaseReferenceType() != null &&
                !getReference().getDiseaseReferenceType().equals(DiseaseReferenceType.NONE));
    }
    
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((acronym == null) ? 0 : acronym.hashCode());
        result = prime * result + ((description == null) ? 0 : description.hashCode());
        result = prime * result + ((diseaseId == null) ? 0 : diseaseId.hashCode());
        result = prime * result + ((reference == null) ? 0 : reference.hashCode());
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
        DiseaseImpl other = (DiseaseImpl) obj;
        if (acronym == null) {
            if (other.acronym != null)
                return false;
        } else if (!acronym.equals(other.acronym))
            return false;
        if (description == null) {
            if (other.description != null)
                return false;
        } else if (!description.equals(other.description))
            return false;
        if (diseaseId == null) {
            if (other.diseaseId != null)
                return false;
        } else if (!diseaseId.equals(other.diseaseId))
            return false;
        if (reference == null) {
            if (other.reference != null)
                return false;
        } else if (!reference.equals(other.reference))
            return false;
        return true;
    }



    static class DiseaseIdImpl extends ValueImpl implements DiseaseId {

        public DiseaseIdImpl(String value) {
            super(value);
        }  
    }
    static class DiseaseDescriptionImpl extends EvidencedValueImpl implements DiseaseDescription {

        public DiseaseDescriptionImpl(String value, List<Evidence> evidences) {
            super(value, evidences);

        }
        
    }
    static class DiseaseReferenceImpl implements DiseaseReference {
        private final DiseaseReferenceType type;
        private final String id;

        public DiseaseReferenceImpl(DiseaseReferenceType type, String id){
            this.type =type;
            this.id = id;
           
        }
        @Override
        public String getDiseaseReferenceId() {
            return id;
        }

        @Override
        public DiseaseReferenceType getDiseaseReferenceType() {
            return type;
        }
        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + ((id == null) ? 0 : id.hashCode());
            result = prime * result + ((type == null) ? 0 : type.hashCode());
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
            DiseaseReferenceImpl other = (DiseaseReferenceImpl) obj;
            if (id == null) {
                if (other.id != null)
                    return false;
            } else if (!id.equals(other.id))
                return false;
            if (type != other.type)
                return false;
            return true;
        }
        
    }
  
}

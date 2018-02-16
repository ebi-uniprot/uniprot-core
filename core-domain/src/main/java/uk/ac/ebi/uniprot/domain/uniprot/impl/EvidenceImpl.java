package uk.ac.ebi.uniprot.domain.uniprot.impl;

import uk.ac.ebi.uniprot.domain.uniprot.evidences.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.evidences.EvidenceCode;
import uk.ac.ebi.uniprot.domain.uniprot.evidences.EvidenceType;

import java.time.LocalDate;


public class EvidenceImpl implements Evidence {
    private static final String PIPE = "|";
    private static final String COLON = ":";
    private final EvidenceType evidenceType;
    private final EvidenceCode evidenceCode;
    private final String value;

    private final String attribute;


    public EvidenceImpl(EvidenceType evidenceType, EvidenceCode evidenceCode,
            String attribute){
        this.evidenceType = evidenceType;
        this.evidenceCode = evidenceCode;
        this.attribute = attribute;
        value = null;
    }
    
    public EvidenceImpl(EvidenceType evidenceType, EvidenceCode evidenceCode,
            String attribute, String value){
        this.evidenceType = evidenceType;
        this.evidenceCode = evidenceCode;
        this.attribute = attribute;
        this.value =value;
    }
    
    @Override
    public int compareTo(Evidence o) {
        return this.getValue().compareTo(o.getValue());
    }


    @Override
    public EvidenceType getType() {
       return this.evidenceType;
    }

    @Override
    public EvidenceCode getEvidenceCode() {
        return this.evidenceCode;
    }


    @Override
    public String getAttribute() {
        return this.attribute;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((attribute == null) ? 0 : attribute.hashCode());
        result = prime * result + ((evidenceCode == null) ? 0 : evidenceCode.hashCode());
        result = prime * result + ((evidenceType == null) ? 0 : evidenceType.hashCode());
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
        EvidenceImpl other = (EvidenceImpl) obj;
        if (attribute == null) {
            if (other.attribute != null)
                return false;
        } else if (!attribute.equals(other.attribute))
            return false;
        if (evidenceCode != other.evidenceCode)
            return false;
        if (evidenceType != other.evidenceType)
            return false;
        return true;
    }

    @Override
    public String toString() {
       return getValue();
    }

    @Override
    public String getValue() {
    	if((value !=null) && !value.isEmpty()) {
    		return value;
    	}
        StringBuilder sb = new StringBuilder();
        sb.append(evidenceCode.getCodeValue());
        if((attribute !=null) && !attribute.isEmpty()){
            sb.append(PIPE).append(evidenceType.getValue())
            .append(COLON).append(attribute);
        }
       return sb.toString();
    }

}

package uk.ac.ebi.uniprot.domain.impl;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import uk.ac.ebi.uniprot.domain.MoleculeWeight;
import uk.ac.ebi.uniprot.domain.Sequence;
import uk.ac.ebi.uniprot.domain.util.MessageDigestUtil;
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class SequenceImpl implements Sequence {
    private final String value;
    private final int length;
    private final int molWeight;
    private final String crc64;
    private final String md5;
	@JsonCreator
    public SequenceImpl(@JsonProperty("value") String sequence){
        if(sequence ==null){
            this.value ="";
        }else
            this.value = sequence;
        this.length = this.value.length();
        this.molWeight =MoleculeWeight.calcMolecularWeight(this.value);
        this.crc64  = MessageDigestUtil.getCrc64(value);
        this.md5 =MessageDigestUtil.getDigest(value, "MD5");
    }
   
    @Override
    public int getLength() {
        return length;
    }

    @Override
    public int getMolWeight() {
        return this.molWeight;
    }
    
    @Override
    public String getCrc64() {
       return this.crc64;
    }

    @Override
    public String getMd5() {
        return md5;
    }

    @Override
    public String getValue() {
       return value;
    }

    @Override
    public Sequence subSequence(int start, int end) {
        String subsequence = this.value.substring(start, end);
         return new SequenceImpl(subsequence);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((value == null) ? 0 : value.hashCode());
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
        SequenceImpl other = (SequenceImpl) obj;
        if (value == null) {
            if (other.value != null)
                return false;
        } else if (!value.equals(other.value))
            return false;
        return true;
    }
   
}

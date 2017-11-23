package uk.ac.ebi.uniprot.domain.uniprot.impl;

import uk.ac.ebi.uniprot.domain.common.MoleculeWeight;
import uk.ac.ebi.uniprot.domain.common.Sequence;
import uk.ac.ebi.uniprot.util.Crc64;
import uk.ac.ebi.uniprot.util.MessageDigestUtil;

public class SequenceImpl implements Sequence {
    private final String sequence;
    public SequenceImpl(String sequence){
        if(sequence ==null){
            this.sequence ="";
        }else
            this.sequence = sequence;
    }
   
    @Override
    public int getLength() {
        return sequence.length();
    }

    @Override
    public int getMolecularWeight() {
        return MoleculeWeight.calcMolecularWeight(this.sequence);
    }
    
    @Override
    public String getCRC64() {
       return Crc64.getCrc64(sequence);
    }

    @Override
    public String getMD5() {
        return MessageDigestUtil.getMD5(sequence);
    }

    @Override
    public String getValue() {
       return sequence;
    }

    @Override
    public Sequence subSequence(int start, int end) {
        String subsequence = this.sequence.substring(start, end);
         return new SequenceImpl(subsequence);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((sequence == null) ? 0 : sequence.hashCode());
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
        if (sequence == null) {
            if (other.sequence != null)
                return false;
        } else if (!sequence.equals(other.sequence))
            return false;
        return true;
    }
   
}

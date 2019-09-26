package org.uniprot.core.impl;

import org.uniprot.core.MoleculeWeight;
import org.uniprot.core.Sequence;
import org.uniprot.core.util.Crc64;
import org.uniprot.core.util.MessageDigestUtil;

public class SequenceImpl implements Sequence {
    private static final long serialVersionUID = 8906599014658129082L;
    private String value;
    private int length;
    private int molWeight;
    private String crc64;
    private String md5;

    private SequenceImpl() {
        this.value = "";
    }

    public SequenceImpl(String sequence) {
        if (sequence == null) {
            this.value = "";
        } else
            this.value = sequence;
        this.length = this.value.length();
        this.molWeight = MoleculeWeight.calcMolecularWeight(this.value);
        this.crc64 = Crc64.getCrc64(value);
        this.md5 = MessageDigestUtil.getMD5(value);
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
            return other.value == null;
        } else return value.equals(other.value);
    }

}

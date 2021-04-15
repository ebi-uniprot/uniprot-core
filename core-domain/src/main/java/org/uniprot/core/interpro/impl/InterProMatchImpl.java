package org.uniprot.core.interpro.impl;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.uniprot.core.interpro.InterProAc;
import org.uniprot.core.interpro.InterProMatch;
import org.uniprot.core.interpro.MethodType;
import org.uniprot.core.interpro.Status;
import org.uniprot.core.uniprotkb.UniProtKBAccession;
import org.uniprot.core.util.Utils;

/**
 * @author jluo
 * @date: 9 Apr 2021
 */
public class InterProMatchImpl implements InterProMatch {

    /** */
    private static final long serialVersionUID = -4430582017753889344L;

    private Integer fromPos;
    private Integer toPos;
    private Double score;
    private List<InterProAc> interProAcs;
    private MethodType methodType;
    private String methodName;
    private String methodAccession;
    private Status status;
    private UniProtKBAccession uniprotKBAccession;

    InterProMatchImpl() {
        interProAcs = Collections.emptyList();
    }

    InterProMatchImpl(
            Integer fromPos,
            Integer toPos,
            Double score,
            List<InterProAc> interProAcs,
            MethodType methodType,
            String methodName,
            String methodAccession,
            Status status,
            UniProtKBAccession uniprotKBAccession) {
        this.fromPos = fromPos;
        this.toPos = toPos;
        this.score = score;
        this.methodType = methodType;
        this.interProAcs = Utils.unmodifiableList(interProAcs);
        this.methodName = methodName;
        this.methodAccession = methodAccession;
        this.status = status;
        this.uniprotKBAccession = uniprotKBAccession;
    }

    @Override
    public Integer getFromPos() {
        return fromPos;
    }

    @Override
    public Integer getToPos() {
        return toPos;
    }

    @Override
    public Double getScore() {
        return score;
    }

    @Override
    public MethodType getMethodType() {
        return methodType;
    }

    @Override
    public List<InterProAc> getInterProAcs() {
        return interProAcs;
    }

    @Override
    public String getMethodName() {
        return methodName;
    }

    @Override
    public Status getStatus() {
        return status;
    }

    @Override
    public String getMethodAccession() {
        return methodAccession;
    }

    @Override
    public UniProtKBAccession getUniProtKBAccession() {
        return uniprotKBAccession;
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                fromPos,
                interProAcs,
                methodAccession,
                methodName,
                methodType,
                score,
                status,
                toPos,
                uniprotKBAccession);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        InterProMatchImpl other = (InterProMatchImpl) obj;
        return Objects.equals(fromPos, other.fromPos)
                && Objects.equals(interProAcs, other.interProAcs)
                && Objects.equals(methodAccession, other.methodAccession)
                && Objects.equals(methodName, other.methodName)
                && Objects.equals(methodType, other.methodType)
                && Objects.equals(score, other.score)
                && Objects.equals(status, other.status)
                && Objects.equals(toPos, other.toPos)
                && Objects.equals(uniprotKBAccession, other.uniprotKBAccession);
    }
}

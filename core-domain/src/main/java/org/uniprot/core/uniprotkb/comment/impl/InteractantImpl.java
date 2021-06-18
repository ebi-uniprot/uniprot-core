package org.uniprot.core.uniprotkb.comment.impl;

import java.util.Objects;

import org.uniprot.core.uniprotkb.UniProtKBAccession;
import org.uniprot.core.uniprotkb.comment.Interactant;

/**
 * @author jluo
 * @date: 17 Mar 2020
 */
public class InteractantImpl implements Interactant {

    private static final long serialVersionUID = 4480365784795150723L;

    private UniProtKBAccession uniProtKBAccession;
    private String geneName;
    private String chainId;
    private String intActId;

    InteractantImpl() {}

    InteractantImpl(
            UniProtKBAccession uniProtKBAccession,
            String geneName,
            String chainId,
            String intActId) {
        super();
        this.uniProtKBAccession = uniProtKBAccession;
        this.geneName = geneName;
        this.chainId = chainId;
        this.intActId = intActId;
    }

    @Override
    public String getChainId() {
        return this.chainId;
    }

    @Override
    public UniProtKBAccession getUniProtKBAccession() {
        return uniProtKBAccession;
    }

    @Override
    public String getIntActId() {
        return intActId;
    }

    @Override
    public String getGeneName() {
        return geneName;
    }

    @Override
    public int hashCode() {
        return Objects.hash(uniProtKBAccession, geneName, chainId, intActId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InteractantImpl interactor = (InteractantImpl) o;
        return Objects.equals(uniProtKBAccession, interactor.uniProtKBAccession)
                && Objects.equals(geneName, interactor.geneName)
                && Objects.equals(chainId, interactor.chainId)
                && Objects.equals(intActId, interactor.intActId);
    }
}

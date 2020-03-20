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

    private UniProtKBAccession uniProtkbAccession;
    private String geneName;
    private String chainId;
    private String intActId;

    InteractantImpl() {}

    InteractantImpl(
            UniProtKBAccession uniProtkbAccession,
            String geneName,
            String chainId,
            String intActId) {
        super();
        this.uniProtkbAccession = uniProtkbAccession;
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
        return uniProtkbAccession;
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
        return Objects.hash(uniProtkbAccession, geneName, chainId, intActId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InteractantImpl interactor = (InteractantImpl) o;
        return Objects.equals(uniProtkbAccession, interactor.uniProtkbAccession)
                && Objects.equals(geneName, interactor.geneName)
                && Objects.equals(chainId, interactor.chainId)
                && Objects.equals(intActId, interactor.intActId);
    }
}

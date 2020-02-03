package org.uniprot.core.flatfile.parser.impl.cc.cclineobject;

public class InteractionObject {
    private boolean isSelf;
    private String spAc;
    private String gene;
    private boolean xeno;
    private int nbexp;
    private String firstId;
    private String secondId;

    public boolean isSelf() {
        return isSelf;
    }

    public void setSelf(boolean self) {
        isSelf = self;
    }

    public String getSpAc() {
        return spAc;
    }

    public void setSpAc(String spAc) {
        this.spAc = spAc;
    }

    public String getGene() {
        return gene;
    }

    public void setGene(String gene) {
        this.gene = gene;
    }

    public boolean isXeno() {
        return xeno;
    }

    public void setXeno(boolean xeno) {
        this.xeno = xeno;
    }

    public int getNbexp() {
        return nbexp;
    }

    public void setNbexp(int nbexp) {
        this.nbexp = nbexp;
    }

    public String getFirstId() {
        return firstId;
    }

    public void setFirstId(String firstId) {
        this.firstId = firstId;
    }

    public String getSecondId() {
        return secondId;
    }

    public void setSecondId(String secondId) {
        this.secondId = secondId;
    }
}

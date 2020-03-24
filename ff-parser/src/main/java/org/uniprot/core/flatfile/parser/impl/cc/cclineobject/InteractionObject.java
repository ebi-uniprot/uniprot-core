package org.uniprot.core.flatfile.parser.impl.cc.cclineobject;

public class InteractionObject {
    private String firstInteractant;
    private String secondInteractant;
    private String secondInteractantParent;
    private String gene;
    private boolean xeno;
    private int nbexp;
    private String firstId;
    private String secondId;

    public String getFirstInteractant() {
        return firstInteractant;
    }

    public void setFirstInteractant(String firstInteractant) {
        this.firstInteractant = firstInteractant;
    }

    public String getSecondInteractant() {
        return secondInteractant;
    }

    public void setSecondInteractant(String secondInteractant) {
        this.secondInteractant = secondInteractant;
    }

    public String getSecondInteractantParent() {
        return secondInteractantParent;
    }

    public void setSecondInteractantParent(String secondInteractantParent) {
        this.secondInteractantParent = secondInteractantParent;
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

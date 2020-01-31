package org.uniprot.core.flatfile.parser.impl.cc.cclineobject;

import java.util.ArrayList;
import java.util.List;

public class MassSpectrometry {
    private String molecule;
    private float mass;
    private float massError;
    private String method;
    private String note;
    private List<String> sources = new ArrayList<>();

    public String getMolecule() {
        return molecule;
    }

    public void setMolecule(String molecule) {
        this.molecule = molecule;
    }

    public float getMass() {
        return mass;
    }

    public void setMass(float mass) {
        this.mass = mass;
    }

    public float getMassError() {
        return massError;
    }

    public void setMassError(float massError) {
        this.massError = massError;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public List<String> getSources() {
        return sources;
    }

    public void setSources(List<String> sources) {
        this.sources = sources;
    }
}

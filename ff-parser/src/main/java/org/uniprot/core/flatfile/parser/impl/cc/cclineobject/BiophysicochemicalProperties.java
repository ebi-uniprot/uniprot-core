package org.uniprot.core.flatfile.parser.impl.cc.cclineobject;

import java.util.ArrayList;
import java.util.List;

public class BiophysicochemicalProperties {
    private String molecule;
    private EvidencedString bsorptionAbs;
    private List<EvidencedString> bsorptionNote = new ArrayList<>();
    private List<EvidencedString> kms = new ArrayList<>();
    private List<EvidencedString> vmaxs = new ArrayList<>();
    private List<EvidencedString> kpNote = new ArrayList<>();
    private List<EvidencedString> phDependence = new ArrayList<>();
    private List<EvidencedString> rdoxPotential = new ArrayList<>();
    private List<EvidencedString> temperatureDependence = new ArrayList<>();
    private boolean bsorptionAbsApproximate;

    public String getMolecule() {
        return molecule;
    }

    public void setMolecule(String molecule) {
        this.molecule = molecule;
    }

    public EvidencedString getBsorptionAbs() {
        return bsorptionAbs;
    }

    public void setBsorptionAbs(EvidencedString bsorptionAbs) {
        this.bsorptionAbs = bsorptionAbs;
    }

    public List<EvidencedString> getBsorptionNote() {
        return bsorptionNote;
    }

    public void setBsorptionNote(List<EvidencedString> bsorptionNote) {
        this.bsorptionNote = bsorptionNote;
    }

    public List<EvidencedString> getKms() {
        return kms;
    }

    public void setKms(List<EvidencedString> kms) {
        this.kms = kms;
    }

    public List<EvidencedString> getVmaxs() {
        return vmaxs;
    }

    public void setVmaxs(List<EvidencedString> vmaxs) {
        this.vmaxs = vmaxs;
    }

    public List<EvidencedString> getKpNote() {
        return kpNote;
    }

    public void setKpNote(List<EvidencedString> kpNote) {
        this.kpNote = kpNote;
    }

    public List<EvidencedString> getPhDependence() {
        return phDependence;
    }

    public void setPhDependence(List<EvidencedString> phDependence) {
        this.phDependence = phDependence;
    }

    public List<EvidencedString> getRdoxPotential() {
        return rdoxPotential;
    }

    public void setRdoxPotential(List<EvidencedString> rdoxPotential) {
        this.rdoxPotential = rdoxPotential;
    }

    public List<EvidencedString> getTemperatureDependence() {
        return temperatureDependence;
    }

    public void setTemperatureDependence(List<EvidencedString> temperatureDependence) {
        this.temperatureDependence = temperatureDependence;
    }

    public boolean isBsorptionAbsApproximate() {
        return bsorptionAbsApproximate;
    }

    public void setBsorptionAbsApproximate(boolean bsorptionAbsApproximate) {
        this.bsorptionAbsApproximate = bsorptionAbsApproximate;
    }
}

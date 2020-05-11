package org.uniprot.core.flatfile.parser.impl.de;

import java.util.ArrayList;
import java.util.List;

import org.uniprot.core.flatfile.parser.impl.EvidenceInfo;
import org.uniprot.core.flatfile.parser.impl.HasEvidenceInfo;

/** User: wudong, Date: 29/08/13, Time: 11:48 */
public class DeLineObject implements HasEvidenceInfo {
    public enum FlagType {
        Precursor,
        Fragment,
        Precursor_Fragment,
        Fragments
    }

    private Name recName;
    private String altAllergen;
    private String altBiotech;
    private List<Name> altNames = new ArrayList<>();
    private List<Name> subNames = new ArrayList<>();
    private EvidenceInfo evidenceInfo = new EvidenceInfo();
    private List<String> altCdAntigens = new ArrayList<>();
    private List<String> altInns = new ArrayList<>();
    private List<NameBlock> containedNames = new ArrayList<>();
    private List<NameBlock> includedNames = new ArrayList<>();
    private List<FlagType> flags = new ArrayList<>();

    public Name getRecName() {
        return recName;
    }

    public void setRecName(Name recName) {
        this.recName = recName;
    }

    public List<Name> getAltNames() {
        return altNames;
    }

    public void setAltName(List<Name> altNames) {
        this.altNames = altNames;
    }

    public List<Name> getSubNames() {
        return subNames;
    }

    public void setSubName(List<Name> subNames) {
        this.subNames = subNames;
    }

    public String getAltAllergen() {
        return altAllergen;
    }

    public void setAltAllergen(String altAllergen) {
        this.altAllergen = altAllergen;
    }

    public String getAltBiotech() {
        return altBiotech;
    }

    public void setAltBiotech(String altBiotech) {
        this.altBiotech = altBiotech;
    }

    public List<String> getAltCdAntigens() {
        return altCdAntigens;
    }

    public void setAltCdAntigen(List<String> altCdAntigens) {
        this.altCdAntigens = altCdAntigens;
    }

    public List<String> getAltInns() {
        return altInns;
    }

    public void setAltInn(List<String> altInns) {
        this.altInns = altInns;
    }

    public List<NameBlock> getContainedNames() {
        return containedNames;
    }

    public void setContainedNames(List<NameBlock> containedNames) {
        this.containedNames = containedNames;
    }

    public List<NameBlock> getIncludedNames() {
        return includedNames;
    }

    public void setIncludedNames(List<NameBlock> includedNames) {
        this.includedNames = includedNames;
    }

    public List<FlagType> getFlags() {
        return flags;
    }

    public void setFlags(List<FlagType> flags) {
        this.flags = flags;
    }

    public void setEvidenceInfo(EvidenceInfo evidenceInfo) {
        this.evidenceInfo = evidenceInfo;
    }

    @Override
    public EvidenceInfo getEvidenceInfo() {
        return evidenceInfo;
    }

    public static class ECEvidence {
        private String ecValue;
        private Name nameECBelong;

        public String getEcValue() {
            return ecValue;
        }

        public void setEcValue(String ecValue) {
            this.ecValue = ecValue;
        }

        public Name getNameECBelong() {
            return nameECBelong;
        }

        public void setNameECBelong(Name nameECBelong) {
            this.nameECBelong = nameECBelong;
        }

        @Override
        public boolean equals(Object ev) {
            if (ev instanceof ECEvidence) {
                ECEvidence ecEv = (ECEvidence) ev;
                if (ecEv.nameECBelong != nameECBelong) return false;
                return ecEv.ecValue.equals(ecValue);
            } else return false;
        }

        @Override
        public int hashCode() {
            int result = 7;
            result = 37 * ecValue.hashCode() + result;
            result = 37 * nameECBelong.hashCode() + result;
            return result;
        }
    }

    public static class NameBlock {
        private Name recName;
        private String altAllergen;
        private String altBiotech;
        private List<Name> altNames = new ArrayList<>();
        private List<Name> subNames = new ArrayList<>();
        private List<String> altCdAntigens = new ArrayList<>();
        private List<String> altInns = new ArrayList<>();

        public Name getRecName() {
            return recName;
        }

        public void setRecName(Name recName) {
            this.recName = recName;
        }

        public List<Name> getAltNames() {
            return altNames;
        }

        public void setAltName(List<Name> altNames) {
            this.altNames = altNames;
        }

        public List<Name> getSubNames() {
            return subNames;
        }

        public void setSubName(List<Name> subName) {
            this.subNames = subName;
        }

        public String getAltAllergen() {
            return altAllergen;
        }

        public void setAltAllergen(String altAllergen) {
            this.altAllergen = altAllergen;
        }

        public String getAltBiotech() {
            return altBiotech;
        }

        public void setAltBiotech(String altBiotech) {
            this.altBiotech = altBiotech;
        }

        public List<String> getAltCdAntigens() {
            return altCdAntigens;
        }

        public void setAltCdAntigens(List<String> altCdAntigen) {
            this.altCdAntigens = altCdAntigen;
        }

        public List<String> getAltInns() {
            return altInns;
        }

        public void setAltInn(List<String> altInn) {
            this.altInns = altInn;
        }
    }

    public static class Name {
        private String fullName;
        private List<String> shortNames = new ArrayList<>();
        private List<String> ecs = new ArrayList<>();

        public String getFullName() {
            return fullName;
        }

        public void setFullName(String fullName) {
            this.fullName = fullName;
        }

        public List<String> getShortNames() {
            return shortNames;
        }

        public void setShortNames(List<String> shortNames) {
            this.shortNames = shortNames;
        }

        public List<String> getEcs() {
            return ecs;
        }

        public void setEcs(List<String> ecs) {
            this.ecs = ecs;
        }
    }
}

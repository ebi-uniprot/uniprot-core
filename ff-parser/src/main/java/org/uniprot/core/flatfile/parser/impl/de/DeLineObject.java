package org.uniprot.core.flatfile.parser.impl.de;

import java.util.ArrayList;
import java.util.List;

import org.uniprot.core.flatfile.parser.impl.EvidenceInfo;
import org.uniprot.core.flatfile.parser.impl.HasEvidenceInfo;

/** User: wudong, Date: 29/08/13, Time: 11:48 */
public class DeLineObject implements HasEvidenceInfo {
    public Name recName;
    public List<Name> altName = new ArrayList<Name>();
    public List<Name> subName = new ArrayList<Name>();
    public String altAllergen;

    public String altBiotech;
    public List<String> altCdAntigen = new ArrayList<String>();
    public List<String> altInn = new ArrayList<String>();

    public List<NameBlock> containedNames = new ArrayList<NameBlock>();
    public List<NameBlock> includedNames = new ArrayList<NameBlock>();

    public List<FlagType> flags = new ArrayList<FlagType>();

    public EvidenceInfo evidenceInfo = new EvidenceInfo();

    @Override
    public EvidenceInfo getEvidenceInfo() {
        return evidenceInfo;
    }

    public static class ECEvidence {
        public String ecValue;
        public Name nameECBelong;

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
        public Name recName;
        public List<Name> altName = new ArrayList<Name>();

        public List<Name> subName = new ArrayList<Name>();
        public String altAllergen;
        public String altBiotech;
        public List<String> altCdAntigen = new ArrayList<String>();
        public List<String> altInn = new ArrayList<String>();
    }

    public static class Name {
        public String fullName;
        public List<String> shortNames = new ArrayList<String>();
        public List<String> ecs = new ArrayList<String>();
    }

    public static enum FlagType {
        Precursor,
        Fragment,
        Precursor_Fragment,
        Fragments
    }
}

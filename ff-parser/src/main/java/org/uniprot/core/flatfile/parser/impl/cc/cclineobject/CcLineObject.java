package org.uniprot.core.flatfile.parser.impl.cc.cclineobject;

import java.util.ArrayList;
import java.util.List;

import org.uniprot.core.flatfile.parser.impl.EvidenceInfo;
import org.uniprot.core.flatfile.parser.impl.HasEvidenceInfo;

/** User: wudong, Date: 03/09/13, Time: 16:35 */
public class CcLineObject implements HasEvidenceInfo {

    private List<CC> ccs = new ArrayList<>();
    private EvidenceInfo evidenceInfo = new EvidenceInfo();

    public List<CC> getCcs() {
        return ccs;
    }

    public void setCcs(List<CC> ccs) {
        this.ccs = ccs;
    }

    @Override
    public EvidenceInfo getEvidenceInfo() {
        return evidenceInfo;
    }

    public void setEvidenceInfo(EvidenceInfo evidenceInfo) {
        this.evidenceInfo = evidenceInfo;
    }

    public enum AlternativeNameSequenceEnum {
        DISPLAYED,
        EXTERNAL,
        NOT_DESCRIBED,
        DESCRIBED,
        UNSURE
    }

    public enum CCTopicEnum {
        ALLERGEN,
        BIOTECHNOLOGY,
        CATALYTIC_ACTIVITY,
        CAUTION,
        COFACTOR,
        DEVELOPMENTAL_STAGE,
        DISEASE,
        DISRUPTION_PHENOTYPE,
        DOMAIN,
        ACTIVITY_REGULATION,
        FUNCTION,
        INDUCTION,
        MISCELLANEOUS,
        PATHWAY,
        PHARMACEUTICAL,
        POLYMORPHISM,
        PTM,
        SIMILARITY,
        SUBUNIT,
        TISSUE_SPECIFICITY,
        TOXIC_DOSE,
        ALTERNATIVE_PRODUCTS,
        BIOPHYSICOCHEMICAL_PROPERTIES,
        WEB_RESOURCE,
        INTERACTION,
        SUBCELLULAR_LOCATION,
        SEQUENCE_CAUTION,
        MASS_SPECTROMETRY,

        RNA_EDITING;

        public static CCTopicEnum fromString(String s) {
            String replace = s.replace(' ', '_');
            //            if (replace.equals("ACTIVITY_REGULATION")) {
            //                replace = "ENZYME_REGULATION";
            //            }
            return CCTopicEnum.valueOf(replace);
        }
    }

    public enum LocationFlagEnum {
        BY_SIMILARITY,
        PROBABLE,
        POTENTIAL,
        FLAG;

        public static LocationFlagEnum fromSting(String s) {
            String s1 = s.toLowerCase();
            if (s1.indexOf("by") >= 0 && s.indexOf("similarity") > 0) return BY_SIMILARITY;
            else if (s1.indexOf("probable") >= 0) {
                return PROBABLE;
            } else if (s1.indexOf("potential") >= 0) {
                return POTENTIAL;
            } else throw new RuntimeException(s + " cannot be parsed to the location flag");
        }
    }

    public enum RnaEditingLocationEnum {
        UNDETERMINED,
        NOT_APPLICABLE
    }

    public enum SequenceCautionType {
        FRAMESHIFT,
        ERRONEOUS_INITIATION,
        ERRONEOUS_TERMINATION,
        ERRONEOUS_GENE_MODEL_PREDICTION,
        ERRONEOUS_TRANSLATION,
        MISCELLANEOUS_DISCREPANCY;

        public static SequenceCautionType fromSting(String s) {
            String replace = s.replace(' ', '_');
            return SequenceCautionType.valueOf(replace.toUpperCase());
        }
    }
}

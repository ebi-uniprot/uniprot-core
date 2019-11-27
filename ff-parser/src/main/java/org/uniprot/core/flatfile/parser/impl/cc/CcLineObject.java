package org.uniprot.core.flatfile.parser.impl.cc;

import java.util.ArrayList;
import java.util.List;

import org.uniprot.core.flatfile.parser.impl.EvidenceInfo;
import org.uniprot.core.flatfile.parser.impl.HasEvidenceInfo;

/** User: wudong, Date: 03/09/13, Time: 16:35 */
public class CcLineObject implements HasEvidenceInfo {

    public List<CC> ccs = new ArrayList<>();
    public EvidenceInfo evidenceInfo = new EvidenceInfo();

    public static class EvidencedString {
        public final String value;
        public final List<String> evidences = new ArrayList<>();

        public EvidencedString(String value, List<String> evidences) {
            this.value = value;
            if (evidences != null) {
                this.evidences.addAll(evidences);
            }
        }

        public static EvidencedString get(String text1) {
            return new EvidencedString(text1, null);
        }

        public static EvidencedString get(String text1, List<String> strings) {
            if (strings == null || strings.isEmpty()) {
                return get(text1);
            } else {
                return new EvidencedString(text1, strings);
            }
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            final EvidencedString evValue = (EvidencedString) o;

            if (evidences != null && !evidences.isEmpty()
                    ? !evidences.equals(evValue.evidences)
                    : evValue.evidences != null && !evValue.evidences.isEmpty()) return false;

            return value.equals(evValue.value);
        }

        @Override
        public int hashCode() {
            int result = 0;
            result = 29 * result + value.hashCode();
            result = 29 * result + evidences.hashCode();
            return result;
        }
    }

    @Override
    public EvidenceInfo getEvidenceInfo() {
        return evidenceInfo;
    }

    public static class CC {
        public CCTopicEnum topic;
        public Object object;
    }

    public static class AlternativeProducts {
        public List<String> events = new ArrayList<>();
        public String namedIsoforms;
        public List<EvidencedString> comment = new ArrayList<>(); // list of evidenced String

        public List<AlternativeProductName> names = new ArrayList<>();
    }

    public static class AlternativeProductName {
        public EvidencedString name;
        public List<EvidencedString> synNames = new ArrayList<>();
        public List<String> isoId = new ArrayList<>();
        public List<String> sequenceFTId = new ArrayList<>();
        public AlternativeNameSequenceEnum sequenceEnum = null;
        public List<EvidencedString> note = new ArrayList<>(); // list of evidenced String
    }

    public static class BiophysicochemicalProperties {
        public String molecule;
        public EvidencedString bsorptionAbs;
        public List<EvidencedString> bsorptionNote = new ArrayList<>();
        public List<EvidencedString> kms = new ArrayList<>();
        public List<EvidencedString> vmaxs = new ArrayList<>();
        public List<EvidencedString> kpNote = new ArrayList<>();
        public List<EvidencedString> phDependence = new ArrayList<>();
        public List<EvidencedString> rdoxPotential = new ArrayList<>();
        public List<EvidencedString> temperatureDependence = new ArrayList<>();
        public boolean bsorptionAbsApproximate;
    }

    public enum AlternativeNameSequenceEnum {
        DISPLAYED,
        EXTERNAL,
        NOT_DESCRIBED,
        DESCRIBED,
        UNSURE
    }

    public static class WebResource {
        public String molecule;
        public String name;
        public String url;
        public String note;
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

    public static class Interaction {
        public List<InteractionObject> interactions = new ArrayList<>();
    }

    public static class InteractionObject {
        public boolean isSelf;
        public String spAc;
        public String gene;
        public boolean xeno;
        public int nbexp;
        public String firstId;
        public String secondId;
    }

    public static class SubcullarLocation {
        public String molecule;
        public List<LocationObject> locations = new ArrayList<>();
        public List<EvidencedString> note = new ArrayList<>();
    }

    public static class LocationValue {
        public String value;
        public LocationFlagEnum flag;
    }

    public static class LocationObject {

        public LocationValue subcellularLocation;
        public LocationValue topology;
        public LocationValue orientation;
    }

    public static enum LocationFlagEnum {
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

    public static class RnaEditing {
        public String molecule;
        public RnaEditingLocationEnum locationEnum;
        public List<Integer> locations = new ArrayList<>();
        public List<EvidencedString> note = new ArrayList<>();
    }

    public static enum RnaEditingLocationEnum {
        UNDETERMINED,
        NOT_APPLICABLE
    }

    public static class SequenceCaution {
        public String molecule;
        public List<SequenceCautionObject> sequenceCautionObjects = new ArrayList<>();
    }

    public static enum SequenceCautionType {
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

    public static class SequenceCautionObject {
        public String molecule;
        public String sequence;
        public SequenceCautionType type;
        //   public List<Integer> positions = new ArrayList<>();
        //   public String positionValue;
        public String note;
    }

    public static class MassSpectrometry {
        public String molecule;
        public float mass;
        public float massError;
        public String method;
        public String note;
        public List<String> sources = new ArrayList<>();
    }

    public static class Disease {
        public String molecule;
        public String name;
        public String abbr;
        public String mim;
        public String description;
        public List<EvidencedString> note = new ArrayList<>();
    }

    public static class StructuredCofactor {
        public String molecule;
        public List<EvidencedString> note = new ArrayList<>();
        public List<CofactorItem> cofactors = new ArrayList<>();
    }

    public static class CofactorItem {
        public String name;
        public String xref;
    }

    public static class FreeText {
        public String molecule;
        public List<EvidencedString> texts = new ArrayList<>();
    }

    public static class CatalyticActivity {
        public String molecule;
        public CAReaction reaction;
        public List<CAPhysioDirection> physiologicalDirections = new ArrayList<>();
    }

    public static class CAReaction {
        public String name;
        public String xref;
        public String ec;
    }

    public static class CAPhysioDirection {
        public String name;
        public String xref;
    }
}

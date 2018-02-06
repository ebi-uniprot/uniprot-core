package uk.ac.ebi.uniprot.parser.impl.cc;

import java.util.ArrayList;
import java.util.List;

import uk.ac.ebi.uniprot.parser.impl.EvidenceInfo;
import uk.ac.ebi.uniprot.parser.impl.HasEvidenceInfo;

/**
 * <p/>
 * User: wudong, Date: 03/09/13, Time: 16:35
 */
public class CcLineObject implements HasEvidenceInfo{
	
	
	public List<CC> ccs = new ArrayList<CC>();
	public EvidenceInfo evidenceInfo = new EvidenceInfo();

	public static class EvidencedString{
		public final String value;
		public final List<String> evidences = new ArrayList<>();

		public EvidencedString(String value, List<String> evidences){
			this.value = value;
			if (evidences!=null){
                this.evidences.addAll(evidences);
            }
		}

        public static EvidencedString get(String text1) {
            return new EvidencedString(text1, null);
        }

        public static EvidencedString get(String text1, List<String> strings) {
            if (strings==null || strings.size()==0){
                return get(text1);
            }else{
                return new EvidencedString(text1, strings);
            }
        }

        @Override
        public boolean equals(Object o) {
            if (this == o)
                return true;
            if (o == null || getClass() != o.getClass())
                return false;
            final EvidencedString evValue = (EvidencedString) o;

            if (evidences != null && evidences.size() > 0 ? !evidences
                    .equals(evValue.evidences) : evValue.evidences != null
                    && evValue.evidences.size() > 0)
                return false;

            return value.equals(evValue.value);
        }
        @Override
        public int hashCode(){
            int result =0;
            result =29*result + value.hashCode();
            result =29*result + evidences.hashCode();
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
		public List<String> events = new ArrayList<String>();
		public String namedIsoforms;
		public List<EvidencedString> comment =new ArrayList<>(); //list of evidenced String

		public List<AlternativeProductName> names = new ArrayList<AlternativeProductName>();
	}

	public static class AlternativeProductName {
		public EvidencedString name;
		public List<EvidencedString> synNames = new ArrayList<>();
		public List<String> isoId = new ArrayList<String>();
		public List<String> sequence_FTId = new ArrayList<String>();
		public AlternativeNameSequenceEnum sequence_enum = null;
		public List<EvidencedString> note = new ArrayList<>(); //list of evidenced String
	}

	public static class BiophysicochemicalProperties {
		public EvidencedString bsorption_abs;
		public List<EvidencedString> bsorption_note = new ArrayList<>();
		public List<EvidencedString> kms = new ArrayList<EvidencedString>();
		public List<EvidencedString> vmaxs = new ArrayList<EvidencedString>();
		public List<EvidencedString> kp_note =new ArrayList<>();
		public List<EvidencedString> ph_dependence =new ArrayList<>();
		public List<EvidencedString> rdox_potential =new ArrayList<>();
		public List<EvidencedString> temperature_dependence =new ArrayList<>();
        public boolean bsorption_abs_approximate;
    }

	public static enum AlternativeNameSequenceEnum {
		Displayed, External, Not_described, Described, Unsure
	}


	public static class WebResource {
		public String name;
		public String url;
		public String note;
	}


	public static enum CCTopicEnum {
		ALLERGEN, BIOTECHNOLOGY, CATALYTIC_ACTIVITY, CAUTION, COFACTOR,
		DEVELOPMENTAL_STAGE, DISEASE, DISRUPTION_PHENOTYPE, DOMAIN,
		ENZYME_REGULATION, FUNCTION, INDUCTION, MISCELLANEOUS,
		PATHWAY, PHARMACEUTICAL, POLYMORPHISM, PTM, SIMILARITY,
		SUBUNIT, TISSUE_SPECIFICITY, TOXIC_DOSE, ALTERNATIVE_PRODUCTS,
		BIOPHYSICOCHEMICAL_PROPERTIES, WEB_RESOURCE, INTERACTION,
		SUBCELLULAR_LOCATION, SEQUENCE_CAUTION, MASS_SPECTROMETRY, RNA_EDITING;

		public static CCTopicEnum fromSting(String s) {
			String replace = s.replace(' ', '_');
			return CCTopicEnum.valueOf(replace);
		}
	}

	public static class Interaction {
		public List<InteractionObject> interactions = new ArrayList<InteractionObject>();
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
		 public List<LocationObject> locations = new ArrayList<LocationObject>();
         public List<EvidencedString> note =new ArrayList<>();
//		 public List<SubcullarLocationNote> notes = new ArrayList<SubcullarLocationNote>();
	}

//	public static class SubcullarLocationNote{
//		public String note;
//	//	public LocationFlagEnum noteFlag;
//	}

    public static class LocationValue {
        public String value;
        public LocationFlagEnum flag;
    }

	public static class LocationObject {
//		public String subcellular_location;
//		public LocationFlagEnum subcellular_location_flag;
//		public String topology;
//		public LocationFlagEnum topology_flag;
//		public String orientation;
//		public LocationFlagEnum orientation_flag;

        public LocationValue subcellular_location;
        public LocationValue topology;
        public LocationValue orientation;

	}

	public static enum LocationFlagEnum {
		By_similarity, Probable, Potential, flag;

		public static LocationFlagEnum fromSting(String s) {
            String s1 = s.toLowerCase();
            if (s1.indexOf("by")>=0 && s.indexOf("similarity")>0)
                return By_similarity;
            else if (s1.indexOf("probable")>=0){
                return Probable;
            }else if (s1.indexOf("potential")>=0){
                return Potential;
            }else
                throw new RuntimeException(s +" cannot be parsed to the location flag");
		}
	}

	public static class RnaEditing {
		public RnaEditingLocationEnum locationEnum;
		public List<Integer> locations = new ArrayList<Integer>();
		public List<EvidencedString> note =new ArrayList<>();
	}

	public static enum RnaEditingLocationEnum {
		Undetermined , Not_applicable
	}

	public static class SequenceCaution {
		public List<SequenceCautionObject> sequenceCautionObjects = new ArrayList<SequenceCautionObject>();
	}

	public static enum SequenceCautionType {
		Frameshift,
		Erroneous_initiation,
		Erroneous_termination,
		Erroneous_gene_model_prediction,
		Erroneous_translation,
		Miscellaneous_discrepancy;

		public static SequenceCautionType fromSting(String s) {
			String replace = s.replace(' ', '_');
			return SequenceCautionType.valueOf(replace);
		}
	}

	public static class SequenceCautionObject {
		public String sequence;
		public SequenceCautionType type;
		public List<Integer> positions = new ArrayList<Integer>();
		public String positionValue;
		public String note;

	}


	public static class MassSpectrometry {
		public float mass;
		public float mass_error;
		public String method;
		public List<MassSpectrometryRange> ranges = new ArrayList<MassSpectrometryRange>();
//		public String range_isoform;
		public String note;
		public List<String> sources = new ArrayList<String>();
	}

	public static class MassSpectrometryRange {
		public int start;
		public boolean start_unknown;
		public int end;
		public boolean end_unknown;
        public String range_isoform;
	}

	public static class Disease {
		public String name;
		public String abbr;
		public String mim;
		public String description ;
		public List<EvidencedString> note =new ArrayList<> () ;
	}

	public static class StructuredCofactor {
		public String molecule;
		public List<EvidencedString> note =new ArrayList<>();
		public  List<CofactorItem> cofactors = new ArrayList<>();
	}
	public static class CofactorItem {
		public String name;
		public String xref;
	}

	public static class FreeText {
		public List<EvidencedString> texts = new ArrayList<> ();
	}
}

package uk.ac.ebi.uniprot.domain.uniprot;

/**
 * Created by IntelliJ IDEA.
 * User: barrera
 * Date: 25/05/11
 * Time: 15:20
 *
 *
 * Used in the RC line of the flatfile
 * The RC (Reference Comment) lines are optional lines which are used to store comments relevant to the reference cited.
 * The format of the RC line is:
 *
 * RC   TOKEN1=Text; TOKEN2=Text; ...
 * The currently defined tokens and their order in the RC line are:
 *
 * STRAIN
 * TISSUE
 * TRANSPOSON
 * PLASMID
 * Reference comment line topics may span lines. Examples of RC lines:
 *
 * RC   STRAIN=Sprague-Dawley; TISSUE=Liver;
 * RC   STRAIN=Holstein; TISSUE=Lymph node, and Mammary gland;
 * RC   STRAIN=301 / Serotype 2a;
 * RC   STRAIN=cv. SP753012-O; TISSUE=Leaf;
 * RC   PLASMID=R1 (R7268); TRANSPOSON=Tn3;
 * RC   STRAIN=AL.012, AZ.026, AZ.180, DC.005, GA.039, GA2181, IL.014, IL2.17,
 * RC   IN.018, KY.172, KY2.37, LA.013, MI.035, MN.001, MNb027, MS.040,
 * RC   NY.016, OH.036, TN.173, TN2.38, UT.002, and VA.015;
 *
 *
 */

public enum ReferenceCommentType {

    PLASMID("PLASMID"),
	TISSUE("TISSUE"),
	TRANSPOSON("TRANSPOSON"),
	STRAIN("STRAIN");

    private String value;

    private ReferenceCommentType(String type){
        this.value = type;
    }

    private String getValue() {
		return value;
	}

	public static ReferenceCommentType typeOf (String value) {
		for (ReferenceCommentType type : ReferenceCommentType.values()) {
			if (type.getValue().equalsIgnoreCase(value)) {
				return type;
			}
		}
		throw new IllegalArgumentException("The SampleSource type " + value + " doesn't exist");
	}
}

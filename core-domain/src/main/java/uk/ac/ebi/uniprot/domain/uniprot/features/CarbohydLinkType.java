package uk.ac.ebi.uniprot.domain.uniprot.features;

/**
 * Descripes the element by which the {@link CarbohydFeature CarbohydFeature}  is linked.
 * <br><br>
 * These values can be found in the FT CARBOHYD line of the flat file on the marked position.
 * <pre class="example"><font color="#AAAAAA">
 * FT   CARBOHYD    121    121     <font color="#000000">C-linked</font> (Man) (By similarity).
 * FT   CARBOHYD    134    134     <font color="#000000">N-linked</font> (GlcNAc...) (Potential).
 * FT   CARBOHYD    145    145     <font color="#000000">O-linked</font> (Xyl...) (glycosaminoglycan).
 * </font></pre>
 */
public enum CarbohydLinkType {

	CARBON ("C-linked"),
	NITROGEN("N-linked"),
	OXYGEN("O-linked"),
	SULFUR ("S-linked"),
	UNKNOWN("UNKNOWN");

	private String value;

    CarbohydLinkType(String s) {
		value =s;
	}
	public String getValue () {
		return this.value;
	}

    public static CarbohydLinkType typeof(String type) {
		for (CarbohydLinkType carbohydLinkType : CarbohydLinkType.values()) {
			if (carbohydLinkType.getValue().equalsIgnoreCase(type)) {
				return carbohydLinkType;
			}

		}

        throw new IllegalArgumentException( "This CarbohydLinkType "+type+" doesn't exist");
	}
}

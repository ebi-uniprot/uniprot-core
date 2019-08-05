package uk.ac.ebi.uniprot.domain.proteome;

import org.uniprot.core.common.EnumDisplay;

/**
 *
 * @author jluo
 * @date: 11 Jun 2019
 *
*/

public enum ComponentType implements EnumDisplay<ComponentType> {
	PRIMARY("Primary"),
	CON("CON"),
	SEGMENTED_GENOME("Segmented genome"),
	WGS("WGS"),
	GCA("GCA"),
	GCF("GCF"),
	UNPLACED ("Unplaced");
	private final String name;
	
	ComponentType(String name){
		this.name= name;
	}
	public String getName() {
		return name;
	}
	@Override
	public String toDisplayName() {
		return name;
	}
	public static ComponentType fromValue(String type) {
		for(ComponentType gnType: ComponentType.values()) {
			if(gnType.getName().equals(type))
				return gnType;
		}
		return ComponentType.UNPLACED;
	}
}


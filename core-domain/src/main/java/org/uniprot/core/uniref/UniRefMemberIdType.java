package org.uniprot.core.uniref;

import org.uniprot.core.util.EnumDisplay;

/**
 *
 * @author jluo
 * @date: 12 Aug 2019
 *
*/

public enum UniRefMemberIdType implements EnumDisplay<UniRefMemberIdType> {
	UNIPROT("UniProtKB ID"),
	UNIPARC("UniParc ID");
	private String displayName;
	UniRefMemberIdType(String displayName){
		this.displayName = displayName;
	}
	 public static UniRefMemberIdType typeOf(String name) {
	        for (UniRefMemberIdType type : UniRefMemberIdType.values()) {
	            if (type.toDisplayName().equalsIgnoreCase(name)) {
	                return type;
	            }
	        }
	        throw new IllegalArgumentException("the UniRef Member Id Type: " + name + " doesn't exist");
	    }

	
	@Override
	public String toDisplayName() {
		return displayName;
	}
}


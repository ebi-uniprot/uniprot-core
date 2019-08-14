package org.uniprot.core.uniref;

import org.uniprot.core.util.EnumDisplay;

/**
 *
 * @author jluo
 * @date: 12 Aug 2019
 *
*/

public enum UniRefType implements EnumDisplay<UniRefType>{
	UniRef100("1.0"),
	UniRef90("0.9"),
	UniRef50("0.5");
	private String identity;
	
	UniRefType(String identity){
		this.identity = identity;
	}
	@Override
	public String toDisplayName() {
		return name();
	}
	public String getIdentity() {
		return this.identity;
	}
}


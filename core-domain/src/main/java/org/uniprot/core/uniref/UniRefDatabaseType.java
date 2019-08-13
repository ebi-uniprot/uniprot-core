package org.uniprot.core.uniref;

import org.uniprot.core.util.EnumDisplay;

/**
 *
 * @author jluo
 * @date: 12 Aug 2019
 *
*/

public enum UniRefDatabaseType implements EnumDisplay<UniRefDatabaseType>{
	UniRef100,
	UniRef90,
	UniRef50;

	@Override
	public String toDisplayName() {
		return name();
	}
}


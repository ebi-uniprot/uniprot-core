package org.uniprot.core.uniref.builder;

import org.uniprot.core.builder.AbstractValueBuilder;
import org.uniprot.core.uniref.UniRefEntryId;
import org.uniprot.core.uniref.impl.UniRefEntryIdImpl;

/**
 *
 * @author jluo
 * @date: 12 Aug 2019
 *
*/

public class UniRefEntryIdBuilder extends AbstractValueBuilder<UniRefEntryIdBuilder, UniRefEntryId> {

	public UniRefEntryIdBuilder(String value) {
		super(value);
	}

	@Override
	public UniRefEntryId build() {
		return new UniRefEntryIdImpl(value);
	}

	@Override
	protected UniRefEntryIdBuilder getThis() {
		return this;
	}

}


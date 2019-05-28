package uk.ac.ebi.uniprot.domain.uniparc.builder;

import uk.ac.ebi.uniprot.domain.builder.AbstractValueBuilder;
import uk.ac.ebi.uniprot.domain.uniparc.UniParcId;
import uk.ac.ebi.uniprot.domain.uniparc.impl.UniParcIdImpl;

/**
 *
 * @author jluo
 * @date: 23 May 2019
 *
*/

public class UniParcIdBuilder extends AbstractValueBuilder<UniParcIdBuilder, UniParcId> {
	public UniParcIdBuilder(String value) {
        super(value);
    }
	@Override
	public UniParcId build() {
		return new UniParcIdImpl(value);
	}

	@Override
	protected UniParcIdBuilder getThis() {
		return this;
	}

}


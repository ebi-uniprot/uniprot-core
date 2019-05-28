package uk.ac.ebi.uniprot.domain.uniparc.builder;

import java.time.LocalDate;

import uk.ac.ebi.uniprot.domain.builder.AbstractDBCrossReferenceBuilder;
import uk.ac.ebi.uniprot.domain.uniparc.UniParcDBCrossReference;
import uk.ac.ebi.uniprot.domain.uniparc.UniParcDatabaseType;
import uk.ac.ebi.uniprot.domain.uniparc.impl.UniParcDBCrossReferenceImpl;

/**
 *
 * @author jluo
 * @date: 23 May 2019
 *
 */

public class UniParcDBCrossReferenceBuilder extends
		AbstractDBCrossReferenceBuilder<UniParcDBCrossReferenceBuilder, UniParcDatabaseType, UniParcDBCrossReference> {
	private int versionI;
	private Integer version;
	private boolean active;
	private LocalDate created;
	private LocalDate lastUpdated;

	@Override
	public UniParcDBCrossReference build() {
		return new UniParcDBCrossReferenceImpl(databaseType, id, properties, versionI, version, active, created,
				lastUpdated);
	}

	public UniParcDBCrossReferenceBuilder versionI(int versionI) {
		this.versionI = versionI;
		return this;
	}

	public UniParcDBCrossReferenceBuilder version(Integer version) {
		this.version = version;
		return this;
	}

	public UniParcDBCrossReferenceBuilder active(boolean active) {
		this.active = active;
		return this;
	}

	public UniParcDBCrossReferenceBuilder created(LocalDate created) {
		this.created = created;
		return this;
	}

	public UniParcDBCrossReferenceBuilder lastUpdated(LocalDate lastUpdated) {
		this.lastUpdated = lastUpdated;
		return this;
	}

	@Override
	public UniParcDBCrossReferenceBuilder from(UniParcDBCrossReference instance) {
		return super.from(instance).versionI(instance.getVersionI()).version(instance.getVersion())
				.active(instance.isActive()).created(instance.getCreated()).lastUpdated(instance.getLastUpdated());
	}

	@Override
	protected UniParcDBCrossReferenceBuilder getThis() {
		return this;
	}

}

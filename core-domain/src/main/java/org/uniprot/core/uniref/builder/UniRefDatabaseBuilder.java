package org.uniprot.core.uniref.builder;

import java.time.LocalDate;

import org.uniprot.core.Builder;
import org.uniprot.core.uniref.UniRefDatabase;
import org.uniprot.core.uniref.UniRefDatabaseType;
import org.uniprot.core.uniref.impl.UniRefDatabaseImpl;

/**
 *
 * @author jluo
 * @date: 12 Aug 2019
 *
*/

public class UniRefDatabaseBuilder implements Builder<UniRefDatabaseBuilder, UniRefDatabase> {
	private UniRefDatabaseType type;
	private String version;
	private LocalDate releaseDate;
	@Override
	public UniRefDatabase build() {
		return new UniRefDatabaseImpl(type, version, releaseDate);
	}

	@Override
	public UniRefDatabaseBuilder from(UniRefDatabase instance) {
		return this.type(instance.getType())
				.version(instance.getVersion())
				.releaseDate(instance.getReleaseDate());
	}

	public UniRefDatabaseBuilder type(UniRefDatabaseType type) {
		this.type = type;
		return this;
	}
	
	public UniRefDatabaseBuilder  version(String version) {
		this.version =version;
		return this;
	}
	
	public UniRefDatabaseBuilder releaseDate(LocalDate releaseDate) {
		this.releaseDate = releaseDate;
		return this;
	}
}

